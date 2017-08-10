package com.ksyun.media.streamer.filter.audio;

import android.util.Log;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AudioFilterBase {
    private static final String a = "AudioFilterBase";
    private SinkPin<AudioBufFrame> b;
    private SrcPin<AudioBufFrame> c;
    private AudioBufFormat d;
    private AudioBufFormat e;
    private ByteBuffer f;
    private long g;
    private long h;

    private class a extends SinkPin<AudioBufFrame> {
        final /* synthetic */ AudioFilterBase a;

        private a(AudioFilterBase audioFilterBase) {
            this.a = audioFilterBase;
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((AudioBufFrame) obj);
        }

        public void onFormatChanged(Object obj) {
            if (obj != null) {
                this.a.d = (AudioBufFormat) obj;
                Log.d(AudioFilterBase.a, "doFormatChanged nativeModule=" + this.a.d.nativeModule);
                if (this.a.d.nativeModule == 0 || this.a.getNativeInstance() == 0) {
                    this.a.e = this.a.doFormatChanged(this.a.d);
                    if (this.a.e == null) {
                        return;
                    }
                    if (this.a.e == this.a.d) {
                        this.a.e = new AudioBufFormat(this.a.d);
                        this.a.e.nativeModule = 0;
                    }
                } else {
                    this.a.attachTo(0, this.a.d.nativeModule, false);
                    AudioBufFormat outFormat = this.a.getOutFormat();
                    if (outFormat == null) {
                        this.a.e = new AudioBufFormat(this.a.d);
                    } else {
                        this.a.e = new AudioBufFormat(outFormat);
                    }
                    this.a.e.nativeModule = this.a.getNativeInstance();
                    this.a.g = 0;
                    this.a.h = 0;
                }
                this.a.c.onFormatChanged(this.a.e);
            }
        }

        public void a(AudioBufFrame audioBufFrame) {
            if (audioBufFrame != null && audioBufFrame.format != null) {
                Object obj;
                AudioBufFrame audioBufFrame2;
                if (audioBufFrame.format.nativeModule != 0 && this.a.getNativeInstance() != 0) {
                    if (audioBufFrame.buf != null) {
                        AudioBufFormat audioBufFormat = audioBufFrame.format;
                        this.a.g = this.a.g + ((long) audioBufFrame.buf.limit());
                        int d = (int) (((((this.a.g / ((long) (audioBufFormat.channels * 2))) * ((long) this.a.e.sampleRate)) / ((long) audioBufFormat.sampleRate)) * ((long) (this.a.e.channels * 2))) - this.a.h);
                        this.a.h = this.a.h + ((long) d);
                        ByteBuffer byteBuffer = audioBufFrame.buf;
                        if (byteBuffer.capacity() < d) {
                            if (this.a.f == null || this.a.f.capacity() < d) {
                                this.a.f = ByteBuffer.allocateDirect(d);
                                this.a.f.order(ByteOrder.nativeOrder());
                            }
                            byteBuffer = this.a.f;
                        }
                        d = this.a.readNative(byteBuffer, d);
                        if (d <= 0) {
                            Log.e(AudioFilterBase.a, this.a.getClass().getSimpleName() + " readNative failed ret=" + d);
                        }
                        AudioBufFrame audioBufFrame3 = new AudioBufFrame(audioBufFrame);
                        audioBufFrame3.format = this.a.e;
                        audioBufFrame3.buf = byteBuffer;
                        obj = audioBufFrame3;
                    } else {
                        audioBufFrame2 = audioBufFrame;
                    }
                    if ((audioBufFrame.flags & AVFrameBase.FLAG_DETACH_NATIVE_MODULE) != 0) {
                        this.a.attachTo(0, audioBufFrame.format.nativeModule, true);
                    }
                } else if (audioBufFrame.buf != null) {
                    if (!audioBufFrame.buf.isDirect()) {
                        Log.e(AudioFilterBase.a, "input frame must use direct ByteBuffer");
                    }
                    obj = this.a.doFilter(audioBufFrame);
                } else {
                    audioBufFrame2 = audioBufFrame;
                }
                if (obj != null) {
                    if (obj == audioBufFrame) {
                        obj = new AudioBufFrame(audioBufFrame);
                        obj.format = this.a.e;
                    }
                    this.a.c.onFrameAvailable(obj);
                }
            }
        }

        public void onDisconnect(boolean z) {
            if (z) {
                this.a.release();
            }
        }
    }

    protected abstract AudioBufFrame doFilter(AudioBufFrame audioBufFrame);

    protected abstract AudioBufFormat doFormatChanged(AudioBufFormat audioBufFormat);

    public AudioFilterBase() {
        this.b = new a();
        this.c = new c();
    }

    public SinkPin<AudioBufFrame> getSinkPin() {
        return this.b;
    }

    public SrcPin<AudioBufFrame> getSrcPin() {
        return this.c;
    }

    protected long getNativeInstance() {
        return 0;
    }

    protected int readNative(ByteBuffer byteBuffer, int i) {
        return 0;
    }

    protected void attachTo(int i, long j, boolean z) {
    }

    protected AudioBufFormat getOutFormat() {
        return null;
    }

    protected void doRelease() {
    }

    public void release() {
        this.c.disconnect(true);
        doRelease();
    }
}
