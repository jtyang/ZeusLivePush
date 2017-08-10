package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import android.util.Log;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase.OnErrorListener;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.gles.GLRender;

public class ImgBeautySmoothFilter extends ImgFilterBase {
    private static final String a = "ImgBeautyFaceFilter";
    private PinAdapter<ImgTexFrame> b;
    private PinAdapter<ImgTexFrame> c;
    private ImgBeautySkinDetectFilter d;
    private ImgBeautyGrindFaceFilter e;
    private ImgBeautySpecialEffectsFilter f;

    public ImgBeautySmoothFilter(GLRender gLRender) {
        a(gLRender, null);
    }

    public ImgBeautySmoothFilter(GLRender gLRender, Context context) {
        a(gLRender, context);
    }

    private void a(GLRender gLRender, Context context) {
        this.b = new PinAdapter();
        this.c = new PinAdapter();
        this.d = new ImgBeautySkinDetectFilter(gLRender);
        this.e = new ImgBeautyGrindFaceFilter(gLRender);
        try {
            this.f = new ImgBeautySpecialEffectsFilter(gLRender, context, 3);
        } catch (Exception e) {
            Log.e(a, "KSYResource missing, ruddy is unusable!");
        }
        this.b.mSrcPin.connect(this.d.getSinkPin());
        this.b.mSrcPin.connect(this.e.getSinkPin(0));
        this.d.getSrcPin().connect(this.e.getSinkPin(1));
        if (this.f != null) {
            this.e.getSrcPin().connect(this.f.getSinkPin());
            this.f.getSrcPin().connect(this.c.mSinkPin);
        } else {
            this.e.getSrcPin().connect(this.c.mSinkPin);
        }
        setGrindRatio(0.4f);
        setWhitenRatio(0.2f);
        setRuddyRatio(0.8f);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        super.setOnErrorListener(onErrorListener);
        this.d.setOnErrorListener(this.mErrorListener);
        this.e.setOnErrorListener(this.mErrorListener);
        if (this.f != null) {
            this.f.setOnErrorListener(this.mErrorListener);
        }
    }

    public boolean isGrindRatioSupported() {
        return true;
    }

    public boolean isWhitenRatioSupported() {
        return true;
    }

    public boolean isRuddyRatioSupported() {
        return this.f != null;
    }

    public void setGrindRatio(float f) {
        super.setGrindRatio(f);
        this.e.setGrindRatio(f);
    }

    public void setWhitenRatio(float f) {
        super.setWhitenRatio(f);
        this.e.setWhitenRatio(f);
    }

    public void setRuddyRatio(float f) {
        super.setRuddyRatio(f);
        if (this.f != null) {
            this.f.setIntensity(f);
        }
    }

    public int getSinkPinNum() {
        return 1;
    }

    public SinkPin<ImgTexFrame> getSinkPin(int i) {
        return this.b.mSinkPin;
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.c.mSrcPin;
    }

    public String getVersion() {
        return "1.2";
    }

    public void setGLRender(GLRender gLRender) {
        this.d.setGLRender(gLRender);
        this.e.setGLRender(gLRender);
        if (this.f != null) {
            this.f.setGLRender(gLRender);
        }
    }
}
