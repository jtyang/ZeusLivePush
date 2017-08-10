package com.ksyun.media.streamer.filter.imgtex;

import android.os.ConditionVariable;
import com.ksyun.media.streamer.capture.ImgTexSrcPin;
import com.ksyun.media.streamer.encoder.ColorFormatConvert;
import com.ksyun.media.streamer.encoder.ImgTexToBuf;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.util.gles.GLRender;
import java.nio.ByteBuffer;

public abstract class ImgTexBufFilter extends ImgFilterBase {
    private static final String a = "ImgTexBufFilter";
    private SinkPin<ImgTexFrame> b;
    private ImgTexToBuf c;
    private ImgTexSrcPin d;
    private ConditionVariable e;
    private ImgBufFrame f;
    private int g;
    private ImgBufFormat h;
    private ByteBuffer i;

    protected abstract ByteBuffer doFilter(ByteBuffer byteBuffer, int[] iArr, int i, int i2);

    protected abstract void onSizeChanged(int[] iArr, int i, int i2);

    public ImgTexBufFilter(GLRender gLRender, int i) {
        this.g = 5;
        if (i == 3 || i == 5) {
            this.e = new ConditionVariable(true);
            this.b = new SinkPin<ImgTexFrame>() {
                final /* synthetic */ ImgTexBufFilter a;

                {
                    this.a = r1;
                }

                public /* synthetic */ void onFrameAvailable(Object obj) {
                    a((ImgTexFrame) obj);
                }

                public void onFormatChanged(Object obj) {
                    this.a.c.mSinkPin.onFormatChanged(obj);
                }

                public void a(ImgTexFrame imgTexFrame) {
                    this.a.e.close();
                    this.a.c.mSinkPin.onFrameAvailable(imgTexFrame);
                    this.a.e.block();
                    if (this.a.f != null) {
                        this.a.d.updateFrame(this.a.f.buf, this.a.f.format.stride[0], this.a.f.format.width, this.a.f.format.height, this.a.f.pts);
                    }
                    this.a.f = null;
                }

                public void onDisconnect(boolean z) {
                    this.a.e.open();
                    this.a.c.mSinkPin.onDisconnect(z);
                }
            };
            this.c = new ImgTexToBuf(gLRender);
            this.d = new ImgTexSrcPin(gLRender);
            SinkPin anonymousClass2 = new SinkPin<ImgBufFrame>() {
                final /* synthetic */ ImgTexBufFilter a;

                {
                    this.a = r1;
                }

                public /* synthetic */ void onFrameAvailable(Object obj) {
                    a((ImgBufFrame) obj);
                }

                public void onFormatChanged(Object obj) {
                    ImgBufFormat imgBufFormat = (ImgBufFormat) obj;
                    if (!(this.a.h == null || (this.a.h.width == imgBufFormat.width && this.a.h.height == imgBufFormat.height))) {
                        this.a.h = null;
                    }
                    if (this.a.h == null) {
                        if (this.a.g != 5) {
                            this.a.h = new ImgBufFormat(this.a.g, imgBufFormat.width, imgBufFormat.height, imgBufFormat.orientation, new int[]{imgBufFormat.width, imgBufFormat.width / 2, imgBufFormat.width / 2});
                        } else {
                            this.a.h = imgBufFormat;
                        }
                    }
                    this.a.onSizeChanged(this.a.h.stride, this.a.h.width, this.a.h.height);
                }

                public void a(ImgBufFrame imgBufFrame) {
                    if (this.a.g != 5) {
                        int b = this.a.a(imgBufFrame.format);
                        if (this.a.i != null && this.a.i.limit() < b) {
                            this.a.i = null;
                        }
                        if (this.a.i == null) {
                            this.a.i = ByteBuffer.allocateDirect(b);
                        }
                        if (this.a.i != null) {
                            this.a.i.clear();
                            this.a.a(imgBufFrame);
                            this.a.i.rewind();
                        }
                        ByteBuffer doFilter = this.a.doFilter(this.a.i, this.a.h.stride, this.a.h.width, this.a.h.height);
                        imgBufFrame.buf.clear();
                        this.a.a(imgBufFrame, doFilter);
                        imgBufFrame.buf.rewind();
                    } else {
                        imgBufFrame.buf = this.a.doFilter(imgBufFrame.buf, imgBufFrame.format.stride, imgBufFrame.format.width, imgBufFrame.format.height);
                    }
                    this.a.f = imgBufFrame;
                    this.a.e.open();
                }

                public void onDisconnect(boolean z) {
                    this.a.d.disconnect(z);
                }
            };
            this.g = i;
            this.c.setOutputColorFormat(5);
            this.c.mSrcPin.connect(anonymousClass2);
            return;
        }
        throw new IllegalArgumentException("only FMT_RGBA or FMT_I420 supported!");
    }

    public int getSinkPinNum() {
        return 1;
    }

    public SinkPin<ImgTexFrame> getSinkPin(int i) {
        return this.b;
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.d;
    }

    private int a(ImgBufFormat imgBufFormat) {
        if (this.g == 3) {
            return ((imgBufFormat.width * imgBufFormat.height) * 3) / 2;
        }
        return 0;
    }

    private void a(ImgBufFrame imgBufFrame) {
        if (this.g == 3) {
            ColorFormatConvert.RGBAToI420(imgBufFrame.buf, imgBufFrame.format.stride[0], imgBufFrame.format.width, imgBufFrame.format.height, this.i);
        }
    }

    private void a(ImgBufFrame imgBufFrame, ByteBuffer byteBuffer) {
        if (this.g == 3) {
            ColorFormatConvert.I420ToRGBA(byteBuffer, imgBufFrame.format.stride[0], imgBufFrame.format.width, imgBufFrame.format.height, imgBufFrame.buf);
        }
    }
}
