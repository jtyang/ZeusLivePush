package com.ksyun.media.streamer.util;

import android.content.Context;

/* compiled from: ContextUtil */
public final class a {
    private static Context a;

    public static Context a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    try {
                        Class cls = Class.forName("android.app.ActivityThread");
                        Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(cls, new Object[0]);
                        a = (Context) invoke.getClass().getMethod("getApplication", new Class[0]).invoke(invoke, new Object[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return a;
    }
}
