package com.yjt.zeuslivepush.camera.impl;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;

import com.yjt.zeuslivepush.camera.CameraDevice;
import com.yjt.zeuslivepush.camera.permission.PermissionChecker;
import com.yjt.zeuslivepush.utils.ThreadUtil;

import junit.framework.Assert;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class DefaultManager {
    private static final String TAG = "DefaultManager";
    private final DefaultCharacteristics[] _CameraList;
    private final PermissionChecker _Permission;
    private Camera _Instance;

    public DefaultManager(PermissionChecker permission) {
        this._Permission = permission;
        int count = Camera.getNumberOfCameras();
        this._CameraList = new DefaultCharacteristics[count];
        Camera.CameraInfo info = new Camera.CameraInfo();

        for (int i = 0; i < count; ++i) {
            Camera.getCameraInfo(i, info);
            this._CameraList[i] = new DefaultCharacteristics(i, info);
        }

    }

    public int findByFacing(int facing) {
        for (int i = 0; i < this._CameraList.length; ++i) {
            if (this._CameraList[i].getLensFacing() == facing) {
                return i;
            }
        }

        return -1;
    }

    public DefaultCharacteristics getCameraCharacteristics(int id) {
        return this._CameraList[id];
    }

    public void openCamera(int id, CameraDevice.StateCallback callback) {
        this.openCamera(id, callback, Looper.myLooper());
    }

    public void openCamera(int id, CameraDevice.StateCallback callback, Looper looper) {
        new DefaultDevice(id, callback, new Handler(looper, new DefaultStateCallback()), this);
    }

    public int getCameraCount() {
        return this._CameraList.length;
    }

    synchronized Camera acquireCamera(int id) throws RuntimeException {
        while (this._Instance != null) {
            ThreadUtil.wait(this);
        }

        Object token = null;
        if (this._Permission != null) {
            token = this._Permission.startPrivilegedOperation("android.permission.CAMERA");
        }

        try {
            this._Instance = Camera.open(id);
        } finally {
            if (this._Permission != null) {
                this._Permission.stopPrivilegedOperation(token);
            }

        }

        if (this._Instance == null) {
            throw new RuntimeException("Camera.open(" + id + ") returned null");
        } else {
            return this._Instance;
        }
    }

    synchronized void releaseCamera(Camera camera) {
        Assert.assertSame(this._Instance, camera);
        camera.release();
        this._Instance = null;
        this.notifyAll();
    }
}
