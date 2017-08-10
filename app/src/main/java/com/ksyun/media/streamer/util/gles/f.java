package com.ksyun.media.streamer.util.gles;

import android.graphics.SurfaceTexture;
import android.view.Surface;

/* compiled from: EglWindowSurface */
public class f extends e {
    private Surface c;

    public f(d dVar, int i, int i2) {
        super(dVar);
        a(i, i2);
    }

    public f(d dVar, Surface surface) {
        super(dVar);
        a((Object) surface);
        this.c = surface;
    }

    public f(d dVar, SurfaceTexture surfaceTexture) {
        super(dVar);
        a((Object) surfaceTexture);
    }

    public void f() {
        c();
        if (this.c != null) {
            this.c.release();
            this.c = null;
        }
    }

    public void a(d dVar) {
        if (this.c == null) {
            throw new RuntimeException("not yet implemented for SurfaceTexture");
        }
        this.b = dVar;
        a((Object) this.c);
    }
}
