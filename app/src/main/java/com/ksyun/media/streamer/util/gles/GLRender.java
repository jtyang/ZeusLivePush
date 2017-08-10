package com.ksyun.media.streamer.util.gles;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.EGLContextFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

public class GLRender {
    public static final int STATE_IDLE = 0;
    public static final int STATE_READY = 1;
    public static final int STATE_RELEASED = 2;
    public static final int VIEW_TYPE_GLSURFACEVIEW = 1;
    public static final int VIEW_TYPE_NONE = 0;
    public static final int VIEW_TYPE_OFFSCREEN = 3;
    public static final int VIEW_TYPE_TEXTUREVIEW = 2;
    private static final String a = "GLRender";
    private static final boolean b = true;
    private static final int c = 0;
    private static final int d = 1;
    private static final int e = 2;
    private static final int f = 3;
    private EGLContextFactory A;
    private EGLConfigChooser B;
    private Renderer C;
    private SurfaceTextureListener D;
    private TextureView g;
    private final Object h;
    private HandlerThread i;
    private Handler j;
    private a k;
    private c l;
    private EGLContext m;
    private EGLContext n;
    private android.opengl.EGLContext o;
    private AtomicInteger p;
    private long q;
    private GLSurfaceView r;
    private LinkedList<GLRenderListener> s;
    private final Object t;
    private LinkedList<Runnable> u;
    private final Object v;
    private LinkedList<Runnable> w;
    private final Object x;
    private FboManager y;
    private Runnable z;

    public interface GLRenderListener {
        void onDrawFrame();

        void onReady();

        void onReleased();

        void onSizeChanged(int i, int i2);
    }

    public interface ScreenShotListener {
        void onBitmapAvailable(Bitmap bitmap);
    }

    public GLRender() {
        this.h = new Object();
        this.t = new Object();
        this.v = new Object();
        this.x = new Object();
        this.z = new Runnable() {
            final /* synthetic */ GLRender a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.d();
            }
        };
        this.A = new EGLContextFactory() {
            final /* synthetic */ GLRender a;

            {
                this.a = r1;
            }

            public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
                return egl10.eglCreateContext(eGLDisplay, eGLConfig, this.a.m, new int[]{12440, GLRender.e, 12344});
            }

