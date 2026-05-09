package com.example.snapchatclone.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.snapchatclone.features.login.AvatarProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// manager class to save and read avatar
class UserSettings(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("user_settings")

        val AVATAR_KEY = stringPreferencesKey("avatar_key")
    }
    // read avatar
    val getAvatar: Flow<Int> = context.dataStore.data
        .map { preferences ->
            val key = preferences[AVATAR_KEY]
            AvatarProvider.getResId(key)
        }
    // save avatar
    suspend fun saveAvatar(resId: Int) {
        val key = AvatarProvider.getName(resId)

        context.dataStore.edit { preferences ->
            preferences[AVATAR_KEY] = key
        }
    }

    // clear all settings
    suspend fun clearData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}