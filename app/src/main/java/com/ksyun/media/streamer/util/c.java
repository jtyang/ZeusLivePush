package com.ksyun.media.streamer.util;

import com.ksyun.media.streamer.logstats.StatsConstant;

/* compiled from: FpsLimiter */
public class c {
    private static final String a = "FpsLimiter";
    private static final int b = 15;
    private float c;
    private long d;
    private int e;

    public void a(float f, long j) {
        this.c = f;
        this.d = j;
        this.e = 0;
    }

    public boolean a(long j) {
        long j2 = (long) (((float) (this.e * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD)) / this.c);
        if (((float) (j - this.d)) > ((float) j2) + (1000.0f / this.c)) {
            this.d = j;
            this.e = 0;
            j2 = 0;
        }
        if (j - this.d < j2 - 15) {
            return true;
        }
        this.e++;
        return false;
    }
}
