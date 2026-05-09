package com.example.snapchatclone.features.camera

import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class CameraPreviewViewModel : ViewModel() {
    // camerax surface for compose preview
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val _lensFacing = MutableStateFlow(DEFAULT_BACK_CAMERA)
    val lensFacing: StateFlow<CameraSelector> = _lensFacing

    // tap to focus vars
    private var surfaceMeteringPointFactory: SurfaceOrientedMeteringPointFactory? = null
    private var cameraControl: CameraControl? = null

    private var imageCaptureUseCase = ImageCapture.Builder().build()

    private var flashEnabled = false

    // toggle flash mode
    fun toggleFlash() {
        flashEnabled = !flashEnabled

        imageCaptureUseCase.flashMode =
            if (flashEnabled)
                ImageCapture.FLASH_MODE_ON
            else
                ImageCapture.FLASH_MODE_OFF
    }
    // preview case setup
    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
            // tap to focus mapping
            surfaceMeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                newSurfaceRequest.resolution.width.toFloat(),
                newSurfaceRequest.resolution.height.toFloat()
            )
        }
    }
    // bind camera to lifecycle
    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        // rebuild camera use to apply flash
        imageCaptureUseCase = ImageCapture.Builder()
            .setFlashMode(
                if (flashEnabled)
                    ImageCapture.FLASH_MODE_ON
                else
                    ImageCapture.FLASH_MODE_OFF
            )
            .build()

        // unbind old camera
        processCameraProvider.unbindAll()

        val camera = processCameraProvider.bindToLifecycle(
            lifecycleOwner,
            _lensFacing.value,
            cameraPreviewUseCase,
            imageCaptureUseCase
        )

        cameraControl = camera.cameraControl

        // keep coroutines alive till we're done
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }
    // switch front and back camera
    fun switchCamera() {
        _lensFacing.update { current ->
            if (current == DEFAULT_BACK_CAMERA)
                DEFAULT_FRONT_CAMERA
            else
                DEFAULT_BACK_CAMERA
        }
    }
    // hande tap-to-focus
    fun tapToFocus(tapCoords: Offset) {
        val point = surfaceMeteringPointFactory?.createPoint(tapCoords.x, tapCoords.y)
        if (point != null) {
            val meteringAction = FocusMeteringAction.Builder(point).build()
            cameraControl?.startFocusAndMetering(meteringAction)
        }
    }
    // capture image and return uri file
    fun takePhoto(context: Context,
                  onImageCaptured: (Uri) -> Unit,) {
        val file = File(
            context.filesDir,
            "photo_${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCaptureUseCase.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(result: ImageCapture.OutputFileResults) {
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        file
                    )
                    onImageCaptured(uri)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            }
        )
    }
}
