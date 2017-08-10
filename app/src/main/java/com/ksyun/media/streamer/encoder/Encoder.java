package com.ksyun.media.streamer.encoder;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.ksyun.media.streamer.framework.AVBufFrame;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.AudioBufFrame;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.c;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Encoder<I, O> {
    public static final int ENCODER_ERROR_UNKNOWN = -1001;
    public static final int ENCODER_ERROR_UNSUPPORTED = -1002;
    public static final int ENCODER_STATE_ENCODING = 2;
    public static final int ENCODER_STATE_IDLE = 0;
    public static final int ENCODER_STATE_INITIALIZING = 1;
    public static final int ENCODER_STATE_STOPPING = 3;
    public static final int INFO_STARTED = 1;
    public static final int INFO_STOPPED = 2;
    private static final String k = "Encoder";
    private static final boolean l = true;
    private static final boolean m = false;
    private static final int n = 1;
    private static final int o = 2;
    private static final int p = 3;
    private static final int q = 4;
    private static final int r = 5;
    private static final int s = 10;
    private static final int t = 11;
    private static final int u = 12;
    private static final int v = 0;
    private static final int w = 1;
    private static final int x = 2;
    private EncoderListener A;
    private c B;
    private boolean C;
    private boolean D;
    private ByteBuffer E;
    private AVBufFrame F;
    private Timer G;
    private I H;
    protected int a;
    protected Object b;
    protected AtomicInteger c;
    protected AtomicInteger d;
    protected boolean e;
    protected AtomicInteger f;
    protected HandlerThread g;
    protected Handler h;
    protected ConditionVariable i;
    protected volatile boolean j;
    public SinkPin<I> mSinkPin;
    public SrcPin<O> mSrcPin;
    private final Handler y;
    private EncoderInfoListener z;

    /* renamed from: com.ksyun.media.streamer.encoder.Encoder.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ int b;
        final /* synthetic */ Encoder c;

        AnonymousClass2(Encoder encoder, int i, int i2) {
            this.c = encoder;
            this.a = i;
            this.b = i2;
        }

        public void run() {
            if (this.c.z != null) {
                this.c.z.onInfo(this.c, this.a, this.b);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.encoder.Encoder.3 */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ Encoder b;

        AnonymousClass3(Encoder encoder, int i) {
            this.b = encoder;
            this.a = i;
        }

        public void run() {
            if (this.b.a == Encoder.x) {
                StatsLogReport.getInstance().reportError(this.a, Encoder.q);
            } else if (this.b.a == Encoder.w) {
                StatsLogReport.getInstance().reportError(this.a, Encoder.p);
            }
            if (this.b.A != null) {
                this.b.A.onError(this.b, this.a);
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.encoder.Encoder.4 */
    class AnonymousClass4 extends Handler {
        final /* synthetic */ Encoder a;

        AnonymousClass4(Encoder encoder, Looper looper) {
            this.a = encoder;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case Encoder.w /*1*/:
                    if (this.a.f.get() == 0) {
                        this.a.f.set(Encoder.w);
                        this.a.c.set(Encoder.v);
                        this.a.d.set(Encoder.v);
                        this.a.H = null;
                        this.a.E = null;
                        this.a.F = null;
                        int a = this.a.a(message.obj);
                        if (a == 0) {
                            this.a.f.set(Encoder.x);
                            this.a.a((int) Encoder.w, (int) Encoder.v);
                        } else {
                            this.a.f.set(Encoder.v);
                            this.a.b(a);
                        }
                    }
                    if (this.a.getAutoWork() && this.a.getUseSyncMode()) {
                        this.a.i.open();
                    }
                case Encoder.x /*2*/:
                    if (this.a.f.get() == Encoder.x) {
                        this.a.f.set(Encoder.p);
                        this.a.a();
                        this.a.f.set(Encoder.v);
                        this.a.a((int) Encoder.x, (int) Encoder.v);
                    }
                    this.a.E = null;
                    this.a.F = null;
                case Encoder.p /*3*/:
                    this.a.g.quit();
                case Encoder.q /*4*/:
                    if (this.a.f.get() == Encoder.x) {
                        this.a.a(message.arg1);
                    }
                case Encoder.r /*5*/:
                    if (this.a.f.get() == Encoder.x) {
                        this.a.b();
                    }
                case Encoder.s /*10*/:
                    this.a.d(message.obj);
                case Encoder.t /*11*/:
                    a(message.obj);
                    if (this.a.getUseSyncMode()) {
                        this.a.i.open();
                    }
                case Encoder.u /*12*/:
                    a(message.obj);
                default:
            }
        }

        private void a(I i) {
            if (this.a.f.get() == Encoder.x) {
                int b;
                long currentTimeMillis = System.currentTimeMillis();
                if ((((AVFrameBase) i).flags & Encoder.q) == 0) {
                    b = this.a.b((Object) i);
                } else if (this.a.getAutoWork()) {
                    Log.d(Encoder.k, "end of stream, flushing...");
                    this.a.b();
                    this.a.stop();
                    return;
                } else {
                    b = Encoder.v;
                }
                int currentTimeMillis2 = (int) (System.currentTimeMillis() - currentTimeMillis);
                if (this.a.a == Encoder.x) {
                    StatsLogReport.getInstance().setVideoEncodeDelay(currentTimeMillis2);
                }
                if (b != 0) {
                    this.a.b(b);
                    return;
                }
                this.a.H = i;
                this.a.c.incrementAndGet();
                if (this.a.a == Encoder.x) {
                    StatsLogReport.getInstance().setEncodedFrames((long) this.a.c.get());
                    return;
                }
                return;
            }
            this.a.e(i);
            this.a.d.incrementAndGet();
            StatsLogReport.getInstance().setEncodeDroppedFrameCount(this.a.d.get());
            Log.d(Encoder.k, "total dropped: " + this.a.d.get() + " total encoded: " + this.a.c.get());
        }
    }

    public interface EncoderInfoListener {
        void onInfo(Encoder encoder, int i, int i2);
    }

    public interface EncoderListener {
        void onError(Encoder encoder, int i);
    }

    private class a extends SinkPin<I> {
        final /* synthetic */ Encoder a;

        private a(Encoder encoder) {
            this.a = encoder;
        }

        public void onFormatChanged(Object obj) {
            if (this.a.getState() == 0 && this.a.getAutoWork()) {
                if (this.a.a(obj, this.a.b)) {
                    if (this.a.getUseSyncMode()) {
                        this.a.i.close();
                    }
                    this.a.start();
                    if (this.a.getUseSyncMode()) {
                        this.a.i.block();
                    }
                }
            } else if (this.a.g != null) {
                this.a.h.sendMessage(this.a.h.obtainMessage(Encoder.s, obj));
            }
        }

        public void onFrameAvailable(I i) {
            Object obj = Encoder.w;
            if (this.a.isEncoding()) {
                Object obj2;
                if (this.a.a == Encoder.x && ((AVFrameBase) i).flags == 0) {
                    float framerate = ((VideoEncodeFormat) this.a.b).getFramerate();
                    if (framerate > 0.0f) {
                        long j = ((AVFrameBase) i).pts;
                        if (this.a.c.get() == 0) {
                            this.a.B.a(framerate, j);
                        }
                        if (this.a.B.a(j)) {
                            return;
                        }
                    }
                }
                if (this.a.a == Encoder.x && this.a.h.hasMessages(Encoder.t)) {
                    obj2 = Encoder.w;
                } else if (this.a.c((Object) i)) {
                    obj2 = Encoder.w;
                } else {
                    if (!(this.a.getUseSyncMode() && (((AVFrameBase) i).flags & Encoder.q) == 0)) {
                        obj = Encoder.v;
                    }
                    if (obj != null) {
                        this.a.i.close();
                    }
                    this.a.h.sendMessage(this.a.h.obtainMessage(Encoder.t, i));
                    if (obj != null) {
                        this.a.i.block();
                    }
                    obj2 = Encoder.v;
                }
                if (obj2 != null) {
                    this.a.d.incrementAndGet();
                    Log.d(Encoder.k, "total dropped: " + this.a.d.get() + " total encoded: " + this.a.c.get());
                }
            }
        }

        public void onDisconnect(boolean z) {
            if (z) {
                this.a.release();
            }
        }
    }

    protected abstract int a(Object obj);

    protected abstract void a();

    protected abstract void a(int i);

    protected abstract int b(I i);

    public Encoder() {
        this.a = v;
        this.i = new ConditionVariable();
        this.C = m;
        this.D = m;
        this.mSinkPin = new a();
        this.mSrcPin = new SrcPin();
        this.f = new AtomicInteger(v);
        this.d = new AtomicInteger(v);
        this.c = new AtomicInteger(v);
        this.y = new Handler(Looper.getMainLooper());
        this.B = new c();
        this.j = m;
        c();
    }

    public void setEncoderListener(EncoderListener encoderListener) {
        this.A = encoderListener;
    }

    public void setEncoderInfoListener(EncoderInfoListener encoderInfoListener) {
        this.z = encoderInfoListener;
    }

    public boolean getUseSyncMode() {
        return this.C;
    }

    public void setUseSyncMode(boolean z) {
        this.C = z;
    }

    public boolean getAutoWork() {
        return this.D;
    }

    public void setAutoWork(boolean z) {
        this.D = z;
    }

    public void configure(Object obj) {
        this.b = obj;
        if (this.a != 0) {
            return;
        }
        if (obj instanceof AudioEncodeFormat) {
            this.a = w;
        } else if (obj instanceof VideoEncodeFormat) {
            this.a = x;
        }
    }

    public int getState() {
        return this.f.get();
    }

    public int getFrameDropped() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.d.get();
        }
        Log.w(k, "you must enableStreamStatModule");
        return v;
    }

    public int getFrameEncoded() {
        if (StatsLogReport.getInstance().isPermitLogReport()) {
            return this.c.get();
        }
        Log.w(k, "you must enableStreamStatModule");
        return v;
    }

    public void setMute(boolean z) {
        this.e = z;
    }

    public void adjustBitrate(int i) {
        if (this.f.get() != x) {
            Log.e(k, "Call adjustBitrate on invalid state");
        } else if (this.g != null) {
            this.h.sendMessage(this.h.obtainMessage(q, i, v));
        }
    }

    public void flush() {
        if (this.f.get() != x) {
            Log.e(k, "Call flush on invalid state");
        } else if (this.g != null) {
            this.h.sendEmptyMessage(r);
        }
    }

    public void start() {
        if (this.f.get() != 0 && this.f.get() != p) {
            Log.i(k, "Call start on invalid state");
        } else if (this.g != null) {
            this.h.sendMessage(this.h.obtainMessage(w, this.b));
        }
    }

    public void forceKeyFrame() {
        this.j = l;
    }

    public void stop() {
        if (this.f.get() != 0 && this.f.get() != p) {
            stopRepeatLastFrame();
            if (getUseSyncMode()) {
                this.i.open();
            }
            if (this.g != null) {
                this.h.sendEmptyMessage(x);
            }
        }
    }

    public void release() {
        stop();
        this.mSrcPin.disconnect(l);
        if (this.g != null) {
            this.h.sendEmptyMessage(p);
            try {
                this.g.join();
            } catch (InterruptedException e) {
                Log.d(k, "Encode Thread Interrupted!");
            }
            this.g = null;
        }
    }

    public boolean isEncoding() {
        return this.f.get() == x ? l : m;
    }

    public void startRepeatLastFrame() {
        if (this.f.get() == x && this.a == x && this.G == null && this.H != null) {
            int framerate = (int) (1000.0f / ((VideoEncodeFormat) this.b).getFramerate());
            this.G = new Timer();
            this.G.schedule(new TimerTask() {
                final /* synthetic */ Encoder a;

                {
                    this.a = r1;
                }

                public void run() {
                    if (this.a.h.hasMessages(Encoder.u)) {
                        this.a.d.incrementAndGet();
                        Log.d(Encoder.k, "total dropped: " + this.a.d.get() + " total encoded: " + this.a.c.get());
                    } else if (this.a.H != null) {
                        ((AVFrameBase) this.a.H).pts = (System.nanoTime() / 1000) / 1000;
                        this.a.h.sendMessage(this.a.h.obtainMessage(Encoder.u, this.a.H));
                    }
                }
            }, (long) framerate, (long) framerate);
            return;
        }
        Log.e(k, "Call startRepeatLastFrame on invalid state");
    }

    public void stopRepeatLastFrame() {
        if (this.G != null) {
            this.G.cancel();
            this.G = null;
        }
    }

    protected boolean a(Object obj, Object obj2) {
        return m;
    }

    protected boolean c(I i) {
        return m;
    }

    protected void f(Object obj) {
        this.mSrcPin.onFormatChanged(obj);
    }

    public AVBufFrame getExtra() {
        if (this.F != null) {
            if (this.F instanceof ImgBufFrame) {
                return new ImgBufFrame((ImgBufFrame) this.F);
            }
            if (this.F instanceof AudioBufFrame) {
                return new AudioBufFrame((AudioBufFrame) this.F);
            }
        }
        return null;
    }

    private void h(O o) {
        AVBufFrame aVBufFrame = (AVBufFrame) o;
        if ((aVBufFrame.flags & x) != 0) {
            Log.d(k, "Cache Extra: " + aVBufFrame.buf.limit() + " bytes");
            if (this.E == null || this.E.capacity() < aVBufFrame.buf.limit()) {
                this.E = ByteBuffer.allocateDirect(aVBufFrame.buf.limit());
                this.E.order(ByteOrder.nativeOrder());
            }
            this.E.clear();
            this.E.put(aVBufFrame.buf);
            this.E.flip();
            aVBufFrame.buf.rewind();
            if (aVBufFrame instanceof ImgBufFrame) {
                this.F = new ImgBufFrame((ImgBufFrame) aVBufFrame);
                this.F.buf = this.E;
            } else if (aVBufFrame instanceof AudioBufFrame) {
                this.F = new AudioBufFrame((AudioBufFrame) aVBufFrame);
                this.F.buf = this.E;
            }
        }
    }

    protected void g(O o) {
        h(o);
        this.mSrcPin.onFrameAvailable(o);
    }

    protected void d(Object obj) {
    }

    protected void b() {
    }

    protected void e(I i) {
    }

    protected void a(int i, int i2) {
        this.y.post(new AnonymousClass2(this, i, i2));
    }

    protected void b(int i) {
        this.y.post(new AnonymousClass3(this, i));
    }

    private void c() {
        this.g = new HandlerThread("EncodeThread");
        this.g.start();
        this.h = new AnonymousClass4(this, this.g.getLooper());
    }
}
