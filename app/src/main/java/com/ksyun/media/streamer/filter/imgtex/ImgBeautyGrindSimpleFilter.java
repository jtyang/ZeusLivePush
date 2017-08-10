package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;

public class ImgBeautyGrindSimpleFilter extends ImgTexFilter {
    private int a;
    private int b;
    private int c;
    private final Object d;
    private float[] e;
    private float f;
    private ImgTexFormat g;

    public ImgBeautyGrindSimpleFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 11);
        this.d = new Object();
        this.f = 1.0f;
    }

    public boolean isGrindRatioSupported() {
        return true;
    }

    public void setGrindRatio(float f) {
        super.setGrindRatio(f);
        synchronized (this.d) {
            if (f < 0.2f) {
                this.e = new float[]{1.0f, 1.0f, 0.15f, 0.15f};
            } else if (f < 0.4f) {
                this.e = new float[]{0.8f, 0.9f, 0.2f, 0.2f};
            } else if (f < 0.6f) {
                this.e = new float[]{0.6f, 0.8f, 0.25f, 0.25f};
            } else if (f < 0.8f) {
                this.e = new float[]{0.4f, 0.7f, 0.38f, 0.3f};
            } else {
                this.e = new float[]{0.33f, 0.63f, 0.4f, 0.35f};
            }
        }
    }

    public void setScaleLumance(float f) {
        this.f = f;
    }

    protected void onFormatChanged(ImgTexFormat imgTexFormat) {
        super.onFormatChanged(imgTexFormat);
        this.g = imgTexFormat;
    }

    protected void onInitialized() {
        this.a = getUniformLocation("singleStepOffset");
        this.b = getUniformLocation("params");
        this.c = getUniformLocation("scaleLumance");
        GLES20.glUniform1f(this.c, this.f);
        GLES20.glUniform2f(this.a, 2.0f / ((float) this.g.width), 2.0f / ((float) this.g.height));
        setGrindRatio(this.mGrindRatio);
    }

    protected void onDrawArraysPre() {
        synchronized (this.d) {
            GLES20.glUniform4fv(this.b, 1, this.e, 0);
        }
    }
}
