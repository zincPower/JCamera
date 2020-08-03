package com.zinc.jcamera.camera1.helper

import android.content.Context
import android.hardware.Camera
import android.util.Log
import com.zinc.jcamera.camera1.utils.getCameraDisplayOrientation

class CameraController(private val camera: Camera?) {

    fun initParam(context: Context, cameraId: Int) {
        camera?.setDisplayOrientation(getCameraDisplayOrientation(context, cameraId))

        camera?.setPreviewCallback { data, camera ->
            val previewSize = camera.parameters.previewSize ?: return@setPreviewCallback
            Log.i("cameraController", "size: [${previewSize.width} * ${previewSize.height}]")
        }

    }

}