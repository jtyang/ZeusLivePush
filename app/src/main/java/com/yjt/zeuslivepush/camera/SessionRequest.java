package com.yjt.zeuslivepush.camera;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class SessionRequest implements Cloneable {
    public boolean recording;
    public int picture_width;
    public int picture_height;
    public int jpeg_quality = 100;
    public boolean videoStabilization = true;
    public int mExposureCompensation = 0;
    public String mFocusMode = "continuous-video";
    public int previewFrameRate = 30;
    public int previewWidth;
    public int previewHeight;
    public int previewFormat;
    public int displayOrientation;

    public int getPreviewDisplayWidth() {
        switch(this.displayOrientation) {
            case 90:
            case 270:
                return this.previewHeight;
            default:
                return this.previewWidth;
        }
    }

    public int getPreviewDisplayHeight() {
        switch(this.displayOrientation) {
            case 90:
            case 270:
                return this.previewWidth;
            default:
                return this.previewHeight;
        }
    }

    public SessionRequest clone() {
        try {
            return (SessionRequest)super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new AssertionError(var2);
        }
    }
}
