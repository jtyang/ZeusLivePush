package com.ksyun.media.streamer.capture.audio;

import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class KSYAudioSLRecord implements a {
    private long a;

    private native long _init(int i, int i2, int i3);

    private native int _read(long j, ByteBuffer byteBuffer, int i);

    private native void _release(long j);

    private native void _setEnableLatencyTest(long j, boolean z);

    private native void _setVolume(long j, float f);

    private native int _start(long j);

    private native int _stop(long j);

    public KSYAudioSLRecord(int i, int i2, int i3) {
        this.a = _init(i, i2, i3);
        if (this.a == 0) {
            throw new IllegalArgumentException("Create OpenGLES record failed!");
        }
    }

    public void a(float f) {
        _setVolume(this.a, f);
    }

    public int a() {
        return _start(this.a);
    }

    public int b() {
        return _stop(this.a);
    }

    public void c() {
        _release(this.a);
    }

    public int a(ByteBuffer byteBuffer, int i) {
        int _read = _read(this.a, byteBuffer, i);
        if (_read > 0) {
            byteBuffer.limit(_read);
            byteBuffer.rewind();
        }
        return _read;
    }

    public long d() {
        return this.a;
    }

    public void a(boolean z) {
        _setEnableLatencyTest(this.a, z);
    }

    static {
        LibraryLoader.load();
    }
}
