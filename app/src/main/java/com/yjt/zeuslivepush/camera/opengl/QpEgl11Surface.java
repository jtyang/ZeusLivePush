package com.yjt.zeuslivepush.camera.opengl;

import android.view.Surface;
import android.view.SurfaceHolder;

import com.yjt.zeuslivepush.camera.CameraSurfaceController;

import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLSurface;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class QpEgl11Surface {
    private static final String TAG = "QpEgl11Surface";
    private EGLSurface mEGLSurface;
    CameraSurfaceController mSurfaceController;
    private Surface mSurface;
    private SurfaceHolder mHolder;

    public QpEgl11Surface(Surface surface) {
        this.mEGLSurface = EGL11.EGL_NO_SURFACE;
        this.mSurfaceController = new CameraSurfaceController();
        this.mSurface = null;
        this.mHolder = null;
        this.mSurface = surface;
    }

    public QpEgl11Surface(SurfaceHolder holder) {
        this.mEGLSurface = EGL11.EGL_NO_SURFACE;
        this.mSurfaceController = new CameraSurfaceController();
        this.mSurface = null;
        this.mHolder = null;
        this.mHolder = holder;
        this.mSurface = holder.getSurface();
    }

    public CameraSurfaceController getSurfaceController() {
        return this.mSurfaceController;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public SurfaceHolder getSurfaceHolder() {
        return this.mHolder;
    }

    public void createEGLSurface(QpEgl11Core eglCore) {
        if (this.mHolder != null) {
            this.mEGLSurface = eglCore.createWindowSurface(this.mHolder);
        } else {
            this.mEGLSurface = eglCore.createWindowSurface(this.mSurface);
        }

    }

    public void releaseEGLSurface(QpEgl11Core eglCore) {
        if (this.mEGLSurface != EGL11.EGL_NO_SURFACE) {
            eglCore.releaseSurface(this.mEGLSurface);
            this.mEGLSurface = EGL11.EGL_NO_SURFACE;
        }

    }

    public EGLSurface getEGLSurface() {
        return this.mEGLSurface;
    }

    public int getWidth() {
        return this.mSurfaceController.getWidth();
    }

    public int getHeight() {
        return this.mSurfaceController.getHeight();
    }

    public void onSurfaceRendered() {
        CameraSurfaceController.Callback render_callback = this.mSurfaceController.getCallback();
        if (render_callback != null) {
            render_callback.onSurfaceRendered();
        }
    }
}
