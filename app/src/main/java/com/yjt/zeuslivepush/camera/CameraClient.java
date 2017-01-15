package com.yjt.zeuslivepush.camera;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.duanqu.qupai.camera.PreviewSource9;
import com.duanqu.qupai.face.impl.FaceDetectSource;
import com.duanqu.qupai.quirks.Quirk;
import com.duanqu.qupai.quirks.QuirksStorage;
import com.yjt.zeuslivepush.camera.impl.DefaultManager;
import com.yjt.zeuslivepush.camera.opengl.QpEgl11Surface;
import com.yjt.zeuslivepush.camera.permission.PermissionChecker;
import com.yjt.zeuslivepush.render.Renderer;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/7
 */
public class CameraClient {
    private static final boolean isDebug = false;
    private static final String TAG = "CameraClient";
    public static final int MODE_SURFACE = 1;
    public static final int MODE_GLSURFACE = 2;
    public static final int MODE_BUFFER = 4;
    public static final int ZOOM_SCALE = 100;

    private final int Status_None = 0;
    private final int Status_OnPreview = 1;

    private CameraCaptureSession _CameraCaptureSession;
    private final DefaultManager _CameraManager;
    private CameraClient.CameraLoader _Loader;

    private int mMode;
    private Renderer mRendererCallback;

    private int mStatus;
    private ArrayList<CaptureRequest> mCaptureRequestList;
    private ArrayList<QpEgl11Surface> mOutputSurfaces;
    private int mReleasableBufferCount;
    private SurfaceHolder mHolder;
    private SessionRequest _Config;
    private CameraClient.OnErrorListener _OnErrorListener;
    private CameraClient.Callback _Callback;

    public float zoomRatio;
    public int _ContentWidth;
    public int _ContentHeight;
    private CameraCharacteristics _Chara;
    private CameraDevice _CameraDevice;
    private int _DisplayRotation;
    private final CameraTransform _Transform;
    private final Rect _PreviewDisplayCrop;
    private final RectF _PreviewDataCrop;
    private final Matrix _DisplayMatrix;
    private final RectF _FocusAreaF;
    private final Rect _FocusRect;
    private PreviewSource9 _PreviewSource;
    private FaceDetectSource _DetectSource;
    private int _CameraID;
    private AutoFocusCallback _AutoFocusCallback;
    private CameraClient.SessionLoader _SessionLoader;

    public boolean hasSession() {
        return this._CameraCaptureSession != null;
    }

    public boolean configureComplete() {
        return this._CameraCaptureSession == null ? false : this._CameraCaptureSession.configureComplete();
    }

    private CameraClient(PermissionChecker permission) {
        this.mMode = MODE_GLSURFACE;
        this.mRendererCallback = null;

        this.mStatus = Status_None;
        this.mCaptureRequestList = new ArrayList();
        this.mOutputSurfaces = new ArrayList();
        this.mReleasableBufferCount = 10;
        this.zoomRatio = 1.0F;
        this._ContentWidth = 480;
        this._ContentHeight = 480;
        this._Transform = new CameraTransform();
        this._PreviewDisplayCrop = new Rect();
        this._PreviewDataCrop = new RectF();
        this._DisplayMatrix = new Matrix();
        this._FocusAreaF = new RectF();
        this._FocusRect = new Rect();
        this._CameraManager = new DefaultManager(permission);
    }

    public CameraClient() {
        this(null);
    }

    public void setReleasableBufferCount(int n) {
        this.mReleasableBufferCount = n;
    }

    public void setMode(int mode) {
        this.mMode = mode;
    }

    public void setRendererCallback(Renderer renderer) {
        this.mRendererCallback = renderer;
        if (this._CameraCaptureSession != null) {
            this._CameraCaptureSession.setRenderer(renderer);
        }
    }

