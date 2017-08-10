package com.ksyun.media.streamer.publisher;

import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class PublisherWrapper {
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 0;
    public static final int d = 1;
    public static final int e = -1010;
    public static final int f = -1011;
    public static final int g = -1012;
    public static final int h = -1020;
    public static final int i = 100;
    public static final int j = 101;
    public static final int k = 102;
    public static final int l = 103;
    public static final int m = 1;
    public static final int n = 2;
    public static final int o = 3;
    public static final int p = 4;
    public static final int q = 5;
    public static final int r = 6;
    public static final int s = 7;
    public static final int t = 8;
    public static final int u = 9;
    public static final int v = 10;
    private static final String w = "PublisherWrapper";
    private long x;
    private a y;

    public interface a {
        void a(int i, long j);
    }

    private native void _abort(long j);

    private native int _add_audio_track(long j, int i, int i2, int i3, int i4, int i5, ByteBuffer byteBuffer, int i6);

    private native void _add_meta_option(long j, String str, String str2);

    private native int _add_video_track(long j, int i, int i2, int i3, float f, int i4, ByteBuffer byteBuffer, int i5);

    private native long _get_property_long(long j, int i);

    private native String _get_property_string(long j, int i);

    private native long _init();

    private native void _release(long j);

    private native void _set_audio_only(long j, boolean z);

    private native void _set_bw_est_config(long j, int i, int i2, int i3, int i4, int i5);

    private native void _set_video_only(long j, boolean z);

    private native int _start(long j, String str);

    private native int _stop(long j);

    private native int _write_frame(long j, int i, long j2, ByteBuffer byteBuffer, int i2, long j3, long j4, int i3);

    public PublisherWrapper() {
        this.x = 0;
        this.x = _init();
    }

    public void a(a aVar) {
        this.y = aVar;
    }

    public void a(int i, int i2, int i3, int i4, int i5) {
        _set_bw_est_config(this.x, i, i2, i3, i4, i5);
    }

    public void a(boolean z) {
        _set_audio_only(this.x, z);
    }

    public void b(boolean z) {
        _set_video_only(this.x, z);
    }

    public void a(String str, String str2) {
        _add_meta_option(this.x, str, str2);
    }

    public int a(int i, int i2, int i3, float f, int i4, ByteBuffer byteBuffer) {
        int i5;
        long j = this.x;
        if (byteBuffer == null) {
            i5 = c;
        } else {
            i5 = byteBuffer.limit();
        }
        return _add_video_track(j, i, i2, i3, f, i4, byteBuffer, i5);
    }

    public int a(int i, int i2, int i3, int i4, int i5, ByteBuffer byteBuffer) {
        int i6;
        long j = this.x;
        if (byteBuffer == null) {
            i6 = c;
        } else {
            i6 = byteBuffer.limit();
        }
        return _add_audio_track(j, i, i2, i3, i4, i5, byteBuffer, i6);
    }

    public int a(int i) {
        return (int) _get_property_long(this.x, i);
    }

    public long b(int i) {
        return _get_property_long(this.x, i);
    }

    public String c(int i) {
        return _get_property_string(this.x, i);
    }

    public void a() {
        _abort(this.x);
    }

    public synchronized void b() {
        if (this.x != 0) {
            _release(this.x);
            this.x = 0;
        }
    }

    private void onEvent(int i, long j) {
        if (this.y != null) {
            this.y.a(i, j);
        }
    }

    public int a(String str) {
        return _start(this.x, str);
    }

    public int a(int i, long j, ByteBuffer byteBuffer, long j2, long j3, int i2) {
        return _write_frame(this.x, i, j, byteBuffer, byteBuffer.limit(), j2, j3, i2);
    }

    public int c() {
        return _stop(this.x);
    }

    static {
        LibraryLoader.load();
    }
}
