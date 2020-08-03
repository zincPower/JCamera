package com.zinc.jcamera.camera1.helper

import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.util.Log
import android.view.SurfaceView
import java.io.File

class CameraRecord {

    // setVideoEncodingBitRate()
    // setVideoSize()
    // setVideoFrameRate()
    // setAudioEncodingBitRate()
    // setAudioChannels()
    // setAudioSamplingRate()

    private val mediaRecorder: MediaRecorder = MediaRecorder()
    private var file: File? = null

    fun prepare(camera: Camera, cameraId: Int = -1, file: File, surfaceView: SurfaceView) {
        camera.unlock()
        mediaRecorder.setCamera(camera)

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        if (cameraId == -1) {
//            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_1080P))
        } else {
            mediaRecorder.setProfile(CamcorderProfile.get(cameraId, 1))
        }
        mediaRecorder.setOutputFile(file.absolutePath)
        mediaRecorder.setPreviewDisplay(surfaceView.holder.surface)

        mediaRecorder.prepare()
        this.file = file
    }

    fun start() {
        mediaRecorder.start()
    }

    fun stop() {
        mediaRecorder.stop()
        Log.i("cameraRecord", "file:${file?.absolutePath}")
    }

    fun reset() {
        mediaRecorder.reset()
    }

}