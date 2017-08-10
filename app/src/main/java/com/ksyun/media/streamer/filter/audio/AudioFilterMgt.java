package com.ksyun.media.streamer.filter.audio;

import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AudioFilterMgt {
    private static final String a = "AudioFilterMgt";
    private PinAdapter<AudioBufFrame> b;
    private PinAdapter<AudioBufFrame> c;
    private LinkedList<AudioFilterBase> d;
    private final Object e;

    public AudioFilterMgt() {
        this.e = new Object();
        this.b = new b();
        this.c = new b();
        this.b.mSrcPin.connect(this.c.mSinkPin);
        this.d = new LinkedList();
    }

    public SinkPin<AudioBufFrame> getSinkPin() {
        return this.b.mSinkPin;
    }

    public SrcPin<AudioBufFrame> getSrcPin() {
        return this.c.mSrcPin;
    }

    public void setFilter(AudioFilterBase audioFilterBase) {
        List list = null;
        if (audioFilterBase != null) {
            list = new LinkedList();
            list.add(audioFilterBase);
        }
        setFilter(list);
    }

    public void setFilter(AudioFilterBase[] audioFilterBaseArr) {
        List list = null;
        if (audioFilterBaseArr != null && audioFilterBaseArr.length > 0) {
            list = new LinkedList();
            Collections.addAll(list, audioFilterBaseArr);
        }
        setFilter(list);
    }

    public synchronized void setFilter(List<? extends AudioFilterBase> list) {
        synchronized (this) {
            synchronized (this.e) {
                if (!this.d.isEmpty()) {
                    ((AudioFilterBase) this.d.get(this.d.size() - 1)).getSrcPin().disconnect(false);
                    this.b.mSrcPin.disconnect(true);
                    this.d.clear();
                } else if (list != null) {
                    if (!list.isEmpty()) {
                        this.b.mSrcPin.disconnect(false);
                    }
                }
                if (list == null || list.isEmpty()) {
                    this.b.mSrcPin.connect(this.c.mSinkPin);
                } else {
                    this.b.mSrcPin.connect(((AudioFilterBase) list.get(0)).getSinkPin());
                    for (int i = 1; i < list.size(); i++) {
                        ((AudioFilterBase) list.get(i - 1)).getSrcPin().connect(((AudioFilterBase) list.get(i)).getSinkPin());
                    }
                    ((AudioFilterBase) list.get(list.size() - 1)).getSrcPin().connect(this.c.mSinkPin);
                    this.d.addAll(list);
                }
            }
            a(list);
        }
    }

    public synchronized List<AudioFilterBase> getFilter() {
        return this.d;
    }

    private void a(List<? extends AudioFilterBase> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    if (list.get(i) instanceof AudioReverbFilter) {
                        ((AudioReverbFilter) list.get(i)).setTakeEffect(true);
                        StatsLogReport.getInstance().updateAudioFilterType(r0.getClass().getSimpleName(), String.valueOf(r0.getReverbType()));
                    } else {
                        StatsLogReport.getInstance().updateAudioFilterType(((AudioFilterBase) list.get(i)).getClass().getSimpleName());
                    }
                }
            }
        }
    }
}
