package com.yjt.zeuslivepush.camera.impl;

import android.os.Handler;
import android.os.Message;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class DefaultStateCallback implements Handler.Callback {
    private static final int WHAT_DEVICE_OPENED = 6;
    private static final int WHAT_DEVICE_ERROR = 7;

    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case WHAT_DEVICE_OPENED:
                ((DefaultDevice)msg.obj).dispatchOpened();
                break;
            case WHAT_DEVICE_ERROR:
                ((DefaultDevice)msg.obj).dispatchError(msg.arg1);
                break;
        }
        return false;
    }

    static void sendMessage(DefaultDevice device, int what) {
        Handler handler = device.getCallbackHandler();
        handler.obtainMessage(what, device).sendToTarget();
    }

    static void sendMessage(DefaultDevice device, int what, int arg1) {
        Handler handler = device.getCallbackHandler();
        handler.obtainMessage(what, arg1, 0, device).sendToTarget();
    }

    static void notifyOpened(DefaultDevice device) {
        sendMessage(device, WHAT_DEVICE_OPENED);
    }

    static void notifyError(DefaultDevice device, int error) {
        sendMessage(device, WHAT_DEVICE_ERROR, error);
    }
}
