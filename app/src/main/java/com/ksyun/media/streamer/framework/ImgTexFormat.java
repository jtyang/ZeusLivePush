package com.ksyun.media.streamer.framework;

public class ImgTexFormat {
    public static final int COLOR_EXTERNAL_OES = 3;
    public static final int COLOR_RGBA = 1;
    public static final int COLOR_YUVA = 2;
    public final int colorFormat;
    public final int height;
    public final int width;

    public ImgTexFormat(int i, int i2, int i3) {
        this.colorFormat = i;
        this.width = i2;
        this.height = i3;
    }
}
