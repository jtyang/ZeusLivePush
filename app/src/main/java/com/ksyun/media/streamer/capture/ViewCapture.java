package com.ksyun.media.streamer.capture;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.gles.GLRender;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

public class ViewCapture {
    public static final String TAG = "ViewCapture";
    private ImgTexSrcPin a;
    private float b;
    private int c;
    private int d;
    private Timer e;
    private View f;
    private Bitmap g;
    private Canvas h;
    private ByteBuffer i;

    public ViewCapture(GLRender gLRender) {
        this.b = StreamerConstants.DEFAULT_TARGET_FPS;
        this.a = new ImgTexSrcPin(gLRender);
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.a;
    }

    public void setTargetResolution(int i, int i2) {
        this.c = i;
        this.d = i2;
    }

    public int getTargetWidth() {
        return this.c;
    }

    public int getTargetHeight() {
        return this.d;
    }

    public float getUpdateFps() {
        return this.b;
    }

    public void setUpdateFps(float f) {
        this.b = f;
    }

    public void start(View view) {
        if (view != null) {
            this.f = view;
            if (this.b > 0.0f) {
                long j = (long) (1000.0f / this.b);
                this.e = new Timer("ViewRepeat");
                this.e.schedule(new TimerTask() {
                    final /* synthetic */ ViewCapture a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.f.post(new Runnable() {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                this.a.a.a();
                            }
                        });
                    }
                }, 40, j);
            }
        }
    }

    public void stop() {
        if (this.e != null) {
            this.e.cancel();
            this.e = null;
        }
        this.a.updateFrame(null, false);
        this.i = null;
        if (this.g != null) {
            this.g.recycle();
            this.g = null;
        }
    }

    private Bitmap a(View view) {
        if (view == null || view.getWidth() == 0 || view.getHeight() == 0) {
            return null;
        }
        if (this.g == null || this.g.isRecycled()) {
            int width = view.getWidth();
            int height = view.getHeight();
            if (this.c > 0 || this.d > 0) {
                if (this.c == 0) {
                    this.c = (this.d * width) / height;
                }
                if (this.d == 0) {
                    this.d = (height * this.c) / width;
                }
                width = this.c;
                height = this.d;
            }
            float width2 = ((float) width) / ((float) view.getWidth());
            float height2 = ((float) height) / ((float) view.getHeight());
            Log.d(TAG, "init bitmap " + width + "x" + height + " scale: " + width2 + "x" + height2);
            this.g = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            this.h = new Canvas(this.g);
            this.h.scale(width2, height2);
        }
        this.g.eraseColor(0);
        view.draw(this.h);
        return this.g;
    }

    private void a() {
        Bitmap a = a(this.f);
        if (a != null) {
            int width = a.getWidth();
            int height = a.getHeight();
            int i = width * 4;
            if (this.i == null) {
                this.i = ByteBuffer.allocate(i * height);
            }
            this.i.clear();
            a.copyPixelsToBuffer(this.i);
            this.i.flip();
            this.a.updateFrame(this.i, i, width, height);
            return;
        }
        this.a.updateFrame(null, false);
    }
}
