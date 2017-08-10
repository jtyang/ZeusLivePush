package com.ksyun.media.streamer.encoder;

import android.util.Log;
import com.ksyun.media.streamer.encoder.AVEncoderWrapper.a;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.d;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import java.nio.ByteBuffer;

public class AVCodecAudioEncoder extends Encoder<AudioBufFrame, AudioBufFrame> implements a {
    private static final String k = "AVCodecAudioEncoder";
    private static final boolean l = false;
    private static final int m = 16;
    private static final int n = 8192;
    private d o;
    private AVEncoderWrapper p;
    private AudioBufFormat q;

    protected /* synthetic */ boolean c(Object obj) {
        return a((AudioBufFrame) obj);
    }

    public AVCodecAudioEncoder() {
        this.o = new d(m, n);
    }

    protected int a(Object obj) {
        if (!(obj instanceof AudioEncodeFormat)) {
            return DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
        }
        AudioEncodeFormat audioEncodeFormat = (AudioEncodeFormat) obj;
        this.p = new AVEncoderWrapper();
        this.p.a((a) this);
        int profile = audioEncodeFormat.getProfile();
        if (profile == 28 && audioEncodeFormat.getChannels() == 1) {
            profile = 4;
            Log.w(k, "set aac_he_v2 for mono audio, fallback to aac_he");
        }
        return this.p.a(StreamerConstants.CODEC_ID_AAC, audioEncodeFormat.getBitrate(), audioEncodeFormat.getSampleFmt(), audioEncodeFormat.getSampleRate(), audioEncodeFormat.getChannels(), profile);
    }

    protected void a() {
        this.p.a();
        this.p.b();
        this.p = null;
        onEncoded(0, null, 0, 0, 4);
    }

    protected boolean a(Object obj, Object obj2) {
        AudioBufFormat audioBufFormat = (AudioBufFormat) obj;
        AudioEncodeFormat audioEncodeFormat = (AudioEncodeFormat) obj2;
        audioEncodeFormat.setSampleFmt(audioBufFormat.sampleFormat);
        audioEncodeFormat.setSampleRate(audioBufFormat.sampleRate);
        audioEncodeFormat.setChannels(audioBufFormat.channels);
        return true;
    }

    protected void a(int i) {
        this.p.a(i);
    }

    protected void b() {
        this.p.a(null, 0, 0);
    }

    protected boolean a(AudioBufFrame audioBufFrame) {
        if (audioBufFrame == null || audioBufFrame.buf == null) {
            return l;
        }
        ByteBuffer a = this.o.a(audioBufFrame.buf.limit());
        if (a == null) {
            Log.w(k, "Audio frame dropped, size=" + audioBufFrame.buf.limit() + " pts=" + audioBufFrame.pts);
            return true;
        }
        a.put(audioBufFrame.buf);
        a.flip();
        audioBufFrame.buf.rewind();
        audioBufFrame.buf = a;
        return l;
    }

    protected int b(AudioBufFrame audioBufFrame) {
        if (audioBufFrame == null || audioBufFrame.buf == null) {
            return 0;
        }
        if (this.e) {
            for (int i = 0; i < audioBufFrame.buf.limit(); i++) {
                audioBufFrame.buf.put(i, (byte) 0);
            }
            audioBufFrame.buf.rewind();
        }
        int a = this.p.a(audioBufFrame.buf, audioBufFrame.pts, audioBufFrame.flags);
        this.o.a(audioBufFrame.buf);
        return a;
    }

    public void onEncoded(long j, ByteBuffer byteBuffer, long j2, long j3, int i) {
        if ((i & 2) != 0) {
            AudioEncodeFormat audioEncodeFormat = (AudioEncodeFormat) this.b;
            this.q = new AudioBufFormat(audioEncodeFormat.getSampleFmt(), audioEncodeFormat.getSampleRate(), audioEncodeFormat.getChannels());
            this.q.codecId = StreamerConstants.CODEC_ID_AAC;
            f(this.q);
        }
        AudioBufFrame audioBufFrame = new AudioBufFrame(this.q, byteBuffer, j3);
        audioBufFrame.dts = j2;
        audioBufFrame.flags = i;
        audioBufFrame.avPacketOpaque = j;
        g(audioBufFrame);
    }
}
