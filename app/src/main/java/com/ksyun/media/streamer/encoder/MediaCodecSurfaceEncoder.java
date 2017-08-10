package com.ksyun.media.streamer.encoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.player.misc.c;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GlUtil;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;
import com.ksyun.media.streamer.util.gles.f;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@TargetApi(18)
public class MediaCodecSurfaceEncoder extends MediaCodecEncoderBase<ImgTexFrame, ImgBufFrame> {
    private static final String o = "HWSurfaceEncoder";
    private static final boolean p = false;
    private GLRender q;
    private boolean r;
    private d s;
    private Surface t;
    private f u;
    private int v;
    private float w;
    private BlockingQueue<Long> x;
    private GLRenderListener y;

    protected int a(ImgTexFrame r7) {
        /* JADX: method processing error */
/*
        Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r6 = this;
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = 0;
        r1 = r6.r;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        if (r1 != 0) goto L_0x0013;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0007:
        r1 = r6.q;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = r1.getEGLContext();	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r6.a(r1);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = 1;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r6.r = r1;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0013:
        r1 = 0;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r6.a(r1);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        android.opengl.GLES20.glClear(r1);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r6.d(r7);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        android.opengl.GLES20.glFinish();	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = r6.j;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        if (r1 == 0) goto L_0x0046;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0026:
        r1 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = 19;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        if (r1 < r2) goto L_0x0043;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x002c:
        r1 = "HWSurfaceEncoder";	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = "request key frame";	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        android.util.Log.d(r1, r2);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = new android.os.Bundle;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1.<init>();	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = "request-sync";	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r3 = 0;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1.putInt(r2, r3);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = r6.k;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2.setParameters(r1);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0043:
        r1 = 0;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r6.j = r1;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0046:
        r1 = r6.u;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = r7.pts;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = r2 * r4;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = r2 * r4;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1.a(r2);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = r6.u;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1.e();	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = r6.x;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = r7.pts;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = java.lang.Long.valueOf(r2);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r1 = r1.offer(r2);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        if (r1 != 0) goto L_0x0069;	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0062:
        r1 = "HWSurfaceEncoder";	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r2 = "offer pts failed!";	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        android.util.Log.e(r1, r2);	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
    L_0x0069:
        r1 = r6.q;
        r1 = r1.getFboManager();
        r2 = r7.textureId;
        r1.unlock(r2);
    L_0x0074:
        return r0;
    L_0x0075:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ Exception -> 0x0075, all -> 0x0087 }
        r0 = -1001; // 0xfffffffffffffc17 float:NaN double:NaN;
        r1 = r6.q;
        r1 = r1.getFboManager();
        r2 = r7.textureId;
        r1.unlock(r2);
        goto L_0x0074;
    L_0x0087:
        r0 = move-exception;
        r1 = r6.q;
        r1 = r1.getFboManager();
        r2 = r7.textureId;
        r1.unlock(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.encoder.MediaCodecSurfaceEncoder.a(com.ksyun.media.streamer.framework.ImgTexFrame):int");
    }

    protected /* synthetic */ int b(Object obj) {
        return a((ImgTexFrame) obj);
    }

    protected /* synthetic */ Object b(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        return a(byteBuffer, bufferInfo);
    }

    protected /* synthetic */ void e(Object obj) {
        b((ImgTexFrame) obj);
    }

    public MediaCodecSurfaceEncoder(GLRender gLRender) {
        this.y = new GLRenderListener() {
            final /* synthetic */ MediaCodecSurfaceEncoder a;

            {
                this.a = r1;
            }

            public void onReady() {
                this.a.r = false;
                this.a.v = 0;
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        };
        this.q = gLRender;
        this.q.addListener(this.y);
        this.x = new ArrayBlockingQueue(128);
        setUseSyncMode(true);
    }

    public void release() {
        this.q.removeListener(this.y);
        super.release();
    }

    protected int a(Object obj) {
        String str;
        int i = 2;
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) obj;
        if (videoEncodeFormat.getCodecId() == 1) {
            str = "video/avc";
        } else if (videoEncodeFormat.getCodecId() != 2) {
            return DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
        } else {
            str = "video/hevc";
        }
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str, ((videoEncodeFormat.getWidth() + 15) / 16) * 16, ((videoEncodeFormat.getHeight() + 1) / 2) * 2);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger(KSYMediaMeta.IJKM_KEY_BITRATE, videoEncodeFormat.getBitrate());
        createVideoFormat.setInteger("bitrate-mode", 2);
        createVideoFormat.setInteger("frame-rate", (int) (videoEncodeFormat.getFramerate() + 0.5f));
        if (VERSION.SDK_INT < 25) {
            createVideoFormat.setInteger("i-frame-interval", (int) (videoEncodeFormat.getIframeinterval() + 0.5f));
        } else {
            createVideoFormat.setFloat("i-frame-interval", videoEncodeFormat.getIframeinterval());
        }
        if (videoEncodeFormat.getCodecId() == 1) {
            int i2 = 512;
            if (videoEncodeFormat.getWidth() * videoEncodeFormat.getHeight() > 921600) {
                i2 = 2048;
            }
            switch (videoEncodeFormat.getProfile()) {
                case d.a /*1*/:
                    i = 8;
                    break;
                case d.b /*2*/:
                    break;
                default:
                    i = 1;
                    break;
            }
            createVideoFormat.setInteger("profile", i);
            createVideoFormat.setInteger("level", i2);
        }
        Log.d(o, "MediaFormat: " + createVideoFormat);
        try {
            this.k = MediaCodec.createEncoderByType(str);
            this.k.configure(createVideoFormat, null, null, 1);
            this.t = this.k.createInputSurface();
            this.k.start();
            this.w = videoEncodeFormat.getFramerate();
            this.x.clear();
            return 0;
        } catch (Exception e) {
            Log.e(o, "Failed to start MediaCodec surface encoder");
            e.printStackTrace();
            return DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
        }
    }

