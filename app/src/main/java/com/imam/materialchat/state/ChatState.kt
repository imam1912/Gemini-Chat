package com.imam.materialchat.state

import com.imam.materialchat.model.Chat

data class ChatState(
    val prompt: String = "",
    var isLoading: Boolean = false,
    val list: MutableList<Chat>? = mutableListOf(),
)
