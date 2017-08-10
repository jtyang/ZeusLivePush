package com.ksyun.media.streamer.filter.audio;

import android.util.Log;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class APMWrapper {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int e = 48000;
    public static final int f = 2;
    public static final int g = 1;
    public static boolean h = false;
    private static final String i = "APMWrapper";
    private static final int j = 0;
    private long k;
    private AudioBufFormat l;

    private native void attachTo(long j, int i, long j2, boolean z);

    private native int config(long j, int i, int i2);

    private native long create();

    private native int enableHighPassFilter(long j, boolean z);

    private native int enableNs(long j, boolean z);

    private native int enableVAD(long j, boolean z);

    private native ByteBuffer processStream(long j, ByteBuffer byteBuffer, int i);

    private native int read(long j, ByteBuffer byteBuffer, int i);

    private native void release(long j);

    private native int setNsLevel(long j, int i);

    private native int setVADLikelihood(long j, int i);

    static {
        h = true;
        LibraryLoader.load();
        try {
            System.loadLibrary("ksyapm");
        } catch (UnsatisfiedLinkError e) {
            h = false;
            Log.e(i, "No libksyapm.so! Please check ");
        }
    }

    public APMWrapper() {
        this.k = 0;
        if (h) {
            this.k = create();
            this.l = new AudioBufFormat(g, e, f);
            if (this.k == 0) {
                Log.e(i, "apm create failed \uff0cret " + this.k);
                return;
            }
            return;
        }
        Log.e(i, "native library not loaded!");
    }

    public long a() {
        return this.k;
    }

    public AudioBufFormat b() {
        return this.l;
    }

    public ByteBuffer a(ByteBuffer byteBuffer) {
        if (this.k == 0) {
            return null;
        }
        return processStream(this.k, byteBuffer, byteBuffer.limit());
    }

    private int b(boolean z) {
        if (this.k == 0) {
            return -1;
        }
        return enableHighPassFilter(this.k, z);
    }

    public int a(boolean z) {
        if (this.k == 0) {
            return -1;
        }
        return enableNs(this.k, z);
    }

    public int a(int i) {
        if (this.k == 0) {
            return -1;
        }
        return setNsLevel(this.k, i);
    }

    private int c(boolean z) {
        if (this.k == 0) {
            return -1;
        }
        return enableVAD(this.k, z);
    }

    private int b(int i) {
        if (this.k == 0) {
            return -1;
        }
        return setVADLikelihood(this.k, i);
    }

    public int a(int i, int i2) {
        if (this.k == 0) {
            return -1;
        }
        this.l.sampleRate = i;
        this.l.channels = i2;
        return config(this.k, i, i2);
    }

    public void a(int i, long j, boolean z) {
        attachTo(this.k, i, j, z);
    }

    public int a(ByteBuffer byteBuffer, int i) {
        int read = read(this.k, byteBuffer, i);
        if (read >= 0) {
            byteBuffer.rewind();
            byteBuffer.limit(read);
        }
        return read;
    }

    public void c() {
        if (this.k != 0) {
            release(this.k);
        }
    }
}
