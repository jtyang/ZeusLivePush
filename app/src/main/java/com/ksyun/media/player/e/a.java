package com.ksyun.media.player.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* compiled from: SharePreferenceUtil */
public class a {
    public static final String a = "ksy_white_list";
    public static final String b = "ksy_support_h264";
    public static final String c = "ksy_support_h265";
    public static final String d = "ksy_264_name";
    public static final String e = "ksy_265_name";
    public static final String f = "ksy_interval";
    public static final String g = "ksy_update";

    public static boolean a(Context context, String str, boolean z) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(a, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(str, z);
        }
        return false;
    }

    public static long a(Context context, String str, long j) {
        if (context == null) {
            return -1;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(a, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(str, j);
        }
        return -1;
    }

    public static String a(Context context, String str, String str2) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(a, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(str, str2);
        }
        return null;
    }

    public static void b(Context context, String str, boolean z) {
        if (context != null) {
            Editor edit = context.getSharedPreferences(a, 0).edit();
            edit.putBoolean(str, z);
            edit.commit();
        }
    }

    public static void b(Context context, String str, long j) {
        if (context != null) {
            Editor edit = context.getSharedPreferences(a, 0).edit();
            edit.putLong(str, j);
            edit.commit();
        }
    }

    public static void b(Context context, String str, String str2) {
        if (context != null) {
            Editor edit = context.getSharedPreferences(a, 0).edit();
            edit.putString(str, str2);
            edit.commit();
        }
    }
}
