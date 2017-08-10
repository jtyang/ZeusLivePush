package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.TextureView;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GlUtil;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import javax.microedition.khronos.egl.EGLContext;

public class ImgTexPreview {
    private static final String a = "ImgTexPreview";
    private SinkPin<ImgTexFrame> b;
    private GLRender c;
    private int d;
    private ImgTexFrame e;
    private ConditionVariable f;
    private GLRenderListener g;

    private class a extends SinkPin<ImgTexFrame> {
        final /* synthetic */ ImgTexPreview a;

        private a(ImgTexPreview imgTexPreview) {
            this.a = imgTexPreview;
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((ImgTexFrame) obj);
        }

        public void onFormatChanged(Object obj) {
        }

        public void a(ImgTexFrame imgTexFrame) {
            if (this.a.c != null) {
                this.a.e = imgTexFrame;
                this.a.f.close();
                if (this.a.c.getState() == 1) {
                    GLES20.glFinish();
                    this.a.c.requestRender();
                    this.a.f.block();
                }
            }
        }

        public synchronized void onDisconnect(boolean z) {
            super.onDisconnect(z);
            if (z) {
                this.a.release();
            }
        }
    }

    public ImgTexPreview() {
        this.f = new ConditionVariable();
        this.g = new GLRenderListener() {
            final /* synthetic */ ImgTexPreview a;

            {
                this.a = r1;
            }

            public void onReady() {
                Log.d(ImgTexPreview.a, "onReady");
                this.a.d = 0;
            }

            public void onSizeChanged(int i, int i2) {
                Log.d(ImgTexPreview.a, "onSizeChanged " + i + "x" + i2);
            }

            public void onDrawFrame() {
                if (this.a.e != null) {
                    GLES20.glClear(16384);
                    this.a.a(this.a.e);
                    GLES20.glFinish();
                    this.a.e = null;
                    this.a.f.open();
                }
            }

            public void onReleased() {
                this.a.f.open();
            }
        };
        this.b = new a();
        this.c = new GLRender();
    }

    public SinkPin<ImgTexFrame> getSinkPin() {
        return this.b;
    }

    public GLRender getGLRender() {
        return this.c;
    }

    public void setEGL10Context(EGLContext eGLContext) {
        this.c.setInitEGL10Context(eGLContext);
        this.c.addListener(this.g);
    }

    public void setDisplayPreview(GLSurfaceView gLSurfaceView) {
        if (gLSurfaceView == null) {
            this.c.release();
        } else {
            this.c.init(gLSurfaceView);
        }
    }

    public void setDisplayPreview(TextureView textureView) {
        if (textureView == null) {
            this.c.release();
        } else {
            this.c.init(textureView);
        }
    }

    public Object getDisplayPreview() {
        return this.c.getCurrentView();
    }

    public void onPause() {
        this.c.onPause();
    }

    public void onResume() {
        this.c.onResume();
    }

    public void release() {
        this.c.release();
    }

    private void a(ImgTexFrame imgTexFrame) {
        int i;
        ImgTexFormat imgTexFormat = imgTexFrame.format;
        int i2 = imgTexFrame.textureId;
        float[] fArr = imgTexFrame.texMatrix;
        if (imgTexFormat.colorFormat == 3) {
            i = 36197;
        } else {
            i = 3553;
        }
        if (this.d == 0) {
            String str;
            String str2 = GlUtil.BASE_VERTEX_SHADER;
            if (imgTexFormat.colorFormat == 3) {
                str = "#extension GL_OES_EGL_image_external : require\nuniform samplerExternalOES sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            } else {
                str = "uniform sampler2D sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            }
            this.d = GlUtil.createProgram(str2, str);
            if (this.d == 0) {
                Log.e(a, "Created program " + this.d + " failed");
                throw new RuntimeException("Unable to create program");
            }
        }
        GLES20.glBlendFunc(1, 771);
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.d, "aPosition");
        GlUtil.checkLocation(glGetAttribLocation, "aPosition");
        int glGetAttribLocation2 = GLES20.glGetAttribLocation(this.d, "aTextureCoord");
        GlUtil.checkLocation(glGetAttribLocation2, "aTextureCoord");
        int glGetUniformLocation = GLES20.glGetUniformLocation(this.d, "uTexMatrix");
        GlUtil.checkLocation(glGetUniformLocation, "uTexMatrix");
        GlUtil.checkGlError("draw start");
        GLES20.glUseProgram(this.d);
        GlUtil.checkGlError("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(i, i2);
        GLES20.glUniformMatrix4fv(glGetUniformLocation, 1, false, fArr, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(glGetAttribLocation, 2, 5126, false, 8, TexTransformUtil.getVertexCoordsBuf());
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation2);
        GlUtil.checkGlError("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(glGetAttribLocation2, 2, 5126, false, 8, TexTransformUtil.getTexCoordsBuf());
        GlUtil.checkGlError("glVertexAttribPointer");
        GLES20.glDrawArrays(5, 0, 4);
        GlUtil.checkGlError("glDrawArrays");
        GLES20.glDisableVertexAttribArray(glGetAttribLocation);
        GLES20.glDisableVertexAttribArray(glGetAttribLocation2);
        GLES20.glBindTexture(i, 0);
        GLES20.glUseProgram(0);
        GLES20.glBlendFunc(770, 771);
    }
}
