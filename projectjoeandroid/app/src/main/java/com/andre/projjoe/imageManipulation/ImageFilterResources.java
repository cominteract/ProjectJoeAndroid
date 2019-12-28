package com.andre.projjoe.imageManipulation;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;


import com.andre.projjoe.R;

import java.util.Random;

/**
 * Created by andreinsigne on 22/11/2017.
 */

public class ImageFilterResources {
    private Bitmap bmp;
    private Bitmap operation;
    public void initResources(Bitmap bmp)
    {
        this.bmp = bmp;
    }

    public Bitmap getOperation() {
        return operation;
    }

    public void gray() {
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());
        double red = 0.33;
        double green = 0.59;
        double blue = 0.11;

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);

                r = (int) red * r;
                g = (int) green * g;
                b = (int) blue * b;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
    }

    public void bright(){
        operation= Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 100  +  r;
                g = 100  + g;
                b = 100  + b;
                alpha = 100 + alpha;
                operation.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }

    }

    public void dark(){
        operation= Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  r - 50;
                g =  g - 50;
                b =  b - 50;
                alpha = alpha -50;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }

    }

    public void gama() {
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(),bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  r + 150;
                g =  0;
                b =  0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }

    }

    public void red(){
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

        for(int i=0; i < bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  0 + 150;
                g =  0;
                b =  0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
    }

    public void green(){
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

        for(int i=0; i < bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  0;
                g =  g+150;
                b =  0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
    }

    public void blue(){
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

        for(int i=0; i<bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  0;
                g =  0;
                b =  b+150;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }

    }

    public void applyHighlightEffect() {

        // create new bitmap, which will be painted and becomes result image
        operation = Bitmap.createBitmap(bmp.getWidth() + 96, bmp.getHeight() + 96, Bitmap.Config.ARGB_8888);
        // setup canvas for painting
        Canvas canvas = new Canvas(operation);
        // setup default color
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // create a blur paint for capturing alpha
        Paint ptBlur = new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
        int[] offsetXY = new int[2];
        // capture alpha into a bitmap
        Bitmap bmAlpha = bmp.extractAlpha(ptBlur, offsetXY);
        // create a color paint
        Paint ptAlphaColor = new Paint();
        ptAlphaColor.setColor(0xFFFFFFFF);
        // paint color for captured alpha region (bitmap)
        canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
        // free memory
        bmAlpha.recycle();

        // paint the image source
        canvas.drawBitmap(bmp, 0, 0, null);

        // return out final image

    }

    public void applyInvertEffect() {
        // create new bitmap with the same settings as source bitmap
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        // color info
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = bmp.getHeight();
        int width = bmp.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get one pixel
                pixelColor = bmp.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final bitmap

    }

    public void applyGreyscaleEffect() {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // scan through every single pixel
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = bmp.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }

    //Gamma Image (R, G, B) = (1.8, 1.8, 1.8)
    public void applyGammaEffect(double red, double green, double blue) {
        // create output image
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // color information
        int A, R, G, B;
        int pixel;
        // constant value curve
        final int MAX_SIZE = 256;
        final double MAX_VALUE_DBL = 255.0;
        final int MAX_VALUE_INT = 255;
        final double REVERSE = 1.0;

        // gamma arrays
        int[] gammaR = new int[MAX_SIZE];
        int[] gammaG = new int[MAX_SIZE];
        int[] gammaB = new int[MAX_SIZE];

        // setting values for every gamma channels
        for (int i = 0; i < MAX_SIZE; ++i) {
            gammaR[i] = (int) Math.min(MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
            gammaG[i] = (int) Math.min(MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
            gammaB[i] = (int) Math.min(MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
        }

        // apply gamma table
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmp.getPixel(x, y);
                A = Color.alpha(pixel);
                // look up gamma
                R = gammaR[Color.red(pixel)];
                G = gammaG[Color.green(pixel)];
                B = gammaB[Color.blue(pixel)];
                // set new color to output bitmap
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }

    public void applyColorFilterEffect(double red, double green, double blue) {
        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // create output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmp.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int) (Color.red(pixel) * red);
                G = (int) (Color.green(pixel) * green);
                B = (int) (Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }


    public  void applySepiaToningEffect(int depth, double red, double green, double blue) {
        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // create output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        // constant grayscale
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmp.getPixel(x, y);
                // get color on each channel
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // apply grayscale sample
                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                // apply intensity level for sepid-toning on each channel
                R += (depth * red);
                if(R > 255) { R = 255; }

                G += (depth * green);
                if(G > 255) { G = 255; }

                B += (depth * blue);
                if(B > 255) { B = 255; }

                // set new pixel color to output image
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }

    public  void applyDecreaseColorDepthEffect(int bitOffset) {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // create output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmp.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // round-off color offset
                R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);
                if(R < 0) { R = 0; }
                G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);
                if(G < 0) { G = 0; }
                B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);
                if(B < 0) { B = 0; }

                // set pixel color to output bitmap
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }

    public  void applyContrastEffect(double value) {
        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // create output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmp.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }

    public  void applyBrightnessEffect(int value) {
        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // create output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = bmp.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                operation.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

    }

    public void applyGaussianBlurEffect() {
        double[][] GaussianBlurConfig = new double[][] {
                { 1, 2, 1 },
                { 2, 4, 2 },
                { 1, 2, 1 }
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(GaussianBlurConfig);
        convMatrix.Factor = 16;
        convMatrix.Offset = 0;
        operation = ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);
    }

    public  void applySharpenEffect(double weight) {
        double[][] SharpConfig = new double[][] {
                { 0 , -2    , 0  },
                { -2, weight, -2 },
                { 0 , -2    , 0  }
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(SharpConfig);
        convMatrix.Factor = weight - 8;
        operation = ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);
    }

    public  void applyMeanRemovalEffect() {
        double[][] MeanRemovalConfig = new double[][] {
                { -1 , -1, -1 },
                { -1 ,  9, -1 },
                { -1 , -1, -1 }
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(MeanRemovalConfig);
        convMatrix.Factor = 1;
        convMatrix.Offset = 0;
        operation = ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);
    }

    public void applySmoothEffect(double value) {
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.setAll(1);
        convMatrix.Matrix[1][1] = value;
        convMatrix.Factor = value + 8;
        convMatrix.Offset = 1;
        operation =  ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);
    }
    public void applyEmbossEffect() {
        double[][] EmbossConfig = new double[][] {
                { -1 ,  0, -1 },
                {  0 ,  4,  0 },
                { -1 ,  0, -1 }
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(EmbossConfig);
        convMatrix.Factor = 1;
        convMatrix.Offset = 127;
        operation =  ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);
    }

    public void applyEngraveEffect() {
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.setAll(0);
        convMatrix.Matrix[0][0] = -2;
        convMatrix.Matrix[1][1] = 2;
        convMatrix.Factor = 1;
        convMatrix.Offset = 95;
        operation = ConvolutionMatrix.computeConvolution3x3(bmp, convMatrix);
    }

    public void applyBoostEffect(int type, float percent) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, bmp.getConfig());

        int A, R, G, B;
        int pixel;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = bmp.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                if(type == 1) {
                    R = (int)(R * (1 + percent));
                    if(R > 255) R = 255;
                }
                else if(type == 2) {
                    G = (int)(G * (1 + percent));
                    if(G > 255) G = 255;
                }
                else if(type == 3) {
                    B = (int)(B * (1 + percent));
                    if(B > 255) B = 255;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

    }

    public void applyRoundCornerEffect( float round) {
        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // create bitmap output
        operation = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // set canvas for painting
        Canvas canvas = new Canvas(operation);
        canvas.drawARGB(0, 0, 0, 0);

        // config paint
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        // config rectangle for embedding
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        // draw rect to canvas
        canvas.drawRoundRect(rectF, round, round, paint);

        // create Xfer mode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // draw source image to canvas
        canvas.drawBitmap(bmp, rect, rect, paint);

        // return final image

    }

    public void combineImage(Context context)
    {
        processCombinedImages(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher).copy(Bitmap.Config.ARGB_8888, true));
    }

    public void combineImagesFromBitmaps(Bitmap bmpToBeOverlayed,Bitmap bmpToOverlay)
    {
        operation = Bitmap.createBitmap(bmpToBeOverlayed.getWidth(), bmpToBeOverlayed.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas combinedImageCanvas = new Canvas(operation);
        //combinedImageCanvas.drawBitmap(c, x-axis position in f, y-axis position in f, null);
        combinedImageCanvas.drawBitmap(bmpToBeOverlayed.copy(Bitmap.Config.ARGB_8888, true), 0f, 0f, null);
        combinedImageCanvas.drawBitmap(bmpToOverlay.copy(Bitmap.Config.ARGB_8888,true), 200, 200, null);
    }

    public void combineImagesFromBitmapsWithXY(Bitmap bmpToBeOverlayed,Bitmap bmpToOverlay, int x, int y)
    {
        operation = Bitmap.createBitmap(bmpToBeOverlayed.getWidth(), bmpToBeOverlayed.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas combinedImageCanvas = new Canvas(operation);
        //combinedImageCanvas.drawBitmap(c, x-axis position in f, y-axis position in f, null);
        combinedImageCanvas.drawBitmap(bmpToBeOverlayed.copy(Bitmap.Config.ARGB_8888, true), 0f, 0f, null);
        combinedImageCanvas.drawBitmap(bmpToOverlay.copy(Bitmap.Config.ARGB_8888,true), x, y, null);
    }

    public void combineImagesFromBitmapsWithXYWithOptions(Bitmap bmpToBeOverlayed,Bitmap bmpToOverlay, int x, int y)
    {
        operation = Bitmap.createBitmap(bmpToBeOverlayed.getWidth(), bmpToBeOverlayed.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas combinedImageCanvas = new Canvas(operation);
        //combinedImageCanvas.drawBitmap(c, x-axis position in f, y-axis position in f, null);
        combinedImageCanvas.drawBitmap(bmpToBeOverlayed.copy(Bitmap.Config.ARGB_8888, true), 0f, 0f, null);
        combinedImageCanvas.drawBitmap(bmpToOverlay.copy(Bitmap.Config.ARGB_8888,true), x, y, paintOptions(true,true));
    }

    public void combineImagesFromBitmapsWithXYWithOptionsAndText(Bitmap bmpToBeOverlayed,Bitmap bmpToOverlay, int x, int y, String text)
    {
        operation = Bitmap.createBitmap(bmpToBeOverlayed.getWidth(), bmpToBeOverlayed.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas combinedImageCanvas = new Canvas(operation);
        //combinedImageCanvas.drawBitmap(c, x-axis position in f, y-axis position in f, null);
        combinedImageCanvas.drawBitmap(bmpToBeOverlayed.copy(Bitmap.Config.ARGB_8888, true), 0f, 0f, null);
        combinedImageCanvas.drawBitmap(bmpToOverlay.copy(Bitmap.Config.ARGB_8888,true), x, y, paintOptions(false,true));




//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setAlpha(254);
//        paint.setTextSize(50);
//        paint.setAntiAlias(true);
//        paint.setUnderlineText(false);


        Paint mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(12);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);

        Paint mTextPaintOutline = new Paint();
        mTextPaintOutline.setAntiAlias(true);
        mTextPaintOutline.setTextSize(12);
        mTextPaintOutline.setColor(Color.RED);
        mTextPaintOutline.setStyle(Paint.Style.STROKE);
        mTextPaintOutline.setStrokeWidth(4);

        combinedImageCanvas.drawText(text, x + 40, y + 30, mTextPaintOutline);
        combinedImageCanvas.drawText(text, x + 40, y + 30, mTextPaint);

    }

    public Paint paintOptions(boolean isSemiTransparent, boolean isDither)
    {
        Paint paint = new Paint();
        if(isSemiTransparent == true)
            paint.setAlpha(58);
        else
            paint.setAlpha(254);
        paint.setDither(isDither);
        return paint;
    }

    private void processCombinedImages(Bitmap s)
    {
        //operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas combinedImageCanvas = new Canvas(operation);
        //combinedImageCanvas.drawBitmap(c, x-axis position in f, y-axis position in f, null);
        combinedImageCanvas.drawBitmap(bmp.copy(Bitmap.Config.ARGB_8888, true), 0f, 0f, null);
        combinedImageCanvas.drawBitmap(s, 200f, 200f, null);
    }


    public  void applyWaterMarkEffect(String watermark, int x,int y, int color, int alpha, int size, boolean underline) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());

        Canvas canvas = new Canvas(operation);
        canvas.drawBitmap(bmp, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, x, y, paint);


    }

    public static final double PI = 3.14159d;
    public static final double FULL_CIRCLE_DEGREE = 360d;
    public static final double HALF_CIRCLE_DEGREE = 180d;
    public static final double RANGE = 256d;

    public void applyTintEffect(int degree) {

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int[] pix = new int[width * height];
        bmp.getPixels(pix, 0, width, 0, 0, width, height);

        int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
        double angle = (PI * (double)degree) / HALF_CIRCLE_DEGREE;

        int S = (int)(RANGE * Math.sin(angle));
        int C = (int)(RANGE * Math.cos(angle));

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = ( pix[index] >> 16 ) & 0xff;
                int g = ( pix[index] >> 8 ) & 0xff;
                int b = pix[index] & 0xff;
                RY = ( 70 * r - 59 * g - 11 * b ) / 100;
                GY = (-30 * r + 41 * g - 11 * b ) / 100;
                BY = (-30 * r - 59 * g + 89 * b ) / 100;
                Y  = ( 30 * r + 59 * g + 11 * b ) / 100;
                RYY = ( S * BY + C * RY ) / 256;
                BYY = ( C * BY - S * RY ) / 256;
                GYY = (-51 * RYY - 19 * BYY ) / 100;
                R = Y + RYY;
                R = ( R < 0 ) ? 0 : (( R > 255 ) ? 255 : R );
                G = Y + GYY;
                G = ( G < 0 ) ? 0 : (( G > 255 ) ? 255 : G );
                B = Y + BYY;
                B = ( B < 0 ) ? 0 : (( B > 255 ) ? 255 : B );
                pix[index] = 0xff000000 | (R << 16) | (G << 8 ) | B;
            }

        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pix, 0, width, 0, 0, width, height);

        pix = null;

    }

    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;

    public  void applyFleaEffect() {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        // a random object
        Random random = new Random();

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get random color
                int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
                // OR
                pixels[index] |= randColor;
            }
        }
        // output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pixels, 0, width, 0, 0, width, height);

    }

    public  void applyBlackFilter() {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        // random object
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get color
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                // generate threshold
                thresHold = random.nextInt(COLOR_MAX);
                if(R < thresHold && G < thresHold && B < thresHold) {
                    pixels[index] = Color.rgb(COLOR_MIN, COLOR_MIN, COLOR_MIN);
                }
            }
        }
        // output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pixels, 0, width, 0, 0, width, height);

    }

    public void applySnowEffect() {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        // random object
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 50;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get color
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                // generate threshold
                thresHold = random.nextInt(COLOR_MAX);
                if(R > thresHold && G > thresHold && B > thresHold) {
                    pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX);
                }
            }
        }
        // output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pixels, 0, width, 0, 0, width, height);

    }

    public void applyShadingFilter(int shadingColor) {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // AND
                pixels[index] &= shadingColor;
            }
        }
        // output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pixels, 0, width, 0, 0, width, height);

    }

    public  void applySaturationFilter(int level) {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        float[] HSV = new float[3];
        // get pixel array from source
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // convert to HSV
                Color.colorToHSV(pixels[index], HSV);
                // increase Saturation level
                HSV[1] *= level;
                HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
                // take color back
                pixels[index] |= Color.HSVToColor(HSV);
            }
        }
        // output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pixels, 0, width, 0, 0, width, height);

    }

    public  void applyHueFilter( int level) {
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        float[] HSV = new float[3];
        // get pixel array from source
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // convert to HSV
                Color.colorToHSV(pixels[index], HSV);
                // increase Saturation level
                HSV[0] *= level;
                HSV[0] = (float) Math.max(0.0, Math.min(HSV[0], 360.0));
                // take color back
                pixels[index] |= Color.HSVToColor(HSV);
            }
        }
        // output bitmap
        operation = Bitmap.createBitmap(width, height, bmp.getConfig());
        operation.setPixels(pixels, 0, width, 0, 0, width, height);

    }

    public  void applyReflection() {
        // gap space between original and reflected
        final int reflectionGap = 4;
        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // this will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // create a Bitmap with the flip matrix applied to it.
        // we only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(bmp, 0, height/2, width, height/2, matrix, false);

        // create a new bitmap with same width but taller to fit reflection
        operation = Bitmap.createBitmap(width, (height + height/2), Bitmap.Config.ARGB_8888);

        // create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(operation);
        // draw in the original image
        canvas.drawBitmap(bmp, 0, 0, null);
        // draw in the gap
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        // draw in the reflection
        canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);

        // create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bmp.getHeight(), 0,
                operation.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                Shader.TileMode.CLAMP);
        // set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, operation.getHeight() + reflectionGap, paint);


    }
}
