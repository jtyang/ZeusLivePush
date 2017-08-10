package com.ksyun.media.streamer.capture;

import android.graphics.Bitmap;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.SrcPin;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: ImgBufSrcPin */
public class a extends SrcPin<ImgBufFrame> {
    private int[] c;
    private ByteBuffer d;
    private ImgBufFrame e;

    public synchronized void a(Bitmap bitmap, boolean z) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                ImgBufFormat imgBufFormat;
                Object obj;
                if (this.e != null && this.e.format.width == bitmap.getWidth() && this.e.format.height == bitmap.getHeight()) {
                    imgBufFormat = this.e.format;
                    obj = null;
                } else {
                    imgBufFormat = new ImgBufFormat(4, bitmap.getWidth(), bitmap.getHeight(), 0);
                    obj = 1;
                }
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int i = width * height;
                int i2 = i * 4;
                if (this.c == null) {
                    this.c = new int[i];
                }
                if (this.c.length < i) {
                    this.c = null;
                    this.c = new int[i];
                }
                bitmap.getPixels(this.c, 0, width, 0, 0, width, height);
                if (z) {
                    bitmap.recycle();
                }
                if (this.d == null) {
                    this.d = ByteBuffer.allocateDirect(i2);
                    this.d.order(ByteOrder.nativeOrder());
                }
                if (this.d.capacity() < i2) {
                    this.d = null;
                    this.d = ByteBuffer.allocateDirect(i2);
                    this.d.order(ByteOrder.nativeOrder());
                }
                this.d.clear();
                this.d.asIntBuffer().put(this.c, 0, i);
                if (obj != null) {
                    onFormatChanged(imgBufFormat);
                    this.e = new ImgBufFrame(imgBufFormat, this.d, 0);
                }
                if (isConnected()) {
                    onFrameAvailable(this.e);
                }
            }
        }
        if (this.e != null) {
            this.e = null;
            if (isConnected()) {
                onFrameAvailable(null);
            }
        }
    }

    public synchronized void a() {
        disconnect(true);
        this.c = null;
        this.d = null;
    }
}
