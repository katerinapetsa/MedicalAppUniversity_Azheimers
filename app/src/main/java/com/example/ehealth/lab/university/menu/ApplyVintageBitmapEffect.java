package com.example.ehealth.lab.university.menu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;

public class ApplyVintageBitmapEffect {

    public static Bitmap applyVintegge(Bitmap originalImage, String flag) {
        Bitmap workingBitmap = Bitmap.createBitmap(originalImage);
        Bitmap mutableBitmap = workingBitmap
                .copy(Config.ARGB_8888, true);
        if (flag.equals("big") || flag.equals("combine")
                || flag.equals("profile")) {
            float radius;
            if (flag.equals("profile")) {
                radius = (float) (mutableBitmap.getWidth() / 0.6);
            } else
                radius = (float) (mutableBitmap.getWidth() / 0.65);
            RadialGradient gradient = new RadialGradient(
                    mutableBitmap.getWidth() / 2,
                    mutableBitmap.getHeight() / 2, radius, Color.TRANSPARENT,
                    Color.BLACK, TileMode.CLAMP);

            Canvas canvas = new Canvas(mutableBitmap);
            canvas.drawARGB(1, 0, 0, 0);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setShader(gradient);

            final Rect rect = new Rect(0, 0, mutableBitmap.getWidth(),
                    mutableBitmap.getHeight());
            final RectF rectf = new RectF(rect);

            canvas.drawRect(rectf, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(mutableBitmap, rect, rect, paint);

            if (flag.equals("big")) {
                return applyReflection(mutableBitmap);
            }
        }

        return mutableBitmap;
    }

    private static Bitmap applyReflection(Bitmap originalImage) {
        // gap space between original and reflected
        final int reflectionGap = 0;
        // get image size
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // this will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // create a Bitmap with the flip matrix applied to it.
        // we only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        // create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (width + height / 2), Config.ARGB_8888);

        // create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(bitmapWithReflection);
        // draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        // draw in the gap
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        // draw in the reflection
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        // create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        // set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
}
