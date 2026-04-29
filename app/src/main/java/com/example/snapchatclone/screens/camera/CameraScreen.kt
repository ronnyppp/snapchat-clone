package com.example.snapchatclone.screens.camera

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.snapchatclone.components.TopBar

@Composable
fun CameraScreen() {
    // Bottom-most layer
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {
        // Top Buttons
        TopBar(
            leftContent = {
                FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.DarkGray, contentColor = Color.White
                ), modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                }
                FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.DarkGray, contentColor = Color.White
                ), modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
            },
        )
        // Right-side buttons
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)) {
            FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.DarkGray, contentColor = Color.White
            ), modifier = Modifier.size(40.dp)) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Add", tint = Color.White)
            }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.DarkGray, contentColor = Color.White
            ), modifier = Modifier.size(40.dp)) {
                Icon(Icons.Default.Cameraswitch, contentDescription = "Flip", tint = Color.White)
            }
            FilledIconButton(onClick = {/**/}, colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.DarkGray, contentColor = Color.White
            ), modifier = Modifier.size(40.dp)) {
                Icon(Icons.Default.FlashOn, contentDescription = "Flash", tint = Color.White)
            }
        }}
        // Bottom-Center Buttons
        Box(
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                // Gallery preview
                IconButton(onClick = {/**/}) {
                    Icon(Icons.Default.Image, contentDescription = "Gallery", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Capture
                OutlinedButton(onClick = {},
                    border = BorderStroke(width = 4.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp)) {
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Filters
                IconButton(onClick = {/**/}) {
                    Icon(Icons.Default.TagFaces, contentDescription = "Filters", tint = Color.White)
                }
            }
        }

    }
}