package com.example.snapchatclone.features.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapchatclone.R
import com.example.snapchatclone.auth.AuthViewModel
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.features.login.AvatarProvider

@Composable
fun ProfileSetupScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    avatarViewModel: AvatarViewModel = viewModel()
) {
    // input state
    var username by remember { mutableStateOf("") }
    // curr avatar
    var selectedAvatar by remember { mutableIntStateOf(R.drawable.wyatt) }
    //toggle to show avatars
    var showAvatars by remember { mutableStateOf(false) }
    // available avatars
    val avatars = listOf(
        R.drawable.adrian,
        R.drawable.eden,
        R.drawable.maria,
        R.drawable.steve,
        R.drawable.wyatt
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFC00))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(120.dp))
        Text("Sign up for Snapchat", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(60.dp))

        // avatar preview and selection
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    showAvatars = !showAvatars
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(selectedAvatar),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        // avatar picker
        if (showAvatars) {
            AnimatedVisibility(visible = showAvatars) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    items(avatars) { avatar ->
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .border(
                                    width = if (selectedAvatar == avatar) 3.dp else 0.dp,
                                    color = Color.Black,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedAvatar = avatar
                                    showAvatars = false
                                }
                        ) {
                            Image(
                                painter = painterResource(avatar),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(Modifier.height(60.dp))

        // username input
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "By tapping Continue, you agree to our Terms and Privacy Policy.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
        // submit profile
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            onClick = {
                if (username.isBlank()) {
                    return@Button
                }

                // convert drawable to avatar model indentifier
                val avatarName = AvatarProvider.getName(selectedAvatar)
                
                // Save to locally
                avatarViewModel.updateAvatar(selectedAvatar)
                // save to backend
                viewModel.saveUser(username, avatarName) { success ->
                    if (success) {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            }
        ) {
            Text("Continue")
        }
    }
}
