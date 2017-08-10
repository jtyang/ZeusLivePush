package com.ksyun.media.streamer.filter.imgbuf;

import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import java.util.UnknownFormatFlagsException;

public class ImgBufScaleFilter extends ImgBufFilterBase {
    private ImgBufFormat a;

    public ImgBufScaleFilter(ImgPreProcessWrap imgPreProcessWrap) {
        super(imgPreProcessWrap);
        this.a = new ImgBufFormat(3, 0, 0, 0);
    }

    public ImgBufScaleFilter() {
        this.a = new ImgBufFormat(3, 0, 0, 0);
    }

    public void setTargetSize(int i, int i2) {
        this.mImagePreProcess.a(i, i2);
        this.a.width = i;
        this.a.height = i2;
    }

    public void setMirror(boolean z) {
        this.mImagePreProcess.a(z);
    }

    public void setOutputFormat(int i) {
        if (i == 3 || i == 5) {
            this.a.format = i;
            return;
        }
        throw new UnknownFormatFlagsException("format should be I420 or RGBA");
    }

    public int getSinkPinNum() {
        return 1;
    }

    public void release() {
        super.release();
    }

    protected ImgBufFormat getSrcPinFormat() {
        return this.a;
    }

    protected void onFormatChanged(int i, ImgBufFormat imgBufFormat) {
        if (this.a.width == 0 || this.a.height == 0) {
            this.a.width = imgBufFormat.width;
            this.a.height = imgBufFormat.height;
        }
    }

    protected void doFilter() {
        ImgBufFrame imgBufFrame = this.mInputFrames[this.mMainSinkPinIndex];
        if (imgBufFrame.format.equals(this.a)) {
            this.mOutPutFrame = imgBufFrame;
        } else if (imgBufFrame.buf == null || imgBufFrame.buf.limit() == 0) {
            this.mOutPutFrame = new ImgBufFrame(imgBufFrame);
            this.mOutPutFrame.format = this.a;
        } else if (this.a.format == 3) {
            this.mOutPutFrame = this.mImagePreProcess.a(imgBufFrame);
        } else if (this.a.format == 5) {
            this.mOutPutFrame = this.mImagePreProcess.b(this.mInputFrames[this.mMainSinkPinIndex]);
        }
    }
}
