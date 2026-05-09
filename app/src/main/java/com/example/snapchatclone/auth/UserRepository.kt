package com.example.snapchatclone.auth

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class UserRepository {
    // fetch users from firestore
    fun getUsers(onResult: (List<Map<String, Any>>) -> Unit) {
        Firebase.firestore.collection("users")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("UserRepository", "Error fetching users: ${error.message}")
                    return@addSnapshotListener
                }

                val currentUid = Firebase.auth.currentUser?.uid
                val documents = snapshot?.documents ?: emptyList()
                
                Log.d("UserRepository", "Fetched ${documents.size} total documents from Firestore")

                val users = documents
                    .mapNotNull { it.data }
                    .filter { it["uid"] != currentUid }

                Log.d("UserRepository", "Found ${users.size} other users (filtered out current user: $currentUid)")

                onResult(users)
            }
    }
}
