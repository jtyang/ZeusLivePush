package com.ksyun.media.streamer.encoder;

import com.ksyun.media.streamer.kit.StreamerConstants;

public class AudioEncodeFormat {
    public static final String MIME_AAC = "audio/mp4a-latm";
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;

    @Deprecated
    public AudioEncodeFormat(String str, int i, int i2, int i3, int i4) {
        this.b = 4;
        this.a = StreamerConstants.CODEC_ID_AAC;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = i4;
    }

    public AudioEncodeFormat(int i, int i2, int i3, int i4, int i5) {
        this.b = 4;
        this.a = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
    }

    public String getMime() {
        return MIME_AAC;
    }

    public int getCodecId() {
        return this.a;
    }

    public int getProfile() {
        return this.b;
    }

    public void setProfile(int i) {
        this.b = i;
    }

    public void setSampleFmt(int i) {
        this.c = i;
    }

    public int getSampleFmt() {
        return this.c;
    }

    public void setSampleRate(int i) {
        this.d = i;
    }

    public int getSampleRate() {
        return this.d;
    }

    public void setChannels(int i) {
        this.e = i;
    }

    public int getChannels() {
        return this.e;
    }

    public int getBitrate() {
        return this.f;
    }

    public void setBitrate(int i) {
        this.f = i;
    }
}
