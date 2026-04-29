package com.example.snapchatclone.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.snapchatclone.screens.camera.CameraScreen
import com.example.snapchatclone.screens.map.MapScreen
import com.example.snapchatclone.screens.messages.MessagesScreen
import com.example.snapchatclone.screens.stories.StoriesScreen
import com.example.snapchatclone.screens.spotlight.SpotlightScreen

@PreviewScreenSizes
@Composable
fun NavBar() {
    val navController = rememberNavController()
    // current route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationSuiteScaffold(
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = Color.Black,
            navigationBarContentColor = Color.Gray
        ),
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    // icon for each item in navbar
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.label
                        )
                    },
                    // selected tab based on current route
                    selected = currentDestination == destination.route,
                    // navigate when tab is clicked
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            // controls which screen is displayed
            NavHost(
                navController = navController,
                startDestination = AppDestinations.CAMERA.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // each route takes you to different screen
                composable(AppDestinations.MAP.route) { MapScreen() }
                composable(AppDestinations.MESSAGES.route) { MessagesScreen() }
                composable(AppDestinations.CAMERA.route) { CameraScreen() }
                composable(AppDestinations.STORIES.route) { StoriesScreen() }
                composable(AppDestinations.SPOTLIGHT.route) { SpotlightScreen() }
            }
        }
    }
}