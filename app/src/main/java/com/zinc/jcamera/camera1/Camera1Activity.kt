package com.zinc.jcamera.camera1

import android.hardware.Camera
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zinc.jcamera.R
import com.zinc.jcamera.camera1.helper.CameraController
import com.zinc.jcamera.camera1.helper.CameraRecord
import com.zinc.jcamera.camera1.utils.getCameraInstance
import kotlinx.android.synthetic.main.activity_camera1.*
import java.io.File


class Camera1Activity : AppCompatActivity() {

    private var camera: Camera? = null

    private var isStart: Boolean = false

    private var cameraRecord: CameraRecord = CameraRecord()
    private var cameraController: CameraController? = null

    private var cameraId: Int = Camera.CameraInfo.CAMERA_FACING_BACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera1)

        camera = getCameraInstance(cameraId)
        cameraController = CameraController(camera)
        cameraController?.initParam(this, cameraId)

        CameraPreview(camera, camera_preview)

        control.setOnClickListener {
            isStart = if (isStart) {
                cameraRecord.stop()
                camera?.lock()
                false
            } else {
                cameraRecord.prepare(
                    camera = camera ?: return@setOnClickListener,
                    cameraId = cameraId,
                    surfaceView = camera_preview,
                    file = File(cacheDir, "video_${System.currentTimeMillis()}.mp4")
                )
                cameraRecord.start()
                true
            }
        }

    }

    override fun onResume() {
        super.onResume()
        camera?.startPreview()
    }

    override fun onPause() {
        super.onPause()
        camera?.release()
    }

}