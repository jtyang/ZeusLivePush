package com.ksyun.media.streamer.util.gles;

import android.graphics.SurfaceTexture;
import android.view.Surface;

/* compiled from: Egl10WindowSurface */
public class c extends b {
    private Surface c;

    public c(a aVar, int i, int i2) {
        super(aVar);
        a(i, i2);
    }

    public c(a aVar, Surface surface) {
        super(aVar);
        a((Object) surface);
        this.c = surface;
    }

    public c(a aVar, SurfaceTexture surfaceTexture) {
        super(aVar);
        a((Object) surfaceTexture);
    }

    public void f() {
        c();
        if (this.c != null) {
            this.c.release();
            this.c = null;
        }
    }

    public void a(a aVar) {
        if (this.c == null) {
            throw new RuntimeException("not yet implemented for SurfaceTexture");
        }
        this.b = aVar;
        a((Object) this.c);
    }
}
