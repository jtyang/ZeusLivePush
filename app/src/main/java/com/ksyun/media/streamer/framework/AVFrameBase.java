package com.ksyun.media.streamer.framework;

public class AVFrameBase {
    public static final int FLAG_CODEC_CONFIG = 2;
    public static final int FLAG_DETACH_NATIVE_MODULE = 65536;
    public static final int FLAG_DUMMY_VIDEO_FRAME = 131072;
    public static final int FLAG_END_OF_STREAM = 4;
    public static final int FLAG_KEY_FRAME = 1;
    public long dts;
    public int flags;
    public long pts;
}
