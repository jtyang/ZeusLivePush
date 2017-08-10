package com.ksyun.media.streamer.framework;

public class ImgBufFormat {
    public static final int FMT_ARGB = 4;
    public static final int FMT_AVC = 256;
    public static final int FMT_BGR8 = 6;
    public static final int FMT_GIF = 258;
    public static final int FMT_HEVC = 257;
    public static final int FMT_I420 = 3;
    public static final int FMT_NV21 = 1;
    public static final int FMT_OPAQUE = 0;
    public static final int FMT_RGBA = 5;
    public static final int FMT_YV12 = 2;
    public int format;
    public int height;
    public int orientation;
    public int[] stride;
    public int strideNum;
    public int width;

    public ImgBufFormat(int i, int i2, int i3, int i4) {
        this.format = i;
        this.width = i2;
        this.height = i3;
        this.orientation = i4;
        this.stride = null;
        this.strideNum = FMT_OPAQUE;
    }

    public ImgBufFormat(int i, int i2, int i3, int i4, int[] iArr) {
        this.format = i;
        this.width = i2;
        this.height = i3;
        this.orientation = i4;
        this.stride = iArr;
        if (iArr != null) {
            this.strideNum = iArr.length;
        }
    }

    public ImgBufFormat() {
        this.format = -1;
        this.width = FMT_OPAQUE;
        this.height = FMT_OPAQUE;
        this.orientation = FMT_OPAQUE;
        this.stride = null;
        this.strideNum = FMT_OPAQUE;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ImgBufFormat)) {
            return false;
        }
        ImgBufFormat imgBufFormat = (ImgBufFormat) obj;
        if (this.format == imgBufFormat.format && this.width == imgBufFormat.width && this.height == imgBufFormat.height && this.orientation == imgBufFormat.orientation) {
            return true;
        }
        return false;
    }
}
