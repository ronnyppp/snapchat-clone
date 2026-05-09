package com.example.snapchatclone.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.features.camera.CameraPreviewViewModel
import com.example.snapchatclone.features.camera.ui.CameraScreen
import com.example.snapchatclone.features.login.ui.ProfileScreen
import com.example.snapchatclone.features.map.ui.MapScreen
import com.example.snapchatclone.features.messages.ui.ChatScreen
import com.example.snapchatclone.features.messages.ui.ChatsScreen
import com.example.snapchatclone.features.stories.ui.StoriesScreen
import com.example.snapchatclone.features.spotlight.ui.SpotlightScreen
import com.example.snapchatclone.features.stories.StoriesViewModel
import com.example.snapchatclone.features.stories.ui.PreviewStoryScreen
import com.example.snapchatclone.features.stories.ui.StoryViewerScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainNavGraph(
    avatarViewModel: AvatarViewModel = viewModel(),
    cameraPreviewModel: CameraPreviewViewModel = viewModel(),
    storiesViewModel: StoriesViewModel = viewModel(),
    rootNavController: NavHostController
) {
    // internal nav controller for app tabs
    val navController = rememberNavController()
    // track curr rout for navigation
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationSuiteScaffold(
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = Color.Black,
            navigationBarContentColor = Color.Gray
        ),
        navigationSuiteItems = {
            // nav bar destinations
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.label
                        )
                    },
                    selected = currentDestination == destination.route,
                    onClick = {
                        // avoid duplicate destinations in back stack
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
            NavHost(
                navController = navController,
                startDestination = AppDestinations.CAMERA.route,
                route = "main_graph",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(AppDestinations.MAP.route) { MapScreen(navController, avatarViewModel) }
                composable(AppDestinations.MESSAGES.route) { ChatsScreen(navController, avatarViewModel) }
                composable(AppDestinations.CAMERA.route) { CameraScreen(
                    navController, cameraPreviewModel, avatarViewModel) }
                composable(AppDestinations.STORIES.route) {
                    // observe stories when going to stories screen
                    LaunchedEffect(Unit) {
                        storiesViewModel.observeStories()
                    }
                    // story state
                    val stories by storiesViewModel.storiesState.collectAsState()

                    StoriesScreen(
                        stories = stories,
                        navController = navController,
                        avatarViewModel = avatarViewModel,
                        onStoryClick = { story ->
                            // Encode the image URL before navigating
                            val encodedUrl = Uri.encode(story.imageUrl)
                            // navigate to story viewer screen with image
                            navController.navigate("story_viewer?imageUrl=$encodedUrl")
                        }
                    )
                }
                composable(AppDestinations.SPOTLIGHT.route) { SpotlightScreen(navController, avatarViewModel) }

                composable("chat/{chatId}") { backStackEntry ->
                    // pass chat id to chat screen
                    val chatId = backStackEntry.arguments?.getString("chatId")!!
                    ChatScreen(chatId, navController)
                }
                composable("profile/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")!!
                    ProfileScreen(avatarViewModel, navController, userId, onLogout = {
                        // clear all user settings from DataStore
                        avatarViewModel.clearAllData()
                        FirebaseAuth.getInstance().signOut()
                        // return to auth graph/login
                        rootNavController.navigate("login") {
                            popUpTo(0) // clears backstack
                        }
                    })
                }
                // story preview
                composable("preview/{imageUri}") { backStackEntry ->
                    val uri = backStackEntry.arguments?.getString("imageUri") ?: return@composable
                    // pass image uri of image taken to preview story
                    PreviewStoryScreen(
                        imageUri = uri,
                        onSend = {
                            // upload story to firebase
                            storiesViewModel.uploadStory(Uri.parse(uri)) {
                                success ->
                                // go back to camera screen if successful
                                if(success) {
                                    navController.popBackStack()
                                }
                            }
                        },
                        // pop back to camera screen if cancel
                        onCancel = {
                            navController.popBackStack()
                        }
                    )
                }
                // story viewer
                composable(
                    route = "story_viewer?imageUrl={imageUrl}",
                    arguments = listOf(navArgument("imageUrl") {
                        type = NavType.StringType
                        defaultValue = ""
                    })
                ) { backStackEntry ->
                    // pass image url to story viewer screen
                    val imageUrl = backStackEntry.arguments?.getString("imageUrl")

                    StoryViewerScreen(
                        imageUrl = imageUrl,
                        onClose = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
