package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;

/* compiled from: AudioAPMFilterMgt */
public class a {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public static final int d = 3;
    private static final String e = "AudioAPMFilterMgt";
    private static final int f = 8000;
    private static final int g = 16000;
    private static final int h = 32000;
    private static final int i = 44100;
    private static final int j = 48000;
    private SinkPin<AudioBufFrame> k;
    private SrcPin<AudioBufFrame> l;
    private PinAdapter<AudioBufFrame> m;
    private AudioResampleFilter n;
    private APMFilter o;
    private AudioBufFormat p;
    private boolean q;
    private int r;

    /* compiled from: AudioAPMFilterMgt */
    private class a extends SinkPin<AudioBufFrame> {
        final /* synthetic */ a a;

        private a(a aVar) {
            this.a = aVar;
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((AudioBufFrame) obj);
        }

        public void onFormatChanged(Object obj) {
            if (obj != null) {
                AudioBufFormat audioBufFormat = (AudioBufFormat) obj;
                switch (audioBufFormat.sampleRate) {
                    case a.f /*8000*/:
                    case a.g /*16000*/:
                    case a.h /*32000*/:
                    case a.j /*48000*/:
                        this.a.p = new AudioBufFormat(a.b, audioBufFormat.sampleRate, audioBufFormat.channels);
                        break;
                    default:
                        this.a.p = new AudioBufFormat(a.b, a.j, audioBufFormat.channels);
                        break;
                }
                this.a.n.setOutFormat(this.a.p);
                this.a.l.onFormatChanged(obj);
            }
        }

        public void a(AudioBufFrame audioBufFrame) {
            this.a.l.onFrameAvailable(audioBufFrame);
        }

        public void onDisconnect(boolean z) {
            if (z) {
                this.a.d();
            }
        }
    }

    public a() {
        this.q = false;
        this.r = b;
        this.k = new a();
        this.l = new c();
        this.m = new b();
        this.n = new AudioResampleFilter();
        this.o = new APMFilter();
        this.o.enableNs(this.q);
        this.o.setNsLevel(this.r);
        this.l.connect(this.n.getSinkPin());
        this.n.getSrcPin().connect(this.o.getSinkPin());
        this.o.getSrcPin().connect(this.m.mSinkPin);
    }

    public SinkPin<AudioBufFrame> a() {
        return this.k;
    }

    public SrcPin<AudioBufFrame> b() {
        return this.m.mSrcPin;
    }

    protected void c() {
    }

    public void d() {
        this.l.disconnect(true);
        c();
    }

    public void a(int i) {
        this.r = i;
        this.o.setNsLevel(this.r);
    }

    public void a(boolean z) {
        if (this.q != z) {
            this.q = z;
            this.o.enableNs(z);
        }
    }

    public boolean e() {
        return this.q;
    }
}
