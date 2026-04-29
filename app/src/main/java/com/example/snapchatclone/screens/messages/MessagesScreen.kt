package com.example.snapchatclone.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapchatclone.components.TopBar

@Composable
fun MessagesScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
        ) {
            TopBar(
                leftContent = {
                    FilledIconButton(
                        onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.LightGray, contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                    FilledIconButton(
                        onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.LightGray, contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                centerContent = {
                    Text("Chat", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                },
                rightContent = {
                    FilledIconButton(
                        onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.LightGray, contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                    FilledIconButton(
                        onClick = {}, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.LightGray, contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.MoreHoriz,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                }
            )
            LazyColumn {
                item {
                    Text("Users")
                }
            }
        }
        }
}


