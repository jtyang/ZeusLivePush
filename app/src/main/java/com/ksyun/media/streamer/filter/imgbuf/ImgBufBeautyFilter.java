package com.ksyun.media.streamer.filter.imgbuf;

import com.ksyun.media.streamer.framework.ImgBufFormat;

public class ImgBufBeautyFilter extends ImgBufFilterBase {
    public static final int BEAUTY_LEVEL_0 = 0;
    public static final int BEAUTY_LEVEL_1 = 1;
    private ImgBufFormat a;

    public ImgBufBeautyFilter(ImgPreProcessWrap imgPreProcessWrap) {
        super(imgPreProcessWrap);
    }

    public void setBeautyLevel(int i) {
        this.mImagePreProcess.a(i);
    }

    public int getSinkPinNum() {
        return BEAUTY_LEVEL_1;
    }

    public void release() {
        super.release();
    }

    protected ImgBufFormat getSrcPinFormat() {
        return this.a;
    }

    protected void onFormatChanged(int i, ImgBufFormat imgBufFormat) {
        this.a = imgBufFormat;
    }

    protected void doFilter() {
        this.mOutPutFrame = this.mImagePreProcess.c(this.mInputFrames[this.mMainSinkPinIndex]);
    }
}