    protected void a() {
        try {
            this.k.signalEndOfInputStream();
        } catch (Exception e) {
            Log.e(o, "signalEndOfInputStream failed, ignore");
            e.printStackTrace();
        }
        try {
            a(true);
        } catch (Exception e2) {
            Log.e(o, "signal end of stream failed, ignore");
        }
        try {
            this.k.stop();
        } catch (Exception e3) {
            Log.w(o, "stop encoder failed, ignore");
        }
        this.k.release();
        this.k = null;
        if (this.v != 0) {
            GLES20.glDeleteProgram(this.v);
            GLES20.glGetError();
            this.v = 0;
        }
        if (this.u != null) {
            this.u.f();
            this.u = null;
        }
        if (this.s != null) {
            this.s.a();
            this.s = null;
        }
        this.r = false;
    }

    protected void d(Object obj) {
        ImgTexFormat imgTexFormat = (ImgTexFormat) obj;
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) this.b;
        if (getState() != 2) {
            return;
        }
        if (videoEncodeFormat.getWidth() != imgTexFormat.width || videoEncodeFormat.getHeight() != imgTexFormat.height) {
            Log.d(o, "restart encoder");
            b();
            a();
            videoEncodeFormat.setWidth(imgTexFormat.width);
            videoEncodeFormat.setHeight(imgTexFormat.height);
            a(this.b);
        }
    }

    protected void a(MediaFormat mediaFormat) {
        int i;
        int integer = mediaFormat.getInteger(c.b);
        int integer2 = mediaFormat.getInteger(c.c);
        if (((VideoEncodeFormat) this.b).getCodecId() == 2) {
            i = ImgBufFormat.FMT_HEVC;
        } else {
            i = StreamerConstants.CODEC_ID_AAC;
        }
        this.n = new ImgBufFormat(i, integer, integer2, 0);
    }

    protected ImgBufFrame a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        Object obj = null;
        Object obj2 = 1;
        Object obj3 = (byteBuffer == null || byteBuffer.limit() == 0) ? 1 : null;
        ImgBufFrame imgBufFrame = new ImgBufFrame((ImgBufFormat) this.n, byteBuffer, bufferInfo.presentationTimeUs / 1000);
        if ((bufferInfo.flags & 4) != 0) {
            imgBufFrame.flags |= 4;
        }
        if ((bufferInfo.flags & 1) != 0) {
            imgBufFrame.flags |= 1;
            obj = 1;
        }
        if ((bufferInfo.flags & 2) != 0) {
            imgBufFrame.flags |= 2;
        } else {
            obj2 = obj3;
        }
        if (obj2 == null) {
            Long l = (Long) this.x.poll();
            if (l != null) {
                if (!(obj == null || l.longValue() == imgBufFrame.pts)) {
                    Log.e(o, "key frame dts calculate error! pts=" + imgBufFrame.pts + " val=" + imgBufFrame.dts);
                }
                imgBufFrame.dts = l.longValue() - ((long) (1000.0f / this.w));
                imgBufFrame.dts = Math.min(imgBufFrame.dts, imgBufFrame.pts);
            } else {
                Log.e(o, "pts queue is empty while trying to cal dts!");
            }
        }
        return imgBufFrame;
    }

    protected void b(ImgTexFrame imgTexFrame) {
        this.q.getFboManager().unlock(imgTexFrame.textureId);
    }

    protected boolean c(ImgTexFrame imgTexFrame) {
        GLES20.glFinish();
        this.q.getFboManager().lock(imgTexFrame.textureId);
        return false;
    }

    private void a(EGLContext eGLContext) {
        if (this.s == null || this.u == null) {
            this.s = new d(eGLContext, 1);
            this.u = new f(this.s, this.t);
        } else {
            this.u.d();
            this.u.c();
            this.s.a();
            this.s = new d(eGLContext, 1);
            this.u.a(this.s);
        }
        this.u.d();
        GLES20.glViewport(0, 0, this.u.a(), this.u.b());
    }

    private void d(ImgTexFrame imgTexFrame) {
        int i;
        ImgTexFormat imgTexFormat = imgTexFrame.format;
        int i2 = imgTexFrame.textureId;
        float[] fArr = imgTexFrame.texMatrix;
        if (imgTexFormat.colorFormat == 3) {
            i = 36197;
        } else {
            i = 3553;
        }
        if (this.v == 0) {
            String str;
            String str2 = GlUtil.BASE_VERTEX_SHADER;
            if (imgTexFormat.colorFormat == 3) {
                str = "#extension GL_OES_EGL_image_external : require\nuniform samplerExternalOES sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            } else {
                str = "uniform sampler2D sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            }
            this.v = GlUtil.createProgram(str2, str);
            if (this.v == 0) {
                Log.e(o, "Created program " + this.v + " failed");
                throw new RuntimeException("Unable to create program");
            }
        }
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.v, "aPosition");
        GlUtil.checkLocation(glGetAttribLocation, "aPosition");
        int glGetAttribLocation2 = GLES20.glGetAttribLocation(this.v, "aTextureCoord");
        GlUtil.checkLocation(glGetAttribLocation2, "aTextureCoord");
        int glGetUniformLocation = GLES20.glGetUniformLocation(this.v, "uTexMatrix");
        GlUtil.checkLocation(glGetUniformLocation, "uTexMatrix");
        GlUtil.checkGlError("draw start");
        GLES20.glUseProgram(this.v);
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
    }
}
