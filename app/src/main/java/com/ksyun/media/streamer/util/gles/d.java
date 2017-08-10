package com.ksyun.media.streamer.util.gles;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.Log;
import android.view.Surface;

@TargetApi(18)
/* compiled from: EglCore */
public final class d {
    public static final int a = 1;
    public static final int b = 2;
    private static final String c = "EglCore";
    private static final int d = 12610;
    private EGLDisplay e;
    private EGLContext f;
    private EGLConfig g;
    private int h;

    public d() {
        this(null, 0);
    }

    public d(EGLContext eGLContext, int i) {
        this.e = EGL14.EGL_NO_DISPLAY;
        this.f = EGL14.EGL_NO_CONTEXT;
        this.g = null;
        this.h = -1;
        if (this.e != EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        if (eGLContext == null) {
            eGLContext = EGL14.EGL_NO_CONTEXT;
        }
        this.e = EGL14.eglGetDisplay(0);
        if (this.e == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        int[] iArr = new int[b];
        if (EGL14.eglInitialize(this.e, iArr, 0, iArr, a)) {
            EGLConfig b;
            EGLContext eglCreateContext;
            if ((i & b) != 0) {
                b = b(i, 3);
                if (b != null) {
                    eglCreateContext = EGL14.eglCreateContext(this.e, b, eGLContext, new int[]{12440, 3, 12344}, 0);
                    if (EGL14.eglGetError() == 12288) {
                        this.g = b;
                        this.f = eglCreateContext;
                        this.h = 3;
                    }
                }
            }
            if (this.f == EGL14.EGL_NO_CONTEXT) {
                b = b(i, b);
                if (b == null) {
                    throw new RuntimeException("Unable to find a suitable EGLConfig");
                }
                eglCreateContext = EGL14.eglCreateContext(this.e, b, eGLContext, new int[]{12440, b, 12344}, 0);
                b("eglCreateContext");
                this.g = b;
                this.f = eglCreateContext;
                this.h = b;
            }
            iArr = new int[a];
            EGL14.eglQueryContext(this.e, this.f, 12440, iArr, 0);
            Log.d(c, "EGLContext created, client version " + iArr[0]);
            return;
        }
        this.e = null;
        throw new RuntimeException("unable to initialize EGL14");
    }

    private EGLConfig b(int i, int i2) {
        int i3;
        if (i2 >= 3) {
            i3 = 68;
        } else {
            i3 = 4;
        }
        int[] iArr = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, i3, 12344, 0, 12344};
        if ((i & a) != 0) {
            iArr[iArr.length - 3] = d;
            iArr[iArr.length - 2] = a;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[a];
        if (EGL14.eglChooseConfig(this.e, iArr, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[a], 0)) {
            return eGLConfigArr[0];
        }
        Log.w(c, "unable to find RGB8888 / " + i2 + " EGLConfig");
        return null;
    }

    public void a() {
        if (this.e != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglMakeCurrent(this.e, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroyContext(this.e, this.f);
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.e);
        }
        this.e = EGL14.EGL_NO_DISPLAY;
        this.f = EGL14.EGL_NO_CONTEXT;
        this.g = null;
    }

    protected void finalize() {
        try {
            if (this.e != EGL14.EGL_NO_DISPLAY) {
                Log.w(c, "WARNING: EglCore was not explicitly released -- state may be leaked");
                a();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public void a(EGLSurface eGLSurface) {
        EGL14.eglDestroySurface(this.e, eGLSurface);
    }

    public EGLSurface a(Object obj) {
        if ((obj instanceof Surface) || (obj instanceof SurfaceTexture)) {
            int[] iArr = new int[a];
            iArr[0] = 12344;
            EGLSurface eglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.e, this.g, obj, iArr, 0);
            b("eglCreateWindowSurface");
            if (eglCreateWindowSurface != null) {
                return eglCreateWindowSurface;
            }
            throw new RuntimeException("surface was null");
        }
        throw new RuntimeException("invalid surface: " + obj);
    }

    public EGLSurface a(int i, int i2) {
        EGLSurface eglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(this.e, this.g, new int[]{12375, i, 12374, i2, 12344}, 0);
        b("eglCreatePbufferSurface");
        if (eglCreatePbufferSurface != null) {
            return eglCreatePbufferSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public void b(EGLSurface eGLSurface) {
        if (this.e == EGL14.EGL_NO_DISPLAY) {
            Log.d(c, "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.e, eGLSurface, eGLSurface, this.f)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void a(EGLSurface eGLSurface, EGLSurface eGLSurface2) {
        if (this.e == EGL14.EGL_NO_DISPLAY) {
            Log.d(c, "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.e, eGLSurface, eGLSurface2, this.f)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public void b() {
        if (!EGL14.eglMakeCurrent(this.e, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public boolean c(EGLSurface eGLSurface) {
        return EGL14.eglSwapBuffers(this.e, eGLSurface);
    }

    public void a(EGLSurface eGLSurface, long j) {
        EGLExt.eglPresentationTimeANDROID(this.e, eGLSurface, j);
    }

    public boolean d(EGLSurface eGLSurface) {
        return this.f.equals(EGL14.eglGetCurrentContext()) && eGLSurface.equals(EGL14.eglGetCurrentSurface(12377));
    }

    public int a(EGLSurface eGLSurface, int i) {
        int[] iArr = new int[a];
        EGL14.eglQuerySurface(this.e, eGLSurface, i, iArr, 0);
        return iArr[0];
    }

    public int c() {
        return this.h;
    }

    public static void a(String str) {
        EGLDisplay eglGetCurrentDisplay = EGL14.eglGetCurrentDisplay();
        EGLContext eglGetCurrentContext = EGL14.eglGetCurrentContext();
        Log.i(c, "Current EGL (" + str + "): display=" + eglGetCurrentDisplay + ", context=" + eglGetCurrentContext + ", surface=" + EGL14.eglGetCurrentSurface(12377));
    }

    private void b(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }
}
