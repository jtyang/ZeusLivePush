package com.ksyun.media.streamer.logstats;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.ksyun.media.player.b;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.d;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: StatsUtil */
public class c {
    public static String a;
    protected static char[] b;
    private static byte[] c;
    private static TelephonyManager d;
    private static String e;
    private static String f;

    static {
        f = b.d;
        a = "UTF-8";
        b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public static String a() {
        return StatsConstant.SDK_UNIQUE_NAME;
    }

    public static String a(String str, String str2) {
        String str3 = "HmacSHA1";
        String str4 = null;
        try {
            Key secretKeySpec = new SecretKeySpec(str.getBytes(a), str3);
            Mac instance = Mac.getInstance(str3);
            instance.init(secretKeySpec);
            str4 = URLEncoder.encode(Base64.encodeToString(instance.doFinal(str2.getBytes(a)), 2), a);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
        }
        return str4;
    }

    private static boolean a(Context context, String str) {
        if (context != null && context.checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }

    public static String a(Context context) {
        if (context == null || !a(context, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager != null ? telephonyManager.getDeviceId() : null;
    }

    public static String b(Context context) {
        if (context != null) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                switch (activeNetworkInfo.getType()) {
                    case GLRender.VIEW_TYPE_NONE /*0*/:
                        return "Mobile Network";
                    case d.a /*1*/:
                        return "WIFI";
                    default:
                        return "Unknown";
                }
            }
        }
        return "Unknown";
    }

    public static String c(Context context) {
        d = d(context);
        String str = "N/A";
        try {
            e = d.getSubscriberId();
            if (TextUtils.isEmpty(e)) {
                return str;
            }
            System.out.println(e);
            if (e.startsWith("46000") || e.startsWith("46002")) {
                return "\u4e2d\u56fd\u79fb\u52a8";
            }
            if (e.startsWith("46001")) {
                return "\u4e2d\u56fd\u8054\u901a";
            }
            if (e.startsWith("46003")) {
                return "\u4e2d\u56fd\u7535\u4fe1";
            }
            return str;
        } catch (Exception e) {
            if (!(e instanceof SecurityException)) {
                return str;
            }
            Log.w("StatsUtil", "the apk do not have the permission of READ_PHONE_STATE");
            return str;
        }
    }

    private static TelephonyManager d(Context context) {
        if (d == null) {
            d = (TelephonyManager) context.getSystemService("phone");
        }
        return d;
    }

    public static byte[] a(byte[] bArr, int i) {
        byte[] bArr2 = null;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr, 0, i);
            bArr2 = instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bArr2;
    }

    public static String a(String str) {
        byte[] bytes = str.getBytes();
        bytes = a(bytes, bytes.length);
        if (bytes != null) {
            return a(bytes);
        }
        return null;
    }

    public static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            cArr[i * 2] = b[(b >>> 4) & 15];
            cArr[(i * 2) + 1] = b[b & 15];
        }
        return new String(cArr);
    }

    public static byte[] b(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] toCharArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (a(toCharArray[i2 + 1]) | (a(toCharArray[i2]) << 4));
        }
        return bArr;
    }

    public static byte a(char c) {
        return c >= 'A' ? (byte) ((c + 10) - 65) : (byte) (c - 48);
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return b.d;
        }
        f = a(str + String.valueOf(System.currentTimeMillis()));
        return f;
    }

    public static String b() {
        return f;
    }

    public static String a(int i) {
        if (i == 0) {
            return StatsConstant.ENCODE_HARD264;
        }
        if (i == 1) {
            return StatsConstant.ENCODE_SOFT264;
        }
        if (i == 2) {
            return StatsConstant.ENCODE_SOFT265;
        }
        return StatsConstant.ENCODE_SOFT264;
    }
}
