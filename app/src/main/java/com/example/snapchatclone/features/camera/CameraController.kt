package com.example.snapchatclone.features.camera

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.lifecycle.LifecycleOwner

class CameraController(
    val context: Context,
    val lifecycleOwner: LifecycleOwner
) {
    lateinit var imageCapture: ImageCapture

}