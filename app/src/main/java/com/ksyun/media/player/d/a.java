package com.ksyun.media.player.d;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import com.a.a.c.b;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.misc.e;
import com.ksyun.media.player.util.c;
import com.ksyun.media.streamer.logstats.StatsConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: KSYBaseStat */
public class a implements Runnable {
    private Handler a;
    private Context b;

    public a(Handler handler, Context context) {
        this.b = context;
        this.a = handler;
    }

    private String a(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = str.split("\\.");
        stringBuffer.append("ksyplayer_android_");
        for (String append : split) {
            stringBuffer.append(append);
        }
        return stringBuffer.toString();
    }

    public void run() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.SDK_TYPE, d.at);
            jSONObject.put(StatsConstant.SDK_VERSION, KSYMediaPlayer.getVersion());
            jSONObject.put(StatsConstant.SYSTEM_PLATFORM, StatsConstant.SYSTEM_PLATFORM_VALUE);
            jSONObject.put(StatsConstant.SYSTEM_VERSION, VERSION.RELEASE);
            if (this.b != null) {
                jSONObject.put(StatsConstant.APP_PACKAGE_NAME, this.b.getPackageName());
            } else {
                jSONObject.put(StatsConstant.APP_PACKAGE_NAME, StatsConstant.STAT_CONSTANTS_UNKNOWN);
            }
            jSONObject.put(StatsConstant.DEVICE_MODEL, Build.MODEL);
            String a = c.a(this.b);
            if (a != null) {
                jSONObject.put(StatsConstant.DEVICE_ID, a);
            } else {
                jSONObject.put(StatsConstant.DEVICE_ID, StatsConstant.STAT_CONSTANTS_UNKNOWN);
            }
            jSONObject.put(d.i, e.a().c());
            jSONObject.put(d.j, e.a().b());
            jSONObject.put(StatsConstant.LOG_MODULE_VERSION, b.a().h());
            jSONObject.put(StatsConstant.LOG_SDK_VN, b.a().i());
            b.a().a(new com.a.a.c.c(jSONObject, "D8uDWZ88ZKUCPu0KRJkR", "2tueIxI3wqxo6IvVU9/Wn+h8RKNNBrxzk/vdmWSD", a(KSYMediaPlayer.getVersion())));
            b.a().a(120000);
            b.a().d();
            if (this.a != null) {
                this.a.obtainMessage(KSYMediaPlayer.MEDIA_LOG_REPORT, 6, 0, jSONObject.toString()).sendToTarget();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
