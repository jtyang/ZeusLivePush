package com.ksyun.media.streamer.capture.audio;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/* compiled from: KSYAudioRecord */
public class c implements a {
    private static final String a = "KSYAudioRecord";
    private AudioRecord b;
    private float c;
    private boolean d;
    private long e;

    public c(int i, int i2, int i3) {
        this.c = 1.0f;
        this.b = new AudioRecord(1, i, i2 == 1 ? 16 : 12, 2, (i3 * i2) * 2);
    }

    public void a(float f) {
        this.c = f;
    }

    public int a() {
        try {
            this.b.startRecording();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int b() {
        try {
            this.b.stop();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void c() {
        this.b.release();
    }

    public int a(ByteBuffer byteBuffer, int i) {
        int i2 = 0;
        int read = this.b.read(byteBuffer, i);
        if (read <= 0) {
            return read;
        }
        byteBuffer.limit(read);
        byteBuffer.rewind();
        if (this.d) {
            int i3;
            long nanoTime = System.nanoTime() / 1000;
            ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
            for (i3 = 0; i3 < asShortBuffer.limit(); i3++) {
                if (asShortBuffer.get(i3) >= (short) 8191) {
                    Log.i(a, "Latency measured : " + ((int) ((nanoTime - this.e) / 1000)) + " ms");
                    break;
                }
            }
            for (i3 = 0; i3 < asShortBuffer.limit(); i3++) {
                asShortBuffer.put(i3, (short) 0);
            }
            if (nanoTime - this.e >= 5000000) {
                this.e = nanoTime;
                asShortBuffer.put(0, Short.MAX_VALUE);
            }
            asShortBuffer.rewind();
        } else if (this.c != 1.0f) {
            ShortBuffer asShortBuffer2 = byteBuffer.asShortBuffer();
            while (i2 < asShortBuffer2.limit()) {
                asShortBuffer2.put(i2, a((int) (((float) asShortBuffer2.get(i2)) * this.c)));
                i2++;
            }
            asShortBuffer2.rewind();
        }
        return read;
    }

    public long d() {
        return 0;
    }

    public void a(boolean z) {
        this.d = z;
    }

    private short a(int i) {
        if (((32768 + i) & -65536) != 0) {
            return (short) ((i >> 31) ^ 32767);
        }
        return (short) i;
    }
}
