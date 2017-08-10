package com.ksyun.media.streamer.util.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.ksy.statlibrary.util.PreferenceUtil;
import com.ksyun.media.player.f;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfoTools {
    public static final int REQUEST_ERROR_FAILED = -1001;
    public static final int REQUEST_ERROR_PARSE_FILED = -1002;
    private static final String a = "DeviceInfoTools";
    private static final boolean b = true;
    private static final int c = 5000;
    private static final int d = 5000;
    private static DeviceInfoTools u;
    private final String e;
    private long f;
    private final long g;
    private final long h;
    private final int i;
    private int j;
    private boolean k;
    private String l;
    private String m;
    public b mRequestListener;
    private final String n;
    private SharedPreferences o;
    private Editor p;
    private final Handler q;
    private final String r;
    private String s;
    private DeviceInfo t;

    private class a extends AsyncTask<String, Void, Void> {
        String a;
        String b;
        final /* synthetic */ DeviceInfoTools c;

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((String[]) objArr);
        }

        public a(DeviceInfoTools deviceInfoTools, String str, String str2) {
            this.c = deviceInfoTools;
            this.a = str;
            this.b = str2;
        }

        protected Void a(String... strArr) {
            HttpURLConnection httpURLConnection;
            Exception exception;
            Throwable th;
            HttpURLConnection httpURLConnection2 = null;
            try {
                this.c.s = "http://devinfo.ks-live.com:8420/info?model=" + this.a.replace(" ", "%20") + "&osver=" + this.b;
                HttpURLConnection httpURLConnection3 = (HttpURLConnection) new URL(this.c.s).openConnection();
                try {
                    httpURLConnection3.setRequestMethod("GET");
                    httpURLConnection3.setConnectTimeout(DeviceInfoTools.d);
                    httpURLConnection3.setReadTimeout(DeviceInfoTools.d);
                    int responseCode = httpURLConnection3.getResponseCode();
                    Log.d(DeviceInfoTools.a, "responseCode=" + responseCode);
                    Log.d(DeviceInfoTools.a, "mRequestUrl=" + this.c.s);
                    if (responseCode == f.f) {
                        Reader inputStreamReader = new InputStreamReader(httpURLConnection3.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuffer stringBuffer = new StringBuffer(com.ksyun.media.player.b.d);
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                stringBuffer.append(readLine);
                            } else {
                                try {
                                    break;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(DeviceInfoTools.a, "get info error:parse failed:");
                                    if (this.c.mRequestListener != null) {
                                        this.c.mRequestListener.a(DeviceInfoTools.REQUEST_ERROR_PARSE_FILED);
                                    }
                                }
                            }
                        }
                        JSONObject jSONObject = new JSONObject(stringBuffer.toString());
                        if (jSONObject != null) {
                            int i = jSONObject.getInt("errno");
                            if (i == 0) {
                                String string = jSONObject.getString("info");
                                this.c.f = (long) ((jSONObject.getInt(PreferenceUtil.INTERVAL) * 60) * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD);
                                jSONObject = new JSONObject(string);
                                if (jSONObject != null) {
                                    if (this.a.equals(this.c.l)) {
                                        this.c.t = new DeviceInfo(jSONObject, this.c.l, this.c.m);
                                        this.c.a(this.c.t);
                                    }
                                    if (this.c.mRequestListener != null) {
                                        this.c.mRequestListener.a(responseCode, new DeviceInfo(jSONObject, this.a, this.b));
                                    }
                                }
                            } else {
                                Log.e(DeviceInfoTools.a, "get info error:" + jSONObject.getString("errmsg"));
                                this.c.e();
                                if (this.c.mRequestListener != null) {
                                    this.c.mRequestListener.a(i);
                                }
                            }
                        }
                        inputStreamReader.close();
                        bufferedReader.close();
                    } else {
                        Log.e(DeviceInfoTools.a, "send request to server failed!");
                        if (responseCode < 400 || responseCode > 500) {
                            this.c.d();
                        } else {
                            this.c.e();
                        }
                        if (this.c.mRequestListener != null) {
                            this.c.mRequestListener.a(responseCode);
                        }
                    }
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                } catch (Exception e2) {
                    Exception exception2 = e2;
                    httpURLConnection = httpURLConnection3;
                    exception = exception2;
                    try {
                        Log.e(DeviceInfoTools.a, "Send request failed");
                        exception.printStackTrace();
                        this.c.d();
                        if (this.c.mRequestListener != null) {
                            this.c.mRequestListener.a(DeviceInfoTools.REQUEST_ERROR_FAILED);
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        httpURLConnection2 = httpURLConnection;
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    httpURLConnection2 = httpURLConnection3;
                    th = th3;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                exception = e3;
                httpURLConnection = null;
                Log.e(DeviceInfoTools.a, "Send request failed");
                exception.printStackTrace();
                this.c.d();
                if (this.c.mRequestListener != null) {
                    this.c.mRequestListener.a(DeviceInfoTools.REQUEST_ERROR_FAILED);
                }
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

    public interface b {
        void a(int i);

        void a(int i, Object obj);
    }

    public static DeviceInfoTools getInstance() {
        if (u == null) {
            synchronized (DeviceInfoTools.class) {
                if (u == null) {
                    u = new DeviceInfoTools();
                }
            }
        }
        return u;
    }

    private DeviceInfoTools() {
        this.e = "last_save_time";
        this.f = 604800000;
        this.g = 86400000;
        this.h = 60000;
        this.i = 3;
        this.j = 0;
        this.k = false;
        this.l = Build.MODEL;
        this.m = VERSION.RELEASE;
        this.n = "ksydeviceinfo";
        this.r = "http://devinfo.ks-live.com:8420/info";
        this.q = new Handler(Looper.getMainLooper());
    }

    public void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("the context must not null");
        }
        if (this.o == null) {
            this.o = context.getSharedPreferences("ksydeviceinfo", 0);
            this.p = this.o.edit();
        }
        a();
    }

    public void unInit() {
        this.q.removeCallbacksAndMessages(null);
    }

    public DeviceInfo getDeviceInfo() {
        a();
        StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_DEVICEINFO);
        return this.t;
    }

    public void sendDeviceInfoRequest(b bVar, String str, String str2) {
        this.mRequestListener = bVar;
        new a(this, str, str2).execute(new String[0]);
    }

    private void a() {
        b();
        if (c() || !this.k) {
            new a(this, this.l, this.m).execute(new String[0]);
        }
    }

    private DeviceInfo b() {
        if (this.o == null && this.t == null) {
            Log.w(a, "please call init before call this function");
            this.t = new DeviceInfo(this.l, this.m);
            return this.t;
        }
        if (!(this.o == null || this.o.getLong("last_save_time", 0) == 0)) {
            this.k = b;
        }
        if (this.t == null) {
            if (this.o != null) {
                this.t = new DeviceInfo(this.o, this.l, this.m);
            } else {
                this.t = new DeviceInfo(this.l, this.m);
            }
        }
        return this.t;
    }

    private void a(DeviceInfo deviceInfo) {
        if (this.o != null) {
            deviceInfo.saveDeviceInfoForLocal(this.p);
            this.p.putLong("last_save_time", System.currentTimeMillis());
            this.p.commit();
            this.k = b;
        }
    }

    private boolean c() {
        if (this.o == null) {
            return b;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.o.getLong("last_save_time", 0);
        if (j == 0 || currentTimeMillis - j < this.f) {
            return false;
        }
        return b;
    }

    private void d() {
        if (this.j <= 3) {
            this.q.postDelayed(new Runnable() {
                final /* synthetic */ DeviceInfoTools a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.a();
                }
            }, 60000);
            this.j++;
        }
    }

    private void e() {
        this.q.postDelayed(new Runnable() {
            final /* synthetic */ DeviceInfoTools a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a();
            }
        }, 86400000);
    }
}
