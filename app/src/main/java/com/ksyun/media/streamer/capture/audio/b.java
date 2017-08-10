package com.ksyun.media.streamer.capture.audio;

import com.ksyun.media.streamer.logstats.StatsConstant;
import java.nio.ByteBuffer;

/* compiled from: KSYAudioDummyRecord */
public class b implements a {
    private int a;
    private int b;
    private int c;
    private long d;
    private int e;

    public b(int i, int i2, int i3) {
        this.a = i;
        this.b = i2 * 2;
        this.c = (i3 * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD) / i;
        this.e = 0;
    }

    public void a(float f) {
    }

    public int a() {
        this.e = 0;
        this.d = System.nanoTime() / 1000;
        return 0;
    }

    public int b() {
        return 0;
    }

    public void c() {
    }

    public int a(ByteBuffer byteBuffer, int i) {
        if (byteBuffer == null || byteBuffer.capacity() < i) {
            return 0;
        }
        while (this.e < i) {
            try {
                Thread.sleep((long) this.c);
                long nanoTime = System.nanoTime() / 1000;
                this.e += a(nanoTime - this.d);
                this.d = nanoTime;
            } catch (InterruptedException e) {
                return 0;
            }
        }
        this.e -= i;
        for (int i2 = 0; i2 < i; i2++) {
            byteBuffer.put(i2, (byte) 0);
        }
        byteBuffer.limit(i);
        byteBuffer.rewind();
        return i;
    }

    public long d() {
        return 0;
    }

    public void a(boolean z) {
    }

    private int a(long j) {
        return (int) (((((long) this.a) * j) / 1000000) * ((long) this.b));
    }
}
