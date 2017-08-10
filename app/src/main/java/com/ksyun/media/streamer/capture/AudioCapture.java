package com.ksyun.media.streamer.capture;

import android.content.Context;
import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import com.ksyun.media.streamer.capture.audio.KSYAudioSLRecord;
import com.ksyun.media.streamer.capture.audio.b;
import com.ksyun.media.streamer.capture.audio.c;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.kit.OnAudioRawDataListener;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class AudioCapture {
    public static final int AUDIO_CAPTURE_TYPE_AUDIORECORDER = 1;
    public static final int AUDIO_CAPTURE_TYPE_DUMMY = 3;
    public static final int AUDIO_CAPTURE_TYPE_OPENSLES = 2;
    public static final int AUDIO_ERROR_UNKNOWN = -2005;
    public static final int AUDIO_START_FAILED = -2003;
    public static final int STATE_IDLE = 0;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_RECORDING = 2;
    private static final String a = "AudioCapture";
    private static final boolean b = false;
    private int c;
    private int d;
    private int e;
    private float f;
    private Context g;
    private com.ksyun.media.streamer.capture.audio.a h;
    private Thread i;
    private Handler j;
    private int k;
    private boolean l;
    private volatile boolean m;
    public SrcPin<AudioBufFrame> mSrcPin;
    private OnAudioCaptureListener n;
    private OnAudioRawDataListener o;
    private short[] p;

    /* renamed from: com.ksyun.media.streamer.capture.AudioCapture.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ AudioCapture b;

        AnonymousClass2(AudioCapture audioCapture, int i) {
            this.b = audioCapture;
            this.a = i;
        }

        public void run() {
            StatsLogReport.getInstance().reportError(this.a, AudioCapture.STATE_INITIALIZED);
            if (this.b.n != null) {
                this.b.n.onError(this.a);
            }
        }
    }

    public interface OnAudioCaptureListener {
        void onError(int i);

        void onStatusChanged(int i);
    }

    private class a extends Thread {
        final /* synthetic */ AudioCapture a;

        private a(AudioCapture audioCapture) {
            this.a = audioCapture;
        }

        public void run() {
            Process.setThreadPriority(-19);
            try {
                int a;
                int i;
                switch (this.a.e) {
                    case AudioCapture.STATE_RECORDING /*2*/:
                        a = com.ksyun.media.streamer.util.audio.a.a(this.a.g, this.a.c);
                        i = a;
                        while (i < (this.a.c * 20) / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD) {
                            i += a;
                        }
                        this.a.h = new KSYAudioSLRecord(this.a.c, this.a.d, a);
                        break;
                    case AudioCapture.AUDIO_CAPTURE_TYPE_DUMMY /*3*/:
                        a = (this.a.c * 10) / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
                        i = a * AudioCapture.STATE_RECORDING;
                        this.a.h = new b(this.a.c, this.a.d, a);
                        break;
                    default:
                        i = AudioRecord.getMinBufferSize(this.a.c, this.a.d == AudioCapture.STATE_INITIALIZED ? 16 : 12, AudioCapture.STATE_RECORDING) / (this.a.d * AudioCapture.STATE_RECORDING);
                        this.a.h = new c(this.a.c, this.a.d, i);
                        a = i;
                        break;
                }
                this.a.a((int) AudioCapture.STATE_INITIALIZED);
                i *= this.a.d * AudioCapture.STATE_RECORDING;
                Log.i(AudioCapture.a, "atomSize:" + a);
                Log.i(AudioCapture.a, "readSize:" + i);
                Log.i(AudioCapture.a, "sampleRate:" + this.a.c);
                Log.i(AudioCapture.a, "channels:" + this.a.d);
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i);
                allocateDirect.order(ByteOrder.nativeOrder());
                AudioBufFormat audioBufFormat = new AudioBufFormat(AudioCapture.STATE_INITIALIZED, this.a.c, this.a.d);
                if (this.a.c == com.ksyun.media.streamer.util.audio.a.a(this.a.g)) {
                    audioBufFormat.nativeModule = this.a.h.d();
                }
                this.a.mSrcPin.onFormatChanged(audioBufFormat);
                this.a.h.a(this.a.l);
                this.a.h.a(this.a.f);
                if (this.a.h.a() != 0) {
                    Log.e(AudioCapture.a, "start recording failed!");
                    this.a.b((int) AudioCapture.AUDIO_START_FAILED);
                    this.a.h.c();
                    this.a.a((int) AudioCapture.STATE_IDLE);
                    return;
                }
                AudioBufFrame audioBufFrame;
                com.ksyun.media.streamer.capture.audio.a g;
                this.a.a((int) AudioCapture.STATE_RECORDING);
                while (!this.a.m) {
                    int a2 = this.a.h.a(allocateDirect, i);
                    if (!this.a.m) {
                        if (a2 > 0) {
                            long nanoTime = ((System.nanoTime() / 1000) - ((long) (((a2 / AudioCapture.STATE_RECORDING) / this.a.d) / this.a.c))) / 1000;
                            if (this.a.o != null) {
                                a2 = allocateDirect.limit() / AudioCapture.STATE_RECORDING;
                                if (this.a.p == null || this.a.p.length < a2) {
                                    this.a.p = new short[a2];
                                }
                                ShortBuffer asShortBuffer = allocateDirect.asShortBuffer();
                                asShortBuffer.get(this.a.p, AudioCapture.STATE_IDLE, a2);
                                short[] OnAudioRawData = this.a.o.OnAudioRawData(this.a.p, a2, this.a.c, this.a.d);
                                asShortBuffer.clear();
                                asShortBuffer.put(OnAudioRawData, AudioCapture.STATE_IDLE, a2);
                                asShortBuffer.rewind();
                            }
                            this.a.mSrcPin.onFrameAvailable(new AudioBufFrame(audioBufFormat, allocateDirect, nanoTime));
                        } else if (a2 < 0) {
                            Log.e(AudioCapture.a, "read error: " + a2);
                            this.a.b((int) AudioCapture.AUDIO_ERROR_UNKNOWN);
                        }
                    }
                    audioBufFrame = new AudioBufFrame(audioBufFormat, null, 0);
                    audioBufFrame.flags |= AVFrameBase.FLAG_DETACH_NATIVE_MODULE;
                    this.a.mSrcPin.onFrameAvailable(audioBufFrame);
                    this.a.h.b();
                    this.a.a((int) AudioCapture.STATE_INITIALIZED);
                    g = this.a.h;
                    this.a.h = null;
                    g.c();
                    this.a.a((int) AudioCapture.STATE_IDLE);
                }
                audioBufFrame = new AudioBufFrame(audioBufFormat, null, 0);
                audioBufFrame.flags |= AVFrameBase.FLAG_DETACH_NATIVE_MODULE;
                this.a.mSrcPin.onFrameAvailable(audioBufFrame);
                this.a.h.b();
                this.a.a((int) AudioCapture.STATE_INITIALIZED);
                g = this.a.h;
                this.a.h = null;
                g.c();
                this.a.a((int) AudioCapture.STATE_IDLE);
            } catch (Exception e) {
                e.printStackTrace();
                this.a.b((int) AudioCapture.AUDIO_START_FAILED);
            }
        }
    }

    public AudioCapture(Context context) {
        this.c = StreamerConstants.DEFAULT_AUDIO_SAMPLE_RATE;
        this.d = STATE_INITIALIZED;
        this.e = STATE_INITIALIZED;
        this.f = 1.0f;
        this.g = context;
        this.c = com.ksyun.media.streamer.util.audio.a.a(this.g);
        this.d = STATE_INITIALIZED;
        this.mSrcPin = new com.ksyun.media.streamer.filter.audio.c();
        this.j = new Handler(Looper.getMainLooper());
        this.k = STATE_IDLE;
    }

    public void setAudioCaptureType(int i) {
        if (isRecordingState() && this.e != i) {
            Log.d(a, "switch audio capture type from " + this.e + " to " + i);
            stop();
            start();
        }
        this.e = i;
    }

    public void setSampleRate(int i) {
        this.c = i;
    }

    public void setChannels(int i) {
        this.d = i;
    }

    public int getSampleRate() {
        return this.c;
    }

    public int getChannels() {
        return this.d;
    }

    public void setVolume(float f) {
        this.f = f;
        if (this.h != null) {
            this.h.a(f);
        }
    }

    public float getVolume() {
        return this.f;
    }

    public void setAudioCaptureListener(OnAudioCaptureListener onAudioCaptureListener) {
        this.n = onAudioCaptureListener;
    }

    @Deprecated
    public void setOnAudioRawDataListener(OnAudioRawDataListener onAudioRawDataListener) {
        this.o = onAudioRawDataListener;
    }

    public SrcPin<AudioBufFrame> getSrcPin() {
        return this.mSrcPin;
    }

    public boolean isRecordingState() {
        return this.i != null;
    }

    public void start() {
        if (this.i == null) {
            Log.d(a, "start");
            this.m = false;
            this.i = new a();
            this.i.start();
        }
    }

    public void stop() {
        if (this.i != null) {
            Log.d(a, "stop");
            this.m = true;
            this.i.interrupt();
            try {
                this.i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.i = null;
            }
        }
    }

    public void setEnableLatencyTest(boolean z) {
        this.l = z;
        if (this.h != null) {
            this.h.a(z);
        }
    }

    public boolean getEnableLatencyTest() {
        return this.l;
    }

    public void release() {
        stop();
        this.p = null;
        this.j.removeCallbacksAndMessages(null);
        this.j = null;
        this.mSrcPin.disconnect(true);
    }

    private void a(int i) {
        this.k = i;
        this.j.post(new Runnable() {
            final /* synthetic */ AudioCapture a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.n != null) {
                    this.a.n.onStatusChanged(this.a.k);
                }
            }
        });
    }

    private void b(int i) {
        this.j.post(new AnonymousClass2(this, i));
    }
}
