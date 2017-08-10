package com.ksyun.media.player;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.ksyun.media.player.e.b;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class KSYHardwareDecodeWhiteList {
    public static final int KSY_STATUS_CONNECTING = 11;
    public static final int KSY_STATUS_FAIL = 13;
    public static final int KSY_STATUS_IDLE = 10;
    public static final int KSY_STATUS_OK = 12;
    private static KSYHardwareDecodeWhiteList a;
    private final String b;
    private boolean c;
    private boolean d;
    private String e;
    private String f;
    private Context g;
    private int h;
    private a i;

    private class a extends AsyncTask<String, Void, String> {
        final /* synthetic */ KSYHardwareDecodeWhiteList a;

        private a(KSYHardwareDecodeWhiteList kSYHardwareDecodeWhiteList) {
            this.a = kSYHardwareDecodeWhiteList;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            a((String) obj);
        }

        protected String a(String... strArr) {
            HttpURLConnection httpURLConnection;
            MalformedURLException malformedURLException;
            IOException iOException;
            Throwable th;
            Object obj;
            HttpURLConnection httpURLConnection2;
            HttpURLConnection httpURLConnection3 = null;
            BufferedReader bufferedReader;
            try {
                HttpURLConnection httpURLConnection4 = (HttpURLConnection) new URL(strArr[0]).openConnection();
                try {
                    String stringBuilder;
                    httpURLConnection4.setConnectTimeout(3000);
                    httpURLConnection4.setReadTimeout(3000);
                    httpURLConnection4.setRequestMethod("POST");
                    httpURLConnection4.setDoOutput(true);
                    httpURLConnection4.setDoInput(true);
                    httpURLConnection4.setRequestProperty("Content-Type", "application/json");
                    String a = b.a(this.a.g);
                    OutputStream outputStream = httpURLConnection4.getOutputStream();
                    outputStream.write(a.getBytes());
                    outputStream.flush();
                    int responseCode = httpURLConnection4.getResponseCode();
                    if (responseCode == f.f) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection4.getInputStream()));
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                stringBuilder2.append(readLine);
                            } catch (MalformedURLException e) {
                                MalformedURLException malformedURLException2 = e;
                                httpURLConnection = httpURLConnection4;
                                malformedURLException = malformedURLException2;
                            } catch (IOException e2) {
                                IOException iOException2 = e2;
                                httpURLConnection = httpURLConnection4;
                                iOException = iOException2;
                            } catch (Throwable th2) {
                                Throwable th3 = th2;
                                httpURLConnection3 = httpURLConnection4;
                                th = th3;
                            }
                        }
                        stringBuilder = stringBuilder2.toString();
                    } else {
                        if (responseCode >= 400) {
                            this.a.h = KSYHardwareDecodeWhiteList.KSY_STATUS_FAIL;
                        }
                        obj = httpURLConnection3;
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    if (httpURLConnection4 == null) {
                        return stringBuilder;
                    }
                    httpURLConnection4.disconnect();
                    return stringBuilder;
                } catch (MalformedURLException e3) {
                    obj = httpURLConnection3;
                    httpURLConnection2 = httpURLConnection4;
                    malformedURLException = e3;
                    httpURLConnection = httpURLConnection2;
                    try {
                        malformedURLException.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException iOException3) {
                                iOException3.printStackTrace();
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                            return httpURLConnection3;
                        }
                        return httpURLConnection3;
                    } catch (Throwable th4) {
                        th = th4;
                        httpURLConnection3 = httpURLConnection;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        if (httpURLConnection3 != null) {
                            httpURLConnection3.disconnect();
                        }
                        throw th;
                    }
                } catch (IOException e2222) {
                    obj = httpURLConnection3;
                    httpURLConnection2 = httpURLConnection4;
                    iOException3 = e2222;
                    httpURLConnection = httpURLConnection2;
                    iOException3.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException iOException32) {
                            iOException32.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                        return httpURLConnection3;
                    }
                    return httpURLConnection3;
                } catch (Throwable th5) {
                    obj = httpURLConnection3;
                    httpURLConnection3 = httpURLConnection4;
                    th = th5;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    throw th;
                }
            } catch (MalformedURLException e4) {
                malformedURLException = e4;
                httpURLConnection = httpURLConnection3;
                obj = httpURLConnection3;
                malformedURLException.printStackTrace();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    return httpURLConnection3;
                }
                return httpURLConnection3;
            } catch (IOException e5) {
                iOException32 = e5;
                httpURLConnection = httpURLConnection3;
                bufferedReader = httpURLConnection3;
                iOException32.printStackTrace();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    return httpURLConnection3;
                }
                return httpURLConnection3;
            } catch (Throwable th6) {
                th = th6;
                bufferedReader = httpURLConnection3;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                throw th;
            }
        }

        protected void a(String str) {
            this.a.a(str);
        }
    }

    public static KSYHardwareDecodeWhiteList getInstance() {
        if (a == null) {
            synchronized (KSYHardwareDecodeWhiteList.class) {
                if (a == null) {
                    a = new KSYHardwareDecodeWhiteList();
                }
            }
        }
        return a;
    }

    private KSYHardwareDecodeWhiteList() {
        this.b = "http://sdk.ks-live.com:8989/api/CanHWDecode/2017-01-01";
        this.c = false;
        this.d = false;
        this.h = KSY_STATUS_IDLE;
        this.i = null;
    }

    public void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The input argument CONTEXT can't be null!");
        } else if (VERSION.SDK_INT < 16) {
            this.h = KSY_STATUS_OK;
        } else if (this.h == KSY_STATUS_IDLE) {
            this.g = context.getApplicationContext();
            long a = com.ksyun.media.player.e.a.a(context, com.ksyun.media.player.e.a.f, 0);
            long a2 = com.ksyun.media.player.e.a.a(context, com.ksyun.media.player.e.a.g, 0);
            if (a > 0 && a2 > 0 && System.currentTimeMillis() - a2 < a) {
                this.c = com.ksyun.media.player.e.a.a(context, com.ksyun.media.player.e.a.b, false);
                this.d = com.ksyun.media.player.e.a.a(context, com.ksyun.media.player.e.a.c, false);
                this.h = KSY_STATUS_OK;
            } else if (this.i == null) {
                this.i = new a();
                this.i.execute(new String[]{"http://sdk.ks-live.com:8989/api/CanHWDecode/2017-01-01"});
                this.h = KSY_STATUS_CONNECTING;
            }
        }
    }

    public int getCurrentStatus() {
        return this.h;
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            this.h = KSY_STATUS_FAIL;
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(new JSONObject(str).getString(b.a));
            if (jSONObject.getInt(b.b) != 0) {
                this.h = KSY_STATUS_FAIL;
                return;
            }
            long j = (long) (jSONObject.getInt(b.c) * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD);
            this.e = jSONObject.getString(b.d);
            this.f = jSONObject.getString(b.e);
            if (!TextUtils.isEmpty(this.e)) {
                this.c = true;
                com.ksyun.media.player.e.a.b(this.g, com.ksyun.media.player.e.a.d, this.e);
            }
            if (!TextUtils.isEmpty(this.f)) {
                this.d = true;
                com.ksyun.media.player.e.a.b(this.g, com.ksyun.media.player.e.a.e, this.f);
            }
            com.ksyun.media.player.e.a.b(this.g, com.ksyun.media.player.e.a.b, this.c);
            com.ksyun.media.player.e.a.b(this.g, com.ksyun.media.player.e.a.c, this.d);
            com.ksyun.media.player.e.a.b(this.g, com.ksyun.media.player.e.a.f, j);
            com.ksyun.media.player.e.a.b(this.g, com.ksyun.media.player.e.a.g, System.currentTimeMillis());
            this.h = KSY_STATUS_OK;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean supportHardwareDecodeH264() {
        if (this.h == KSY_STATUS_OK) {
            return this.c;
        }
        throw new IllegalStateException("This can only be called when the status is KSY_STATUS_OK");
    }

    public boolean supportHardwareDecodeH265() {
        if (this.h == KSY_STATUS_OK) {
            return this.d;
        }
        throw new IllegalStateException("This can only be called when the status is KSY_STATUS_OK");
    }
}
