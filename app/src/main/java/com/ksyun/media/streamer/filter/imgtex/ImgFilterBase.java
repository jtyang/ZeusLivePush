package com.ksyun.media.streamer.filter.imgtex;

import com.ksyun.media.player.b;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;

public abstract class ImgFilterBase {
    protected OnErrorListener mErrorListener;
    protected float mGrindRatio;
    protected int mMainSinkPinIndex;
    protected float mRuddyRatio;
    protected float mWhitenRatio;

    public interface OnErrorListener {
        void onError(ImgTexFilterBase imgTexFilterBase, int i);
    }

    public abstract SinkPin<ImgTexFrame> getSinkPin(int i);

    public abstract int getSinkPinNum();

    public abstract SrcPin<ImgTexFrame> getSrcPin();

    public ImgFilterBase() {
        this.mGrindRatio = 0.5f;
        this.mWhitenRatio = 0.5f;
        this.mRuddyRatio = 0.5f;
    }

    public SinkPin<ImgTexFrame> getSinkPin() {
        return getSinkPin(this.mMainSinkPinIndex);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mErrorListener = onErrorListener;
    }

    public final void setMainSinkPinIndex(int i) {
        this.mMainSinkPinIndex = i;
    }

    public boolean isGrindRatioSupported() {
        return false;
    }

    public boolean isWhitenRatioSupported() {
        return false;
    }

    public boolean isRuddyRatioSupported() {
        return false;
    }

    public void setGrindRatio(float f) {
        this.mGrindRatio = f;
    }

    public float getGrindRatio() {
        return this.mGrindRatio;
    }

    public void setWhitenRatio(float f) {
        this.mWhitenRatio = f;
    }

    public float getWhitenRatio() {
        return this.mWhitenRatio;
    }

    public void setRuddyRatio(float f) {
        this.mRuddyRatio = f;
    }

    public float getRuddyRatio() {
        return this.mRuddyRatio;
    }

    public String getVersion() {
        return b.f;
    }
}
