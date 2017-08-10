package com.ksyun.media.streamer.logstats;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.ksy.statlibrary.interval.IntervalTask;
import com.ksy.statlibrary.log.LogClient;
import com.ksyun.media.streamer.publisher.RtmpPublisher;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: StatsLogMaker */
public class a {
    public static JSONObject a(Context context) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(StatsConstant.SDK_TYPE, StatsConstant.SDK_TYPE_VALUE);
                jSONObject.put(StatsConstant.SDK_VERSION, StatsConstant.SDK_VERSION_VALUE);
                jSONObject.put(StatsConstant.SYSTEM_PLATFORM, StatsConstant.SYSTEM_PLATFORM_VALUE);
                jSONObject.put(StatsConstant.SYSTEM_VERSION, VERSION.RELEASE);
                if (context != null) {
                    jSONObject.put(StatsConstant.APP_PACKAGE_NAME, context.getPackageName());
                } else {
                    jSONObject.put(StatsConstant.APP_PACKAGE_NAME, StatsConstant.STAT_CONSTANTS_UNKNOWN);
                }
                jSONObject.put(StatsConstant.DEVICE_MODEL, Build.MODEL);
                String a = c.a(context);
                if (a != null) {
                    jSONObject.put(StatsConstant.DEVICE_ID, a);
                } else {
                    jSONObject.put(StatsConstant.DEVICE_ID, StatsConstant.STAT_CONSTANTS_UNKNOWN);
                }
                jSONObject.put(StatsConstant.LOG_MODULE_VERSION, LogClient.getInstance().getBuildVersion());
                jSONObject.put(StatsConstant.LOG_SDK_VN, RtmpPublisher.INFO_EST_BW_DROP);
            } catch (JSONException e) {
                e = e;
                e.printStackTrace();
                return jSONObject;
            }
        } catch (JSONException e2) {
            JSONException e3;
            JSONException jSONException = e2;
            jSONObject = null;
            e3 = jSONException;
            e3.printStackTrace();
            return jSONObject;
        }
        return jSONObject;
    }

    public static JSONObject b(Context context) {
        return a(context);
    }

    public static JSONObject a(String str, String str2, String str3, Context context, String str4, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.ID, str2);
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_START_STREAMING);
            jSONObject.put(StatsConstant.ACTION_ID, str3);
            jSONObject.put(StatsConstant.STREAM_ID, str3);
            jSONObject.put(StatsConstant.STREAM_URL, str);
            jSONObject.put(StatsConstant.STREAM_STATUS, StatsConstant.STREAM_STATUS_OK);
            jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
            jSONObject.put(StatsConstant.NETWORK_TYPE_DESCRIPTION, c.c(context));
            if (str4 == null) {
                str4 = StatsConstant.SERVER_IP_DEFAULT_VALUE;
            }
            jSONObject.put(StatsConstant.SERVER_IP, str4);
            jSONObject.put(StatsConstant.DATE, j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject a(String str, String str2, String str3, Context context, String str4, boolean z, int i, long j, int i2, long j2) {
        JSONObject jSONObject = new JSONObject();
        try {
            float a = a(z, (float) i);
            jSONObject.put(StatsConstant.ID, str2);
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_EVENT);
            jSONObject.put(StatsConstant.EVENT_TYPE, StatsConstant.EVENT_TYPE_AUTO_BITRATE);
            jSONObject.put(StatsConstant.ACTION_ID, str3);
            jSONObject.put(StatsConstant.STREAM_ID, str3);
            jSONObject.put(StatsConstant.STREAM_URL, str);
            if (z) {
                jSONObject.put(StatsConstant.STREAM_STATUS, StatsConstant.EVENT_TYPE_SUB_AUTOBITRATE_RAISE);
            } else {
                jSONObject.put(StatsConstant.STREAM_STATUS, StatsConstant.EVENT_TYPE_SUB_AUTOBITRATE_DROP);
            }
            jSONObject.put(StatsConstant.EVENT_AUTOBITRATE_THRESHOLD, (double) a);
            jSONObject.put(StatsConstant.EVENT_AUTOBITRATE_BANDWIDTH, j);
            jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
            jSONObject.put(StatsConstant.NETWORK_TYPE_DESCRIPTION, c.c(context));
            if (str4 == null) {
                str4 = StatsConstant.SERVER_IP_DEFAULT_VALUE;
            }
            jSONObject.put(StatsConstant.SERVER_IP, str4);
            if (i2 == 0) {
                jSONObject.put(StatsConstant.BW_ESTIMATE_MODE, StatsConstant.BW_EST_STRATEGY_NORMAL);
            } else if (i2 == 1) {
                jSONObject.put(StatsConstant.BW_ESTIMATE_MODE, StatsConstant.BW_EST_STRATEGY_NEGATIVE);
            }
            jSONObject.put(StatsConstant.DATE, j2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject a(String str, String str2, int i, long j, int i2, int i3, long j2) {
        JSONObject a;
        JSONException e;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.ID, str);
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_NETWORKING_STATUS);
            jSONObject.put(StatsConstant.ACTION_ID, str2);
            jSONObject.put(StatsConstant.STREAM_ID, str2);
            a = a(jSONObject, j);
            try {
                a.put(StatsConstant.DROP_FRAME_COUNT, a(i2 + i3));
                a.put(StatsConstant.DROP_FRAME_COUNT_AM, b(i2));
                a.put(StatsConstant.DROP_FRAME_COUNT_BM, c(i3));
                a.put(StatsConstant.SEND_SLOW_COUNT, a());
                a.put(StatsConstant.UPLOAD_SPEED, f(i));
                a.put(StatsConstant.NETWORK_STAT_FREQUENCY, IntervalTask.TIMEOUT_MILLIS);
                a.put(StatsConstant.DATE, j2);
            } catch (JSONException e2) {
                e = e2;
                e.printStackTrace();
                return a;
            }
        } catch (JSONException e3) {
            JSONException jSONException = e3;
            a = jSONObject;
            e = jSONException;
            e.printStackTrace();
            return a;
        }
        return a;
    }

    public static JSONObject a(String str, long j, long j2, int i, int i2, int i3, long j3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_WLD_UPLOAD);
            jSONObject.put(StatsConstant.STREAM_ID, str);
            jSONObject.put(StatsConstant.END_ROLE, StatsConstant.BODY_TYPE_END_TYPE);
            jSONObject.put(StatsConstant.END_ROLE, StatsConstant.BODY_TYPE_END_TYPE);
            jSONObject.put(StatsConstant.PTS, j);
            jSONObject.put(StatsConstant.PTS_DIFF, a(j));
            jSONObject.put(StatsConstant.AUDIO_DURATION, c(j2));
            jSONObject.put(StatsConstant.TIME_COST, b(j3));
            jSONObject.put(StatsConstant.VIDEO_FRAME_NUM, i);
            jSONObject.put(StatsConstant.AUDIO_DELAY_MIN, d(i3));
            jSONObject.put(StatsConstant.AUDIO_DELAY_MAX, e(i3));
            jSONObject.put(StatsConstant.ENCODE_DELAY, i2);
            jSONObject.put(StatsConstant.SEND_DELAY, i3);
            jSONObject.put(StatsConstant.DATE, j3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject a(String str, String str2, String str3, int i, int i2, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.ID, str);
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_CONNECTION_STATUS);
            jSONObject.put(StatsConstant.ACTION_ID, str2);
            jSONObject.put(StatsConstant.STREAM_ID, str2);
            jSONObject.put(StatsConstant.DNS_PARSE_TIME, i);
            jSONObject.put(StatsConstant.DNS_IP, str3);
            jSONObject.put(StatsConstant.CONNECT_TIME, i2);
            jSONObject.put(StatsConstant.DATE, j);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject a(String str, String str2, String str3, Context context, int i, String str4, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.ID, str2);
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_START_STREAMING);
            jSONObject.put(StatsConstant.ACTION_ID, str3);
            jSONObject.put(StatsConstant.STREAM_ID, str3);
            jSONObject.put(StatsConstant.STREAM_URL, str);
            jSONObject.put(StatsConstant.STREAM_STATUS, StatsConstant.STREAM_STATUS_FAIL);
            jSONObject.put(StatsConstant.STREAM_FAIL_CODE, i);
            jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
            jSONObject.put(StatsConstant.NETWORK_TYPE_DESCRIPTION, c.c(context));
            if (str4 == null) {
                str4 = StatsConstant.SERVER_IP_DEFAULT_VALUE;
            }
            jSONObject.put(StatsConstant.SERVER_IP, str4);
            jSONObject.put(StatsConstant.DATE, j);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject a(String str, String str2, Context context, int i, int i2, long j, int i3, int i4, long j2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.ID, str);
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_END_STREAMING);
            jSONObject.put(StatsConstant.ACTION_ID, str2);
            jSONObject.put(StatsConstant.STREAM_ID, str2);
            jSONObject.put(StatsConstant.STREAM_TIME_LENGTH, c());
            jSONObject.put(StatsConstant.SEND_SLOW_COUNT, b());
            jSONObject.put(StatsConstant.DROP_FRAME_COUNT, i3 + i4);
            jSONObject.put(StatsConstant.DROP_FRAME_COUNT_AM, i3);
            jSONObject.put(StatsConstant.DROP_FRAME_COUNT_BM, i4);
            jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
            jSONObject.put(StatsConstant.NETWORK_TYPE_DESCRIPTION, c.c(context));
            jSONObject.put(StatsConstant.UPLOAD_SIZE, i2);
            jSONObject.put(StatsConstant.ENCODE_FRAME_COUNT, j);
            jSONObject.put(StatsConstant.END_TYPE, i);
            jSONObject.put(StatsConstant.END_ROLE, StatsConstant.BODY_TYPE_END_TYPE);
            jSONObject.put(StatsConstant.DATE, j2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject a(long j, Context context, String... strArr) {
        int i = 1;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_BEAUTY);
            if (strArr.length > 0) {
                StringBuilder stringBuilder = new StringBuilder(strArr[0]);
                if (strArr.length > 1) {
                    while (i < strArr.length) {
                        if (strArr[i] != null) {
                            stringBuilder.append("_");
                            stringBuilder.append(strArr[i]);
                        }
                        i++;
                    }
                }
                jSONObject.put(StatsConstant.BEAUTY_TYPE, stringBuilder.toString());
            }
            jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
            jSONObject.put(StatsConstant.DATE, j);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject b(long j, Context context, String... strArr) {
        int i = 1;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_AUDIO_FILTER);
            if (strArr.length > 0) {
                StringBuilder stringBuilder = new StringBuilder(strArr[0]);
                if (strArr.length > 1) {
                    while (i < strArr.length) {
                        if (strArr[i] != null) {
                            stringBuilder.append("_");
                            stringBuilder.append(strArr[i]);
                        }
                        i++;
                    }
                }
                jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
                jSONObject.put(StatsConstant.AUDIO_FILTER_TYPE, stringBuilder.toString());
            }
            jSONObject.put(StatsConstant.DATE, j);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject a(String str, Context context, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(StatsConstant.LOG_TYPE, 100);
            jSONObject.put(StatsConstant.BODY_TYPE, StatsConstant.BODY_TYPE_FUNCTION_POINT);
            jSONObject.put(StatsConstant.FUNCTION_TYPE, str);
            jSONObject.put(StatsConstant.NETWORK_TYPE, c.b(context));
            jSONObject.put(StatsConstant.DATE, j);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int a(int i) {
        int m = i - b.a().m();
        b.a().b(i);
        return m;
    }

    private static int b(int i) {
        int n = i - b.a().n();
        b.a().c(i);
        return n;
    }

    private static int c(int i) {
        int o = i - b.a().o();
        b.a().d(i);
        return o;
    }

    private static int a() {
        int i = b.a().i();
        int p = i - b.a().p();
        b.a().e(i);
        return p;
    }

    private static long a(long j) {
        long r = j - b.a().r();
        b.a().b(j);
        return r;
    }

    private static long b(long j) {
        long s = j - b.a().s();
        b.a().c(j);
        return s;
    }

    private static int d(int i) {
        return Math.min(i, b.a().t());
    }

    private static int e(int i) {
        int t = b.a().t();
        b.a().f(i);
        return Math.max(i, t);
    }

    private static long c(long j) {
        long u = j - b.a().u();
        b.a().d(j);
        return u;
    }

    public static JSONObject a(JSONObject jSONObject, long j) {
        long l = j - b.a().l();
        b.a().a(j);
        try {
            int i;
            jSONObject.put(StatsConstant.ENCODE_FRAME_COUNT, l);
            if (l < 5) {
                i = 1;
            } else {
                i = (int) (l / 5);
            }
            jSONObject.put(StatsConstant.AVERAGE_FRAME, i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static int f(int i) {
        int k = i - b.a().k();
        b.a().a(i);
        return k / 5;
    }

    private static int b() {
        return b.a().i();
    }

    private static long c() {
        return (long) b.a().f();
    }

    private static float a(boolean z, float f) {
        float q = b.a().q();
        if (z) {
            q = f - q;
            b.a().a(f);
            return q;
        }
        q -= f;
        b.a().a(f);
        return q;
    }
}
