package com.ksyun.media.streamer.framework;

public class CredtpModel {
    public static final int BEAUTY_ADJ_SKIN_COLOR_FILTER = 12;
    public static final int BEAUTY_DENOISE_FILTER = 1;
    public static final int BEAUTY_GRIND_ADVANCE_FILTER = 13;
    public static final int BEAUTY_GRIND_FACE_FILTER = 9;
    public static final int BEAUTY_GRIND_SIMPLE_FILTER = 11;
    public static final int BEAUTY_ILLUSION_FILTER = 3;
    public static final int BEAUTY_LOOK_UP_FILTER = 10;
    public static final int BEAUTY_SHARPEN_FILTER = 6;
    public static final int BEAUTY_SKIN_DETECT_FILTER = 8;
    public static final int BEAUTY_SKIN_WHITEN_FILTER = 4;
    public static final int BEAUTY_SMOOTH_FILTER = 5;
    public static final int BEAUTY_SOFT_EXT_FILTER = 7;
    public static final int BEAUTY_SOFT_FILTER = 2;
    private String body;
    private String key;
    private int type;

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}
