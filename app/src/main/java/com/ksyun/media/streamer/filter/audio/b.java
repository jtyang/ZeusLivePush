package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SrcPin;

/* compiled from: AudioBufPinAdapter */
public class b extends PinAdapter<AudioBufFrame> {
    protected SrcPin<AudioBufFrame> a() {
        return new c();
    }
}
