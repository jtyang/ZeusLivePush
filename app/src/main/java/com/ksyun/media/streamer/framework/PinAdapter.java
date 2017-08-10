package com.ksyun.media.streamer.framework;

public class PinAdapter<T> {
    public SinkPin<T> mSinkPin;
    public SrcPin<T> mSrcPin;

    public PinAdapter() {
        this.mSrcPin = a();
        this.mSinkPin = new SinkPin<T>() {


            public void onFormatChanged(Object obj) {
                mSrcPin.onFormatChanged(obj);
            }

            public void onFrameAvailable(T t) {
                mSrcPin.onFrameAvailable(t);
            }

            public void onDisconnect(boolean z) {
                super.onDisconnect(z);
                onDisconnect(z);
                if (z) {
                    mSrcPin.disconnect(true);
                }
            }
        };
    }

    protected SrcPin<T> a() {
        return new SrcPin();
    }

    public void onDisconnect(boolean z) {
    }
}
