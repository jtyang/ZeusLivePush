package com.yjt.zeuslivepush.camera;

import android.graphics.Rect;
import android.hardware.Camera;

import com.yjt.zeuslivepush.camera.opengl.QpEgl11Surface;
import com.yjt.zeuslivepush.render.Renderer;


/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class CaptureOutput {
    public static String TAG = "CaptureOutput";
    protected Camera mCamera;

    public CaptureOutput(Camera camera) {
        this.mCamera = camera;
    }

    public abstract boolean configure();

    public abstract boolean close();

    public void addGlSurface(QpEgl11Surface surface) {
    }

    public void removeSurface(QpEgl11Surface surface) {
    }

    public void setRenderer(Renderer renderer) {
    }

    public void setCrop(Rect crop) {
    }
}
