package com.yjt.zeuslivepush.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class WeakReferenceHandler<T> extends Handler {
    private WeakReference<T> mReference;

    public WeakReferenceHandler(Looper looper, T reference) {
        super(looper);
        this.mReference = new WeakReference(reference);
    }

    public void handleMessage(Message msg) {
        T reference = this.mReference.get();
        if (reference != null) {
            this.handleMessage(reference, msg);
        }
    }

    protected abstract void handleMessage(T var1, Message var2);
}