    public void removeSurface(SurfaceHolder holder) {
        boolean found = false;
        for (QpEgl11Surface surface : this.mOutputSurfaces) {
            if (surface.getSurfaceHolder() == holder) {
                found = true;
                this.mOutputSurfaces.remove(surface);
                if (this._CameraCaptureSession != null) {
                    this._CameraCaptureSession.removeQpEglSurface(surface);
                }
                break;
            }
        }
        if (!found && isDebug) {
            throw new RuntimeException("The surface has not been added!");
        }
    }

    public CameraSurfaceController addSurface(SurfaceHolder holder) {
        for (QpEgl11Surface surface : this.mOutputSurfaces) {
            if (surface.getSurfaceHolder() == holder) {
                throw new RuntimeException("The surface has been added!");
            }
        }

        QpEgl11Surface surface = new QpEgl11Surface(holder);
        this.mOutputSurfaces.add(surface);

        if (this._CameraCaptureSession != null) {
            this._CameraCaptureSession.addQpEglSurface(surface);
        }

        return surface.getSurfaceController();
    }

    public CameraSurfaceController addSurface(Surface sur) {
        Log.d("MediaCodecRecorder", " ====== call CameraClient.addSurface() ======");

        for (QpEgl11Surface surface : this.mOutputSurfaces) {
            if (surface.getSurface() == sur) {
                throw new RuntimeException("The surface has been added!");
            }
        }

        QpEgl11Surface surface = new QpEgl11Surface(sur);
        this.mOutputSurfaces.add(surface);

        if (this._CameraCaptureSession != null) {
            this._CameraCaptureSession.addQpEglSurface(surface);
        }

        return surface.getSurfaceController();
    }

    public void removeSurface(Surface surface) {
        boolean found = false;
        for (QpEgl11Surface surf : this.mOutputSurfaces) {
            if (surf.getSurface() == surface) {
                found = true;
                this.mOutputSurfaces.remove(surf);
                if (this._CameraCaptureSession != null) {
                    this._CameraCaptureSession.removeQpEglSurface(surf);
                }
                break;
            }
        }
        if (!found && isDebug) {
            throw new RuntimeException("The surface has not been added!");
        }
    }

    public void setupSurfaceHolder(SurfaceHolder holder) {
        this.mHolder = holder;
        this.createCaptureSessionIfPossible();
    }

    public SessionRequest getSessionRequest() {
        return this._Config;
    }

    public void setOnErrorListener(CameraClient.OnErrorListener listener) {
        this._OnErrorListener = listener;
    }

    public void setCallback(CameraClient.Callback callback) {
        this._Callback = callback;
    }

    private void dispatchCaptureUpdate() {
        if (this._Callback != null) {
            this._Callback.onCaptureUpdate(this);
        }
    }

    private void dispatchDeviceAttach() {
        if (this._Callback != null) {
            this._Callback.onDeviceAttach(this);
        }
    }

    private void dispatchSessionAttach() {
        if (this._Callback != null) {
            this._Callback.onSessionAttach(this);
        }
    }

    private void dispatchSessionDetach() {
        if (this._Callback != null) {
            this._Callback.onSessionDetach(this);
        }
    }

    private void dispatchDeviceDetach() {
        if (this._Callback != null) {
            this._Callback.onDeviceDetach(this);
        }
    }

    /**
     * @deprecated
     */
    public void setZoom(float zoom) {
        CaptureRequest.OnCaptureRequestResultListener l = new CaptureRequest.OnCaptureRequestResultListener() {
            public void onCaptureResult(int result) {
                CameraClient.this.dispatchCaptureUpdate();
            }
        };
        this.setZoom(zoom, l);
    }

    private int getSuitableZoomIndex(int[] zoom_list, int target_zoom) {
        int zoom_max = zoom_list[zoom_list.length - 1];
        target_zoom = Math.min(target_zoom, zoom_max);
        int best_diff = zoom_max;
        int best_idx = 0;
        int i = 0;

        for (int count = zoom_list.length; i < count; ++i) {
            int current_value = zoom_list[i];
            int current_diff = Math.abs(current_value - target_zoom);
            if (current_diff < best_diff) {
                best_idx = i;
                best_diff = current_diff;
            }
        }
        return best_idx;
    }

