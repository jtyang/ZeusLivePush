package com.yjt.zeuslivepush.camera.impl;

import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.yjt.zeuslivepush.camera.CameraCaptureSession;
import com.yjt.zeuslivepush.camera.CameraCharacteristics;
import com.yjt.zeuslivepush.camera.CameraDevice;
import com.yjt.zeuslivepush.camera.SessionRequest;
import com.yjt.zeuslivepush.render.Renderer;
import com.yjt.zeuslivepush.utils.HandlerUtil;
import com.yjt.zeuslivepush.utils.ThreadUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class DefaultDevice extends CameraDevice {
    private static final String TAG = "DefaultCamera";
    private final Handler _CallbackHandler;
    private final StateCallback _Callback;
    private final HandlerThread _CameraThread;
    private final DefaultManager _Manager;
    private Handler mCameraHandler = null;
    private CameraCaptureSession mCameraCaptureSession;
    public static final int WHAT_DEVICE_OPEN = 1;
    public static final int WHAT_DEVICE_CLOSE = 2;

    public Handler getCallbackHandler() {
        return this._CallbackHandler;
    }

    public DefaultDevice(int id, StateCallback callback, Handler callback_handler, DefaultManager manager) {
        super(manager.getCameraCharacteristics(id));
        this._Manager = manager;
        this._CameraThread = new HandlerThread("Camera" + id);
        this._CameraThread.start();
        this.mCameraHandler = new Handler(this._CameraThread.getLooper(), new DefaultDevice.CameraThreadCallback());
        this._Callback = callback;
        this._CallbackHandler = callback_handler;
        this.mCameraHandler.obtainMessage(1).sendToTarget();
    }

    void doOpen() {
        Camera camera;
        try {
            camera = this._Manager.acquireCamera(this._Info.id);
            this._Camera = camera;
        } catch (Throwable var3) {
            Log.e("DefaultCamera", "failed to open camera: " + this._Info.id, var3);
            DefaultStateCallback.notifyError(this, 1);
            this._Camera = null;
            return;
        }

        try {
            DefaultCharacteristics tr = this._Manager.getCameraCharacteristics(this._Info.id);
            if (!tr.isInitialized()) {
                tr.initialize(camera.getParameters());
            }
        } catch (Throwable var4) {
            Log.e("DefaultCamera", "failed to init camera: " + this._Info.id, var4);
            this._Manager.releaseCamera(camera);
            DefaultStateCallback.notifyError(this, 1);
            this._Camera = null;
            return;
        }

        DefaultStateCallback.notifyOpened(this);
    }

    void dispatchOpened() {
        this._Callback.onOpened(this);
    }

    void dispatchError(int error) {
        this._Callback.onError(this, error);
    }

    public Looper getLooper() {
        return this._CameraThread.getLooper();
    }

    CameraCharacteristics getChara() {
        return this._Info;
    }

    static void configure(Camera.Parameters p, SessionRequest c) throws Throwable {
        int preview_framerate = c.previewFrameRate;
        p.setPreviewSize(c.previewWidth, c.previewHeight);
        p.setRecordingHint(c.recording);
        if (p.isVideoStabilizationSupported()) {
            p.setVideoStabilization(c.videoStabilization);
        }

        if (p.getMinExposureCompensation() <= c.mExposureCompensation && p.getMaxExposureCompensation() >= c.mExposureCompensation) {
            p.setExposureCompensation(c.mExposureCompensation);
        }

        if (p.getSupportedFocusModes() != null && p.getSupportedFocusModes().contains(c.mFocusMode)) {
            p.setFocusMode(c.mFocusMode);
        }

        p.setJpegQuality(c.jpeg_quality);
        if (c.picture_width != 0 && c.picture_height != 0) {
            p.setPictureSize(c.picture_width, c.picture_height);
        }

        setPreviewFrameRate(p, preview_framerate);
        setAntibanding(p, true);
    }

    public void close() {
        if (this.mCameraCaptureSession != null) {
            this.mCameraCaptureSession.close();
            this.mCameraCaptureSession = null;
        }

        this.mCameraHandler.obtainMessage(2).sendToTarget();
        HandlerUtil.quitSafely(this.mCameraHandler);
        ThreadUtil.join(this._CameraThread);
    }

    void doClose() {
        if (this._Camera != null) {
            this._Manager.releaseCamera(this._Camera);
            this._Camera = null;
        }

    }

    public static boolean setAntibanding(Camera.Parameters params, boolean value) {
        List supported_list = params.getSupportedAntibanding();
        String request = value ? "auto" : "off";
        String found = findString(supported_list, new String[]{request});
        if (found != null) {
            params.setAntibanding(found);
        }

        return found != null;
    }

    private static int setPreviewFrameRate5(Camera.Parameters params, final int target_fps) {
        List supported_list = params.getSupportedPreviewFrameRates();
        if (supported_list == null) {
            return -1;
        } else {
            Collections.sort(supported_list, new Comparator<Integer>() {
                private int getScore(int value) {
                    return -Math.abs(value - target_fps);
                }

                public int compare(Integer lhs, Integer rhs) {
                    return this.getScore(rhs.intValue()) - this.getScore(lhs.intValue());
                }
            });
            int actual_fps = ((Integer) supported_list.get(0)).intValue();
            Log.w("DefaultCamera", "setPreviewFrameRate: requested(" + target_fps + ") actual(" + actual_fps + ")");
            params.setPreviewFrameRate(actual_fps);
            return actual_fps;
        }
    }

    private static void setPreviewFrameRate9(Camera.Parameters params, int target_fps) {
        List supported_list = params.getSupportedPreviewFpsRange();
        if (supported_list != null && !supported_list.isEmpty()) {
            final int fps_1000 = target_fps * 1000;
            Collections.sort(supported_list, new Comparator<int[]>() {
                private int getScore(int[] value) {
                    return value[0] > fps_1000 ? -value[0] : (value[1] < fps_1000 ? -value[1] : fps_1000 + (fps_1000 - value[0]));
                }

                public int compare(int[] lhs, int[] rhs) {
                    return this.getScore(rhs) - this.getScore(lhs);
                }
            });
            int[] value = (int[]) supported_list.get(0);
            Log.v("DefaultCamera", "setPreviewFpsRange requested(" + target_fps + ")" + " actual([" + value[0] + "," + value[1] + "])");
            params.setPreviewFpsRange(value[0], value[1]);
            params.setPreviewFrameRate(30);
        }
    }

    public static void setPreviewFrameRate(Camera.Parameters params, int target_fps) {
        setPreviewFrameRate5(params, target_fps);
        if (Build.VERSION.SDK_INT >= 9) {
            setPreviewFrameRate9(params, target_fps);
        }

    }

    public static String findString(List<String> list, String... candidate_list) {
        if (list == null) {
            return null;
        }
        for (String item : candidate_list) {
            if (list.contains(item)) {
                return item;
            }
        }
        return null;
    }

    public void createCaptureSession(List output_list, SessionRequest conf, CameraCaptureSession.StateCallback state_callback, Renderer renderer, Rect crop) {
        if (this.mCameraCaptureSession != null) {
            this.mCameraCaptureSession.close();
        }

        this.mCameraCaptureSession = new DefaultCaptureSession(output_list, conf, state_callback, this, renderer, crop);
    }

    public HandlerThread getCameraThread() {
        return this._CameraThread;
    }

    class CameraThreadCallback implements Handler.Callback {
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case WHAT_DEVICE_OPEN:
                    DefaultDevice.this.doOpen();
                    break;
                case WHAT_DEVICE_CLOSE:
                    DefaultDevice.this.doClose();
            }

            return true;
        }
    }
}
