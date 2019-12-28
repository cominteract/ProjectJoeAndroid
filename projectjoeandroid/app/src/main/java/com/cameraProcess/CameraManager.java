package com.cameraProcess;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import com.andre.projjoe.activities.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andreinsigne on 22/11/2017.
 */

public class CameraManager {



    private Uri fileUri; // file url to store image/video
    BaseActivity baseActivity;
    private ImageView imgPreview;
    private VideoView videoPreview;
    private static String imageDirectory;


    private TextureView textureView;
    private Camera camera;
    private TextureView.SurfaceTextureListener surfaceTextureListener;


    public void initManager(TextureView textureView,
                            TextureView.SurfaceTextureListener surfaceTextureListener,
                            BaseActivity baseActivity)
    {

        this.textureView = textureView;
        this.surfaceTextureListener = surfaceTextureListener;
        this.textureView.setSurfaceTextureListener(surfaceTextureListener);
        this.baseActivity = baseActivity;

    }

    public void initManager(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        camera = Camera.open();
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if(textureView!=null)
            textureView.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        else
            Log.d(" On Sic Null ", " On Sic Null ");

        try {
            camera.setPreviewTexture(surface);
        } catch (IOException t) {
        }

        camera.startPreview();
        textureView.setAlpha(1.0f);
        textureView.setRotation(90.0f);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        camera.stopPreview();
        camera.release();
        return true;
    }



    /** hardware **/

    public void initManager(ImageView imgPreview, VideoView videoPreview,
                            BaseActivity baseActivity)
    {
        this.imgPreview = imgPreview;
        this.videoPreview = videoPreview;
        this.baseActivity = baseActivity;

    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public void setImageDirectory(String imageDirectory) {
        CameraManager.imageDirectory = imageDirectory;
    }

    public boolean isCameraSupported() {
        if (baseActivity.getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(CameraConstants.MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        baseActivity.startActivityForResult(intent, CameraConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    public void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(CameraConstants.MEDIA_TYPE_VIDEO);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        // name

        // start the video capture Intent
        baseActivity.startActivityForResult(intent, CameraConstants.CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    public void previewCapturedImage() {
        try {
            // hide video preview
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getCapturedImage() {
        try {
            // hide video preview
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            return bitmap;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void previewVideo() {
        try {
            // hide image preview

            videoPreview.setVideoPath(fileUri.getPath());
            // start playing
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
        if(imageDirectory==null)
            imageDirectory = "aibits_directory";
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                imageDirectory);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(imageDirectory, "Oops! Failed create "
                        + imageDirectory + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == CameraConstants.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == CameraConstants.MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
