package com.ksyun.media.streamer.encoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.util.Log;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.streamer.framework.AVConst;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.d;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import java.nio.ByteBuffer;

@TargetApi(16)
public class MediaCodecAudioEncoder extends MediaCodecEncoderBase<AudioBufFrame, AudioBufFrame> {
    private static final String o = "HWAudioEncoder";
    private static final int p = 16;
    private static final int q = 8192;
    private d r;

    protected /* synthetic */ Object b(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        return a(byteBuffer, bufferInfo);
    }

    protected /* synthetic */ boolean c(Object obj) {
        return a((AudioBufFrame) obj);
    }

    public MediaCodecAudioEncoder() {
        this.r = new d(p, q);
    }

    protected int a(Object obj) {
        int i;
        AudioEncodeFormat audioEncodeFormat = (AudioEncodeFormat) obj;
        switch (audioEncodeFormat.getChannels()) {
            case com.ksyun.media.streamer.util.gles.d.a /*1*/:
                i = p;
                break;
            case com.ksyun.media.streamer.util.gles.d.b /*2*/:
                i = 12;
                break;
            default:
                throw new IllegalArgumentException("Invalid channel count. Must be 1 or 2");
        }
        int profile = audioEncodeFormat.getProfile();
        if (profile == 28 && audioEncodeFormat.getChannels() == 1) {
            profile = 4;
            Log.w(o, "set aac_he_v2 for mono audio, fallback to aac_he");
        }
        switch (profile) {
            case TexTransformUtil.COORDS_COUNT /*4*/:
                profile = 5;
                break;
            case AVConst.PROFILE_AAC_HE_V2 /*28*/:
                profile = 29;
                break;
            default:
                profile = 2;
                break;
        }
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(audioEncodeFormat.getMime(), audioEncodeFormat.getSampleRate(), i);
        createAudioFormat.setInteger("aac-profile", profile);
        createAudioFormat.setInteger("channel-count", audioEncodeFormat.getChannels());
        createAudioFormat.setInteger(KSYMediaMeta.IJKM_KEY_BITRATE, audioEncodeFormat.getBitrate());
        createAudioFormat.setInteger("max-input-size", 16384);
        try {
            this.k = MediaCodec.createEncoderByType(audioEncodeFormat.getMime());
            this.k.configure(createAudioFormat, null, null, 1);
            this.k.start();
            this.l = new BufferInfo();
            this.n = null;
            return 0;
        } catch (Exception e) {
            Log.e(o, "Failed to start MediaCodec audio encoder");
            e.printStackTrace();
            return DeviceInfoTools.REQUEST_ERROR_PARSE_FILED;
        }
    }

    protected void a() {
        try {
            a(null, 0);
            a(true);
        } catch (Exception e) {
        }
        try {
            this.k.stop();
        } catch (Exception e2) {
            Log.e(o, "stop encoder failed, ignore");
        }
        this.k.release();
        this.k = null;
        Log.i(o, "MediaCodec released");
        AudioBufFrame audioBufFrame = new AudioBufFrame((AudioBufFormat) this.n, null, 0);
        audioBufFrame.flags |= 4;
        g(audioBufFrame);
    }

    protected boolean a(AudioBufFrame audioBufFrame) {
        if (audioBufFrame == null || audioBufFrame.buf == null) {
            return false;
        }
        ByteBuffer a = this.r.a(audioBufFrame.buf.limit());
        if (a == null) {
            Log.w(o, "Audio frame dropped, size=" + audioBufFrame.buf.limit() + " pts=" + audioBufFrame.pts);
            return true;
        }
        a.put(audioBufFrame.buf);
        a.flip();
        audioBufFrame.buf.rewind();
        audioBufFrame.buf = a;
        return false;
    }

    protected int b(AudioBufFrame audioBufFrame) {
        int i = 0;
        if (!(audioBufFrame == null || audioBufFrame.buf == null)) {
            if (this.e) {
                for (int i2 = 0; i2 < audioBufFrame.buf.limit(); i2++) {
                    audioBufFrame.buf.put(i2, (byte) 0);
                }
                audioBufFrame.buf.rewind();
            }
            try {
                a(false);
                a(audioBufFrame.buf, audioBufFrame.pts * 1000);
            } catch (Exception e) {
                Log.e(o, "Encode frame failed!");
                i = DeviceInfoTools.REQUEST_ERROR_FAILED;
                e.printStackTrace();
            }
            this.r.a(audioBufFrame.buf);
        }
        return i;
    }

    protected void a(MediaFormat mediaFormat) {
        AudioEncodeFormat audioEncodeFormat = (AudioEncodeFormat) this.b;
        AudioBufFormat audioBufFormat = new AudioBufFormat(audioEncodeFormat.getSampleFmt(), audioEncodeFormat.getSampleRate(), audioEncodeFormat.getChannels());
        audioBufFormat.codecId = StreamerConstants.CODEC_ID_AAC;
        this.n = audioBufFormat;
    }

    protected AudioBufFrame a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        AudioBufFrame audioBufFrame = new AudioBufFrame((AudioBufFormat) this.n, byteBuffer, bufferInfo.presentationTimeUs / 1000);
        if ((bufferInfo.flags & 4) != 0) {
            audioBufFrame.flags |= 4;
        }
        if ((bufferInfo.flags & 1) != 0) {
            audioBufFrame.flags |= 1;
        }
        if ((bufferInfo.flags & 2) != 0) {
            audioBufFrame.flags |= 2;
        }
        return audioBufFrame;
    }
}
