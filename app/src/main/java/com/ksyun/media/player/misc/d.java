package com.ksyun.media.player.misc;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: KSYListSourceMgr */
public class d {
    private static final int a = 12;
    private static final int b = 13;
    private static final int c = 14;
    private final int d;
    private final String e;
    private final String f;
    private final String g;
    private WeakReference<KSYMediaPlayer> h;
    private List<String> i;
    private ExecutorService j;
    private ArrayList<String> k;
    private Handler l;
    private Map<String, String> m;
    private long n;
    private int o;
    private int p;
    private boolean q;
    private int r;
    private int s;
    private Callback t;

    /* compiled from: KSYListSourceMgr */
    private class a implements Runnable {
        final /* synthetic */ d a;

        public a(d dVar) {
            this.a = dVar;
        }

        public void run() {
            for (int i = 0; i < this.a.i.size() && !this.a.q; i++) {
                String str = (String) this.a.i.get(i);
                KSYProbeMediaInfo kSYProbeMediaInfo = new KSYProbeMediaInfo();
                kSYProbeMediaInfo.probeMediaInfo(str, this.a.o, this.a.m, true);
                if (System.currentTimeMillis() - this.a.n < ((long) this.a.p)) {
                    long mediaDuration = kSYProbeMediaInfo.getMediaDuration();
                    if (mediaDuration > 0) {
                        this.a.l.obtainMessage(d.a, i, 0, String.valueOf(mediaDuration)).sendToTarget();
                    } else {
                        this.a.l.obtainMessage(d.b, i, 0).sendToTarget();
                    }
                } else if (!this.a.q) {
                    this.a.q = true;
                    this.a.l.obtainMessage(d.c).sendToTarget();
                }
            }
        }
    }

    public d() {
        this.d = 10;
        this.e = "#KSYFILELIST";
        this.f = "#KSY_DURATION:";
        this.g = "\r\n";
        this.q = false;
        this.t = new Callback() {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case d.a /*12*/:
                        this.a.r = this.a.r + 1;
                        this.a.s = this.a.s + 1;
                        this.a.k.set(message.arg1, (String) message.obj);
                        break;
                    case d.b /*13*/:
                        this.a.r = this.a.r + 1;
                        break;
                    case d.c /*14*/:
                        this.a.c();
                        break;
                }
                if (this.a.r == this.a.i.size()) {
                    this.a.c();
                }
                return false;
            }
        };
        this.i = new ArrayList();
        this.j = Executors.newSingleThreadExecutor();
        this.k = new ArrayList();
        this.l = new Handler(Looper.getMainLooper(), this.t);
        this.m = new HashMap();
    }

    private void b() {
        this.i.clear();
        this.m.clear();
        this.r = 0;
        this.s = 0;
        this.o = 10;
        this.p = 10000;
        if (!this.k.isEmpty()) {
            this.k.clear();
        }
        this.k = null;
        if (this.h != null) {
            this.h.clear();
        }
        this.h = null;
    }

    private void c() {
        int i = 0;
        if (this.s > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("#KSYFILELIST").append("\r\n");
            while (i < this.i.size()) {
                long longValue = Long.valueOf((String) this.k.get(i)).longValue();
                if (longValue > 0) {
                    stringBuilder.append("#KSY_DURATION:");
                    stringBuilder.append(String.valueOf((float) (longValue / 1000))).append("\r\n");
                    stringBuilder.append((String) this.i.get(i)).append("\r\n");
                }
                i++;
            }
            if (this.h != null && this.h.get() != null) {
                ((KSYMediaPlayer) this.h.get()).prepareSourceList(stringBuilder.toString(), true);
            }
        } else if (this.h != null && this.h.get() != null) {
            ((KSYMediaPlayer) this.h.get()).prepareSourceList(null, false);
        }
    }

    public void a(List<String> list, Map<String, String> map) {
        b();
        this.i.addAll(list);
        if (map != null) {
            this.m.putAll(map);
        }
    }

    public void a(int i) {
        if (i > 0) {
            this.o = i;
            this.p = i * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
        }
    }

    public void a(KSYMediaPlayer kSYMediaPlayer, long j) {
        if (!this.i.isEmpty()) {
            this.n = j;
            this.h = new WeakReference(kSYMediaPlayer);
            this.k = new ArrayList(Collections.nCopies(this.i.size(), "0"));
            this.j.execute(new a(this));
        }
    }

    public void a() {
        this.q = true;
        this.j.shutdown();
        this.l.removeCallbacksAndMessages(null);
    }
}
