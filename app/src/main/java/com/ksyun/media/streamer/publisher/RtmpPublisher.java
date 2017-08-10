package com.ksyun.media.streamer.publisher;

import android.util.Log;
import com.ksy.statlibrary.util.PreferenceUtil;
import com.ksyun.media.player.b;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.logstats.c;
import com.ksyun.media.streamer.util.TimeDeltaUtil;

public class RtmpPublisher extends Publisher {
    public static final int BW_EST_STRATEGY_NEGATIVE = 1;
    public static final int BW_EST_STRATEGY_NORMAL = 0;
    public static final int ERROR_AV_ASYNC_ERROR = -2004;
    public static final int ERROR_CONNECT_BREAKED = -1020;
    public static final int ERROR_CONNECT_FAILED = -1011;
    public static final int ERROR_DNS_PARSE_FAILED = -1010;
    public static final int ERROR_PUBLISH_FAILED = -1012;
    public static final int INFO_CONNECTED = 1;
    public static final int INFO_EST_BW_DROP = 102;
    public static final int INFO_EST_BW_RAISE = 101;
    public static final int INFO_PACKET_SEND_SLOW = 100;
    private static final String a = "RtmpPublisher";
    private static final boolean b = false;
    private BwEstConfig c;
    private String d;
    private String e;
    private int f;
    private int g;
    private int h;
    private int i;
    private long j;
    private int k;

    /* renamed from: com.ksyun.media.streamer.publisher.RtmpPublisher.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ long b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;
        final /* synthetic */ RtmpPublisher f;

        AnonymousClass1(RtmpPublisher rtmpPublisher, int i, long j, int i2, int i3, int i4) {
            this.f = rtmpPublisher;
            this.a = i;
            this.b = j;
            this.c = i2;
            this.d = i3;
            this.e = i4;
        }

