package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;

@TargetApi(14)
/* compiled from: TextureMediaPlayer */
public class h extends g implements IMediaPlayer, c {
    private SurfaceTexture b;
    private d c;

    public h(IMediaPlayer iMediaPlayer) {
        super(iMediaPlayer);
    }

    public void c() {
        if (this.b != null) {
            if (this.c != null) {
                this.c.a(this.b);
            } else {
                this.b.release();
            }
            this.b = null;
        }
    }

    public void reset() {
        super.reset();
        c();
    }

    public void release() {
        super.release();
        c();
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (this.b == null) {
            super.setDisplay(surfaceHolder);
        }
    }

    public void setSurface(Surface surface) {
        if (this.b == null) {
            super.setSurface(surface);
        }
    }

    public void a(SurfaceTexture surfaceTexture) {
        if (this.b != surfaceTexture) {
            c();
            this.b = surfaceTexture;
            if (surfaceTexture == null) {
                super.setSurface(null);
            } else {
                super.setSurface(new Surface(surfaceTexture));
            }
        }
    }

    public SurfaceTexture a() {
        return this.b;
    }

    public void a(d dVar) {
        this.c = dVar;
    }
}
