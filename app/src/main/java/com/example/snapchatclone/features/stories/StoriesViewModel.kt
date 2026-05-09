package com.example.snapchatclone.features.stories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StoriesViewModel : ViewModel() {
    // holds active stories in ui state
    private val _storiesState = MutableStateFlow<List<Story>>(emptyList())
    val storiesState: StateFlow<List<Story>> = _storiesState

    // upload image to firebase storage and save data in firestore
    fun uploadStory(uri: Uri, onResult: (Boolean) -> Unit) {

        val user = FirebaseAuth.getInstance().currentUser ?: return

        val storageRef = Firebase.storage.reference
            .child("stories/${user.uid}/${System.currentTimeMillis()}.jpg")
        Log.d("UPLOAD_URI", uri.toString())
        Log.d("UPLOAD_SCHEME", uri.scheme ?: "null")
        storageRef.putFile(uri)
            .addOnSuccessListener {

                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // fetch user info for story metadata
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(user.uid)
                        .get()
                        .addOnSuccessListener { document ->

                            val username = document.getString("username") ?: ""
                            val avatarResId = (document.getLong("avatarResId") ?: 0L).toInt()
                            // story info
                            val story = mapOf(
                                "userId" to user.uid,
                                "username" to username,
                                "avatarResId" to avatarResId,
                                "imageUrl" to downloadUri.toString(),
                                "timestamp" to System.currentTimeMillis()
                            )
                            // save story to firestore
                            FirebaseFirestore.getInstance()
                                .collection("stories")
                                .add(story)
                                .addOnSuccessListener {
                                    onResult(true)
                                }
                                .addOnFailureListener {
                                    onResult(false)
                                }
                        }
                }
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
    // listen to story updates and filter expired stories
    fun observeStories() {
        FirebaseFirestore.getInstance()
            .collection("stories")
            .addSnapshotListener { snapshot, _ ->

                val cutoff = System.currentTimeMillis() - 24 * 60 * 60 * 1000

                val list = snapshot?.documents
                    ?.mapNotNull { it.toObject(Story::class.java) }
                    ?.filter { it.imageUrl.isNotBlank() && it.timestamp > cutoff }
                    ?: emptyList()
                // avoid unnecessary updates
                if (_storiesState.value != list) {
                    _storiesState.value = list
                }
            }
    }
}