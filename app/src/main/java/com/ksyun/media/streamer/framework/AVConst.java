package com.ksyun.media.streamer.framework;

public class AVConst {
    public static final int AV_SAMPLE_FMT_DBL = 4;
    public static final int AV_SAMPLE_FMT_DBLP = 9;
    public static final int AV_SAMPLE_FMT_FLT = 3;
    public static final int AV_SAMPLE_FMT_FLTP = 8;
    public static final int AV_SAMPLE_FMT_S16 = 1;
    public static final int AV_SAMPLE_FMT_S16P = 6;
    public static final int AV_SAMPLE_FMT_S32 = 2;
    public static final int AV_SAMPLE_FMT_S32P = 7;
    public static final int AV_SAMPLE_FMT_U8 = 0;
    public static final int AV_SAMPLE_FMT_U8P = 5;
    public static final int CODEC_ID_AAC = 256;
    public static final int CODEC_ID_AVC = 1;
    public static final int CODEC_ID_GIF = 3;
    public static final int CODEC_ID_HEVC = 2;
    public static final int CODEC_ID_NONE = 0;
    public static final int PROFILE_AAC_HE = 4;
    public static final int PROFILE_AAC_HE_V2 = 28;
    public static final int PROFILE_AAC_LOW = 1;

    public static int getBytesPerSample(int i) {
        switch (i) {
            case CODEC_ID_NONE /*0*/:
            case AV_SAMPLE_FMT_U8P /*5*/:
                return PROFILE_AAC_LOW;
            case CODEC_ID_HEVC /*2*/:
            case CODEC_ID_GIF /*3*/:
            case AV_SAMPLE_FMT_S32P /*7*/:
            case AV_SAMPLE_FMT_FLTP /*8*/:
                return PROFILE_AAC_HE;
            case PROFILE_AAC_HE /*4*/:
            case AV_SAMPLE_FMT_DBLP /*9*/:
                return AV_SAMPLE_FMT_FLTP;
            default:
                return CODEC_ID_HEVC;
        }
    }
}
