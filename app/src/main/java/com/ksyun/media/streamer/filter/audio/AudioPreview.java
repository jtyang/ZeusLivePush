package com.ksyun.media.streamer.filter.audio;

import android.content.Context;
import com.ksyun.media.player.f;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.audio.a;
import java.nio.ByteBuffer;

public class AudioPreview extends AudioFilterBase {
    private static final String a = "AudioPreview";
    private Context b;
    private AudioSLPlayer c;
    private boolean d;

    public AudioPreview(Context context) {
        this.b = context;
        this.c = new AudioSLPlayer();
    }

    public void start() {
        this.c.b();
        StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_AUDIOPREVIEW);
    }

    public void stop() {
        this.c.c();
    }

    public void setMute(boolean z) {
        this.c.b(z);
    }

    public void setBlockingMode(boolean z) {
        this.d = z;
    }

    protected long getNativeInstance() {
        return this.c.a();
    }

    protected int readNative(ByteBuffer byteBuffer, int i) {
        return this.c.a(byteBuffer, i);
    }

    protected void attachTo(int i, long j, boolean z) {
        this.c.a(i, j, z);
    }

    protected AudioBufFormat doFormatChanged(AudioBufFormat audioBufFormat) {
        this.c.a(audioBufFormat.sampleRate, audioBufFormat.channels, a.a(this.b, audioBufFormat.sampleRate), f.f);
        return audioBufFormat;
    }

    protected AudioBufFrame doFilter(AudioBufFrame audioBufFrame) {
        this.c.a(audioBufFrame.buf, !this.d);
        return audioBufFrame;
    }

    protected void doRelease() {
        this.c.c();
        this.c.d();
    }
}
