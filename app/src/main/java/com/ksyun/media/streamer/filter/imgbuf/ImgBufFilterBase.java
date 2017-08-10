package com.ksyun.media.streamer.filter.imgbuf;

import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import java.util.LinkedList;
import java.util.List;

public abstract class ImgBufFilterBase {
    protected static final int DEFAULT_SINKPIN_NUM = 1;
    protected static final int DEFAULT_SRCPIN_NUM = 1;
    private static final String a = "ImgBufFilterBase";
    private final List<SinkPin<ImgBufFrame>> b;
    private final SrcPin<ImgBufFrame> c;
    private boolean d;
    private ImgBufFormat e;
    protected ImgPreProcessWrap mImagePreProcess;
    protected ImgBufFrame[] mInputFrames;
    protected int mMainSinkPinIndex;
    protected ImgBufFrame mOutPutFrame;

    private class a extends SinkPin<ImgBufFrame> {
        final /* synthetic */ ImgBufFilterBase a;
        private int b;

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((ImgBufFrame) obj);
        }

        public a(ImgBufFilterBase imgBufFilterBase, int i) {
            this.a = imgBufFilterBase;
            this.b = i;
        }

        public void onFormatChanged(Object obj) {
            this.a.onFormatChanged(this.b, (ImgBufFormat) obj);
            if (this.b == this.a.mMainSinkPinIndex) {
                this.a.c.onFormatChanged(this.a.getSrcPinFormat());
            }
        }

        public void a(ImgBufFrame imgBufFrame) {
            this.a.mInputFrames[this.b] = imgBufFrame;
            if (this.b == this.a.mMainSinkPinIndex) {
                this.a.doFilter();
                ImgBufFormat imgBufFormat = this.a.mOutPutFrame.format;
                if (this.a.e == null || !imgBufFormat.equals(this.a.e)) {
                    this.a.e = imgBufFormat;
                    this.a.c.onFormatChanged(imgBufFormat);
                }
                this.a.c.onFrameAvailable(this.a.mOutPutFrame);
            }
        }

        public void onDisconnect(boolean z) {
            if (this.b == this.a.mMainSinkPinIndex && z) {
                this.a.release();
            }
        }
    }

    protected abstract void doFilter();

    public abstract int getSinkPinNum();

    protected abstract ImgBufFormat getSrcPinFormat();

    public ImgBufFilterBase(ImgPreProcessWrap imgPreProcessWrap) {
        int i = 0;
        this.mMainSinkPinIndex = 0;
        this.d = false;
        this.b = new LinkedList();
        this.c = new SrcPin();
        while (i < getSinkPinNum()) {
            this.b.add(new a(this, i));
            i += DEFAULT_SRCPIN_NUM;
        }
        this.mInputFrames = new ImgBufFrame[getSinkPinNum()];
        this.mImagePreProcess = imgPreProcessWrap;
    }

    public ImgBufFilterBase() {
        int i = 0;
        this.mMainSinkPinIndex = 0;
        this.d = false;
        this.b = new LinkedList();
        this.c = new SrcPin();
        while (i < getSinkPinNum()) {
            this.b.add(new a(this, i));
            i += DEFAULT_SRCPIN_NUM;
        }
        this.mInputFrames = new ImgBufFrame[getSinkPinNum()];
        this.mImagePreProcess = new ImgPreProcessWrap();
    }

    public final void setMainSinkPinIndex(int i) {
        this.mMainSinkPinIndex = i;
    }

    public final int getMainSinkPinIndex() {
        return this.mMainSinkPinIndex;
    }

    public SinkPin<ImgBufFrame> getSinkPin() {
        return getSinkPin(this.mMainSinkPinIndex);
    }

    public SinkPin<ImgBufFrame> getSinkPin(int i) {
        if (this.b.size() > i) {
            return (SinkPin) this.b.get(i);
        }
        return null;
    }

    public SrcPin<ImgBufFrame> getSrcPin() {
        return this.c;
    }

    protected void onFormatChanged(int i, ImgBufFormat imgBufFormat) {
    }

    public synchronized void release() {
        if (!this.d) {
            this.b.clear();
            this.c.disconnect(true);
            this.d = true;
        }
    }
}
