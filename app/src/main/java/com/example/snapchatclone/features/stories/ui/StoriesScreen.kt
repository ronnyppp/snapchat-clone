package com.example.snapchatclone.features.stories.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.components.TopBar
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.snapchatclone.features.stories.Story

@Composable
fun StoriesScreen(
    stories: List<Story>,
    onStoryClick: (Story) -> Unit,
    navController: NavController,
    avatarViewModel: AvatarViewModel
) {
    // curr user avatar state
    val avatarId by avatarViewModel.avatarState.collectAsState()
    // get curr user id
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(16.dp)
        ) {
            TopBar(
                leftContent = {
                    FilledIconButton(
                        onClick = {
                            navController.navigate("profile/$userId")
                        }, colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.LightGray, contentColor = Color.White
                        ), modifier = Modifier.size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(avatarId),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
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
                    Text(
                        "Stories", fontSize = 25.sp,
                        color = Color.Black, fontWeight = FontWeight.Bold
                    )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Friends",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                // row for stories
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // render each story bubble
                    items(stories) { story ->
                        StoryItem(
                            story = story,
                            onClick = {
                                // encode url safely for navigation
                                val encodedUrl = Uri.encode(story.imageUrl)
                                navController.navigate("story_viewer?imageUrl=$encodedUrl")
                            }
                        )
                    }
                }
                // SUBSCRIPTIONS
                Text(
                    "Subscription",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(
                        R.drawable.mountain,
                        R.drawable.owl,
                        R.drawable.lemur
                    ).forEach { img ->
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                        ) {
                            Image(
                                painter = painterResource(id = img),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                // DISCOVER
                Text(
                    "Discover",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(
                        R.drawable.snakes,
                        R.drawable.baboon
                    ).forEach { img ->
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .height(350.dp)
                        ) {
                            Image(
                                painter = painterResource(id = img),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoryItem(
    story: Story,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {

        // Story ring (Snapchat style)
        Box(
            modifier = Modifier
                .size(70.dp)
                .padding(3.dp)
        ) {
            Image(
                // downscale story preview img
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(story.imageUrl)
                        .size(300)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = story.username,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1
        )
    }
}