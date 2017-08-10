package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class AudioResample {
    private long a;

    private native void _attachTo(long j, int i, long j2, boolean z);

    private native int _config(long j, int i, int i2);

    private native long _init();

    private native int _read(long j, ByteBuffer byteBuffer, int i);

    private native void _release(long j);

    private native ByteBuffer _resample(long j, ByteBuffer byteBuffer, int i);

    private native void _setOutputFormat(long j, int i, int i2);

    public AudioResample() {
        this.a = _init();
    }

    public AudioResample(AudioBufFormat audioBufFormat, AudioBufFormat audioBufFormat2) {
        this.a = _init();
        a(audioBufFormat2.sampleRate, audioBufFormat2.channels);
        b(audioBufFormat.sampleRate, audioBufFormat.channels);
    }

    public long a() {
        return this.a;
    }

    public void a(int i, int i2) {
        _setOutputFormat(this.a, i, i2);
    }

    public int b(int i, int i2) {
        return _config(this.a, i, i2);
    }

    public void a(int i, long j, boolean z) {
        _attachTo(this.a, i, j, z);
    }

    public int a(ByteBuffer byteBuffer, int i) {
        int _read = _read(this.a, byteBuffer, i);
        if (_read >= 0) {
            byteBuffer.rewind();
            byteBuffer.limit(_read);
        }
        return _read;
    }

    public ByteBuffer a(ByteBuffer byteBuffer) {
        if (byteBuffer == null || byteBuffer.limit() == 0) {
            return null;
        }
        return _resample(this.a, byteBuffer, byteBuffer.limit());
    }

    public void b() {
        _release(this.a);
    }

    static {
        LibraryLoader.load();
    }
}
