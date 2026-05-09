package com.example.snapchatclone.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapchatclone.R
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// view model to read to and from DataStore/Compose
class AvatarViewModel(application: Application) : AndroidViewModel(application) {
    private val userSettings = UserSettings(application)

    // Convert Flow to State that Compose can watch
    val avatarState: StateFlow<Int> = userSettings.getAvatar
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = R.drawable.wyatt)

    fun updateAvatar(resId: Int) {
        viewModelScope.launch {
            userSettings.saveAvatar(resId)
        }
    }
    
    fun clearAvatar() {
        viewModelScope.launch {
            userSettings.saveAvatar(R.drawable.wyatt)
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            userSettings.clearData()
        }
    }
}
