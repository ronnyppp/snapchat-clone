package com.example.snapchatclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import com.example.snapchatclone.ui.theme.SnapChatCloneTheme
import com.example.snapchatclone.navigation.NavBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(

        )
        setContent {
            SnapChatCloneTheme {
                NavBar()
            }
        }
    }
}



