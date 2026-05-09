package com.example.snapchatclone.features.map.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun MapScreen(
    navController: NavController,
    avatarViewModel: AvatarViewModel)
{
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
                Text("Snap Map", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            },
            rightContent = {
                Spacer(modifier = Modifier.width(16.dp))
                FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.DarkGray, contentColor = Color.White
                ), modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
            }
        )
        // Bottom-Center Buttons


    }
}