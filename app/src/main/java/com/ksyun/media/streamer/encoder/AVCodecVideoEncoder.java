package com.ksyun.media.streamer.encoder;

import android.util.Log;
import com.ksyun.media.streamer.encoder.AVEncoderWrapper.a;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import java.nio.ByteBuffer;

public class AVCodecVideoEncoder extends Encoder<ImgBufFrame, ImgBufFrame> implements a {
    private static final String k = "AVCodecVideoEncoder";
    private static final boolean l = false;
    private AVEncoderWrapper m;
    private ImgBufFormat n;
    private boolean o;

    protected /* synthetic */ int b(Object obj) {
        return a((ImgBufFrame) obj);
    }

    protected int a(Object obj) {
        if (!(obj instanceof VideoEncodeFormat)) {
            return DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
        }
        this.o = true;
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) obj;
        this.m = new AVEncoderWrapper();
        this.m.a((a) this);
        return this.m.a(videoEncodeFormat.getCodecId(), videoEncodeFormat.getBitrate(), videoEncodeFormat.getPixFmt(), videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight(), videoEncodeFormat.getFramerate(), videoEncodeFormat.getIframeinterval(), videoEncodeFormat.getScene(), videoEncodeFormat.getProfile(), videoEncodeFormat.getCrf(), videoEncodeFormat.getLiveStreaming());
    }

    protected void a() {
        this.m.a();
        this.m.b();
        this.m = null;
        onEncoded(0, null, 0, 0, 4);
    }

    protected boolean a(Object obj, Object obj2) {
        ImgBufFormat imgBufFormat = (ImgBufFormat) obj;
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) obj2;
        videoEncodeFormat.setWidth(imgBufFormat.width);
        videoEncodeFormat.setHeight(imgBufFormat.height);
        videoEncodeFormat.setPixFmt(imgBufFormat.format);
        return true;
    }

    protected void d(Object obj) {
        ImgBufFormat imgBufFormat = (ImgBufFormat) obj;
        VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) this.b;
        if (getState() != 2) {
            return;
        }
        if (videoEncodeFormat.getWidth() != imgBufFormat.width || videoEncodeFormat.getHeight() != imgBufFormat.height) {
            Log.d(k, "restart encoder");
            b();
            a();
            videoEncodeFormat.setWidth(imgBufFormat.width);
            videoEncodeFormat.setHeight(imgBufFormat.height);
            a(this.b);
        }
    }

    protected void a(int i) {
        this.m.a(i);
    }

    protected void b() {
        this.m.a(null, 0, 0);
    }

    protected int a(ImgBufFrame imgBufFrame) {
        if (imgBufFrame.format.format == 6 && this.o) {
            VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) this.b;
            this.n = new ImgBufFormat(ImgBufFormat.FMT_GIF, videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight(), 0);
            f(this.n);
        }
        if (this.o || this.j) {
            ImgBufFrame imgBufFrame2 = new ImgBufFrame(this.n, null, imgBufFrame.pts);
            imgBufFrame2.flags |= AVFrameBase.FLAG_DUMMY_VIDEO_FRAME;
            g(imgBufFrame2);
            if (this.o) {
                this.o = false;
            }
            if (this.j) {
                imgBufFrame.flags |= 1;
                this.j = false;
            }
        }
        return this.m.a(imgBufFrame.buf, imgBufFrame.pts, imgBufFrame.flags);
    }

    public void onEncoded(long j, ByteBuffer byteBuffer, long j2, long j3, int i) {
        if ((i & 2) != 0) {
            int i2;
            VideoEncodeFormat videoEncodeFormat = (VideoEncodeFormat) this.b;
            if (videoEncodeFormat.getCodecId() == 2) {
                i2 = ImgBufFormat.FMT_HEVC;
            } else if (videoEncodeFormat.getCodecId() == 1) {
                i2 = StreamerConstants.CODEC_ID_AAC;
            } else {
                i2 = ImgBufFormat.FMT_GIF;
            }
            this.n = new ImgBufFormat(i2, videoEncodeFormat.getWidth(), videoEncodeFormat.getHeight(), 0);
            f(this.n);
        }
        ImgBufFrame imgBufFrame = new ImgBufFrame(this.n, byteBuffer, j3);
        imgBufFrame.dts = j2;
        imgBufFrame.flags = i;
        imgBufFrame.avPacketOpaque = j;
        g(imgBufFrame);
    }
}
