package com.ksyun.media.streamer.encoder;

import android.annotation.TargetApi;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GlUtil;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;
import com.ksyun.media.streamer.util.gles.f;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

@TargetApi(19)
public class ImgTexToBuf {
    public static final int ERROR_UNKNOWN = -2;
    public static final int ERROR_UNSUPPORTED = -1;
    private static final String d = "ImgTexToBuf";
    private static final boolean e = false;
    private static final int f = 1;
    private static final int g = 2;
    private static final int h = 3;
    protected volatile boolean a;
    protected AtomicInteger b;
    protected AtomicInteger c;
    private GLRender i;
    private boolean j;
    private d k;
    private Surface l;
    private f m;
    public SinkPin<ImgTexFrame> mSinkPin;
    public SrcPin<ImgBufFrame> mSrcPin;
    private int n;
    private int o;
    private ImgTexFormat p;
    private ImageReader q;
    private ByteBuffer r;
    private ImgBufFormat s;
    private HandlerThread t;
    private Handler u;
    private ConditionVariable v;
    private final Handler w;
    private ErrorListener x;
    private GLRenderListener y;

    /* renamed from: com.ksyun.media.streamer.encoder.ImgTexToBuf.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ ImgTexToBuf b;

        AnonymousClass2(ImgTexToBuf imgTexToBuf, int i) {
            this.b = imgTexToBuf;
            this.a = i;
        }

        public void run() {
            if (this.b.x != null) {
                this.b.x.onError(this.b, this.a);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.encoder.ImgTexToBuf.3 */
    class AnonymousClass3 extends Handler {
        final /* synthetic */ ImgTexToBuf a;

