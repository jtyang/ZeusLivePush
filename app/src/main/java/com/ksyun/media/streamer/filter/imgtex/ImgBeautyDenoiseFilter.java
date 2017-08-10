package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;

public class ImgBeautyDenoiseFilter extends ImgTexFilter {
    private ImgTexFormat a;

    public ImgBeautyDenoiseFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 1);
    }

    public void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.a = imgTexFormat;
    }

    protected void onInitialized() {
        int glGetUniformLocation = GLES20.glGetUniformLocation(this.mProgramId, "textureWidth");
        GlUtil.checkLocation(glGetUniformLocation, "textureWidth");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(this.mProgramId, "textureHigh");
        GlUtil.checkLocation(glGetUniformLocation2, "textureHigh");
        GLES20.glUniform1f(glGetUniformLocation, (float) this.a.width);
        GlUtil.checkGlError("glUniform1f");
        GLES20.glUniform1f(glGetUniformLocation2, (float) this.a.height);
        GlUtil.checkGlError("glUniform1f");
    }
}
