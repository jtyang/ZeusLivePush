package com.yjt.zeuslivepush.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class HandlerUtil {
    public HandlerUtil() {
    }

    @TargetApi(18)
    private static void quitSafely18(Looper looper) {
        looper.quitSafely();
    }

    public static void quitSafely(Handler handler) {
        final Looper looper = handler.getLooper();
        if(Build.VERSION.SDK_INT >= 18) {
            quitSafely18(looper);
        } else {
            handler.post(new Runnable() {
                public void run() {
                    looper.quit();
                }
            });
        }
    }

    @TargetApi(18)
    private static void quitSafely18(HandlerThread thread) {
        thread.quitSafely();
    }

    public static void quitSafely(HandlerThread thread) {
        if(Build.VERSION.SDK_INT >= 18) {
            quitSafely18(thread);
        } else {
            quitSafely(new Handler(thread.getLooper()));
        }
    }
}
