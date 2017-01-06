package com.yjt.zeuslivepush.camera;

import android.graphics.Rect;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class CaptureRequest {
    private CaptureRequest.OnCaptureRequestResultListener mResultListener;
    public static final int REQUESTKEY_FLASHMODE = 1;
    public static final int REQUESTKEY_ZOOM = 2;
    public static final int REQUESTKEY_AUTOFOCUS = 4;
    public static final int REQUESTKEY_TAKEPICTURE = 8;
    public static final int REQUESTKEY_CROP = 16;
    public static final int REQUESTKEY_FOCUSMODE = 32;
    public int zoom;
    public String flashMode;
    public String filename;
    public String focusMode;
    public Rect mFocusArea = new Rect();
    public Rect mCropArea = new Rect();
    private int mRequestKey;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_FAILED = 2;
    public static final int RESULT_NOTSUPPORT = 4;
    public static final int RESULT_INVALIDVALUE = 8;
    private int mResult = 1;

    public int getRequestKey() {
        return this.mRequestKey;
    }

    public CaptureRequest(int key, int value, CaptureRequest.OnCaptureRequestResultListener l) {
        this.mRequestKey = key;
        this.mResultListener = l;
        switch (key) {
            case REQUESTKEY_ZOOM:
                this.zoom = value;
                break;
            default:
                break;
        }
    }

    public CaptureRequest(int key, Object obj, CaptureRequest.OnCaptureRequestResultListener l) {
        this.mRequestKey = key;
        this.mResultListener = l;
        switch (key) {
            case REQUESTKEY_FLASHMODE:
                this.flashMode = (String) obj;
                break;
            case REQUESTKEY_AUTOFOCUS:
                this.mFocusArea = (Rect) obj;
                break;
            case REQUESTKEY_TAKEPICTURE:
                this.filename = (String) obj;
                break;
            case REQUESTKEY_CROP:
                this.mCropArea = (Rect) obj;
                break;
            case REQUESTKEY_FOCUSMODE:
                this.focusMode = (String) obj;
                break;
        }

    }

    public void setResult(int result) {
        this.mResult = result;
    }

    public void onResult() {
        if (this.mResultListener != null) {
            this.mResultListener.onCaptureResult(this.mResult);
        }

    }

    public interface OnCaptureRequestResultListener {
        void onCaptureResult(int var1);
    }
}
