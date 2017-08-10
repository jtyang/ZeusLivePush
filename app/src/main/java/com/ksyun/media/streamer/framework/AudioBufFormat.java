package com.ksyun.media.streamer.framework;

public class AudioBufFormat {
    public int channels;
    public int codecId;
    public long nativeModule;
    public int sampleFormat;
    public int sampleRate;

    public AudioBufFormat(int i, int i2, int i3) {
        this.sampleFormat = i;
        this.sampleRate = i2;
        this.channels = i3;
        this.codecId = 0;
        this.nativeModule = 0;
    }

    public AudioBufFormat(AudioBufFormat audioBufFormat) {
        this.sampleFormat = audioBufFormat.sampleFormat;
        this.sampleRate = audioBufFormat.sampleRate;
        this.channels = audioBufFormat.channels;
        this.codecId = audioBufFormat.codecId;
        this.nativeModule = audioBufFormat.nativeModule;
    }

    private AudioBufFormat() {
    }

    public boolean equals(AudioBufFormat audioBufFormat) {
        return audioBufFormat != null && this.sampleFormat == audioBufFormat.sampleFormat && this.sampleRate == audioBufFormat.sampleRate && this.channels == audioBufFormat.channels && this.codecId == audioBufFormat.codecId;
    }
}
