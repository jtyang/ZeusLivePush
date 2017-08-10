package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import android.util.Log;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.util.CredtpWrapper;
import com.ksyun.media.streamer.util.gles.GLProgramLoadException;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import java.nio.FloatBuffer;

public class ImgTexFilter extends ImgTexFilterBase {
    public static final String BASE_FRAGMENT_SHADER_BODY = "precision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    public static final String BASE_VERTEX_SHADER = "uniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
    private static final String a = "ImgTexFilter";
    private ImgTexFormat b;
    protected String mFragmentShader;
    protected String mFragmentShaderBody;
    protected boolean mMirror;
    protected int mProgramId;
    protected int mTextureTarget;
    protected String mVertexShader;
    protected int maPositionLoc;
    protected int maTextureCoordLoc;
    protected int muTexMatrixLoc;

    public ImgTexFilter(GLRender gLRender) {
        super(gLRender);
        this.mTextureTarget = 3553;
        a(BASE_VERTEX_SHADER, BASE_FRAGMENT_SHADER_BODY);
    }

    protected ImgTexFilter(GLRender gLRender, String str, int i) {
        super(gLRender);
        this.mTextureTarget = 3553;
        a(str, CredtpWrapper.a().a(i));
    }

    public ImgTexFilter(GLRender gLRender, String str, String str2) {
        super(gLRender);
        this.mTextureTarget = 3553;
        a(str, str2);
    }

    private void a(String str, String str2) {
        this.mVertexShader = str;
        this.mFragmentShaderBody = str2;
    }

    public SinkPin<ImgTexFrame> getSinkPin() {
        return getSinkPin(0);
    }

    public void setMirror(boolean z) {
        this.mMirror = z;
    }

    protected void onFormatChanged(ImgTexFormat imgTexFormat) {
    }

    protected void onInitialized() {
    }

    protected void onDrawArraysPre() {
    }

    protected void onDrawArraysAfter() {
    }

    public int getSinkPinNum() {
        return 1;
    }

    protected ImgTexFormat getSrcPinFormat() {
        return this.b;
    }

    public void onFormatChanged(int i, ImgTexFormat imgTexFormat) {
        if (i == this.mMainSinkPinIndex) {
            if (imgTexFormat.colorFormat == 3) {
                this.mTextureTarget = 36197;
                this.mFragmentShader = GlUtil.FRAGMENT_SHADER_OES_HEADER + this.mFragmentShaderBody;
            } else {
                this.mTextureTarget = 3553;
                this.mFragmentShader = GlUtil.FRAGMENT_SHADER_HEADER + this.mFragmentShaderBody;
            }
            this.b = new ImgTexFormat(1, imgTexFormat.width, imgTexFormat.height);
            onFormatChanged(imgTexFormat);
        }
    }

    public void onDraw(ImgTexFrame[] imgTexFrameArr) {
        int i = imgTexFrameArr[this.mMainSinkPinIndex].textureId;
        float[] fArr = imgTexFrameArr[this.mMainSinkPinIndex].texMatrix;
        GlUtil.checkGlError("draw start");
        if (this.mInited) {
            GLES20.glUseProgram(this.mProgramId);
            GlUtil.checkGlError("glUseProgram");
        } else {
            this.mProgramId = GlUtil.createProgram(this.mVertexShader, this.mFragmentShader);
            if (this.mProgramId == 0) {
                Log.e(a, "Created program " + this.mProgramId + " failed");
                throw new GLProgramLoadException("Unable to create program");
            }
            this.maPositionLoc = GLES20.glGetAttribLocation(this.mProgramId, "aPosition");
            GlUtil.checkLocation(this.maPositionLoc, "aPosition");
            this.maTextureCoordLoc = GLES20.glGetAttribLocation(this.mProgramId, "aTextureCoord");
            GlUtil.checkLocation(this.maTextureCoordLoc, "aTextureCoord");
            this.muTexMatrixLoc = GLES20.glGetUniformLocation(this.mProgramId, "uTexMatrix");
            GlUtil.checkLocation(this.muTexMatrixLoc, "uTexMatrix");
            GLES20.glUseProgram(this.mProgramId);
            GlUtil.checkGlError("glUseProgram");
            onInitialized();
            GlUtil.checkGlError("onInitialized " + this);
            this.mInited = true;
        }
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.mTextureTarget, i);
        GlUtil.checkGlError("glBindTexture");
        GLES20.glUniformMatrix4fv(this.muTexMatrixLoc, 1, false, fArr, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.maPositionLoc);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.maPositionLoc, 2, 5126, false, 8, a());
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.maTextureCoordLoc);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.maTextureCoordLoc, 2, 5126, false, 8, getTexCoords());
        GlUtil.checkGlError("glVertexAttribPointer");
        onDrawArraysPre();
        GLES20.glDrawArrays(5, 0, 4);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.maPositionLoc);
        GLES20.glDisableVertexAttribArray(this.maTextureCoordLoc);
        onDrawArraysAfter();
        GLES20.glBindTexture(this.mTextureTarget, 0);
        GLES20.glUseProgram(0);
    }

    protected void onRelease() {
        if (this.mProgramId != 0) {
            GLES20.glDeleteProgram(this.mProgramId);
            this.mProgramId = 0;
        }
    }

    protected FloatBuffer getTexCoords() {
        return TexTransformUtil.getTexCoordsBuf();
    }

    protected int getUniformLocation(String str) {
        int glGetUniformLocation = GLES20.glGetUniformLocation(this.mProgramId, str);
        GlUtil.checkLocation(glGetUniformLocation, str);
        return glGetUniformLocation;
    }

    private FloatBuffer a() {
        if (this.mMirror) {
            return TexTransformUtil.getVertexMirrorCoordsBuf();
        }
        return TexTransformUtil.getVertexCoordsBuf();
    }
}
