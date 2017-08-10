package com.ksyun.media.player;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.media.MediaCodecList;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.ksy.statlibrary.db.DBConstant;
import com.ksyun.media.player.KSYMediaMeta.KSYStreamMeta;
import com.ksyun.media.player.misc.IMediaDataSource;
import com.ksyun.media.player.misc.KSYQosInfo;
import com.ksyun.media.player.misc.KSYTrackInfo;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public final class KSYMediaPlayer extends a {
    private static final int A = 50001;
    private static final int B = 50002;
    private static final int C = 20003;
    private static final int D = 20004;
    private static final int E = 0;
    private static final int F = 3;
    public static final int FFP_PROPV_DECODER_AVCODEC = 1;
    public static final int FFP_PROPV_DECODER_MEDIACODEC = 2;
    private static final int G = 20005;
    private static final int H = 20006;
    private static final int I = 20007;
    private static final int J = 20008;
    private static final int K = 20009;
    public static final int KSY_LOG_DEBUG = 3;
    public static final int KSY_LOG_DEFAULT = 1;
    public static final int KSY_LOG_ERROR = 6;
    public static final int KSY_LOG_FATAL = 7;
    public static final int KSY_LOG_INFO = 4;
    public static final int KSY_LOG_SILENT = 8;
    public static final int KSY_LOG_UNKNOWN = 0;
    public static final int KSY_LOG_VERBOSE = 2;
    public static final int KSY_LOG_WARN = 5;
    private static final int L = 20010;
    public static final int LOG_REPORT_GOT_HEADER = 6;
    public static final int LOG_REPORT_GOT_LOCAL_DNS_INFO = 7;
    private static final int M = 20030;
    public static final int MEDIA_LOG_REPORT = 501;
    private static final int N = 20032;
    private static final int O = 20031;
    public static final int OPT_CATEGORY_CODEC = 2;
    public static final int OPT_CATEGORY_FORMAT = 1;
    public static final int OPT_CATEGORY_PLAYER = 4;
    public static final int OPT_CATEGORY_SWS = 3;
    public static final int SDL_FCC_RV16 = 909203026;
    public static final int SDL_FCC_RV32 = 842225234;
    public static final int SDL_FCC_YV12 = 842094169;
    public static final int VIDEO_SCALING_MODE_NOSCALE_TO_FIT = 0;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    protected static final int a = 599;
    private static volatile boolean aJ = false;
    private static volatile boolean aK = false;
    private static String ax = null;
    protected static final int b = 10001;
    private static final String d;
    private static final int e = 0;
    private static final int f = 1;
    private static final int g = 2;
    private static final int h = 3;
    private static final int i = 4;
    private static final int j = 5;
    private static final int k = 99;
    private static final int l = 100;
    private static final int m = 200;
    private static final int n = 40010;
    private static final int o = 1;
    private static final int p = 2;
    private static final int q = 10001;
    private static final int r = 10002;
    private static final int s = 10003;
    private static final int t = 20001;
    private static final int u = 20002;
    private static final int v = 20050;
    private static final int w = 20051;
    private static final int x = 40001;
    private static final int y = 40002;
    private static final int z = 40003;
    private long P;
    private long Q;
    @com.ksyun.media.player.a.a
    private int R;
    @com.ksyun.media.player.a.a
    private int S;
    private Context T;
    private SurfaceHolder U;
    private b V;
    private WakeLock W;
    private boolean X;
    private boolean Y;
    private int Z;
    private Object aA;
    private boolean aB;
    private List<String> aC;
    private int aD;
    private com.ksyun.media.player.misc.d aE;
    private Object aF;
    private Object aG;
    private int aH;
    private c aI;
    private d aL;
    private f aM;
    private e aN;
    private OnVideoTextureListener aO;
    private OnAudioPCMListener aP;
    private ByteBuffer aQ;
    private OnVideoRawDataListener aR;
    private int aa;
    private int ab;
    private int ac;
    private int ad;
    private int ae;
    private long af;
    private int ag;
    private long ah;
    private int ai;
    private long aj;
    private boolean ak;
    private boolean al;
    private UUID am;
    private String an;
    private String ao;
    private String ap;
    private String aq;
    private g ar;
    private String as;
    private String at;
    private boolean au;
    private float av;
    private String aw;
    private String ay;
    private String az;
    KSYMediaMeta c;

    /* renamed from: com.ksyun.media.player.KSYMediaPlayer.1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            a = new int[KSYDecodeMode.values().length];
            try {
                a[KSYDecodeMode.KSY_DECODE_MODE_SOFTWARE.ordinal()] = KSYMediaPlayer.o;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[KSYDecodeMode.KSY_DECODE_MODE_AUTO.ordinal()] = KSYMediaPlayer.p;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[KSYDecodeMode.KSY_DECODE_MODE_HARDWARE.ordinal()] = KSYMediaPlayer.h;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static class Builder {
        private boolean enableStatModule;
        private Context mContext;

        public Builder(Context context) {
            this.enableStatModule = true;
            this.mContext = context.getApplicationContext();
        }

        public Builder enableKSYStatModule(boolean z) {
            this.enableStatModule = z;
            return this;
        }

        public KSYMediaPlayer build() {
            return new KSYMediaPlayer();
        }
    }

    public enum KSYDecodeMode {
        KSY_DECODE_MODE_SOFTWARE,
        KSY_DECODE_MODE_AUTO,
        KSY_DECODE_MODE_HARDWARE
    }

    public enum KSYDeinterlaceMode {
        KSY_Deinterlace_Close,
        KSY_Deinterlace_Open,
        KSY_Deinterlace_Auto
    }

    public enum KSYReloadMode {
        KSY_RELOAD_MODE_FAST,
        KSY_RELOAD_MODE_ACCURATE
    }

    public interface OnAudioPCMListener {
        void onAudioPCMAvailable(IMediaPlayer iMediaPlayer, ByteBuffer byteBuffer, long j, int i, int i2, int i3);
    }

    public interface OnVideoRawDataListener {
        void onVideoRawDataAvailable(IMediaPlayer iMediaPlayer, byte[] bArr, int i, int i2, int i3, int i4, long j);
    }

    public interface OnVideoTextureListener {
        void onVideoTextureAvailable(IMediaPlayer iMediaPlayer, SurfaceTexture surfaceTexture, int i);
    }

    public interface e {
        String a(IMediaPlayer iMediaPlayer, String str, int i, int i2, int i3, int i4);
    }

    public static class a implements e {
        public static final a a;

        static {
            a = new a();
        }

        @TargetApi(16)
        public String a(IMediaPlayer iMediaPlayer, String str, int i, int i2, int i3, int i4) {
            if (VERSION.SDK_INT < 16) {
                return null;
            }
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int codecCount = MediaCodecList.getCodecCount();
            for (int i5 = KSYMediaPlayer.e; i5 < codecCount; i5 += KSYMediaPlayer.o) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i5);
                if (!codecInfoAt.isEncoder()) {
                    String[] supportedTypes = codecInfoAt.getSupportedTypes();
                    if (supportedTypes != null) {
                        int length = supportedTypes.length;
                        for (int i6 = KSYMediaPlayer.e; i6 < length; i6 += KSYMediaPlayer.o) {
                            Object obj = supportedTypes[i6];
                            if (!TextUtils.isEmpty(obj) && obj.equalsIgnoreCase(str)) {
                                f a = f.a(codecInfoAt, str);
                                if (a != null && (VERSION.SDK_INT < 21 || KSYMediaPlayer.checkMediaCodecSupportedResolution(codecInfoAt, str, i3, i4) >= 0)) {
                                    arrayList.add(a);
                                    a.a(str);
                                }
                            }
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            f fVar = (f) arrayList.get(KSYMediaPlayer.e);
            Iterator it = arrayList.iterator();
            f fVar2 = fVar;
            while (it.hasNext()) {
                fVar = (f) it.next();
                if (fVar.j <= fVar2.j) {
                    fVar = fVar2;
                }
                fVar2 = fVar;
            }
            if (fVar2.j >= f.d) {
                return fVar2.i.getName();
            }
            String access$400 = KSYMediaPlayer.d;
            Object[] objArr = new Object[KSYMediaPlayer.o];
            objArr[KSYMediaPlayer.e] = fVar2.i.getName();
            Log.w(access$400, String.format(Locale.US, "unaccetable codec: %s", objArr));
            return null;
        }
    }

    private static class b extends Handler {
        private final WeakReference<KSYMediaPlayer> a;

        public b(KSYMediaPlayer kSYMediaPlayer, Looper looper) {
            super(looper);
            this.a = new WeakReference(kSYMediaPlayer);
        }

        public void handleMessage(Message message) {
            KSYMediaPlayer kSYMediaPlayer = (KSYMediaPlayer) this.a.get();
            if (kSYMediaPlayer == null || kSYMediaPlayer.P == 0) {
                com.ksyun.media.player.c.a.c(KSYMediaPlayer.d, "KSYMediaPlayer went away with unhandled events");
            } else if (message.what == KSYMediaPlayer.MEDIA_LOG_REPORT) {
                switch (message.arg1) {
                    case KSYMediaPlayer.o /*1*/:
                        kSYMediaPlayer.j();
                    case KSYMediaPlayer.p /*2*/:
                        kSYMediaPlayer.h();
                    case KSYMediaPlayer.LOG_REPORT_GOT_HEADER /*6*/:
                        kSYMediaPlayer.notifyOnLogEvent((String) message.obj);
                    case KSYMediaPlayer.LOG_REPORT_GOT_LOCAL_DNS_INFO /*7*/:
                        r1 = null;
                        if (message.obj != null && (message.obj instanceof String)) {
                            r1 = (String) message.obj;
                        }
                        kSYMediaPlayer.a(r1);
                    default:
                }
            } else {
                switch (message.what) {
                    case KSYMediaPlayer.e /*0*/:
                    case KSYMediaPlayer.o /*1*/:
                        kSYMediaPlayer.ar = g.STATE_PREPARED;
                        kSYMediaPlayer.notifyOnPrepared();
                        kSYMediaPlayer._setCounterInfo(kSYMediaPlayer.T.getPackageName(), com.ksyun.media.player.misc.e.a().g());
                        kSYMediaPlayer.g();
                        kSYMediaPlayer.i();
                    case KSYMediaPlayer.p /*2*/:
                        kSYMediaPlayer.ar = g.STATE_COMPLETED;
                        kSYMediaPlayer.notifyOnCompletion();
                        kSYMediaPlayer.a(false);
                    case KSYMediaPlayer.h /*3*/:
                        long j = (long) message.arg1;
                        if (j < 0) {
                            j = 0;
                        }
                        long duration = kSYMediaPlayer.getDuration();
                        if (duration > 0) {
                            j = (j * 100) / duration;
                        } else {
                            j = 0;
                        }
                        if (j >= 100) {
                            j = 100;
                        }
                        kSYMediaPlayer.notifyOnBufferingUpdate((int) j);
                    case KSYMediaPlayer.i /*4*/:
                        kSYMediaPlayer.notifyOnSeekComplete();
                    case KSYMediaPlayer.j /*5*/:
                        kSYMediaPlayer.Z = message.arg1;
                        kSYMediaPlayer.aa = message.arg2;
                        kSYMediaPlayer.notifyOnVideoSizeChanged(kSYMediaPlayer.Z, kSYMediaPlayer.aa, kSYMediaPlayer.ab, kSYMediaPlayer.ac);
                    case KSYMediaPlayer.k /*99*/:
                        if (message.obj != null) {
                            kSYMediaPlayer.notifyOnTimedText(message.obj.toString());
                        }
                    case KSYMediaPlayer.l /*100*/:
                        com.ksyun.media.player.c.a.a(KSYMediaPlayer.d, "Error (" + message.arg1 + "," + message.arg2 + ")");
                        if (kSYMediaPlayer.ar.ordinal() < g.STATE_PREPARED.ordinal()) {
                            Object obj = "N/A";
                            if (kSYMediaPlayer.an != null) {
                                obj = kSYMediaPlayer.an;
                            }
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put(StatsConstant.ID, kSYMediaPlayer.am);
                                jSONObject.put(StatsConstant.LOG_TYPE, KSYMediaPlayer.l);
                                jSONObject.put(StatsConstant.BODY_TYPE, com.ksyun.media.player.d.d.aw);
                                jSONObject.put(StatsConstant.ACTION_ID, kSYMediaPlayer.ao);
                                jSONObject.put(StatsConstant.STREAM_URL, obj);
                                jSONObject.put(com.ksyun.media.player.d.d.s, (double) kSYMediaPlayer.av);
                                jSONObject.put(com.ksyun.media.player.d.d.t, StatsConstant.STREAM_STATUS_FAIL);
                                jSONObject.put(StatsConstant.STREAM_FAIL_CODE, message.arg1);
                                jSONObject.put(StatsConstant.NETWORK_TYPE, com.ksyun.media.player.util.c.b(kSYMediaPlayer.T));
                                jSONObject.put(StatsConstant.NETWORK_TYPE_DESCRIPTION, com.ksyun.media.player.util.c.c(kSYMediaPlayer.T));
                                jSONObject.put(StatsConstant.DATE, System.currentTimeMillis());
                                jSONObject.put(com.ksyun.media.player.d.d.o, kSYMediaPlayer.aq);
                                jSONObject.put(com.ksyun.media.player.d.d.S, jSONObject.length() + KSYMediaPlayer.o);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            com.ksyun.media.player.d.c.a().a(jSONObject);
                            kSYMediaPlayer.notifyOnLogEvent(jSONObject.toString());
                            kSYMediaPlayer.a(true, message.arg2);
                            kSYMediaPlayer.i();
                        }
                        if (!kSYMediaPlayer.notifyOnError(message.arg1, message.arg2)) {
                            kSYMediaPlayer.notifyOnCompletion();
                        }
                        kSYMediaPlayer.a(false);
                    case KSYMediaPlayer.m /*200*/:
                        switch (message.arg1) {
                            case KSYMediaPlayer.h /*3*/:
                                kSYMediaPlayer.ai = (int) (System.currentTimeMillis() - kSYMediaPlayer.aj);
                                break;
                            case IMediaPlayer.MEDIA_INFO_BUFFERING_START /*701*/:
                                kSYMediaPlayer.ad = kSYMediaPlayer.ad + KSYMediaPlayer.o;
                                kSYMediaPlayer.ah = System.currentTimeMillis();
                                break;
                            case IMediaPlayer.MEDIA_INFO_BUFFERING_END /*702*/:
                                kSYMediaPlayer.ag = ((int) (System.currentTimeMillis() - kSYMediaPlayer.ah)) + kSYMediaPlayer.ag;
                                break;
                            case KSYMediaPlayer.q /*10001*/:
                                if (kSYMediaPlayer.az == com.ksyun.media.player.d.d.av) {
                                    kSYMediaPlayer.setRotateDegree(message.arg2);
                                    break;
                                }
                                break;
                            case IMediaPlayer.MEDIA_INFO_HARDWARE_DECODE /*41000*/:
                                kSYMediaPlayer.az = com.ksyun.media.player.d.d.au;
                                Log.d(KSYMediaPlayer.d, "KSYMediaPlayer Use Hardware Decode and SDK Version is:" + KSYMediaPlayer.getVersion() + ", BufferTimeMax:" + kSYMediaPlayer.getBufferTimeMax());
                                break;
                            case IMediaPlayer.MEDIA_INFO_SOFTWARE_DECODE /*41001*/:
                                kSYMediaPlayer.az = com.ksyun.media.player.d.d.av;
                                Log.d(KSYMediaPlayer.d, "KSYMediaPlayer Use Software Decode and SDK Version is:" + KSYMediaPlayer.getVersion() + ", BufferTimeMax:" + kSYMediaPlayer.getBufferTimeMax());
                                break;
                        }
                        if (message.arg1 == KSYMediaPlayer.n) {
                            kSYMediaPlayer.V.obtainMessage(KSYMediaPlayer.MEDIA_LOG_REPORT, KSYMediaPlayer.o, KSYMediaPlayer.e).sendToTarget();
                        } else {
                            kSYMediaPlayer.notifyOnInfo(message.arg1, message.arg2);
                        }
                    case KSYMediaPlayer.a /*599*/:
                        try {
                            double d;
                            JSONObject jSONObject2 = new JSONObject((String) message.obj);
                            String string = jSONObject2.getString("ksy_name");
                            if (jSONObject2.has("ksy_number")) {
                                r1 = null;
                                d = jSONObject2.getDouble("ksy_number");
                            } else if (jSONObject2.has("ksy_string")) {
                                r1 = jSONObject2.getString("ksy_string");
                                d = 0.0d;
                            } else {
                                r1 = null;
                                d = 0.0d;
                            }
                            kSYMediaPlayer.notifyMessageInfo(string, r1, d);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    case KSYMediaPlayer.q /*10001*/:
                        kSYMediaPlayer.ab = message.arg1;
                        kSYMediaPlayer.ac = message.arg2;
                        kSYMediaPlayer.notifyOnVideoSizeChanged(kSYMediaPlayer.Z, kSYMediaPlayer.aa, kSYMediaPlayer.ab, kSYMediaPlayer.ac);
                    default:
                        com.ksyun.media.player.c.a.a(KSYMediaPlayer.d, "Unknown message type " + message.what);
                }
            }
        }
    }

    @TargetApi(14)
    private class c implements OnFrameAvailableListener {
        final /* synthetic */ KSYMediaPlayer a;
        private KSYMediaPlayer b;

        c(KSYMediaPlayer kSYMediaPlayer, KSYMediaPlayer kSYMediaPlayer2) {
            this.a = kSYMediaPlayer;
            this.b = null;
            this.b = kSYMediaPlayer2;
        }

        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            if (this.a.aO != null) {
                this.a.aO.onVideoTextureAvailable(this.b, surfaceTexture, this.a.aH);
            }
        }
    }

    public interface d {
        String a(int i);
    }

    public interface f {
        public static final int a = 65536;
        public static final int b = 65537;
        public static final int c = 65538;
        public static final int d = 65540;
        public static final String e = "url";
        public static final String f = "segment_index";
        public static final String g = "retry_counter";

        boolean a(int i, Bundle bundle);
    }

    private enum g {
        STATE_IDLE,
        STATE_INITIALIZED,
        STATE_PREPARING,
        STATE_PREPARED,
        STATE_PLAYING,
        STATE_PAUSED,
        STATE_STOPPED,
        STATE_COMPLETED,
        STATE_ERROR,
        STATE_END
    }

    private native String _getAudioCodecInfo();

    private static native String _getColorFormatName(int i);

    private native String _getLinkLatencyInfo(String str);

    private native int _getLoopCount();

    private native Bundle _getMediaMeta();

    private native float _getPropertyFloat(int i, float f);

    private native long _getPropertyLong(int i, long j);

    private native String _getPropertyString(int i);

    private native Bundle _getQosInfo();

    private native void _getScreenShot(Bitmap bitmap);

    private native String _getVideoCodecInfo();

    private native void _pause() throws IllegalStateException;

    private native void _prepareAsync() throws IllegalStateException;

    private native void _release();

    private native void _reload(String str, boolean z, int i) throws IllegalStateException;

    private native void _reset();

    private native void _seekTo(long j, boolean z);

    private native void _setBufferSize(int i);

    private native void _setCounterInfo(String str, String str2);

    private native void _setDataSource(IMediaDataSource iMediaDataSource) throws IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setDataSource(String str, String[] strArr, String[] strArr2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setDataSourceFd(int i, long j, long j2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setDataSourceList(String str);

    private native void _setDecodeMode(int i);

    private native void _setLoopCount(int i);

    private native boolean _setMirror(boolean z);

    private native void _setOption(int i, String str, long j);

    private native void _setOption(int i, String str, String str2);

    private native void _setPlayerMute(int i);

    private native void _setPropertyFloat(int i, float f);

    private native void _setPropertyLong(int i, long j);

    private native void _setPropertyString(int i, String str);

    private native boolean _setRotateDegree(int i);

    private native void _setStreamSelected(int i, boolean z);

    private native void _setTimeout(int i, int i2);

    private native void _setVideoRenderingState(int i);

    private native void _setVideoScalingMode(int i);

    private native void _setVideoSurface(Surface surface);

    private native void _softReset();

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    public static native String getVersion();

    private native void native_addTimedTextSource(String str);

    private native void native_addVideoRawBuffer(byte[] bArr);

    private native void native_enableDeinterlace(boolean z);

    private native void native_enableFastPlayMode(boolean z);

    private native void native_enableVideoRawDataCallback(boolean z);

    private native void native_finalize();

    private static native void native_init();

    private native void native_message_loop(Object obj);

    public static native void native_profileBegin(String str);

    public static native void native_profileEnd();

    public static native void native_setLogLevel(int i);

    public static native void native_setPCMBuffer(long j, ByteBuffer byteBuffer);

    private native void native_setPlayableRanges(long j, long j2);

    private native void native_setVideoOffset(float f, float f2);

    private native void native_setup(Object obj);

    public native int getAudioSessionId();

    public native long getCurrentPosition();

    public native long getDuration();

    public native boolean isPlaying();

    public native void setVolume(float f, float f2);

    static {
        d = KSYMediaPlayer.class.getName();
        aJ = false;
        aK = false;
    }

    public static void loadLibrariesOnce() {
        synchronized (KSYMediaPlayer.class) {
            if (!aJ) {
                if (TextUtils.isEmpty(KSYLibraryManager.getLocalLibraryPath())) {
                    if (!e.a("ksylive")) {
                        e.a("ksyplayer");
                    }
                } else if (!e.a(KSYLibraryManager.getLocalLibraryPath(), "ksylive")) {
                    e.a(KSYLibraryManager.getLocalLibraryPath(), "ksyplayer");
                }
                aJ = true;
            }
        }
    }

    private static void initNativeOnce() {
        synchronized (KSYMediaPlayer.class) {
            if (!aK) {
                native_init();
                aK = true;
            }
        }
    }

    private KSYMediaPlayer(Builder builder) {
        this.W = null;
        this.av = 2.0f;
        this.ay = null;
        this.aH = -1;
        this.aI = null;
        this.aO = null;
        this.aP = null;
        this.aQ = null;
        this.aR = null;
        this.T = builder.mContext;
        this.aA = new Object();
        this.aG = new Object();
        this.aF = new Object();
        this.am = UUID.randomUUID();
        this.ad = e;
        this.ae = e;
        this.ag = e;
        this.ah = 0;
        this.as = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.at = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.aw = com.ksyun.media.player.d.d.av;
        ax = null;
        this.ar = g.STATE_IDLE;
        this.ak = false;
        this.au = false;
        this.al = builder.enableStatModule;
        this.aB = false;
        com.ksyun.media.player.misc.e.a().a(this.T);
        b();
    }

    private void a() {
        this.am = UUID.randomUUID();
        this.ad = e;
        this.ae = e;
        this.ag = e;
        this.ah = 0;
        this.ak = false;
        this.au = false;
        this.af = 0;
        this.ai = e;
        this.aj = System.currentTimeMillis();
        this.av = getBufferTimeMax();
        this.as = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.at = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.ar = g.STATE_IDLE;
        this.aB = false;
    }

    private void b() {
        loadLibrariesOnce();
        initNativeOnce();
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.V = new b(this, myLooper);
        } else {
            myLooper = Looper.getMainLooper();
            if (myLooper != null) {
                this.V = new b(this, myLooper);
            } else {
                this.V = null;
            }
        }
        native_setup(new WeakReference(this));
        this.aI = new c(this, this);
    }

    private String c() {
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = getVersion().split("\\.");
        for (int i = e; i < split.length; i += o) {
            stringBuffer.append(split[i]);
        }
        return stringBuffer.toString();
    }

    public void shouldAutoPlay(boolean z) {
        setOption((int) i, "start-on-prepared", z ? 1 : 0);
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        Surface surface;
        this.U = surfaceHolder;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
        } else {
            surface = null;
        }
        _setVideoSurface(surface);
        d();
    }

    public void setSurface(Surface surface) {
        if (this.X && surface != null) {
            com.ksyun.media.player.c.a.c(d, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.U = null;
        _setVideoSurface(surface);
        d();
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, null);
    }

    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        AssetFileDescriptor openAssetFileDescriptor;
        Throwable th;
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            setDataSource(uri.getPath());
            return;
        }
        if (DBConstant.TABLE_LOG_COLUMN_CONTENT.equals(scheme) && "settings".equals(uri.getAuthority())) {
            uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(uri));
            if (uri == null) {
                throw new FileNotFoundException("Failed to resolve default ringtone");
            }
        }
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            openAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
            if (openAssetFileDescriptor != null) {
                try {
                    if (openAssetFileDescriptor.getDeclaredLength() < 0) {
                        setDataSource(openAssetFileDescriptor.getFileDescriptor());
                    } else {
                        setDataSource(openAssetFileDescriptor.getFileDescriptor(), openAssetFileDescriptor.getStartOffset(), openAssetFileDescriptor.getDeclaredLength());
                    }
                    if (openAssetFileDescriptor != null) {
                        openAssetFileDescriptor.close();
                    }
                } catch (SecurityException e) {
                    assetFileDescriptor = openAssetFileDescriptor;
                    if (assetFileDescriptor != null) {
                        assetFileDescriptor.close();
                    }
                    Log.d(d, "Couldn't open file on client side, trying server side");
                    setDataSource(uri.toString(), (Map) map);
                } catch (IOException e2) {
                    if (openAssetFileDescriptor != null) {
                        openAssetFileDescriptor.close();
                    }
                    Log.d(d, "Couldn't open file on client side, trying server side");
                    setDataSource(uri.toString(), (Map) map);
                } catch (Throwable th2) {
                    th = th2;
                    if (openAssetFileDescriptor != null) {
                        openAssetFileDescriptor.close();
                    }
                    throw th;
                }
            } else if (openAssetFileDescriptor != null) {
                openAssetFileDescriptor.close();
            }
        } catch (SecurityException e3) {
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
            Log.d(d, "Couldn't open file on client side, trying server side");
            setDataSource(uri.toString(), (Map) map);
        } catch (IOException e4) {
            openAssetFileDescriptor = null;
            if (openAssetFileDescriptor != null) {
                openAssetFileDescriptor.close();
            }
            Log.d(d, "Couldn't open file on client side, trying server side");
            setDataSource(uri.toString(), (Map) map);
        } catch (Throwable th3) {
            openAssetFileDescriptor = null;
            th = th3;
            if (openAssetFileDescriptor != null) {
                openAssetFileDescriptor.close();
            }
            throw th;
        }
    }

    public void setDataSource(String str) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.an = str;
        _setDataSource(str, null, null);
        this.ar = g.STATE_INITIALIZED;
    }

    public void setDataSource(String str, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (!(map == null || map.isEmpty())) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append(":");
                if (!TextUtils.isEmpty((String) entry.getValue())) {
                    stringBuilder.append(" " + ((String) entry.getValue()));
                }
                stringBuilder.append("\r\n");
                setOption((int) o, "headers", stringBuilder.toString());
            }
        }
        setDataSource(str);
    }

    @TargetApi(13)
    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        this.an = fileDescriptor.toString();
        _setDataSourceFd(a(fileDescriptor), 0, 0);
        this.ar = g.STATE_INITIALIZED;
    }

    @TargetApi(13)
    private int a(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        if (VERSION.SDK_INT >= 12) {
            return ParcelFileDescriptor.dup(fileDescriptor).getFd();
        }
        try {
            Field declaredField = fileDescriptor.getClass().getDeclaredField("descriptor");
            declaredField.setAccessible(true);
            return declaredField.getInt(fileDescriptor);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException {
        this.an = fileDescriptor.toString();
        _setDataSourceFd(a(fileDescriptor), j, j2);
        this.ar = g.STATE_INITIALIZED;
    }

    public void setDataSource(List<String> list, Map<String, String> map) throws IOException, IllegalArgumentException, IllegalStateException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Empty Input Source List.");
        }
        if (this.aE == null) {
            this.aE = new com.ksyun.media.player.misc.d();
        }
        if (this.aC == null) {
            this.aC = new ArrayList();
        }
        this.aC.clear();
        this.aC.addAll(list);
        this.an = (String) this.aC.get(e);
        if (!(map == null || map.isEmpty())) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append(":");
                if (!TextUtils.isEmpty((String) entry.getValue())) {
                    stringBuilder.append(" " + ((String) entry.getValue()));
                }
                stringBuilder.append("\r\n");
            }
            setOption((int) o, "headers", stringBuilder.toString());
        }
        this.aE.a(this.aC, (Map) map);
    }

    public void setDataSource(IMediaDataSource iMediaDataSource) throws IllegalArgumentException, SecurityException, IllegalStateException {
        _setDataSource(iMediaDataSource);
    }

    public String getDataSource() {
        return this.an;
    }

    public void prepareAsync() throws IllegalStateException {
        a();
        setOption((int) o, "user-agent", e());
        com.ksyun.media.player.d.c.a().a(this.T, this.V, getVersion());
        this.ao = com.ksyun.media.player.util.c.a(this.an + String.valueOf(System.currentTimeMillis()));
        this.aq = com.ksyun.media.player.util.c.a(this.an + String.valueOf(System.currentTimeMillis()) + com.ksyun.media.player.util.c.a(this.T));
        this.aj = System.currentTimeMillis();
        this.ak = false;
        if (this.aC == null || this.aC.isEmpty()) {
            _prepareAsync();
            this.ar = g.STATE_PREPARING;
            return;
        }
        this.aE.a(this.aD);
        this.aE.a(this, this.aj);
    }

    public void prepareSourceList(String str, boolean z) {
        if (z) {
            _setDataSourceList(str);
            _prepareAsync();
            this.ar = g.STATE_PREPARING;
        } else if (this.V != null) {
            this.V.obtainMessage(l, IMediaPlayer.MEDIA_ERROR_INVALID_URL, e).sendToTarget();
        }
    }

    public void reload(String str, boolean z) throws IllegalStateException {
        _reload(str, z, KSYReloadMode.KSY_RELOAD_MODE_FAST.ordinal());
    }

    public void reload(String str, boolean z, KSYReloadMode kSYReloadMode) throws IllegalStateException {
        _reload(str, z, kSYReloadMode.ordinal());
    }

    public void start() throws IllegalStateException {
        if (this.ar.ordinal() == g.STATE_PAUSED.ordinal()) {
            this.ae = ((int) (System.currentTimeMillis() - this.af)) + this.ae;
        }
        a(true);
        _start();
        if (this.ar == g.STATE_PREPARED || this.ar == g.STATE_PAUSED) {
            this.ar = g.STATE_PLAYING;
        }
    }

    public void stop() throws IllegalStateException {
        a(false);
        f();
        _stop();
        this.ar = g.STATE_STOPPED;
    }

    public void pause() throws IllegalStateException {
        if (this.ar.ordinal() < g.STATE_PAUSED.ordinal()) {
            this.af = System.currentTimeMillis();
        }
        a(false);
        _pause();
        this.ar = g.STATE_PAUSED;
    }

    @SuppressLint({"Wakelock"})
    public void setWakeMode(Context context, int i) {
        boolean z;
        if (this.W != null) {
            boolean z2;
            if (this.W.isHeld()) {
                z2 = true;
                this.W.release();
            } else {
                z2 = false;
            }
            this.W = null;
            z = z2;
        } else {
            z = false;
        }
        this.W = ((PowerManager) context.getSystemService("power")).newWakeLock(536870912 | i, KSYMediaPlayer.class.getName());
        this.W.setReferenceCounted(false);
        if (z) {
            this.W.acquire();
        }
    }

    public void setScreenOnWhilePlaying(boolean z) {
        if (this.X != z) {
            if (z && this.U == null) {
                com.ksyun.media.player.c.a.c(d, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.X = z;
            d();
        }
    }

    @SuppressLint({"Wakelock"})
    private void a(boolean z) {
        if (this.W != null) {
            if (z && !this.W.isHeld()) {
                this.W.acquire();
            } else if (!z && this.W.isHeld()) {
                this.W.release();
            }
        }
        this.Y = z;
        d();
    }

    private void d() {
        if (this.U != null) {
            SurfaceHolder surfaceHolder = this.U;
            boolean z = this.X && this.Y;
            surfaceHolder.setKeepScreenOn(z);
        }
    }

    public KSYTrackInfo[] getTrackInfo() {
        Bundle mediaMeta = getMediaMeta();
        if (mediaMeta == null) {
            return null;
        }
        KSYMediaMeta parse = KSYMediaMeta.parse(mediaMeta);
        if (parse == null || parse.mStreams == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = parse.mStreams.iterator();
        while (it.hasNext()) {
            KSYStreamMeta kSYStreamMeta = (KSYStreamMeta) it.next();
            KSYTrackInfo kSYTrackInfo = new KSYTrackInfo(kSYStreamMeta);
            if (kSYStreamMeta.mType.equalsIgnoreCase(KSYMediaMeta.IJKM_VAL_TYPE__VIDEO)) {
                kSYTrackInfo.setTrackType(o);
            } else if (kSYStreamMeta.mType.equalsIgnoreCase(KSYMediaMeta.IJKM_VAL_TYPE__AUDIO)) {
                kSYTrackInfo.setTrackType(p);
            } else if (kSYStreamMeta.mType.equalsIgnoreCase(KSYMediaMeta.IJKM_VAL_TYPE__SUBTITLE)) {
                kSYTrackInfo.setTrackType(h);
            } else if (kSYStreamMeta.mType.equalsIgnoreCase(KSYMediaMeta.IJKM_VAL_TYPE__EXTERNAL_TIMED_TEXT)) {
                kSYTrackInfo.setTrackType(l);
            }
            kSYTrackInfo.setTrackIndex(kSYStreamMeta.mIndex);
            arrayList.add(kSYTrackInfo);
        }
        return (KSYTrackInfo[]) arrayList.toArray(new KSYTrackInfo[arrayList.size()]);
    }

    public int getSelectedTrack(int i) {
        switch (i) {
            case o /*1*/:
                return (int) _getPropertyLong(t, -1);
            case p /*2*/:
                return (int) _getPropertyLong(u, -1);
            case h /*3*/:
                return (int) _getPropertyLong(v, -1);
            case l /*100*/:
                return (int) _getPropertyLong(w, -1);
            default:
                return -1;
        }
    }

    public void selectTrack(int i) {
        _setStreamSelected(i, true);
    }

    public void deselectTrack(int i) {
        _setStreamSelected(i, false);
    }

    public int getVideoWidth() {
        return this.Z;
    }

    public int getVideoHeight() {
        return this.aa;
    }

    public int getVideoSarNum() {
        return this.ab;
    }

    public int getVideoSarDen() {
        return this.ac;
    }

    public void seekTo(long j) throws IllegalStateException {
        _seekTo(j, false);
    }

    public void seekTo(long j, boolean z) throws IllegalStateException {
        _seekTo(j, z);
    }

    public void release() {
        a(false);
        f();
        d();
        resetListeners();
        if (!(this.aC == null || this.aC.isEmpty())) {
            this.aC.clear();
        }
        if (this.aE != null) {
            this.aE.a();
        }
        this.aE = null;
        synchronized (this.aA) {
            this.aB = true;
            _release();
        }
        this.ar = g.STATE_END;
    }

    private String e() {
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = getVersion().split("\\.");
        Object b = com.ksyun.media.player.misc.e.a().b();
        stringBuffer.append("ksyplayer/");
        for (int i = e; i < split.length; i += o) {
            stringBuffer.append(split[i]);
        }
        stringBuffer.append("/");
        if (TextUtils.isEmpty(b)) {
            stringBuffer.append("null");
        } else {
            stringBuffer.append(b);
        }
        return stringBuffer.toString();
    }

    private void f() {
        if (!this.ak && this.ar.ordinal() >= g.STATE_PREPARED.ordinal()) {
            JSONObject jSONObject = new JSONObject();
            int currentTimeMillis = (int) ((System.currentTimeMillis() - this.aj) - ((long) this.ae));
            try {
                jSONObject.put(StatsConstant.ID, this.am.toString());
                jSONObject.put(StatsConstant.LOG_TYPE, l);
                jSONObject.put(StatsConstant.BODY_TYPE, com.ksyun.media.player.d.d.ax);
                jSONObject.put(StatsConstant.ACTION_ID, this.ao);
                jSONObject.put(com.ksyun.media.player.d.d.G, getCurrentPosition());
                jSONObject.put(com.ksyun.media.player.d.d.H, this.ad);
                jSONObject.put(com.ksyun.media.player.d.d.I, this.ag);
                jSONObject.put(StatsConstant.NETWORK_TYPE, com.ksyun.media.player.util.c.b(this.T));
                jSONObject.put(com.ksyun.media.player.d.d.J, getDownloadDataSize());
                jSONObject.put(StatsConstant.DATE, System.currentTimeMillis());
                jSONObject.put(com.ksyun.media.player.d.d.w, this.ai);
                jSONObject.put(com.ksyun.media.player.d.d.o, this.aq);
                if (TextUtils.isEmpty(this.ap)) {
                    jSONObject.put(StatsConstant.STREAM_ID, b.d);
                } else {
                    jSONObject.put(StatsConstant.STREAM_ID, this.ap);
                }
                jSONObject.put(com.ksyun.media.player.d.d.Q, currentTimeMillis);
                jSONObject.put(com.ksyun.media.player.d.d.R, this.ae);
                jSONObject.put(com.ksyun.media.player.d.d.s, (double) getBufferTimeMax());
                jSONObject.put(com.ksyun.media.player.d.d.S, jSONObject.length() + o);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            com.ksyun.media.player.d.c.a().a(jSONObject);
            this.ak = true;
            if (this.al) {
                notifyOnLogEvent(jSONObject.toString());
            }
        }
    }

    private void a(boolean z, int i) {
        if ((this.an == null || this.an.startsWith("http")) && !this.au) {
            long currentTimeMillis = System.currentTimeMillis();
            long currentTimeMillis2 = System.currentTimeMillis() - this.aj;
            int i2 = (int) (currentTimeMillis2 / 1000);
            synchronized (this.aA) {
                if (this.aB) {
                    return;
                }
                long downloadDataSize = getDownloadDataSize();
                String serverAddress = getServerAddress();
                int i3 = (int) ((downloadDataSize * 1000) / currentTimeMillis2);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(com.ksyun.media.player.d.d.A, new URL(this.an).getHost());
                    jSONObject.put(StatsConstant.ID, this.am.toString());
                    jSONObject.put(StatsConstant.LOG_TYPE, l);
                    jSONObject.put(StatsConstant.BODY_TYPE, com.ksyun.media.player.d.d.ay);
                    jSONObject.put(StatsConstant.ACTION_ID, this.ao);
                    jSONObject.put(com.ksyun.media.player.d.d.T, e);
                    jSONObject.put(StatsConstant.DATE, currentTimeMillis);
                    jSONObject.put(com.ksyun.media.player.d.d.V, i2);
                    jSONObject.put(com.ksyun.media.player.d.d.W, i3);
                    jSONObject.put(StatsConstant.SERVER_IP, serverAddress);
                    jSONObject.put(com.ksyun.media.player.d.d.D, this.as);
                    jSONObject.put(com.ksyun.media.player.d.d.F, this.at);
                    jSONObject.put(StatsConstant.SYSTEM_PLATFORM, StatsConstant.SYSTEM_PLATFORM_VALUE);
                    jSONObject.put(StatsConstant.SYSTEM_VERSION, VERSION.RELEASE);
                    jSONObject.put(StatsConstant.DEVICE_MODEL, Build.MODEL);
                    jSONObject.put(com.ksyun.media.player.d.d.o, this.aq);
                    if (TextUtils.isEmpty(this.ap)) {
                        jSONObject.put(StatsConstant.STREAM_ID, b.d);
                    } else {
                        jSONObject.put(StatsConstant.STREAM_ID, this.ap);
                    }
                    jSONObject.put(StatsConstant.NETWORK_TYPE, com.ksyun.media.player.util.c.b(this.T));
                    if (this.c != null) {
                        jSONObject.put(StatsConstant.CONNECT_TIME, this.c.getConnectTime());
                        jSONObject.put(com.ksyun.media.player.d.d.Y, this.c.getFirstDataTime());
                        jSONObject.put(com.ksyun.media.player.d.d.X, this.c.getAnalyzeDnsTime());
                        jSONObject.put(com.ksyun.media.player.d.d.Z, this.c.getHttpCode());
                    } else if (i > 0) {
                        jSONObject.put(com.ksyun.media.player.d.d.Z, i);
                    }
                    jSONObject.put(com.ksyun.media.player.d.d.S, jSONObject.length() + o);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e2) {
                    e2.printStackTrace();
                }
                com.ksyun.media.player.d.c.a().a(jSONObject, z);
                if (this.al) {
                    notifyOnLogEvent(jSONObject.toString());
                }
                this.au = true;
            }
        }
    }

    private void g() {
        int prepareCostTime;
        int prepareReadBytes;
        int parserInfoStatus;
        int openStreamCostTime;
        int i;
        int i2 = e;
        JSONObject jSONObject = new JSONObject();
        String str = StatsConstant.SERVER_IP_DEFAULT_VALUE;
        String str2 = "N/A";
        String str3 = "N/A";
        synchronized (this.aA) {
            if (this.aB) {
                Object obj = "N/A";
            } else {
                String serverAddress = getServerAddress();
            }
        }
        try {
            this.c = KSYMediaMeta.parse(_getMediaMeta());
        } catch (Throwable th) {
            this.c = null;
            th.printStackTrace();
        }
        if (this.c != null) {
            this.ap = this.c.getStreamId();
            if (TextUtils.isEmpty(this.ap)) {
                this.ap = b.d;
            }
            int firstDataTime = this.c.getFirstDataTime();
            if (firstDataTime >= 0) {
                i2 = firstDataTime;
            }
            prepareCostTime = this.c.getPrepareCostTime();
            prepareReadBytes = this.c.getPrepareReadBytes();
            parserInfoStatus = this.c.getParserInfoStatus();
            openStreamCostTime = this.c.getOpenStreamCostTime();
            str2 = this.c.getStreamType();
            str3 = this.c.getVideoCodec();
            Object obj2 = str3;
            Object obj3 = str2;
            i = openStreamCostTime;
            openStreamCostTime = parserInfoStatus;
            parserInfoStatus = prepareReadBytes;
            prepareReadBytes = prepareCostTime;
            prepareCostTime = i2;
            Object audioCodec = this.c.getAudioCodec();
        } else {
            String str4 = str2;
            parserInfoStatus = e;
            prepareReadBytes = e;
            prepareCostTime = e;
            i = e;
            String str5 = str3;
            str3 = str;
            openStreamCostTime = e;
            String str6 = str5;
        }
        try {
            jSONObject.put(StatsConstant.ID, this.am.toString());
            jSONObject.put(StatsConstant.LOG_TYPE, l);
            jSONObject.put(StatsConstant.BODY_TYPE, com.ksyun.media.player.d.d.aw);
            jSONObject.put(com.ksyun.media.player.d.d.aa, prepareReadBytes);
            jSONObject.put(com.ksyun.media.player.d.d.ab, parserInfoStatus);
            jSONObject.put(com.ksyun.media.player.d.d.ae, openStreamCostTime);
            jSONObject.put(com.ksyun.media.player.d.d.ac, i);
            jSONObject.put(com.ksyun.media.player.d.d.af, obj3);
            jSONObject.put(com.ksyun.media.player.d.d.ag, obj2);
            jSONObject.put(com.ksyun.media.player.d.d.ah, audioCodec);
            jSONObject.put(StatsConstant.ACTION_ID, this.ao);
            jSONObject.put(StatsConstant.STREAM_URL, this.an);
            jSONObject.put(com.ksyun.media.player.d.d.s, (double) getBufferTimeMax());
            jSONObject.put(com.ksyun.media.player.d.d.t, StatsConstant.STREAM_STATUS_OK);
            jSONObject.put(StatsConstant.STREAM_FAIL_CODE, e);
            jSONObject.put(com.ksyun.media.player.d.d.v, prepareCostTime);
            jSONObject.put(StatsConstant.NETWORK_TYPE, com.ksyun.media.player.util.c.b(this.T));
            jSONObject.put(StatsConstant.NETWORK_TYPE_DESCRIPTION, com.ksyun.media.player.util.c.c(this.T));
            jSONObject.put(StatsConstant.SERVER_IP, obj);
            jSONObject.put(StatsConstant.DATE, System.currentTimeMillis());
            jSONObject.put(com.ksyun.media.player.d.d.o, this.aq);
            if (TextUtils.isEmpty(this.ap)) {
                jSONObject.put(StatsConstant.STREAM_ID, b.d);
            } else {
                jSONObject.put(StatsConstant.STREAM_ID, this.ap);
            }
            jSONObject.put(com.ksyun.media.player.d.d.S, jSONObject.length() + o);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        com.ksyun.media.player.d.c.a().a(jSONObject);
        if (this.al) {
            notifyOnLogEvent(jSONObject.toString());
        }
        com.ksyun.media.player.d.c.a().a(this.V);
    }

    private void h() {
        boolean z = true;
        if (this.an == null || this.an.startsWith("http")) {
            if (this.c != null) {
                int analyzeDnsTime = this.c.getAnalyzeDnsTime();
                int connectTime = this.c.getConnectTime();
                int firstDataTime = this.c.getFirstDataTime();
                synchronized (this.aA) {
                    if (this.aB) {
                        return;
                    }
                    boolean z2;
                    long downloadDataSize = getDownloadDataSize();
                    downloadDataSize = (downloadDataSize * 1000) / (System.currentTimeMillis() - this.aj);
                    if (analyzeDnsTime > 80) {
                        z2 = true;
                    } else {
                        z2 = e;
                    }
                    if (connectTime > 30) {
                        z2 = true;
                    }
                    if (firstDataTime > l) {
                        z2 = true;
                    }
                    if (downloadDataSize > 0 && downloadDataSize < 200) {
                        z2 = true;
                    }
                    if (this.c.getHttpCode() < 400) {
                        z = z2;
                    }
                }
            } else {
                z = false;
            }
            a(z, e);
        }
    }

    private void i() {
        JSONObject jSONObject = new JSONObject();
        Object c = c(ax);
        if (c == null) {
            c = "unsupport";
        }
        try {
            jSONObject.put(StatsConstant.ID, this.am.toString());
            jSONObject.put(StatsConstant.BODY_TYPE, com.ksyun.media.player.d.d.az);
            jSONObject.put(StatsConstant.ACTION_ID, this.ao);
            jSONObject.put(StatsConstant.DATE, System.currentTimeMillis());
            jSONObject.put(com.ksyun.media.player.d.d.ai, this.aw);
            jSONObject.put(com.ksyun.media.player.d.d.al, c);
            jSONObject.put(com.ksyun.media.player.d.d.aj, this.az);
            jSONObject.put(com.ksyun.media.player.d.d.o, this.aq);
            if (TextUtils.isEmpty(this.ap)) {
                jSONObject.put(StatsConstant.STREAM_ID, b.d);
            } else {
                jSONObject.put(StatsConstant.STREAM_ID, this.ap);
            }
            jSONObject.put(com.ksyun.media.player.d.d.S, jSONObject.length() + o);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.aw = com.ksyun.media.player.d.d.av;
        ax = null;
        com.ksyun.media.player.d.c.a().a(jSONObject);
    }

    private void a(String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject != null) {
                    this.as = jSONObject.getString(StatsConstant.RESPONSE_KEY_ClientIP);
                    this.at = jSONObject.getString(StatsConstant.RESPONSE_KEY_LocalDnsIP);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.V.obtainMessage(MEDIA_LOG_REPORT, p, e).sendToTarget();
    }

    private void j() {
        if (this.al) {
            String _getLinkLatencyInfo = _getLinkLatencyInfo(this.aq);
            com.ksyun.media.player.d.c.a().a(_getLinkLatencyInfo);
            notifyOnLogEvent(_getLinkLatencyInfo);
        }
    }

    public void reset() {
        a(false);
        f();
        if (!(this.aC == null || this.aC.isEmpty())) {
            this.aC.clear();
        }
        if (this.aE != null) {
            this.aE.a();
        }
        this.aE = null;
        synchronized (this) {
            this.aB = true;
            _reset();
        }
        this.V.removeCallbacksAndMessages(null);
        this.Z = e;
        this.aa = e;
    }

    public void setLooping(boolean z) {
        int i = z ? e : o;
        setOption((int) i, "loop", (long) i);
        _setLoopCount(i);
    }

    public boolean isLooping() {
        if (_getLoopCount() != o) {
            return true;
        }
        return false;
    }

    public void softReset() {
        a(false);
        f();
        synchronized (this) {
            this.aB = true;
            _softReset();
        }
        this.V.removeCallbacksAndMessages(null);
        this.Z = e;
        this.aa = e;
    }

    public void setVideoRenderingState(int i) {
        if (i > o || i < 0) {
            Log.e(d, "setVideoRenderingState wrong parameter:" + i);
        } else {
            _setVideoRenderingState(i);
        }
    }

    public void setSpeed(float f) {
        _setPropertyFloat(s, f);
    }

    public float getSpeed() {
        return _getPropertyFloat(s, 0.0f);
    }

    public int getVideoDecoder() {
        return (int) _getPropertyLong(C, 0);
    }

    public float getVideoOutputFramesPerSecond() {
        return _getPropertyFloat(r, 0.0f);
    }

    public float getVideoDecodeFramesPerSecond() {
        return _getPropertyFloat(q, 0.0f);
    }

    public long getDecodedDataSize() {
        return _getPropertyLong(x, 0);
    }

    public long getDownloadDataSize() {
        return _getPropertyLong(y, 0);
    }

    public String getServerAddress() {
        return _getPropertyString(A);
    }

    public int bufferEmptyCount() {
        return this.ad;
    }

    public float bufferEmptyDuration() {
        return (float) (this.ag / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD);
    }

    public long getVideoCachedDuration() {
        return _getPropertyLong(G, 0);
    }

    public long getAudioCachedDuration() {
        return _getPropertyLong(H, 0);
    }

    public long getVideoCachedBytes() {
        return _getPropertyLong(I, 0);
    }

    public long getAudioCachedBytes() {
        return _getPropertyLong(J, 0);
    }

    public long getVideoCachedPackets() {
        return _getPropertyLong(K, 0);
    }

    public long getAudioCachedPackets() {
        return _getPropertyLong(L, 0);
    }

    private void b(String str) {
        _setPropertyString(B, str);
    }

    public long getCurrentPts() {
        return _getPropertyLong(M, 0);
    }

    public long getCurrentVideoPts() {
        return _getPropertyLong(N, 0);
    }

    public long getStreamStartTime() {
        return _getPropertyLong(O, 0);
    }

    public MediaInfo getMediaInfo() {
        String[] split;
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.mMediaPlayerName = "ksyplayer";
        Object _getVideoCodecInfo = _getVideoCodecInfo();
        if (!TextUtils.isEmpty(_getVideoCodecInfo)) {
            split = _getVideoCodecInfo.split(",");
            if (split.length >= p) {
                mediaInfo.mVideoDecoder = split[e];
                mediaInfo.mVideoDecoderImpl = split[o];
            } else if (split.length >= o) {
                mediaInfo.mVideoDecoder = split[e];
                mediaInfo.mVideoDecoderImpl = b.d;
            }
        }
        _getVideoCodecInfo = _getAudioCodecInfo();
        if (!TextUtils.isEmpty(_getVideoCodecInfo)) {
            split = _getVideoCodecInfo.split(",");
            if (split.length >= p) {
                mediaInfo.mAudioDecoder = split[e];
                mediaInfo.mAudioDecoderImpl = split[o];
            } else if (split.length >= o) {
                mediaInfo.mAudioDecoder = split[e];
                mediaInfo.mAudioDecoderImpl = b.d;
            }
        }
        try {
            mediaInfo.mMeta = KSYMediaMeta.parse(getMediaMeta());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return mediaInfo;
    }

    public void setLogEnabled(boolean z) {
    }

    public boolean isPlayable() {
        return true;
    }

    public void setOption(int i, String str, String str2) {
        _setOption(i, str, str2);
    }

    public void setOption(int i, String str, long j) {
        _setOption(i, str, j);
    }

    public Bundle getMediaMeta() {
        if (this.al) {
            return _getMediaMeta();
        }
        return null;
    }

    public static String getColorFormatName(int i) {
        return _getColorFormatName(i);
    }

    public void setAudioStreamType(int i) {
    }

    public void setKeepInBackground(boolean z) {
    }

    private void setMediaPlayer(long j) {
        this.P = j;
    }

    private long getMediaPlayer() {
        return this.P;
    }

    private void setMediaDataSource(long j) {
        this.Q = j;
    }

    private long getMediaDataSource() {
        return this.Q;
    }

    public void setTimeout(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            Log.w(d, "Wrong parameters, prepareTimeout:" + i + ", readTimeout:" + i2);
            return;
        }
        this.aD = i;
        _setTimeout(i, i2);
    }

    public void setBufferSize(int i) {
        if (i <= 0) {
            i = e;
        }
        _setBufferSize(i);
    }

    public void setDecodeMode(KSYDecodeMode kSYDecodeMode) {
        switch (AnonymousClass1.a[kSYDecodeMode.ordinal()]) {
            case o /*1*/:
                this.aw = com.ksyun.media.player.d.d.av;
                break;
            case p /*2*/:
            case h /*3*/:
                this.aw = com.ksyun.media.player.d.d.au;
                break;
        }
        _setDecodeMode(kSYDecodeMode.ordinal());
    }

    public KSYQosInfo getStreamQosInfo() {
        Bundle _getQosInfo = _getQosInfo();
        if (_getQosInfo == null || !this.al) {
            return null;
        }
        KSYQosInfo kSYQosInfo = new KSYQosInfo();
        kSYQosInfo.audioBufferByteLength = _getQosInfo.getInt(KSYQosInfo.AUDIO_BUFFER_BYTE, e);
        kSYQosInfo.audioBufferTimeLength = _getQosInfo.getInt(KSYQosInfo.AUDIO_BUFFER_TIME, e);
        kSYQosInfo.audioTotalDataSize = _getQosInfo.getLong(KSYQosInfo.AUDIO_TOTAL_DATA_SIZE, 0);
        kSYQosInfo.videoBufferByteLength = _getQosInfo.getInt(KSYQosInfo.VIDEO_BUFFER_BYTE, e);
        kSYQosInfo.videoBufferTimeLength = _getQosInfo.getInt(KSYQosInfo.VIDEO_BUFFER_TIME, e);
        kSYQosInfo.videoTotalDataSize = _getQosInfo.getLong(KSYQosInfo.VIDEO_TOTAL_DATA_SIZE, 0);
        kSYQosInfo.totalDataSize = _getQosInfo.getLong(KSYQosInfo.TOTAL_DATA_BYTES, 0);
        return kSYQosInfo;
    }

    public void setBufferTimeMax(float f) {
        this.av = f;
        _setPropertyFloat(z, f);
    }

    public float getBufferTimeMax() {
        return _getPropertyFloat(z, 0.0f);
    }

    public void setVideoScalingMode(int i) {
        _setVideoScalingMode(i);
    }

    public Bitmap getScreenShot() {
        if (this.Z <= 0 || this.aa <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(this.Z, this.aa, Config.RGB_565);
        _getScreenShot(createBitmap);
        return createBitmap;
    }

    public boolean setRotateDegree(int i) {
        return _setRotateDegree(i);
    }

    public void setPlayerMute(int i) {
        _setPlayerMute(i);
    }

    public boolean setMirror(boolean z) {
        return _setMirror(z);
    }

    public String getLocalDnsIP() {
        return this.at;
    }

    public String getClientIP() {
        return this.as;
    }

    public void setDeinterlaceMode(KSYDeinterlaceMode kSYDeinterlaceMode) {
        if (kSYDeinterlaceMode == KSYDeinterlaceMode.KSY_Deinterlace_Close || kSYDeinterlaceMode == KSYDeinterlaceMode.KSY_Deinterlace_Auto) {
            native_enableDeinterlace(false);
        } else {
            native_enableDeinterlace(true);
        }
    }

    public void setVideoOffset(float f, float f2) {
        float f3;
        float f4 = 1.0f;
        float f5 = -1.0f;
        if (f < -1.0f) {
            f3 = -1.0f;
        } else {
            f3 = f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        if (f2 >= -1.0f) {
            f5 = f2;
        }
        if (f5 <= 1.0f) {
            f4 = f5;
        }
        native_setVideoOffset(f3, f4);
    }

    public void setPlayableRanges(long j, long j2) {
        if ((j >= 0 || j2 >= 0) && (j2 <= 0 || j <= j2)) {
            native_setPlayableRanges(j, j2);
            return;
        }
        throw new IllegalArgumentException("Wrong Input Arguments, start time:" + j + ", end time:" + j2);
    }

    public void addTimedTextSource(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Wrong Input Argument, path can't be NULL!");
        }
        native_addTimedTextSource(str);
    }

    public void enableFastPlayMode(boolean z) {
        native_enableFastPlayMode(z);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        native_finalize();
    }

    @com.ksyun.media.player.a.b
    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        if (obj != null) {
            KSYMediaPlayer kSYMediaPlayer = (KSYMediaPlayer) ((WeakReference) obj).get();
            if (kSYMediaPlayer != null) {
                if (i == m && i2 == p) {
                    kSYMediaPlayer.start();
                }
                if (kSYMediaPlayer.V != null) {
                    kSYMediaPlayer.V.sendMessage(kSYMediaPlayer.V.obtainMessage(i, i2, i3, obj2));
                }
            }
        }
    }

    public void setOnControlMessageListener(d dVar) {
        this.aL = dVar;
    }

    public void setOnNativeInvokeListener(f fVar) {
        this.aM = fVar;
    }

    @com.ksyun.media.player.a.b
    private static boolean onNativeInvoke(Object obj, int i, Bundle bundle) {
        Object[] objArr = new Object[o];
        objArr[e] = Integer.valueOf(i);
        com.ksyun.media.player.c.a.b(d, "onNativeInvoke %d", objArr);
        if (obj == null || !(obj instanceof WeakReference)) {
            throw new IllegalStateException("<null weakThiz>.onNativeInvoke()");
        }
        KSYMediaPlayer kSYMediaPlayer = (KSYMediaPlayer) ((WeakReference) obj).get();
        if (kSYMediaPlayer == null) {
            throw new IllegalStateException("<null weakPlayer>.onNativeInvoke()");
        }
        f fVar = kSYMediaPlayer.aM;
        if (fVar != null && fVar.a(i, bundle)) {
            return true;
        }
        switch (i) {
            case AVFrameBase.FLAG_DETACH_NATIVE_MODULE /*65536*/:
                d dVar = kSYMediaPlayer.aL;
                if (dVar == null) {
                    return false;
                }
                int i2 = bundle.getInt(f.f, -1);
                if (i2 < 0) {
                    throw new InvalidParameterException("onNativeInvoke(invalid segment index)");
                }
                String a = dVar.a(i2);
                if (a == null) {
                    throw new RuntimeException(new IOException("onNativeInvoke() = <NULL newUrl>"));
                }
                bundle.putString(StatsConstant.STREAM_URL, a);
                return true;
            default:
                return false;
        }
    }

    public void setOnMediaCodecSelectListener(e eVar) {
        this.aN = eVar;
    }

    public void setOnVideoTextureListener(OnVideoTextureListener onVideoTextureListener) {
        this.aO = onVideoTextureListener;
    }

    public void setOnAudioPCMAvailableListener(OnAudioPCMListener onAudioPCMListener) {
        if (this.aQ == null) {
            this.aQ = ByteBuffer.allocate(176000);
        }
        synchronized (this.aG) {
            this.aP = onAudioPCMListener;
            if (this.aP != null) {
                native_setPCMBuffer(getMediaPlayer(), this.aQ);
            } else {
                native_setPCMBuffer(getMediaPlayer(), null);
            }
        }
    }

    public void _onAudioPCMReady(ByteBuffer byteBuffer, long j, int i, int i2, int i3) {
        synchronized (this.aG) {
            if (this.aP != null) {
                this.aP.onAudioPCMAvailable(this, byteBuffer, j, i2, i, i3);
            }
        }
    }

    public void resetListeners() {
        super.resetListeners();
        this.aN = null;
        this.aQ = null;
    }

    @TargetApi(21)
    private static int checkMediaCodecSupportedResolution(MediaCodecInfo mediaCodecInfo, String str, int i, int i2) {
        VideoCapabilities videoCapabilities = mediaCodecInfo.getCapabilitiesForType(str).getVideoCapabilities();
        if (videoCapabilities.getSupportedWidths().contains(Integer.valueOf(i)) && videoCapabilities.getSupportedHeights().contains(Integer.valueOf(i2))) {
            return e;
        }
        return -1;
    }

    @com.ksyun.media.player.a.b
    private static String onSelectCodec(Object obj, String str, int i, int i2, int i3, int i4) {
        if (obj == null || !(obj instanceof WeakReference)) {
            return null;
        }
        KSYMediaPlayer kSYMediaPlayer = (KSYMediaPlayer) ((WeakReference) obj).get();
        if (kSYMediaPlayer == null) {
            return null;
        }
        e eVar = kSYMediaPlayer.aN;
        if (eVar == null) {
            eVar = a.a;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (KSYHardwareDecodeWhiteList.getInstance().getCurrentStatus() != 12) {
            ax = eVar.a(kSYMediaPlayer, str, i, i2, i3, i4);
        } else if (str.equalsIgnoreCase("video/avc")) {
            if (KSYHardwareDecodeWhiteList.getInstance().supportHardwareDecodeH264()) {
                ax = com.ksyun.media.player.e.a.a(kSYMediaPlayer.T, com.ksyun.media.player.e.a.d, null);
            }
        } else if (!str.equalsIgnoreCase("video/hevc")) {
            ax = eVar.a(kSYMediaPlayer, str, i, i2, i3, i4);
        } else if (KSYHardwareDecodeWhiteList.getInstance().supportHardwareDecodeH265()) {
            ax = com.ksyun.media.player.e.a.a(kSYMediaPlayer.T, com.ksyun.media.player.e.a.e, null);
        }
        return ax;
    }

    @TargetApi(14)
    public SurfaceTexture createSurfaceTexture() {
        this.aH = com.ksyun.media.player.util.a.a(36197);
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.aH);
        surfaceTexture.setOnFrameAvailableListener(this.aI);
        return surfaceTexture;
    }

    public void setVideoRawDataListener(OnVideoRawDataListener onVideoRawDataListener) {
        synchronized (this.aF) {
            this.aR = onVideoRawDataListener;
            if (onVideoRawDataListener == null) {
                native_enableVideoRawDataCallback(false);
            } else {
                native_enableVideoRawDataCallback(true);
            }
        }
    }

    public void addVideoRawBuffer(byte[] bArr) {
        native_addVideoRawBuffer(bArr);
    }

    @com.ksyun.media.player.a.b
    private static void onVideoRawDataReady(Object obj, byte[] bArr, int i, int i2, int i3, int i4, long j) {
        WeakReference weakReference = (WeakReference) obj;
        if (weakReference != null) {
            KSYMediaPlayer kSYMediaPlayer = (KSYMediaPlayer) weakReference.get();
            if (kSYMediaPlayer != null && kSYMediaPlayer.aF != null) {
                synchronized (kSYMediaPlayer.aF) {
                    if (kSYMediaPlayer.aR != null) {
                        kSYMediaPlayer.aR.onVideoRawDataAvailable(kSYMediaPlayer, bArr, i, i2, i3, i4, j);
                    }
                }
            }
        }
    }

    @TargetApi(16)
    private String c(String str) {
        if (VERSION.SDK_INT < 16) {
            return "unsupport";
        }
        if (this.ay != null) {
            return this.ay;
        }
        ArrayList arrayList = new ArrayList();
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = e; i < codecCount; i += o) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                if (supportedTypes != null) {
                    int length = supportedTypes.length;
                    for (int i2 = e; i2 < length; i2 += o) {
                        String str2 = supportedTypes[i2];
                        if (!TextUtils.isEmpty(str2) && (str2.equalsIgnoreCase("video/avc") || str2.equalsIgnoreCase("video/hevc"))) {
                            f a = f.a(codecInfoAt, str2);
                            if (!(a == null || a.j == m)) {
                                str2 = codecInfoAt.getName();
                                if (str != null && str.equalsIgnoreCase(str2)) {
                                    str2 = str2 + "*";
                                }
                                if (!str2.isEmpty()) {
                                    if (this.ay == null) {
                                        this.ay = str2;
                                    } else if (this.ay.isEmpty()) {
                                        this.ay = str2;
                                    } else {
                                        this.ay += "," + str2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return this.ay;
    }
}
