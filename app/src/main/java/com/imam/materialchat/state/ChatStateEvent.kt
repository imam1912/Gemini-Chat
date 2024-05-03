package com.imam.materialchat.state

sealed class ChatStateEvent {
    data class EnteredPrompt(val promptText: String) : ChatStateEvent()
    data object ButtonClicked : ChatStateEvent()
    data object ClearChatClicked : ChatStateEvent()
}