        public void run() {
            if (this.f.mIsPublishing) {
                switch (this.a) {
                    case RtmpPublisher.INFO_CONNECTED /*1*/:
                        StatsLogReport.getInstance().setCurrentBitrate((float) this.f.i);
                        StatsLogReport.getInstance().startStreamSuccess();
                        break;
                    case RtmpPublisher.INFO_PACKET_SEND_SLOW /*100*/:
                        StatsLogReport.getInstance().frameDataSendSlow();
                        break;
                    case RtmpPublisher.INFO_EST_BW_RAISE /*101*/:
                        StatsLogReport.getInstance().estBitrateRaise(this.f.i, this.b);
                        break;
                    case RtmpPublisher.INFO_EST_BW_DROP /*102*/:
                        StatsLogReport.getInstance().estBitrateDrop(this.f.i, this.b);
                        break;
                    case PublisherWrapper.l /*103*/:
                        StatsLogReport.getInstance().setAudioFrameNum(this.c);
                        StatsLogReport.getInstance().setVideoFrameNum(this.d);
                        StatsLogReport.getInstance().setAudioSendDelay(this.e);
                        StatsLogReport.getInstance().setAudioPts(this.b);
                        break;
                }
                if (this.f.getPubListener() != null) {
                    this.f.getPubListener().onInfo(this.a, this.b);
                }
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.publisher.RtmpPublisher.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ long b;
        final /* synthetic */ RtmpPublisher c;

        AnonymousClass2(RtmpPublisher rtmpPublisher, int i, long j) {
            this.c = rtmpPublisher;
            this.a = i;
            this.b = j;
        }

        public void run() {
            StatsLogReport.getInstance().reportError(this.a, 5);
            if (this.c.getPubListener() != null) {
                this.c.getPubListener().onError(this.a, this.b);
            }
        }
    }

    public static class BwEstConfig {
        public int initAudioBitrate;
        public int initVideoBitrate;
        public boolean isAdjustBitrate;
        public int maxVideoBitrate;
        public int minVideoBitrate;
        public int strategy;

        public BwEstConfig() {
            this.strategy = RtmpPublisher.BW_EST_STRATEGY_NORMAL;
        }
    }

    public RtmpPublisher() {
        super("RtmpPub");
    }

    public void setBwEstConfig(BwEstConfig bwEstConfig) {
        this.mPubWrapper.a(bwEstConfig.initAudioBitrate, bwEstConfig.initVideoBitrate, bwEstConfig.minVideoBitrate, bwEstConfig.maxVideoBitrate, bwEstConfig.strategy);
        StatsLogReport.getInstance().setBwEstStrategy(bwEstConfig.strategy);
        StatsLogReport.getInstance().setAudioBitrate(bwEstConfig.initAudioBitrate);
        StatsLogReport.getInstance().setInitVideoBitrate(bwEstConfig.initVideoBitrate);
        StatsLogReport.getInstance().setMaxVideoBitrate(bwEstConfig.maxVideoBitrate);
        StatsLogReport.getInstance().setMinVideoBitrate(bwEstConfig.minVideoBitrate);
        StatsLogReport.getInstance().setAutoAdjustVideoBitrate(bwEstConfig.isAdjustBitrate);
    }

    public void setVideoBitrate(int i) {
        this.mVideoBitrate = i;
        StatsLogReport.getInstance().setMaxVideoBitrate(this.mVideoBitrate);
    }

    public void setAudioBitrate(int i) {
        this.mAudioBitrate = i;
        StatsLogReport.getInstance().setAudioBitrate(this.mAudioBitrate);
    }

    public void setFramerate(float f) {
        this.mFramerate = f;
        StatsLogReport.getInstance().setFrameRate(this.mFramerate);
    }

    public void connect(String str) {
        super.start(str);
        this.e = null;
        this.f = BW_EST_STRATEGY_NORMAL;
        this.g = BW_EST_STRATEGY_NORMAL;
        this.h = BW_EST_STRATEGY_NORMAL;
        this.i = BW_EST_STRATEGY_NORMAL;
        this.j = 0;
        this.k = BW_EST_STRATEGY_NORMAL;
        this.d = c.c(str);
        StatsLogReport.getInstance().startStream(str, this.d);
    }

    public void disconnect() {
        super.stop();
        StatsLogReport.getInstance().stopStream();
    }

    public void release() {
        this.mPubWrapper.a();
        disconnect();
        super.release();
    }

    public String getHostIp() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.e != null ? this.e : StatsConstant.SERVER_IP_DEFAULT_VALUE;
        } else {
            Log.w(a, "you must enableStreamStatModule");
            return b.d;
        }
    }

    public int getDnsParseTime() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.f;
        }
        Log.w(a, "you must enableStreamStatModule");
        return BW_EST_STRATEGY_NORMAL;
    }

    public int getConnectTime() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.g;
        }
        Log.w(a, "you must enableStreamStatModule");
        return BW_EST_STRATEGY_NORMAL;
    }

    public int getUploadedKBytes() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.h;
        }
        Log.w(a, "you must enableStreamStatModule");
        return BW_EST_STRATEGY_NORMAL;
    }

    public int getDroppedVideoFrames() {
        return this.mVFrameDroppedInner + this.mVFrameDroppedUpper;
    }

    public int getCurrentUploadKBitrate() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.i;
        }
        Log.w(a, "you must enableStreamStatModule");
        return BW_EST_STRATEGY_NORMAL;
    }

    private int a(String str) {
        int doStart = super.doStart(str);
        if (doStart == 0) {
            this.mPubWrapper.a(StatsConstant.STREAM_ID, this.d);
            this.mPubWrapper.a("manufacturer", "KSY-a-v4.3.1.1");
            this.mPubWrapper.a(PreferenceUtil.INTERVAL, Long.toString(StatsLogReport.getInstance().getLogInterval()));
            long timeDelta = TimeDeltaUtil.getInstance().getTimeDelta();
            if (timeDelta != Long.MAX_VALUE) {
                this.mPubWrapper.a("utcstarttime", Long.toString(timeDelta + System.currentTimeMillis()));
            }
        }
        return doStart;
    }

    protected void postInfo(int i, long j) {
        if (this.mIsPublishing) {
            this.mMainHandler.post(new AnonymousClass1(this, i, j, this.mPubWrapper.a(6), this.mPubWrapper.a(7), this.mPubWrapper.a(8)));
        }
    }

    protected void postError(int i, long j) {
        if (this.mIsPublishing) {
            this.mMainHandler.post(new AnonymousClass2(this, i, j));
        }
    }

    protected int doStart(String str) {
        int a = a(str);
        if (a == 0) {
            this.e = this.mPubWrapper.c(INFO_CONNECTED);
            StatsLogReport.getInstance().setRtmpHostIp(this.e);
            this.f = (int) this.mPubWrapper.b(2);
            StatsLogReport.getInstance().setDnsParseTime(this.f);
            this.g = (int) this.mPubWrapper.b(3);
            StatsLogReport.getInstance().setConnectTime(this.g);
        } else {
            this.e = this.mPubWrapper.c(INFO_CONNECTED);
            StatsLogReport.getInstance().setRtmpHostIp(this.e);
        }
        return a;
    }

    protected int doWriteFrame(Object obj) {
        int doWriteFrame = super.doWriteFrame(obj);
        if (doWriteFrame < 0) {
            postError(doWriteFrame);
        } else {
            this.mVFrameDroppedInner = (int) this.mPubWrapper.b(5);
            StatsLogReport.getInstance().setRtmpDroppedFrameCount(this.mVFrameDroppedInner + this.mVFrameDroppedUpper);
            this.h = (int) this.mPubWrapper.b(4);
            StatsLogReport.getInstance().setUploadedKBytes(this.h);
            long currentTimeMillis = System.currentTimeMillis();
            if (this.j == 0) {
                this.j = currentTimeMillis;
            }
            int i = (int) (currentTimeMillis - this.j);
            if (i >= StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD) {
                this.i = ((this.h - this.k) * 8000) / i;
                this.k = this.h;
                this.j = currentTimeMillis;
            }
        }
        return doWriteFrame;
    }

    protected boolean IsAddExtraForVideoKeyFrame() {
        return true;
    }
}
