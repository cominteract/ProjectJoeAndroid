package com.andre.projjoe.imageManipulation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.cameraProcess.CameraConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andreinsigne on 22/11/2017.
 */

public class ImageManager {
    ImageView imageView;
    ImageInterface imageInterface;
    ImageFilterResources imageFilterResources;
    private boolean isProcessing = false;
    private int count = 0;

    public enum PhotoFilter
    {
        kPhotoContrast,
        kPhotoBrightness,
        kPhotoSaturation,
        kPhotoBoost,
        kPhotoBlack,
        kPhotoEmboss,
        kPhotoEngrave,
        kPhotoColorFilter,
        kPhotoFleaEffect,
        kPhotoDecreaseColorDepth,
        kPhotoGamma,
        kPhotoGaussianBlur,
        kPhotoGreyscale,
        kPhotoHighlightEffect,
        kPhotoHueFilterEffect,
        kPhotoInvertEffect,
        kPhotoMeanRemovalEffect,
        kPhotoRoundCornerEffect,
        kPhotoShading,
        kPhotoSepiaToning,
        kPhotoSharpen,
        kPhotoSmooth,
        kPhotoTint,
        kPhotoGama,
        kPhotoDark,
        kPhotoBright,
        kPhotoGreen,
        kPhotoBlue,
        kPhotoRed,
        kPhotoGrey
    }

    public Bitmap getBitmap()
    {
        return imageFilterResources.getOperation();
    }

    public void useFilter(PhotoFilter photoFilter,int[]params)
    {
        isProcessing = true;
        if(photoFilter.equals(PhotoFilter.kPhotoContrast)
                || photoFilter.equals(PhotoFilter.kPhotoBrightness)
                || photoFilter.equals(PhotoFilter.kPhotoHueFilterEffect)
                || photoFilter.equals(PhotoFilter.kPhotoDecreaseColorDepth)
                || photoFilter.equals(PhotoFilter.kPhotoSaturation)
                || photoFilter.equals(PhotoFilter.kPhotoRoundCornerEffect)
                || photoFilter.equals(PhotoFilter.kPhotoShading)
                || photoFilter.equals(PhotoFilter.kPhotoSharpen)
                || photoFilter.equals(PhotoFilter.kPhotoSmooth)
                || photoFilter.equals(PhotoFilter.kPhotoTint))
            singleParamFilter(params[0],photoFilter);
        else if(photoFilter.equals(PhotoFilter.kPhotoBoost)
                || photoFilter.equals(PhotoFilter.kPhotoColorFilter)
                || photoFilter.equals(PhotoFilter.kPhotoSepiaToning)
                || photoFilter.equals(PhotoFilter.kPhotoGamma))
            multiParamFilter(params,photoFilter);
        else
            emptyParamFilter(photoFilter);
        isProcessing = false;
    }
    private void multiParamFilter(int[] params, PhotoFilter photoFilter)
    {
        if(photoFilter.equals(PhotoFilter.kPhotoBoost)) {
            Log.d(" Photo boost ", params[1] * 25 + "");
            imageFilterResources.applyBoostEffect(2, params[1] * 25);
        }
        else if(photoFilter.equals(PhotoFilter.kPhotoColorFilter))
            imageFilterResources.applyColorFilterEffect(params[0],params[1],params[2]);
        else if(photoFilter.equals(PhotoFilter.kPhotoSepiaToning))
            imageFilterResources.applySepiaToningEffect(5,.6,.4,.2);
        else if(photoFilter.equals(PhotoFilter.kPhotoGamma))
            imageFilterResources.applyGammaEffect(.5,.3,.4);
    }