    public boolean setZoom(float zoom, CaptureRequest.OnCaptureRequestResultListener l) {
        if (this._CameraCaptureSession != null && this._Chara.isZoomSupported()) {
            int zoom_scaled = (int) (zoom * 100.0F);
            int[] zoom_list = this._Chara.getZoomRatioList();
            int best_idx = this.getSuitableZoomIndex(zoom_list, zoom_scaled);
            this.zoomRatio = zoom;
            CaptureRequest request = new CaptureRequest(2, best_idx, l);
            this._CameraCaptureSession.setCaptureRequest(request);
            return true;
        } else {
            return false;
        }
    }

    public void setContentSize(int w, int h) {
        if (this._ContentWidth != w || this._ContentHeight != h) {
            this._ContentWidth = w;
            this._ContentHeight = h;
            if (this._CameraDevice != null) {
                if ((this.mMode & MODE_BUFFER) != 0) {
                    this.destroyCaptureSessionChecked();
                    if (this.configurePreviewSize(this._Chara)) {
                        this.createCaptureSessionIfPossible();
                    }
                } else {
                    this.fillConfiguration(this._Config);
                    CaptureRequest req = new CaptureRequest(CaptureRequest.REQUESTKEY_CROP, this._PreviewDisplayCrop, (CaptureRequest.OnCaptureRequestResultListener) null);
                    if (this._CameraCaptureSession != null) {
                        this._CameraCaptureSession.setCaptureRequest(req);
                    } else {
                        this.mCaptureRequestList.add(req);
                    }
                }
            }
        }
    }

    public CameraCharacteristics getCharacteristics() {
        return this._Chara;
    }

    private void onDeviceOpened(CameraDevice camera) {
        this._Loader = null;
        CameraCharacteristics chara = camera._Info;
        this._Chara = chara;
        this._Config = new SessionRequest();
        this._Config.recording = QuirksStorage.getBoolean(Quirk.CAMERA_RECORDING_HINT);
        this._Config.displayOrientation = getDisplayOrientation(this._DisplayRotation, chara);
        this._CameraDevice = camera;
        this.dispatchDeviceAttach();
        this.zoomRatio = 1.0F;
        if (this._Config.previewWidth != 0 && this._Config.previewHeight != 0 || this.configurePreviewSize(chara)) {
            this.fillConfiguration(this._Config);
            this.createCaptureSessionIfPossible();
        }
    }

    private boolean configurePreviewSize(CameraCharacteristics chara) {
        if ((this.mMode & MODE_BUFFER) != 0) {
            boolean preview_size = QuirksStorage.getBoolean(Quirk.CAMERA_ASPECT_RATIO_DEDUCTION);
            Size preview_size1 = (new PreviewSizeChooser(preview_size)).choose(chara, this._ContentWidth, this._ContentHeight, this._Config.displayOrientation);
            if (preview_size1 == null) {
                Log.e("CameraClient", "no preview size for " + this._ContentWidth + "x" + this._ContentHeight + " " + this._Config.displayOrientation);
                return false;
            }

            this._Config.previewWidth = preview_size1.width;
            this._Config.previewHeight = preview_size1.height;
        } else {
            for (int var4 = 0; var4 < this._Chara.getPreviewSizeList().length; ++var4) {
                Log.d("preview_size", "" + this._Chara.getPreviewSizeList()[var4]);
            }

            Size var5 = (new PreviewSizeChooser(false)).choose(chara, this._ContentWidth, this._ContentHeight, this._Config.displayOrientation);
            if (var5 == null) {
                Log.d("CameraClient", "no preview size for " + this._ContentWidth + "x" + this._ContentHeight + " " + this._Config.displayOrientation);
                this._Config.previewWidth = this._Chara.getPreviewSizeList()[this._Chara.getPreviewSizeList().length - 1].width;
                this._Config.previewHeight = this._Chara.getPreviewSizeList()[this._Chara.getPreviewSizeList().length - 1].height;
                this.onIllegalOutputResolution(this._CameraDevice, this._Config.previewWidth, this._Config.previewHeight);
                return false;
            }

            this._Config.previewWidth = var5.width;
            this._Config.previewHeight = var5.height;
            Log.d("preview_size", "_Config.previewWidth" + this._Config.previewWidth + "_Config.previewHeight" + this._Config.previewHeight);
        }

        return true;
    }

