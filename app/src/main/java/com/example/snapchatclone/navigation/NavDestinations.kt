package com.example.snapchatclone.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    MAP("map","Map", Icons.Default.LocationOn),
    MESSAGES("messages","Messages", Icons.AutoMirrored.Filled.Message),
    CAMERA("camera","Camera", Icons.Default.PhotoCamera),
    STORIES("stories","Stories", Icons.Default.PeopleAlt),
    SPOTLIGHT("Spotlight","Spotlight", Icons.Default.PlayArrow),
}