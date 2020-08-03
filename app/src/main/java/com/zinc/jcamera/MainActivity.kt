package com.zinc.jcamera

import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.zinc.jcamera.controls.Face
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "JCamera"
    }

    private var face: Face = Face.BACK

    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        camera = Camera.open()
        camera?.let {
            it.setDisplayOrientation(90)
            showParam(it.parameters)
        }

        camera_preview?.holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(
                holder: SurfaceHolder?,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.i(TAG, "surfaceChanged: [$width, $height]")
//                camera?.parameters?.setPreviewSize(width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                camera?.release()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                camera?.setPreviewDisplay(holder)
                camera?.startPreview()
            }

        })

    }

    private fun showParam(parameters: Camera.Parameters) {
        // 预览的尺寸
        val supportedPreviewSizes = parameters.supportedPreviewSizes
        for (supportedPreviewSize in supportedPreviewSizes) {
            Log.i(
                TAG,
                "previewSize: [width: " + supportedPreviewSize.width + ", " +
                        "height: " + supportedPreviewSize.height + "]"
            )
        }

        // 图片的尺寸
        val supportedPictureSizes = parameters.supportedPictureSizes
        for (supportedPictureSize in supportedPictureSizes) {
            Log.i(
                TAG,
                "pictureSize: [width: " + supportedPictureSize.width + ", " +
                        "height: " + supportedPictureSize.height + "]"
            )
        }

        // 视频的尺寸
        val supportedVideoSizes = parameters.supportedVideoSizes
        for (supportedVideoSize in supportedVideoSizes) {
            Log.i(
                TAG,
                "videoSize: [width: " + supportedVideoSize.width + ", " +
                        "height: " + supportedVideoSize.height + "]"
            )
        }
    }
}