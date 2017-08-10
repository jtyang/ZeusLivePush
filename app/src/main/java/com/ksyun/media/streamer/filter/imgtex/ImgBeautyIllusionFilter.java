package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;
import java.nio.FloatBuffer;

public class ImgBeautyIllusionFilter extends ImgTexFilter {
    private int a;
    private final Object b;
    private float[] c;
    private ImgTexFormat d;

    public ImgBeautyIllusionFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 3);
        this.b = new Object();
    }

    public boolean isGrindRatioSupported() {
        return true;
    }

    public void setGrindRatio(float f) {
        super.setGrindRatio(f);
        synchronized (this.b) {
            if (f < 0.2f) {
                this.c = new float[]{1.0f, 1.0f, 0.15f, 0.15f};
            } else if (f < 0.4f) {
                this.c = new float[]{0.8f, 0.9f, 0.2f, 0.2f};
            } else if (f < 0.6f) {
                this.c = new float[]{0.6f, 0.8f, 0.25f, 0.25f};
            } else if (f < 0.8f) {
                this.c = new float[]{0.4f, 0.7f, 0.38f, 0.3f};
            } else {
                this.c = new float[]{0.33f, 0.63f, 0.4f, 0.35f};
            }
        }
    }

    public void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.d = imgTexFormat;
    }

    protected void onInitialized() {
        int uniformLocation = getUniformLocation("singleStepOffset");
        this.a = getUniformLocation("params");
        setGrindRatio(this.mGrindRatio);
        GLES20.glUniform2fv(uniformLocation, 1, FloatBuffer.wrap(new float[]{2.0f / ((float) this.d.width), 2.0f / ((float) this.d.height)}));
        GlUtil.checkGlError("glUniform2fv");
    }

    protected void onDrawArraysPre() {
        synchronized (this.b) {
            GLES20.glUniform4fv(this.a, 1, this.c, 0);
        }
    }
}
