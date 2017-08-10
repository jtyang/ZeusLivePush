package com.ksyun.media.streamer.framework;

import java.nio.ByteBuffer;

public class AudioBufFrame extends AVBufFrame {
    public AudioBufFormat format;

    public AudioBufFrame(AudioBufFormat audioBufFormat, ByteBuffer byteBuffer, long j) {
        this.format = audioBufFormat;
        this.buf = byteBuffer;
        this.dts = j;
        this.pts = j;
        this.flags = 0;
    }

    public AudioBufFrame(AudioBufFrame audioBufFrame) {
        this.format = audioBufFrame.format;
        this.buf = audioBufFrame.buf;
        this.dts = audioBufFrame.dts;
        this.pts = audioBufFrame.pts;
        this.flags = audioBufFrame.flags;
    }
}
