package com.ksyun.media.streamer.encoder;

import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class AVEncoderWrapper {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 0;
    public static final int e = 1;
    public static final int f = 2;
    public static final int g = 3;
    public static final int h = 4;
    private static final String i = "AVEncoderWrapper";
    private static final int j = 1;
    private static final int k = 2;
    private long l;
    private int m;
    private a n;

    public interface a {
        void onEncoded(long j, ByteBuffer byteBuffer, long j2, long j3, int i);
    }

    private native int _adjust_bitrate(long j, int i);

    private native int _close(long j);

    private native int _encode(long j, int i, ByteBuffer byteBuffer, int i2, long j2, int i3);

    private native long _init();

    private native int _open_audio(long j, int i, int i2, int i3, int i4, int i5, int i6);

    private native int _open_video(long j, int i, int i2, int i3, int i4, int i5, float f, float f2, int i6, int i7, int i8, boolean z);

    private native void _release(long j);

    public AVEncoderWrapper() {
        this.l = _init();
    }

    public void a(a aVar) {
        this.n = aVar;
    }

    public int a(int i, int i2, int i3, int i4, int i5, int i6) {
        this.m = j;
        return _open_audio(this.l, i, i2, i3, i4, i5, i6);
    }

    public int a(int i, int i2, int i3, int i4, int i5, float f, float f2, int i6, int i7, int i8, boolean z) {
        this.m = k;
        return _open_video(this.l, i, i2, i3, i4, i5, f, f2, i6, i7, i8, z);
    }

    public int a(int i) {
        return _adjust_bitrate(this.l, i);
    }

    public int a(ByteBuffer byteBuffer, long j, int i) {
        if (byteBuffer == null) {
            return _encode(this.l, this.m, null, d, j, i);
        }
        return _encode(this.l, this.m, byteBuffer, byteBuffer.limit(), j, i);
    }

    public int a() {
        return _close(this.l);
    }

    public void b() {
        _release(this.l);
    }

    private void onEncoded(long j, ByteBuffer byteBuffer, long j2, long j3, int i) {
        if (this.n != null) {
            this.n.onEncoded(j, byteBuffer, j2, j3, i);
        }
    }

    static {
        LibraryLoader.load();
    }
}
