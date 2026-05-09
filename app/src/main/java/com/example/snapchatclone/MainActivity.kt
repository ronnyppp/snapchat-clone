package com.example.snapchatclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.snapchatclone.navigation.AuthNavGraph
import com.example.snapchatclone.ui.theme.SnapChatCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapChatCloneTheme {
                AuthNavGraph()
            }
        }
    }
}
