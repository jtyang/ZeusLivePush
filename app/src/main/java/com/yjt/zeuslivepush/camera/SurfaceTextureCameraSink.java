package com.yjt.zeuslivepush.camera;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class SurfaceTextureCameraSink implements CameraSink {
    private static final String TAG = "CameraSink";
    private final Handler _Handler;
    private final CameraClient _Client;
    private SurfaceTexture _Current;
    private static final int WHAT_CHANGE = 1;
    private static final Handler.Callback CALLBACK = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            SurfaceTextureCameraSink self = (SurfaceTextureCameraSink) msg.obj;
            switch (msg.what) {
                case WHAT_CHANGE:
                    self.onAvailable();
                    return true;
                default:
                    return false;
            }
        }
    };

    public SurfaceTextureCameraSink(CameraClient client) {
        this(client, Looper.myLooper());
    }

    public SurfaceTextureCameraSink(CameraClient client, Looper looper) {
        this._Handler = new Handler(looper, CALLBACK);
        this._Client = client;
    }

    public synchronized SurfaceTexture getSink(CameraClient c) {
        return this._Current;
    }

    public void onSurfaceTextureDestroyed(SurfaceTexture texture) {
        synchronized (this) {
            if (this._Current != texture) {
                Log.e("CameraSink", "onSurfaceTextureDestroyed: SurfaceTexture instance mismatch, ignored");
                return;
            }

            this._Current = null;
        }

        this.notifySurfaceTextureChanged();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture texture) {
        synchronized (this) {
            this._Current = texture;
        }

        this.notifySurfaceTextureChanged();
    }

    private void notifySurfaceTextureChanged() {
        if (!this._Handler.hasMessages(1)) {
            this._Handler.obtainMessage(1, this).sendToTarget();
        }

    }

    private void onAvailable() {
        this._Client.onSinkChange(this);
    }
}
