package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;
import java.nio.FloatBuffer;

public class ImgBeautySoftExtFilter extends ImgTexFilter {
    private ImgTexFormat a;

    public ImgBeautySoftExtFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 7);
    }

    public void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.a = imgTexFormat;
    }

    protected void onInitialized() {
        GLES20.glUniform2fv(getUniformLocation("singleStepOffset"), 1, FloatBuffer.wrap(new float[]{2.0f / ((float) this.a.width), 2.0f / ((float) this.a.height)}));
        GlUtil.checkGlError("glUniform2fv");
    }
}
