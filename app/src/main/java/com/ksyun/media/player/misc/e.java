package com.ksyun.media.player.misc;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.net.URLEncoder;
import java.security.MessageDigest;

/* compiled from: LibKSYAuth */
public class e {
    private static e a = null;
    private static final String b = "ffffffffff";
    private String c;
    private String d;
    private String e;
    private String f;
    private Context g;

    public static e a() {
        e eVar;
        synchronized (e.class) {
            if (a == null) {
                a = new e();
            }
            eVar = a;
        }
        return eVar;
    }

    private e() {
    }

    public void a(Context context) {
        this.g = context;
    }

    public void a(String str, String str2, String str3, String str4) {
        this.c = b(str);
        this.d = b(str2);
        this.e = b(str3);
        this.f = b(str4);
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.f;
    }

    public String f() {
        if (this.g == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.g.getPackageName()).append(';').append(VERSION.RELEASE).append(';').append(Build.MODEL);
        return b(stringBuilder.toString());
    }

    public String g() {
        if (this.g == null) {
            return null;
        }
        String h = h();
        if (TextUtils.isEmpty(h)) {
            h = b;
        }
        String i = i();
        if (TextUtils.isEmpty(i)) {
            i = b;
        }
        String k = k();
        if (TextUtils.isEmpty(k)) {
            k = b;
        }
        return b(h + "-" + a(k + i));
    }

    public String a(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuilder stringBuilder = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                if ((b & 255) < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(b & 255));
            }
            return stringBuilder.toString();
        } catch (Throwable e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }
    }

    private String b(String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                str2 = URLEncoder.encode(new String(str.getBytes(), "UTF-8"), "UTF-8");
            } catch (Exception e) {
            }
        }
        return str2;
    }

    private String h() {
        if (this.g == null || !c("android.permission.READ_PHONE_STATE")) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) this.g.getSystemService("phone");
        return telephonyManager != null ? telephonyManager.getDeviceId() : null;
    }

    private String i() {
        if (this.g == null) {
            return null;
        }
        return Secure.getString(this.g.getContentResolver(), "android_id");
    }

    private String j() {
        if (this.g == null || !c("android.permission.ACCESS_WIFI_STATE")) {
            return null;
        }
        return ((WifiManager) this.g.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    private String k() {
        if (this.g == null) {
            return null;
        }
        String str;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", StatsConstant.SERVER_IP_DEFAULT_VALUE});
        } catch (Exception e) {
            str = null;
        }
        return str;
    }

    private boolean c(String str) {
        if (this.g != null && this.g.checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }
}
