package com.ksyun.media.streamer.framework;

import android.opengl.Matrix;

public class ImgTexFrame extends AVFrameBase {
    public static final int NO_TEXTURE = -1;
    private static final float[] a;
    public ImgTexFormat format;
    public final float[] texMatrix;
    public int textureId;

    static {
        a = new float[16];
    }

    public ImgTexFrame(ImgTexFormat imgTexFormat, int i, float[] fArr, long j) {
        this.format = imgTexFormat;
        this.textureId = i;
        this.pts = j;
        this.dts = j;
        this.flags = 0;
        if (fArr == null || fArr.length != 16) {
            this.texMatrix = a;
            Matrix.setIdentityM(this.texMatrix, 0);
            return;
        }
        this.texMatrix = fArr;
    }
}
