package com.ksyun.media.streamer.util.gles;

import android.util.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLSurface;

/* compiled from: Egl10SurfaceBase */
public class b {
    protected static final String a = "Egl10SurfaceBase";
    protected a b;
    private EGLSurface c;
    private int d;
    private int e;

    protected b(a aVar) {
        this.c = EGL10.EGL_NO_SURFACE;
        this.d = -1;
        this.e = -1;
        this.b = aVar;
    }

    public void a(Object obj) {
        if (this.c != EGL10.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.c = this.b.a(obj);
        this.d = this.b.a(this.c, 12375);
        this.e = this.b.a(this.c, 12374);
    }

    public void a(int i, int i2) {
        if (this.c != EGL10.EGL_NO_SURFACE) {
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
        this.c = EGL10.EGL_NO_SURFACE;
        this.e = -1;
        this.d = -1;
    }

    public void d() {
        this.b.b(this.c);
    }

    public void a(b bVar) {
        this.b.a(this.c, bVar.c);
    }

    public boolean e() {
        boolean c = this.b.c(this.c);
        if (!c) {
            Log.d(a, "WARNING: swapBuffers() failed");
        }
        return c;
    }
}
