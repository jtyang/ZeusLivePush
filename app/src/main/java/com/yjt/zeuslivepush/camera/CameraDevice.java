package com.yjt.zeuslivepush.camera;

import android.graphics.Rect;
import android.hardware.Camera;
import android.os.HandlerThread;

import com.yjt.zeuslivepush.render.Renderer;

import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class CameraDevice {
    protected Camera _Camera;
    protected final CameraCharacteristics _Info;

    public CameraDevice(CameraCharacteristics info) {
        this._Info = info;
    }

    public final Camera getCamera() {
        return this._Camera;
    }

    public abstract void close();

    public abstract HandlerThread getCameraThread();

    public abstract void createCaptureSession(List var1, SessionRequest var2,
                                              CameraCaptureSession.StateCallback var3,
                                              Renderer var4,
                                              Rect var5);

    public final int getID() {
        return this._Info.id;
    }

    public interface StateCallback {
        void onOpened(CameraDevice var1);

        void onError(CameraDevice var1, int var2);
    }
}
