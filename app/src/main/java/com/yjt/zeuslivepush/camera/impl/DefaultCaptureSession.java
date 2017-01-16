package com.yjt.zeuslivepush.camera.impl;

import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import com.yjt.zeuslivepush.camera.CameraCaptureSession;
import com.yjt.zeuslivepush.camera.CaptureOutput;
import com.yjt.zeuslivepush.camera.CaptureRequest;
import com.yjt.zeuslivepush.camera.FaceReceiver;
import com.yjt.zeuslivepush.camera.PreviewQueue;
import com.yjt.zeuslivepush.camera.SessionRequest;
import com.yjt.zeuslivepush.camera.opengl.QpEgl11Surface;
import com.yjt.zeuslivepush.quirks.Quirk;
import com.yjt.zeuslivepush.quirks.QuirksStorage;
import com.yjt.zeuslivepush.render.Renderer;
import com.yjt.zeuslivepush.utils.Assert;
import com.yjt.zeuslivepush.utils.ThreadUtil;
import com.yjt.zeuslivepush.utils.WeakReferenceHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class DefaultCaptureSession extends CameraCaptureSession implements Camera.FaceDetectionListener {
    public static final int ERROR_CAMERA_CONNECTION = 1;
    public static final int ERROR_CAMERA_CONFIGURATION = 2;
    public static final int ERROR_CAMERA_PREVIEW = 3;
    private static final String TAG = "CameraCapture";
    private CaptureRequest mCurCaptureRequest;
    private List<CaptureRequest> mCaptureRequestList = new ArrayList();

    private boolean mOnPreviewing = false;
    private final DefaultCaptureSession.CameraThreadHandler mCameraHandler;
    private final DefaultCaptureSession.MasterThreadHandler mMasterHandler;
    private volatile boolean mConfigureComplete = false;
    private List<CaptureOutput> mCaptureOutputs = new ArrayList();
    protected final SessionRequest _Current;
    private FaceReceiver _DetectSource;

    private Camera.PictureCallback callback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File tt = new File(DefaultCaptureSession.this.mCurCaptureRequest.filename);

            try {
                FileOutputStream e = new FileOutputStream(tt);
                e.write(bytes);
                e.close();
            } catch (IOException e) {
                e.printStackTrace();
                DefaultCaptureSession.this.mCurCaptureRequest.setResult(2);
                DefaultCaptureSession.this._Camera.startPreview();
                DefaultCaptureSession.this.notifyCaptureResult(DefaultCaptureSession.this.mCurCaptureRequest);
                return;
            }

            DefaultCaptureSession.this._Camera.startPreview();
            DefaultCaptureSession.this.notifyCaptureResult(DefaultCaptureSession.this.mCurCaptureRequest);
        }
    };

    DefaultCaptureSession(List<?> output_list, SessionRequest config, StateCallback state_callback, DefaultDevice device, Renderer renderer, Rect crop) {
        super(device);
        this.mCameraHandler = new DefaultCaptureSession.CameraThreadHandler(this.mCameraThread.getLooper(), this);
        this.mMasterHandler = new DefaultCaptureSession.MasterThreadHandler(Looper.myLooper(), this);
        FaceReceiver detect_source = null;

        for (Object item : output_list) {
            if (item instanceof PreviewQueue) {
                PreviewQueueCapture output1 = new PreviewQueueCapture(this._Camera, (PreviewQueue) item, config);
                this.mCaptureOutputs.add(output1);
            } else if (item instanceof FaceReceiver) {
                detect_source = (FaceReceiver) item;
            } else if (item instanceof SurfaceHolder) {
                SurfaceCapture output2 = new SurfaceCapture(this._Camera, (SurfaceHolder) item);
                this.mCaptureOutputs.add(output2);
            }
        }

        if (renderer != null) {
            GLSurfaceCapture output3 = new GLSurfaceCapture(this._Camera, renderer, config.getPreviewDisplayWidth(), config.getPreviewDisplayHeight(), crop);
            this.mCaptureOutputs.add(output3);
        }

        this._DetectSource = detect_source;
        this._Current = config;
        this._StateCallback = state_callback;
        this.mCameraHandler.obtainMessage(1).sendToTarget();
    }

    public void doConfigure() {
        try {
            Camera.Parameters tr = this._Camera.getParameters();
            DefaultDevice.configure(tr, this._Current);
            this._Camera.setParameters(tr);
            this._Camera.setDisplayOrientation(this._Current.displayOrientation);
            if (Build.VERSION.SDK_INT >= 16 &&
                    (this._Camera.getParameters().getFocusMode() == "continuous-video" ||
                            this._Camera.getParameters().getFocusMode() == "continuous-video")) {
                this._Camera.setAutoFocusMoveCallback(new Camera.AutoFocusMoveCallback() {
                    public void onAutoFocusMoving(boolean b, Camera camera) {
                        DefaultCaptureSession.this.mMasterHandler.obtainMessage(8, b ? 1 : 0, 0).sendToTarget();
                    }
                });
            }
        } catch (Throwable var5) {
            Log.e("CameraCapture", "", var5);
            this.mMasterHandler.obtainMessage(ERROR_CAMERA_CONFIGURATION).sendToTarget();
            return;
        }

        if (!this.mCaptureOutputs.isEmpty()) {
            for (Object obj : this.mCaptureOutputs) {
                CaptureOutput output = (CaptureOutput) obj;
                if (!output.configure()) {
                    this.mMasterHandler.obtainMessage(ERROR_CAMERA_CONFIGURATION).sendToTarget();
                    return;
                }
                this.mConfigureComplete = true;
            }
        }

        try {
            this._Camera.startPreview();
        } catch (Throwable e) {
            Log.e("CameraCapture", "", e);
            this.mMasterHandler.obtainMessage(ERROR_CAMERA_CONFIGURATION).sendToTarget();
            return;
        }

        if (null != this._DetectSource && this._Camera.getParameters().getMaxNumDetectedFaces() > 0) {
            this._Camera.setFaceDetectionListener(this);
            this._Camera.startFaceDetection();
        } else {
            this._Camera.setFaceDetectionListener((Camera.FaceDetectionListener) null);
        }

        this.mOnPreviewing = true;
        this.mMasterHandler.obtainMessage(1).sendToTarget();
    }

    void onConfigured() {
        this._StateCallback.onConfigured(this);
    }

    void onConfigureFailed() {
        this._StateCallback.onConfigureFailed(this);
    }

    public void setCaptureRequest(CaptureRequest request) {
        this.mCameraHandler.obtainMessage(WHAT_CAPTURE_REQUEST, request).sendToTarget();
    }

    void doSetCaptureRequest(CaptureRequest req) {
        if (this.mCurCaptureRequest != null) {
            int size = this.mCaptureRequestList.size();
            for (int i = 0; i < size; i++) {
                CaptureRequest request = (CaptureRequest) this.mCaptureRequestList.get(i);
                if (request.getRequestKey() == req.getRequestKey()) {
                    this.mCaptureRequestList.remove(request);
                    i--;
                    size--;
                }
            }
            this.mCaptureRequestList.add(req);
        } else {
            this.mCurCaptureRequest = req;
            switch (req.getRequestKey()) {
                case WHAT_SESSION_CONFIGURE:
                    this.setFlashMode(req);
                    break;
                case WHAT_CAPTURE_REQUEST:
                    this.setZoom(req);
                    break;
                case WHAT_CAPTURE_RENDERER:
                    this.setAutoFocus(req);
                    break;
                case WHAT_CAPTURE_GLSURFACE:
                    this.takePicture(req);
                    break;
                case WHAT_CAPTURE_REMOVESURFACE:
                    for (Object obj : this.mCaptureOutputs) {
                        CaptureOutput output = (CaptureOutput) obj;
                        output.setCrop(req.mCropArea);
                    }
                    this.mCurCaptureRequest = null;
                    break;
                case 32:
                    this.setFocusMode(req);
            }

        }
    }

    void doClose() {
        Log.i("CameraCapture", "doClose");
        if (!this.mOnPreviewing) {
            synchronized (this) {
                this.notifyAll();
                Log.i("CameraCapture", " notify doClose ok");
            }
        } else {
            try {
                this._Camera.stopPreview();
            } catch (Throwable var7) {
                this.notifyError(3, var7);
            }

            if (!this.mCaptureOutputs.isEmpty()) {
                for (Object obj : this.mCaptureOutputs) {
                    CaptureOutput output = (CaptureOutput) obj;
                    Log.i("CameraCapture", "CapureOutput close");
                    output.close();
                }
            }

            this.mOnPreviewing = false;
            synchronized (this) {
                this.notifyAll();
                Log.i("CameraCapture", " notify doClose ok");
            }
        }
    }

    public void setRenderer(Renderer renderer) {
        this.mCameraHandler.obtainMessage(4, renderer).sendToTarget();
    }

    private void doSetRenderer(Renderer renderer) {
        for (Object obj : this.mCaptureOutputs) {
            CaptureOutput output = (CaptureOutput) obj;
            output.setRenderer(renderer);
        }
    }

    public void addQpEglSurface(QpEgl11Surface surface) {
        this.mCameraHandler.obtainMessage(8, surface).sendToTarget();
    }

    private void doAddQpEglSurface(QpEgl11Surface surface) {
        CaptureOutput output = null;

        for (int i = 0, count = this.mCaptureOutputs.size(); i < count; ++i) {
            output = (CaptureOutput) this.mCaptureOutputs.get(i);
            output.addGlSurface(surface);
        }
    }

    public void removeQpEglSurface(QpEgl11Surface surface) {
        synchronized (surface) {
            this.mCameraHandler.obtainMessage(16, surface).sendToTarget();
            ThreadUtil.wait(surface);
        }
    }

    private void doRemoveQpEglSurface(QpEgl11Surface surface) {
        CaptureOutput output = null;
        int i = 0;

        for (int count = this.mCaptureOutputs.size(); i < count; ++i) {
            output = (CaptureOutput) this.mCaptureOutputs.get(i);
            output.removeSurface(surface);
        }

        synchronized (surface) {
            surface.notifyAll();
        }
    }

    public void onAutoFocusMoved(boolean start) {
        if (this._AutoFocusCallback != null) {
            this._AutoFocusCallback.onAutoFocusMoving(start, null);
        }
    }

    public void onCaptureRequestResult(CaptureRequest req) {
        req.onResult();
    }

    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        Log.d("CameraCapture", "face count: " + faces.length);
        this._DetectSource.write(faces);
    }

    private void notifyError(int err, Throwable tr) {
    }

    public void close() {
        Log.d("CameraCapture", "sendToTarget What_session_close");
        this.mCameraHandler.obtainMessage(18).sendToTarget();
        synchronized (this) {
            try {
                this.wait(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean configureComplete() {
        return this.mConfigureComplete;
    }

    private void setZoom(CaptureRequest req) {
        Camera.Parameters p = this._Camera.getParameters();
        if (!p.isZoomSupported()) {
            req.setResult(4);
        } else if (p.getZoomRatios().size() <= req.zoom) {
            req.setResult(8);
        } else {
            p.setZoom(req.zoom);
        }

        this._Camera.setParameters(p);
        this.notifyCaptureResult(req);
    }

    private void setAutoFocus(final CaptureRequest req) {
        Camera.Parameters params = this._Camera.getParameters();
        if (params.getFocusMode() != null && (params.getFocusMode().contains("auto") || params.getFocusMode().contains("continuous-video") || params.getFocusMode().contains("continuous-picture")) && params.getMaxNumFocusAreas() >= 1) {
            if (params.getMaxNumFocusAreas() > 0) {
                if (req.mFocusArea.width() > 0 && req.mFocusArea.height() > 0) {
                    params.setFocusAreas(Arrays.asList(new Camera.Area[]{new Camera.Area(req.mFocusArea, 1)}));
                    this._Camera.setParameters(params);
                    this._Camera.autoFocus(new Camera.AutoFocusCallback() {
                        public void onAutoFocus(boolean b, Camera camera) {
                            DefaultCaptureSession.this._Camera.cancelAutoFocus();
                            if (!QuirksStorage.getBoolean(Quirk.CAMERA_NO_AUTO_FOCUS_CALLBACK)) {
                                DefaultCaptureSession.this.notifyCaptureResult(req);
                            }

                        }
                    });
                    if (QuirksStorage.getBoolean(Quirk.CAMERA_NO_AUTO_FOCUS_CALLBACK)) {
                        this.notifyCaptureResult(req);
                    }
                } else {
                    req.setResult(4);
                    this.notifyCaptureResult(req);
                }
            } else {
                req.setResult(2);
                this.notifyCaptureResult(req);
            }
        } else {
            req.setResult(4);
            this.notifyCaptureResult(req);
        }

    }

    private void setFocusMode(CaptureRequest req) {
        Camera.Parameters params = this._Camera.getParameters();
        if (params.getFocusMode() != null && params.getFocusMode().contains(req.focusMode)) {
            params.setFocusMode(req.focusMode);
            this._Camera.setParameters(params);
            if (req.focusMode == "continuous-picture" || req.focusMode == "continuous-video") {
                this._Camera.cancelAutoFocus();
            }

            this.notifyCaptureResult(this.mCurCaptureRequest);
        } else {
            req.setResult(4);
            this.notifyCaptureResult(req);
        }

    }

    private void setFlashMode(CaptureRequest request) {
        Camera.Parameters p = this._Camera.getParameters();
        if (p.getSupportedFlashModes() == null) {
            request.setResult(4);
        } else if (!p.getSupportedFlashModes().contains(request.flashMode)) {
            request.setResult(4);
        } else if (!p.getSupportedFlashModes().contains("on")) {
            request.setResult(4);
        } else {
            p.setFlashMode(request.flashMode);
        }

        this._Camera.setParameters(p);
        this.notifyCaptureResult(request);
    }

    private void takePicture(CaptureRequest request) {
        boolean needFocus = false;
        if (Build.MODEL.equals("MI 3") || Build.MODEL.equals("Mi-4c")) {
            Camera.Parameters p = this._Camera.getParameters();
            if (p.getFlashMode() != null && (p.getFlashMode().contains("on") || p.getFlashMode().contains("auto"))) {
                needFocus = true;
            }
        }

        if (needFocus) {
            this._Camera.cancelAutoFocus();
            this._Camera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean b, Camera camera) {
                    DefaultCaptureSession.this._Camera.takePicture((Camera.ShutterCallback) null, (Camera.PictureCallback) null, (Camera.PictureCallback) null, DefaultCaptureSession.this.callback);
                }
            });
        } else {
            this._Camera.takePicture((Camera.ShutterCallback) null, (Camera.PictureCallback) null, (Camera.PictureCallback) null, this.callback);
        }

    }

    private void notifyCaptureResult(CaptureRequest req) {
        Assert.assertEquals(req, this.mCurCaptureRequest);
        this.mMasterHandler.obtainMessage(16, req).sendToTarget();
        this.mCurCaptureRequest = null;
        if (!this.mCaptureRequestList.isEmpty()) {
            if (this.mOnPreviewing) {
                this.doSetCaptureRequest((CaptureRequest) this.mCaptureRequestList.remove(0));
            } else {
                this.mCaptureRequestList.clear();
            }
        }

    }

    static class MasterThreadHandler extends WeakReferenceHandler<DefaultCaptureSession> {
        MasterThreadHandler(Looper looper, DefaultCaptureSession reference) {
            super(looper, reference);
        }

        protected void handleMessage(DefaultCaptureSession reference, Message msg) {
            switch (msg.what) {
                case 1:
                    reference.onConfigured();
                    break;
                case 2:
                    reference.onConfigureFailed();
                    break;
                case 8:
                    boolean tmp = msg.arg1 == 1;
                    reference.onAutoFocusMoved(tmp);
                    break;
                case 16:
                    reference.onCaptureRequestResult((CaptureRequest) msg.obj);
                    break;
            }

        }
    }

    static class CameraThreadHandler extends WeakReferenceHandler<DefaultCaptureSession> {
        CameraThreadHandler(Looper looper, DefaultCaptureSession reference) {
            super(looper, reference);
        }

        protected void handleMessage(DefaultCaptureSession reference, Message msg) {
            Log.d("CameraCapture", "CameraThreadHandler receive message, msg.what =" + msg.what);
            QpEgl11Surface surface = null;
            switch (msg.what) {
                case 1:
                    reference.doConfigure();
                    break;
                case 2:
                    CaptureRequest request = (CaptureRequest) msg.obj;
                    reference.doSetCaptureRequest(request);
                case 3:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 17:
                default:
                    break;
                case 4:
                    Renderer renderer = (Renderer) msg.obj;
                    reference.doSetRenderer(renderer);
                    break;
                case 8:
                    surface = (QpEgl11Surface) msg.obj;
                    reference.doAddQpEglSurface(surface);
                    break;
                case 16:
                    surface = (QpEgl11Surface) msg.obj;
                    reference.doRemoveQpEglSurface(surface);
                    break;
                case 18:
                    reference.doClose();
                    break;
            }

            Log.d("Zoom", "handled message");
        }
    }
}
