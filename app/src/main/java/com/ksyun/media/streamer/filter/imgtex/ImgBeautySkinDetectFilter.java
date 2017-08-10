package com.ksyun.media.streamer.filter.imgtex;

import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;

public class ImgBeautySkinDetectFilter extends ImgTexFilter {
    public ImgBeautySkinDetectFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 8);
    }

    protected boolean isReuseFbo() {
        return false;
    }

    protected void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.mInited = false;
        this.mOutTexture = -1;
    }
}
