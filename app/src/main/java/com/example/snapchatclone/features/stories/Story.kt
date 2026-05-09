package com.example.snapchatclone.features.stories

// class for stories to be used in StoriesViewModel
data class Story(
    val userId: String = "",
    val username: String = "",
    val avatarResId: Int = 0,
    val imageUrl: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
