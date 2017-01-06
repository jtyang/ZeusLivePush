package com.yjt.zeuslivepush.camera;

import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class CameraCharacteristics {
    public final int id;
    protected int _SensorOrientation;
    protected int _LensFacing;
    protected boolean _PreviewDataMirrored;
    protected Size[] _PreviewSizeList;
    protected Size[] mSupportedVideoSize;
    protected Size _PreferredPreviewSizeForVideo;
    protected int[] _ZoomRatioList;
    protected int _MinExposureCompensation;
    protected int _MaxExposureCompensation;
    protected float _ExposureCompensationStep;
    protected boolean _VideoStabilizationSupported;
    protected List<String> _FlashModeList;
    protected String _FlashMode;
    protected Size[] mSupportedPictureSizes;

    public CameraCharacteristics(int _id) {
        this.id = _id;
    }

    public final int getSensorOrientation() {
        return this._SensorOrientation;
    }

    public final int getLensFacing() {
        return this._LensFacing;
    }

    public final boolean isPreviewDataMirrored() {
        return this._PreviewDataMirrored;
    }

    public Size[] getSupportedVideoSize() {
        return this.mSupportedVideoSize;
    }

    public Size[] getPreviewSizeList() {
        return this._PreviewSizeList;
    }

    public Size getPreferredPreviewSizeForVideo() {
        return this._PreferredPreviewSizeForVideo;
    }

    public final int[] getZoomRatioList() {
        return this._ZoomRatioList;
    }

    public final boolean isZoomSupported() {
        return this._ZoomRatioList != null;
    }

    public int getMinExposureCompensation() {
        return this._MinExposureCompensation;
    }

    public int getMaxExposureCompensation() {
        return this._MaxExposureCompensation;
    }

    public float getExposureCompensationStep() {
        return this._ExposureCompensationStep;
    }

    public final boolean isExposureCompensationSupported() {
        return this._MaxExposureCompensation > 0 || this._MinExposureCompensation < 0;
    }

    public final boolean isVideoStabilizationSupported() {
        return this._VideoStabilizationSupported;
    }

    public List<String> getSupportedFlashMode() {
        return this._FlashModeList;
    }

    public String getDefaultFlashMode() {
        return this._FlashMode;
    }

    public final Size[] getSupportedPictureSizes() {
        return this.mSupportedPictureSizes;
    }
}
