package com.yjt.zeuslivepush.live;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

import com.yjt.zeuslivepush.camera.CameraClient;
import com.yjt.zeuslivepush.camera.CameraSurfaceController;
import com.yjt.zeuslivepush.quirks.Quirk;
import com.yjt.zeuslivepush.quirks.QuirksStorage;

import java.util.Map;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/6
 */
public class ZeusLiveManager {
    private static final int STATUS_PREPARED = 1;
    private static final int STATUS_INITIALIZED = 3;
    private static final int STATUS_RELEASED = 4;


    private Context mContext;
    private CameraClient mCameraClient;
    private Surface mPreviewSurface;
    private CameraSurfaceController mSurfaceController;

    private final Resolution mPreviewResolution = new Resolution();
    private LiveRecordConfig mConfig;

    private int mStatus = STATUS_RELEASED;

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        this.mCameraClient = new CameraClient();
//        this.mCameraClient.setCallback(this.mCameraCallback);
//        this.mCameraClient.setOnErrorListener(this.mCameraErrorListener);

        this.mStatus = STATUS_INITIALIZED;
    }

    public void prepare(Map<String, Object> params, Surface previewSurface) {
        synchronized (this) {
            if (this.mStatus == STATUS_PREPARED) {
                this.reset();
            }

            if (this.mStatus == STATUS_INITIALIZED) {
                this.mPreviewSurface = previewSurface;

                try {
                    this.mSurfaceController = this.mCameraClient.addSurface(previewSurface);
                } catch (Exception var9) {
                    ;
                }

                if (this.mPreviewResolution.size() > 0) {
                    this.mSurfaceController.setResolution(this.mPreviewResolution.getWidth(), this.mPreviewResolution.getHeight());
                }

                this.mSurfaceController.setDisplayMethod(CameraSurfaceController.ScaleEnabled);
                int displayMethod = this.mSurfaceController.getDisplayMethod();
                if (this.mCameraClient.isFrontCamera() && QuirksStorage.getBoolean(Quirk.FRONT_CAMERA_PREVIEW_DATA_MIRRORED)) {
                    this.mSurfaceController.setDisplayMethod(displayMethod | CameraSurfaceController.Mirrored);
                } else if ((displayMethod & CameraSurfaceController.Mirrored) != 0) {
                    this.mSurfaceController.setDisplayMethod(displayMethod ^ CameraSurfaceController.Mirrored);//128=0x80
                }

                this.mSurfaceController.setVisible(true);
                WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
                Point size = new Point();
                wm.getDefaultDisplay().getSize(size);
                this.mConfig = LiveRecordConfig.newInstance(params, new Resolution(size.x, size.y), wm.getDefaultDisplay().getRotation());
//                DQLiveWatermark watermark = this.mConfig.getWatermark();
//                if (watermark != null) {
//                    this.mBeautyRenderer.setWatermark(watermark.getWatermarkUrl(), watermark.getPaddingX(), watermark.getPaddingY(), watermark.getSite());
//                }

                this.mCameraClient.setDisplayRotation(this.mConfig.getDisplayRotation());
                this.mCameraClient.setContentSize(this.mConfig.getOutputSize().getWidth(), this.mConfig.getOutputSize().getHeight());
                this.mCameraClient.setCameraFacing(this.mConfig.getCameraInitialFacing());
                this.mCameraClient.startPreview();
//                if (this.mVideoStream != null) {
//                    this.mVideoStream.setMirrored(this.mCameraClient.isFrontCamera());
//                }
            } else if (this.mStatus != STATUS_PREPARED) {
                throw new IllegalStateException("This method could be called after init()");
            }

        }
    }

    public void setPreviewSize(int width, int height) {
        if (this.mSurfaceController != null && this.mContext != null) {
            switch (this.mConfig.getDisplayRotation()) {
                case 90:
                case 270:
                    WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
                    switch (wm.getDefaultDisplay().getRotation()) {
                        case 0:
                        case 2:
                            this.mPreviewResolution.setWidth(height);
                            this.mPreviewResolution.setHeight(width);
                            this.mSurfaceController.setResolution(height, width);
                            break;
                        case 1:
                        case 3:
                            this.mPreviewResolution.setWidth(width);
                            this.mPreviewResolution.setHeight(height);
                            this.mSurfaceController.setResolution(width, height);
                    }
                    break;
                case 0:
                case 180:
                default:
                    this.mPreviewResolution.setWidth(width);
                    this.mPreviewResolution.setHeight(height);
                    this.mSurfaceController.setResolution(width, height);
                    break;
            }
        }
    }

    public void startRecord(String outputUrl) {
        synchronized (this) {
        }
    }

    public void stopRecord() {
        synchronized (this) {
//            if (this.isRecording) {
//
//            }
        }
    }

    public void reset() {
        synchronized (this) {
            if (this.mStatus == STATUS_PREPARED) {
                this.mConfig = null;
                Log.d("DQLiveMediaRecorder", "====== reset =======");
                if (this.mSurfaceController != null) {
                    this.mSurfaceController.setVisible(false);
                }

                if (this.mCameraClient != null) {
                    this.mCameraClient.removeSurface(this.mPreviewSurface);
                    this.mCameraClient.stopPreview();
                }
            }

        }
    }

    public void release() {
        synchronized (this) {
//            LiveEventBus.getInstance().unSpecifyDispatcher();
//            this.mFlagManager.removeAllFlag();
//            if (this.mDataTrunkStatistics != null) {
//                this.mDataTrunkStatistics.cancel();
//            }
//
//            if (this.isRecording) {
//                this.stopRecord();
//            }

            if (this.mStatus == STATUS_PREPARED) {
                this.reset();
            }

            if (this.mStatus == STATUS_INITIALIZED) {
//                if (this.mRecorderTask != null) {
//                    this.mRecorderTask.dispose();
//                    this.mRecorderTask = null;
//                }

//                this.mContext.unregisterReceiver(this.mConnectionReceiver);
                this.mCameraClient.setCallback(null);
                this.mCameraClient.setOnErrorListener(null);
                this.mCameraClient.onDestroy();
                this.mCameraClient = null;
                this.mStatus = STATUS_RELEASED;
            }

//            this.mReporter = null;
//            this.mFlagController.clear();
//            this.mFlagController = null;
        }
    }


}
