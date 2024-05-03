package com.imam.materialchat.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.imam.materialchat.model.Chat
import com.imam.materialchat.model.ChatRoleEnum
import com.imam.materialchat.state.ChatState
import com.imam.materialchat.state.ChatStateEvent
import com.imam.materialchat.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {


    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    var dialogHistory by mutableStateOf(false)


    val emojiList =
        listOf("╮(╯ _╰ )╭", "╮(╯ ∀ ╰)╭", "╮ (. ❛ ❛ _.) ╭", "└(・。・)┘", "┐(￣ ヘ￣)┌").random()


    private val genAI by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = Utils.API_KEY
        )
    }

    fun showDialogHistory() {
        dialogHistory = true
    }

    fun dismissDialogHistory() {
        dialogHistory = false
    }

    fun onEvent(event: ChatStateEvent) {
        when (event) {
            ChatStateEvent.ButtonClicked -> {
                try {
                    if (state.value.prompt.isNotBlank()) {
                        _state.update {
                            it.copy(
                                list = it.list!!.toMutableList().apply {
                                    add(
                                        Chat(
                                            state.value.prompt,
                                            ChatRoleEnum.USER.value,
                                            direction = false
                                        )
                                    )
                                }
                            )
                        }
                        sendMessage(_state.value.prompt)
                    }
                } catch (e: Exception) {
                    Log.d("logs", "onEvent: $e")
                }
            }

            is ChatStateEvent.EnteredPrompt -> {
                _state.update { it.copy(prompt = event.promptText) }
            }

            ChatStateEvent.ClearChatClicked -> {
                clearChat()
            }
        }
    }


    private fun clearChat() {
        _state.update { it.copy(isLoading = true, list = mutableListOf()) }
        _state.update { it.copy(isLoading = false) }
    }

    private fun sendMessage(message: String) {
        _state.update { it.copy(isLoading = true) }
        try {
            viewModelScope.launch {
                val chat = genAI.startChat()
                val updatedList = _state.value.list?.toMutableList() ?: mutableListOf()
                chat.sendMessage(
                    content(ChatRoleEnum.USER.value) {
                        text(message)
                        _state.update { it.copy(prompt = "") }
                    }
                ).text?.let {
                    updatedList.add(Chat(it, ChatRoleEnum.MODEL.value, direction = true))
                }

                _state.update { it.copy(list = updatedList, isLoading = false) }
            }
        } catch (e: Exception) {
            Log.d("logs", "sendMessage: $e")
        }
    }


}