package com.ksyun.media.streamer.capture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.ConditionVariable;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GlUtil;
import java.nio.ByteBuffer;

public class ImgTexSrcPin extends SrcPin<ImgTexFrame> {
    private static final String c = "ImgTexSrcPin";
    private ImgTexFormat d;
    private int e;
    private float[] f;
    private GLRender g;
    private boolean h;
    private ConditionVariable i;
    private GLRenderListener j;

    /* renamed from: com.ksyun.media.streamer.capture.ImgTexSrcPin.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Bitmap a;
        final /* synthetic */ int b;
        final /* synthetic */ long c;
        final /* synthetic */ boolean d;
        final /* synthetic */ ImgTexSrcPin e;

        AnonymousClass1(ImgTexSrcPin imgTexSrcPin, Bitmap bitmap, int i, long j, boolean z) {
            this.e = imgTexSrcPin;
            this.a = bitmap;
            this.b = i;
            this.c = j;
            this.d = z;
        }

        public void run() {
            this.e.a(this.a, this.b, this.c, this.d);
        }
    }

    /* renamed from: com.ksyun.media.streamer.capture.ImgTexSrcPin.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ ByteBuffer a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;
        final /* synthetic */ long f;
        final /* synthetic */ ImgTexSrcPin g;

        AnonymousClass2(ImgTexSrcPin imgTexSrcPin, ByteBuffer byteBuffer, int i, int i2, int i3, int i4, long j) {
            this.g = imgTexSrcPin;
            this.a = byteBuffer;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = j;
        }

        public void run() {
            this.g.a(this.a, this.b, this.c, this.d, this.e, this.f);
            if (this.g.h) {
                this.g.i.open();
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.capture.ImgTexSrcPin.3 */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ long a;
        final /* synthetic */ ImgTexSrcPin b;

        AnonymousClass3(ImgTexSrcPin imgTexSrcPin, long j) {
            this.b = imgTexSrcPin;
            this.a = j;
        }

        public void run() {
            this.b.a(this.a);
            if (this.b.h) {
                this.b.i.open();
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.capture.ImgTexSrcPin.4 */
    class AnonymousClass4 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ ImgTexSrcPin b;

        AnonymousClass4(ImgTexSrcPin imgTexSrcPin, int i) {
            this.b = imgTexSrcPin;
            this.a = i;
        }

        public void run() {
            GLES20.glDeleteTextures(1, new int[]{this.a}, 0);
        }
    }

    public ImgTexSrcPin(GLRender gLRender) {
        this.e = -1;
        this.i = new ConditionVariable();
        this.j = new GLRenderListener() {
            final /* synthetic */ ImgTexSrcPin a;

            {
                this.a = r1;
            }

            public void onReady() {
                this.a.d = null;
                this.a.e = -1;
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
                if (this.a.h) {
                    this.a.i.open();
                }
            }
        };
        this.f = new float[16];
        this.g = gLRender;
        this.g.addListener(this.j);
        this.h = false;
    }

    public boolean getUseSyncMode() {
        return this.h;
    }

    public void setUseSyncMode(boolean z) {
        this.h = z;
    }

    public void updateFrame(Bitmap bitmap, boolean z) {
        updateFrame(bitmap, 0, (System.nanoTime() / 1000) / 1000, z);
    }

    public void updateFrame(Bitmap bitmap, long j, boolean z) {
        updateFrame(bitmap, 0, j, z);
    }

    public void updateFrame(Bitmap bitmap, int i, long j, boolean z) {
        if (this.g.isGLRenderThread()) {
            a(bitmap, i, j, z);
        } else {
            this.g.queueEvent(new AnonymousClass1(this, bitmap, i, j, z));
        }
    }

    public void updateFrame(ByteBuffer byteBuffer, int i, int i2, int i3) {
        updateFrame(byteBuffer, i, i2, i3, 0, (System.nanoTime() / 1000) / 1000);
    }

    public void updateFrame(ByteBuffer byteBuffer, int i, int i2, int i3, long j) {
        updateFrame(byteBuffer, i, i2, i3, 0, j);
    }

    public void updateFrame(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, long j) {
        if (this.g.isGLRenderThread()) {
            a(byteBuffer, i, i2, i3, i4, j);
            return;
        }
        if (this.h) {
            this.i.close();
        }
        this.g.queueEvent(new AnonymousClass2(this, byteBuffer, i, i2, i3, i4, j));
        if (this.h) {
            this.i.block();
        }
    }

    public void repeatFrame() {
        repeatFrame((System.nanoTime() / 1000) / 1000);
    }

    public void repeatFrame(long j) {
        if (this.g.isGLRenderThread()) {
            a(j);
            return;
        }
        if (this.h) {
            this.i.close();
        }
        this.g.queueEvent(new AnonymousClass3(this, j));
        if (this.h) {
            this.i.block();
        }
    }

    public void release() {
        disconnect(true);
        this.g.removeListener(this.j);
        int i = this.e;
        this.e = -1;
        if (i != -1) {
            this.g.queueEvent(new AnonymousClass4(this, i));
        }
    }

    private void a(float[] fArr, float f, int i) {
        int i2 = i % 360;
        if (i2 % 90 == 0) {
            Matrix.setIdentityM(fArr, 0);
            switch (i2) {
                case GLRender.VIEW_TYPE_NONE /*0*/:
                    Matrix.translateM(fArr, 0, 0.0f, 1.0f, 0.0f);
                    break;
                case 90:
                    Matrix.translateM(fArr, 0, 0.0f, 0.0f, 0.0f);
                    break;
                case 180:
                    Matrix.translateM(fArr, 0, 1.0f, 0.0f, 0.0f);
                    break;
                case 270:
                    Matrix.translateM(fArr, 0, 1.0f, 1.0f, 0.0f);
                    break;
            }
            Matrix.rotateM(fArr, 0, (float) i2, 0.0f, 0.0f, 1.0f);
            Matrix.scaleM(fArr, 0, f, -1.0f, 1.0f);
        }
    }

    private void a(Bitmap bitmap, int i, long j, boolean z) {
        if (bitmap != null && !bitmap.isRecycled()) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (i % 180 != 0) {
                width = bitmap.getHeight();
                height = bitmap.getWidth();
            }
            if (this.d != null && this.d.width == width && this.d.height == height) {
                height = 0;
            } else {
                this.d = new ImgTexFormat(1, width, height);
                height = 1;
            }
            this.e = GlUtil.loadTexture(bitmap, this.e);
            if (z) {
                bitmap.recycle();
            }
            if (this.e != -1) {
                if (height != 0) {
                    onFormatChanged(this.d);
                }
                a(this.f, 1.0f, i);
                onFrameAvailable(new ImgTexFrame(this.d, this.e, this.f, j));
            }
        } else if (this.d != null) {
            if (this.e != -1) {
                GLES20.glDeleteTextures(1, new int[]{this.e}, 0);
                this.e = -1;
            }
            onFrameAvailable(new ImgTexFrame(this.d, -1, null, 0));
        }
    }

    private void a(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, long j) {
        int i5 = 0;
        if (byteBuffer != null && byteBuffer.limit() != 0) {
            if (!(this.d != null && this.d.width == i2 && this.d.height == i3)) {
                this.d = new ImgTexFormat(1, i2, i3);
                i5 = 1;
            }
            int i6 = i / 4;
            this.e = GlUtil.loadTexture(byteBuffer, i6, i3, this.e);
            if (this.e != -1) {
                if (i5 != 0) {
                    onFormatChanged(this.d);
                }
                a(this.f, ((float) i2) / ((float) i6), i4);
                onFrameAvailable(new ImgTexFrame(this.d, this.e, this.f, j));
            }
        } else if (this.d != null) {
            if (this.e != -1) {
                GLES20.glDeleteTextures(1, new int[]{this.e}, 0);
                this.e = -1;
            }
            onFrameAvailable(new ImgTexFrame(this.d, -1, null, 0));
        }
    }

    private void a(long j) {
        if (this.e != -1 && this.d != null) {
            onFrameAvailable(new ImgTexFrame(this.d, this.e, this.f, j));
        }
    }
}
