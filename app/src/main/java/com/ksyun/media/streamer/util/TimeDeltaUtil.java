package com.ksyun.media.streamer.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.ksy.statlibrary.interval.IntervalTask;
import com.ksyun.media.streamer.util.https.HttpResponseListener;
import com.ksyun.media.streamer.util.https.KsyHttpClient;
import com.ksyun.media.streamer.util.https.KsyHttpResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TimeDeltaUtil {
    public static final int NTP_QUIT = 4;
    public static final int NTP_SERVER_REQUEST = 2;
    public static final int NTP_SERVER_RETRY = 3;
    public static final int URL_PARSE = 1;
    private static final String a = "TimeDeltaUtil";
    private static final String b = "http://centertime.ksyun.com/time";
    private static final String c = "http://";
    private static final int m = 64;
    private static TimeDeltaUtil u;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    private long i;
    private volatile int j;
    private HandlerThread k;
    private Handler l;
    private String n;
    private String o;
    private long p;
    private String q;
    private String r;
    private long s;
    private long t;

    /* renamed from: com.ksyun.media.streamer.util.TimeDeltaUtil.1 */
    class AnonymousClass1 extends Handler {
        final /* synthetic */ TimeDeltaUtil a;

        AnonymousClass1(TimeDeltaUtil timeDeltaUtil, Looper looper) {
            this.a = timeDeltaUtil;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case TimeDeltaUtil.URL_PARSE /*1*/:
                    this.a.b();
                case TimeDeltaUtil.NTP_SERVER_REQUEST /*2*/:
                    this.a.c();
                case TimeDeltaUtil.NTP_SERVER_RETRY /*3*/:
                    this.a.d();
                case TimeDeltaUtil.NTP_QUIT /*4*/:
                    Log.e(TimeDeltaUtil.a, "quit");
                    this.a.k.quit();
                default:
            }
        }
    }

    public class a implements HttpResponseListener {
        final /* synthetic */ TimeDeltaUtil a;

        public a(TimeDeltaUtil timeDeltaUtil) {
            this.a = timeDeltaUtil;
        }

        public void onHttpResponse(KsyHttpResponse ksyHttpResponse) {
            String data = ksyHttpResponse.getData();
            if (ksyHttpResponse.getData() == null || ksyHttpResponse.getData().length() == 0 || -1 == ksyHttpResponse.getResponseCode()) {
                this.a.l.removeMessages(TimeDeltaUtil.NTP_SERVER_RETRY);
                this.a.l.sendEmptyMessage(TimeDeltaUtil.NTP_SERVER_RETRY);
                return;
            }
            this.a.t = System.currentTimeMillis();
            if (this.a.t - this.a.s <= 100) {
                try {
                    this.a.i = ((long) (Double.valueOf(data.trim()).doubleValue() * 1000.0d)) - ((this.a.s + this.a.t) / 2);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Log.e(TimeDeltaUtil.a, "server data error:" + data);
                    this.a.l.removeMessages(TimeDeltaUtil.NTP_SERVER_RETRY);
                    this.a.l.sendEmptyMessage(TimeDeltaUtil.NTP_SERVER_RETRY);
                    return;
                }
            }
            this.a.l.removeMessages(TimeDeltaUtil.NTP_SERVER_RETRY);
            this.a.l.sendEmptyMessage(TimeDeltaUtil.NTP_SERVER_RETRY);
        }
    }

    public TimeDeltaUtil() {
        this.d = 100;
        this.e = -1;
        this.f = IntervalTask.TIMEOUT_MILLIS;
        this.g = 100;
        this.h = NTP_SERVER_RETRY;
        this.i = Long.MAX_VALUE;
        this.j = 0;
    }

    public static TimeDeltaUtil getInstance() {
        synchronized (TimeDeltaUtil.class) {
            if (u == null) {
                synchronized (TimeDeltaUtil.class) {
                    if (u == null) {
                        u = new TimeDeltaUtil();
                    }
                }
            }
        }
        return u;
    }

    public long getTimeDelta() {
        if (this.i == Long.MAX_VALUE) {
            a();
        }
        return this.i;
    }

    private void a() {
        if (this.k == null) {
            this.k = new HandlerThread("ksy_sync_time_thread", 5);
            this.k.start();
            this.l = new AnonymousClass1(this, this.k.getLooper());
            this.l.removeMessages(URL_PARSE);
            this.l.sendEmptyMessage(URL_PARSE);
        }
    }

    private void b() {
        if (a(b) != 0) {
            Log.e(a, "url parse failed");
            return;
        }
        if (c(this.n)) {
            this.q = new String(b);
        } else {
            int indexOf = b.substring(b.indexOf(c) + c.length()).indexOf(47);
            StringBuilder stringBuilder = new StringBuilder(c);
            if (this.o != null) {
                stringBuilder.append(this.o);
            }
            if (indexOf != -1 && indexOf < b.length()) {
                stringBuilder.append(b.substring(indexOf + c.length()));
            }
            this.q = new String(stringBuilder.toString());
            if (this.p != 0) {
                this.r = new String(this.n + ":" + String.valueOf(this.p));
            } else {
                this.r = new String(this.n);
            }
        }
        this.l.removeMessages(NTP_SERVER_REQUEST);
        this.l.sendEmptyMessage(NTP_SERVER_REQUEST);
    }

    private void c() {
        e();
    }

    private void d() {
        this.j += URL_PARSE;
        if (this.j < NTP_SERVER_RETRY) {
            int i = 0;
            while (i < IntervalTask.TIMEOUT_MILLIS) {
                try {
                    Thread.sleep(100);
                    i += 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e(a, "doRetry:" + this.j);
            this.l.removeMessages(NTP_SERVER_REQUEST);
            this.l.sendEmptyMessage(NTP_SERVER_REQUEST);
            return;
        }
        this.l.removeMessages(NTP_QUIT);
        this.l.sendEmptyMessage(NTP_QUIT);
    }

    private int a(String str) {
        int i = 0;
        if (str == null || str.substring(0, c.length()).compareToIgnoreCase(c) != 0) {
            return -1;
        }
        if (b(str) != 0) {
            Log.w(a, "host or port parse failed");
            return -1;
        }
        if (this.n == null) {
            i = -1;
        } else if (c(this.n)) {
            this.o = new String(this.n);
        } else {
            try {
                this.o = InetAddress.getByName(this.n).getHostAddress().toString();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                i = -1;
            }
        }
        return i;
    }

    private int b(String str) {
        int length = c.length();
        int length2 = str.length();
        if (length >= str.length()) {
            return -1;
        }
        String substring = str.substring(length);
        int i;
        if (substring.charAt(length) == '[' && substring.contains("]")) {
            length2 = substring.indexOf(93);
            if (length2 <= length || (length2 - length) + URL_PARSE >= m) {
                return -1;
            }
            this.n = str.substring(length, length2 + URL_PARSE);
            i = length2 + URL_PARSE;
            if (substring.charAt(i) != ':') {
                return 0;
            }
            this.p = Long.valueOf(substring.substring(i + URL_PARSE)).longValue();
            return 0;
        }
        i = 0;
        int i2 = length;
        while (i2 < length2) {
            char charAt = str.charAt(i2);
            if (charAt == ':' || charAt == '/') {
                if (charAt == ':') {
                    i2 += URL_PARSE;
                    this.p = Long.valueOf(str.substring(i2)).longValue();
                }
                this.n = str.substring(length, i2);
                return 0;
            } else if (i < m) {
                i += URL_PARSE;
                i2 += URL_PARSE;
            } else {
                i = 0;
            }
        }
        this.n = str.substring(length, i2);
        return 0;
    }

    private boolean c(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']' && str.contains(":")) {
            return true;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < NTP_QUIT) {
            int i4 = i3;
            i3 = 0;
            while (str.charAt(i4) != '.' && str.charAt(i4) != '\u0000') {
                if (i4 - i2 > NTP_SERVER_REQUEST || str.charAt(i4) < '0' || str.charAt(i4) > '9') {
                    return false;
                }
                i3 = ((i3 * 10) + str.charAt(i4)) - 48;
                i4 += URL_PARSE;
            }
            if (i4 == i2) {
                return false;
            }
            if (i3 > 255) {
                return false;
            }
            i4 += URL_PARSE;
            i += URL_PARSE;
            i2 = i4;
            i3 = i4;
        }
        if (i == NTP_QUIT && str.charAt(i3 - 1) == '\u0000' && str.charAt(i3 - 2) != '.') {
            return true;
        }
        return false;
    }

    private void e() {
        KsyHttpClient ksyHttpClient = new KsyHttpClient();
        ksyHttpClient.setTimeout(3000);
        ksyHttpClient.setConnectTimetout(3000);
        ksyHttpClient.setRequestProperty("Host", this.r);
        ksyHttpClient.setListener(new a(this));
        this.s = System.currentTimeMillis();
        ksyHttpClient.performHttpRequest(this.q);
    }
}
