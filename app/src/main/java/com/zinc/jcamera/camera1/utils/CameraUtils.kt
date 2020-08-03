package com.zinc.jcamera.camera1.utils

import android.content.Context
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.view.Surface
import android.view.WindowManager

/**
 * 获取相机实例
 *
 * @param cameraId 相机id，不传开启后置
 */
fun getCameraInstance(cameraId: Int = -1): Camera? {
    var camera: Camera? = null
    try {
        // 在多个摄像头时，默认打开后置摄像头
        // 可选值为后置（CAMERA_FACING_BACK）/前置（ CAMERA_FACING_FRONT）
        camera = if (cameraId == -1) {
            Camera.open()
        } else {
            Camera.open(cameraId)
        }
    } catch (e: Exception) {
        // Camera被占用或者设备上没有相机时会崩溃。
        e.printStackTrace()
    }
    return camera
}

fun getCameraDisplayOrientation(context: Context, cameraId: Int): Int {
    val info = CameraInfo()
    Camera.getCameraInfo(cameraId, info)
    val rotation =
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
    var degrees = 0
    when (rotation) {
        Surface.ROTATION_0 -> degrees = 0
        Surface.ROTATION_90 -> degrees = 90
        Surface.ROTATION_180 -> degrees = 180
        Surface.ROTATION_270 -> degrees = 270
    }
    var result: Int
    //前置
    if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
        result = (info.orientation + degrees) % 360
        result = (360 - result) % 360
    } else {
        result = (info.orientation - degrees + 360) % 360
    }
    return result
}