    private void onDeviceError(CameraDevice device) {
        int id = device.getID();
        device.close();
        this._SessionLoader = null;
        this.mStatus = Status_None;
        if (this._OnErrorListener != null) {
            this._OnErrorListener.onError(this, id);
        }
    }

    private void onIllegalOutputResolution(CameraDevice device, int supportWidth, int supportedHeight) {
        device.close();
        this._SessionLoader = null;
        this.mStatus = Status_None;
        if (this._OnErrorListener != null) {
            this._OnErrorListener.onIllegalOutputResolution(supportWidth, supportedHeight);
        }
    }

    private void onConfigureFailure() {
        if (this._OnErrorListener != null) {
            this._OnErrorListener.onError(this, this._CameraDevice.getID());
        }
    }

    private void createCaptureSessionIfPossible() {
        if (this._CameraCaptureSession == null && this._SessionLoader == null) {
            if (this._CameraDevice != null) {
                ArrayList output_list = new ArrayList();
                if ((this.mMode & MODE_SURFACE) != 0) {
                    if (this.mHolder == null) {
                        return;
                    }

                    output_list.add(this.mHolder);
                }

                if ((this.mMode & MODE_BUFFER) != 0) {
                    Rect temp2 = new Rect((int) this._PreviewDataCrop.left, (int) this._PreviewDataCrop.top, (int) this._PreviewDataCrop.right, (int) this._PreviewDataCrop.bottom);
                    this._PreviewSource = new PreviewSource9(this._Config.previewWidth, this._Config.previewHeight, temp2, this._DisplayMatrix, this.mReleasableBufferCount);
                    output_list.add(this._PreviewSource);
                }

                this._SessionLoader = new CameraClient.SessionLoader();
                this._CameraDevice.createCaptureSession(output_list, this._Config.clone(), this._SessionLoader, this.mRendererCallback, this._PreviewDisplayCrop);
            }
        }
    }

    private void destroyCaptureSessionChecked() {
        if (this._SessionLoader != null) {
            this._SessionLoader.cancel();
            this._SessionLoader = null;
        }

        if (this._CameraCaptureSession != null) {
            Log.i("CameraClient", "CameraCaptureSession.close");
            this._CameraCaptureSession.close();
            this._CameraCaptureSession = null;
            this.dispatchSessionDetach();
        } else {
            Log.i("CameraClient", "CameraCaptureSession null");
        }

    }

    public void setDisplayRotation(int rotation) {
        this._DisplayRotation = rotation;
    }

    public static int getDisplayOrientation(int display_rotation, CameraCharacteristics info) {
        int result;
        if (info.getLensFacing() == 1) {
            result = (info.getSensorOrientation() + display_rotation) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.getSensorOrientation() - display_rotation + 360) % 360;
        }

