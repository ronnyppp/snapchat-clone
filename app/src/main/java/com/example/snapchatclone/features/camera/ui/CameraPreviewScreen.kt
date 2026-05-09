package com.example.snapchatclone.features.camera.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.viewfinder.compose.MutableCoordinateTransformer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapchatclone.features.camera.CameraPreviewViewModel
import com.google.accompanist.permissions.*
import kotlinx.coroutines.delay
import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreviewScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: CameraPreviewViewModel = viewModel()
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    
    // Using rememberSaveable ensures the state persists across activity recreation or backgrounding
    var hasRequestedPermission by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier.background(Color.Black)) {
        // If permission is granted, show the camera preview
        if (cameraPermissionState.status.isGranted) {
            CameraPreviewContent(viewModel, Modifier.fillMaxSize())
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Camera Access Required",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                
                val status = cameraPermissionState.status
                val shouldShowRationale = status is PermissionStatus.Denied && status.shouldShowRationale
                
                val textToShow = if (shouldShowRationale) {
                    "Snapchat needs camera access so you can take photos and send them to friends!"
                } else if (hasRequestedPermission) {
                    "Camera access was denied. Please enable it in Settings to use the camera."
                } else {
                    "To get started, please allow camera access to take photos."
                }
                
                Text(
                    text = textToShow,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 300.dp)
                )
                
                Spacer(Modifier.height(24.dp))
                
                Button(
                    onClick = { 
                        if (shouldShowRationale || !hasRequestedPermission) {
                            cameraPermissionState.launchPermissionRequest()
                            hasRequestedPermission = true
                        } else {
                            // If we've already requested and rationale is false, the user likely checked "Don't ask again"
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow, contentColor = Color.Black),
                    shape = CircleShape,
                    modifier = Modifier.height(50.dp).widthIn(min = 200.dp)
                ) {
                    Text("Enable Camera", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CameraPreviewContent(
    viewModel: CameraPreviewViewModel,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val lensFacing by viewModel.lensFacing.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // bind/unbind camera when something changes
    LaunchedEffect(lifecycleOwner, lensFacing) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }
    // tracks tap-to-focus and forces recomposition per tap
    var autofocusRequest by remember { mutableStateOf(UUID.randomUUID() to Offset.Unspecified) }
    val autofocusRequestId = autofocusRequest.first
    val showAutofocusIndicator = autofocusRequest.second.isSpecified
    val autofocusCoords = remember(autofocusRequestId) { autofocusRequest.second }
    // hide auto focus circle after delay
    if (showAutofocusIndicator) {
        LaunchedEffect(autofocusRequestId) {
            delay(1000)
            autofocusRequest = autofocusRequestId to Offset.Unspecified
        }
    }

    Box(modifier = modifier) {
        surfaceRequest?.let { request ->
            val coordinateTransformer = remember { MutableCoordinateTransformer() }
            CameraXViewfinder(
                surfaceRequest = request,
                coordinateTransformer = coordinateTransformer,
                modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTapGestures { tapCoords ->
                        // update auto focus ui indicator and trigger camera focus
                        autofocusRequest = UUID.randomUUID() to tapCoords
                        with(coordinateTransformer) {
                            viewModel.tapToFocus(tapCoords.transform())
                        }
                    }
                }
            )
        } ?: run {
            // Loading state while CameraX initializes
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Yellow)
            }
        }
        // tap-to-focus visual indicator
        AnimatedVisibility(
            visible = showAutofocusIndicator,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .offset { autofocusCoords.takeOrElse { Offset.Zero }.round() }
                .offset((-24).dp, (-24).dp)
        ) {
            Spacer(Modifier.border(2.dp, Color.White, CircleShape).size(48.dp))
        }
    }
}
