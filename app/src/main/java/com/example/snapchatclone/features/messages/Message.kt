package com.example.snapchatclone.features.messages

data class Message(
    val text: String = "",
    val senderId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)