package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import android.util.Log;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase.OnErrorListener;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.gles.GLRender;

public class ImgBeautyAdvanceFilter extends ImgFilterBase {
    private static final String a = "ImgBeautyAdvanceFilter";
    private ImgBeautyGrindAdvanceFilter b;
    private ImgBeautySpecialEffectsFilter c;
    private ImgBeautyAdjustSkinColorFilter d;

    public ImgBeautyAdvanceFilter(GLRender gLRender, Context context) {
        this.b = new ImgBeautyGrindAdvanceFilter(gLRender);
        try {
            this.c = new ImgBeautySpecialEffectsFilter(gLRender, context, "13_nature.png");
        } catch (Exception e) {
            Log.e(a, "KSYResource missing!");
        }
        if (this.c != null) {
            this.b.getSrcPin().connect(this.c.getSinkPin());
        }
        try {
            this.d = new ImgBeautyAdjustSkinColorFilter(gLRender, context, "assets://KSYResource/0_pink.png", "assets://KSYResource/0_ruddy2.png");
        } catch (Exception e2) {
            Log.e(a, "KSYResource missing!");
        }
        if (this.d != null) {
            if (this.c != null) {
                this.c.getSrcPin().connect(this.d.getSinkPin());
            } else {
                this.b.getSrcPin().connect(this.d.getSinkPin());
            }
        }
        setGrindRatio(0.3f);
        setRuddyRatio(0.0f);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        super.setOnErrorListener(onErrorListener);
        this.b.setOnErrorListener(this.mErrorListener);
        if (this.c != null) {
            this.c.setOnErrorListener(this.mErrorListener);
        }
        if (this.d != null) {
            this.d.setOnErrorListener(this.mErrorListener);
        }
    }

    public boolean isGrindRatioSupported() {
        return true;
    }

    public boolean isWhitenRatioSupported() {
        return this.c != null;
    }

    public boolean isRuddyRatioSupported() {
        return this.d != null;
    }

    public void setGrindRatio(float f) {
        super.setGrindRatio(f);
        this.b.setGrindRatio(f);
    }

    public void setWhitenRatio(float f) {
        super.setWhitenRatio(f);
        if (this.c != null) {
            this.c.setIntensity(f);
        }
    }

    public void setRuddyRatio(float f) {
        super.setRuddyRatio(f);
        if (this.d != null) {
            this.d.setRuddyRatio(f);
        }
    }

    public int getSinkPinNum() {
        return 1;
    }

    public SinkPin<ImgTexFrame> getSinkPin(int i) {
        return this.b.getSinkPin();
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        if (this.d != null) {
            return this.d.getSrcPin();
        }
        if (this.c != null) {
            return this.c.getSrcPin();
        }
        return this.b.getSrcPin();
    }

    public void setGLRender(GLRender gLRender) {
        this.b.setGLRender(gLRender);
        if (this.d != null) {
            this.d.setGLRender(gLRender);
        }
        if (this.c != null) {
            this.c.setGLRender(gLRender);
        }
    }
}
