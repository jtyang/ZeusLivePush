package com.yjt.zeuslivepush.camera;

import android.graphics.Matrix;
import android.graphics.RectF;

import junit.framework.Assert;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class CameraTransform {
    private int _PreviewDisplayRotation;
    private CameraCharacteristics _CameraInfo;
    private int _PreviewWidth;
    private int _PreviewHeight;
    private final Matrix _PreviewDataToDisplay = new Matrix();
    private final Matrix _DisplayToPreviewData = new Matrix();

    public void setPreviewDisplayRotation(int value) {
        if (value != 0 && value != 90 && value != 180 && value != 270) {
            throw new IllegalArgumentException("unsupported rotation: " + value);
        } else {
            this._PreviewDisplayRotation = value;
        }
    }

    public void setCameraInfo(CameraCharacteristics chara) {
        this._CameraInfo = chara;
    }

    public void setPreviewSize(int w, int h) {
        if (w % 2 == 0 && h % 2 == 0) {
            this._PreviewWidth = w;
            this._PreviewHeight = h;
        } else {
            throw new IllegalArgumentException("unsupported size: " + w + "x" + h);
        }
    }

    public void update(float scale_factor) {
        this._PreviewDataToDisplay.reset();
        if (this._CameraInfo.getLensFacing() == 1 && !this._CameraInfo.isPreviewDataMirrored()) {
            this._PreviewDataToDisplay.postScale(-1.0F, 1.0F, (float) (this._PreviewWidth / 2), 0.0F);
        }

        int tx;
        int ty;
        switch (this._PreviewDisplayRotation) {
            case 0:
            case 180:
                tx = this._PreviewWidth / 2;
                ty = this._PreviewHeight / 2;
                break;
            case 90:
            case 270:
                tx = this._PreviewHeight / 2;
                ty = this._PreviewWidth / 2;
                break;
            default:
                throw new AssertionError("unsupported rotation: " + this._PreviewDisplayRotation);
        }

        this._PreviewDataToDisplay.postTranslate((float) (-this._PreviewWidth / 2), (float) (-this._PreviewHeight / 2));
        this._PreviewDataToDisplay.postRotate((float) this._PreviewDisplayRotation);
        this._PreviewDataToDisplay.postTranslate((float) tx, (float) ty);
        boolean succ = this._PreviewDataToDisplay.invert(this._DisplayToPreviewData);
        Assert.assertTrue(Boolean.valueOf(succ));
    }

    public void mapScreenToPreviewData(float[] p) {
        this._DisplayToPreviewData.mapPoints(p);
    }

    public void mapScreenToPreviewData(RectF rect) {
        this._DisplayToPreviewData.mapRect(rect);
    }

    public void mapScreenToOriginalPreview(RectF rect) {
        this.mapScreenToPreviewData(rect);
        if (this._CameraInfo.getLensFacing() == 1 && !this._CameraInfo.isPreviewDataMirrored()) {
            float left = (float) this._PreviewWidth - rect.right;
            float right = (float) this._PreviewWidth - rect.left;
            rect.left = left;
            rect.right = right;
        }

    }

    public void getDisplayMatrix(Matrix m) {
        m.set(this._PreviewDataToDisplay);
    }

    public Matrix getDisplayMatrix() {
        return this._PreviewDataToDisplay;
    }
}
