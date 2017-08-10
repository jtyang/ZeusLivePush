package com.ksyun.media.player.detector;

import android.os.Bundle;
import com.ksyun.media.player.KSYNetworkDetector.KSYDetectorPacketType;

public class KSYNetworkTrackerConfig {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private int f;
    private KSYDetectorPacketType g;
    private int h;
    private int i;
    private int j;

    public KSYNetworkTrackerConfig() {
        this.a = "tracker_type";
        this.b = "tracker_pkt_type";
        this.c = "tracker_timeout";
        this.d = "tracker_max_ttl";
        this.e = "tracker_detect_count";
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.f = 1;
        this.g = KSYDetectorPacketType.KSY_DETECTOR_PACKET_TYPE_ICMP;
    }

    public int getTrackerType() {
        return this.f;
    }

    public int getTimeout() {
        return this.h;
    }

    public void setTimeout(int i) {
        this.h = i;
    }

    public int getMaxTimeToLiveCount() {
        return this.i;
    }

    public void setMaxTimeToLiveCount(int i) {
        this.i = i;
    }

    public int getDetectCount() {
        return this.j;
    }

    public void setDetectCount(int i) {
        this.j = i;
    }

    public void parse(Bundle bundle) {
        if (bundle != null) {
            int i = bundle.getInt("tracker_pkt_type", 0);
            if (i < KSYDetectorPacketType.values().length) {
                this.g = KSYDetectorPacketType.values()[i];
            }
            this.h = bundle.getInt("tracker_timeout");
            this.i = bundle.getInt("tracker_max_ttl");
            this.j = bundle.getInt("tracker_detect_count");
        }
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("tracker_type", this.f);
        bundle.putInt("tracker_pkt_type", this.g.ordinal());
        bundle.putInt("tracker_timeout", this.h);
        bundle.putInt("tracker_max_ttl", this.i);
        bundle.putInt("tracker_detect_count", this.j);
        return bundle;
    }
}
