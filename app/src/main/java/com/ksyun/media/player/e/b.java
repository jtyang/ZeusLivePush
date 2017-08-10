package com.ksyun.media.player.e;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.ksyun.media.player.util.c;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: WhiteListUtil */
public class b {
    public static final String a = "Data";
    public static final String b = "RetCode";
    public static final String c = "Intval";
    public static final String d = "HwDec264";
    public static final String e = "HwDec265";
    private static final String f = "Model";
    private static final String g = "OsVer";
    private static final String h = "DeviceID";
    private static final String i = "Pkg";
    private static final String j = "AvcInfo";
    private static final String k = "HevcInfo";
    private static final String l = "RomInfo";
    private static final String m = "RetMsg";

    public static String a(Context context) {
        if (context == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            jSONObject.put(f, Build.MODEL);
            jSONObject.put(g, VERSION.RELEASE);
            jSONObject.put(h, c.a(context));
            jSONObject.put(i, context.getPackageName());
            JSONArray c = c();
            if (c != null) {
                jSONObject.put(j, c);
            } else {
                jSONObject.put(j, jSONArray);
            }
            c = b();
            if (c != null) {
                jSONObject.put(k, c);
            } else {
                jSONObject.put(k, jSONArray);
            }
            jSONObject.put(l, a());
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a() {
        String str = Build.DISPLAY;
        String toLowerCase = Build.BRAND.toLowerCase();
        String str2 = null;
        Object obj = -1;
        switch (toLowerCase.hashCode()) {
            case -1206476313:
                if (toLowerCase.equals("huawei")) {
                    obj = 2;
                    break;
                }
                break;
            case -759499589:
                if (toLowerCase.equals("xiaomi")) {
                    obj = 1;
                    break;
                }
                break;
            case 3318203:
                if (toLowerCase.equals("letv")) {
                    obj = null;
                    break;
                }
                break;
            case 3418016:
                if (toLowerCase.equals("oppo")) {
                    obj = 3;
                    break;
                }
                break;
            case 3620012:
                if (toLowerCase.equals("vivo")) {
                    obj = 4;
                    break;
                }
                break;
        }
        switch (obj) {
            case GLRender.VIEW_TYPE_NONE /*0*/:
                str2 = a("ro.letv.release.version");
                break;
            case d.a /*1*/:
                str2 = VERSION.INCREMENTAL;
                break;
            case d.b /*2*/:
                str2 = a("ro.build.version.emui");
                break;
            case GLRender.VIEW_TYPE_OFFSCREEN /*3*/:
                str2 = a("ro.build.version.opporom");
                break;
            case TexTransformUtil.COORDS_COUNT /*4*/:
                str2 = a("ro.vivo.os.build.display.id");
                break;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = str;
        }
        return b(str2);
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            str2 = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{str, StatsConstant.SERVER_IP_DEFAULT_VALUE});
        } catch (Exception e) {
            str2 = null;
        }
        return str2;
    }

    private static String b(String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                str2 = URLEncoder.encode(new String(str.getBytes(), "UTF-8"), "UTF-8");
            } catch (Exception e) {
            }
        }
        return str2;
    }

    private static JSONArray b() {
        return c("video/hevc");
    }

    private static JSONArray c() {
        return c("video/avc");
    }

    @TargetApi(16)
    private static JSONArray c(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        JSONArray jSONArray = null;
        int i = 0;
        for (int i2 = 0; i2 < codecCount; i2++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i2);
            if (!codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                if (supportedTypes != null) {
                    for (Object obj : supportedTypes) {
                        if (!TextUtils.isEmpty(obj) && obj.equalsIgnoreCase(str)) {
                            String name = codecInfoAt.getName();
                            if (!(name.contains("ffmpeg") || name.contains("avcodec") || name.contains("google") || name.contains(".sw") || name.startsWith("omx.pv"))) {
                                if (jSONArray == null) {
                                    jSONArray = new JSONArray();
                                }
                                try {
                                    jSONArray.put(i, name);
                                    i++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return jSONArray;
    }
}
