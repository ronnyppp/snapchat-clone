package com.example.snapchatclone.features.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(
    avatarViewModel: AvatarViewModel,
    navController: NavController,
    userId: String,
    onLogout: () -> Unit
) {
    // observe avatar id from view model
    val avatarId by avatarViewModel.avatarState.collectAsState()

    var username by remember { mutableStateOf("Loading...") }
    // fetch username from firestore
    LaunchedEffect(userId) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                username = it.getString("username") ?: "Unknown"
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            leftContent = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.DarkGray,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Avatar
        Box(
            modifier = Modifier
                .size(110.dp)
                .background(Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = avatarId),
                contentDescription = "Profile",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Username
        Text(
            text = username,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Simple stats row static data
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("12", fontWeight = FontWeight.Bold)
                Text("Snaps")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("5", fontWeight = FontWeight.Bold)
                Text("Friends")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("3", fontWeight = FontWeight.Bold)
                Text("Stories")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout button
        Button(
            // execute on logout when button is clicked
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Log Out", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}