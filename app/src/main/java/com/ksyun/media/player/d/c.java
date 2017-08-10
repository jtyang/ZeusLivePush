package com.ksyun.media.player.d;

import android.content.Context;
import android.os.Handler;
import com.a.a.c.b;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

/* compiled from: KSYStatRecord */
public class c {
    private static c a;
    private ExecutorService b;
    private Context c;
    private boolean d;
    private String e;

    public static c a() {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c();
            }
            cVar = a;
        }
        return cVar;
    }

    private c() {
        this.b = Executors.newFixedThreadPool(2);
        this.d = false;
    }

    public void a(Context context, Handler handler, String str) {
        if (this.d) {
            b.a().d();
            return;
        }
        this.c = context;
        this.e = b(str);
        this.b.execute(new a(handler, this.c));
        this.d = true;
    }

    public void a(Handler handler) {
        if (this.b != null) {
            this.b.execute(new b(handler));
        }
    }

    public void a(String str) {
        if (str != null) {
            try {
                b.a().a(str, this.e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                b.a().a(jSONObject.toString(), this.e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void a(JSONObject jSONObject, boolean z) {
        if (jSONObject != null) {
            try {
                b.a().a(jSONObject.toString(), this.e, z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String b(String str) {
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
}
