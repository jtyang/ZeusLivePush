package com.ksyun.media.streamer.util;

import android.util.Log;

public class LibraryLoader {
    private static final String a = "LibraryLoader";

    public static void load() {
        try {
            System.loadLibrary("ksylive");
        } catch (UnsatisfiedLinkError e) {
            Log.e(a, "No libksylive.so! Please check");
        }
    }
}
