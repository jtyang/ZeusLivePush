package com.ksyun.media.streamer.capture;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.IMediaPlayer.OnCompletionListener;
import com.ksyun.media.player.IMediaPlayer.OnErrorListener;
import com.ksyun.media.player.IMediaPlayer.OnPreparedListener;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer.Builder;
import com.ksyun.media.player.KSYMediaPlayer.OnAudioPCMListener;
import com.ksyun.media.player.d.d;
import com.ksyun.media.streamer.filter.audio.AudioFilterMgt;
import com.ksyun.media.streamer.filter.audio.AudioSLPlayer;
import com.ksyun.media.streamer.filter.audio.c;
import com.ksyun.media.streamer.filter.audio.e;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.audio.a;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AudioPlayerCapture {
    public static final int AUDIO_PLAYER_TYPE_AUDIOTRACK = 0;
    public static final int AUDIO_PLAYER_TYPE_OPENSLES = 1;
    private static final String a = "AudioPlayerCapture";
    private static final boolean b = true;
    private static final String c = "assets://";
    private OnAudioPCMListener A;
    private AudioFilterMgt d;
    private Context e;
    private KSYMediaPlayer f;
    private e g;
    private AudioBufFormat h;
    private ByteBuffer i;
    private int j;
    private boolean k;
    private boolean l;
    private float m;
    public SrcPin<AudioBufFrame> mSrcPin;
    private boolean n;
    private long o;
    private long p;
    private long q;
    private PlayRanges r;
    private String s;
    private boolean t;
    private OnPreparedListener u;
    private OnCompletionListener v;
    private OnErrorListener w;
    private OnPreparedListener x;
    private OnCompletionListener y;
    private OnErrorListener z;

    public class PlayRanges {
        public long endTime;
        public long startTime;
    }

    public AudioPlayerCapture(Context context) {
        this.j = AUDIO_PLAYER_TYPE_AUDIOTRACK;
        this.l = false;
        this.m = 1.0f;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        this.x = new OnPreparedListener() {
            final /* synthetic */ AudioPlayerCapture a;

            {
                this.a = r1;
            }

            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.d(AudioPlayerCapture.a, d.aw);
                if (this.a.u != null) {
                    this.a.u.onPrepared(iMediaPlayer);
                }
                this.a.o = iMediaPlayer.getDuration();
                this.a.h = null;
                this.a.i.clear();
                iMediaPlayer.start();
            }
        };
        this.y = new OnCompletionListener() {
            final /* synthetic */ AudioPlayerCapture a;

            {
                this.a = r1;
            }

            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.e(AudioPlayerCapture.a, "onCompletion");
                if (!this.a.t) {
                    this.a.a();
                }
                if (this.a.v != null) {
                    this.a.v.onCompletion(iMediaPlayer);
                }
            }
        };
        this.z = new OnErrorListener() {
            final /* synthetic */ AudioPlayerCapture a;

            {
                this.a = r1;
            }

            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
                Log.d(AudioPlayerCapture.a, "onError:" + i + "/" + i2);
                return (this.a.w == null || !this.a.w.onError(iMediaPlayer, i, i2)) ? false : AudioPlayerCapture.b;
            }
        };
        this.A = new OnAudioPCMListener() {
            final /* synthetic */ AudioPlayerCapture a;

            {
                this.a = r1;
            }

            public void onAudioPCMAvailable(IMediaPlayer iMediaPlayer, ByteBuffer byteBuffer, long j, int i, int i2, int i3) {
                if (byteBuffer != null) {
                    if (this.a.k) {
                        this.a.k = false;
                        if ((this.a.j == AudioPlayerCapture.AUDIO_PLAYER_TYPE_OPENSLES && (this.a.g instanceof com.ksyun.media.streamer.filter.audio.d)) || (this.a.j == 0 && (this.a.g instanceof AudioSLPlayer))) {
                            if (this.a.h != null) {
                                AudioBufFrame audioBufFrame = new AudioBufFrame(this.a.h, null, 0);
                                audioBufFrame.flags |= AVFrameBase.FLAG_DETACH_NATIVE_MODULE;
                                this.a.mSrcPin.onFrameAvailable(audioBufFrame);
                            }
                            e a = this.a.g;
                            this.a.g = null;
                            a.c();
                            a.d();
                            if (this.a.j == AudioPlayerCapture.AUDIO_PLAYER_TYPE_OPENSLES) {
                                this.a.g = new AudioSLPlayer();
                            } else {
                                this.a.g = new com.ksyun.media.streamer.filter.audio.d();
                            }
                            this.a.g.b(this.a.l);
                            this.a.h = null;
                            this.a.i.clear();
                        }
                    }
                    if (this.a.h == null) {
                        this.a.g.a(i2, i, a.a(this.a.e, i2), 40);
                        this.a.g.b();
                        this.a.h = new AudioBufFormat(i3, i2, i);
                        this.a.d.getSinkPin().onFormatChanged(this.a.h);
                    }
                    int position = this.a.i.position() + byteBuffer.limit();
                    if (this.a.i.capacity() < position) {
                        Log.d(AudioPlayerCapture.a, "expand buffer to " + position + " bytes");
                        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(position);
                        allocateDirect.order(ByteOrder.nativeOrder());
                        allocateDirect.put(this.a.i);
                        this.a.i = allocateDirect;
                    }
                    this.a.i.put(byteBuffer);
                    if (this.a.i.position() >= i * 2048) {
                        this.a.i.flip();
                        this.a.d.getSinkPin().onFrameAvailable(new AudioBufFrame(this.a.h, this.a.i, j));
                        this.a.i.clear();
                    }
                }
            }
        };
        this.e = context;
        this.mSrcPin = new c();
        this.d = new AudioFilterMgt();
        this.d.getSrcPin().connect(new SinkPin<AudioBufFrame>() {
            AudioBufFormat a;
            final /* synthetic */ AudioPlayerCapture b;

            {
                this.b = r2;
                this.a = null;
            }

            public /* synthetic */ void onFrameAvailable(Object obj) {
                a((AudioBufFrame) obj);
            }

            public void onFormatChanged(Object obj) {
                this.a = new AudioBufFormat((AudioBufFormat) obj);
                this.a.nativeModule = this.b.g.a();
                this.b.mSrcPin.onFormatChanged(this.a);
            }

            public void a(AudioBufFrame audioBufFrame) {
                if (audioBufFrame.buf != null && audioBufFrame.buf.limit() > 0) {
                    if (!this.b.n) {
                        this.b.g.a(audioBufFrame.buf);
                    }
                    AudioBufFrame audioBufFrame2 = new AudioBufFrame(audioBufFrame);
                    audioBufFrame2.format = this.a;
                    this.b.mSrcPin.onFrameAvailable(audioBufFrame2);
                }
            }
        });
    }

    public KSYMediaPlayer getMediaPlayer() {
        if (this.f == null) {
            this.f = new Builder(this.e).build();
            if (this.j == AUDIO_PLAYER_TYPE_OPENSLES) {
                this.g = new AudioSLPlayer();
            } else {
                this.g = new com.ksyun.media.streamer.filter.audio.d();
            }
            this.f.setOnErrorListener(this.z);
            this.i = ByteBuffer.allocateDirect(8192);
            this.i.order(ByteOrder.nativeOrder());
        }
        return this.f;
    }

    public void setAudioPlayerType(int i) {
        this.k = this.j != i ? b : false;
        this.j = i;
    }

    public void setMute(boolean z) {
        if (this.g != null) {
            this.g.b(z);
        }
        this.l = z;
    }

    public void setVolume(float f) {
        this.m = f;
        if (this.f != null) {
            this.f.setVolume(f, f);
        }
    }

    public float getVolume() {
        return this.m;
    }

    public void setEnableFastPlay(boolean z) {
        this.n = z;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.u = onPreparedListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.v = onCompletionListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.w = onErrorListener;
    }

    public void setPlayableRanges(long j, long j2) {
        this.p = j;
        this.q = j2;
        if (this.r == null) {
            this.r = new PlayRanges();
        }
        this.r.startTime = this.p;
        this.r.endTime = this.q;
    }

    public PlayRanges getPlayableRanges() {
        return this.r;
    }

    public long getFileDuration() {
        return this.o;
    }

    public void start(String str, boolean z) {
        getMediaPlayer();
        this.f.reset();
        this.f.setOnPreparedListener(this.x);
        this.f.setOnCompletionListener(this.y);
        this.f.setOnAudioPCMAvailableListener(this.A);
        this.t = z;
        this.f.setLooping(z);
        this.f.shouldAutoPlay(false);
        this.f.enableFastPlayMode(b);
        this.s = str;
        if (this.p >= 0 && this.q > this.p) {
            this.f.setPlayableRanges(this.p, this.q);
        }
        a(this.s);
        this.f.setVolume(this.m, this.m);
        this.g.b(this.l);
        StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_BGM);
    }

    public void stop() {
        if (this.f != null) {
            Log.d(a, "stop");
            this.f.setOnAudioPCMAvailableListener(null);
            this.f.stop();
            this.g.c();
            a();
        }
    }

    public void restart() {
        if (this.f != null) {
            this.f.stop();
            this.f.reset();
            this.f.setOnPreparedListener(this.x);
            this.f.setOnCompletionListener(this.y);
            this.f.setOnAudioPCMAvailableListener(this.A);
            this.f.setLooping(this.t);
            this.f.shouldAutoPlay(false);
            this.f.enableFastPlayMode(b);
            if (this.p >= 0 && this.q > this.p) {
                this.f.setPlayableRanges(this.p, this.q);
            }
            a(this.s);
            this.f.setVolume(this.m, this.m);
            this.g.b(this.l);
        }
    }

    private void a(String str) {
        try {
            if (!str.startsWith(c) || this.e == null) {
                this.f.setDataSource(str);
            } else {
                AssetFileDescriptor openFd = this.e.getAssets().openFd(str.substring(c.length()));
                this.f.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
            }
            this.f.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        stop();
        this.mSrcPin.disconnect(b);
        if (this.f != null) {
            this.f.release();
            this.f = null;
        }
        if (this.g != null) {
            this.g.d();
            this.g = null;
        }
        this.i = null;
    }

    private void a() {
        Log.e(a, "sendEos");
        if (this.h != null) {
            AudioBufFrame audioBufFrame = new AudioBufFrame(this.h, null, 0);
            audioBufFrame.flags |= AVFrameBase.FLAG_DETACH_NATIVE_MODULE;
            audioBufFrame.flags |= 4;
            this.mSrcPin.onFrameAvailable(audioBufFrame);
        }
    }

    public AudioFilterMgt getAudioFilterMgt() {
        return this.d;
    }
}