        return result;
    }

    public Rect getPreviewDisplayCrop() {
        return this._PreviewDisplayCrop;
    }

    protected void fillConfiguration(SessionRequest c) {
        int width = 0;
        int height = 0;
        switch (c.displayOrientation) {
            case 0:
            case 180:
                width = this._Config.previewWidth;
                height = this._Config.previewHeight;
                break;
            case 90:
            case 270:
                width = this._Config.previewHeight;
                height = this._Config.previewWidth;
        }

        float scale_factor;
        if ((this.mMode & MODE_BUFFER) != 0) {
            Assert.assertTrue(Boolean.valueOf(width >= this._ContentWidth && height >= this._ContentHeight));
            this._PreviewDisplayCrop.set(0, 0, this._ContentWidth, this._ContentHeight);
            scale_factor = 1.0F;
        } else if (this._ContentWidth * height > this._ContentHeight * width) {
            scale_factor = (float) this._ContentWidth / (float) width;
            this._PreviewDisplayCrop.set(0, 0, width, this._ContentHeight * width / this._ContentWidth);
        } else {
            scale_factor = (float) this._ContentHeight / (float) height;
            this._PreviewDisplayCrop.set(0, 0, this._ContentWidth * height / this._ContentHeight, height);
        }

        this._Transform.setCameraInfo(this._Chara);
        this._Transform.setPreviewDisplayRotation(c.displayOrientation);
        this._Transform.setPreviewSize(c.previewWidth, c.previewHeight);
        this._Transform.update(scale_factor);
        this._Transform.getDisplayMatrix(this._DisplayMatrix);
        this._PreviewDataCrop.set(this._PreviewDisplayCrop);
        this._Transform.mapScreenToPreviewData(this._PreviewDataCrop);
    }

    private final void setScreenFocusArea(float l, float t, float r, float b) {
        this._FocusAreaF.set(l, t, r, b);
        this._Transform.mapScreenToOriginalPreview(this._FocusAreaF);
        this.setFocusArea(this._FocusAreaF);
    }

    private void setFocusArea(RectF rect) {
        int center_x = this._Config.previewWidth / 2;
        int center_y = this._Config.previewHeight / 2;
        this._FocusRect.left = (int) ((rect.left - (float) center_x) / (float) center_x * 1000.0F);
        this._FocusRect.right = (int) ((rect.right - (float) center_x) / (float) center_x * 1000.0F);
        this._FocusRect.top = (int) ((rect.top - (float) center_y) / (float) center_y * 1000.0F);
        this._FocusRect.bottom = (int) ((rect.bottom - (float) center_y) / (float) center_y * 1000.0F);
    }

    public void setFocusMode(String focusMode, CaptureRequest.OnCaptureRequestResultListener l) {
        if (this._CameraCaptureSession != null) {
            CaptureRequest req = new CaptureRequest(CaptureRequest.REQUESTKEY_FOCUSMODE, focusMode, l);
            this._CameraCaptureSession.setCaptureRequest(req);
        }

    }

    public boolean isFocusModeSupported(String focusMode) {
        if (this._CameraDevice != null && this._CameraDevice._Camera != null) {
            Camera.Parameters parameters = this._CameraDevice._Camera.getParameters();
            return parameters.getSupportedFocusModes().contains(focusMode);
        } else {
            throw new IllegalStateException("Camera not opened");
        }
    }

    public boolean isFlashSupported(String flashMode) {
        if (this._CameraDevice != null && this._CameraDevice._Camera != null) {
            Camera.Parameters parameters = this._CameraDevice._Camera.getParameters();
            return parameters.getSupportedFlashModes().contains(flashMode);
        } else {
            throw new IllegalStateException("Camera not opened");
        }
    }

    public boolean autoFocus(float x, float y, CameraSurfaceController sur_controller) {
        if (this._CameraCaptureSession == null) {
            return false;
        } else if (this.isFrontCamera()) {
            return false;
        } else {
            boolean width = false;
            boolean height = false;
            int width1;
            int height1;
            switch (this._Config.displayOrientation) {
                case 0:
                case 180:
                    width1 = this._Config.previewWidth;
                    height1 = this._Config.previewHeight;
                    break;
                case 90:
                case 270:
                    width1 = this._Config.previewHeight;
                    height1 = this._Config.previewWidth;
            }

            if (sur_controller != null && sur_controller.getWidth() != 0 && sur_controller.getHeight() != 0) {
                Rect left = new Rect();
                sur_controller.calculateViewPort(this._PreviewDisplayCrop.width(), this._PreviewDisplayCrop.height(), left);
                if ((sur_controller.getDisplayMethod() & 32) != 0) {
                    x = (x * (float) sur_controller.getWidth() - (float) left.left) * (float) this._ContentWidth / (float) left.width();
                    y = (y * (float) sur_controller.getHeight() - (float) left.top) * (float) this._ContentHeight / (float) left.height();
                } else {
                    x = x * (float) left.width() + (float) left.left;
                    y = y * (float) left.height() + (float) left.top;
                }
            } else {
                x *= (float) this._ContentWidth;
                y *= (float) this._ContentHeight;
            }

            float left1 = Math.max(0.0F, x - 50.0F);
            float top = Math.max(0.0F, y - 50.0F);
            float right = Math.min((float) this._ContentWidth, x + 50.0F);
            float bottom = Math.min((float) this._ContentHeight, y + 50.0F);
            Log.v("CameraClient", String.format("screen focus-area: (%4.0f %4.0f, %4.0f %4.0f)", new Object[]{Float.valueOf(left1), Float.valueOf(top), Float.valueOf(right), Float.valueOf(bottom)}));
            this.setScreenFocusArea(left1, top, right, bottom);
            CaptureRequest req = new CaptureRequest(4, this._FocusRect, new CaptureRequest.OnCaptureRequestResultListener() {
                public void onCaptureResult(int result) {
                    if (CameraClient.this._AutoFocusCallback != null) {
                        CameraClient.this._AutoFocusCallback.onAutoFocus(true, (CameraDevice) null);
                    }

                }
            });
            this._CameraCaptureSession.setCaptureRequest(req);
            return true;
        }
    }

    public PreviewSource9 getSource() {
        return this._PreviewSource;
    }

    public FaceDetectSource getDetectSource() {
        return this._DetectSource;
    }

    public int getCameraID() {
        return this._CameraDevice != null ? this._CameraDevice._Info.id : (this._Loader != null ? this._Loader.id : -1);
    }

    private void setCameraID(int id) {
        if (this.mStatus != Status_OnPreview || this.getCameraID() != id) {
            Log.i("CameraClient", "setCameraID destroyCaptureSessionChecked");
            this.destroyCaptureSessionChecked();
            if (this._Loader != null) {
                this._Loader.cancel();
                this._Loader = null;
            }

            if (this._CameraDevice != null) {
                this._CameraDevice.close();
                this._CameraDevice = null;
                this.dispatchDeviceDetach();
            }

            this._PreviewSource = null;
            if (id < 0) {
                this.mStatus = Status_None;
            } else {
                this.mStatus = Status_OnPreview;
                this._Loader = new CameraClient.CameraLoader(id);
                this._CameraManager.openCamera(id, this._Loader);
            }
        }
    }

    public int nextCamera() {
        if (this._Loader == null && this._SessionLoader == null) {
            int camera_count = this._CameraManager.getCameraCount();
            if (camera_count == 0) {
                Log.e("CameraClient", "camera count is zero");
                return -1;
            } else {
                this._CameraID = (this._CameraID + 1) % camera_count;
                this.setCameraID(this._CameraID);
                return this._CameraID;
            }
        } else {
            return -1;
        }
    }

    public void takePicture(String url, CaptureRequest.OnCaptureRequestResultListener l) {
        if (this._CameraCaptureSession != null) {
            CaptureRequest req = new CaptureRequest(CaptureRequest.REQUESTKEY_TAKEPICTURE, url, l);
            this._CameraCaptureSession.setCaptureRequest(req);
        }

    }

    public void setFlashMode(String flash_mode, CaptureRequest.OnCaptureRequestResultListener l) {
        CaptureRequest req = new CaptureRequest(CaptureRequest.REQUESTKEY_FLASHMODE, flash_mode, l);
        if (this._CameraCaptureSession != null) {
            this._CameraCaptureSession.setCaptureRequest(req);
        } else if (this.mStatus == Status_OnPreview) {
            this.mCaptureRequestList.add(req);
        }

    }

    public void turnLight() {
        if (this._CameraCaptureSession != null) {
            Camera.Parameters parameters = this._CameraCaptureSession._Camera.getParameters();
            if (parameters != null) {
                List flashModes = parameters.getSupportedFlashModes();
                String flashMode = parameters.getFlashMode();
                if (flashModes != null) {
                    if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
                        this.turnLightOn(flashMode, flashModes, parameters);
                    } else {
                        this.turnLightOff(flashMode, flashModes, parameters);
                    }

                }
            }
        }
    }

    public boolean isLightOn() {
        if (this._CameraCaptureSession == null) {
            return false;
        } else {
            Camera.Parameters parameters = this._CameraCaptureSession._Camera.getParameters();
            if (parameters == null) {
                return false;
            } else {
                List flashModes = parameters.getSupportedFlashModes();
                String flashMode = parameters.getFlashMode();
                return flashModes == null ? false : Camera.Parameters.FLASH_MODE_OFF.equals(flashMode);
            }
        }
    }

    public void turnLightOn(String flashMode, List<String> flashModes, Camera.Parameters parameters) {
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode) && flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            this._CameraCaptureSession._Camera.setParameters(parameters);
        }

    }

    public void turnLightOff(String flashMode, List<String> flashModes, Camera.Parameters parameters) {
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                this._CameraCaptureSession._Camera.setParameters(parameters);
            } else {
                Log.e("CameraClient", "FLASH_MODE_OFF not supported");
            }
        }

    }

    public boolean isFrontCamera() {
        return this._CameraID == this._CameraManager.findByFacing(1);
    }

    public void onSinkChange(CameraSink sink) {
        this.destroyCaptureSessionChecked();
        this.createCaptureSessionIfPossible();
    }

    public void setCameraFacing(int facing) {
        int camera_id = this._CameraManager.findByFacing(facing);
        if (camera_id < 0) {
            Log.e("CameraClient", "no camera with facing " + facing);
        } else {
            this._CameraID = camera_id;
        }
    }

    public void startPreview() {
        this.setCameraID(this._CameraID);
    }

    public void stopPreview() {
        this.setCameraID(-1);
    }

    public void onDestroy() {
    }

    public void setAutoFocusCallback(AutoFocusCallback callback) {
        this._AutoFocusCallback = callback;
    }

    private void onSessionConfigured(CameraCaptureSession session) {
        Assert.assertNull(this._CameraCaptureSession);
        this._SessionLoader = null;
        this._CameraCaptureSession = session;
        this._CameraCaptureSession.setAutoFocusCallback(this._AutoFocusCallback);

        for (QpEgl11Surface req : this.mOutputSurfaces) {
            this._CameraCaptureSession.addQpEglSurface(req);
        }
        for (CaptureRequest req1 : this.mCaptureRequestList) {
            this._CameraCaptureSession.setCaptureRequest(req1);
        }
        this.mCaptureRequestList.clear();
        this.dispatchSessionAttach();
    }

    private class SessionLoader extends CameraCaptureSession.StateCallback {
        private boolean _Cancelled;

        private SessionLoader() {
        }

        public void onConfigureFailed(CameraCaptureSession session) {
            if (!this._Cancelled) {
                CameraClient.this._SessionLoader = null;
                CameraClient.this.onConfigureFailure();
            }
        }

        public void onConfigured(CameraCaptureSession session) {
            if (this._Cancelled) {
                session.close();
            } else {
                CameraClient.this.onSessionConfigured(session);
            }
        }

        public void cancel() {
            this._Cancelled = true;
        }
    }

    private class CameraLoader implements CameraDevice.StateCallback {
        public final int id;
        private boolean _Canceled;

        public CameraLoader(int _id) {
            this.id = _id;
        }

        public void cancel() {
            this._Canceled = true;
        }

        public void onOpened(CameraDevice camera) {
            if (this._Canceled) {
                camera.close();
            } else {
                CameraClient.this.onDeviceOpened(camera);
            }
        }

        public void onError(CameraDevice camera, int error) {
            if (this._Canceled) {
                camera.close();
            } else {
                CameraClient.this.onDeviceError(camera);
            }
        }
    }

    public interface Callback {
        void onDeviceAttach(CameraClient var1);

        void onSessionAttach(CameraClient var1);

        void onCaptureUpdate(CameraClient var1);

        void onSessionDetach(CameraClient var1);

        void onDeviceDetach(CameraClient var1);
    }

    public interface OnErrorListener {
        void onError(CameraClient var1, int var2);

        void onIllegalOutputResolution(int var1, int var2);
    }
}
