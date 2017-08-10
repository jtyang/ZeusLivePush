package com.ksyun.media.streamer.encoder;

import com.ksyun.media.streamer.kit.StreamerConstants;

public class VideoEncodeFormat {
    public static final int ENCODE_PROFILE_BALANCE = 2;
    public static final int ENCODE_PROFILE_DEFAULT = 0;
    public static final int ENCODE_PROFILE_HIGH_PERFORMANCE = 1;
    public static final int ENCODE_PROFILE_LOW_POWER = 3;
    public static final int ENCODE_SCENE_DEFAULT = 0;
    public static final int ENCODE_SCENE_GAME = 2;
    public static final int ENCODE_SCENE_SHOWSELF = 1;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private float f;
    private float g;
    private int h;
    private int i;
    private int j;
    private boolean k;

    public VideoEncodeFormat(int i, int i2, int i3, int i4) {
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = StreamerConstants.DEFAULT_TARGET_FPS;
        this.g = 5.0f;
        this.h = ENCODE_SCENE_SHOWSELF;
        this.i = ENCODE_PROFILE_LOW_POWER;
        this.a = ENCODE_PROFILE_LOW_POWER;
        this.j = ENCODE_SCENE_DEFAULT;
        this.k = true;
    }

    public void setPixFmt(int i) {
        this.a = i;
    }

    public int getPixFmt() {
        return this.a;
    }

    public void setCodecId(int i) {
        this.b = i;
    }

    public int getCodecId() {
        return this.b;
    }

    public void setWidth(int i) {
        this.c = i;
    }

    public int getWidth() {
        return this.c;
    }

    public void setHeight(int i) {
        this.d = i;
    }

    public int getHeight() {
        return this.d;
    }

    public void setBitrate(int i) {
        this.e = i;
    }

    public int getBitrate() {
        return this.e;
    }

    public void setFramerate(float f) {
        this.f = f;
    }

    public float getFramerate() {
        return this.f;
    }

    public void setIframeinterval(float f) {
        this.g = f;
    }

    public float getIframeinterval() {
        return this.g;
    }

    public int getScene() {
        return this.h;
    }

    public void setScene(int i) {
        this.h = i;
    }

    public int getProfile() {
        return this.i;
    }

    public void setProfile(int i) {
        this.i = i;
    }

    public int getCrf() {
        return this.j;
    }

    public void setCrf(int i) {
        this.j = i;
    }

    public boolean getLiveStreaming() {
        return this.k;
    }

    public void setLiveStreaming(boolean z) {
        this.k = z;
    }

    public VideoEncodeFormat clone() {
        VideoEncodeFormat videoEncodeFormat = new VideoEncodeFormat(this.b, this.c, this.d, this.e);
        videoEncodeFormat.setFramerate(this.f);
        videoEncodeFormat.setIframeinterval(this.g);
        videoEncodeFormat.setScene(this.h);
        videoEncodeFormat.setProfile(this.i);
        videoEncodeFormat.setPixFmt(this.a);
        videoEncodeFormat.setCrf(this.j);
        videoEncodeFormat.setLiveStreaming(this.k);
        return videoEncodeFormat;
    }
}
