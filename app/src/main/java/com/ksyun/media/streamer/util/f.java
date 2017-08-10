package com.ksyun.media.streamer.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;

/* compiled from: TextGraphicUtils */
public class f {
    public static float a(String str, float f) {
        Paint paint = new Paint();
        paint.setTextSize(f);
        return paint.measureText(str);
    }

    public static float a(Paint paint, String str) {
        return paint.measureText(str);
    }

    public static float a(float f) {
        Paint paint = new Paint();
        paint.setTextSize(f);
        FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float a(Paint paint) {
        FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static int b(String str, float f) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.setTextSize(f);
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public static int c(String str, float f) {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.setTextSize(f);
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public static float b(Paint paint) {
        FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.leading - fontMetrics.ascent;
    }

    public static Bitmap a(String str, int i, float f, Bitmap bitmap) {
        int i2;
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, 1));
        paint.setColor(i);
        paint.setTextSize(f);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        float a = a(paint, str);
        float a2 = a(paint);
        int i3 = a % 1.0f == 0.0f ? (int) a : ((int) a) + 1;
        if (a2 % 1.0f == 0.0f) {
            i2 = (int) a2;
        } else {
            i2 = ((int) a2) + 1;
        }
        if (bitmap != null) {
            i3 += bitmap.getWidth();
            if (bitmap.getHeight() > i2) {
                i2 = bitmap.getHeight();
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(i3, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, (float) ((i2 - bitmap.getHeight()) / 2), paint);
            canvas.drawText(str, (float) bitmap.getWidth(), ((((float) i2) - a2) / 2.0f) + b(paint), paint);
        } else {
            canvas.drawText(str, 0.0f, ((((float) i2) - a2) / 2.0f) + b(paint), paint);
        }
        canvas.save();
        return createBitmap;
    }

    public static Bitmap a(String str, int i, float f) {
        return a(str, i, f, null);
    }
}
