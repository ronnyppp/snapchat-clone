package com.example.snapchatclone.screens.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapchatclone.components.TopBar

@Composable
fun MapScreen() {
    // Bottom-most layer
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Gray),
    ) {
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