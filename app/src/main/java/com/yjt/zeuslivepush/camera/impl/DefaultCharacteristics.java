package com.yjt.zeuslivepush.camera.impl;

import android.hardware.Camera;

import com.yjt.zeuslivepush.camera.CameraCharacteristics;
import com.yjt.zeuslivepush.camera.Size;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/8
 */
public class DefaultCharacteristics extends CameraCharacteristics {
    protected boolean _Initialized;

    public DefaultCharacteristics(int id, Camera.CameraInfo info) {
        super(id);
        this._LensFacing = info.facing;
        this._SensorOrientation = info.orientation;
    }

    public boolean isInitialized() {
        return this._Initialized;
    }

    public void initialize(Camera.Parameters params) {
        Comparator<Size> cmp = new Comparator<Size>() {
            public int compare(Size lhs, Size rhs) {
                int lmin = Math.min(lhs.width, lhs.height);
                int rmin = Math.min(lhs.width, lhs.height);
                return lmin != rmin ? lmin - rmin : lhs.width * lhs.height - rhs.width * rhs.height;
            }
        };
        this._PreviewSizeList = simplifySizeList(params.getSupportedPreviewSizes());
        Arrays.sort(this._PreviewSizeList, cmp);
        android.hardware.Camera.Size preferred_preview_size = params.getPreferredPreviewSizeForVideo();
        if (preferred_preview_size != null) {
            this._PreferredPreviewSizeForVideo = simplifySize(preferred_preview_size);
        }

        if (params.isZoomSupported()) {
            this._ZoomRatioList = toArray(params.getZoomRatios());
        }

        this._ExposureCompensationStep = params.getExposureCompensationStep();
        this._MinExposureCompensation = params.getMinExposureCompensation();
        this._MaxExposureCompensation = params.getMaxExposureCompensation();
        this.mSupportedPictureSizes = simplifySizeList(params.getSupportedPictureSizes());
        this._VideoStabilizationSupported = params.isVideoStabilizationSupported();
        this._FlashModeList = params.getSupportedFlashModes();
        this._FlashMode = params.getFlashMode();
        this._Initialized = true;
    }

    static Size simplifySize(android.hardware.Camera.Size size) {
        return new Size(size.width, size.height);
    }

    static Size[] simplifySizeList(List<Camera.Size> size_list) {
        Size[] array = new Size[size_list.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = simplifySize((android.hardware.Camera.Size) size_list.get(i));
        }
        return array;
    }

    static int[] toArray(List<Integer> list) {
        if (list != null && !list.isEmpty()) {
            int[] array = new int[list.size()];
            for (int i = 0; i < array.length; ++i) {
                array[i] = ((Integer) list.get(i)).intValue();
            }
            return array;
        } else {
            return null;
        }
    }
}
