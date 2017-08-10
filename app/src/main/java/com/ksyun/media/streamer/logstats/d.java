package com.ksyun.media.streamer.logstats;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Base64;
import com.ksyun.media.player.f;
import com.ksyun.media.streamer.util.StringWrapper;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;

/* compiled from: StreamSucessCount */
public class d {
    private static final String a = "StreamSucessCount";
    private static final boolean b = false;
    private static final int c = 5000;
    private static final int d = 5000;
    private static final String e = "http://videodev.ksyun.com:8980/univ/clientcounter";
    private Context f;
    private StringWrapper g;
    private String h;
    private String i;
    private String j;
    private String k;

    /* compiled from: StreamSucessCount */
    private class a extends AsyncTask<String, Void, Void> {
        final /* synthetic */ d a;

        private a(d dVar) {
            this.a = dVar;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((String[]) objArr);
        }

        protected Void a(String... strArr) {
            HttpURLConnection httpURLConnection;
            Throwable th;
            HttpURLConnection httpURLConnection2 = null;
            this.a.c();
            try {
                httpURLConnection = (HttpURLConnection) new URL(this.a.k).openConnection();
                try {
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(d.d);
                    httpURLConnection.setReadTimeout(d.d);
                    if (httpURLConnection.getResponseCode() == f.f) {
                        this.a.a(httpURLConnection.getInputStream());
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (Exception e) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    httpURLConnection2 = httpURLConnection;
                    th = th3;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                httpURLConnection = null;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                throw th;
            }
            return null;
        }
    }

    public d(Context context, StringWrapper stringWrapper) {
        this.f = context;
        this.g = stringWrapper;
    }

    public synchronized void a() {
        this.j = String.valueOf(f());
        new a().execute(new String[0]);
    }

    public Context b() {
        return this.f;
    }

    private void c() {
        if (this.h == null) {
            d();
        }
        e();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e);
        stringBuilder.append("?accesskey=");
        stringBuilder.append(this.g.getStringInfo(StringWrapper.COUNT_ACCESS_KEY));
        stringBuilder.append("&expire=");
        stringBuilder.append(this.j);
        stringBuilder.append("&cont=");
        stringBuilder.append(this.h);
        stringBuilder.append("&uniqname=");
        stringBuilder.append(c.a());
        stringBuilder.append("&signature=");
        stringBuilder.append(this.i);
        this.k = stringBuilder.toString();
    }

    private void d() {
        JSONObject b = a.b(this.f);
        if (b != null) {
            Object jSONObject = b.toString();
            if (!TextUtils.isEmpty(jSONObject)) {
                this.h = Base64.encodeToString(jSONObject.getBytes(), 2);
                try {
                    this.h = URLEncoder.encode(this.h, c.a);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cont=");
        stringBuilder.append(this.h);
        stringBuilder.append("&method=clientcounter&uniqname=");
        stringBuilder.append(c.a());
        this.i = c.a(this.g.getStringInfo(StringWrapper.COUNT_SECRET_KEY), "GET\n" + String.valueOf(this.j) + "\n" + stringBuilder.toString());
    }

    private long f() {
        long timeInMillis = (Calendar.getInstance().getTimeInMillis() / 1000) + 600;
        Date date = new Date(1000 * timeInMillis);
        return timeInMillis;
    }

    private String a(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                inputStream.close();
                String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                byteArrayOutputStream.close();
                return byteArrayOutputStream2;
            }
        }
    }
}
