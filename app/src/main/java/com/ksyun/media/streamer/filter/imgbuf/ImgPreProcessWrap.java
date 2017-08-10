package com.ksyun.media.streamer.filter.imgbuf;

import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class ImgPreProcessWrap {
    private boolean a;
    private int b;
    private long c;

    public static class ImgBufMixerConfig {
        public int alpha;
        public int color;
        public int h;
        public int w;
        public int x;
        public int y;

        public ImgBufMixerConfig(int i, int i2, int i3, int i4, int i5) {
            this.x = i;
            this.y = i2;
            this.w = i3;
            this.h = i4;
            this.color = 0;
            this.alpha = i5;
        }

        public ImgBufMixerConfig(int i, int i2, int i3, int i4, int i5, int i6) {
            this.x = i;
            this.y = i2;
            this.w = i3;
            this.h = i4;
            this.color = i5;
            this.alpha = i6;
        }
    }

    private native ImgBufFrame convertI420ToNv21(long j, ImgBufFrame imgBufFrame);

    private native long create();

    private native ImgBufFrame doBeauty(long j, ImgBufFrame imgBufFrame);

    private native ImgBufFrame doMixer(long j, ImgBufFrame[] imgBufFrameArr, int i, ImgBufMixerConfig[] imgBufMixerConfigArr, int i2);

    private native ImgBufFrame doScale(long j, ImgBufFrame imgBufFrame);

    private native ImgBufFrame doScaleAndConvert2RGBA(long j, ImgBufFrame imgBufFrame);

    private native void releaseInfo(long j);

    private native void setBeautyInfo(long j, int i);

    private native void setTargetSize(long j, int i, int i2);

    private native void updateIsFrontMirror(long j, boolean z);

    public native void debugBeautyFlag(long j, boolean z);

    public native void debugMixerFlag(long j, boolean z);

    public native void debugScaleFlag(long j, boolean z);

    public native void priteByteBuffer(long j, ByteBuffer byteBuffer);

    public ImgPreProcessWrap() {
        this.a = false;
        this.b = 1;
        this.c = 0;
        this.c = create();
    }

    public void a(int i, int i2) {
        setTargetSize(this.c, i, i2);
    }

    public void a(boolean z) {
        if (this.a != z) {
            this.a = z;
            updateIsFrontMirror(this.c, z);
        }
    }

    public void a(int i) {
        if (this.b != i) {
            this.b = i;
            setBeautyInfo(this.c, this.b);
        }
    }

    public ImgBufFrame a(ImgBufFrame imgBufFrame) {
        return doScale(this.c, imgBufFrame);
    }

    public ImgBufFrame b(ImgBufFrame imgBufFrame) {
        return doScaleAndConvert2RGBA(this.c, imgBufFrame);
    }

    public ImgBufFrame c(ImgBufFrame imgBufFrame) {
        return doBeauty(this.c, imgBufFrame);
    }

    public ImgBufFrame a(ImgBufFrame[] imgBufFrameArr, ImgBufMixerConfig[] imgBufMixerConfigArr) {
        return doMixer(this.c, imgBufFrameArr, imgBufFrameArr.length, imgBufMixerConfigArr, imgBufMixerConfigArr.length);
    }

    public ImgBufFrame d(ImgBufFrame imgBufFrame) {
        return convertI420ToNv21(this.c, imgBufFrame);
    }

    public void a() {
        if (this.c != 0) {
            releaseInfo(this.c);
            this.c = 0;
        }
    }

    static {
        LibraryLoader.load();
    }
}
