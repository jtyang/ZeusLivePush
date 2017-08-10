package com.ksyun.media.streamer.framework;

import java.nio.ByteBuffer;

public class ImgBufFrame extends AVBufFrame {
    public ImgBufFormat format;

    public ImgBufFrame(ImgBufFormat imgBufFormat, ByteBuffer byteBuffer, long j) {
        this.format = imgBufFormat;
        this.buf = byteBuffer;
        this.pts = j;
        this.dts = j;
    }

    public ImgBufFrame(ImgBufFrame imgBufFrame) {
        this.format = imgBufFrame.format;
        this.buf = imgBufFrame.buf;
        this.pts = imgBufFrame.pts;
        this.dts = imgBufFrame.dts;
        this.flags = imgBufFrame.flags;
        this.avPacketOpaque = imgBufFrame.avPacketOpaque;
    }

    private ImgBufFrame() {
        this.format = null;
        this.buf = null;
        this.pts = 0;
        this.dts = 0;
    }
}
