package com.example.snapchatclone.features.spotlight.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.components.TopBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SpotlightScreen(
    navController: NavController,
    avatarViewModel: AvatarViewModel
) {
    val avatarId by avatarViewModel.avatarState.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Bottom-most layer
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Gray),
    ) {
        TopBar(
            leftContent = {
                FilledIconButton(onClick = {
                    navController.navigate("profile/$userId")
                }, colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.DarkGray, contentColor = Color.White
                ), modifier = Modifier.size(40.dp)) {
                    Image(
                        painter = painterResource(avatarId),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.DarkGray, contentColor = Color.White
                ), modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
            },
            centerContent = {
                Text("Spotlight", fontSize = 25.sp, fontWeight = FontWeight.Bold
                )
            },
            rightContent = {
                Spacer(modifier = Modifier.width(40.dp * 2 + 16.dp))
            }
        )

        // User/Post Info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("@username", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Caption goes here...", color = Color.White)
        }
        // Interaction Buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Favorite, contentDescription = "Like", tint = Color.White)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.ChatBubble, contentDescription = "Comment", tint = Color.White)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
            }
        }
    }
}