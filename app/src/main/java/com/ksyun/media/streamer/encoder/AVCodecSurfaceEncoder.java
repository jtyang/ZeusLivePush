package com.ksyun.media.streamer.encoder;

import android.annotation.TargetApi;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.util.Log;
import android.view.Surface;
import com.ksyun.media.streamer.encoder.AVEncoderWrapper.a;
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

@TargetApi(19)
public class AVCodecSurfaceEncoder extends Encoder<ImgTexFrame, ImgBufFrame> implements a {
    private static final String k = "AVCodecSurfaceEncoder";
    private static final boolean l = false;
    private GLRender m;
    private boolean n;
    private d o;
    private Surface p;
    private f q;
    private int r;
    private ImageReader s;
    private ByteBuffer t;
    private AVEncoderWrapper u;
    private ImgBufFormat v;
    private GLRenderListener w;

    protected int b(ImgTexFrame r7) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:15:? in {4, 10, 12, 13, 14, 17, 18} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.rerun(BlockProcessor.java:44)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:57)
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
        r0 = r6.n;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        if (r0 != 0) goto L_0x0012;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
    L_0x0006:
        r0 = r6.m;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0 = r0.getEGLContext();	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r6.a(r0);	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0 = 1;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r6.n = r0;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
    L_0x0012:
        r0 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        android.opengl.GLES20.glClear(r0);	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r6.d(r7);	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        android.opengl.GLES20.glFinish();	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0 = r6.q;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r2 = r7.pts;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r2 = r2 * r4;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r2 = r2 * r4;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0.a(r2);	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0 = r6.q;	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0.e();	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0 = r6.m;
        r0 = r0.getFboManager();
        r1 = r7.textureId;
        r0.unlock(r1);
        r0 = 0;
    L_0x0037:
        return r0;
    L_0x0038:
        r0 = move-exception;
        r1 = "AVCodecSurfaceEncoder";	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r2 = "Render to ImageReader surface failed!";	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        android.util.Log.e(r1, r2);	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0.printStackTrace();	 Catch:{ Exception -> 0x0038, all -> 0x0051 }
        r0 = -1001; // 0xfffffffffffffc17 float:NaN double:NaN;
        r1 = r6.m;
        r1 = r1.getFboManager();
        r2 = r7.textureId;
        r1.unlock(r2);
        goto L_0x0037;
    L_0x0051:
        r0 = move-exception;
        r1 = r6.m;
        r1 = r1.getFboManager();
        r2 = r7.textureId;
        r1.unlock(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.encoder.AVCodecSurfaceEncoder.b(com.ksyun.media.streamer.framework.ImgTexFrame):int");
    }

    protected /* synthetic */ boolean c(Object obj) {
        return a((ImgTexFrame) obj);
    }

    protected /* synthetic */ void e(Object obj) {
        c((ImgTexFrame) obj);
    }

    public AVCodecSurfaceEncoder(GLRender gLRender) {
        this.w = new GLRenderListener() {
            final /* synthetic */ AVCodecSurfaceEncoder a;

            {
                this.a = r1;
            }

            public void onReady() {
                this.a.n = false;
                this.a.r = 0;
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        };
        this.m = gLRender;
        this.m.addListener(this.w);
    }

    public void release() {
        this.m.removeListener(this.w);
        super.release();
    }

    protected int a(Object obj) {
        if (!(obj instanceof VideoEncodeFormat)) {
            return DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
        }
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) obj;
        this.u = new AVEncoderWrapper();
        this.u.a((a) this);
        int a = this.u.a(videoEncodeFormat.getCodecId(), videoEncodeFormat.getBitrate(), 0, videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight(), videoEncodeFormat.getFramerate(), videoEncodeFormat.getIframeinterval(), videoEncodeFormat.getScene(), videoEncodeFormat.getProfile(), videoEncodeFormat.getCrf(), videoEncodeFormat.getLiveStreaming());
        if (a != 0 || this.s != null) {
            return a;
        }
        this.s = ImageReader.newInstance(videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight(), 1, 1);
        this.p = this.s.getSurface();
        this.s.setOnImageAvailableListener(new OnImageAvailableListener() {
            final /* synthetic */ AVCodecSurfaceEncoder a;

            {
                this.a = r1;
            }

            public void onImageAvailable(ImageReader imageReader) {
                int i = 0;
                try {
                    Image acquireNextImage = this.a.s.acquireNextImage();
                    ByteBuffer buffer = acquireNextImage.getPlanes()[0].getBuffer();
                    int rowStride = acquireNextImage.getPlanes()[0].getRowStride();
                    if (buffer != null) {
                        long timestamp = (acquireNextImage.getTimestamp() / 1000) / 1000;
                        int i2 = ((this.a.v.width * this.a.v.height) * 3) / 2;
                        if (this.a.t == null || this.a.t.capacity() < i2) {
                            this.a.t = ByteBuffer.allocateDirect(i2);
                        }
                        if (this.a.t != null) {
                            this.a.t.clear();
                            ColorFormatConvert.RGBAToI420(buffer, rowStride, this.a.v.width, this.a.v.height, this.a.t);
                            this.a.t.rewind();
                            AVEncoderWrapper d = this.a.u;
                            ByteBuffer c = this.a.t;
                            if (this.a.j) {
                                i = 1;
                            }
                            i = d.a(c, timestamp, i);
                            this.a.j = false;
                            if (i < 0) {
                                this.a.b(i);
                            }
                        }
                    }
                    acquireNextImage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.a.b((int) DeviceInfoTools.REQUEST_ERROR_PARSE_FILED);
                }
            }
        }, null);
        return a;
    }

    protected void a() {
        this.u.a();
        this.u.b();
        this.u = null;
        if (this.s != null) {
            this.s.close();
            this.s = null;
        }
        if (this.r != 0) {
            GLES20.glDeleteProgram(this.r);
            GLES20.glGetError();
            this.r = 0;
        }
        if (this.q != null) {
            this.q.f();
            this.q = null;
        }
        if (this.o != null) {
            this.o.a();
            this.o = null;
        }
        this.n = false;
    }

    protected void d(Object obj) {
        ImgTexFormat imgTexFormat = (ImgTexFormat) obj;
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) this.b;
        if (videoEncodeFormat.getWidth() != imgTexFormat.width || videoEncodeFormat.getHeight() != imgTexFormat.height) {
            Log.d(k, "restart encoder");
            b();
            a();
            videoEncodeFormat.setWidth(imgTexFormat.width);
            videoEncodeFormat.setHeight(imgTexFormat.height);
            a(this.b);
        }
    }

    protected void a(int i) {
        this.u.a(i);
    }

    protected void b() {
        this.u.a(null, 0, 0);
    }

    protected boolean a(ImgTexFrame imgTexFrame) {
        GLES20.glFinish();
        this.m.getFboManager().lock(imgTexFrame.textureId);
        return false;
    }

    protected void c(ImgTexFrame imgTexFrame) {
        this.m.getFboManager().unlock(imgTexFrame.textureId);
    }

    public void onEncoded(long j, ByteBuffer byteBuffer, long j2, long j3, int i) {
        if ((i & 2) != 0) {
            int i2;
            VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) this.b;
            if (videoEncodeFormat.getCodecId() == 2) {
                i2 = ImgBufFormat.FMT_HEVC;
            } else {
                i2 = StreamerConstants.CODEC_ID_AAC;
            }
            this.v = new ImgBufFormat(i2, videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight(), 0);
            f(this.v);
        }
        ImgBufFrame imgBufFrame = new ImgBufFrame(this.v, byteBuffer, j3);
        imgBufFrame.dts = j2;
        imgBufFrame.flags = i;
        imgBufFrame.avPacketOpaque = j;
        g(imgBufFrame);
    }

    private void a(EGLContext eGLContext) {
        if (this.o == null || this.q == null) {
            this.o = new d(eGLContext, 0);
            this.q = new f(this.o, this.p);
        } else {
            this.q.d();
            this.q.c();
            this.o.a();
            this.o = new d(eGLContext, 0);
            this.q.a(this.o);
        }
        this.q.d();
        GLES20.glViewport(0, 0, this.q.a(), this.q.b());
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
        if (this.r == 0) {
            String str;
            String str2 = GlUtil.BASE_VERTEX_SHADER;
            if (imgTexFormat.colorFormat == 3) {
                str = "#extension GL_OES_EGL_image_external : require\nuniform samplerExternalOES sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            } else {
                str = "uniform sampler2D sTexture;\nprecision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
            }
            this.r = GlUtil.createProgram(str2, str);
            if (this.r == 0) {
                Log.e(k, "Created program " + this.r + " failed");
                throw new RuntimeException("Unable to create program");
            }
        }
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.r, "aPosition");
        GlUtil.checkLocation(glGetAttribLocation, "aPosition");
        int glGetAttribLocation2 = GLES20.glGetAttribLocation(this.r, "aTextureCoord");
        GlUtil.checkLocation(glGetAttribLocation2, "aTextureCoord");
        int glGetUniformLocation = GLES20.glGetUniformLocation(this.r, "uTexMatrix");
        GlUtil.checkLocation(glGetUniformLocation, "uTexMatrix");
        GlUtil.checkGlError("draw start");
        GLES20.glUseProgram(this.r);
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
