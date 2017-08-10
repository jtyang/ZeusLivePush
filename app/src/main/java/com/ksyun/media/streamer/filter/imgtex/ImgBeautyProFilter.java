package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase.OnErrorListener;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.gles.GLRender;

public class ImgBeautyProFilter extends ImgFilterBase {
    private static final String a = "ImgBeautyProFilter";
    private ImgFilterBase b;

    public ImgBeautyProFilter(GLRender gLRender, Context context) {
        this(gLRender, context, 1);
    }

    public ImgBeautyProFilter(GLRender gLRender, Context context, int i) {
        if (i == 1) {
            this.b = new ImgBeautySimpleFilter(gLRender, context);
            setGrindRatio(0.5f);
            ((ImgBeautySimpleFilter) this.b).setScaleLumance(1.0f);
        } else if (i == 2) {
            this.b = new ImgBeautyAdvanceFilter(gLRender, context);
            setGrindRatio(0.5f);
        } else if (i == 3) {
            this.b = new ImgBeautySimpleFilter(gLRender, context);
            setGrindRatio(0.2f);
            ((ImgBeautySimpleFilter) this.b).setScaleLumance(0.7f);
        } else if (i == 4) {
            this.b = new ImgBeautyAdvanceFilter(gLRender, context);
            setGrindRatio(0.3f);
        } else {
            throw new IllegalArgumentException("only type 1-4 supported!");
        }
        setWhitenRatio(0.3f);
        setRuddyRatio(-0.3f);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        super.setOnErrorListener(onErrorListener);
        this.b.setOnErrorListener(onErrorListener);
    }

    public boolean isGrindRatioSupported() {
        return this.b.isGrindRatioSupported();
    }

    public boolean isWhitenRatioSupported() {
        return this.b.isWhitenRatioSupported();
    }

    public boolean isRuddyRatioSupported() {
        return this.b.isRuddyRatioSupported();
    }

    public void setGrindRatio(float f) {
        super.setGrindRatio(f);
        this.b.setGrindRatio(f);
    }

    public void setWhitenRatio(float f) {
        super.setWhitenRatio(f);
        this.b.setWhitenRatio(f);
    }

    public void setRuddyRatio(float f) {
        super.setRuddyRatio(f);
        this.b.setRuddyRatio(f);
    }

    public int getSinkPinNum() {
        return 1;
    }

    public SinkPin<ImgTexFrame> getSinkPin(int i) {
        return this.b.getSinkPin();
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.b.getSrcPin();
    }

    public String getVersion() {
        return "2.4";
    }

    public void setGLRender(GLRender gLRender) {
        if (this.b instanceof ImgBeautySimpleFilter) {
            ((ImgBeautySimpleFilter) this.b).setGLRender(gLRender);
        } else if (this.b instanceof ImgBeautyAdvanceFilter) {
            ((ImgBeautyAdvanceFilter) this.b).setGLRender(gLRender);
        }
    }
}
