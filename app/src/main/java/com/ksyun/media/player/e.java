package com.ksyun.media.player;

/* compiled from: KSYLibLoader */
public class e {
    public static boolean a(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (UnsatisfiedLinkError e) {
            return false;
        } catch (SecurityException e2) {
            return false;
        }
    }

    public static boolean a(String str, String str2) {
        try {
            System.load(str + "lib" + str2 + ".so");
            return true;
        } catch (UnsatisfiedLinkError e) {
            return false;
        } catch (SecurityException e2) {
            return false;
        }
    }
}
