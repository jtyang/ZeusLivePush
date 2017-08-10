package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class AudioReverbWrap {
    private long a;

    private native void attachTo(long j, int i, long j2, boolean z);

    private native void config(long j, int i, int i2);

    private native long create();

    private native boolean delete(long j);

    private native int process(long j, ByteBuffer byteBuffer, int i);

    private native int read(long j, ByteBuffer byteBuffer, int i);

    private native boolean setLevel(long j, int i);

    public AudioReverbWrap() {
        this.a = create();
    }

    public long a() {
        return this.a;
    }

    public void a(int i, int i2) {
        config(this.a, i, i2);
    }

    public void a(int i, long j, boolean z) {
        attachTo(this.a, i, j, z);
    }

    public void a(int i) {
        setLevel(this.a, i);
    }

    public int a(ByteBuffer byteBuffer, int i) {
        return read(this.a, byteBuffer, i);
    }

    public int a(ByteBuffer byteBuffer) {
        if (byteBuffer == null || byteBuffer.limit() <= 0) {
            return 0;
        }
        return process(this.a, byteBuffer, byteBuffer.limit());
    }

    public void b() {
        delete(this.a);
    }

    static {
        LibraryLoader.load();
    }
}
