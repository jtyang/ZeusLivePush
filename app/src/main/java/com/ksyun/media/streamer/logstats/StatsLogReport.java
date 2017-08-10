package com.ksyun.media.streamer.logstats;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.ksy.statlibrary.interval.IntervalResultListener;
import com.ksy.statlibrary.log.LogClient;
import com.ksy.statlibrary.log.LogParams;
import com.ksy.statlibrary.util.PreferenceUtil;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.publisher.RtmpPublisher;
import com.ksyun.media.streamer.util.StringWrapper;
import com.ksyun.media.streamer.util.TimeDeltaUtil;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class StatsLogReport {
    public static final int ERROR_MODEL_TYPE_AUDIOCAPTURE = 1;
    public static final int ERROR_MODEL_TYPE_CAMERACAPTURE = 2;
    public static final int ERROR_MODEL_TYPE_ENCODE_AUDIO = 3;
    public static final int ERROR_MODEL_TYPE_ENCODE_VIDEO = 4;
    public static final int ERROR_MODEL_TYPE_PUBLISH = 5;
    private static StatsLogReport aj = null;
    private static final int b = 44100;
    private static final int f = 0;
    private static final int g = 1;
    private static final int h = 2;
    private boolean A;
    private float B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private String I;
    private String J;
    private int K;
    private int L;
    private String M;
    private int N;
    private String O;
    private int P;
    private String Q;
    private int R;
    private float S;
    private String T;
    private int U;
    private String V;
    private long W;
    private int X;
    private int Y;
    private int Z;
    Runnable a;
    private int aa;
    private float ab;
    private int ac;
    private long ad;
    private int ae;
    private int af;
    private int ag;
    private int ah;
    private boolean ai;
    private IntervalResultListener ak;
    private final String c;
    private StringWrapper d;
    private Thread e;
    private volatile int i;
    private String j;
    private String k;
    private String l;
    private String m;
    private volatile boolean n;
    private volatile boolean o;
    private StringBuilder p;
    private JSONObject q;
    private d r;
    private Timer s;
    private Context t;
    private boolean u;
    private boolean v;
    private OnLogEventListener w;
    private int x;
    private boolean y;
    private boolean z;

    public interface OnLogEventListener {
        void onLogEvent(StringBuilder stringBuilder);
    }

    private class a extends AsyncTask<String, Void, Void> {
        private a() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected Void doInBackground(String... r9) {
            /*
            r8 = this;
            r3 = 0;
            r2 = -1;
            r0 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00a5, IOException -> 0x007f, all -> 0x008c }
            r1 = "http://trace-ldns.ksyun.com/getlocaldns";
            r0.<init>(r1);	 Catch:{ MalformedURLException -> 0x00a5, IOException -> 0x007f, all -> 0x008c }
            r0 = r0.openConnection();	 Catch:{ MalformedURLException -> 0x00a5, IOException -> 0x007f, all -> 0x008c }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x00a5, IOException -> 0x007f, all -> 0x008c }
            r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
            r0.setConnectTimeout(r1);	 Catch:{ MalformedURLException -> 0x00aa, IOException -> 0x009a, all -> 0x0093 }
            r1 = r0.getResponseCode();	 Catch:{ MalformedURLException -> 0x00aa, IOException -> 0x009a, all -> 0x0093 }
            r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r1 != r2) goto L_0x0075;
        L_0x001c:
            r2 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r4 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r2.<init>(r4);	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r4 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r4.<init>(r2);	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r5 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r6 = "";
            r5.<init>(r6);	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
        L_0x0031:
            r6 = r4.readLine();	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            if (r6 == 0) goto L_0x0066;
        L_0x0037:
            r5.append(r6);	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            goto L_0x0031;
        L_0x003b:
            r2 = move-exception;
            r7 = r2;
            r2 = r0;
            r0 = r1;
            r1 = r7;
        L_0x0040:
            r1.printStackTrace();	 Catch:{ all -> 0x0097 }
            if (r2 == 0) goto L_0x0048;
        L_0x0045:
            r2.disconnect();
        L_0x0048:
            r1 = "StatsLogReport";
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r4 = "dns check result:";
            r2 = r2.append(r4);
            r0 = r2.append(r0);
            r0 = r0.toString();
            android.util.Log.i(r1, r0);
            r0 = com.ksyun.media.streamer.logstats.StatsLogReport.this;
            r0.f();
            return r3;
        L_0x0066:
            r6 = com.ksyun.media.streamer.logstats.StatsLogReport.this;	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r5 = r5.toString();	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r6.a(r5);	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r2.close();	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            r4.close();	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
        L_0x0075:
            r0.disconnect();	 Catch:{ MalformedURLException -> 0x003b, IOException -> 0x009f, all -> 0x0093 }
            if (r0 == 0) goto L_0x00af;
        L_0x007a:
            r0.disconnect();
            r0 = r1;
            goto L_0x0048;
        L_0x007f:
            r0 = move-exception;
            r1 = r0;
            r0 = r2;
            r2 = r3;
        L_0x0083:
            r1.printStackTrace();	 Catch:{ all -> 0x0097 }
            if (r2 == 0) goto L_0x0048;
        L_0x0088:
            r2.disconnect();
            goto L_0x0048;
        L_0x008c:
            r0 = move-exception;
        L_0x008d:
            if (r3 == 0) goto L_0x0092;
        L_0x008f:
            r3.disconnect();
        L_0x0092:
            throw r0;
        L_0x0093:
            r1 = move-exception;
            r3 = r0;
            r0 = r1;
            goto L_0x008d;
        L_0x0097:
            r0 = move-exception;
            r3 = r2;
            goto L_0x008d;
        L_0x009a:
            r1 = move-exception;
            r7 = r0;
            r0 = r2;
            r2 = r7;
            goto L_0x0083;
        L_0x009f:
            r2 = move-exception;
            r7 = r2;
            r2 = r0;
            r0 = r1;
            r1 = r7;
            goto L_0x0083;
        L_0x00a5:
            r0 = move-exception;
            r1 = r0;
            r0 = r2;
            r2 = r3;
            goto L_0x0040;
        L_0x00aa:
            r1 = move-exception;
            r7 = r0;
            r0 = r2;
            r2 = r7;
            goto L_0x0040;
        L_0x00af:
            r0 = r1;
            goto L_0x0048;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.logstats.StatsLogReport.a.doInBackground(java.lang.String[]):java.lang.Void");
        }
    }

    public static StatsLogReport getInstance() {
        if (aj == null) {
            synchronized (StatsLogReport.class) {
                if (aj == null) {
                    aj = new StatsLogReport();
                }
            }
        }
        return aj;
    }

    public static void uninitInstance() {
        if (aj != null) {
            aj.a();
            aj = null;
        }
    }

    private StatsLogReport() {
        this.c = "StatsLogReport";
        this.i = f;
        this.p = new StringBuilder();
        this.u = true;
        this.v = false;
        this.y = true;
        this.A = false;
        this.I = "0x0";
        this.J = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.M = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.O = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.Q = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        this.ac = StatsConstant.DEFAULT_LOG_INTERVAL;
        this.ad = 0;
        this.ae = f;
        this.af = f;
        this.ag = f;
        this.ah = f;
        this.ai = false;
        this.a = new Runnable() {
            public void run() {
                if (StatsLogReport.this.t == null) {
                    Log.i("StatsLogReport", "init log cleint failed because the context is null");
                    return;
                }
                Log.i("StatsLogReport", "init log cleint begin");
                JSONObject a = a.a(StatsLogReport.this.t);
                if (StatsLogReport.this.v) {
                    Log.i("StatsLogReport", "***initLog header : " + a.toString());
                }
                LogClient.getInstance().addReportParam(new LogParams(a, StatsLogReport.this.d.getStringInfo(StringWrapper.LOG_ACCESS_KEY), StatsLogReport.this.d.getStringInfo(StringWrapper.LOG_SECRET_KEY), c.a()));
                StatsLogReport.this.b(a.toString());
                LogClient.getInstance().sendIntervalRequest(StatsLogReport.this.ak, c.a());
                LogClient.getInstance().start();
                StatsLogReport.this.i = StatsLogReport.h;
                Log.i("StatsLogReport", "init log cleint end");
            }
        };
        this.ak = new IntervalResultListener() {
            public void onGetIntervalFailure(int i, String str) {
                Log.w("StatsLogReport", "get interval failed retCode:" + i);
                StatsLogReport.this.ac = StatsConstant.DEFAULT_LOG_INTERVAL;
            }

            public void onGetIntervalSuccess(int i, int i2) {
                if (i == 0) {
                    Log.d("StatsLogReport", "get interval from server: " + i2);
                } else if (i == -1000) {
                    Log.d("StatsLogReport", "get interval from local sharepreference : " + i2);
                    LogClient.getInstance().sendIntervalRequest(StatsLogReport.this.ak, true, c.a());
                }
                StatsLogReport.this.ac = i2;
            }
        };
        this.d = StringWrapper.getInstance();
        this.t = com.ksyun.media.streamer.util.a.a();
        j();
    }

    public void startStream(String str, String str2) {
        TimeDeltaUtil.getInstance().getTimeDelta();
        this.j = str2;
        this.k = UUID.randomUUID().toString();
        this.T = str;
        this.U = f;
        this.V = null;
        this.W = 0;
        this.X = f;
        this.Y = f;
        this.Z = f;
        this.aa = f;
        this.ab = 0.0f;
        this.ad = 0;
        this.ae = f;
        this.af = f;
        this.ag = f;
        this.ah = f;
        this.ai = false;
    }

    private void a() {
        this.w = null;
        this.q = null;
        this.i = f;
        b.b();
        StringWrapper.unInitInstance();
    }

    public void initLogReport(Context context) {
        this.t = context;
        j();
    }

    private void a(boolean z) {
        this.u = z;
    }

    public boolean isPermitLogReport() {
        return this.u;
    }

    public void setEnableDebugLog(boolean z) {
        this.v = z;
    }

    public boolean getEnableDebugLog() {
        return this.v;
    }

    public void setOnLogEventListener(OnLogEventListener onLogEventListener) {
        this.w = onLogEventListener;
    }

    private void b() {
        if (this.t != null && this.i == 0) {
            PreferenceUtil.init(this.t);
            this.ac = PreferenceUtil.getIntervalTime();
            this.e = new Thread(this.a);
            this.e.start();
            this.i = g;
        } else if (this.t == null) {
            Log.i("StatsLogReport", "the user did not set context");
        } else {
            Log.i("StatsLogReport", "mLogClientInitState:" + this.i);
        }
    }

    public void setAudioPts(long j) {
        this.ad = j;
        if (this.n && this.u && TimeDeltaUtil.getInstance().getTimeDelta() != Long.MAX_VALUE) {
            if (b.a().s() == 0) {
                b.a().c(h());
            }
            if (this.ad - b.a().r() >= ((long) this.ac) && this.n) {
                b(a.a(this.j, this.ad, l() ? ((((long) this.ae) * KSYMediaMeta.AV_CH_SIDE_RIGHT) * 1000) / 44100 : ((((long) this.ae) * KSYMediaMeta.AV_CH_TOP_CENTER) * 1000) / 44100, this.af, this.ah, this.ag, h()), false);
            }
        }
    }

    public long getLogInterval() {
        return (long) this.ac;
    }

    public void setAudioFrameNum(int i) {
        this.ae = i;
    }

    public void setVideoFrameNum(int i) {
        this.af = i;
    }

    public void setAudioSendDelay(int i) {
        this.ag = i;
    }

    public void setVideoEncodeDelay(int i) {
        this.ah = i;
    }

    private void c() {
        if (this.s == null) {
            this.s = new Timer("NetworkStatusStat");
            this.s.schedule(new TimerTask() {
                public void run() {
                    if (StatsLogReport.this.n) {
                        b.a().e();
                        b.a().c();
                        if (b.a().f() % StatsLogReport.ERROR_MODEL_TYPE_PUBLISH == 0 && StatsLogReport.this.n) {
                            StatsLogReport.this.b(a.a(StatsLogReport.this.k, StatsLogReport.this.j, StatsLogReport.this.U, StatsLogReport.this.W, StatsLogReport.this.X, StatsLogReport.this.Y, StatsLogReport.this.h()), false);
                        }
                    }
                }
            }, 300, 1000);
        }
    }

    private void d() {
        if (this.s != null) {
            this.s.cancel();
            this.s = null;
        }
    }

    public void startStreamSuccess() {
        if (this.i != h) {
            Log.i("StatsLogReport", "the log client is not been init or disable logreport");
            return;
        }
        this.n = true;
        this.r.a();
        b.a().v();
        b.a().a(this.ab);
        JSONObject c = c(a.a(this.T, this.k, this.j, this.t, this.V, h()));
        this.q = c;
        if (this.v) {
            Log.i("StatsLogReport", "***startStreamSuccess : " + c.toString());
        }
        b(c, false);
        if (this.u) {
            c();
            e();
        }
        if (this.o) {
            updateFunctionPoint(StatsConstant.FUNCTION_STREAMERRECORDER);
        }
    }

    public void stopStream() {
        Log.d("StatsLogReport", "stopStream :" + this.ai);
        if (this.i == h) {
            this.q = null;
            if (!this.ai) {
                JSONObject c = c(a.a(this.k, this.j, this.t, f, this.U, this.W, this.X, this.Y, h()));
                if (this.v) {
                    Log.i("StatsLogReport", "***stopStream : " + c.toString());
                }
                b(c, false);
            }
            d();
            b.a().v();
            k();
            this.n = false;
        }
        this.ai = false;
    }

    public void reportError(int i, int i2) {
        int i3 = StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN;
        this.ai = true;
        if (this.i == h) {
            switch (i2) {
                case g /*1*/:
                    switch (i) {
                        case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN /*-2005*/:
                            break;
                        case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED /*-2003*/:
                            i3 = StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED;
                            break;
                        default:
                            break;
                    }
                case h /*2*/:
                    switch (i) {
                        case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED /*-2006*/:
                            i3 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED;
                            break;
                        case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED /*-2002*/:
                            i3 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED;
                            break;
                        case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN /*-2001*/:
                            i3 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN;
                            break;
                        default:
                            i3 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN;
                            break;
                    }
                case ERROR_MODEL_TYPE_ENCODE_AUDIO /*3*/:
                    switch (i) {
                        case DeviceInfoTools.REQUEST_ERROR_PARSE_FILED /*-1002*/:
                            i3 = StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED;
                            break;
                        case DeviceInfoTools.REQUEST_ERROR_FAILED /*-1001*/:
                            i3 = RtmpPublisher.ERROR_CONNECT_FAILED;
                            break;
                        default:
                            i3 = RtmpPublisher.ERROR_CONNECT_FAILED;
                            break;
                    }
                case ERROR_MODEL_TYPE_ENCODE_VIDEO /*4*/:
                    switch (i) {
                        case DeviceInfoTools.REQUEST_ERROR_PARSE_FILED /*-1002*/:
                            i3 = StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED;
                            break;
                        case DeviceInfoTools.REQUEST_ERROR_FAILED /*-1001*/:
                            i3 = StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN;
                            break;
                        default:
                            i3 = StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN;
                            break;
                    }
                case ERROR_MODEL_TYPE_PUBLISH /*5*/:
                    switch (i) {
                        case RtmpPublisher.ERROR_AV_ASYNC_ERROR /*-2004*/:
                            i3 = RtmpPublisher.ERROR_AV_ASYNC_ERROR;
                            break;
                        case RtmpPublisher.ERROR_CONNECT_BREAKED /*-1020*/:
                            i3 = StreamerConstants.KSY_STREAMER_ERROR_CONNECT_BREAKED;
                            break;
                        case RtmpPublisher.ERROR_PUBLISH_FAILED /*-1012*/:
                            i3 = RtmpPublisher.ERROR_DNS_PARSE_FAILED;
                            break;
                        case RtmpPublisher.ERROR_CONNECT_FAILED /*-1011*/:
                            i3 = StreamerConstants.KSY_STREAMER_ERROR_CONNECT_FAILED;
                            break;
                        case RtmpPublisher.ERROR_DNS_PARSE_FAILED /*-1010*/:
                            i3 = StreamerConstants.KSY_STREAMER_ERROR_DNS_PARSE_FAILED;
                            break;
                        default:
                            i3 = RtmpPublisher.ERROR_DNS_PARSE_FAILED;
                            break;
                    }
                default:
                    i3 = i;
                    break;
            }
            b.a().v();
            JSONObject c = c(a.a(this.T, this.k, this.j, this.t, i3, this.V, h()));
            if (this.v) {
                Log.i("StatsLogReport", "***reportError : " + c.toString());
            }
            b(c, false);
        }
    }

    public void estBitrateRaise(int i, long j) {
        if (this.i != h) {
            Log.i("StatsLogReport", "the log client is not been init");
        } else if (i < 0 || j < 0) {
            Log.w("StatsLogReport", " estBitrateRaise the input param is wrong, do not report. currentBitrate:" + i + "; estimateBandWidth:" + j);
        } else {
            JSONObject a = a.a(this.T, this.k, this.j, this.t, this.V, true, i, j, this.F, h());
            if (a != null) {
                a = c(a);
                if (this.v) {
                    Log.i("StatsLogReport", "***estBitrateRaise : " + a.toString());
                }
                b(a, false);
            } else if (this.v) {
                Log.w("StatsLogReport", "***estBitrateRaise the wrong notify");
            }
        }
    }

    public void estBitrateDrop(int i, long j) {
        if (this.i != h) {
            Log.i("StatsLogReport", "the log client is not been init");
        } else if (i < 0 || j < 0) {
            Log.w("StatsLogReport", "estBitrateRaise the input param is wrong. do not report. currentBitrate:" + i + "; estimateBandWidth:" + j);
        } else {
            JSONObject a = a.a(this.T, this.k, this.j, this.t, this.V, false, i, j, this.F, h());
            if (a != null) {
                a = c(a);
                if (this.v) {
                    Log.i("StatsLogReport", "***estBitrateDrop : " + a.toString());
                }
                b(a, false);
            } else if (this.v) {
                Log.w("StatsLogReport", "***estBitrateDrop the wrong notify");
            }
        }
    }

    public void frameDataSendSlow() {
        b.a().h();
    }

    public void updateBeautyType(String... strArr) {
        if (this.i != h) {
            Log.i("StatsLogReport", "the log client is not been init or disable logreport");
            return;
        }
        JSONObject a = a.a(i(), this.t, strArr);
        if (a != null) {
            b(a, false);
        } else if (this.v) {
            Log.w("StatsLogReport", "***updateBeautyType report error");
        }
    }

    public void updateAudioFilterType(String... strArr) {
        if (this.i != h) {
            Log.i("StatsLogReport", "the log client is not been init or disable logreport");
            return;
        }
        JSONObject b = a.b(i(), this.t, strArr);
        if (b != null) {
            b(b, false);
        } else if (this.v) {
            Log.w("StatsLogReport", "***updateAudioFilterType report error");
        }
    }

    public void updateFunctionPoint(String str) {
        if (this.i != h) {
            Log.i("StatsLogReport", "the log client is not been init or disable logreport");
            return;
        }
        JSONObject a = a.a(str, this.t, i());
        if (a != null) {
            b(a, false);
        } else if (this.v) {
            Log.w("StatsLogReport", "***updateFunctionPoint report error");
        }
    }

    public void startRecordSuccess() {
        this.o = true;
        if (this.n) {
            updateFunctionPoint(StatsConstant.FUNCTION_STREAMERRECORDER);
        }
    }

    public void stopRecord() {
        this.o = false;
    }

    private synchronized void e() {
        Log.i("StatsLogReport", "begin dns check");
        new a().execute(new String[f]);
    }

    private void f() {
        int i = this.Z;
        int i2 = this.aa;
        b(a.a(this.k, this.j, this.m, i, i2, h()), a(i, i2));
    }

    private void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.l = jSONObject.getString(StatsConstant.RESPONSE_KEY_ClientIP);
            this.m = jSONObject.getString(StatsConstant.RESPONSE_KEY_LocalDnsIP);
            Log.i("StatsLogReport", "dns check ClientIP:" + this.l);
            Log.i("StatsLogReport", "dns check LocalDnsIp:" + this.m);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean a(int i, int i2) {
        if (i < 80 && i2 < 30) {
            return false;
        }
        return true;
    }

    private void a(JSONObject jSONObject) {
        if (this.u && this.w != null && jSONObject != null) {
            if (this.p == null || this.p.length() <= 0) {
                this.p = new StringBuilder();
            } else {
                this.p.delete(f, this.p.length());
            }
            this.p.append(jSONObject.toString());
            this.w.onLogEvent(this.p);
        }
    }

    private void b(String str) {
        if (this.u && this.w != null && str != null) {
            if (this.p == null || this.p.length() <= 0) {
                this.p = new StringBuilder();
            } else {
                this.p.delete(f, this.p.length());
            }
            this.p.append(str);
            this.w.onLogEvent(this.p);
        }
    }

    public void setAudioChannels(int i) {
        this.x = i;
    }

    public void setAudioSampleRateInHz(int i) {
        this.G = i;
    }

    public void setAudioBitrate(int i) {
        this.H = i / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
    }

    public void setAutoAdjustVideoBitrate(boolean z) {
        this.y = z;
    }

    public void setIsLandscape(boolean z) {
        this.z = z;
    }

    public void setIsFrontCameraMirror(boolean z) {
        this.A = z;
    }

    public void setIFrameIntervalSec(float f) {
        this.B = f;
    }

    public void setInitVideoBitrate(int i) {
        this.C = i / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
    }

    public void setMaxVideoBitrate(int i) {
        this.D = i / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
    }

    public void setMinVideoBitrate(int i) {
        this.E = i / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
    }

    public void setBwEstStrategy(int i) {
        this.F = i;
    }

    public void setUrl(String str) {
        this.T = str;
    }

    public void setTargetResolution(int i, int i2) {
        this.I = String.valueOf(i) + "x" + String.valueOf(i2);
    }

    public void setEncodeMethod(int i) {
        this.K = i;
    }

    public void setEncodeFormat(int i) {
        this.L = i;
    }

    public void setAudioEncodeProfile(int i) {
        this.N = i;
    }

    public void setVideoEncodeProfile(int i) {
        this.P = i;
    }

    public void setVideoEncodeScence(int i) {
        this.R = i;
    }

    private void g() {
        switch (this.K) {
            case g /*1*/:
            case ERROR_MODEL_TYPE_ENCODE_AUDIO /*3*/:
                this.J = StatsConstant.ENCODE_SOFT264;
                if (this.L == g) {
                    this.J = StatsConstant.ENCODE_SOFT264;
                } else if (this.L == h) {
                    this.J = StatsConstant.ENCODE_SOFT265;
                }
                if (this.N != ERROR_MODEL_TYPE_ENCODE_VIDEO) {
                    if (this.N != 28) {
                        if (this.N == g) {
                            this.M = StatsConstant.AUDIO_ENCODE_PROFILE_SOFT_LOW;
                            break;
                        }
                    }
                    this.M = StatsConstant.AUDIO_ENCODE_PROFILE_SOFT_HEHE_V2;
                    break;
                }
                this.M = StatsConstant.AUDIO_ENCODE_PROFILE_SOFT_HE;
                break;
                break;
            case h /*2*/:
                this.J = StatsConstant.ENCODE_HARD264;
                if (this.L == g) {
                    this.J = StatsConstant.ENCODE_HARD264;
                } else if (this.L == h) {
                    this.J = StatsConstant.ENCODE_HARD265;
                }
                if (this.N != ERROR_MODEL_TYPE_ENCODE_VIDEO) {
                    if (this.N != 28) {
                        if (this.N == g) {
                            this.M = StatsConstant.AUDIO_ENCODE_PROFILE_HARD_LOW;
                            break;
                        }
                    }
                    this.M = StatsConstant.AUDIO_ENCODE_PROFILE_HARD_HEHE_V2;
                    break;
                }
                this.M = StatsConstant.AUDIO_ENCODE_PROFILE_HARD_HE;
                break;
                break;
        }
        if (this.K == h) {
            this.O = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        } else if (this.P == ERROR_MODEL_TYPE_ENCODE_AUDIO) {
            this.O = StatsConstant.ENCODE_PROFILE_LOWPOWER;
        } else if (this.P == h) {
            this.O = StatsConstant.ENCODE_PROFILE_BALANCE;
        } else if (this.P == g) {
            this.O = StatsConstant.ENCODE_PROFILE_HIGH;
        } else if (this.P == 0) {
            this.O = StatsConstant.ENCODE_SCENCE_DEFAULT;
        }
        if (this.K == h) {
            this.Q = StatsConstant.STAT_CONSTANTS_UNKNOWN;
        } else if (this.R == 0) {
            this.Q = StatsConstant.ENCODE_SCENCE_DEFAULT;
        } else if (this.R == h) {
            this.Q = StatsConstant.ENCODE_SCENCE_GAME;
        } else if (this.R == g) {
            this.Q = StatsConstant.ENCODE_SCENCE_SHOWSELF;
        }
    }

    public void setFrameRate(float f) {
        this.S = f;
    }

    public void setUploadedKBytes(int i) {
        this.U = i;
    }

    public void setRtmpHostIp(String str) {
        this.V = str;
    }

    public void setEncodedFrames(long j) {
        this.W = j;
    }

    public void setRtmpDroppedFrameCount(int i) {
        this.X = i;
    }

    public void setEncodeDroppedFrameCount(int i) {
        this.Y = i;
    }

    public void setDnsParseTime(int i) {
        this.Z = i;
    }

    public void setConnectTime(int i) {
        this.aa = i;
    }

    public void setCurrentBitrate(float f) {
        this.ab = f;
    }

    private long h() {
        long j = 0;
        if (!this.n) {
            return 0;
        }
        long timeDelta = TimeDeltaUtil.getInstance().getTimeDelta();
        if (timeDelta != Long.MAX_VALUE) {
            j = timeDelta;
        }
        return j + System.currentTimeMillis();
    }

    private long i() {
        long timeDelta = TimeDeltaUtil.getInstance().getTimeDelta();
        if (timeDelta == Long.MAX_VALUE) {
            timeDelta = 0;
        }
        return timeDelta + System.currentTimeMillis();
    }

    private void b(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                LogClient.getInstance().put(jSONObject.toString(), c.a());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(JSONObject jSONObject, boolean z) {
        if (jSONObject != null) {
            try {
                LogClient.getInstance().put(jSONObject.toString(), c.a(), z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            try {
                LogClient.getInstance().put(str, c.a(), z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void b(JSONObject jSONObject, boolean z) {
        a(jSONObject, z);
        a(jSONObject);
    }

    private JSONObject c(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                jSONObject.put(StatsConstant.AUDIO_CHANNELS, this.x);
                jSONObject.put(StatsConstant.AUTO_ADJUST_BITRATE, this.y);
                jSONObject.put(StatsConstant.IS_LANDSPACE, this.z);
                jSONObject.put(StatsConstant.IS_FRONT_MIRROR, this.A);
                jSONObject.put(StatsConstant.IFRAME_INTERVAL, (double) this.B);
                jSONObject.put(StatsConstant.MAX_VIDEO_BITRATE, this.D);
                jSONObject.put(StatsConstant.MIN_VIDEO_BITRATE, this.E);
                jSONObject.put(StatsConstant.VIDEO_BITRATE, this.C);
                jSONObject.put(StatsConstant.SAMPLE_AUDIO_RATE, this.G);
                jSONObject.put(StatsConstant.AUDIO_BITRATE, this.H);
                jSONObject.put(StatsConstant.RESOLUTION, this.I);
                jSONObject.put(StatsConstant.FRAME_RATE, (double) this.S);
                g();
                jSONObject.put(StatsConstant.VIDEO_ENCODE_TYPE, this.J);
                jSONObject.put(StatsConstant.AUDIO_ENCODE_TYPE, this.M);
                jSONObject.put(StatsConstant.VIDEO_ENCODE_PROFILE, this.O);
                jSONObject.put(StatsConstant.VIDEO_SCENCE, this.Q);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    private void j() {
        if (this.r == null || this.r.b() == null) {
            this.r = new d(this.t, this.d);
        }
        b();
    }

    private void k() {
        this.ab = 0.0f;
        this.ae = f;
        this.af = f;
        this.ad = 0;
    }

    private boolean l() {
        return this.K == h;
    }
}
