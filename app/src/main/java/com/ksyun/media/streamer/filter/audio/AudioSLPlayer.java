package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class AudioSLPlayer implements e {
    private static final String a = "AudioSLPlayer";
    private long b;

    private native void _attachTo(long j, int i, long j2, boolean z);

    private native int _config(long j, int i, int i2, int i3, int i4);

    private native long _init();

    private native int _read(long j, ByteBuffer byteBuffer, int i);

    private native void _release(long j);

    private native void _setMute(long j, boolean z);

    private native void _setTuneLatency(long j, boolean z);

    private native int _start(long j);

    private native int _stop(long j);

    private native int _write(long j, ByteBuffer byteBuffer, int i, boolean z);

    public AudioSLPlayer() {
        this.b = _init();
    }

    public long a() {
        return this.b;
    }

    public int a(int i, int i2, int i3, int i4) {
        return _config(this.b, i, i2, i3, i4);
    }

    public void a(int i, long j, boolean z) {
        _attachTo(this.b, i, j, z);
    }

    public void a(boolean z) {
        _setTuneLatency(this.b, z);
    }

    public void b(boolean z) {
        _setMute(this.b, z);
    }

    public int b() {
        return _start(this.b);
    }

    public int c() {
        return _stop(this.b);
    }

    public int a(ByteBuffer byteBuffer, int i) {
        int _read = _read(this.b, byteBuffer, i);
        if (_read >= 0) {
            byteBuffer.rewind();
            byteBuffer.limit(_read);
        }
        return _read;
    }

    public int a(ByteBuffer byteBuffer) {
        return a(byteBuffer, false);
    }

    public int a(ByteBuffer byteBuffer, boolean z) {
        return _write(this.b, byteBuffer, byteBuffer.limit(), z);
    }

    public void d() {
        _release(this.b);
    }

    static {
        LibraryLoader.load();
    }
}
