package com.ksyun.media.streamer.filter.imgtex;

import android.graphics.PointF;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import java.nio.FloatBuffer;

public class ImgTexScaleFilter extends ImgTexFilter {
    private FloatBuffer a;
    private ImgTexFormat b;
    private ImgTexFormat c;

    public ImgTexScaleFilter(GLRender gLRender) {
        super(gLRender);
        this.a = TexTransformUtil.getTexCoordsBuf();
    }

    public void setTargetSize(int i, int i2) {
        this.b = new ImgTexFormat(1, i, i2);
        if (this.c != null) {
            a(this.c);
        }
    }

    protected ImgTexFormat getSrcPinFormat() {
        return this.b;
    }

    public void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.c = imgTexFormat;
        a(imgTexFormat);
    }

    protected FloatBuffer getTexCoords() {
        return this.a;
    }

    private void a(ImgTexFormat imgTexFormat) {
        if (this.b != null && this.b.width != 0 && this.b.height != 0 && imgTexFormat.width != 0 && imgTexFormat.height != 0) {
            PointF calCrop = TexTransformUtil.calCrop(((float) imgTexFormat.width) / ((float) imgTexFormat.height), ((float) this.b.width) / ((float) this.b.height));
            this.a = TexTransformUtil.getCropTexCoordsBuf(calCrop.x, calCrop.y);
        }
    }
}