        AnonymousClass3(ImgTexToBuf imgTexToBuf, Looper looper) {
            this.a = imgTexToBuf;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case ImgTexToBuf.f /*1*/:
                    try {
                        this.a.a((ImgTexFormat) message.obj);
                    } catch (Exception e) {
                        this.a.a((int) ImgTexToBuf.ERROR_UNSUPPORTED);
                    }
                case ImgTexToBuf.g /*2*/:
                    try {
                        this.a.a((ImgTexFrame) message.obj);
                    } catch (Exception e2) {
                        this.a.a((int) ImgTexToBuf.ERROR_UNKNOWN);
                    } finally {
                        this.a.v.open();
                    }
                case ImgTexToBuf.h /*3*/:
                    this.a.b();
                    this.a.t.quit();
                default:
            }
        }
    }

    public interface ErrorListener {
        void onError(ImgTexToBuf imgTexToBuf, int i);
    }

    private class a extends SinkPin<ImgTexFrame> {
        final /* synthetic */ ImgTexToBuf a;

        private a(ImgTexToBuf imgTexToBuf) {
            this.a = imgTexToBuf;
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((ImgTexFrame) obj);
        }

        public synchronized void onFormatChanged(Object obj) {
            this.a.a = true;
            this.a.u.sendMessage(this.a.u.obtainMessage(ImgTexToBuf.f, obj));
        }

        public synchronized void a(ImgTexFrame imgTexFrame) {
            if (this.a.a) {
                if (this.a.u.hasMessages(ImgTexToBuf.g)) {
                    Log.d(ImgTexToBuf.d, "total dropped: " + this.a.c.get() + " total sent: " + this.a.b.get());
                    this.a.c.incrementAndGet();
                } else {
                    this.a.b.incrementAndGet();
                    GLES20.glFinish();
                    this.a.i.getFboManager().lock(imgTexFrame.textureId);
                    this.a.v.close();
                    this.a.u.sendMessage(this.a.u.obtainMessage(ImgTexToBuf.g, imgTexFrame));
                    this.a.v.block();
                }
            }
        }

        public synchronized void onDisconnect(boolean z) {
            if (z) {
                this.a.release();
            }
        }
    }

    public ImgTexToBuf(GLRender gLRender) {
        this.o = h;
        this.v = new ConditionVariable();
        this.a = e;
        this.y = new GLRenderListener() {
            final /* synthetic */ ImgTexToBuf a;

            {
                this.a = r1;
            }

            public void onReady() {
                this.a.j = ImgTexToBuf.e;
                this.a.n = 0;
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        };
        this.mSinkPin = new a();
        this.mSrcPin = new SrcPin();
        this.c = new AtomicInteger(0);
        this.b = new AtomicInteger(0);
        this.w = new Handler(Looper.getMainLooper());
        this.i = gLRender;
        this.i.addListener(this.y);
        a();
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.x = errorListener;
    }

    public void setOutputColorFormat(int i) {
        if (i == 5 || i == h || i == 6) {
            this.o = i;
            this.r = null;
            return;
        }
        throw new IllegalArgumentException("only FMT_RGBA or FMT_I420 supported!");
    }

    public void resetFrameStat() {
        this.c.set(0);
        this.b.set(0);
    }

    public int getFrameDropped() {
        return this.c.get();
    }

    public int getFrameSent() {
        return this.b.get();
    }

    @Deprecated
    public void start() {
    }

    @Deprecated
    public void stop() {
    }

    public SinkPin<ImgTexFrame> getSinkPin() {
        return this.mSinkPin;
    }

    public SrcPin<ImgBufFrame> getSrcPin() {
        return this.mSrcPin;
    }

    public void release() {
        this.v.open();
        this.i.removeListener(this.y);
        this.mSrcPin.disconnect(true);
        if (this.t != null) {
            this.u.sendEmptyMessage(h);
            try {
                this.t.join();
            } catch (Exception e) {
                Log.d(d, "ImgTexToBuf thread interrupted");
            } finally {
                this.t = null;
            }
        }
    }

    private void a(int i) {
        this.w.post(new AnonymousClass2(this, i));
    }

    private void a() {
        this.t = new HandlerThread(d);
        this.t.start();
        this.u = new AnonymousClass3(this, this.t.getLooper());
    }

    protected void a(ImageReader imageReader) {
        Image acquireNextImage = imageReader.acquireNextImage();
        ByteBuffer buffer = acquireNextImage.getPlanes()[0].getBuffer();
        int rowStride = acquireNextImage.getPlanes()[0].getRowStride();
        if (buffer != null) {
            long timestamp = (acquireNextImage.getTimestamp() / 1000) / 1000;
            if (this.s == null) {
                this.s = new ImgBufFormat(this.o, this.p.width, this.p.height, 0);
                if (this.o == 5) {
                    this.s.stride = new int[f];
                    this.s.stride[0] = rowStride;
                    this.s.strideNum = f;
                } else if (this.o == 6) {
                    r6 = new int[4];
                    this.s.stride = r6;
                    this.s.stride[0] = this.p.width;
                    this.s.stride[f] = 0;
                    this.s.stride[g] = 0;
                    this.s.stride[h] = 0;
                    this.s.strideNum = 4;
                }
                this.mSrcPin.onFormatChanged(this.s);
            }
            if (this.o == 5) {
                this.mSrcPin.onFrameAvailable(new ImgBufFrame(this.s, buffer, timestamp));
            } else if (this.o == h) {
                if (this.r == null) {
                    this.r = ByteBuffer.allocateDirect(((this.s.width * this.s.height) * h) / g);
                }
                if (this.r != null) {
                    this.r.clear();
                    ColorFormatConvert.RGBAToI420(buffer, rowStride, this.s.width, this.s.height, this.r);
                    this.r.rewind();
                    this.mSrcPin.onFrameAvailable(new ImgBufFrame(this.s, this.r, timestamp));
                }
            } else {
                if (this.r == null) {
                    this.r = ByteBuffer.allocateDirect(this.s.width * this.s.height);
                }
                if (this.r != null) {
                    this.r.clear();
                    ColorFormatConvert.RGBAToBGR8(buffer, rowStride, this.s.width, this.s.height, this.r);
                    this.r.rewind();
                    this.mSrcPin.onFrameAvailable(new ImgBufFrame(this.s, this.r, timestamp));
                }
            }
        }
        acquireNextImage.close();
    }

    private void a(ImgTexFormat imgTexFormat) {
        if (!(this.p == null || this.q == null || (this.p.width == imgTexFormat.width && this.p.height == imgTexFormat.height))) {
            b();
        }
        this.p = imgTexFormat;
        this.s = null;
        if (this.q == null) {
            this.q = ImageReader.newInstance(this.p.width, this.p.height, f, f);
            this.l = this.q.getSurface();
            this.q.setOnImageAvailableListener(new OnImageAvailableListener() {
                final /* synthetic */ ImgTexToBuf a;

                {
                    this.a = r1;
                }

                public void onImageAvailable(ImageReader imageReader) {
                    try {
                        this.a.a(imageReader);
                    } catch (Exception e) {
                        this.a.a((int) ImgTexToBuf.ERROR_UNSUPPORTED);
                    }
                }
            }, this.u);
        }
    }

    private void b() {
        if (this.q != null) {
            this.q.close();
            this.q = null;
        }
        if (this.n != 0) {
            GLES20.glDeleteProgram(this.n);
            GLES20.glGetError();
            this.n = 0;
        }
        if (this.m != null) {
            this.m.f();
            this.m = null;
        }
        if (this.k != null) {
            this.k.a();
            this.k = null;
        }
        this.s = null;
        this.r = null;
        this.j = e;
    }

    private void a(ImgTexFrame imgTexFrame) {
        if (this.q != null) {
            if ((imgTexFrame.flags & 4) != 0) {
                ImgBufFrame imgBufFrame = new ImgBufFrame(this.s, null, 0);
                imgBufFrame.flags |= 4;
                this.mSrcPin.onFrameAvailable(imgBufFrame);
                return;
            }
            if (!this.j) {
                a(this.i.getEGLContext());
                this.j = true;
            }
            GLES20.glClear(16384);
            b(imgTexFrame);
            GLES20.glFinish();
            this.m.a((imgTexFrame.pts * 1000) * 1000);
            this.m.e();
        }
        this.i.getFboManager().unlock(imgTexFrame.textureId);
    }

    private void a(EGLContext eGLContext) {
        if (this.k == null || this.m == null) {
            this.k = new d(eGLContext, 0);
            this.m = new f(this.k, this.l);
        } else {
            this.m.d();
            this.m.c();
            this.k.a();
            this.k = new d(eGLContext, 0);
            this.m.a(this.k);
        }
        this.m.d();
        GLES20.glViewport(0, 0, this.m.a(), this.m.b());
    }

    private void b(ImgTexFrame imgTexFrame) {
        int i;
        ImgTexFormat imgTexFormat = imgTexFrame.format;
        int i2 = imgTexFrame.textureId;
        float[] fArr = imgTexFrame.texMatrix;
        if (imgTexFormat.colorFormat == h) {
            i = 36197;
        } else {
            i = 3553;
        }
        if (this.n == 0) {
            String str;
            String str2 = GlUtil.BASE_VERTEX_SHADER;
            if (imgTexFormat.colorFormat == h) {
                str = "#extension GL_OES_EGL_image_external : require\nuniform samplerExternalOES sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            } else {
                str = "uniform sampler2D sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            }
            this.n = GlUtil.createProgram(str2, str);
            if (this.n == 0) {
                Log.e(d, "Created program " + this.n + " failed");
                throw new RuntimeException("Unable to create program");
            }
        }
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.n, "aPosition");
        GlUtil.checkLocation(glGetAttribLocation, "aPosition");
        int glGetAttribLocation2 = GLES20.glGetAttribLocation(this.n, "aTextureCoord");
        GlUtil.checkLocation(glGetAttribLocation2, "aTextureCoord");
        int glGetUniformLocation = GLES20.glGetUniformLocation(this.n, "uTexMatrix");
        GlUtil.checkLocation(glGetUniformLocation, "uTexMatrix");
        GlUtil.checkGlError("draw start");
        GLES20.glUseProgram(this.n);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(i, i2);
        GLES20.glUniformMatrix4fv(glGetUniformLocation, f, e, fArr, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(glGetAttribLocation, g, 5126, e, 8, TexTransformUtil.getVertexCoordsBuf());
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation2);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(glGetAttribLocation2, g, 5126, e, 8, TexTransformUtil.getTexCoordsBuf());
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glDrawArrays(5, 0, 4);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(glGetAttribLocation);
        GLES20.glDisableVertexAttribArray(glGetAttribLocation2);
        GLES20.glBindTexture(i, 0);
        GLES20.glUseProgram(0);
    }
}
