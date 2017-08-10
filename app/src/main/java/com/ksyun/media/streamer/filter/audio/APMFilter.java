package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import java.nio.ByteBuffer;

public class APMFilter extends AudioFilterBase {
    private APMWrapper a;

    public APMFilter() {
        this.a = new APMWrapper();
    }

    public int enableNs(boolean z) {
        return this.a.a(z);
    }

    public int setNsLevel(int i) {
        return this.a.a(i);
    }

    protected AudioBufFormat doFormatChanged(AudioBufFormat audioBufFormat) {
        if (audioBufFormat == null) {
            return null;
        }
        this.a.a(audioBufFormat.sampleRate, audioBufFormat.channels);
        return audioBufFormat;
    }

    protected AudioBufFrame doFilter(AudioBufFrame audioBufFrame) {
        ByteBuffer a = this.a.a(audioBufFrame.buf);
        return a == null ? audioBufFrame : new AudioBufFrame(this.a.b(), a, audioBufFrame.pts);
    }

    protected void doRelease() {
        if (this.a != null) {
            this.a.c();
            this.a = null;
        }
    }

    protected void attachTo(int i, long j, boolean z) {
        this.a.a(i, j, z);
    }

    protected int readNative(ByteBuffer byteBuffer, int i) {
        return this.a.a(byteBuffer, i);
    }

    protected long getNativeInstance() {
        return this.a.a();
    }
}