    private void emptyParamFilter(PhotoFilter photoFilter)
    {
        if(photoFilter.equals(PhotoFilter.kPhotoBlack))
            imageFilterResources.applyBlackFilter();
        if(photoFilter.equals(PhotoFilter.kPhotoBlue))
            imageFilterResources.blue();
        if(photoFilter.equals(PhotoFilter.kPhotoBright))
            imageFilterResources.bright();
        if(photoFilter.equals(PhotoFilter.kPhotoDark))
            imageFilterResources.dark();
        if(photoFilter.equals(PhotoFilter.kPhotoGreen))
            imageFilterResources.green();
        if(photoFilter.equals(PhotoFilter.kPhotoGrey))
            imageFilterResources.green();
        if(photoFilter.equals(PhotoFilter.kPhotoRed))
            imageFilterResources.red();
        if(photoFilter.equals(PhotoFilter.kPhotoGreyscale))
            imageFilterResources.applyGreyscaleEffect();
        if(photoFilter.equals(PhotoFilter.kPhotoEmboss))
            imageFilterResources.applyEmbossEffect();
        if(photoFilter.equals(PhotoFilter.kPhotoEngrave))
            imageFilterResources.applyEngraveEffect();
        if(photoFilter.equals(PhotoFilter.kPhotoFleaEffect))
            imageFilterResources.applyFleaEffect();

        if(photoFilter.equals(PhotoFilter.kPhotoGaussianBlur))
            imageFilterResources.applyGaussianBlurEffect();
        if(photoFilter.equals(PhotoFilter.kPhotoHighlightEffect))
            imageFilterResources.applyHighlightEffect();
        if(photoFilter.equals(PhotoFilter.kPhotoMeanRemovalEffect))
            imageFilterResources.applyMeanRemovalEffect();
        if(photoFilter.equals(PhotoFilter.kPhotoInvertEffect))
            imageFilterResources.applyInvertEffect();

    }

    private void singleParamFilter(int param, PhotoFilter photoFilter)
    {
        if(photoFilter.equals(PhotoFilter.kPhotoContrast))
            imageFilterResources.applyContrastEffect(param);
        else if(photoFilter.equals(PhotoFilter.kPhotoBrightness))
            imageFilterResources.applyBrightnessEffect(param);
        else if(photoFilter.equals(PhotoFilter.kPhotoHueFilterEffect))
            imageFilterResources.applyHueFilter(3);
        else if(photoFilter.equals(PhotoFilter.kPhotoDecreaseColorDepth))
            imageFilterResources.applyDecreaseColorDepthEffect(param);
        else if(photoFilter.equals(PhotoFilter.kPhotoSaturation))
            imageFilterResources.applySaturationFilter(4);
        else if(photoFilter.equals(PhotoFilter.kPhotoRoundCornerEffect))
            imageFilterResources.applyRoundCornerEffect(4);
        else if(photoFilter.equals(PhotoFilter.kPhotoShading))
            imageFilterResources.applyShadingFilter(4);
        else if(photoFilter.equals(PhotoFilter.kPhotoSharpen))
            imageFilterResources.applySharpenEffect(5);
        else if(photoFilter.equals(PhotoFilter.kPhotoSmooth))
            imageFilterResources.applySmoothEffect(10);
        else if(photoFilter.equals(PhotoFilter.kPhotoTint))
            imageFilterResources.applyTintEffect(2);

        else imageFilterResources.applyReflection();
    }

