package com.ksyun.media.player;

public class KSYLibraryManager {
    private static String mLocalLibraryPath;

    public static void setLocalLibraryPath(String str) {
        mLocalLibraryPath = str;
    }

    public static String getLocalLibraryPath() {
        return mLocalLibraryPath;
    }
}
