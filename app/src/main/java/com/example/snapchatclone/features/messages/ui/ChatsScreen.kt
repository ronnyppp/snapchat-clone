package com.example.snapchatclone.features.messages.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.snapchatclone.R
import com.example.snapchatclone.auth.UserRepository
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.components.TopBar
import com.example.snapchatclone.features.login.AvatarProvider
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatsScreen(navController: NavController,
                avatarViewModel: AvatarViewModel) {
    // user repo
    val repo = remember { UserRepository() }
    // list of users
    var users by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    // curr users avatar state
    val avatarId by avatarViewModel.avatarState.collectAsState()

    // get users from repo
    LaunchedEffect(Unit) {
        repo.getUsers {
            users = it
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            TopBar(
                leftContent = {
                    FilledIconButton(
                        onClick = { 
                            val uid = FirebaseAuth.getInstance().currentUser?.uid
                            navController.navigate("profile/$uid")
                        }, 
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.5f), contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = avatarId),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    }
                    FilledIconButton(
                        onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.5f), contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                },
                centerContent = {
                    Text("Chat", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                },
                rightContent = {
                    FilledIconButton(
                        onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.5f), contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Add", tint = Color.White)
                    }
                    FilledIconButton(
                        onClick = {}, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.5f), contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.MoreHoriz, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )

            // show text if no other users
            if (users.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No other users found.", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    // display chat per user
                    items(users) { user ->
                        val username = user["username"].toString()
                        val uid = user["uid"].toString()
                        
                        // Handle both  name and id for avatar storage
                        val avatarName = user["avatarName"] as? String
                        val userAvatarResId = if (avatarName != null) {
                            AvatarProvider.getResId(avatarName)
                        } else {
                            // Fallback for old data or missing names
                            val legacyId = (user["avatarId"] as? Long)?.toInt() ?: 0
                            if (legacyId != 0 && legacyId != -1) {
                                // default if not found
                                R.drawable.wyatt
                            } else {
                                R.drawable.wyatt
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = userAvatarResId),
                                contentDescription = "Profile",
                                modifier = Modifier.size(50.dp).clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(username, color = Color.Black, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.weight(1f))
                            // send user to according chat
                            IconButton(onClick = {
                                val currentUid = FirebaseAuth.getInstance().currentUser?.uid ?: return@IconButton
                                val chatId = if (currentUid < uid) "$currentUid-$uid" else "$uid-$currentUid"
                                navController.navigate("chat/$chatId")
                            }) {
                                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Chat")
                            }
                        }
                    }
                }
            }
        }
    }
}
