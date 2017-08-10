package com.ksyun.media.streamer.filter.imgtex;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GLRender.ScreenShotListener;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;

public abstract class ImgTexFilterBase extends ImgFilterBase {
    public static final int ERROR_LOAD_PROGRAM_FAILED = -1;
    public static final int ERROR_UNKNOWN = -2;
    private static final String a = "ImgTexFilterBase";
    private static final boolean b = true;
    private List<SinkPin<ImgTexFrame>> c;
    private SrcPin<ImgTexFrame> d;
    private ImgTexFrame[] e;
    private int[] f;
    private ImgTexFormat g;
    private boolean h;
    private float i;
    private ScreenShotListener j;
    private Thread k;
    private GLRenderListener l;
    protected GLRender mGLRender;
    protected boolean mInited;
    protected Handler mMainHandler;
    protected int mOutTexture;
    protected boolean mReuseFbo;

    /* renamed from: com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ ImgTexFilterBase b;

        AnonymousClass2(ImgTexFilterBase imgTexFilterBase, int i) {
            this.b = imgTexFilterBase;
            this.a = i;
        }

        public void run() {
            if (this.b.mErrorListener != null) {
                this.b.mErrorListener.onError(this.b, this.a);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase.4 */
    class AnonymousClass4 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ int b;
        final /* synthetic */ ByteBuffer c;
        final /* synthetic */ long d;
        final /* synthetic */ ImgTexFilterBase e;

        AnonymousClass4(ImgTexFilterBase imgTexFilterBase, int i, int i2, ByteBuffer byteBuffer, long j) {
            this.e = imgTexFilterBase;
            this.a = i;
            this.b = i2;
            this.c = byteBuffer;
            this.d = j;
        }

