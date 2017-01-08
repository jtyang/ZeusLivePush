package com.yjt.zeuslivepush.camera.impl;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.yjt.zeuslivepush.camera.CaptureOutput;

import java.io.IOException;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class SurfaceCapture extends CaptureOutput {
    private SurfaceHolder mHolder;

    public SurfaceCapture(Camera camera, SurfaceHolder holder) {
        super(camera);
        this.mHolder = holder;
    }

    public boolean configure() {
        try {
            this.mCamera.setPreviewDisplay(this.mHolder);
            return true;
        } catch (IOException var2) {
            Log.e(TAG, "Failed to configure surface capture output!");
            var2.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        try {
            this.mCamera.setPreviewDisplay((SurfaceHolder) null);
            return true;
        } catch (IOException var2) {
            Log.e(TAG, "Failed to close surface capture output!");
            var2.printStackTrace();
            return false;
        }
    }
}