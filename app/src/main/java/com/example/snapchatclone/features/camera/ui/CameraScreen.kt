package com.example.snapchatclone.features.camera.ui

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapchatclone.auth.AvatarViewModel
import com.example.snapchatclone.features.camera.CameraPreviewViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraPreviewViewModel = viewModel(),
    avatarViewModel: AvatarViewModel = viewModel(
        navController.getBackStackEntry("main_graph")
    )
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    Box {
        // Always show the preview screen
        CameraPreviewScreen(viewModel = viewModel)

        // Only show the Snap UI controls if permission is granted
        if (cameraPermissionState.status.isGranted) {
            CameraUI(
                onFlipCamera = {
                    viewModel.switchCamera()
                },
                onFlashToggle = {
                    viewModel.toggleFlash()
                },
                onCapture = {
                    viewModel.takePhoto(context) { uri ->
                        navController.navigate("preview/${Uri.encode(uri.toString())}")
                    }
                },
                navController = navController,
                avatarViewModel = avatarViewModel,
                viewModel = viewModel
            )
        }
    }
}
