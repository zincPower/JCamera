package com.zinc.jcamera.camera1

import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class CameraPreview(var camera: Camera?, surfaceView: SurfaceView) : SurfaceHolder.Callback {

    private val mHolder: SurfaceHolder = surfaceView.holder

    init {
        mHolder.addCallback(this)
    }

    // 如果预览视图可变或者旋转，要在这里处理好这些事件
    // 在重置大小或格式化时，确保停止预览
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        mHolder.surface ?: return

        // 变更之前要停止预览
        try {
            camera?.stopPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 在这里重置预览视图的大小、旋转、格式化

        // 使用新设置启动预览视图
        try {
            camera?.setPreviewDisplay(mHolder)
            camera?.startPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.i("CameraPreview", "surfaceDestroyed: ")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        try {
            // 连接
            camera?.setPreviewDisplay(holder)
            camera?.startPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}