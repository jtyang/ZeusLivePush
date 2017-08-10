package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.ksyun.media.streamer.util.BitmapLoader;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;

public class ImgBeautyAdjustSkinColorFilter extends ImgTexFilter {
    private Context a;
    private String b;
    private Bitmap c;
    private String d;
    private Bitmap e;
    private int[] f;
    private int[] g;
    private int h;
    private int i;

    public ImgBeautyAdjustSkinColorFilter(GLRender gLRender, Context context, String str, String str2) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 12);
        this.f = new int[]{-1};
        this.g = new int[]{-1};
        this.c = BitmapLoader.loadBitmap(context, str, 0, 0);
        if (this.c == null || this.c.isRecycled()) {
            throw new IllegalStateException("Resource bitmap not valid!");
        }
        this.b = str;
        this.a = context;
    }

    public boolean isRuddyRatioSupported() {
        return true;
    }

    public void setRuddyRatio(float f) {
        super.setRuddyRatio(f);
    }

    protected void onInitialized() {
        this.h = getUniformLocation("whitenTexture");
        this.i = getUniformLocation("skinColorRatio");
        if (this.c == null || this.c.isRecycled()) {
            this.c = BitmapLoader.loadBitmap(this.a, this.b, 0, 0);
            if (this.c == null || this.c.isRecycled()) {
                throw new IllegalStateException("Resource bitmap not valid!");
            }
        }
        this.f[0] = GlUtil.loadTexture(this.c, -1);
        this.c.recycle();
        this.c = null;
    }

    protected void onDrawArraysPre() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, this.f[0]);
        GLES20.glUniform1i(this.h, 2);
        GLES20.glUniform1f(this.i, this.mRuddyRatio);
    }

    protected void onDrawArraysAfter() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, 0);
    }

    protected void onRelease() {
        super.onRelease();
        GLES20.glDeleteTextures(1, this.f, 0);
        this.f[0] = -1;
    }
}
