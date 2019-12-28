package com.andre.projjoe.activities;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;

import com.cameraProcess.CameraConstants;
import com.cameraProcess.CameraInterface;


public class BaseActivity extends AppCompatActivity implements CameraInterface, TextureView.SurfaceTextureListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                cameraPictureTakenSuccess();
                // successfully captured the image
                // display it in image view
         //       previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                cameraPictureCancelled();
                // user cancelled Image capture

            } else {
                cameraPictureFailed();
                // failed to capture image

            }
        } else if (requestCode == CameraConstants.CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                videoRecordTakenSuccess();
                // video successfully recorded
                // preview the recorded video

            } else if (resultCode == RESULT_CANCELED) {
                videoRecordCancelled();
                // user cancelled recording

            } else {
                videoRecordFailed();
                // failed to record video

            }
        }
    }

    @Override
    public void cameraPictureTakenSuccess() {

    }

    @Override
    public void cameraPictureCancelled() {

    }

    @Override
    public void cameraPictureFailed() {

    }

    @Override
    public void videoRecordTakenSuccess() {

    }

    @Override
    public void videoRecordCancelled() {

    }

    @Override
    public void videoRecordFailed() {

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
