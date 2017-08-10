package com.ksyun.media.streamer.filter.imgtex;

import android.graphics.RectF;
import android.opengl.GLES20;
import android.util.Log;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.util.gles.GLProgramLoadException;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import java.nio.FloatBuffer;

public class ImgTexMixer extends ImgTexFilterBase {
    public static final int SCALING_MODE_BEST_FIT = 1;
    public static final int SCALING_MODE_CENTER_CROP = 2;
    public static final int SCALING_MODE_FULL_FILL = 0;
    private static final String a = "ImgTexMixer";
    private static final String b = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform  float alpha;\nvoid main() {\n vec4 tc = texture2D(sTexture, vTextureCoord); tc = tc * alpha; gl_FragColor = tc;\n}\n";
    private static final int c = 8;
    private int d;
    private int e;
    private ImgTexFormat[] f;
    private RectF[] g;
    private RectF[] h;
    private float[] i;
    private int[] j;
    private boolean[] k;
    private ImgTexFormat l;
    private FloatBuffer[] m;
    protected String mFragmentShader;
    protected String mFragmentShaderOES;
    protected int mProgram;
    protected int mProgramOES;
    protected String mVertexShader;
    private FloatBuffer[] n;

    public ImgTexMixer(GLRender gLRender) {
        super(gLRender);
        this.mVertexShader = GlUtil.BASE_VERTEX_SHADER;
        this.mFragmentShader = "uniform sampler2D sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform  float alpha;\nvoid main() {\n vec4 tc = texture2D(sTexture, vTextureCoord); tc = tc * alpha; gl_FragColor = tc;\n}\n";
        this.mFragmentShaderOES = "#extension GL_OES_EGL_image_external : require\nuniform samplerExternalOES sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform  float alpha;\nvoid main() {\n vec4 tc = texture2D(sTexture, vTextureCoord); tc = tc * alpha; gl_FragColor = tc;\n}\n";
        this.f = new ImgTexFormat[getSinkPinNum()];
        this.g = new RectF[getSinkPinNum()];
        this.h = new RectF[getSinkPinNum()];
        this.i = new float[getSinkPinNum()];
        this.j = new int[getSinkPinNum()];
        this.k = new boolean[getSinkPinNum()];
        this.m = new FloatBuffer[getSinkPinNum()];
        this.n = new FloatBuffer[getSinkPinNum()];
        for (int i = SCALING_MODE_FULL_FILL; i < getSinkPinNum(); i += SCALING_MODE_BEST_FIT) {
            this.g[i] = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
            this.h[i] = new RectF(this.g[i]);
            this.i[i] = 1.0f;
            this.j[i] = SCALING_MODE_FULL_FILL;
            this.m[i] = TexTransformUtil.getTexCoordsBuf();
            this.n[i] = TexTransformUtil.getVertexCoordsBuf();
        }
    }

    public void setTargetSize(int i, int i2) {
        this.d = i;
        this.e = i2;
        this.l = new ImgTexFormat(SCALING_MODE_BEST_FIT, this.d, this.e);
        for (int i3 = SCALING_MODE_FULL_FILL; i3 < getSinkPinNum(); i3 += SCALING_MODE_BEST_FIT) {
            a(i3);
        }
    }

    public void setRenderRect(int i, RectF rectF, float f) {
        if (i < getSinkPinNum()) {
            this.g[i].set(rectF);
            this.h[i].set(rectF);
            this.i[i] = f;
            a(i);
        }
    }

    public void setRenderRect(int i, float f, float f2, float f3, float f4, float f5) {
        if (i < getSinkPinNum()) {
            this.g[i].set(f, f2, f + f3, f2 + f4);
            this.h[i].set(this.g[i]);
            this.i[i] = f5;
            a(i);
        }
    }

    public RectF getRenderRect(int i) {
        if (i < getSinkPinNum()) {
            return this.g[i];
        }
        return null;
    }

    public void setScalingMode(int i, int i2) {
        if (i < getSinkPinNum()) {
            this.j[i] = i2;
            a(i);
        }
    }

    public void setMirror(int i, boolean z) {
        if (i < this.k.length) {
            this.k[i] = z;
            a(i);
        }
    }

