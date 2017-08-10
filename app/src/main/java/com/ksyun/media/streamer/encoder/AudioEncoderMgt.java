package com.ksyun.media.streamer.encoder;

import android.os.Build.VERSION;
import com.ksyun.media.streamer.encoder.Encoder.EncoderListener;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsLogReport;

public class AudioEncoderMgt {
    public static final int METHOD_HARDWARE = 2;
    public static final int METHOD_SOFTWARE = 3;
    private static final String a = "AudioEncoderMgt";
    private PinAdapter<AudioBufFrame> b;
    private PinAdapter<AudioBufFrame> c;
    private Encoder<AudioBufFrame, AudioBufFrame> d;
    private int e;
    private AudioEncodeFormat f;
    private EncoderListener g;

    public AudioEncoderMgt() {
        this.b = new PinAdapter();
        this.c = new PinAdapter();
        this.e = METHOD_SOFTWARE;
        this.d = new AVCodecAudioEncoder();
        this.b.mSrcPin.connect(this.d.mSinkPin);
        this.d.mSrcPin.connect(this.c.mSinkPin);
    }

    public SinkPin<AudioBufFrame> getSinkPin() {
        return this.b.mSinkPin;
    }

    public SrcPin<AudioBufFrame> getSrcPin() {
        return this.c.mSrcPin;
    }

    public synchronized Encoder getEncoder() {
        return this.d;
    }

    public synchronized int getEncodeMethod() {
        return this.e;
    }

    public synchronized void setEncodeMethod(int i) {
        int a = a(i);
        if (a != this.e) {
            this.d.mSrcPin.disconnect(false);
            this.b.mSrcPin.disconnect(false);
            this.d.release();
            this.e = a;
            if (a == METHOD_HARDWARE) {
                this.d = new MediaCodecAudioEncoder();
            } else {
                this.d = new AVCodecAudioEncoder();
            }
            if (this.f != null) {
                this.d.configure(this.f);
            }
            if (this.g != null) {
                this.d.setEncoderListener(this.g);
            }
            this.b.mSrcPin.connect(this.d.mSinkPin);
            this.d.mSrcPin.connect(this.c.mSinkPin);
        }
    }

    public synchronized void setEncodeFormat(AudioEncodeFormat audioEncodeFormat) {
        this.f = audioEncodeFormat;
        this.d.configure(audioEncodeFormat);
        StatsLogReport.getInstance().setAudioSampleRateInHz(this.f.getSampleRate());
        StatsLogReport.getInstance().setAudioEncodeProfile(this.f.getProfile());
        StatsLogReport.getInstance().setAudioChannels(this.f.getChannels());
    }

    public synchronized void setEncoderListener(EncoderListener encoderListener) {
        this.g = encoderListener;
        this.d.setEncoderListener(encoderListener);
    }

    private int a(int i) {
        if (i != METHOD_HARDWARE || VERSION.SDK_INT < 18) {
            return METHOD_SOFTWARE;
        }
        return METHOD_HARDWARE;
    }
}
