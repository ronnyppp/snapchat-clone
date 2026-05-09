package com.example.snapchatclone.features.login

import com.example.snapchatclone.R

// use provider for easy access to avatars
object AvatarProvider {
    val avatars =
        mapOf("adrian" to R.drawable.adrian,
            "eden" to R.drawable.eden,
            "maria" to R.drawable.maria,
            "steve" to R.drawable.steve,
            "wyatt" to R.drawable.wyatt)

    fun getResId(name: String?): Int {
        return avatars[name] ?: R.drawable.wyatt
    }
    fun getName(resId: Int): String {
        return avatars.entries.find { it.value == resId }?.key ?: "wyatt"
    }
}