package com.ksyun.media.streamer.encoder;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.ScreenShotListener;
import java.nio.ByteBuffer;

/* compiled from: ImgTexToBitmap */
public class a extends ImgTexToBuf {
    private static final String d = "ImgTexToBitmap";
    private boolean e;
    private float f;
    private ScreenShotListener g;
    private Thread h;

    /* compiled from: ImgTexToBitmap */
    /* renamed from: com.ksyun.media.streamer.encoder.a.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ ByteBuffer e;
        final /* synthetic */ a f;

        AnonymousClass1(a aVar, int i, int i2, int i3, int i4, ByteBuffer byteBuffer) {
            this.f = aVar;
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = byteBuffer;
        }

        public void run() {
            Bitmap createBitmap = Bitmap.createBitmap(this.a + (this.b / this.c), this.d, Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.e);
            if (((double) this.f.f) != 1.0d) {
                createBitmap = Bitmap.createScaledBitmap(createBitmap, (int) (((float) this.a) * this.f.f), (int) (((float) this.d) * this.f.f), true);
                if (this.f.g != null) {
                    this.f.g.onBitmapAvailable(createBitmap);
                }
                if (createBitmap != null) {
                    createBitmap.recycle();
                    return;
                }
                return;
            }
            if (this.f.g != null) {
                this.f.g.onBitmapAvailable(createBitmap);
            }
            if (createBitmap != null) {
                createBitmap.recycle();
            }
        }
    }

    public a(GLRender gLRender) {
        super(gLRender);
        this.e = false;
        this.f = 1.0f;
        this.g = null;
        this.h = null;
        setOutputColorFormat(5);
    }

    @TargetApi(19)
    protected void a(ImageReader imageReader) {
        Image acquireNextImage = imageReader.acquireNextImage();
        if (this.e) {
            if (acquireNextImage != null) {
                Plane[] planes = acquireNextImage.getPlanes();
                ByteBuffer buffer = planes[0].getBuffer();
                if (buffer != null) {
                    int width = acquireNextImage.getWidth();
                    int height = acquireNextImage.getHeight();
                    int pixelStride = planes[0].getPixelStride();
                    this.h = new Thread(new AnonymousClass1(this, width, planes[0].getRowStride() - (pixelStride * width), pixelStride, height, buffer));
                    this.h.start();
                }
            }
            this.e = false;
            this.a = false;
        }
        acquireNextImage.close();
    }

    public SinkPin<ImgTexFrame> getSinkPin() {
        return this.mSinkPin;
    }

    private void a(float f) {
        this.f = Math.min(Math.max(0.0f, f), 1.0f);
    }

    public void a(ScreenShotListener screenShotListener) {
        a(1.0f, screenShotListener);
    }

    public void a(float f, ScreenShotListener screenShotListener) {
        a(f);
        this.a = true;
        this.e = true;
        this.g = screenShotListener;
    }

    public void release() {
        if (this.h != null && this.h.isAlive()) {
            this.h.interrupt();
            this.h = null;
        }
    }
}