            public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
                egl10.eglDestroyContext(eGLDisplay, eGLContext);
                this.a.n = EGL10.EGL_NO_CONTEXT;
            }
        };
        this.B = new EGLConfigChooser() {
            final /* synthetic */ GLRender a;

            {
                this.a = r1;
            }

            public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
                EGLConfig[] eGLConfigArr = new EGLConfig[GLRender.d];
                if (egl10.eglChooseConfig(eGLDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344}, eGLConfigArr, eGLConfigArr.length, new int[GLRender.d])) {
                    return eGLConfigArr[GLRender.c];
                }
                Log.w(GLRender.a, "unable to find RGB8888 / 2 EGLConfig");
                return null;
            }
        };
        this.C = new Renderer() {
            final /* synthetic */ GLRender a;

            {
                this.a = r1;
            }

            public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
                this.a.a();
            }

            public void onSurfaceChanged(GL10 gl10, int i, int i2) {
                this.a.a(i, i2);
            }

            public void onDrawFrame(GL10 gl10) {
                this.a.b();
            }
        };
        this.D = new SurfaceTextureListener() {
            final /* synthetic */ GLRender a;

            {
                this.a = r1;
            }

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                Log.d(GLRender.a, "onSurfaceTextureAvailable " + i + "x" + i2);
                if (this.a.g != null && this.a.i == null) {
                    this.a.e();
                    this.a.j.sendMessage(Message.obtain(this.a.j, GLRender.c, surfaceTexture));
                    this.a.j.sendMessage(Message.obtain(this.a.j, GLRender.d, i, i2));
                }
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                Log.d(GLRender.a, "onSurfaceTextureSizeChanged " + i + "x" + i2);
                if (this.a.g != null && surfaceTexture == this.a.g.getSurfaceTexture()) {
                    this.a.j.sendMessage(Message.obtain(this.a.j, GLRender.d, i, i2));
                }
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                Log.d(GLRender.a, "onSurfaceTextureDestroyed");
                if (this.a.g == null || surfaceTexture != this.a.g.getSurfaceTexture()) {
                    surfaceTexture.release();
                } else {
                    this.a.b(surfaceTexture);
                }
                return false;
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
        this.p = new AtomicInteger(e);
        this.s = new LinkedList();
        this.u = new LinkedList();
        this.w = new LinkedList();
        this.m = EGL10.EGL_NO_CONTEXT;
        this.n = EGL10.EGL_NO_CONTEXT;
        if (VERSION.SDK_INT >= 17) {
            this.o = EGL14.EGL_NO_CONTEXT;
        }
        this.y = new FboManager();
    }

    public GLRender(EGLContext eGLContext) {
        this();
        this.m = eGLContext;
    }

    public void setInitEGL10Context(EGLContext eGLContext) {
        this.m = eGLContext;
    }

    public android.opengl.EGLContext getEGLContext() {
        return this.o;
    }

    public EGLContext getEGL10Context() {
        return this.n;
    }

    public void init(int i, int i2) {
        release();
        this.p.set(c);
        e();
        this.j.sendMessage(Message.obtain(this.j, c, i, i2));
        this.j.sendMessage(Message.obtain(this.j, d, i, i2));
    }

    public void init(GLSurfaceView gLSurfaceView) {
        if (gLSurfaceView != this.r) {
            release();
            this.p.set(c);
            try {
                gLSurfaceView.setEGLConfigChooser(this.B);
                gLSurfaceView.setEGLContextFactory(this.A);
                gLSurfaceView.setEGLContextClientVersion(e);
                gLSurfaceView.setRenderer(this.C);
                gLSurfaceView.setRenderMode(c);
            } catch (Exception e) {
            }
            this.r = gLSurfaceView;
            StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_GLSURFACE);
        }
    }

    public void init(TextureView textureView) {
        if (textureView != this.g) {
            release();
            this.p.set(c);
            textureView.setSurfaceTextureListener(this.D);
            this.g = textureView;
            if (textureView.getSurfaceTexture() != null) {
                Log.d(a, "TextureView already initialized");
                e();
                this.j.sendMessage(Message.obtain(this.j, c, textureView.getSurfaceTexture()));
                this.j.sendMessage(Message.obtain(this.j, d, textureView.getWidth(), textureView.getHeight()));
            }
            StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_TEXTUREVIEW);
        }
    }

    public int getViewType() {
        if (this.r == null && this.g == null && this.i == null) {
            return c;
        }
        if (this.r != null) {
            return d;
        }
        if (this.g != null) {
            return e;
        }
        return f;
    }

    public Object getCurrentView() {
        if (this.r != null) {
            return this.r;
        }
        if (this.g != null) {
            return this.g;
        }
        return null;
    }

    public void addListener(GLRenderListener gLRenderListener) {
        synchronized (this.t) {
            if (!this.s.contains(gLRenderListener)) {
                this.s.add(gLRenderListener);
            }
        }
    }

    public void removeListener(GLRenderListener gLRenderListener) {
        synchronized (this.t) {
            this.s.remove(gLRenderListener);
        }
    }

    public int getState() {
        return this.p.get();
    }

    public boolean isGLRenderThread() {
        return this.q == Thread.currentThread().getId() ? b : false;
    }

    public void onPause() {
        if (this.r != null) {
            this.p.set(e);
            this.r.queueEvent(new Runnable() {
                final /* synthetic */ GLRender a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.c();
                }
            });
            this.r.onPause();
        }
    }

    public void onResume() {
        if (this.p.get() == e) {
            this.p.set(c);
        }
        if (this.r != null) {
            this.r.onResume();
        }
    }

    public void requestRender() {
        if (this.p.get() == d) {
            if (this.r != null) {
                this.r.requestRender();
            }
            if (this.j != null) {
                this.j.sendEmptyMessage(e);
            }
        }
    }

    public void queueEvent(Runnable runnable) {
        if (this.p.get() == 0) {
            Log.d(a, "glContext not ready, queue event:" + runnable);
            synchronized (this.v) {
                this.u.add(runnable);
            }
        } else if (this.p.get() != d) {
            Log.d(a, "glContext lost, drop event:" + runnable);
        } else if (this.r != null) {
            this.r.queueEvent(runnable);
            this.r.queueEvent(this.z);
        } else if (this.j != null) {
            this.j.post(runnable);
            this.j.post(this.z);
        }
    }

    public void queueDrawFrameAppends(Runnable runnable) {
        if (this.p.get() == d) {
            synchronized (this.x) {
                this.w.add(runnable);
            }
        }
    }

    public void release() {
        if (this.r != null && this.p.get() == d) {
            this.r.queueEvent(new Runnable() {
                final /* synthetic */ GLRender a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.c();
                }
            });
            this.r.onPause();
        }
        this.r = null;
        this.g = null;
        this.p.set(e);
        b(null);
    }

    public FboManager getFboManager() {
        return this.y;
    }

    private void a() {
        this.p.set(d);
        this.q = Thread.currentThread().getId();
        this.y.init();
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        this.n = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        if (VERSION.SDK_INT >= 17) {
            this.o = EGL14.eglGetCurrentContext();
        }
        synchronized (this.t) {
            Iterator it = this.s.iterator();
            while (it.hasNext()) {
                ((GLRenderListener) it.next()).onReady();
            }
        }
    }

    private void a(int i, int i2) {
        GLES20.glViewport(c, c, i, i2);
        synchronized (this.t) {
            Iterator it = this.s.iterator();
            while (it.hasNext()) {
                ((GLRenderListener) it.next()).onSizeChanged(i, i2);
            }
        }
    }

    private void b() {
        synchronized (this.v) {
            Iterator it = this.u.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
            this.u.clear();
        }
        synchronized (this.t) {
            it = this.s.iterator();
            while (it.hasNext()) {
                ((GLRenderListener) it.next()).onDrawFrame();
            }
        }
        d();
    }

    private void c() {
        if (VERSION.SDK_INT >= 17) {
            this.o = EGL14.EGL_NO_CONTEXT;
        }
        this.p.set(e);
        synchronized (this.t) {
            Iterator it = this.s.iterator();
            while (it.hasNext()) {
                ((GLRenderListener) it.next()).onReleased();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() {
        /*
        r3 = this;
    L_0x0000:
        r1 = r3.x;
        monitor-enter(r1);
        r0 = r3.w;	 Catch:{ all -> 0x001f }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x001f }
        if (r0 == 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        return;
    L_0x000d:
        r0 = r3.w;	 Catch:{ all -> 0x001f }
        r0 = r0.getFirst();	 Catch:{ all -> 0x001f }
        r0 = (java.lang.Runnable) r0;	 Catch:{ all -> 0x001f }
        r2 = r3.w;	 Catch:{ all -> 0x001f }
        r2.removeFirst();	 Catch:{ all -> 0x001f }
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        r0.run();
        goto L_0x0000;
    L_0x001f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.util.gles.GLRender.d():void");
    }

    private void a(SurfaceTexture surfaceTexture, int i, int i2) {
        this.k = new a(this.m, c);
        if (surfaceTexture != null) {
            this.l = new c(this.k, surfaceTexture);
        } else {
            this.l = new c(this.k, i, i2);
        }
        this.l.d();
        GLES20.glViewport(c, c, this.l.a(), this.l.b());
    }

    private void a(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
        if (this.l != null) {
            this.l.f();
            this.l = null;
        }
        if (this.k != null) {
            this.k.a();
            this.k = null;
        }
    }

    private void e() {
        synchronized (this.h) {
            if (this.i == null) {
                this.i = new HandlerThread("GLThread");
                this.i.start();
                this.j = new Handler(this.i.getLooper(), new Callback() {
                    final /* synthetic */ GLRender a;

                    {
                        this.a = r1;
                    }

                    public boolean handleMessage(Message message) {
                        switch (message.what) {
                            case GLRender.c /*0*/:
                                this.a.a((SurfaceTexture) message.obj, message.arg1, message.arg2);
                                this.a.a();
                                break;
                            case GLRender.d /*1*/:
                                this.a.a(message.arg1, message.arg2);
                                break;
                            case GLRender.e /*2*/:
                                this.a.b();
                                this.a.l.e();
                                break;
                            case GLRender.f /*3*/:
                                this.a.c();
                                this.a.a((SurfaceTexture) message.obj);
                                this.a.i.quit();
                                break;
                        }
                        return GLRender.b;
                    }
                });
            }
        }
    }

    private void b(SurfaceTexture surfaceTexture) {
        synchronized (this.h) {
            if (this.i != null) {
                this.j.removeCallbacksAndMessages(null);
                this.j.sendMessage(Message.obtain(this.j, f, surfaceTexture));
                try {
                    this.i.join();
                    this.i = null;
                    this.j = null;
                } catch (InterruptedException e) {
                    Log.d(a, "GLThread Interrupted!");
                    this.i = null;
                    this.j = null;
                } catch (Throwable th) {
                    this.i = null;
                    this.j = null;
                }
            } else if (surfaceTexture != null) {
                surfaceTexture.release();
            }
        }
    }
}
