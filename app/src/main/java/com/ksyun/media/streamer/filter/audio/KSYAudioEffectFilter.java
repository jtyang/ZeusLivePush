package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import java.nio.ByteBuffer;

public class KSYAudioEffectFilter extends AudioFilterBase {
    public static int AUDIO_EFFECT_TYPE_FEMALE = 0;
    public static int AUDIO_EFFECT_TYPE_HEROIC = 0;
    public static int AUDIO_EFFECT_TYPE_MALE = 0;
    public static int AUDIO_EFFECT_TYPE_PITCH = 0;
    public static int AUDIO_EFFECT_TYPE_ROBOT = 0;
    public static int AUDIO_PITCH_LEVEL_1 = 0;
    public static int AUDIO_PITCH_LEVEL_2 = 0;
    public static int AUDIO_PITCH_LEVEL_3 = 0;
    public static int AUDIO_PITCH_LEVEL_4 = 0;
    public static int AUDIO_PITCH_LEVEL_5 = 0;
    public static int AUDIO_PITCH_LEVEL_6 = 0;
    public static int AUDIO_PITCH_LEVEL_7 = 0;
    private static final String a = "KSYAudioEffectFilter";
    private static final boolean b = false;
    private KSYAudioEffectWrapper c;
    private AudioBufFormat d;
    private int e;
    private int f;

    static {
        AUDIO_PITCH_LEVEL_1 = -3;
        AUDIO_PITCH_LEVEL_2 = -2;
        AUDIO_PITCH_LEVEL_3 = -1;
        AUDIO_PITCH_LEVEL_4 = 0;
        AUDIO_PITCH_LEVEL_5 = 1;
        AUDIO_PITCH_LEVEL_6 = 2;
        AUDIO_PITCH_LEVEL_7 = 3;
        AUDIO_EFFECT_TYPE_PITCH = 9;
        AUDIO_EFFECT_TYPE_FEMALE = 10;
        AUDIO_EFFECT_TYPE_MALE = 11;
        AUDIO_EFFECT_TYPE_HEROIC = 12;
        AUDIO_EFFECT_TYPE_ROBOT = 13;
    }

    public KSYAudioEffectFilter(int i) {
        this.e = AUDIO_EFFECT_TYPE_PITCH;
        this.f = AUDIO_PITCH_LEVEL_4;
        this.e = i;
        this.c = new KSYAudioEffectWrapper(i);
    }

    public void setAudioEffectType(int i) {
        this.e = i;
    }

    public int getAudioEffectType() {
        return this.e;
    }

    public void setPitchLevel(int i) {
        this.e = AUDIO_EFFECT_TYPE_PITCH;
        this.f = i;
        this.c.b(i);
    }

    public int getPitchLevel() {
        return this.f;
    }

    protected AudioBufFormat doFormatChanged(AudioBufFormat audioBufFormat) {
        this.d = audioBufFormat;
        this.c.a(audioBufFormat);
        this.c.a(this.e);
        return audioBufFormat;
    }

    protected void doRelease() {
        if (this.c != null) {
            this.c.a();
            this.c = null;
        }
    }

    protected AudioBufFrame doFilter(AudioBufFrame audioBufFrame) {
        if (this.c != null) {
            long nanoTime = (System.nanoTime() / 1000) / 1000;
            this.c.a(audioBufFrame.buf);
            nanoTime = (System.nanoTime() / 1000) / 1000;
        }
        return audioBufFrame;
    }

    protected long getNativeInstance() {
        return this.c.b();
    }

    protected void attachTo(int i, long j, boolean z) {
        this.c.a(i, j, z);
    }

    protected int readNative(ByteBuffer byteBuffer, int i) {
        return this.c.a(byteBuffer, i);
    }
}
