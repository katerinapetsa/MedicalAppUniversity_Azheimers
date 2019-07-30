package com.example.ehealth.lab.university.diary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Iterator;

/**
 * @author Stavroula Kousparou
 */

public class Helper {

    public static Bitmap overlay(Context context, EnumSet<BodyRegion> bodyRegions) {
        Bitmap overlay = null;
        Iterator<BodyRegion> it = bodyRegions.iterator();
        if (it.hasNext()) {
            Bitmap img = BitmapFactory.decodeResource(context.getResources(), it.next().getResourceID());
            overlay = Bitmap.createBitmap(img.getWidth(), img.getHeight(), img.getConfig());
            if (overlay != null) {
                Canvas canvas = new Canvas(overlay);
                canvas.drawBitmap(img, 0, 0, null);
                img.recycle();

                while (it.hasNext()) {
                    img = BitmapFactory.decodeResource(context.getResources(), it.next().getResourceID());
                    canvas.drawBitmap(img, 0, 0, null);
                    img.recycle();
                }
            }
        }
        return overlay;
    }

    public static Bitmap overlay(Bitmap bitmap, Bitmap bitmapToAdd) {
        Bitmap overlay = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(overlay);
        canvas.drawBitmap(bitmap, 0, 0, null);
        bitmap.recycle();
        canvas.drawBitmap(bitmapToAdd, 0, 0, null);
        bitmapToAdd.recycle();
        return overlay;
    }

}
