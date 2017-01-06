package com.yjt.zeuslivepush.camera;

import android.hardware.Camera;
import android.os.HandlerThread;

import com.yjt.zeuslivepush.camera.opengl.QpEgl11Surface;
import com.yjt.zeuslivepush.render.Renderer;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class CameraCaptureSession {
    public static final int WHAT_SESSION_CONFIGURE = 1;
    public static final int WHAT_CAPTURE_REQUEST = 2;
    public static final int WHAT_CAPTURE_RENDERER = 4;
    public static final int WHAT_CAPTURE_GLSURFACE = 8;
    public static final int WHAT_CAPTURE_REMOVESURFACE = 16;
    public static final int WHAT_SESSION_CLOSE = 18;
    public static final int WHAT_SESSION_ONCONFIGURED = 1;
    public static final int WHAT_SESSION_CONFIGURE_FAILED = 2;
    public static final int WHAT_SESSION_ONAUTOFOCUSMOVED = 8;
    public static final int WHAT_CAPTURE_REQUEST_RESULT = 16;
    protected final Camera _Camera;
    protected CameraCaptureSession.StateCallback _StateCallback;
    protected final HandlerThread mCameraThread;
    public AutoFocusCallback _AutoFocusCallback;

    public CameraCaptureSession(CameraDevice device) {
        this.mCameraThread = device.getCameraThread();
        this._Camera = device.getCamera();
    }

    public abstract void setCaptureRequest(CaptureRequest var1);

    public final void setAutoFocusCallback(AutoFocusCallback autoFocusCallback) {
        this._AutoFocusCallback = autoFocusCallback;
    }

    public abstract void setRenderer(Renderer var1);

    public abstract void addQpEglSurface(QpEgl11Surface var1);

    public abstract void removeQpEglSurface(QpEgl11Surface var1);

    public abstract void close();

    public abstract boolean configureComplete();

    public abstract static class StateCallback {
        public abstract void onConfigureFailed(CameraCaptureSession var1);

        public abstract void onConfigured(CameraCaptureSession var1);
    }
}
