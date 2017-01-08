package com.yjt.zeuslivepush.camera.impl;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;

import com.yjt.zeuslivepush.camera.CaptureOutput;
import com.yjt.zeuslivepush.camera.opengl.QpEgl11Core;
import com.yjt.zeuslivepush.camera.opengl.QpEgl11Surface;
import com.yjt.zeuslivepush.camera.opengl.QpOpenglUtils;
import com.yjt.zeuslivepush.gl.Texture;
import com.yjt.zeuslivepush.logger.data.collect.CaptureCollector;
import com.yjt.zeuslivepush.logger.data.collect.DataCollectTrunk;
import com.yjt.zeuslivepush.render.Renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLSurface;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class GLSurfaceCapture extends CaptureOutput implements OnFrameAvailableListener, Renderer.RenderOutput {
    private ArrayList<QpEgl11Surface> mQpEglSurfaceList = new ArrayList();
    private Texture _Texture;
    private QpEgl11Core mQpEgl11Core = null;
    private SurfaceTexture mSurfaceTexture = null;
    private float[] mStMatrix = new float[16];
    private Renderer mRenderer = null;
    private QpEgl11Surface mGlSurface = null;
    private int mTextureTarget = 36197;
    private int mTextureWidth;
    private int mTextureHeight;
    private Rect mCrop = new Rect();
    private EGLSurface mEGLSurface;
    private CaptureCollector mCaptureCollector = new CaptureCollector();
    private static final String TAG = "Beauty";
    Rect view_port = new Rect();

    public GLSurfaceCapture(Camera camera, Renderer renderer, int texture_width, int texture_height, Rect crop) {
        super(camera);
        this.mRenderer = renderer;
        this.mTextureWidth = texture_width;
        this.mTextureHeight = texture_height;
        this.mCrop.set(crop);
    }

    public boolean configure() {
        this.mQpEgl11Core = new QpEgl11Core((EGLContext) null, 1);
        this.mEGLSurface = this.mQpEgl11Core.createPbufferSurface(1, 1);
        this.mQpEgl11Core.makeCurrent(this.mEGLSurface);
        int textureId = QpOpenglUtils.createTextureObject(this.mTextureTarget);
        this._Texture = new Texture();
        this._Texture.id = textureId;
        this._Texture.target = 36197;
        this.mSurfaceTexture = new SurfaceTexture(this._Texture.id);
        this.mSurfaceTexture.setOnFrameAvailableListener(this);
        this.mRenderer.setRenderOutput(this);
        this.mRenderer.setInputTexture(this._Texture);
        this.mRenderer.setInputSize(this.mTextureWidth, this.mTextureHeight, this.mCrop);
        this.mRenderer.realize();

        try {
            this.mCamera.setPreviewTexture(this.mSurfaceTexture);
            return true;
        } catch (IOException e) {
            Log.e("Beauty", "Failed to configure GlSurface capture output!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        this.mQpEgl11Core.releaseSurface(this.mEGLSurface);
        Iterator e = this.mQpEglSurfaceList.iterator();

        while (e.hasNext()) {
            QpEgl11Surface surface = (QpEgl11Surface) e.next();
            surface.releaseEGLSurface(this.mQpEgl11Core);
        }

        this.mQpEglSurfaceList.clear();
        this.mRenderer.unrealize();

        boolean surface1;
        try {
            this.mCamera.setPreviewTexture((SurfaceTexture) null);
            return true;
        } catch (IOException var6) {
            Log.e("Beauty", "Failed to close GlSurface output!");
            var6.printStackTrace();
            surface1 = false;
        } finally {
            this.mSurfaceTexture.setOnFrameAvailableListener((OnFrameAvailableListener) null);
            this.mSurfaceTexture.release();
            this.mSurfaceTexture = null;
            this.mQpEgl11Core.release();
            this.mQpEgl11Core = null;
            this.mCamera = null;
            this.mRenderer = null;
        }

        return surface1;
    }

    public void setRenderer(Renderer renderer) {
        this.mQpEgl11Core.makeCurrent(this.mEGLSurface);
        this.mRenderer.unrealize();
        renderer.setRenderOutput(this);
        renderer.setInputTexture(this._Texture);
        renderer.setInputSize(this.mTextureWidth, this.mTextureHeight, this.mCrop);
        renderer.realize();
        this.mRenderer = renderer;
    }

    public void setCrop(Rect crop) {
        this.mCrop = crop;
    }

    public void addGlSurface(QpEgl11Surface surface) {
        Log.i("Beauty", "thread addGlSurface " + Thread.currentThread().getId());
        this.mQpEglSurfaceList.add(surface);
    }

    public void removeSurface(QpEgl11Surface surface) {
        surface.releaseEGLSurface(this.mQpEgl11Core);
        Log.i("Beauty", "thread removeGlSurface " + Thread.currentThread().getId());
        this.mQpEglSurfaceList.remove(surface);
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.mCaptureCollector.addVideoCaptureFrameCount(1);
        if (this.mSurfaceTexture != null) {
            surfaceTexture.updateTexImage();
            surfaceTexture.getTransformMatrix(this.mStMatrix);

            try {
                int r = 0;

                for (int count = this.mQpEglSurfaceList.size(); r < count; ++r) {
                    this.mGlSurface = (QpEgl11Surface) this.mQpEglSurfaceList.get(r);
                    if (this.mGlSurface.getEGLSurface() == EGL10.EGL_NO_SURFACE) {
                        this.mGlSurface.createEGLSurface(this.mQpEgl11Core);
                    }

                    this.mQpEgl11Core.makeCurrent(this.mGlSurface.getEGLSurface());
                    if (this.mGlSurface.getSurfaceController().isVisible()) {
                        this.mRenderer.setInputTransform(this.mStMatrix);
                        this.mRenderer.setInputSize(this.mTextureWidth, this.mTextureHeight, this.mCrop);
                        if ((this.mGlSurface.getSurfaceController().getDisplayMethod() & 128) != 0) {
                            this.mRenderer.isMirrored(true);
                        } else {
                            this.mRenderer.isMirrored(false);
                        }

                        this.mRenderer.draw();
                        if (this.mGlSurface.getSurfaceController().isCodecController()) {
                            DataCollectTrunk.getInstance().setVideoEncoderStartTime(System.currentTimeMillis());
                        }

                        if ((this.mGlSurface.getSurfaceController().getFlags() & 1) != 0) {
                            this.mQpEgl11Core.setPresentationTime(surfaceTexture.getTimestamp());
                        }

                        this.mQpEgl11Core.swapBuffers(this.mGlSurface.getEGLSurface());
                        this.mGlSurface.onSurfaceRendered();
                        if ("2014501".equals(Build.MODEL) || "A11W".equals(Build.MODEL)) {
                            Thread.sleep(16L);
                        }
                    }
                }
            } catch (ConcurrentModificationException var4) {
                Log.e("Beauty", var4.toString());
            } catch (Throwable var5) {
                this.removeSurface(this.mGlSurface);
            }

        }
    }

    public void beginFrame() {
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GLES20.glClear(16384);
        this.mGlSurface.getSurfaceController().calculateViewPort(this.mCrop.width(), this.mCrop.height(), this.view_port);
        GLES20.glViewport(this.view_port.left, this.view_port.top, this.view_port.width(), this.view_port.height());
    }

    public void endFrame() {
    }
}