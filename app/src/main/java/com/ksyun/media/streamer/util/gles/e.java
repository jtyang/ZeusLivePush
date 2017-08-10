package com.ksyun.media.streamer.util.gles;

import android.annotation.TargetApi;
import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.util.Log;

@TargetApi(17)
/* compiled from: EglSurfaceBase */
public class e {
    protected static final String a = "EglSurfaceBase";
    protected d b;
    private EGLSurface c;
    private int d;
    private int e;

    protected e(d dVar) {
        this.c = EGL14.EGL_NO_SURFACE;
        this.d = -1;
        this.e = -1;
        this.b = dVar;
    }

    public void a(Object obj) {
        if (this.c != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.c = this.b.a(obj);
        this.d = this.b.a(this.c, 12375);
        this.e = this.b.a(this.c, 12374);
    }

    public void a(int i, int i2) {
        if (this.c != EGL14.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.c = this.b.a(i, i2);
        this.d = i;
        this.e = i2;
    }

    public int a() {
        return this.d;
    }

    public int b() {
        return this.e;
    }

    public void c() {
        this.b.a(this.c);
        this.c = EGL14.EGL_NO_SURFACE;
        this.e = -1;
        this.d = -1;
    }

    public void d() {
        this.b.b(this.c);
    }

    public void a(e eVar) {
        this.b.a(this.c, eVar.c);
    }

    public boolean e() {
        boolean c = this.b.c(this.c);
        if (!c) {
            Log.d(a, "WARNING: swapBuffers() failed");
        }
        return c;
    }

    public void a(long j) {
        this.b.a(this.c, j);
    }
}
