package com.ksyun.media.streamer.encoder;

import android.os.Build.VERSION;
import com.ksyun.media.streamer.encoder.Encoder.EncoderListener;
import com.ksyun.media.streamer.encoder.ImgTexToBuf.ErrorListener;
import com.ksyun.media.streamer.filter.imgbuf.ImgBufBeautyFilter;
import com.ksyun.media.streamer.filter.imgbuf.ImgBufMixer;
import com.ksyun.media.streamer.filter.imgbuf.ImgBufScaleFilter;
import com.ksyun.media.streamer.filter.imgbuf.ImgPreProcessWrap;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import com.ksyun.media.streamer.util.gles.GLRender;

public class VideoEncoderMgt {
    public static final int METHOD_HARDWARE = 2;
    public static final int METHOD_SOFTWARE = 3;
    public static final int METHOD_SOFTWARE_COMPAT = 1;
    private static final String a = "VideoEncoderMgt";
    private GLRender b;
    private ImgTexToBuf c;
    private Encoder d;
    private int e;
    private VideoEncodeFormat f;
    private EncoderListener g;
    private ImgBufScaleFilter h;
    private ImgBufBeautyFilter i;
    private boolean j;
    private ImgBufMixer k;
    private ImgPreProcessWrap l;
    private PinAdapter<ImgTexFrame> m;
    private PinAdapter<ImgBufFrame> n;
    private PinAdapter<ImgBufFrame> o;

    private class a<T> extends PinAdapter<T> {
        final /* synthetic */ VideoEncoderMgt a;

        private a(VideoEncoderMgt videoEncoderMgt) {
            this.a = videoEncoderMgt;
        }

        public void onDisconnect(boolean z) {
            if (z) {
                this.a.release();
            }
        }
    }

    public VideoEncoderMgt(GLRender gLRender) {
        this.b = gLRender;
        this.l = new ImgPreProcessWrap();
        this.h = new ImgBufScaleFilter(this.l);
        this.i = new ImgBufBeautyFilter(this.l);
        this.k = new ImgBufMixer(this.l);
        this.o = new PinAdapter();
        this.m = new a();
        this.n = new a();
        this.h.getSrcPin().connect(this.k.getSinkPin());
        this.c = new ImgTexToBuf(gLRender);
        this.c.setErrorListener(new ErrorListener() {
            final /* synthetic */ VideoEncoderMgt a;

            {
                this.a = r1;
            }

            public void onError(ImgTexToBuf imgTexToBuf, int i) {
                if (this.a.g != null) {
                    int i2 = DeviceInfoTools.REQUEST_ERROR_FAILED;
                    if (i == -1) {
                        i2 = DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
                    }
                    this.a.g.onError(this.a.d, i2);
                }
            }
        });
        this.e = a((int) METHOD_SOFTWARE);
        a();
    }

    public SinkPin<ImgTexFrame> getImgTexSinkPin() {
        return this.m.mSinkPin;
    }

    public SinkPin<ImgBufFrame> getImgBufSinkPin() {
        return this.n.mSinkPin;
    }

    public SrcPin<ImgBufFrame> getSrcPin() {
        return this.o.mSrcPin;
    }

    public synchronized Encoder getEncoder() {
        return this.d;
    }

    public ImgBufMixer getImgBufMixer() {
        return this.k;
    }

    public synchronized int getEncodeMethod() {
        return this.e;
    }

    public synchronized void setEncodeMethod(int i) {
        int a = a(i);
        StatsLogReport.getInstance().setEncodeMethod(a);
        if (a != this.e) {
            if (this.e == METHOD_HARDWARE) {
                MediaCodecSurfaceEncoder d = d();
                d.mSrcPin.disconnect(false);
                this.m.mSrcPin.disconnect(d.mSinkPin, false);
            } else if (this.e == METHOD_SOFTWARE) {
                b().mSrcPin.disconnect(false);
                this.c.mSrcPin.disconnect(false);
                this.m.mSrcPin.disconnect(this.c.mSinkPin, false);
            } else {
                AVCodecVideoEncoder b = b();
                b.mSrcPin.disconnect(false);
                this.k.getSrcPin().disconnect(b.mSinkPin, false);
                this.n.mSrcPin.disconnect(this.h.getSinkPin(), false);
            }
            this.d.release();
            this.e = a;
            a();
        }
    }

