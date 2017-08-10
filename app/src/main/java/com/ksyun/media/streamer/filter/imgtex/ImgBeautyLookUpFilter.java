package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.ksyun.media.streamer.util.BitmapLoader;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;

public class ImgBeautyLookUpFilter extends ImgTexFilter {
    private final Object a;
    private Context b;
    private String c;
    private Bitmap d;
    private int[] e;
    private float f;
    private int g;
    private int h;

    public ImgBeautyLookUpFilter(GLRender gLRender, Context context) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 10);
        this.a = new Object();
        this.e = new int[]{-1};
        this.f = 0.5f;
        this.b = context;
    }

    public void setLookupBitmap(String str) {
        Bitmap loadBitmap = BitmapLoader.loadBitmap(this.b, str, 0, 0);
        if (loadBitmap == null || loadBitmap.isRecycled()) {
            throw new IllegalArgumentException("Resource bitmap not valid!");
        }
        synchronized (this.a) {
            this.c = str;
            this.d = loadBitmap;
        }
    }

    public void setIntensity(float f) {
        this.f = f;
    }

    protected void onInitialized() {
        this.g = getUniformLocation("lookUpTexture");
        this.h = getUniformLocation("intensity");
        synchronized (this.a) {
            if (this.d == null || this.d.isRecycled()) {
                this.d = BitmapLoader.loadBitmap(this.b, this.c, 0, 0);
                if (this.d == null || this.d.isRecycled()) {
                    throw new IllegalArgumentException("Resource bitmap not valid!");
                }
            }
            this.e[0] = GlUtil.loadTexture(this.d, -1);
            this.d.recycle();
            this.d = null;
        }
    }

    protected void onDrawArraysPre() {
        synchronized (this.a) {
            if (!(this.d == null || this.d.isRecycled())) {
                this.e[0] = GlUtil.loadTexture(this.d, this.e[0]);
                this.d.recycle();
                this.d = null;
            }
        }
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, this.e[0]);
        GLES20.glUniform1i(this.g, 2);
        GLES20.glUniform1f(this.h, this.f);
    }

    protected void onDrawArraysAfter() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, 0);
    }

    protected void onRelease() {
        super.onRelease();
        GLES20.glDeleteTextures(1, this.e, 0);
        this.e[0] = -1;
    }
}
