package com.ksyun.media.streamer.filter.imgbuf;

import android.graphics.RectF;
import com.ksyun.media.streamer.filter.imgbuf.ImgPreProcessWrap.ImgBufMixerConfig;
import com.ksyun.media.streamer.framework.ImgBufFormat;

public class ImgBufMixer extends ImgBufFilterBase {
    private static final int a = 8;
    private RectF[] b;
    private ImgBufMixerConfig[] c;
    private boolean d;
    private int e;
    private int f;

    public ImgBufMixer(ImgPreProcessWrap imgPreProcessWrap) {
        super(imgPreProcessWrap);
        this.b = new RectF[getSinkPinNum()];
        this.c = new ImgBufMixerConfig[getSinkPinNum()];
    }

    public void setTargetSize(int i, int i2) {
        this.e = i;
        this.f = i2;
        for (int i3 = 0; i3 < this.b.length; i3++) {
            RectF rectF = this.b[i3];
            ImgBufMixerConfig imgBufMixerConfig = this.c[i3];
            if (!(rectF == null || imgBufMixerConfig == null)) {
                if (imgBufMixerConfig.x == 0) {
                    imgBufMixerConfig.x = (int) (rectF.left * ((float) this.e));
                }
                if (imgBufMixerConfig.y == 0) {
                    imgBufMixerConfig.y = (int) (rectF.top * ((float) this.f));
                }
                if (imgBufMixerConfig.w == 0) {
                    imgBufMixerConfig.w = (((int) (rectF.width() * ((float) this.e))) / 2) * 2;
                }
                if (imgBufMixerConfig.h == 0) {
                    imgBufMixerConfig.h = (((int) (rectF.height() * ((float) this.f))) / 2) * 2;
                }
            }
        }
    }

    public void setRenderRect(int i, RectF rectF, float f) {
        if (i < getSinkPinNum()) {
            this.b[i] = rectF;
            this.c[i] = new ImgBufMixerConfig((int) (rectF.left * ((float) this.e)), (int) (rectF.top * ((float) this.f)), (((int) (rectF.width() * ((float) this.e))) / 2) * 2, (((int) (rectF.height() * ((float) this.f))) / 2) * 2, (int) (255.0f * f));
        }
        if (i > 0) {
            this.d = true;
        }
    }

    public void setRenderRect(int i, float f, float f2, float f3, float f4, float f5) {
        setRenderRect(i, new RectF(f, f2, f + f3, f2 + f4), f5);
    }

    public int getSinkPinNum() {
        return a;
    }

    public void release() {
        super.release();
    }

    protected ImgBufFormat getSrcPinFormat() {
        return new ImgBufFormat(3, this.e, this.f, 0);
    }

    protected void onFormatChanged(int i, ImgBufFormat imgBufFormat) {
        if (this.c[i] != null) {
            this.c[i].w = imgBufFormat.width;
            this.c[i].h = imgBufFormat.height;
        }
    }

    protected void doFilter() {
        if (this.d && a()) {
            this.mOutPutFrame = this.mImagePreProcess.a(this.mInputFrames, this.c);
        } else {
            this.mOutPutFrame = this.mInputFrames[this.mMainSinkPinIndex];
        }
    }

    private boolean a() {
        for (int i = 1; i < this.mInputFrames.length; i++) {
            if (this.mInputFrames[i] != null) {
                return true;
            }
        }
        return false;
    }
}