        public void run() {
            Bitmap createBitmap = Bitmap.createBitmap(this.a, this.b, Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.c);
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, -1.0f);
            Bitmap createScaledBitmap;
            if (((double) this.e.i) != 1.0d) {
                createScaledBitmap = Bitmap.createScaledBitmap(createBitmap, (int) (((float) this.a) * this.e.i), (int) (((float) this.b) * this.e.i), ImgTexFilterBase.b);
                Bitmap createBitmap2 = Bitmap.createBitmap(createScaledBitmap, 0, 0, createScaledBitmap.getWidth(), createScaledBitmap.getHeight(), matrix, ImgTexFilterBase.b);
                if (this.e.j != null) {
                    this.e.j.onBitmapAvailable(createBitmap2);
                }
                createScaledBitmap.recycle();
                createBitmap2.recycle();
            } else {
                createScaledBitmap = Bitmap.createBitmap(createBitmap, 0, 0, this.a, this.b, matrix, ImgTexFilterBase.b);
                if (this.e.j != null) {
                    this.e.j.onBitmapAvailable(createScaledBitmap);
                }
                createScaledBitmap.recycle();
            }
            createBitmap.recycle();
            Log.d(ImgTexFilterBase.a, "Saved " + this.a + "x" + this.b + " frame in " + (System.currentTimeMillis() - this.d) + " ms");
        }
    }

    private class a extends SinkPin<ImgTexFrame> {
        final /* synthetic */ ImgTexFilterBase a;
        private int b;

        /* renamed from: com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase.a.1 */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ ImgTexFormat a;
            final /* synthetic */ long b;
            final /* synthetic */ a c;

            AnonymousClass1(a aVar, ImgTexFormat imgTexFormat, long j) {
                this.c = aVar;
                this.a = imgTexFormat;
                this.b = j;
            }

            public void run() {
                try {
                    this.c.a.d.onFrameAvailable(new ImgTexFrame(this.a, this.c.a.mOutTexture, null, this.b));
                } finally {
                    this.c.a.mGLRender.getFboManager().unlock(this.c.a.mOutTexture);
                    this.c.a.mOutTexture = ImgTexFilterBase.ERROR_LOAD_PROGRAM_FAILED;
                }
            }
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((ImgTexFrame) obj);
        }

        public a(ImgTexFilterBase imgTexFilterBase, int i) {
            this.a = imgTexFilterBase;
            this.b = i;
        }

        public void onFormatChanged(Object obj) {
            this.a.onFormatChanged(this.b, (ImgTexFormat) obj);
            if (this.b == this.a.mMainSinkPinIndex) {
                ImgTexFormat srcPinFormat = this.a.getSrcPinFormat();
                this.a.d.onFormatChanged(srcPinFormat);
                this.a.g = srcPinFormat;
            }
        }

        public void a(ImgTexFrame imgTexFrame) {
            this.a.e[this.b] = imgTexFrame;
            if (this.b == this.a.mMainSinkPinIndex) {
                b(imgTexFrame);
            }
        }

        public void onDisconnect(boolean z) {
            this.a.e[this.b] = null;
            if (this.b == this.a.mMainSinkPinIndex && z) {
                this.a.release();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void b(ImgTexFrame r10) {
            /*
            r9 = this;
            r8 = 2;
            r7 = 1;
            r3 = -1;
            r6 = 36160; // 0x8d40 float:5.0671E-41 double:1.78654E-319;
            r5 = 0;
            r0 = r9.a;
            r0 = r0.d;
            r0 = r0.isConnected();
            if (r0 != 0) goto L_0x0014;
        L_0x0013:
            return;
        L_0x0014:
            r0 = r9.a;
            r1 = r0.getSrcPinFormat();
            r0 = r9.a;
            r0 = r0.g;
            if (r0 == 0) goto L_0x004b;
        L_0x0022:
            r0 = r9.a;
            r0 = r0.g;
            r0 = r0.width;
            r2 = r1.width;
            if (r0 != r2) goto L_0x003a;
        L_0x002e:
            r0 = r9.a;
            r0 = r0.g;
            r0 = r0.height;
            r2 = r1.height;
            if (r0 == r2) goto L_0x004b;
        L_0x003a:
            r0 = r9.b;
            r2 = r9.a;
            r2 = r2.mMainSinkPinIndex;
            if (r0 != r2) goto L_0x004b;
        L_0x0042:
            r0 = r9.a;
            r0 = r0.d;
            r0.onFormatChanged(r1);
        L_0x004b:
            r0 = r9.a;
            r0.g = r1;
            r0 = r9.a;
            r0 = r0.mOutTexture;
            if (r0 != r3) goto L_0x006a;
        L_0x0056:
            r0 = r9.a;
            r2 = r9.a;
            r2 = r2.mGLRender;
            r2 = r2.getFboManager();
            r3 = r1.width;
            r4 = r1.height;
            r2 = r2.getTextureAndLock(r3, r4);
            r0.mOutTexture = r2;
        L_0x006a:
            r0 = r9.a;
            r0 = r0.mGLRender;
            r0 = r0.getFboManager();
            r2 = r9.a;
            r2 = r2.mOutTexture;
            r0 = r0.getFramebuffer(r2);
            r2 = 2978; // 0xba2 float:4.173E-42 double:1.4713E-320;
            r3 = r9.a;
            r3 = r3.f;
            android.opengl.GLES20.glGetIntegerv(r2, r3, r5);
            r2 = r1.width;
            r3 = r1.height;
            android.opengl.GLES20.glViewport(r5, r5, r2, r3);
            android.opengl.GLES20.glBindFramebuffer(r6, r0);
            r0 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
            android.opengl.GLES20.glClear(r0);	 Catch:{ Exception -> 0x0100 }
            r0 = r9.a;	 Catch:{ Exception -> 0x0100 }
            r2 = r9.a;	 Catch:{ Exception -> 0x0100 }
            r2 = r2.e;	 Catch:{ Exception -> 0x0100 }
            r0.onDraw(r2);	 Catch:{ Exception -> 0x0100 }
            r0 = r9.a;	 Catch:{ Exception -> 0x0100 }
            r0 = r0.h;	 Catch:{ Exception -> 0x0100 }
            if (r0 == 0) goto L_0x00b6;
        L_0x00a7:
            r0 = r9.a;	 Catch:{ Exception -> 0x0100 }
            r2 = r1.width;	 Catch:{ Exception -> 0x0100 }
            r3 = r1.height;	 Catch:{ Exception -> 0x0100 }
            r0.a(r2, r3);	 Catch:{ Exception -> 0x0100 }
            r0 = r9.a;	 Catch:{ Exception -> 0x0100 }
            r2 = 0;
            r0.h = r2;	 Catch:{ Exception -> 0x0100 }
        L_0x00b6:
            android.opengl.GLES20.glBindFramebuffer(r6, r5);
            r0 = r9.a;
            r0 = r0.f;
            r0 = r0[r5];
            r2 = r9.a;
            r2 = r2.f;
            r2 = r2[r7];
            r3 = r9.a;
            r3 = r3.f;
            r3 = r3[r8];
            r4 = r9.a;
            r4 = r4.f;
            r5 = 3;
            r4 = r4[r5];
            android.opengl.GLES20.glViewport(r0, r2, r3, r4);
        L_0x00dd:
            r4 = r10.pts;
            r0 = r10.flags;
            r0 = r0 & 4;
            if (r0 == 0) goto L_0x0189;
        L_0x00e5:
            r0 = new com.ksyun.media.streamer.framework.ImgTexFrame;
            r2 = r9.a;
            r2 = r2.mOutTexture;
            r3 = 0;
            r0.<init>(r1, r2, r3, r4);
            r1 = r0.flags;
            r1 = r1 | 4;
            r0.flags = r1;
            r1 = r9.a;
            r1 = r1.d;
            r1.onFrameAvailable(r0);
            goto L_0x0013;
        L_0x0100:
            r0 = move-exception;
            r2 = r9.a;	 Catch:{ all -> 0x0160 }
            r2 = r2.isReuseFbo();	 Catch:{ all -> 0x0160 }
            if (r2 == 0) goto L_0x011d;
        L_0x0109:
            r2 = r9.a;	 Catch:{ all -> 0x0160 }
            r2 = r2.mGLRender;	 Catch:{ all -> 0x0160 }
            r2 = r2.getFboManager();	 Catch:{ all -> 0x0160 }
            r3 = r9.a;	 Catch:{ all -> 0x0160 }
            r3 = r3.mOutTexture;	 Catch:{ all -> 0x0160 }
            r2.unlock(r3);	 Catch:{ all -> 0x0160 }
            r2 = r9.a;	 Catch:{ all -> 0x0160 }
            r3 = -1;
            r2.mOutTexture = r3;	 Catch:{ all -> 0x0160 }
        L_0x011d:
            r2 = r0 instanceof com.ksyun.media.streamer.util.gles.GLProgramLoadException;	 Catch:{ all -> 0x0160 }
            if (r2 == 0) goto L_0x0159;
        L_0x0121:
            r2 = r9.a;	 Catch:{ all -> 0x0160 }
            r3 = -1;
            r2.sendError(r3);	 Catch:{ all -> 0x0160 }
        L_0x0127:
            r2 = "ImgTexFilterBase";
            r3 = "Draw frame error!";
            android.util.Log.e(r2, r3);	 Catch:{ all -> 0x0160 }
            r0.printStackTrace();	 Catch:{ all -> 0x0160 }
            android.opengl.GLES20.glBindFramebuffer(r6, r5);
            r0 = r9.a;
            r0 = r0.f;
            r0 = r0[r5];
            r2 = r9.a;
            r2 = r2.f;
            r2 = r2[r7];
            r3 = r9.a;
            r3 = r3.f;
            r3 = r3[r8];
            r4 = r9.a;
            r4 = r4.f;
            r5 = 3;
            r4 = r4[r5];
            android.opengl.GLES20.glViewport(r0, r2, r3, r4);
            goto L_0x00dd;
        L_0x0159:
            r2 = r9.a;	 Catch:{ all -> 0x0160 }
            r3 = -2;
            r2.sendError(r3);	 Catch:{ all -> 0x0160 }
            goto L_0x0127;
        L_0x0160:
            r0 = move-exception;
            android.opengl.GLES20.glBindFramebuffer(r6, r5);
            r1 = r9.a;
            r1 = r1.f;
            r1 = r1[r5];
            r2 = r9.a;
            r2 = r2.f;
            r2 = r2[r7];
            r3 = r9.a;
            r3 = r3.f;
            r3 = r3[r8];
            r4 = r9.a;
            r4 = r4.f;
            r5 = 3;
            r4 = r4[r5];
            android.opengl.GLES20.glViewport(r1, r2, r3, r4);
            throw r0;
        L_0x0189:
            r0 = r9.a;
            r0 = r0.isReuseFbo();
            if (r0 == 0) goto L_0x019f;
        L_0x0191:
            r0 = r9.a;
            r0 = r0.mGLRender;
            r2 = new com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase$a$1;
            r2.<init>(r9, r1, r4);
            r0.queueDrawFrameAppends(r2);
            goto L_0x0013;
        L_0x019f:
            r0 = r9.a;
            r6 = r0.d;
            r0 = new com.ksyun.media.streamer.framework.ImgTexFrame;
            r2 = r9.a;
            r2 = r2.mOutTexture;
            r3 = 0;
            r0.<init>(r1, r2, r3, r4);
            r6.onFrameAvailable(r0);
            goto L_0x0013;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase.a.b(com.ksyun.media.streamer.framework.ImgTexFrame):void");
        }
    }

    protected abstract ImgTexFormat getSrcPinFormat();

    protected abstract void onDraw(ImgTexFrame[] imgTexFrameArr);

    protected abstract void onFormatChanged(int i, ImgTexFormat imgTexFormat);

    public ImgTexFilterBase(GLRender gLRender) {
        this.mReuseFbo = b;
        this.f = new int[4];
        this.mOutTexture = ERROR_LOAD_PROGRAM_FAILED;
        this.k = null;
        this.l = new GLRenderListener() {
            final /* synthetic */ ImgTexFilterBase a;

            {
                this.a = r1;
            }

            public void onReady() {
                int i = 0;
                this.a.mInited = false;
                this.a.mOutTexture = ImgTexFilterBase.ERROR_LOAD_PROGRAM_FAILED;
                while (i < this.a.e.length) {
                    this.a.e[i] = null;
                    i++;
                }
                this.a.onGLContextReady();
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        };
        this.c = new LinkedList();
        for (int i = 0; i < getSinkPinNum(); i++) {
            this.c.add(new a(this, i));
        }
        this.d = new SrcPin();
        this.e = new ImgTexFrame[getSinkPinNum()];
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mGLRender = gLRender;
        this.mGLRender.addListener(this.l);
    }

    public void setGLRender(GLRender gLRender) {
        if (this.mGLRender != null) {
            this.mGLRender.removeListener(this.l);
        }
        this.mGLRender = gLRender;
        this.mGLRender.addListener(this.l);
    }

    public SinkPin<ImgTexFrame> getSinkPin(int i) {
        return (SinkPin) this.c.get(i);
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.d;
    }

    public void release() {
        this.d.disconnect(b);
        if (this.mOutTexture != ERROR_LOAD_PROGRAM_FAILED) {
            this.mGLRender.getFboManager().unlock(this.mOutTexture);
            this.mOutTexture = ERROR_LOAD_PROGRAM_FAILED;
        }
        this.mGLRender.queueEvent(new Runnable() {
            final /* synthetic */ ImgTexFilterBase a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.onRelease();
            }
        });
        this.mGLRender.removeListener(this.l);
        if (this.k != null && this.k.isAlive()) {
            this.k.interrupt();
            this.k = null;
        }
    }

    protected boolean isReuseFbo() {
        return this.mReuseFbo;
    }

    public void setReuseFbo(boolean z) {
        this.mReuseFbo = z;
    }

    protected void onGLContextReady() {
    }

    protected void onRelease() {
    }

    protected void sendError(int i) {
        this.mMainHandler.post(new AnonymousClass2(this, i));
    }

    private void a(float f) {
        this.i = Math.min(Math.max(0.0f, f), 1.0f);
    }

    public void requestScreenShot(ScreenShotListener screenShotListener) {
        requestScreenShot(1.0f, screenShotListener);
    }

    public void requestScreenShot(float f, ScreenShotListener screenShotListener) {
        a(f);
        this.h = b;
        this.j = screenShotListener;
        StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_SCREENSHOT);
    }

    private void a(int i, int i2) {
        long currentTimeMillis = System.currentTimeMillis();
        Buffer allocateDirect = ByteBuffer.allocateDirect((i * i2) * 4);
        allocateDirect.order(ByteOrder.LITTLE_ENDIAN);
        GLES20.glReadPixels(0, 0, i, i2, 6408, 5121, allocateDirect);
        allocateDirect.rewind();
        this.k = new Thread(new AnonymousClass4(this, i, i2, allocateDirect, currentTimeMillis));
        this.k.start();
    }
}
