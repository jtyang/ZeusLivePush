package com.ksyun.media.streamer.publisher;

public class RTPPublisher extends Publisher {
    private static String a;

    static {
        a = "RTPPublisher";
    }

    public RTPPublisher() {
        super(a);
    }

    protected boolean IsAddExtraForVideoKeyFrame() {
        return true;
    }

    public void release() {
        stop();
        super.release();
    }
}
