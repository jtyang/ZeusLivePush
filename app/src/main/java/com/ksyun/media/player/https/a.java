package com.ksyun.media.player.https;

import android.util.Log;
import com.ksyun.media.player.f;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.X509TrustManager;

/* compiled from: KsyHttpConnection */
public class a {
    HashMap<String, String> a;
    private final String b;
    private int c;
    private int d;
    private HttpResponseListener e;
    private final Object f;
    private volatile boolean g;

    /* compiled from: KsyHttpConnection */
    /* renamed from: com.ksyun.media.player.https.a.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ a b;

        AnonymousClass1(a aVar, String str) {
            this.b = aVar;
            this.a = str;
        }

        public void run() {
            HttpURLConnection httpURLConnection;
            String responseMessage;
            int i;
            BufferedReader bufferedReader;
            HttpURLConnection httpURLConnection2;
            IOException iOException;
            MalformedURLException malformedURLException;
            MalformedURLException malformedURLException2;
            int i2;
            Throwable th;
            Object obj;
            Exception exception;
            Object obj2;
            Object obj3;
            int i3;
            String str;
            HttpURLConnection httpURLConnection3 = null;
            int i4 = -1;
            BufferedReader bufferedReader2 = null;
            KsyHttpResponse ksyHttpResponse = new KsyHttpResponse();
            try {
                URL url = new URL(this.a);
                String str2;
                try {
                    synchronized (this.b.f) {
                        if (this.b.g) {
                            ksyHttpResponse.setResponseCode(-1);
                            synchronized (this.b.f) {
                                if (!(this.b.e == null || this.b.g)) {
                                    this.b.e.onHttpResponse(ksyHttpResponse);
                                }
                            }
                            if (httpURLConnection3 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + -1 + " response message : " + httpURLConnection3);
                            return;
                        }
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        try {
                            if (this.b.d > 0) {
                                httpURLConnection.setConnectTimeout(this.b.d);
                            }
                            if (this.b.c > 0) {
                                httpURLConnection.setReadTimeout(this.b.c);
                            }
                            for (String str22 : this.b.a.keySet()) {
                                httpURLConnection.addRequestProperty(str22, (String) this.b.a.get(str22));
                            }
                            responseMessage = httpURLConnection.getResponseMessage();
                        } catch (IOException e2) {
                            i = -1;
                            bufferedReader = httpURLConnection3;
                            httpURLConnection2 = httpURLConnection3;
                            iOException = e2;
                            str22 = httpURLConnection2;
                            try {
                                iOException.printStackTrace();
                                ksyHttpResponse.setResponseCode(i4);
                                synchronized (this.b.f) {
                                    this.b.e.onHttpResponse(ksyHttpResponse);
                                }
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                            } catch (MalformedURLException e4) {
                                malformedURLException = e4;
                                httpURLConnection3 = httpURLConnection;
                                malformedURLException2 = malformedURLException;
                                int i5 = i;
                                responseMessage = str22;
                                i2 = i5;
                                try {
                                    malformedURLException2.printStackTrace();
                                    ksyHttpResponse.setResponseCode(-1);
                                    synchronized (this.b.f) {
                                        if (!(this.b.e == null || this.b.g)) {
                                            this.b.e.onHttpResponse(ksyHttpResponse);
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                    if (httpURLConnection3 != null) {
                                        httpURLConnection3.disconnect();
                                    }
                                    Log.d("KsyHttpClient", "http response code: " + -1 + " response message : " + responseMessage);
                                } catch (Throwable th2) {
                                    th = th2;
                                    i4 = i2;
                                    ksyHttpResponse.setResponseCode(i4);
                                    synchronized (this.b.f) {
                                        this.b.e.onHttpResponse(ksyHttpResponse);
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e22) {
                                            e22.printStackTrace();
                                        }
                                    }
                                    if (httpURLConnection3 != null) {
                                        httpURLConnection3.disconnect();
                                    }
                                    Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + responseMessage);
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                httpURLConnection3 = httpURLConnection;
                                th = th3;
                                i4 = i;
                                responseMessage = str22;
                                ksyHttpResponse.setResponseCode(i4);
                                synchronized (this.b.f) {
                                    if (!(this.b.e == null || this.b.g)) {
                                        this.b.e.onHttpResponse(ksyHttpResponse);
                                    }
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (httpURLConnection3 != null) {
                                    httpURLConnection3.disconnect();
                                }
                                Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + responseMessage);
                                throw th;
                            }
                        } catch (Exception e6) {
                            i = -1;
                            obj = httpURLConnection3;
                            httpURLConnection2 = httpURLConnection3;
                            exception = e6;
                            obj2 = httpURLConnection2;
                            exception.printStackTrace();
                            ksyHttpResponse.setResponseCode(i4);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                        } catch (MalformedURLException e7) {
                            obj3 = httpURLConnection3;
                            obj = httpURLConnection3;
                            httpURLConnection3 = httpURLConnection;
                            malformedURLException2 = e7;
                            i2 = -1;
                            malformedURLException2.printStackTrace();
                            ksyHttpResponse.setResponseCode(-1);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + -1 + " response message : " + responseMessage);
                        } catch (Throwable th4) {
                            obj3 = httpURLConnection3;
                            obj = httpURLConnection3;
                            httpURLConnection3 = httpURLConnection;
                            th = th4;
                            ksyHttpResponse.setResponseCode(i4);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + responseMessage);
                            throw th;
                        }
                        try {
                            i2 = httpURLConnection.getResponseCode();
                            if (i2 == f.f) {
                                try {
                                    ksyHttpResponse.restResponse();
                                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                                    while (true) {
                                        try {
                                            String readLine = bufferedReader.readLine();
                                            if (readLine == null) {
                                                break;
                                            }
                                            ksyHttpResponse.appendData(readLine);
                                        } catch (IOException e8) {
                                            iOException = e8;
                                            i3 = i2;
                                            str22 = responseMessage;
                                            i = i3;
                                        } catch (Exception e9) {
                                            exception = e9;
                                            i3 = i2;
                                            str22 = responseMessage;
                                            i = i3;
                                        } catch (MalformedURLException e42) {
                                            malformedURLException = e42;
                                            httpURLConnection3 = httpURLConnection;
                                            malformedURLException2 = malformedURLException;
                                        } catch (Throwable th32) {
                                            httpURLConnection3 = httpURLConnection;
                                            th = th32;
                                            i4 = i2;
                                        }
                                    }
                                } catch (IOException e32) {
                                    IOException iOException2 = e32;
                                    obj = httpURLConnection3;
                                    iOException = iOException2;
                                    str = responseMessage;
                                    i = i2;
                                    str22 = str;
                                    iOException.printStackTrace();
                                    ksyHttpResponse.setResponseCode(i4);
                                    synchronized (this.b.f) {
                                        this.b.e.onHttpResponse(ksyHttpResponse);
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                                } catch (Exception e10) {
                                    Exception exception2 = e10;
                                    obj = httpURLConnection3;
                                    exception = exception2;
                                    str = responseMessage;
                                    i = i2;
                                    str22 = str;
                                    exception.printStackTrace();
                                    ksyHttpResponse.setResponseCode(i4);
                                    synchronized (this.b.f) {
                                        this.b.e.onHttpResponse(ksyHttpResponse);
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                                } catch (MalformedURLException e11) {
                                    malformedURLException = e11;
                                    obj = httpURLConnection3;
                                    httpURLConnection3 = httpURLConnection;
                                    malformedURLException2 = malformedURLException;
                                    malformedURLException2.printStackTrace();
                                    ksyHttpResponse.setResponseCode(-1);
                                    synchronized (this.b.f) {
                                        this.b.e.onHttpResponse(ksyHttpResponse);
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    if (httpURLConnection3 != null) {
                                        httpURLConnection3.disconnect();
                                    }
                                    Log.d("KsyHttpClient", "http response code: " + -1 + " response message : " + responseMessage);
                                } catch (Throwable th5) {
                                    i4 = i2;
                                    httpURLConnection2 = httpURLConnection;
                                    th = th5;
                                    obj = httpURLConnection3;
                                    httpURLConnection3 = httpURLConnection2;
                                    ksyHttpResponse.setResponseCode(i4);
                                    synchronized (this.b.f) {
                                        this.b.e.onHttpResponse(ksyHttpResponse);
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    if (httpURLConnection3 != null) {
                                        httpURLConnection3.disconnect();
                                    }
                                    Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + responseMessage);
                                    throw th;
                                }
                            }
                            i2 = -1;
                            bufferedReader = httpURLConnection3;
                            i4 = i2;
                            str22 = responseMessage;
                        } catch (IOException e222) {
                            obj = httpURLConnection3;
                            iOException = e222;
                            str22 = responseMessage;
                            i = -1;
                            iOException.printStackTrace();
                            ksyHttpResponse.setResponseCode(i4);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                        } catch (Exception e62) {
                            obj = httpURLConnection3;
                            exception = e62;
                            str22 = responseMessage;
                            i = -1;
                            exception.printStackTrace();
                            ksyHttpResponse.setResponseCode(i4);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                        } catch (MalformedURLException e72) {
                            obj = httpURLConnection3;
                            httpURLConnection3 = httpURLConnection;
                            malformedURLException2 = e72;
                            i2 = -1;
                            malformedURLException2.printStackTrace();
                            ksyHttpResponse.setResponseCode(-1);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + -1 + " response message : " + responseMessage);
                        } catch (Throwable th42) {
                            obj = httpURLConnection3;
                            httpURLConnection3 = httpURLConnection;
                            th = th42;
                            ksyHttpResponse.setResponseCode(i4);
                            synchronized (this.b.f) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection3 != null) {
                                httpURLConnection3.disconnect();
                            }
                            Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + responseMessage);
                            throw th;
                        }
                        ksyHttpResponse.setResponseCode(i4);
                        synchronized (this.b.f) {
                            if (!(this.b.e == null || this.b.g)) {
                                this.b.e.onHttpResponse(ksyHttpResponse);
                            }
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                    }
                } catch (IOException e52) {
                    i = -1;
                    obj2 = httpURLConnection3;
                    obj = httpURLConnection3;
                    httpURLConnection2 = httpURLConnection3;
                    iOException = e52;
                    httpURLConnection = httpURLConnection2;
                } catch (Exception e12) {
                    i = -1;
                    str22 = httpURLConnection3;
                    bufferedReader = httpURLConnection3;
                    httpURLConnection2 = httpURLConnection3;
                    exception = e12;
                    httpURLConnection = httpURLConnection2;
                    exception.printStackTrace();
                    ksyHttpResponse.setResponseCode(i4);
                    synchronized (this.b.f) {
                        this.b.e.onHttpResponse(ksyHttpResponse);
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + str22);
                }
            } catch (MalformedURLException e13) {
                malformedURLException2 = e13;
                i2 = -1;
                obj3 = httpURLConnection3;
                obj = httpURLConnection3;
                malformedURLException2.printStackTrace();
                ksyHttpResponse.setResponseCode(-1);
                synchronized (this.b.f) {
                    this.b.e.onHttpResponse(ksyHttpResponse);
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                Log.d("KsyHttpClient", "http response code: " + -1 + " response message : " + responseMessage);
            } catch (Throwable th6) {
                th = th6;
                responseMessage = httpURLConnection3;
                bufferedReader = httpURLConnection3;
                ksyHttpResponse.setResponseCode(i4);
                synchronized (this.b.f) {
                    this.b.e.onHttpResponse(ksyHttpResponse);
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                Log.d("KsyHttpClient", "http response code: " + i4 + " response message : " + responseMessage);
                throw th;
            }
        }
    }

    /* compiled from: KsyHttpConnection */
    /* renamed from: com.ksyun.media.player.https.a.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ a b;

        AnonymousClass2(a aVar, String str) {
            this.b = aVar;
            this.a = str;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r12 = this;
            r3 = 0;
            r4 = -1;
            r0 = 0;
            r6 = new com.ksyun.media.player.https.KsyHttpResponse;
            r6.<init>();
            r1 = new java.net.URL;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r2 = r12.a;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r1.<init>(r2);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r2 = "TLS";
            r2 = javax.net.ssl.SSLContext.getInstance(r2);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r5 = 0;
            r7 = 1;
            r7 = new javax.net.ssl.TrustManager[r7];	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r8 = 0;
            r9 = new com.ksyun.media.player.https.a$a;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r10 = r12.b;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r9.<init>(r10);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r7[r8] = r9;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r8 = 0;
            r2.init(r5, r7, r8);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r2 = r2.getSocketFactory();	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(r2);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r2 = new com.ksyun.media.player.https.a$2$1;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r2.<init>(r12);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r5 = r12.b;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r5 = r5.f;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            monitor-enter(r5);	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r7 = r12.b;	 Catch:{ all -> 0x0105 }
            r7 = r7.g;	 Catch:{ all -> 0x0105 }
            if (r7 == 0) goto L_0x007a;
        L_0x0042:
            monitor-exit(r5);	 Catch:{ all -> 0x0105 }
            r6.setResponseCode(r4);
            r1 = r12.b;
            r1 = r1.f;
            monitor-enter(r1);
            r2 = r12.b;	 Catch:{ all -> 0x0072 }
            r2 = r2.e;	 Catch:{ all -> 0x0072 }
            if (r2 == 0) goto L_0x0066;
        L_0x0055:
            r2 = r12.b;	 Catch:{ all -> 0x0072 }
            r2 = r2.g;	 Catch:{ all -> 0x0072 }
            if (r2 != 0) goto L_0x0066;
        L_0x005d:
            r2 = r12.b;	 Catch:{ all -> 0x0072 }
            r2 = r2.e;	 Catch:{ all -> 0x0072 }
            r2.onHttpResponse(r6);	 Catch:{ all -> 0x0072 }
        L_0x0066:
            monitor-exit(r1);	 Catch:{ all -> 0x0072 }
            if (r3 == 0) goto L_0x006c;
        L_0x0069:
            r0.close();	 Catch:{ IOException -> 0x0075 }
        L_0x006c:
            if (r3 == 0) goto L_0x0071;
        L_0x006e:
            r3.disconnect();
        L_0x0071:
            return;
        L_0x0072:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0072 }
            throw r0;
        L_0x0075:
            r0 = move-exception;
            r0.printStackTrace();
            goto L_0x006c;
        L_0x007a:
            monitor-exit(r5);	 Catch:{ all -> 0x0105 }
            r0 = r1.openConnection();	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
            r0.setHostnameVerifier(r2);	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r12.b;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r1.a;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r1.keySet();	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r5 = r1.iterator();	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
        L_0x0090:
            r1 = r5.hasNext();	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            if (r1 == 0) goto L_0x010e;
        L_0x0096:
            r1 = r5.next();	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = (java.lang.String) r1;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r2 = r12.b;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r2 = r2.a;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r2 = r2.get(r1);	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r2 = (java.lang.String) r2;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r0.addRequestProperty(r1, r2);	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            goto L_0x0090;
        L_0x00aa:
            r1 = move-exception;
            r2 = r3;
            r5 = r4;
            r11 = r3;
            r3 = r0;
            r0 = r11;
        L_0x00b0:
            r1.printStackTrace();	 Catch:{ all -> 0x02b2 }
            r6.setResponseCode(r4);
            r1 = r12.b;
            r1 = r1.f;
            monitor-enter(r1);
            r5 = r12.b;	 Catch:{ all -> 0x019e }
            r5 = r5.e;	 Catch:{ all -> 0x019e }
            if (r5 == 0) goto L_0x00d6;
        L_0x00c5:
            r5 = r12.b;	 Catch:{ all -> 0x019e }
            r5 = r5.g;	 Catch:{ all -> 0x019e }
            if (r5 != 0) goto L_0x00d6;
        L_0x00cd:
            r5 = r12.b;	 Catch:{ all -> 0x019e }
            r5 = r5.e;	 Catch:{ all -> 0x019e }
            r5.onHttpResponse(r6);	 Catch:{ all -> 0x019e }
        L_0x00d6:
            monitor-exit(r1);	 Catch:{ all -> 0x019e }
            if (r2 == 0) goto L_0x00dc;
        L_0x00d9:
            r2.close();	 Catch:{ IOException -> 0x01a1 }
        L_0x00dc:
            if (r3 == 0) goto L_0x00e1;
        L_0x00de:
            r3.disconnect();
        L_0x00e1:
            r1 = "KsyHttpClient";
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "https response code: ";
            r2 = r2.append(r3);
            r2 = r2.append(r4);
            r3 = " response message : ";
            r2 = r2.append(r3);
            r0 = r2.append(r0);
            r0 = r0.toString();
            android.util.Log.d(r1, r0);
            goto L_0x0071;
        L_0x0105:
            r0 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0105 }
            throw r0;	 Catch:{ IOException -> 0x0108, NoSuchAlgorithmException -> 0x01a7, KeyManagementException -> 0x01e7, Exception -> 0x0227, all -> 0x0267 }
        L_0x0108:
            r0 = move-exception;
            r1 = r0;
            r2 = r3;
            r5 = r4;
            r0 = r3;
            goto L_0x00b0;
        L_0x010e:
            r1 = r12.b;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r1.d;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            if (r1 <= 0) goto L_0x011f;
        L_0x0116:
            r1 = r12.b;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r1.d;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r0.setConnectTimeout(r1);	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
        L_0x011f:
            r1 = r12.b;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r1.c;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            if (r1 <= 0) goto L_0x0130;
        L_0x0127:
            r1 = r12.b;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r1 = r1.c;	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r0.setReadTimeout(r1);	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
        L_0x0130:
            r2 = r0.getResponseCode();	 Catch:{ IOException -> 0x00aa, NoSuchAlgorithmException -> 0x02f7, KeyManagementException -> 0x02d6, Exception -> 0x02b5, all -> 0x02a0 }
            r5 = r0.getResponseMessage();	 Catch:{ IOException -> 0x0318, NoSuchAlgorithmException -> 0x02ff, KeyManagementException -> 0x02de, Exception -> 0x02bd, all -> 0x02a5 }
            r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r2 != r1) goto L_0x0162;
        L_0x013c:
            r6.restResponse();	 Catch:{ IOException -> 0x0320, NoSuchAlgorithmException -> 0x0307, KeyManagementException -> 0x02e6, Exception -> 0x02c5, all -> 0x02a5 }
            r1 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0320, NoSuchAlgorithmException -> 0x0307, KeyManagementException -> 0x02e6, Exception -> 0x02c5, all -> 0x02a5 }
            r7 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0320, NoSuchAlgorithmException -> 0x0307, KeyManagementException -> 0x02e6, Exception -> 0x02c5, all -> 0x02a5 }
            r8 = r0.getInputStream();	 Catch:{ IOException -> 0x0320, NoSuchAlgorithmException -> 0x0307, KeyManagementException -> 0x02e6, Exception -> 0x02c5, all -> 0x02a5 }
            r7.<init>(r8);	 Catch:{ IOException -> 0x0320, NoSuchAlgorithmException -> 0x0307, KeyManagementException -> 0x02e6, Exception -> 0x02c5, all -> 0x02a5 }
            r1.<init>(r7);	 Catch:{ IOException -> 0x0320, NoSuchAlgorithmException -> 0x0307, KeyManagementException -> 0x02e6, Exception -> 0x02c5, all -> 0x02a5 }
            r3 = "";
        L_0x014f:
            r3 = r1.readLine();	 Catch:{ IOException -> 0x0159, NoSuchAlgorithmException -> 0x030f, KeyManagementException -> 0x02ee, Exception -> 0x02cd, all -> 0x02ab }
            if (r3 == 0) goto L_0x0164;
        L_0x0155:
            r6.appendData(r3);	 Catch:{ IOException -> 0x0159, NoSuchAlgorithmException -> 0x030f, KeyManagementException -> 0x02ee, Exception -> 0x02cd, all -> 0x02ab }
            goto L_0x014f;
        L_0x0159:
            r3 = move-exception;
            r11 = r3;
            r3 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r1;
            r1 = r11;
            goto L_0x00b0;
        L_0x0162:
            r1 = r3;
            r2 = r4;
        L_0x0164:
            r6.setResponseCode(r2);
            r3 = r12.b;
            r3 = r3.f;
            monitor-enter(r3);
            r4 = r12.b;	 Catch:{ all -> 0x0196 }
            r4 = r4.e;	 Catch:{ all -> 0x0196 }
            if (r4 == 0) goto L_0x0187;
        L_0x0176:
            r4 = r12.b;	 Catch:{ all -> 0x0196 }
            r4 = r4.g;	 Catch:{ all -> 0x0196 }
            if (r4 != 0) goto L_0x0187;
        L_0x017e:
            r4 = r12.b;	 Catch:{ all -> 0x0196 }
            r4 = r4.e;	 Catch:{ all -> 0x0196 }
            r4.onHttpResponse(r6);	 Catch:{ all -> 0x0196 }
        L_0x0187:
            monitor-exit(r3);	 Catch:{ all -> 0x0196 }
            if (r1 == 0) goto L_0x018d;
        L_0x018a:
            r1.close();	 Catch:{ IOException -> 0x0199 }
        L_0x018d:
            if (r0 == 0) goto L_0x0328;
        L_0x018f:
            r0.disconnect();
            r0 = r5;
            r4 = r2;
            goto L_0x00e1;
        L_0x0196:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0196 }
            throw r0;
        L_0x0199:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x018d;
        L_0x019e:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x019e }
            throw r0;
        L_0x01a1:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x00dc;
        L_0x01a7:
            r0 = move-exception;
            r1 = r0;
            r2 = r3;
            r5 = r4;
            r0 = r3;
        L_0x01ac:
            r1.printStackTrace();	 Catch:{ all -> 0x02b2 }
            r6.setResponseCode(r4);
            r1 = r12.b;
            r1 = r1.f;
            monitor-enter(r1);
            r5 = r12.b;	 Catch:{ all -> 0x01df }
            r5 = r5.e;	 Catch:{ all -> 0x01df }
            if (r5 == 0) goto L_0x01d2;
        L_0x01c1:
            r5 = r12.b;	 Catch:{ all -> 0x01df }
            r5 = r5.g;	 Catch:{ all -> 0x01df }
            if (r5 != 0) goto L_0x01d2;
        L_0x01c9:
            r5 = r12.b;	 Catch:{ all -> 0x01df }
            r5 = r5.e;	 Catch:{ all -> 0x01df }
            r5.onHttpResponse(r6);	 Catch:{ all -> 0x01df }
        L_0x01d2:
            monitor-exit(r1);	 Catch:{ all -> 0x01df }
            if (r2 == 0) goto L_0x01d8;
        L_0x01d5:
            r2.close();	 Catch:{ IOException -> 0x01e2 }
        L_0x01d8:
            if (r3 == 0) goto L_0x00e1;
        L_0x01da:
            r3.disconnect();
            goto L_0x00e1;
        L_0x01df:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x01df }
            throw r0;
        L_0x01e2:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x01d8;
        L_0x01e7:
            r0 = move-exception;
            r1 = r0;
            r2 = r3;
            r5 = r4;
            r0 = r3;
        L_0x01ec:
            r1.printStackTrace();	 Catch:{ all -> 0x02b2 }
            r6.setResponseCode(r4);
            r1 = r12.b;
            r1 = r1.f;
            monitor-enter(r1);
            r5 = r12.b;	 Catch:{ all -> 0x021f }
            r5 = r5.e;	 Catch:{ all -> 0x021f }
            if (r5 == 0) goto L_0x0212;
        L_0x0201:
            r5 = r12.b;	 Catch:{ all -> 0x021f }
            r5 = r5.g;	 Catch:{ all -> 0x021f }
            if (r5 != 0) goto L_0x0212;
        L_0x0209:
            r5 = r12.b;	 Catch:{ all -> 0x021f }
            r5 = r5.e;	 Catch:{ all -> 0x021f }
            r5.onHttpResponse(r6);	 Catch:{ all -> 0x021f }
        L_0x0212:
            monitor-exit(r1);	 Catch:{ all -> 0x021f }
            if (r2 == 0) goto L_0x0218;
        L_0x0215:
            r2.close();	 Catch:{ IOException -> 0x0222 }
        L_0x0218:
            if (r3 == 0) goto L_0x00e1;
        L_0x021a:
            r3.disconnect();
            goto L_0x00e1;
        L_0x021f:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x021f }
            throw r0;
        L_0x0222:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x0218;
        L_0x0227:
            r0 = move-exception;
            r1 = r0;
            r2 = r3;
            r5 = r4;
            r0 = r3;
        L_0x022c:
            r1.printStackTrace();	 Catch:{ all -> 0x02b2 }
            r6.setResponseCode(r4);
            r1 = r12.b;
            r1 = r1.f;
            monitor-enter(r1);
            r5 = r12.b;	 Catch:{ all -> 0x025f }
            r5 = r5.e;	 Catch:{ all -> 0x025f }
            if (r5 == 0) goto L_0x0252;
        L_0x0241:
            r5 = r12.b;	 Catch:{ all -> 0x025f }
            r5 = r5.g;	 Catch:{ all -> 0x025f }
            if (r5 != 0) goto L_0x0252;
        L_0x0249:
            r5 = r12.b;	 Catch:{ all -> 0x025f }
            r5 = r5.e;	 Catch:{ all -> 0x025f }
            r5.onHttpResponse(r6);	 Catch:{ all -> 0x025f }
        L_0x0252:
            monitor-exit(r1);	 Catch:{ all -> 0x025f }
            if (r2 == 0) goto L_0x0258;
        L_0x0255:
            r2.close();	 Catch:{ IOException -> 0x0262 }
        L_0x0258:
            if (r3 == 0) goto L_0x00e1;
        L_0x025a:
            r3.disconnect();
            goto L_0x00e1;
        L_0x025f:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x025f }
            throw r0;
        L_0x0262:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x0258;
        L_0x0267:
            r0 = move-exception;
            r2 = r3;
        L_0x0269:
            r6.setResponseCode(r4);
            r1 = r12.b;
            r1 = r1.f;
            monitor-enter(r1);
            r4 = r12.b;	 Catch:{ all -> 0x0298 }
            r4 = r4.e;	 Catch:{ all -> 0x0298 }
            if (r4 == 0) goto L_0x028c;
        L_0x027b:
            r4 = r12.b;	 Catch:{ all -> 0x0298 }
            r4 = r4.g;	 Catch:{ all -> 0x0298 }
            if (r4 != 0) goto L_0x028c;
        L_0x0283:
            r4 = r12.b;	 Catch:{ all -> 0x0298 }
            r4 = r4.e;	 Catch:{ all -> 0x0298 }
            r4.onHttpResponse(r6);	 Catch:{ all -> 0x0298 }
        L_0x028c:
            monitor-exit(r1);	 Catch:{ all -> 0x0298 }
            if (r2 == 0) goto L_0x0292;
        L_0x028f:
            r2.close();	 Catch:{ IOException -> 0x029b }
        L_0x0292:
            if (r3 == 0) goto L_0x0297;
        L_0x0294:
            r3.disconnect();
        L_0x0297:
            throw r0;
        L_0x0298:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0298 }
            throw r0;
        L_0x029b:
            r1 = move-exception;
            r1.printStackTrace();
            goto L_0x0292;
        L_0x02a0:
            r1 = move-exception;
            r2 = r3;
            r3 = r0;
            r0 = r1;
            goto L_0x0269;
        L_0x02a5:
            r1 = move-exception;
            r4 = r2;
            r2 = r3;
            r3 = r0;
            r0 = r1;
            goto L_0x0269;
        L_0x02ab:
            r3 = move-exception;
            r4 = r2;
            r2 = r1;
            r11 = r0;
            r0 = r3;
            r3 = r11;
            goto L_0x0269;
        L_0x02b2:
            r0 = move-exception;
            r4 = r5;
            goto L_0x0269;
        L_0x02b5:
            r1 = move-exception;
            r2 = r3;
            r5 = r4;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x022c;
        L_0x02bd:
            r1 = move-exception;
            r5 = r2;
            r2 = r3;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x022c;
        L_0x02c5:
            r1 = move-exception;
            r11 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r3;
            r3 = r11;
            goto L_0x022c;
        L_0x02cd:
            r3 = move-exception;
            r11 = r3;
            r3 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r1;
            r1 = r11;
            goto L_0x022c;
        L_0x02d6:
            r1 = move-exception;
            r2 = r3;
            r5 = r4;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x01ec;
        L_0x02de:
            r1 = move-exception;
            r5 = r2;
            r2 = r3;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x01ec;
        L_0x02e6:
            r1 = move-exception;
            r11 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r3;
            r3 = r11;
            goto L_0x01ec;
        L_0x02ee:
            r3 = move-exception;
            r11 = r3;
            r3 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r1;
            r1 = r11;
            goto L_0x01ec;
        L_0x02f7:
            r1 = move-exception;
            r2 = r3;
            r5 = r4;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x01ac;
        L_0x02ff:
            r1 = move-exception;
            r5 = r2;
            r2 = r3;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x01ac;
        L_0x0307:
            r1 = move-exception;
            r11 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r3;
            r3 = r11;
            goto L_0x01ac;
        L_0x030f:
            r3 = move-exception;
            r11 = r3;
            r3 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r1;
            r1 = r11;
            goto L_0x01ac;
        L_0x0318:
            r1 = move-exception;
            r5 = r2;
            r2 = r3;
            r11 = r3;
            r3 = r0;
            r0 = r11;
            goto L_0x00b0;
        L_0x0320:
            r1 = move-exception;
            r11 = r0;
            r0 = r5;
            r5 = r2;
            r2 = r3;
            r3 = r11;
            goto L_0x00b0;
        L_0x0328:
            r0 = r5;
            r4 = r2;
            goto L_0x00e1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.player.https.a.2.run():void");
        }
    }

    /* compiled from: KsyHttpConnection */
    public class a implements X509TrustManager {
        final /* synthetic */ a a;

        public a(a aVar) {
            this.a = aVar;
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                x509CertificateArr[0].checkValidity();
            } catch (Exception e) {
                throw new CertificateException("Certificate not valid or trusted.");
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    a() {
        this.b = "KsyHttpClient";
        this.c = 0;
        this.d = 0;
        this.e = null;
        this.f = new Object();
        this.g = false;
        this.a = new HashMap();
        this.g = false;
    }

    void a(int i) {
        this.c = i;
    }

    void b(int i) {
        this.d = i;
    }

    void a(String str, String str2) {
        this.a.put(str, str2);
    }

    void a(HttpResponseListener httpResponseListener) {
        this.e = httpResponseListener;
    }

    public void a() {
        synchronized (this.f) {
            this.g = true;
        }
    }

    public void a(String str) {
        new Thread(new AnonymousClass1(this, str)).start();
    }

    public void b(String str) {
        new Thread(new AnonymousClass2(this, str)).start();
    }
}
