package com.ksyun.media.streamer.filter.audio;

import android.support.annotation.Nullable;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;

/* compiled from: AudioBufSrcPin */
public class c extends SrcPin<AudioBufFrame> {
    public /* synthetic */ void onFrameAvailable(Object obj) {
        a((AudioBufFrame) obj);
    }

    public synchronized void a(AudioBufFrame audioBufFrame) {
        super.onFrameAvailable(audioBufFrame);
        if ((audioBufFrame.flags & AVFrameBase.FLAG_DETACH_NATIVE_MODULE) != 0) {
            this.b = null;
        }
    }

    public synchronized void disconnect(@Nullable SinkPin<AudioBufFrame> sinkPin, boolean z) {
        if (this.b != null) {
            AudioBufFrame audioBufFrame = new AudioBufFrame((AudioBufFormat) this.b, null, 0);
            audioBufFrame.flags |= AVFrameBase.FLAG_DETACH_NATIVE_MODULE;
            if (sinkPin != null) {
                sinkPin.onFrameAvailable(audioBufFrame);
            } else {
                for (SinkPin onFrameAvailable : this.a) {
                    onFrameAvailable.onFrameAvailable(audioBufFrame);
                }
            }
        }
        super.disconnect(sinkPin, z);
    }
}
