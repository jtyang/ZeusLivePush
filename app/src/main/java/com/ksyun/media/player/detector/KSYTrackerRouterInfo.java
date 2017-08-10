package com.ksyun.media.player.detector;

import android.os.Bundle;
import java.util.ArrayList;

public class KSYTrackerRouterInfo {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;
    private final String h;
    private ArrayList<String> i;
    private float j;
    private float k;
    private float l;
    private float m;
    private float n;
    private int o;
    private int p;

    public KSYTrackerRouterInfo() {
        this.a = "router_ip";
        this.b = "router_max_delay";
        this.c = "router_min_delay";
        this.d = "router_avg_delay";
        this.e = "router_avg_dev";
        this.f = "router_pkt_loss";
        this.g = "router_bandwidth";
        this.h = "router_pkt_number";
    }

    public ArrayList<String> getRouterIP() {
        return this.i;
    }

    public float getMaxDelay() {
        return this.j;
    }

    public float getMinDelay() {
        return this.k;
    }

    public float getAverageDelay() {
        return this.l;
    }

    public float getDelayAverageDeviation() {
        return this.m;
    }

    public float getPacketLossRate() {
        return this.n;
    }

    public int parse(Bundle bundle) {
        if (bundle == null) {
            return -1;
        }
        this.i = bundle.getStringArrayList("router_ip");
        this.j = bundle.getFloat("router_max_delay");
        this.k = bundle.getFloat("router_min_delay");
        this.l = bundle.getFloat("router_avg_delay");
        this.m = bundle.getFloat("router_avg_dev");
        this.n = bundle.getFloat("router_pkt_loss");
        this.o = bundle.getInt("router_bandwidth");
        this.p = bundle.getInt("router_pkt_number");
        return 0;
    }
}
