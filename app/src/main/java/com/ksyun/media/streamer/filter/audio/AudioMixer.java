package com.ksyun.media.streamer.filter.audio;

import android.util.Log;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class AudioMixer {
    private static final String a = "AudioMixer";
    private static final int b = 8;
    private static final boolean c = false;
    private static final int d = 0;
    private List<SinkPin<AudioBufFrame>> e;
    private SrcPin<AudioBufFrame> f;
    private long g;
    private int h;
    private float[] i;
    private float j;
    private boolean k;
    private boolean l;
    private AudioBufFormat[] m;
    private AudioBufFormat n;

    private class a extends SinkPin<AudioBufFrame> {
        final /* synthetic */ AudioMixer a;
        private int b;

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((AudioBufFrame) obj);
        }

        public a(AudioMixer audioMixer, int i) {
            this.a = audioMixer;
            this.b = i;
        }

        public void onFormatChanged(Object obj) {
            this.a.a(this.b, (AudioBufFormat) obj);
        }

        public void a(AudioBufFrame audioBufFrame) {
            this.a.a(this.b, audioBufFrame);
        }

        public void onDisconnect(boolean z) {
            super.onDisconnect(z);
            this.a.a(this.b, z);
        }
    }

    private native void _attachTo(long j, int i, long j2, boolean z);

    private native int _config(long j, int i, int i2, int i3, int i4, int i5);

    private native void _destroy(long j, int i);

    private native long _init();

    private native int _process(long j, int i, ByteBuffer byteBuffer, int i2);

    private native int _read(long j, ByteBuffer byteBuffer, int i);

    private native void _release(long j);

    private native void _setBlockingMode(long j, boolean z);

    private native void _setInputVolume(long j, int i, float f);

    private native void _setMainIdx(long j, int i);

    private native void _setMute(long j, boolean z);

    private native void _setOutputVolume(long j, float f);

    public AudioMixer() {
        int i = 0;
        this.g = 0;
        this.h = 0;
        this.e = new LinkedList();
        this.f = new c();
        this.j = 1.0f;
        this.i = new float[getSinkPinNum()];
        while (i < getSinkPinNum()) {
            this.e.add(new a(this, i));
            this.i[i] = 1.0f;
            i++;
        }
        this.m = new AudioBufFormat[getSinkPinNum()];
        this.g = _init();
    }

    public SinkPin<AudioBufFrame> getSinkPin(int i) {
        if (this.e.size() > i) {
            return (SinkPin) this.e.get(i);
        }
        return null;
    }

    public SrcPin<AudioBufFrame> getSrcPin() {
        return this.f;
    }

    public final void setMainSinkPinIndex(int i) {
        this.h = i;
        _setMainIdx(this.g, i);
    }

    public void setInputVolume(int i, float f) {
        if (i < this.i.length) {
            this.i[i] = f;
            _setInputVolume(this.g, i, f);
        }
    }

    public float getInputVolume(int i) {
        if (i < this.i.length) {
            return this.i[i];
        }
        return 0.0f;
    }

    public void setOutputVolume(float f) {
        this.j = f;
        _setOutputVolume(this.g, f);
    }

    public float getOutputVolume() {
        return this.j;
    }

    public void setMute(boolean z) {
        this.k = z;
        _setMute(this.g, z);
    }

    public boolean getMute() {
        return this.k;
    }

    public void setBlockingMode(boolean z) {
        this.l = z;
        _setBlockingMode(this.g, z);
    }

    public boolean getBlockingMode() {
        return this.l;
    }

    public void clearBuffer() {
    }

    public void clearBuffer(int i) {
    }

    public int getSinkPinNum() {
        return b;
    }

    public int getEmptySinkPin() {
        for (int i = 0; i < getSinkPinNum(); i++) {
            if (!((SinkPin) this.e.get(i)).isConnected()) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void release() {
        a();
    }

    private void a() {
        this.f.disconnect(true);
        this.e.clear();
        if (this.g != 0) {
            _release(this.g);
            this.g = 0;
        }
    }

    protected synchronized void a(int i, boolean z) {
        if (this.g != 0) {
            _destroy(this.g, i);
        }
        if (i == this.h && z) {
            a();
        }
    }

    protected synchronized void a(int i, AudioBufFormat audioBufFormat) {
        if (audioBufFormat != null) {
            this.m[i] = audioBufFormat;
            Log.d(a, "doFormatChanged " + i + " nativeModule=" + audioBufFormat.nativeModule);
            if (audioBufFormat.nativeModule != 0) {
                _attachTo(this.g, i, audioBufFormat.nativeModule, false);
            } else {
                _config(this.g, i, audioBufFormat.sampleRate, audioBufFormat.channels, 1024, StatsConstant.DEFAULT_NETWORK_STAT_DELAY);
            }
            if (i == this.h) {
                this.n = new AudioBufFormat(audioBufFormat.sampleFormat, audioBufFormat.sampleRate, audioBufFormat.channels);
                if (audioBufFormat.nativeModule != 0) {
                    this.n.nativeModule = this.g;
                }
                this.f.onFormatChanged(this.n);
            }
        }
    }

    protected void a(int i, AudioBufFrame audioBufFrame) {
        if ((audioBufFrame.flags & AVFrameBase.FLAG_DETACH_NATIVE_MODULE) != 0) {
            if (audioBufFrame.format.nativeModule != 0) {
                _attachTo(this.g, i, audioBufFrame.format.nativeModule, true);
            }
            if (this.g != 0) {
                _destroy(this.g, i);
            }
        }
        if (!((audioBufFrame.flags & 4) == 0 || this.g == 0)) {
            _destroy(this.g, i);
        }
        if (!(audioBufFrame.buf == null || audioBufFrame.format.nativeModule != 0 || this.g == 0)) {
            _process(this.g, i, audioBufFrame.buf, audioBufFrame.buf.limit());
        }
        if (i == this.h) {
            if (!(audioBufFrame.buf == null || audioBufFrame.format.nativeModule == 0)) {
                int _read = _read(this.g, audioBufFrame.buf, audioBufFrame.buf.limit());
                if (_read <= 0) {
                    Log.e(a, "readNative failed ret=" + _read);
                }
            }
            AudioBufFrame audioBufFrame2 = new AudioBufFrame(audioBufFrame);
            audioBufFrame2.format = this.n;
            this.f.onFrameAvailable(audioBufFrame2);
        }
    }

    static {
        LibraryLoader.load();
    }
}
