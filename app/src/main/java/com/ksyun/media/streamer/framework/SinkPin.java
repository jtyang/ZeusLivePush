package com.ksyun.media.streamer.framework;

public abstract class SinkPin<T> {
    private boolean a;

    public abstract void onFormatChanged(Object obj);

    public abstract void onFrameAvailable(T t);

    public SinkPin() {
        this.a = false;
    }

    public synchronized void onConnected() {
        this.a = true;
    }

    public boolean isConnected() {
        return this.a;
    }

    public synchronized void onDisconnect(boolean z) {
        this.a = false;
    }
}
