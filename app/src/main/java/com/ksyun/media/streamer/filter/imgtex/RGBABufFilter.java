package com.ksyun.media.streamer.filter.imgtex;

import com.ksyun.media.streamer.util.gles.GLRender;
import java.nio.ByteBuffer;

public abstract class RGBABufFilter extends ImgTexBufFilter {
    private static final String a = "RGBABufFilter";

    protected abstract ByteBuffer doFilter(ByteBuffer byteBuffer, int i, int i2, int i3);

    protected abstract void onSizeChanged(int i, int i2, int i3);

    public RGBABufFilter(GLRender gLRender) {
        super(gLRender, 5);
    }

    protected void onSizeChanged(int[] iArr, int i, int i2) {
        onSizeChanged(iArr[0], i, i2);
    }

    protected ByteBuffer doFilter(ByteBuffer byteBuffer, int[] iArr, int i, int i2) {
        return doFilter(byteBuffer, iArr[0], i, i2);
    }
}
