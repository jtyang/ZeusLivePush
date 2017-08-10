package com.ksyun.media.streamer.util;

import java.util.List;

public class StringWrapper {
    public static int COUNT_ACCESS_KEY;
    public static int COUNT_SECRET_KEY;
    public static int LOG_ACCESS_KEY;
    public static int LOG_SECRET_KEY;
    private static int b;
    private static StringWrapper c;
    List<String> a;

    private native List<String> getStringList();

    static {
        LOG_ACCESS_KEY = 0;
        LOG_SECRET_KEY = 1;
        COUNT_ACCESS_KEY = 2;
        COUNT_SECRET_KEY = 3;
        LibraryLoader.load();
    }

    public static StringWrapper getInstance() {
        StringWrapper stringWrapper;
        synchronized (CredtpWrapper.class) {
            b++;
            if (c == null) {
                synchronized (CredtpWrapper.class) {
                    if (c == null) {
                        c = new StringWrapper();
                    }
                }
            }
            stringWrapper = c;
        }
        return stringWrapper;
    }

    public static void unInitInstance() {
        synchronized (CredtpWrapper.class) {
            b--;
            if (c != null && b == 0) {
                c = null;
            }
        }
    }

    public StringWrapper() {
        this.a = getStringList();
    }

    public String getStringInfo(int i) {
        if (this.a != null) {
            return (String) this.a.get(i);
        }
        return null;
    }
}
