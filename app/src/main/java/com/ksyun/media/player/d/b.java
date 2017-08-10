package com.ksyun.media.player.d;

import android.os.Handler;
import com.ksy.statlibrary.interval.IntervalTask;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.f;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* compiled from: KSYDnsInfo */
public class b implements Runnable {
    private Handler a;

    /* compiled from: KSYDnsInfo */
    /* renamed from: com.ksyun.media.player.d.b.1 */
    class AnonymousClass1 implements HostnameVerifier {
        final /* synthetic */ URL a;
        final /* synthetic */ b b;

        AnonymousClass1(b bVar, URL url) {
            this.b = bVar;
            this.a = url;
        }

        public boolean verify(String str, SSLSession sSLSession) {
            if (this.a.getHost().equals(str)) {
                return true;
            }
            return HttpsURLConnection.getDefaultHostnameVerifier().verify(str, sSLSession);
        }
    }

    /* compiled from: KSYDnsInfo */
    private class a implements X509TrustManager {
        final /* synthetic */ b a;

        private a(b bVar) {
            this.a = bVar;
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

    public b(Handler handler) {
        this.a = handler;
    }

    public void run() {
        try {
            URL url = new URL(d.an);
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, new TrustManager[]{new a()}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(instance.getSocketFactory());
            HostnameVerifier anonymousClass1 = new AnonymousClass1(this, url);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(anonymousClass1);
            httpsURLConnection.setConnectTimeout(IntervalTask.TIMEOUT_MILLIS);
            if (httpsURLConnection.getResponseCode() == f.f) {
                Reader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer(com.ksyun.media.player.b.d);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuffer.append(readLine);
                }
                inputStreamReader.close();
                bufferedReader.close();
                this.a.obtainMessage(KSYMediaPlayer.MEDIA_LOG_REPORT, 7, 0, stringBuffer.toString()).sendToTarget();
            } else {
                this.a.obtainMessage(KSYMediaPlayer.MEDIA_LOG_REPORT, Integer.valueOf(7)).sendToTarget();
            }
            httpsURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }
}
