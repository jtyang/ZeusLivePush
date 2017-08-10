package com.ksyun.media.streamer.filter.audio;

import android.media.AudioTrack;
import java.nio.ByteBuffer;

/* compiled from: AudioTrackPlayer */
public class d implements e {
    private AudioTrack a;
    private short[] b;
    private boolean c;
    private boolean d;

    public long a() {
        return 0;
    }

    public synchronized int a(int i, int i2, int i3, int i4) {
        if (this.a != null) {
            this.a.release();
        }
        int i5 = i2 == 1 ? 4 : 12;
        this.a = new AudioTrack(3, i, i5, 2, AudioTrack.getMinBufferSize(i, i5, 2), 1);
        if (this.c) {
            this.a.setStereoVolume(0.0f, 0.0f);
        }
        if (this.d) {
            this.a.play();
        }
        return 0;
    }

    public synchronized void b(boolean z) {
        if (this.a != null) {
            float f = z ? 0.0f : 1.0f;
            this.a.setStereoVolume(f, f);
        }
        this.c = z;
    }

    public synchronized int b() {
        if (this.a != null) {
            this.a.play();
        }
        this.d = true;
        return 0;
    }

    public synchronized int c() {
        if (this.a != null) {
            this.a.stop();
        }
        this.d = false;
        this.b = null;
        return 0;
    }

    public synchronized int a(ByteBuffer byteBuffer) {
        int i = 0;
        synchronized (this) {
            if (byteBuffer != null) {
                if (this.a != null) {
                    i = byteBuffer.limit() / 2;
                    if (this.b == null || this.b.length < i) {
                        this.b = new short[i];
                    }
                    byteBuffer.asShortBuffer().get(this.b, 0, i);
                    i = this.a.write(this.b, 0, i);
                }
            }
        }
        return i;
    }

    public synchronized void d() {
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
        this.b = null;
    }
}
