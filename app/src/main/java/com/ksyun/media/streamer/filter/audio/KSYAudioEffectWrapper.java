package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AVConst;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

class KSYAudioEffectWrapper {
    private AudioBufFormat a;
    private long b;

    private native void attachTo(long j, int i, long j2, boolean z);

    private native long native_init();

    private native void native_process(long j, ByteBuffer byteBuffer, int i);

    private native void native_quit(long j);

    private native int native_read(long j, ByteBuffer byteBuffer, int i);

    private native void native_set_audio_format(long j, int i, int i2, int i3);

    private native void native_set_effect_type(long j, int i);

    private native void native_set_pitch_level(long j, int i);

    public KSYAudioEffectWrapper(int i) {
        this.b = 0;
        this.b = native_init();
        native_set_effect_type(this.b, i);
    }

    public void a(AudioBufFormat audioBufFormat) {
        this.a = audioBufFormat;
        native_set_audio_format(this.b, AVConst.getBytesPerSample(audioBufFormat.sampleFormat) * 8, audioBufFormat.sampleRate, audioBufFormat.channels);
    }

    public void a(int i) {
        native_set_effect_type(this.b, i);
    }

    public void b(int i) {
        native_set_pitch_level(this.b, i);
    }

    public void a(ByteBuffer byteBuffer) {
        if (byteBuffer != null && byteBuffer.limit() > 0) {
            native_process(this.b, byteBuffer, byteBuffer.limit());
        }
    }

    public void a() {
        native_quit(this.b);
    }

    public void a(int i, long j, boolean z) {
        attachTo(this.b, i, j, z);
    }

    public long b() {
        return this.b;
    }

    public int a(ByteBuffer byteBuffer, int i) {
        int native_read = native_read(this.b, byteBuffer, i);
        if (native_read >= 0) {
            byteBuffer.rewind();
            byteBuffer.limit(native_read);
        }
        return native_read;
    }

    static {
        LibraryLoader.load();
    }
}
