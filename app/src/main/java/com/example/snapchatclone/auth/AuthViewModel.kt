package com.example.snapchatclone.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()

    // login
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        repo.login(email, password) { success, msg ->
            onResult(success, msg)
        }
    }
    // sign up
    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        repo.signUp(email, password) { success, msg ->
            onResult(success, msg)
        }
    }
    // fetch user avatar from firestore
    fun fetchUserAvatar(uid: String, onResult: (String?) -> Unit) {
        Firebase.firestore.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val avatarName = document.getString("avatarName")
                onResult(avatarName)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    // save user to firestore
    fun saveUser(username: String, avatarName: String, onResult: (Boolean) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid ?: return
        val email = currentUser.email

        val user = mapOf(
            "uid" to uid,
            "email" to email,
            "username" to username,
            "avatarName" to avatarName
        )

        Firebase.firestore
            .collection("users")
            .document(uid)
            .set(user)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }
}
