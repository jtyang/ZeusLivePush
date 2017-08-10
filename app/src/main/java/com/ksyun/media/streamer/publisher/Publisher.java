package com.ksyun.media.streamer.publisher;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.streamer.framework.AVBufFrame;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.AVPacketUtil;
import com.ksyun.media.streamer.util.d;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Publisher {
    protected static final int CMD_RELEASE = 4;
    protected static final int CMD_START = 1;
    protected static final int CMD_STOP = 2;
    protected static final int CMD_WRITE_FRAME = 3;
    public static final int ERROR_AV_ASYNC_ERROR = -2004;
    public static final int INFO_AUDIO_HEADER_GOT = 2;
    public static final int INFO_STARTED = 1;
    public static final int INFO_STOPPED = 4;
    public static final int INFO_VIDEO_HEADER_GOT = 3;
    protected static final long INVALID_TS = Long.MIN_VALUE;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PUBLISHING = 2;
    public static final int STATE_STARTING = 1;
    public static final int STATE_STOPPING = 3;
    private static final String a = "Publisher";
    private static final boolean b = false;
    private static final int c = 10000;
    private static final int d = 4;
    private static final int e = 20;
    private static final int f = 204800;
    private static final int g = 2048;
    private d h;
    private d i;
    private final Object j;
    private final Object k;
    private ByteBuffer l;
    private ByteBuffer m;
    protected int mAFrameDropped;
    protected int mAudioBitrate;
    protected boolean mAudioTrackAdded;
    protected float mFramerate;
    protected long mInitDts;
    protected boolean mIsAudioOnly;
    protected boolean mIsPublishing;
    protected boolean mIsVideoOnly;
    protected long mLastAudioDts;
    protected long mLastVideoDts;
    protected final Handler mMainHandler;
    protected PubListener mPubListener;
    protected PublisherWrapper mPubWrapper;
    protected Handler mPublishHandler;
    protected HandlerThread mPublishThread;
    protected AtomicInteger mState;
    protected int mVFrameDroppedInner;
    protected int mVFrameDroppedUpper;
    protected int mVideoBitrate;
    protected boolean mVideoKeyFrameGot;
    protected long mVideoPts;
    protected boolean mVideoTrackAdded;
    private final Map<String, String> n;
    private boolean o;
    private ConditionVariable p;
    private ConditionVariable q;
    private ConditionVariable r;
    private String s;
    private boolean t;
    private boolean u;
    private boolean v;
    private SinkPin<ImgBufFrame> w;
    private SinkPin<AudioBufFrame> x;

    public interface PubListener {
        void onError(int i, long j);

        void onInfo(int i, long j);
    }

    /* renamed from: com.ksyun.media.streamer.publisher.Publisher.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ long b;
        final /* synthetic */ Publisher c;

        AnonymousClass2(Publisher publisher, int i, long j) {
            this.c = publisher;
            this.a = i;
            this.b = j;
        }

        public void run() {
            if (this.c.getPubListener() != null) {
                this.c.getPubListener().onInfo(this.a, this.b);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.publisher.Publisher.3 */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ long b;
        final /* synthetic */ Publisher c;

        AnonymousClass3(Publisher publisher, int i, long j) {
            this.c = publisher;
            this.a = i;
            this.b = j;
        }

        public void run() {
            if (this.c.getPubListener() != null) {
                this.c.getPubListener().onError(this.a, this.b);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.publisher.Publisher.4 */
    class AnonymousClass4 extends Handler {
        final /* synthetic */ Publisher a;

        AnonymousClass4(Publisher publisher, Looper looper) {
            this.a = publisher;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case Publisher.STATE_STARTING /*1*/:
                    if (this.a.mState.get() == 0) {
                        this.a.mState.set(Publisher.STATE_STARTING);
                        this.a.mAudioTrackAdded = Publisher.b;
                        this.a.mVideoTrackAdded = Publisher.b;
                        this.a.mVideoKeyFrameGot = Publisher.b;
                        this.a.v = Publisher.b;
                        this.a.u = Publisher.b;
                        this.a.r.close();
                        this.a.q.close();
                        int doStart = this.a.doStart((String) message.obj);
                        this.a.mState.set(doStart == 0 ? Publisher.STATE_PUBLISHING : Publisher.STATE_IDLE);
                        if (this.a.getAutoWork()) {
                            this.a.p.open();
                        }
                        if (doStart == 0) {
                            this.a.postInfo(Publisher.STATE_STARTING);
                        } else {
                            this.a.postError(doStart);
                        }
                    }
                case Publisher.STATE_PUBLISHING /*2*/:
                    if (this.a.mState.get() == Publisher.STATE_PUBLISHING) {
                        this.a.mState.set(Publisher.STATE_STOPPING);
                        synchronized (this.a.k) {
                            this.a.m = null;
                            this.a.l = null;
                            break;
                        }
                        synchronized (this.a.j) {
                            if (this.a.h != null) {
                                this.a.h.a();
                                this.a.h = null;
                            }
                            if (this.a.i != null) {
                                this.a.i.a();
                                this.a.i = null;
                            }
                            break;
                        }
                        this.a.doStop();
                        this.a.mState.set(Publisher.STATE_IDLE);
                        this.a.postInfo(Publisher.d);
                    }
                case Publisher.STATE_STOPPING /*3*/:
                    if (this.a.mState.get() == Publisher.STATE_PUBLISHING) {
                        this.a.doWriteFrame(message.obj);
                        return;
                    }
                    Log.e(Publisher.a, "Please start publisher before encoder, or memory leak may be occured!");
                    this.a.a(message.obj);
                case Publisher.d /*4*/:
                    this.a.doRelease();
                    ((HandlerThread) message.obj).quit();
                default:
            }
        }
    }

    private class a extends SinkPin<AudioBufFrame> {
        final /* synthetic */ Publisher a;

        private a(Publisher publisher) {
            this.a = publisher;
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((AudioBufFrame) obj);
        }

        public void onFormatChanged(Object obj) {
            this.a.b(obj);
        }

        public void a(AudioBufFrame audioBufFrame) {
            this.a.handleAVFrame(audioBufFrame);
        }

        public void onDisconnect(boolean z) {
            if (z && !this.a.mIsVideoOnly) {
                this.a.release();
            }
        }
    }

    private class b extends SinkPin<ImgBufFrame> {
        final /* synthetic */ Publisher a;

        private b(Publisher publisher) {
            this.a = publisher;
        }

        public /* synthetic */ void onFrameAvailable(Object obj) {
            a((ImgBufFrame) obj);
        }

        public void onFormatChanged(Object obj) {
            this.a.b(obj);
        }

        public void a(ImgBufFrame imgBufFrame) {
            this.a.handleAVFrame(imgBufFrame);
        }

        public void onDisconnect(boolean z) {
            if (z && this.a.mIsVideoOnly) {
                this.a.release();
            }
        }
    }

    protected abstract boolean IsAddExtraForVideoKeyFrame();

    public Publisher(String str) {
        this.j = new Object();
        this.mVideoPts = 0;
        this.k = new Object();
        this.o = b;
        this.p = new ConditionVariable();
        this.q = new ConditionVariable();
        this.r = new ConditionVariable();
        this.t = b;
        this.w = new b();
        this.x = new a();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mState = new AtomicInteger(STATE_IDLE);
        a(str);
        this.mPubWrapper = new PublisherWrapper();
        this.mPubWrapper.a(new com.ksyun.media.streamer.publisher.PublisherWrapper.a() {
            final /* synthetic */ Publisher a;

            {
                this.a = r1;
            }

            public void a(int i, long j) {
                this.a.postInfo(i, j);
            }
        });
        this.n = new LinkedHashMap();
    }

    public void setPubListener(PubListener pubListener) {
        this.mPubListener = pubListener;
    }

    public PubListener getPubListener() {
        return this.mPubListener;
    }

    public boolean isPublishing() {
        return this.mIsPublishing;
    }

    public SinkPin<ImgBufFrame> getVideoSink() {
        return this.w;
    }

    public SinkPin<AudioBufFrame> getAudioSink() {
        return this.x;
    }

    public void setVideoBitrate(int i) {
        this.mVideoBitrate = i;
    }

    public void setAudioBitrate(int i) {
        this.mAudioBitrate = i;
    }

    public void setFramerate(float f) {
        this.mFramerate = f;
    }

    public void setAudioOnly(boolean z) {
        if (this.mIsVideoOnly && z) {
            throw new IllegalArgumentException("audioOnly and videoOnly both be true");
        }
        this.mIsAudioOnly = z;
        this.mPubWrapper.a(z);
    }

    public boolean isAudioOnly() {
        return this.mIsAudioOnly;
    }

    public void setVideoOnly(boolean z) {
        if (this.mIsAudioOnly && z) {
            throw new IllegalArgumentException("audioOnly and videoOnly both be true");
        }
        this.mIsVideoOnly = z;
        this.mPubWrapper.b(z);
    }

    public boolean isVideoOnly() {
        return this.mIsVideoOnly;
    }

    public boolean getUseSyncMode() {
        return this.o;
    }

    public void setUseSyncMode(boolean z) {
        this.o = z;
    }

    public boolean getAutoWork() {
        return this.t;
    }

    public void setAutoWork(boolean z) {
        this.t = z;
    }

    public void setAudioExtra(AVBufFrame aVBufFrame) {
        a(aVBufFrame);
    }

    public void setVideoExtra(AVBufFrame aVBufFrame) {
        a(aVBufFrame);
    }

    private void a(AVBufFrame aVBufFrame) {
        if (aVBufFrame != null && aVBufFrame.buf != null) {
            handleAVFrame(aVBufFrame);
        }
    }

    public String getUrl() {
        return this.s;
    }

    public void setUrl(String str) {
        this.s = str;
    }

    public void addMetaOption(String str, String str2) {
        synchronized (this.n) {
            this.n.put(str, str2);
        }
    }

    public int getVideoCacheLength() {
        if (this.mPubWrapper == null) {
            return STATE_IDLE;
        }
        return this.mPubWrapper.a(10);
    }

    public boolean start(String str) {
        if (this.mState.get() != 0 && this.mState.get() != STATE_STOPPING) {
            Log.e(a, "startRecording on invalid state");
            return b;
        } else if (TextUtils.isEmpty(str)) {
            Log.e(a, "uri is empty");
            return b;
        } else {
            this.mIsPublishing = true;
            this.mInitDts = INVALID_TS;
            this.mLastVideoDts = INVALID_TS;
            this.mLastAudioDts = INVALID_TS;
            this.mVideoPts = 0;
            this.mAFrameDropped = STATE_IDLE;
            this.mVFrameDroppedUpper = STATE_IDLE;
            this.mVFrameDroppedInner = STATE_IDLE;
            this.s = str;
            if (this.mPublishThread == null) {
                return b;
            }
            this.mPublishHandler.sendMessage(this.mPublishHandler.obtainMessage(STATE_STARTING, str));
            return true;
        }
    }

    protected int doStart(String str) {
        int a = this.mPubWrapper.a(str);
        if (a == 0) {
            synchronized (this.n) {
                for (String str2 : this.n.keySet()) {
                    this.mPubWrapper.a(str2, (String) this.n.get(str2));
                }
            }
        }
        return a;
    }

    private int a(ImgBufFormat imgBufFormat, ByteBuffer byteBuffer) {
        int i = STATE_IDLE;
        switch (imgBufFormat.format) {
            case StreamerConstants.CODEC_ID_AAC /*256*/:
                i = STATE_STARTING;
                break;
            case ImgBufFormat.FMT_HEVC /*257*/:
                i = STATE_PUBLISHING;
                break;
            case ImgBufFormat.FMT_GIF /*258*/:
                i = STATE_STOPPING;
                break;
        }
        return addVideoTrack(i, imgBufFormat.width, imgBufFormat.height, this.mFramerate, this.mVideoBitrate, byteBuffer);
    }

    public int addVideoTrack(int i, int i2, int i3, float f, int i4, ByteBuffer byteBuffer) {
        return this.mPubWrapper.a(i, i2, i3, f, i4, byteBuffer);
    }

    public int addAudioTrack(AudioBufFormat audioBufFormat, ByteBuffer byteBuffer) {
        return this.mPubWrapper.a(audioBufFormat.codecId, audioBufFormat.sampleFormat, audioBufFormat.sampleRate, audioBufFormat.channels, this.mAudioBitrate, byteBuffer);
    }

    protected int doWriteFrame(Object obj) {
        int i = STATE_IDLE;
        if (obj instanceof AudioBufFrame) {
            AudioBufFrame audioBufFrame = (AudioBufFrame) obj;
            if ((audioBufFrame.flags & STATE_PUBLISHING) == 0) {
                if (!(this.mAudioTrackAdded || this.m == null)) {
                    postInfo(STATE_PUBLISHING);
                    synchronized (this.k) {
                        addAudioTrack(audioBufFrame.format, this.m);
                    }
                    this.mAudioTrackAdded = true;
                    this.r.open();
                }
                i = writeFrame(STATE_STARTING, audioBufFrame.avPacketOpaque, audioBufFrame.buf, audioBufFrame.dts, audioBufFrame.pts, audioBufFrame.flags);
            } else if (!this.mAudioTrackAdded) {
                postInfo(STATE_PUBLISHING);
                i = addAudioTrack(audioBufFrame.format, audioBufFrame.buf);
                this.mAudioTrackAdded = true;
                this.r.open();
            }
            synchronized (this.j) {
                if (this.h != null) {
                    this.h.a(audioBufFrame.buf);
                }
            }
        } else if (obj instanceof ImgBufFrame) {
            ImgBufFrame imgBufFrame = (ImgBufFrame) obj;
            if ((imgBufFrame.flags & STATE_PUBLISHING) != 0) {
                if (!this.mVideoTrackAdded) {
                    postInfo(STATE_STOPPING);
                    this.mVideoTrackAdded = true;
                }
                i = a(imgBufFrame.format, imgBufFrame.buf);
                this.q.open();
            } else {
                if (!this.mVideoTrackAdded && (this.l != null || imgBufFrame.format.format == ImgBufFormat.FMT_GIF)) {
                    postInfo(STATE_STOPPING);
                    synchronized (this.k) {
                        a(imgBufFrame.format, this.l);
                    }
                    this.mVideoTrackAdded = true;
                    this.q.open();
                }
                i = writeFrame(STATE_PUBLISHING, imgBufFrame.avPacketOpaque, imgBufFrame.buf, imgBufFrame.dts, imgBufFrame.pts, imgBufFrame.flags);
            }
            synchronized (this.j) {
                if (this.i != null) {
                    this.i.a(imgBufFrame.buf);
                }
            }
        }
        return i;
    }

    public int writeFrame(int i, long j, ByteBuffer byteBuffer, long j2, long j3, int i2) {
        return this.mPubWrapper.a(i, j, byteBuffer, j2, j3, i2);
    }

    private void a(Object obj) {
        if (obj instanceof AudioBufFrame) {
            AudioBufFrame audioBufFrame = (AudioBufFrame) obj;
            synchronized (this.j) {
                if (this.h != null) {
                    this.h.a(audioBufFrame.buf);
                }
            }
        } else if (obj instanceof ImgBufFrame) {
            ImgBufFrame imgBufFrame = (ImgBufFrame) obj;
            synchronized (this.j) {
                if (this.i != null) {
                    this.i.a(imgBufFrame.buf);
                }
            }
        }
    }

    public void stop() {
        if (this.mState.get() != 0 && this.mState.get() != STATE_STOPPING) {
            this.mIsPublishing = b;
            this.r.open();
            this.q.open();
            if (this.mState.get() == STATE_STARTING) {
                Log.d(a, "abort connecting...");
                this.mPubWrapper.a();
            }
            if (this.mPublishThread != null) {
                this.mPublishHandler.sendEmptyMessage(STATE_PUBLISHING);
            }
            synchronized (this.n) {
                this.n.clear();
            }
        }
    }

    protected void doStop() {
        this.mPubWrapper.c();
    }

    public void release() {
        if (this.mPublishThread != null) {
            this.mPublishHandler.sendMessage(this.mPublishHandler.obtainMessage(d, this.mPublishThread));
            this.mPublishThread = null;
        }
    }

    protected void doRelease() {
        if (this.mPubWrapper != null) {
            this.mPubWrapper.b();
            this.mPubWrapper = null;
        }
    }

    protected void postInfo(int i) {
        postInfo(i, 0);
    }

    protected void postInfo(int i, long j) {
        this.mMainHandler.post(new AnonymousClass2(this, i, j));
    }

    protected void postError(int i) {
        postError(i, 0);
    }

    protected void postError(int i, long j) {
        if (isPublishing()) {
            this.mMainHandler.post(new AnonymousClass3(this, i, j));
        }
    }

    private void a(String str) {
        this.mPublishThread = new HandlerThread(str + "thread");
        this.mPublishThread.start();
        this.mPublishHandler = new AnonymousClass4(this, this.mPublishThread.getLooper());
    }

    protected boolean isUseFFmpeg() {
        return true;
    }

    protected void handleAVFrame(AVBufFrame aVBufFrame) {
        if (getAutoWork()) {
            this.p.block();
        }
        if (this.mState.get() == STATE_PUBLISHING && aVBufFrame != null) {
            AVBufFrame aVBufFrame2 = null;
            if (aVBufFrame instanceof AudioBufFrame) {
                aVBufFrame2 = new AudioBufFrame((AudioBufFrame) aVBufFrame);
            } else if (aVBufFrame instanceof ImgBufFrame) {
                aVBufFrame2 = new ImgBufFrame((ImgBufFrame) aVBufFrame);
            }
            a(aVBufFrame2, IsAddExtraForVideoKeyFrame());
        }
    }

    private void a(AVBufFrame aVBufFrame, boolean z) {
        boolean z2;
        String str;
        if (aVBufFrame instanceof AudioBufFrame) {
            z2 = b;
            str = KSYMediaMeta.IJKM_VAL_TYPE__AUDIO;
        } else if (aVBufFrame instanceof ImgBufFrame) {
            str = KSYMediaMeta.IJKM_VAL_TYPE__VIDEO;
            z2 = true;
        } else {
            Log.i(a, "got unknown type frame, dropped");
            return;
        }
        if (getUseSyncMode() && (aVBufFrame.flags & STATE_PUBLISHING) == 0) {
            if (!z2 && !isAudioOnly() && !this.mVideoTrackAdded) {
                this.q.block();
            } else if (!(!z2 || isVideoOnly() || this.mAudioTrackAdded)) {
                this.r.block();
            }
        }
        if ((aVBufFrame.flags & d) != 0 && getAutoWork()) {
            if (z2) {
                this.u = true;
            } else {
                this.v = true;
            }
            Log.d(a, str + " EOS got");
            if ((this.mIsVideoOnly && this.u) || ((this.mIsAudioOnly && this.v) || (this.u && this.v))) {
                stop();
            }
        } else if ((aVBufFrame.buf != null && aVBufFrame.buf.limit() != 0) || (aVBufFrame.flags & STATE_PUBLISHING) != 0) {
            if (z2 && !this.mVideoKeyFrameGot && aVBufFrame.flags == 0) {
                Log.d(a, "drop non-key frame");
                return;
            }
            ByteBuffer byteBuffer;
            if ((aVBufFrame.flags & STATE_PUBLISHING) == 0) {
                long abs;
                if (this.mIsAudioOnly || this.mIsVideoOnly) {
                    if (this.mIsAudioOnly) {
                        this.mVideoPts = 0;
                    }
                } else if (z2) {
                    this.mVideoPts = aVBufFrame.pts;
                } else if (this.mVideoPts != 0) {
                    abs = Math.abs(aVBufFrame.pts - this.mVideoPts);
                    if (abs > 10000) {
                        Log.w(a, "the audio and video capture ptsDiff above :" + abs);
                        postError(ERROR_AV_ASYNC_ERROR, abs);
                    }
                }
                if (this.mInitDts == INVALID_TS) {
                    if (aVBufFrame.dts > 0) {
                        this.mInitDts = aVBufFrame.dts;
                    } else {
                        this.mInitDts = 0;
                    }
                }
                abs = z2 ? this.mLastVideoDts : this.mLastAudioDts;
                if (aVBufFrame.dts <= abs) {
                    Log.i(a, "non increase " + str + " timestamp, diff=" + (aVBufFrame.dts - this.mLastAudioDts));
                    aVBufFrame.pts += (abs - aVBufFrame.dts) + 10;
                    aVBufFrame.dts = abs + 10;
                }
                if (z2) {
                    this.mLastVideoDts = aVBufFrame.dts;
                } else {
                    this.mLastAudioDts = aVBufFrame.dts;
                }
                aVBufFrame.dts -= this.mInitDts;
                aVBufFrame.pts -= this.mInitDts;
            } else {
                synchronized (this.k) {
                    Log.d(a, str + " header got");
                    if (aVBufFrame.buf != null && aVBufFrame.buf.limit() > 0) {
                        if (!z2 && this.m == null) {
                            this.m = ByteBuffer.allocateDirect(aVBufFrame.buf.limit());
                        } else if (z2) {
                            if (this.l == null) {
                                this.l = ByteBuffer.allocateDirect(aVBufFrame.buf.limit());
                            }
                        }
                        byteBuffer = z2 ? this.l : this.m;
                        if (byteBuffer.capacity() < aVBufFrame.buf.limit()) {
                            byteBuffer = ByteBuffer.allocateDirect(aVBufFrame.buf.limit());
                            if (z2) {
                                this.l = byteBuffer;
                            } else {
                                this.m = byteBuffer;
                            }
                        }
                        byteBuffer.clear();
                        byteBuffer.put(aVBufFrame.buf);
                        byteBuffer.flip();
                        aVBufFrame.buf.flip();
                    }
                }
            }
            if (aVBufFrame.buf != null && aVBufFrame.buf.limit() > 0) {
                synchronized (this.j) {
                    if (!z2) {
                        if (this.h == null) {
                            this.h = new d(e, g);
                        }
                    }
                    if (z2) {
                        if (this.i == null) {
                            this.i = new d(d, f);
                        }
                    }
                }
                d dVar = z2 ? this.i : this.h;
                if (getUseSyncMode()) {
                    ByteBuffer a;
                    do {
                        a = dVar.a(aVBufFrame.buf.limit(), 20);
                    } while (a == null);
                    byteBuffer = a;
                } else {
                    byteBuffer = dVar.a(aVBufFrame.buf.limit());
                }
                if (byteBuffer != null) {
                    if (z2 && (aVBufFrame.flags & STATE_STARTING) != 0) {
                        this.mVideoKeyFrameGot = true;
                        if (z && this.l != null) {
                            byteBuffer.put(this.l);
                            this.l.flip();
                        }
                    }
                    byteBuffer.put(aVBufFrame.buf);
                    byteBuffer.flip();
                    aVBufFrame.buf.flip();
                    aVBufFrame.buf = byteBuffer;
                } else if (z2) {
                    this.mVFrameDroppedUpper += STATE_STARTING;
                    return;
                } else {
                    this.mAFrameDropped += STATE_STARTING;
                    return;
                }
            }
            if (isUseFFmpeg()) {
                aVBufFrame.avPacketOpaque = AVPacketUtil.clone(aVBufFrame.avPacketOpaque);
            }
            if (this.mPublishThread != null) {
                this.mPublishHandler.sendMessage(this.mPublishHandler.obtainMessage(STATE_STOPPING, aVBufFrame));
            }
        }
    }

    private void b(Object obj) {
        if (getAutoWork()) {
            Object obj2;
            if (obj instanceof ImgBufFormat) {
                obj2 = STATE_STARTING;
            } else if (obj instanceof AudioBufFormat) {
                obj2 = null;
            } else {
                return;
            }
            if (isAudioOnly() && r0 != null) {
                return;
            }
            if (!(isVideoOnly() && r0 == null) && !this.mIsPublishing && this.mState.get() == 0 && getUseSyncMode()) {
                this.p.close();
                if (start(this.s)) {
                    this.p.block();
                }
            }
        }
    }
}
