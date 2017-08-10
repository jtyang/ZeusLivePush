package com.ksyun.media.streamer.publisher;

import android.support.annotation.NonNull;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import java.util.LinkedList;
import java.util.List;

public class PublisherMgt {
    private static final String a = "PublisherMgt";
    private List<Publisher> b;
    private PinAdapter<AudioBufFrame> c;
    private PinAdapter<ImgBufFrame> d;

    public PublisherMgt() {
        this.c = new PinAdapter();
        this.d = new PinAdapter();
        this.b = new LinkedList();
    }

    public SinkPin<AudioBufFrame> getAudioSink() {
        return this.c.mSinkPin;
    }

    public SinkPin<ImgBufFrame> getVideoSink() {
        return this.d.mSinkPin;
    }

    public void setVideoOnly(boolean z) {
        for (Publisher videoOnly : this.b) {
            videoOnly.setVideoOnly(z);
        }
    }

    public void setAudioOnly(boolean z) {
        for (Publisher audioOnly : this.b) {
            audioOnly.setAudioOnly(z);
        }
        if (z) {
            StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_AUDIOONLY);
        }
    }

    public void addPublisher(@NonNull Publisher publisher) {
        if (!this.b.contains(publisher)) {
            this.b.add(publisher);
            this.c.mSrcPin.connect(publisher.getAudioSink());
            this.d.mSrcPin.connect(publisher.getVideoSink());
        }
    }

    public void removePublisher(Publisher publisher) {
        this.b.remove(publisher);
        this.c.mSrcPin.disconnect(publisher.getAudioSink(), false);
        this.d.mSrcPin.disconnect(publisher.getVideoSink(), false);
    }
}