    public int getSinkPinNum() {
        return c;
    }

    protected ImgTexFormat getSrcPinFormat() {
        if (this.l == null) {
            Log.w(a, "you must call setTargetSize");
        }
        return this.l;
    }

    public void onFormatChanged(int i, ImgTexFormat imgTexFormat) {
        this.f[i] = imgTexFormat;
        a(i);
    }

    public void onDraw(ImgTexFrame[] imgTexFrameArr) {
        GLES20.glBlendFunc(SCALING_MODE_BEST_FIT, 771);
        for (int i = SCALING_MODE_FULL_FILL; i < imgTexFrameArr.length; i += SCALING_MODE_BEST_FIT) {
            if (imgTexFrameArr[i] != null) {
                a(imgTexFrameArr[i], this.m[i], this.n[i], this.i[i]);
            }
        }
        GLES20.glBlendFunc(770, 771);
    }

    protected void onRelease() {
        if (this.mProgram != 0) {
            GLES20.glDeleteProgram(this.mProgram);
            this.mProgram = SCALING_MODE_FULL_FILL;
        }
        if (this.mProgramOES != 0) {
            GLES20.glDeleteProgram(this.mProgramOES);
            this.mProgramOES = SCALING_MODE_FULL_FILL;
        }
    }

    private void a(ImgTexFrame imgTexFrame, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, float f) {
        float[] fArr = imgTexFrame.texMatrix;
        int i = imgTexFrame.textureId;
        if (i != -1) {
            int i2;
            int i3;
            if (!this.mInited) {
                this.mProgram = SCALING_MODE_FULL_FILL;
                this.mProgramOES = SCALING_MODE_FULL_FILL;
                this.mInited = true;
            }
            if (imgTexFrame.format.colorFormat == 3) {
                if (this.mProgramOES == 0) {
                    this.mProgramOES = GlUtil.createProgram(this.mVertexShader, this.mFragmentShaderOES);
                    if (this.mProgramOES == 0) {
                        Log.e(a, "Created program " + this.mProgramOES + " failed");
                        throw new RuntimeException("Unable to create program");
                    }
                }
                i2 = 36197;
                i3 = this.mProgramOES;
            } else {
                if (this.mProgram == 0) {
                    this.mProgram = GlUtil.createProgram(this.mVertexShader, this.mFragmentShader);
                    if (this.mProgram == 0) {
                        Log.e(a, "Created program " + this.mProgram + " failed");
                        throw new GLProgramLoadException("Unable to create program");
                    }
                }
                i2 = 3553;
                i3 = this.mProgram;
            }
            int glGetAttribLocation = GLES20.glGetAttribLocation(i3, "aPosition");
            GlUtil.checkLocation(glGetAttribLocation, "aPosition");
            int glGetAttribLocation2 = GLES20.glGetAttribLocation(i3, "aTextureCoord");
            GlUtil.checkLocation(glGetAttribLocation2, "aTextureCoord");
            int glGetUniformLocation = GLES20.glGetUniformLocation(i3, "uTexMatrix");
            GlUtil.checkLocation(glGetUniformLocation, "uTexMatrix");
            GlUtil.checkGlError("draw start");
            GLES20.glUseProgram(i3);
            GlUtil.checkGlError("glUseProgram");
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(i2, i);
            GLES20.glUniformMatrix4fv(glGetUniformLocation, SCALING_MODE_BEST_FIT, false, fArr, SCALING_MODE_FULL_FILL);
            GlUtil.checkGlError("glUniformMatrix4fv");
            GLES20.glEnableVertexAttribArray(glGetAttribLocation);
            GlUtil.checkGlError("glEnableVertexAttribArray");
            GLES20.glVertexAttribPointer(glGetAttribLocation, SCALING_MODE_CENTER_CROP, 5126, false, c, floatBuffer2);
            GlUtil.checkGlError("glVertexAttribPointer");
            GLES20.glEnableVertexAttribArray(glGetAttribLocation2);
            GlUtil.checkGlError("glEnableVertexAttribArray");
            GLES20.glVertexAttribPointer(glGetAttribLocation2, SCALING_MODE_CENTER_CROP, 5126, false, c, floatBuffer);
            GlUtil.checkGlError("glVertexAttribPointer");
            glGetUniformLocation = GLES20.glGetUniformLocation(i3, "alpha");
            GlUtil.checkLocation(glGetUniformLocation, "alpha");
            GLES20.glUniform1f(glGetUniformLocation, f);
            GLES20.glDrawArrays(5, SCALING_MODE_FULL_FILL, 4);
            GlUtil.checkGlError("glDrawArrays");
            GLES20.glDisableVertexAttribArray(glGetAttribLocation);
            GLES20.glDisableVertexAttribArray(glGetAttribLocation2);
            GLES20.glBindTexture(i2, SCALING_MODE_FULL_FILL);
            GLES20.glUseProgram(SCALING_MODE_FULL_FILL);
        }
    }

