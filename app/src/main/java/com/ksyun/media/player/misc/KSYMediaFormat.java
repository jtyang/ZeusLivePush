package com.ksyun.media.player.misc;

import android.annotation.TargetApi;
import android.text.TextUtils;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.player.KSYMediaMeta.KSYStreamMeta;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class KSYMediaFormat implements c {
    public static final String CODEC_NAME_H264 = "h264";
    public static final String KEY_IJK_BIT_RATE_UI = "ijk-bit-rate-ui";
    public static final String KEY_IJK_CHANNEL_UI = "ijk-channel-ui";
    public static final String KEY_IJK_CODEC_LONG_NAME_UI = "ijk-codec-long-name-ui";
    public static final String KEY_IJK_CODEC_PIXEL_FORMAT_UI = "ijk-pixel-format-ui";
    public static final String KEY_IJK_CODEC_PROFILE_LEVEL_UI = "ijk-profile-level-ui";
    public static final String KEY_IJK_FRAME_RATE_UI = "ijk-frame-rate-ui";
    public static final String KEY_IJK_RESOLUTION_UI = "ijk-resolution-ui";
    public static final String KEY_IJK_SAMPLE_RATE_UI = "ijk-sample-rate-ui";
    private static final Map<String, a> d;
    public final KSYStreamMeta mMediaFormat;

    private static abstract class a {
        protected abstract String a(KSYMediaFormat kSYMediaFormat);

        private a() {
        }

        public String b(KSYMediaFormat kSYMediaFormat) {
            String a = a(kSYMediaFormat);
            if (TextUtils.isEmpty(a)) {
                return a();
            }
            return a;
        }

        protected String a() {
            return "N/A";
        }
    }

    public KSYMediaFormat(KSYStreamMeta kSYStreamMeta) {
        d.put(KEY_IJK_CODEC_LONG_NAME_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            public String a(KSYMediaFormat kSYMediaFormat) {
                return this.a.mMediaFormat.getString(KSYMediaMeta.IJKM_KEY_CODEC_LONG_NAME);
            }
        });
        d.put(KEY_IJK_BIT_RATE_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                int integer = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_BITRATE);
                if (integer <= 0) {
                    return null;
                }
                if (integer < StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD) {
                    return String.format(Locale.US, "%d bit/s", new Object[]{Integer.valueOf(integer)});
                }
                return String.format(Locale.US, "%d kb/s", new Object[]{Integer.valueOf(integer / StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD)});
            }
        });
        d.put(KEY_IJK_CODEC_PROFILE_LEVEL_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                Object string = kSYMediaFormat.getString(KSYMediaMeta.IJKM_KEY_CODEC_PROFILE);
                if (TextUtils.isEmpty(string)) {
                    return null;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                string = kSYMediaFormat.getString(KSYMediaMeta.IJKM_KEY_CODEC_NAME);
                if (!TextUtils.isEmpty(string) && string.equalsIgnoreCase(KSYMediaFormat.CODEC_NAME_H264)) {
                    int integer = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_CODEC_LEVEL);
                    if (integer < 10) {
                        return stringBuilder.toString();
                    }
                    stringBuilder.append(" Profile Level ");
                    stringBuilder.append((integer / 10) % 10);
                    if (integer % 10 != 0) {
                        stringBuilder.append(".");
                        stringBuilder.append(integer % 10);
                    }
                }
                return stringBuilder.toString();
            }
        });
        d.put(KEY_IJK_CODEC_PIXEL_FORMAT_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                return kSYMediaFormat.getString(KSYMediaMeta.IJKM_KEY_CODEC_PIXEL_FORMAT);
            }
        });
        d.put(KEY_IJK_RESOLUTION_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                int integer = kSYMediaFormat.getInteger(c.b);
                int integer2 = kSYMediaFormat.getInteger(c.c);
                int integer3 = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_SAR_NUM);
                int integer4 = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_SAR_DEN);
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                if (integer3 <= 0 || integer4 <= 0) {
                    return String.format(Locale.US, "%d x %d", new Object[]{Integer.valueOf(integer), Integer.valueOf(integer2)});
                }
                return String.format(Locale.US, "%d x %d [SAR %d:%d]", new Object[]{Integer.valueOf(integer), Integer.valueOf(integer2), Integer.valueOf(integer3), Integer.valueOf(integer4)});
            }
        });
        d.put(KEY_IJK_FRAME_RATE_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                int integer = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_FPS_NUM);
                int integer2 = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_FPS_DEN);
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                return String.valueOf(((float) integer) / ((float) integer2));
            }
        });
        d.put(KEY_IJK_SAMPLE_RATE_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                if (kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_SAMPLE_RATE) <= 0) {
                    return null;
                }
                return String.format(Locale.US, "%d Hz", new Object[]{Integer.valueOf(kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_SAMPLE_RATE))});
            }
        });
        d.put(KEY_IJK_CHANNEL_UI, new a() {
            final /* synthetic */ KSYMediaFormat a;

            {
                this.a = r2;
            }

            protected String a(KSYMediaFormat kSYMediaFormat) {
                int integer = kSYMediaFormat.getInteger(KSYMediaMeta.IJKM_KEY_CHANNEL_LAYOUT);
                if (integer <= 0) {
                    return null;
                }
                if (((long) integer) == 4) {
                    return "mono";
                }
                if (((long) integer) == 3) {
                    return "stereo";
                }
                return String.format(Locale.US, "%x", new Object[]{Integer.valueOf(integer)});
            }
        });
        this.mMediaFormat = kSYStreamMeta;
    }

    @TargetApi(16)
    public int getInteger(String str) {
        if (this.mMediaFormat == null) {
            return 0;
        }
        return this.mMediaFormat.getInt(str);
    }

    public String getString(String str) {
        if (this.mMediaFormat == null) {
            return null;
        }
        if (d.containsKey(str)) {
            return ((a) d.get(str)).b(this);
        }
        return this.mMediaFormat.getString(str);
    }

    static {
        d = new HashMap();
    }
}
