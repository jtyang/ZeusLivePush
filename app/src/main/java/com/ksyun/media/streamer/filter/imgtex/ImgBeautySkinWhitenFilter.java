package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import com.ksyun.media.player.f;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.publisher.PublisherWrapper;
import com.ksyun.media.streamer.publisher.RtmpPublisher;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;
import java.nio.ByteBuffer;

public class ImgBeautySkinWhitenFilter extends ImgTexFilter {
    byte[] a;
    int[] b;
    int[] c;
    private int d;
    private int[] e;
    private ImgTexFormat f;

    public ImgBeautySkinWhitenFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 4);
        this.e = new int[]{-1};
        this.a = new byte[1024];
        this.b = new int[]{95, 95, 96, 97, 97, 98, 99, 99, 100, RtmpPublisher.INFO_EST_BW_RAISE, RtmpPublisher.INFO_EST_BW_RAISE, RtmpPublisher.INFO_EST_BW_DROP, PublisherWrapper.l, 104, 104, 105, 106, 106, 107, 108, 108, 109, 110, 111, 111, 112, 113, 113, 114, 115, 115, 116, 117, 117, 118, 119, 120, 120, 121, 122, 122, 123, 124, 124, 125, 126, 127, 127, 128, 129, 129, 130, 131, 131, 132, 133, 133, 134, 135, 136, 136, 137, 138, 138, 139, 140, 140, 141, 142, 143, 143, 144, 145, 145, 146, 147, 147, 148, 149, 149, 150, 151, 152, 152, 153, 154, 154, 155, 156, 156, 157, 158, 159, 159, 160, 161, 161, 162, 163, 163, 164, 165, 165, 166, 167, 168, 168, 169, 170, 170, 171, 172, 172, 173, 174, 175, 175, 176, 177, 177, 178, 179, 179, 180, 181, 181, 182, 183, 184, 184, 185, 186, 186, 187, 188, 188, 189, 190, 191, 191, 192, 193, 193, 194, 195, 195, 196, 197, 197, 198, 199, f.f, f.f, 201, 202, 202, 203, 204, 204, 205, 206, 207, 207, 208, 209, 209, 210, 211, 211, 212, 213, 213, 214, 215, 216, 216, 217, 218, 218, 219, 220, 220, 221, 222, 223, 223, 224, 225, 225, 226, 227, 227, 228, 229, 229, 230, 231, 232, 232, 233, 234, 234, 235, 236, 236, 237, 238, 239, 239, 240, 241, 241, 242, 243, 243, 244, 245, 245, 246, 247, 248, 248, 249, 250, 250, 251, 252, 252, 253, 254, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        this.c = new int[]{0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 28, 28, 29, 29, 30, 29, 31, 31, 31, 31, 32, 32, 33, 33, 34, 34, 34, 34, 35, 35, 36, 36, 37, 37, 37, 38, 38, 39, 39, 39, 40, 40, 40, 41, 42, 42, 43, 43, 44, 44, 45, 45, 45, 46, 47, 47, 48, 48, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 59, 60, 60, 61, 62, 63, 63, 64, 65, 66, 67, 68, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, RtmpPublisher.INFO_EST_BW_RAISE, PublisherWrapper.l, 104, 105, 107, 108, 110, 111, 113, 115, 116, 118, 119, 120, 122, 123, 125, 127, 128, 130, 132, 134, 135, 137, 139, 141, 143, 144, 146, 148, 150, 152, 154, 156, 158, 160, 163, 165, 167, 169, 171, 173, 175, 178, 180, 182, 185, 187, 189, 192, 194, 197, 199, 201, 204, 206, 209, 211, 214, 216, 219, 221, 224, 226, 229, 232, 234, 236, 239, 241, 245, 247, 250, 252, 255};
    }

    public void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.f = imgTexFormat;
    }

    protected void onInitialized() {
        this.d = getUniformLocation("curve");
        int uniformLocation = getUniformLocation("texelWidthOffset");
        int uniformLocation2 = getUniformLocation("texelHeightOffset");
        if (this.d >= 0) {
            GLES20.glUniform1f(uniformLocation, 1.0f / ((float) this.f.width));
            GLES20.glUniform1f(uniformLocation2, 1.0f / ((float) this.f.height));
            GLES20.glActiveTexture(33987);
            GLES20.glGenTextures(1, this.e, 0);
            GLES20.glBindTexture(3553, this.e[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            for (uniformLocation = 0; uniformLocation < StreamerConstants.CODEC_ID_AAC; uniformLocation++) {
                this.a[uniformLocation * 4] = (byte) this.b[uniformLocation];
                this.a[(uniformLocation * 4) + 1] = (byte) this.b[uniformLocation];
                this.a[(uniformLocation * 4) + 2] = (byte) this.c[uniformLocation];
                this.a[(uniformLocation * 4) + 3] = (byte) -1;
            }
            GLES20.glTexImage2D(3553, 0, 6408, StreamerConstants.CODEC_ID_AAC, 1, 0, 6408, 5121, ByteBuffer.wrap(this.a));
        }
    }

    protected void onDrawArraysPre() {
        if (this.e[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.e[0]);
            GLES20.glUniform1i(this.d, 3);
        }
    }

    protected void onDrawArraysAfter() {
        if (this.e[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }

    protected void onRelease() {
        super.onRelease();
        GLES20.glDeleteTextures(1, this.e, 0);
        this.e[0] = -1;
    }
}
