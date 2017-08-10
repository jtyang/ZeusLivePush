package com.ksyun.media.streamer.encoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import java.nio.ByteBuffer;

@TargetApi(16)
public abstract class MediaCodecEncoderBase<I, O> extends Encoder<I, O> {
    private static final String o = "MediaCodecEncoderBase";
    private static final boolean p = false;
    protected MediaCodec k;
    protected BufferInfo l;
    protected boolean m;
    protected Object n;
    private int q;

    protected abstract void a(MediaFormat mediaFormat);

    protected abstract O b(ByteBuffer byteBuffer, BufferInfo bufferInfo);

    public MediaCodecEncoderBase() {
        this.m = false;
        this.q = 0;
    }

    public void signalEndOfStream() {
        this.m = true;
    }

    @TargetApi(19)
    protected void a(int i) {
        if (this.k != null) {
            if (VERSION.SDK_INT >= 19) {
                Bundle bundle = new Bundle();
                bundle.putInt("video-bitrate", i);
                this.k.setParameters(bundle);
                return;
            }
            Log.w(o, "Ignoring adjustVideoBitrate call. This functionality is only available on Android API 19+");
        }
    }

    protected void a(ByteBuffer byteBuffer, long j) {
        ByteBuffer[] inputBuffers = this.k.getInputBuffers();
        int dequeueInputBuffer = this.k.dequeueInputBuffer(0);
        if (dequeueInputBuffer >= 0) {
            ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
            byteBuffer2.clear();
            if (byteBuffer != null) {
                byteBuffer2.put(byteBuffer);
                byteBuffer.rewind();
                this.k.queueInputBuffer(dequeueInputBuffer, 0, byteBuffer.limit(), j, 0);
                return;
            }
            this.k.queueInputBuffer(dequeueInputBuffer, 0, 0, j, 4);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void a(boolean r7) {
        /*
        r6 = this;
        r0 = r6.l;
        if (r0 != 0) goto L_0x000b;
    L_0x0004:
        r0 = new android.media.MediaCodec$BufferInfo;
        r0.<init>();
        r6.l = r0;
    L_0x000b:
        r0 = r6.k;
        r0 = r0.getOutputBuffers();
    L_0x0011:
        r1 = r6.k;	 Catch:{ Exception -> 0x00ff }
        r2 = r6.l;	 Catch:{ Exception -> 0x00ff }
        r4 = 0;
        r1 = r1.dequeueOutputBuffer(r2, r4);	 Catch:{ Exception -> 0x00ff }
        r2 = -1;
        if (r1 != r2) goto L_0x0042;
    L_0x001e:
        if (r7 != 0) goto L_0x0023;
    L_0x0020:
        if (r7 == 0) goto L_0x0022;
    L_0x0022:
        return;
    L_0x0023:
        r1 = r6.q;
        r1 = r1 + 1;
        r6.q = r1;
        r1 = r6.q;
        r2 = 10;
        if (r1 <= r2) goto L_0x0011;
    L_0x002f:
        r0 = r6.l;
        r1 = r0.flags;
        r1 = r1 | 4;
        r0.flags = r1;
        r0 = 0;
        r1 = r6.l;
        r0 = r6.b(r0, r1);
        r6.g(r0);
        goto L_0x0020;
    L_0x0042:
        r2 = -3;
        if (r1 != r2) goto L_0x004c;
    L_0x0045:
        r0 = r6.k;
        r0 = r0.getOutputBuffers();
        goto L_0x0011;
    L_0x004c:
        r2 = -2;
        if (r1 != r2) goto L_0x005e;
    L_0x004f:
        r1 = r6.k;
        r1 = r1.getOutputFormat();
        r6.a(r1);
        r1 = r6.n;
        r6.f(r1);
        goto L_0x0011;
    L_0x005e:
        if (r1 >= 0) goto L_0x0079;
    L_0x0060:
        r2 = "MediaCodecEncoderBase";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "unexpected result from encoder.dequeueOutputBuffer: ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.w(r2, r1);
        goto L_0x0011;
    L_0x0079:
        r2 = r0[r1];
        if (r2 != 0) goto L_0x009c;
    L_0x007d:
        r0 = new java.lang.RuntimeException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "encoderOutputBuffer ";
        r2 = r2.append(r3);
        r1 = r2.append(r1);
        r2 = " was null";
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x009c:
        r3 = r6.l;
        r3 = r3.size;
        if (r3 < 0) goto L_0x00ec;
    L_0x00a2:
        r3 = r6.l;
        r3 = r3.offset;
        r2.position(r3);
        r3 = r6.l;
        r3 = r3.offset;
        r4 = r6.l;
        r4 = r4.size;
        r3 = r3 + r4;
        r2.limit(r3);
        r3 = r6.m;
        if (r3 == 0) goto L_0x00ca;
    L_0x00b9:
        r3 = r6.l;
        r4 = r6.l;
        r4 = r4.flags;
        r4 = r4 | 4;
        r3.flags = r4;
        r3 = "MediaCodecEncoderBase";
        r4 = "Forcing EOS";
        android.util.Log.i(r3, r4);
    L_0x00ca:
        r3 = r6.n;
        if (r3 != 0) goto L_0x00dd;
    L_0x00ce:
        r0 = "MediaCodecEncoderBase";
        r1 = "No INFO_OUTPUT_FORMAT_CHANGED event before frame available";
        android.util.Log.e(r0, r1);
        r0 = new java.lang.IllegalStateException;
        r1 = "No INFO_OUTPUT_FORMAT_CHANGED event before frame available";
        r0.<init>(r1);
        throw r0;
    L_0x00dd:
        r3 = r6.l;
        r2 = r6.b(r2, r3);
        r6.g(r2);
        r2 = r6.k;
        r3 = 0;
        r2.releaseOutputBuffer(r1, r3);
    L_0x00ec:
        r1 = r6.l;
        r1 = r1.flags;
        r1 = r1 & 4;
        if (r1 == 0) goto L_0x0011;
    L_0x00f4:
        if (r7 != 0) goto L_0x0020;
    L_0x00f6:
        r0 = "MediaCodecEncoderBase";
        r1 = "reached end of stream unexpectedly";
        android.util.Log.w(r0, r1);
        goto L_0x0020;
    L_0x00ff:
        r0 = move-exception;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.encoder.MediaCodecEncoderBase.a(boolean):void");
    }
}
