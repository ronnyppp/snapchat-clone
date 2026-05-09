package com.example.snapchatclone.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapchatclone.R
import com.example.snapchatclone.auth.AuthViewModel
import com.example.snapchatclone.auth.AvatarViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
    avatarViewModel: AvatarViewModel = viewModel(),
    navController: NavController
) {
    // input state
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // validation errors
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.snapchat_logo),
            contentDescription = "Snapchat Logo",
            modifier = Modifier
                .size(80.dp)
        )
        Text(
            text = "Snapchat",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(Modifier.height(32.dp))
        // email input
        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = "" // clear error while typing
            },
            isError = emailError.isNotEmpty(),
            placeholder = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,

                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,


                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                cursorColor = Color.Black
            )
        )
        // check for error then display
        if (emailError.isNotEmpty()) {
            Text(emailError, color = Color.Red, modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(Modifier.height(12.dp))

        // password input
        TextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = "" // clear error while typing
            },
            isError = passwordError.isNotEmpty(),
            placeholder = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,

                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                cursorColor = Color.Black
            )
        )
        // check for error then display
        if (passwordError.isNotEmpty()) {
            Text(passwordError, color = Color.Red, modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(Modifier.height(16.dp))

        // login button
        Button(onClick = {
            // validation for logging in
            val isEmailValid =
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

            val isPasswordValid = password.length >= 6
            // create possible errors
            if (!isEmailValid) {
                emailError = "Enter a valid email (must include @)"
                return@Button
            }

            if (!isPasswordValid) {
                passwordError = "Password must be at least 6 characters"
                return@Button
            }

            // If valid → call Firebase login
            viewModel.login(email, password) { success, msg ->
                if (success) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    if (uid != null) {
                        viewModel.fetchUserAvatar(uid) { avatarName ->
                            if (avatarName != null) {
                                val resId = context.resources.getIdentifier(
                                    avatarName, "drawable", context.packageName
                                )
                                if (resId != 0) {
                                    avatarViewModel.updateAvatar(resId)
                                }
                            }
                            onLoginSuccess()
                        }
                    } else {
                        onLoginSuccess()
                    }
                } else {
                    generalError = msg ?: "Login failed"
                }
            }
        },
            modifier = Modifier.height(55.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow
        )) {
            Text("Log in", color = Color.Black)
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = {
            // validation for signing up
            val isEmailValid =
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

            val isPasswordValid = password.length >= 6
            // create possible errors
            if (!isEmailValid) {
                emailError = "Enter a valid email (must include @)"
                return@TextButton
            }

            if (!isPasswordValid) {
                passwordError = "Password must be at least 6 characters"
                return@TextButton
            }

            viewModel.signUp(email, password) { success, msg ->
                if (success) {
                    navController.navigate("profileSetup") {
                        popUpTo("login") { inclusive = false }
                    }
                } else {
                    generalError = msg ?: "Signup failed"
                }
            }

        }) {
            Text("New to Snapchat? Sign Up", color = Color.Black)
        }
        // general error display
        if (generalError.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(generalError, color = Color.Red)
        }
    }
}
