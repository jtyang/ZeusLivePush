package com.ksyun.media.streamer.capture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.BitmapLoader;
import com.ksyun.media.streamer.util.gles.GLRender;
import java.util.Timer;
import java.util.TimerTask;

public class ImageCapture {
    public static final String TAG = "ImageCapture";
    private ImgTexSrcPin a;
    private float b;
    private Timer c;

    public ImageCapture(GLRender gLRender) {
        this.b = 0.0f;
        this.a = new ImgTexSrcPin(gLRender);
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.a;
    }

    public float getRepeatFps() {
        return this.b;
    }

    public void setRepeatFps(float f) {
        this.b = f;
    }

    public void start(Context context, String str) {
        start(BitmapLoader.loadBitmap(context, str), true);
    }

    public void start(Bitmap bitmap, boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            Log.e(TAG, "invalid bitmap, start failed!");
            return;
        }
        this.a.updateFrame(bitmap, z);
        if (this.b > 0.0f) {
            long j = (long) (1000.0f / this.b);
            this.c = new Timer("ImageRepeat");
            this.c.schedule(new TimerTask() {
                final /* synthetic */ ImageCapture a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.a.repeatFrame();
                }
            }, j, j);
        }
    }

    public void stop() {
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        this.a.updateFrame(null, false);
    }

    public void release() {
        stop();
    }
}
