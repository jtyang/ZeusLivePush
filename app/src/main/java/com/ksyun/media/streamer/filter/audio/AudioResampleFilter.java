package com.ksyun.media.streamer.filter.audio;

import android.support.annotation.NonNull;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import java.nio.ByteBuffer;

public class AudioResampleFilter extends AudioFilterBase {
    private AudioResample a;
    private AudioBufFormat b;

    public AudioResampleFilter() {
        this.a = new AudioResample();
    }

    public void setOutFormat(@NonNull AudioBufFormat audioBufFormat) {
        this.b = audioBufFormat;
        this.a.a(audioBufFormat.sampleRate, audioBufFormat.channels);
    }

    protected long getNativeInstance() {
        return this.a.a();
    }

    public AudioBufFormat getOutFormat() {
        return this.b;
    }

    protected void attachTo(int i, long j, boolean z) {
        this.a.a(i, j, z);
    }

    protected int readNative(ByteBuffer byteBuffer, int i) {
        return this.a.a(byteBuffer, i);
    }

    protected AudioBufFormat doFormatChanged(AudioBufFormat audioBufFormat) {
        if (this.b == null) {
            throw new IllegalArgumentException("you must call setOutFormat first");
        }
        this.a.b(audioBufFormat.sampleRate, audioBufFormat.channels);
        return this.b;
    }

    protected AudioBufFrame doFilter(AudioBufFrame audioBufFrame) {
        if (audioBufFrame.buf == null) {
            return audioBufFrame;
        }
        return new AudioBufFrame(this.b, this.a.a(audioBufFrame.buf), audioBufFrame.pts);
    }

    protected void doRelease() {
        if (this.a != null) {
            this.a.b();
            this.a = null;
        }
    }
}
