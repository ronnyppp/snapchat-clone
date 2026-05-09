package com.example.snapchatclone.features.messages.ui

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.snapchatclone.components.TopBar
import com.example.snapchatclone.features.messages.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ChatScreen(chatId: String,
               navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    // curr user id
    val currentUser =
        FirebaseAuth.getInstance().currentUser?.uid ?: return
    // text state
    var messageText by remember {
        mutableStateOf("")
    }
    // messages list
    var messages by remember {
        mutableStateOf(listOf<Message>())
    }
    // get username of user being messaged
    var username by remember { mutableStateOf("Loading...") }

    LaunchedEffect(chatId) {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect

        val chatRef = db.collection("chats").document(chatId)

        // Create chat document if it doesn't exist
        chatRef.get()
            .addOnSuccessListener { chatDoc ->

                if (!chatDoc.exists()) {

                    // Extract users from chatId
                    val members = chatId.split("-")

                    val chatData = hashMapOf(
                        "members" to members
                    )
                    // make chat doc
                    chatRef.set(chatData)
                        .addOnSuccessListener {
                            Log.d("CHAT", "Chat document created")
                        }
                        .addOnFailureListener {
                            Log.e("CHAT", "Failed creating chat document", it)
                        }

                    return@addOnSuccessListener
                }

                // Existing chat
                val members = chatDoc.get("members") as? List<String>

                val otherUserId = members?.find { it != currentUid }

                if (otherUserId == null) {
                    username = "Chat"
                    return@addOnSuccessListener
                }
                // get profile of other user
                db.collection("users")
                    .document(otherUserId)
                    .get()
                    .addOnSuccessListener { userDoc ->

                        Log.d("CHAT", "userDoc = ${userDoc.data}")
                        username = userDoc.getString("username") ?: "Unknown User"
                    }
                    .addOnFailureListener {
                        Log.e("CHAT", "Failed loading user", it)
                        username = "Unknown User"
                    }
            }
            .addOnFailureListener {
                Log.e("CHAT", "Failed loading chat", it)
                username = "Error"
            }

        // Listen for messages
        chatRef.collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    Log.e("CHAT", "Messages listener error", error)
                    return@addSnapshotListener
                }

                messages = value?.documents?.mapNotNull {
                    it.toObject(Message::class.java)
                } ?: emptyList()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(
            // user can go back
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
            },
            centerContent = {
                Text(
                    text = username,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            rightContent = {
                FilledIconButton(
                    onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.5f), contentColor = Color.White
                    ), modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        tint = Color.White
                    )
                }
                FilledIconButton(
                    onClick = {/**/ }, colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.5f), contentColor = Color.White
                    ), modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Default.Videocam,
                        contentDescription = "Video Call",
                        tint = Color.White
                    )
                }
            }
        )

        // Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false
        ) {
            items(messages) { message ->
                val isMine = message.senderId == currentUser
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {

                    // Vertical line
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(48.dp)
                            .background(
                                color = if (isMine) Color.Cyan else Color.Red,
                                shape = RoundedCornerShape(10.dp)
                            )
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        // show title based on user
                        Text(
                            text = if (isMine) "You" else username,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if(isMine) Color(0xFFB3E5FC) else Color.Red
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = message.text,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // Input area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // user text field
            TextField(
                value = messageText,
                onValueChange = {
                    messageText = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Send a chat")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(30.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            // send button
            IconButton(
                onClick = {
                    if (messageText.isBlank()) return@IconButton

                    // save message with message model
                    val message = Message(
                        text = messageText,
                        senderId = currentUser,
                        timestamp = System.currentTimeMillis()
                    )
                    // add user message to db
                    db.collection("chats")
                        .document(chatId)
                        .collection("messages")
                        .add(message)
                    // empty user text
                    messageText = ""
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color.DarkGray.copy(alpha = 0.5f),
                )
            }
        }
    }
}