    private void a(int i) {
        float f = 0.0f;
        if (this.d != 0 && this.e != 0) {
            float height;
            ImgTexFormat imgTexFormat = this.f[i];
            if (imgTexFormat != null && imgTexFormat.width > 0 && imgTexFormat.height > 0) {
                if (this.g[i].width() == 0.0f) {
                    height = (((this.g[i].height() * ((float) imgTexFormat.width)) / ((float) imgTexFormat.height)) * ((float) this.e)) / ((float) this.d);
                    this.h[i].right = height + this.g[i].right;
                } else if (this.g[i].height() == 0.0f) {
                    height = (((this.g[i].width() * ((float) imgTexFormat.height)) / ((float) imgTexFormat.width)) * ((float) this.d)) / ((float) this.e);
                    this.h[i].bottom = height + this.g[i].bottom;
                }
            }
            RectF rectF = this.h[i];
            if (imgTexFormat != null && imgTexFormat.width != 0 && imgTexFormat.height != 0 && rectF != null && rectF.width() != 0.0f && rectF.height() != 0.0f) {
                float f2;
                RectF rectF2;
                float f3 = ((float) imgTexFormat.width) / ((float) imgTexFormat.height);
                float width = (((float) this.d) * rectF.width()) / (((float) this.e) * rectF.height());
                if (this.j[i] == SCALING_MODE_BEST_FIT) {
                    if (f3 > width) {
                        f2 = (1.0f - (width / f3)) / 2.0f;
                        height = 0.0f;
                    } else {
                        height = (1.0f - (f3 / width)) / 2.0f;
                        f2 = 0.0f;
                    }
                    Log.d(a, "sar=" + f3 + " dar=" + width + " cropX=" + height + " cropY=" + f2);
                    RectF rectF3 = new RectF(rectF.left + height, rectF.top + f2, rectF.right - height, rectF.bottom - f2);
                    Log.d(a, "rectF=" + rectF3);
                    rectF2 = rectF3;
                } else {
                    rectF2 = rectF;
                }
                this.n[i] = a(rectF2);
                if (this.j[i] != SCALING_MODE_CENTER_CROP) {
                    f2 = 0.0f;
                } else if (f3 > width) {
                    f2 = (1.0f - (width / f3)) / 2.0f;
                } else {
                    f2 = 0.0f;
                    f = (1.0f - (f3 / width)) / 2.0f;
                }
                this.m[i] = TexTransformUtil.getTexCoordsBuf(f2, f, SCALING_MODE_FULL_FILL, this.k[i], false);
            }
        }
    }

    private FloatBuffer a(RectF rectF) {
        float[] fArr = new float[c];
        fArr[SCALING_MODE_FULL_FILL] = (rectF.left * 2.0f) - 4.0f;
        fArr[SCALING_MODE_BEST_FIT] = 1.0f - (rectF.bottom * 2.0f);
        fArr[SCALING_MODE_CENTER_CROP] = (rectF.right * 2.0f) - 4.0f;
        fArr[3] = 1.0f - (rectF.bottom * 2.0f);
        fArr[4] = (rectF.left * 2.0f) - 4.0f;
        fArr[5] = 1.0f - (rectF.top * 2.0f);
        fArr[6] = (rectF.right * 2.0f) - 4.0f;
        fArr[7] = 1.0f - (rectF.top * 2.0f);
        return GlUtil.createFloatBuffer(fArr);
    }
}
