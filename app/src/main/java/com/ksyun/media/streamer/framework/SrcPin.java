package com.ksyun.media.streamer.framework;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class SrcPin<T> {
    protected List<SinkPin<T>> a;
    protected Object b;

    public SrcPin() {
        this.a = new LinkedList();
    }

    public synchronized boolean isConnected() {
        return !this.a.isEmpty();
    }

    public synchronized void connect(@NonNull SinkPin<T> sinkPin) {
        if (!this.a.contains(sinkPin)) {
            this.a.add(sinkPin);
            sinkPin.onConnected();
            if (this.b != null) {
                sinkPin.onFormatChanged(this.b);
            }
        }
    }

    public synchronized void onFormatChanged(Object obj) {
        this.b = obj;
        for (SinkPin onFormatChanged : this.a) {
            onFormatChanged.onFormatChanged(obj);
        }
    }

    public synchronized void onFrameAvailable(T t) {
        for (SinkPin onFrameAvailable : this.a) {
            onFrameAvailable.onFrameAvailable(t);
        }
        if (t != null) {
            if ((((AVFrameBase) t).flags & 4) != 0) {
                this.b = null;
            }
        }
    }

    public synchronized void disconnect(boolean z) {
        disconnect(null, z);
    }

    public synchronized void disconnect(@Nullable SinkPin<T> sinkPin, boolean z) {
        if (sinkPin != null) {
            sinkPin.onDisconnect(z);
            this.a.remove(sinkPin);
        } else {
            for (SinkPin onDisconnect : this.a) {
                onDisconnect.onDisconnect(z);
            }
            this.a.clear();
        }
    }
}
