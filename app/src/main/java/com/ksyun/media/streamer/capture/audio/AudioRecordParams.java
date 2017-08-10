package com.ksyun.media.streamer.capture.audio;

import android.media.AudioRecord;
import com.ksyun.media.streamer.kit.StreamerConstants;

public class AudioRecordParams {
    public static int AUDIO_CHANNEL_MONO;
    public static int AUDIO_CHANNEL_STEREO;
    public static int AUDIO_FORMAT_DEFAULT;
    public static int AUDIO_RATE_DEFAULT;
    public static int AUDIO_SOURCE_DEFAULT;
    public int bufferSize;
    public int channel;
    public int format;
    public int rate;
    public int source;

    static {
        AUDIO_FORMAT_DEFAULT = 2;
        AUDIO_SOURCE_DEFAULT = 1;
        AUDIO_RATE_DEFAULT = StreamerConstants.DEFAULT_AUDIO_SAMPLE_RATE;
        AUDIO_CHANNEL_MONO = 16;
        AUDIO_CHANNEL_STEREO = 12;
    }

    public AudioRecordParams() {
        this.format = AUDIO_FORMAT_DEFAULT;
        this.source = AUDIO_SOURCE_DEFAULT;
        this.channel = AUDIO_CHANNEL_MONO;
        this.rate = AUDIO_RATE_DEFAULT;
        getAudioBufferSize();
    }

    public AudioRecordParams(int i, int i2, int i3, int i4) {
        this.format = AUDIO_FORMAT_DEFAULT;
        this.source = AUDIO_SOURCE_DEFAULT;
        this.channel = AUDIO_CHANNEL_MONO;
        this.rate = AUDIO_RATE_DEFAULT;
        this.format = i;
        this.source = i2;
        this.channel = i3;
        this.rate = i4;
        getAudioBufferSize();
    }

    public AudioRecordParams(int i) {
        this.format = AUDIO_FORMAT_DEFAULT;
        this.source = AUDIO_SOURCE_DEFAULT;
        this.channel = AUDIO_CHANNEL_MONO;
        this.rate = AUDIO_RATE_DEFAULT;
        this.rate = i;
        getAudioBufferSize();
    }

    public int getFormat() {
        return this.format;
    }

    public void setFormat(int i) {
        this.format = i;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int i) {
        this.source = i;
    }

    public int getChannel() {
        return this.channel;
    }

    public void setChannel(int i) {
        this.channel = i;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int i) {
        this.rate = i;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int i) {
        this.bufferSize = i;
    }

    private void getAudioBufferSize() {
        this.bufferSize = AudioRecord.getMinBufferSize(this.rate, this.channel, this.format);
    }
}
