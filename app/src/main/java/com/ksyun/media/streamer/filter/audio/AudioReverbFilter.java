package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import java.nio.ByteBuffer;

public class AudioReverbFilter extends AudioFilterBase {
    public static int AUDIO_REVERB_LEVEL_1;
    public static int AUDIO_REVERB_LEVEL_2;
    public static int AUDIO_REVERB_LEVEL_3;
    public static int AUDIO_REVERB_LEVEL_4;
    public static int AUDIO_REVERB_LEVEL_5;
    private int a;
    private boolean b;
    private AudioReverbWrap c;

    static {
        AUDIO_REVERB_LEVEL_1 = 1;
        AUDIO_REVERB_LEVEL_2 = 2;
        AUDIO_REVERB_LEVEL_3 = 3;
        AUDIO_REVERB_LEVEL_4 = 4;
        AUDIO_REVERB_LEVEL_5 = 5;
    }

    public AudioReverbFilter() {
        this.a = AUDIO_REVERB_LEVEL_3;
        this.b = false;
        this.c = new AudioReverbWrap();
    }

    public void setReverbLevel(int i) {
        if (i != this.a) {
            this.a = i;
            this.c.a(this.a);
            if (this.b) {
                StatsLogReport.getInstance().updateAudioFilterType(getClass().getSimpleName(), String.valueOf(this.a));
            }
        }
    }

    public void setTakeEffect(boolean z) {
        this.b = true;
    }

    public int getReverbType() {
        return this.a;
    }

    protected long getNativeInstance() {
        return this.c.a();
    }

    protected void attachTo(int i, long j, boolean z) {
        this.c.a(i, j, z);
    }

    protected int readNative(ByteBuffer byteBuffer, int i) {
        return this.c.a(byteBuffer, i);
    }

    protected AudioBufFormat doFormatChanged(AudioBufFormat audioBufFormat) {
        this.c.a(audioBufFormat.sampleRate, audioBufFormat.channels);
        this.c.a(this.a);
        return audioBufFormat;
    }

    protected AudioBufFrame doFilter(AudioBufFrame audioBufFrame) {
        this.c.a(audioBufFrame.buf);
        return audioBufFrame;
    }

    protected void doRelease() {
        this.b = false;
        if (this.c != null) {
            this.c.b();
            this.c = null;
        }
    }
}
