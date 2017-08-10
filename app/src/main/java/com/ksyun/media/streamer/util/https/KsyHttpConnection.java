package com.ksyun.media.streamer.util.https;

import android.util.Log;
import com.ksyun.media.player.b;
import com.ksyun.media.player.f;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class KsyHttpConnection {
    HashMap<String, String> a;
    private final String b;
    private int c;
    private int d;
    private HttpURLConnection e;
    private HttpsURLConnection f;
    private KsyHttpResponse g;
    private Thread h;
    private HttpResponseListener i;
    private final Object j;
    private final Object k;
    private volatile boolean l;
    private List<String> m;

    /* renamed from: com.ksyun.media.streamer.util.https.KsyHttpConnection.1 */
    class AnonymousClass1 implements Runnable {
        URL a;
        BufferedReader b;
        String c;
        int d;
        final /* synthetic */ String e;
        final /* synthetic */ KsyHttpConnection f;

        AnonymousClass1(KsyHttpConnection ksyHttpConnection, String str) {
            this.f = ksyHttpConnection;
            this.e = str;
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = -1;
        }

        public void run() {
            try {
                this.a = new URL(this.e);
                try {
                    synchronized (this.f.j) {
                        if (this.f.l) {
                            this.f.g.setResponseCode(this.d);
                            if (!(Thread.currentThread().isInterrupted() || this.f.i == null)) {
                                this.f.i.onHttpResponse(this.f.g);
                            }
                            if (this.b != null) {
                                try {
                                    this.b.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (this.f.e != null) {
                                this.f.e.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + this.d + " response message : " + this.c);
                            return;
                        }
                        String readLine;
                        this.f.e = (HttpURLConnection) this.a.openConnection();
                        if (this.f.d > 0) {
                            this.f.e.setConnectTimeout(this.f.d);
                        }
                        if (this.f.c > 0) {
                            this.f.e.setReadTimeout(this.f.c);
                        }
                        for (String readLine2 : this.f.a.keySet()) {
                            this.f.e.addRequestProperty(readLine2, (String) this.f.a.get(readLine2));
                        }
                        this.c = this.f.e.getResponseMessage();
                        this.d = this.f.e.getResponseCode();
                        if (this.d == f.f) {
                            this.f.g.restResponse();
                            this.b = new BufferedReader(new InputStreamReader(this.f.e.getInputStream()));
                            while (true) {
                                readLine2 = this.b.readLine();
                                if (readLine2 == null) {
                                    break;
                                }
                                this.f.g.appendData(readLine2);
                            }
                        } else {
                            this.d = -1;
                        }
                        this.f.g.setResponseCode(this.d);
                        if (!(Thread.currentThread().isInterrupted() || this.f.i == null)) {
                            this.f.i.onHttpResponse(this.f.g);
                        }
                        if (this.b != null) {
                            try {
                                this.b.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (this.f.e != null) {
                            this.f.e.disconnect();
                        }
                        Log.d("KsyHttpClient", "http response code: " + this.d + " response message : " + this.c);
                    }
                } catch (IOException e22) {
                    e22.printStackTrace();
                    this.d = -1;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    this.d = -1;
                }
            } catch (MalformedURLException e4) {
                e4.printStackTrace();
                this.d = -1;
                this.f.g.setResponseCode(this.d);
                if (!(Thread.currentThread().isInterrupted() || this.f.i == null)) {
                    this.f.i.onHttpResponse(this.f.g);
                }
                if (this.b != null) {
                    try {
                        this.b.close();
                    } catch (IOException e222) {
                        e222.printStackTrace();
                    }
                }
                if (this.f.e != null) {
                    this.f.e.disconnect();
                }
                Log.d("KsyHttpClient", "http response code: " + this.d + " response message : " + this.c);
            } catch (Throwable th) {
                this.f.g.setResponseCode(this.d);
                if (!(Thread.currentThread().isInterrupted() || this.f.i == null)) {
                    this.f.i.onHttpResponse(this.f.g);
                }
                if (this.b != null) {
                    try {
                        this.b.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (this.f.e != null) {
                    this.f.e.disconnect();
                }
                Log.d("KsyHttpClient", "http response code: " + this.d + " response message : " + this.c);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.util.https.KsyHttpConnection.2 */
    class AnonymousClass2 implements Runnable {
        int a;
        String b;
        URL c;
        SSLContext d;
        BufferedReader e;
        final /* synthetic */ String f;
        final /* synthetic */ KsyHttpConnection g;

        AnonymousClass2(KsyHttpConnection ksyHttpConnection, String str) {
            this.g = ksyHttpConnection;
            this.f = str;
            this.a = -1;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
        }

        public void run() {
            try {
                this.c = new URL(this.f);
                this.d = SSLContext.getInstance("TLS");
                this.d.init(null, new TrustManager[]{new a(this.g)}, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(this.d.getSocketFactory());
                HostnameVerifier anonymousClass1 = new HostnameVerifier() {
                    final /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public boolean verify(String str, SSLSession sSLSession) {
                        if ("videodev.ksyun.com".equals(str) || "rtc.vcloud.ks-live.com".equals(str) || this.a.g.otherHostName(str)) {
                            return true;
                        }
                        return HttpsURLConnection.getDefaultHostnameVerifier().verify(str, sSLSession);
                    }
                };
                synchronized (this.g.k) {
                    if (this.g.l) {
                        this.g.g.setResponseCode(this.a);
                        if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                            this.g.i.onHttpResponse(this.g.g);
                        }
                        if (this.e != null) {
                            try {
                                this.e.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (this.g.f != null) {
                            this.g.f.disconnect();
                            return;
                        }
                        return;
                    }
                    String str;
                    this.g.f = (HttpsURLConnection) this.c.openConnection();
                    this.g.f.setHostnameVerifier(anonymousClass1);
                    for (String str2 : this.g.a.keySet()) {
                        this.g.f.addRequestProperty(str2, (String) this.g.a.get(str2));
                    }
                    if (this.g.d > 0) {
                        this.g.f.setConnectTimeout(this.g.d);
                    }
                    if (this.g.c > 0) {
                        this.g.f.setReadTimeout(this.g.c);
                    }
                    this.a = this.g.f.getResponseCode();
                    this.b = this.g.f.getResponseMessage();
                    if (this.a == f.f) {
                        this.g.g.restResponse();
                        this.e = new BufferedReader(new InputStreamReader(this.g.f.getInputStream()));
                        str2 = b.d;
                        while (true) {
                            str2 = this.e.readLine();
                            if (str2 == null) {
                                break;
                            }
                            this.g.g.appendData(str2);
                        }
                    } else {
                        this.a = -1;
                    }
                    this.g.g.setResponseCode(this.a);
                    if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                        this.g.i.onHttpResponse(this.g.g);
                    }
                    if (this.e != null) {
                        try {
                            this.e.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (this.g.f != null) {
                        this.g.f.disconnect();
                    }
                    Log.d("KsyHttpClient", "https response code: " + this.a + " response message : " + this.b);
                }
            } catch (IOException e22) {
                e22.printStackTrace();
                this.a = -1;
                this.g.g.setResponseCode(this.a);
                if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                    this.g.i.onHttpResponse(this.g.g);
                }
                if (this.e != null) {
                    try {
                        this.e.close();
                    } catch (IOException e222) {
                        e222.printStackTrace();
                    }
                }
                if (this.g.f != null) {
                    this.g.f.disconnect();
                }
            } catch (NoSuchAlgorithmException e3) {
                e3.printStackTrace();
                this.a = -1;
                this.g.g.setResponseCode(this.a);
                if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                    this.g.i.onHttpResponse(this.g.g);
                }
                if (this.e != null) {
                    try {
                        this.e.close();
                    } catch (IOException e2222) {
                        e2222.printStackTrace();
                    }
                }
                if (this.g.f != null) {
                    this.g.f.disconnect();
                }
            } catch (KeyManagementException e4) {
                e4.printStackTrace();
                this.a = -1;
                this.g.g.setResponseCode(this.a);
                if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                    this.g.i.onHttpResponse(this.g.g);
                }
                if (this.e != null) {
                    try {
                        this.e.close();
                    } catch (IOException e22222) {
                        e22222.printStackTrace();
                    }
                }
                if (this.g.f != null) {
                    this.g.f.disconnect();
                }
            } catch (Exception e5) {
                e5.printStackTrace();
                this.a = -1;
                this.g.g.setResponseCode(this.a);
                if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                    this.g.i.onHttpResponse(this.g.g);
                }
                if (this.e != null) {
                    try {
                        this.e.close();
                    } catch (IOException e222222) {
                        e222222.printStackTrace();
                    }
                }
                if (this.g.f != null) {
                    this.g.f.disconnect();
                }
            } catch (Throwable th) {
                this.g.g.setResponseCode(this.a);
                if (!(Thread.currentThread().isInterrupted() || this.g.i == null)) {
                    this.g.i.onHttpResponse(this.g.g);
                }
                if (this.e != null) {
                    try {
                        this.e.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (this.g.f != null) {
                    this.g.f.disconnect();
                }
            }
        }
    }

    public class a implements X509TrustManager {
        final /* synthetic */ KsyHttpConnection a;

        public a(KsyHttpConnection ksyHttpConnection) {
            this.a = ksyHttpConnection;
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public KsyHttpConnection() {
        this.b = "KsyHttpClient";
        this.c = 0;
        this.d = 0;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = new Object();
        this.k = new Object();
        this.l = false;
        this.a = new HashMap();
        this.m = new LinkedList();
        this.g = new KsyHttpResponse();
    }

    public void setTimeout(int i) {
        this.c = i;
    }

    public void setConnectTimeout(int i) {
        this.d = i;
    }

    public void setRequestProperty(String str, String str2) {
        this.a.put(str, str2);
    }

    public void setListener(HttpResponseListener httpResponseListener) {
        this.i = httpResponseListener;
    }

    public void addHostName(String str) {
        if (!this.m.contains(str)) {
            this.m.add(str);
        }
    }

    public void cancelHttpRequest() {
        this.l = true;
        if (this.h != null && this.h.isAlive()) {
            this.h.interrupt();
            try {
                this.h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void performHttpRequest(String str) {
        this.h = new Thread(new AnonymousClass1(this, str));
        this.h.start();
    }

    public void performHttpsRequest(String str) {
        this.h = new Thread(new AnonymousClass2(this, str));
        this.h.start();
    }

    public boolean otherHostName(String str) {
        return this.m.contains(str);
    }
}