    public static Bitmap drawableToBitmap (Drawable drawable, int resizedWidth,int resizedHeight) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if(resizedHeight < 1 && resizedWidth < 1)
            bitmap = Bitmap.createBitmap(resizedWidth, resizedHeight, Bitmap.Config.ARGB_8888);
        else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }



    public void initManager(ImageInterface imageInterface, ImageView imageView)
    {
        this.imageInterface = imageInterface;
        this.imageView = imageView;
        imageFilterResources = new ImageFilterResources();
        BitmapDrawable abmp = (BitmapDrawable) imageView.getDrawable();
        imageFilterResources.initResources(abmp.getBitmap());
    }

    public void saveBitmap(int type,String imageDirectory, Bitmap bmp)
    {
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

            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == CameraConstants.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png");


            FileOutputStream out = null;
            try {
                out = new FileOutputStream(mediaFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void combineImages(Context c)
    {
        count = 0;
        isProcessing = true;
        imageFilterResources.combineImage(c);
        isProcessing = false;
    }



    private String[] filters = new String[]{"blue","green","grey","dark","gama","bright",
            "blackfilter","boost","brightness","colorfilter","contrast","decreasecolor",
            "emboss","engrave","flea","gammaeffect","gaussian","greyscale","highlight",
            "hue","invert","meanremoval","saturation","roundcorner","reflection","shading",
            "sepia","sharpen","smooth","tint","watermark"};

    public void combineImages(Bitmap bmpToBeOverlayed, Bitmap bmpToOverlay, int x, int y)
    {
        count = 0;
        isProcessing = true;
        imageFilterResources.combineImagesFromBitmapsWithXYWithOptions(bmpToBeOverlayed,bmpToOverlay,x,y);
        isProcessing = false;
    }

    public void combineImagesWithTextAbove(Bitmap bmpToBeOverlayed, Bitmap bmpToOverlay, String text)
    {
        count = 0;
        isProcessing = true;
        imageFilterResources.combineImagesFromBitmapsWithXYWithOptionsAndText(bmpToBeOverlayed,bmpToOverlay,30,50,text);
        isProcessing = false;
    }

    public void combineImagesWithTextBelow(Bitmap bmpToBeOverlayed, Bitmap bmpToOverlay, String text)
    {
        count = 0;
        isProcessing = true;
        Log.d(" Height and Width ", " Height " + bmpToBeOverlayed.getHeight() +  " Width " + bmpToBeOverlayed.getWidth());
        imageFilterResources.combineImagesFromBitmapsWithXYWithOptionsAndText(bmpToBeOverlayed,bmpToOverlay,30,bmpToBeOverlayed.getHeight() - (bmpToOverlay.getHeight() + 50),text);
        isProcessing = false;
    }



    public void tryWaterMark()
    {
        count = 30;
        isProcessing = true;
        imageFilterResources.applyWaterMarkEffect("AIBITS",100,280, Color.GREEN, 254,50, false);
        isProcessing = false;
    }

    public void addWatermarkBottom(String text)
    {
        count = 30;
        isProcessing = true;
        imageFilterResources.applyWaterMarkEffect(text,100,280, Color.LTGRAY, 254,50, false);
        isProcessing = false;
    }



    public void cycleImages()
    {
        isProcessing = true;
        switch (count){
            case 0:
                imageFilterResources.blue();
                break;
            case 1:
                imageFilterResources.green();

                break;
            case 2:
                imageFilterResources.gray();
                break;
            case 3:
                imageFilterResources.dark();
                break;
            case 4:
                imageFilterResources.gama();
                break;
            case 5:
                imageFilterResources.bright();
                break;
            case 6:
                imageFilterResources.applyBlackFilter();
                break;
            case 7:
                imageFilterResources.applyBoostEffect(2,25);
                break;
            case 8:
                imageFilterResources.applyBrightnessEffect(2);
                break;
            case 9:
                imageFilterResources.applyColorFilterEffect(.5,.3,.4);
                break;
            case 10:
                imageFilterResources.applyContrastEffect(4);
                break;
            case 11:
                imageFilterResources.applyDecreaseColorDepthEffect(3);
                break;
            case 12:
                imageFilterResources.applyEmbossEffect();
                break;
            case 13:
                imageFilterResources.applyEngraveEffect();
                break;
            case 14:
                imageFilterResources.applyFleaEffect();
                break;
            case 15:
                imageFilterResources.applyGammaEffect(.5,.3,.4);
                break;
            case 16:
                imageFilterResources.applyGaussianBlurEffect();
                break;
            case 17:
                imageFilterResources.applyGreyscaleEffect();
                break;
            case 18:
                imageFilterResources.applyHighlightEffect();
                break;
            case 19:
                imageFilterResources.applyHueFilter(3);
                break;
            case 20:
                imageFilterResources.applyInvertEffect();
                break;
            case 21:
                imageFilterResources.applyMeanRemovalEffect();
                break;
            case 22:
                imageFilterResources.applySaturationFilter(4);
                break;
            case 23:
                imageFilterResources.applyRoundCornerEffect(4);
                break;
            case 24:
                imageFilterResources.applyReflection();
                break;
            case 25:
                imageFilterResources.applyShadingFilter(4);
                break;
            case 26:
                imageFilterResources.applySepiaToningEffect(5,.6,.4,.2);
                break;
            case 27:
                imageFilterResources.applySharpenEffect(5);
                break;
            case 28:
                imageFilterResources.applySmoothEffect(10);
                break;
            case 29:
                imageFilterResources.applyTintEffect(2);
                break;
            case 30:
                imageFilterResources.applyWaterMarkEffect("AIBITS",100,100, Color.GREEN, 1,20, false);
                break;

        }
        isProcessing = false;

    }

    public boolean isImageProcessing()
    {
        return isProcessing;
    }

    public void changeImage()
    {

        imageInterface.imageChangedWithCurrentFilter(filters[count]);
        imageView.setImageBitmap(imageFilterResources.getOperation());
        count++;
        if(count >= 30)
            count = 0;
    }
    public void updateImage()
    {
        imageView.setImageBitmap(imageFilterResources.getOperation());
        count = 0;
    }


}