    public synchronized void setEncodeFormat(VideoEncodeFormat videoEncodeFormat) {
        this.f = videoEncodeFormat;
        this.d.configure(videoEncodeFormat);
        this.h.setTargetSize(videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight());
        this.k.setTargetSize(videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight());
        StatsLogReport.getInstance().setEncodeFormat(this.f.getCodecId());
        StatsLogReport.getInstance().setVideoEncodeProfile(this.f.getProfile());
        StatsLogReport.getInstance().setVideoEncodeScence(this.f.getScene());
        StatsLogReport.getInstance().setIFrameIntervalSec(this.f.getIframeinterval());
        StatsLogReport.getInstance().setTargetResolution(this.f.getWidth(), this.f.getHeight());
    }

    public VideoEncodeFormat getEncodeFormat() {
        return this.f;
    }

    public synchronized void setEncoderListener(EncoderListener encoderListener) {
        this.g = encoderListener;
        this.d.setEncoderListener(encoderListener);
    }

    public synchronized void start() {
        if (this.e == METHOD_SOFTWARE) {
            this.m.mSrcPin.connect(this.c.mSinkPin);
        } else if (this.e == METHOD_SOFTWARE_COMPAT) {
            this.n.mSrcPin.connect(this.h.getSinkPin());
        }
        this.d.start();
    }

    public synchronized void stop() {
        if (this.e == METHOD_SOFTWARE) {
            this.m.mSrcPin.disconnect(this.c.mSinkPin, false);
        } else if (this.e == METHOD_SOFTWARE_COMPAT) {
            this.n.mSrcPin.disconnect(this.h.getSinkPin(), false);
        }
        this.d.stop();
    }

    public void setEnableImgBufBeauty(boolean z) {
        if (this.j != z) {
            if (z) {
                this.h.getSrcPin().disconnect(this.k.getSinkPin(), false);
                this.h.getSrcPin().connect(this.i.getSinkPin());
                this.i.getSrcPin().connect(this.k.getSinkPin());
            } else {
                this.i.getSrcPin().disconnect(false);
                this.h.getSrcPin().disconnect(this.i.getSinkPin(), false);
                this.h.getSrcPin().connect(this.k.getSinkPin());
            }
            this.j = z;
        }
    }

    public void setImgBufMirror(boolean z) {
        this.h.setMirror(z);
    }

    public void setImgBufTargetSize(int i, int i2) {
        if (this.f == null) {
            return;
        }
        if (i != this.f.getWidth() || i2 != this.f.getHeight()) {
            this.h.setTargetSize(i, i2);
            this.k.setTargetSize(i, i2);
        }
    }

    public synchronized void release() {
        this.d.release();
        this.l.a();
        this.h.release();
        this.i.release();
        this.k.release();
    }

    private int a(int i) {
        if (i == METHOD_HARDWARE && VERSION.SDK_INT < 18) {
            return METHOD_SOFTWARE_COMPAT;
        }
        if (i != METHOD_SOFTWARE || VERSION.SDK_INT >= 19) {
            return i;
        }
        return METHOD_SOFTWARE_COMPAT;
    }

    private void a() {
        Encoder mediaCodecSurfaceEncoder;
        if (this.e == METHOD_HARDWARE) {
            mediaCodecSurfaceEncoder = new MediaCodecSurfaceEncoder(this.b);
            this.m.mSrcPin.connect(mediaCodecSurfaceEncoder.mSinkPin);
            mediaCodecSurfaceEncoder.mSrcPin.connect(this.o.mSinkPin);
            this.d = mediaCodecSurfaceEncoder;
        } else if (this.e == METHOD_SOFTWARE) {
            mediaCodecSurfaceEncoder = new AVCodecVideoEncoder();
            this.c.mSrcPin.connect(mediaCodecSurfaceEncoder.mSinkPin);
            mediaCodecSurfaceEncoder.mSrcPin.connect(this.o.mSinkPin);
            this.d = mediaCodecSurfaceEncoder;
        } else {
            mediaCodecSurfaceEncoder = new AVCodecVideoEncoder();
            this.k.getSrcPin().connect(mediaCodecSurfaceEncoder.mSinkPin);
            mediaCodecSurfaceEncoder.mSrcPin.connect(this.o.mSinkPin);
            this.d = mediaCodecSurfaceEncoder;
        }
        if (this.f != null) {
            this.d.configure(this.f);
        }
        if (this.g != null) {
            this.d.setEncoderListener(this.g);
        }
    }

    private AVCodecVideoEncoder b() {
        return (AVCodecVideoEncoder) AVCodecVideoEncoder.class.cast(this.d);
    }

    private AVCodecSurfaceEncoder c() {
        return (AVCodecSurfaceEncoder) AVCodecSurfaceEncoder.class.cast(this.d);
    }

    private MediaCodecSurfaceEncoder d() {
        return (MediaCodecSurfaceEncoder) MediaCodecSurfaceEncoder.class.cast(this.d);
    }
}
