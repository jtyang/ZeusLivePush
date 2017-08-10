package com.ksyun.media.streamer.util.gles;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* compiled from: Egl10Core */
public final class a {
    public static final int a = 1;
    public static final int b = 2;
    private static final String c = "Egl10Core";
    private static final int d = 12440;
    private static final int e = 4;
    private static final int f = 64;
    private static final int g = 12610;
    private EGLDisplay h;
    private EGLContext i;
    private EGLConfig j;
    private int k;

    public a() {
        this(null, 0);
    }

    public a(EGLContext eGLContext, int i) {
        this.h = EGL10.EGL_NO_DISPLAY;
        this.i = EGL10.EGL_NO_CONTEXT;
        this.j = null;
        this.k = -1;
        if (this.h != EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        if (eGLContext == null) {
            eGLContext = EGL10.EGL_NO_CONTEXT;
        }
        this.h = d().eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.h == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        if (d().eglInitialize(this.h, new int[b])) {
            EGLConfig b;
            EGLContext eglCreateContext;
            if ((i & b) != 0) {
                b = b(i, 3);
                if (b != null) {
                    eglCreateContext = d().eglCreateContext(this.h, b, eGLContext, new int[]{d, 3, 12344});
                    if (d().eglGetError() == 12288) {
                        this.j = b;
                        this.i = eglCreateContext;
                        this.k = 3;
                    }
                }
            }
            if (this.i == EGL10.EGL_NO_CONTEXT) {
                b = b(i, b);
                if (b == null) {
                    throw new RuntimeException("Unable to find a suitable EGLConfig");
                }
                eglCreateContext = d().eglCreateContext(this.h, b, eGLContext, new int[]{d, b, 12344});
                b("eglCreateContext");
                this.j = b;
                this.i = eglCreateContext;
                this.k = b;
            }
            int[] iArr = new int[a];
            d().eglQueryContext(this.h, this.i, d, iArr);
            Log.d(c, "EGLContext created, client version " + iArr[0]);
            return;
        }
        this.h = null;
        throw new RuntimeException("unable to initialize EGL14");
    }

    private EGLConfig b(int i, int i2) {
        int i3;
        if (i2 >= 3) {
            i3 = 68;
        } else {
            i3 = e;
        }
        int[] iArr = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, i3, 12344, 0, 12344};
        if ((i & a) != 0) {
            iArr[iArr.length - 3] = g;
            iArr[iArr.length - 2] = a;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[a];
        if (d().eglChooseConfig(this.h, iArr, eGLConfigArr, eGLConfigArr.length, new int[a])) {
            return eGLConfigArr[0];
        }
        Log.w(c, "unable to find RGB8888 / " + i2 + " EGLConfig");
        return null;
    }

    public void a() {
        if (this.h != EGL10.EGL_NO_DISPLAY) {
            d().eglMakeCurrent(this.h, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            d().eglDestroyContext(this.h, this.i);
            d().eglTerminate(this.h);
        }
        this.h = EGL10.EGL_NO_DISPLAY;
        this.i = EGL10.EGL_NO_CONTEXT;
        this.j = null;
    }

    protected void finalize() {
        try {
            if (this.h != EGL10.EGL_NO_DISPLAY) {
                Log.w(c, "WARNING: EglCore was not explicitly released -- state may be leaked");
                a();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public void a(EGLSurface eGLSurface) {
        d().eglDestroySurface(this.h, eGLSurface);
    }

    public EGLSurface a(Object obj) {
        if ((obj instanceof Surface) || (obj instanceof SurfaceTexture)) {
            int[] iArr = new int[a];
            iArr[0] = 12344;
            EGLSurface eglCreateWindowSurface = d().eglCreateWindowSurface(this.h, this.j, obj, iArr);
            b("eglCreateWindowSurface");
            if (eglCreateWindowSurface != null) {
                return eglCreateWindowSurface;
            }
            throw new RuntimeException("surface was null");
        }
        throw new RuntimeException("invalid surface: " + obj);
    }

    public EGLSurface a(int i, int i2) {
        EGLSurface eglCreatePbufferSurface = d().eglCreatePbufferSurface(this.h, this.j, new int[]{12375, i, 12374, i2, 12344});
        b("eglCreatePbufferSurface");
        if (eglCreatePbufferSurface != null) {
            return eglCreatePbufferSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public void b(EGLSurface eGLSurface) {
        if (this.h == EGL10.EGL_NO_DISPLAY) {
            Log.d(c, "NOTE: makeCurrent w/o display");
        }
        if (!d().eglMakeCurrent(this.h, eGLSurface, eGLSurface, this.i)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void a(EGLSurface eGLSurface, EGLSurface eGLSurface2) {
        if (this.h == EGL10.EGL_NO_DISPLAY) {
            Log.d(c, "NOTE: makeCurrent w/o display");
        }
        if (!d().eglMakeCurrent(this.h, eGLSurface, eGLSurface2, this.i)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public void b() {
        if (!d().eglMakeCurrent(this.h, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public boolean c(EGLSurface eGLSurface) {
        return d().eglSwapBuffers(this.h, eGLSurface);
    }

    public boolean d(EGLSurface eGLSurface) {
        return this.i.equals(d().eglGetCurrentContext()) && eGLSurface.equals(d().eglGetCurrentSurface(12377));
    }

    public int a(EGLSurface eGLSurface, int i) {
        int[] iArr = new int[a];
        d().eglQuerySurface(this.h, eGLSurface, i, iArr);
        return iArr[0];
    }

    public int c() {
        return this.k;
    }

    public static void a(String str) {
        EGLDisplay eglGetCurrentDisplay = d().eglGetCurrentDisplay();
        EGLContext eglGetCurrentContext = d().eglGetCurrentContext();
        Log.i(c, "Current EGL (" + str + "): display=" + eglGetCurrentDisplay + ", context=" + eglGetCurrentContext + ", surface=" + d().eglGetCurrentSurface(12377));
    }

    private void b(String str) {
        int eglGetError = d().eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

    private static EGL10 d() {
        return (EGL10) EGLContext.getEGL();
    }
}
