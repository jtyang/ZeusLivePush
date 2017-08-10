package com.ksyun.media.player.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.framework.CredtpModel;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.publisher.PublisherWrapper;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;
import java.security.MessageDigest;

/* compiled from: StatUtils */
public class c {
    private static final String a = "ffffffffff";

    public static String a(Context context) {
        String str;
        if (context == null || !a(context, "android.permission.READ_PHONE_STATE")) {
            str = null;
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            str = telephonyManager != null ? telephonyManager.getDeviceId() : null;
        }
        if (str == null) {
            str = a;
        }
        String d = d(context);
        if (TextUtils.isEmpty(d)) {
            d = a;
        }
        String e = e(context);
        if (TextUtils.isEmpty(e)) {
            e = a;
        }
        return str + "-" + a(e + d);
    }

    private static String d(Context context) {
        if (context == null) {
            return null;
        }
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    private static String e(Context context) {
        if (context == null) {
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

    public static String b(Context context) {
        if (context == null || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return null;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "disconnected";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "wifi";
        }
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case d.a /*1*/:
            case d.b /*2*/:
            case TexTransformUtil.COORDS_COUNT /*4*/:
            case PublisherWrapper.s /*7*/:
            case CredtpModel.BEAUTY_GRIND_SIMPLE_FILTER /*11*/:
                return "2G";
            case GLRender.VIEW_TYPE_OFFSCREEN /*3*/:
            case PublisherWrapper.q /*5*/:
            case PublisherWrapper.r /*6*/:
            case TexTransformUtil.COORDS_STRIDE /*8*/:
            case PublisherWrapper.u /*9*/:
            case PublisherWrapper.v /*10*/:
            case CredtpModel.BEAUTY_ADJ_SKIN_COLOR_FILTER /*12*/:
            case 14:
            case CameraCapture.DEFAULT_PREVIEW_FPS /*15*/:
                return "3G";
            case CredtpModel.BEAUTY_GRIND_ADVANCE_FILTER /*13*/:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String a(String str) {
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

    public static String c(Context context) {
        if (context == null || !a(context, "android.permission.READ_PHONE_STATE")) {
            return "N/A";
        }
        String str = "N/A";
        try {
            String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            if (!TextUtils.isEmpty(subscriberId)) {
                System.out.println(subscriberId);
                if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002")) {
                    return "\u4e2d\u56fd\u79fb\u52a8";
                }
                if (subscriberId.startsWith("46001")) {
                    return "\u4e2d\u56fd\u8054\u901a";
                }
                if (subscriberId.startsWith("46003")) {
                    return "\u4e2d\u56fd\u7535\u4fe1";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static boolean a(Context context, String str) {
        if (context != null && context.checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }
}
