package com.imam.materialchat.model

data class Chat(
    val message: String,
    val role: String,
    val direction: Boolean,
)

enum class ChatRoleEnum(val value: String) {
    USER("user"),
    MODEL("model")
}

