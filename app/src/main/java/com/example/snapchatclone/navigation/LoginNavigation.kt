package com.example.snapchatclone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.features.LoginScreen
import com.example.snapchatclone.features.login.ui.ProfileSetupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginNavigation() {
    // root navigation for auth
    val rootNavController = rememberNavController()
    // shared avatar state
    val avatarViewModel: AvatarViewModel = viewModel()
    // curr user state
    val user = FirebaseAuth.getInstance().currentUser

    // set start destination based on authentication state
    NavHost(
        navController = rootNavController,
        startDestination = "splash"
    ) {
        // splash screen to redirect logic
        composable("splash") {
            LaunchedEffect(Unit) {
                if (user == null) {
                    rootNavController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    rootNavController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }
        composable("login") {
            LoginScreen(
                navController = rootNavController,
                avatarViewModel = avatarViewModel,
                onLoginSuccess = {
                    rootNavController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("profileSetup") {
            ProfileSetupScreen(
                navController = rootNavController, 
                avatarViewModel = avatarViewModel
            )
        }
        // main app navigation graph
        composable("main") {
            MainNavGraph(
                avatarViewModel = avatarViewModel,
                rootNavController = rootNavController
            )
        }
    }
}
