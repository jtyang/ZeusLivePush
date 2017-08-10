package com.ksyun.media.player;

import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class KSYMediaMeta {
    public static final long AV_CH_BACK_CENTER = 256;
    public static final long AV_CH_BACK_LEFT = 16;
    public static final long AV_CH_BACK_RIGHT = 32;
    public static final long AV_CH_FRONT_CENTER = 4;
    public static final long AV_CH_FRONT_LEFT = 1;
    public static final long AV_CH_FRONT_LEFT_OF_CENTER = 64;
    public static final long AV_CH_FRONT_RIGHT = 2;
    public static final long AV_CH_FRONT_RIGHT_OF_CENTER = 128;
    public static final long AV_CH_LAYOUT_2POINT1 = 11;
    public static final long AV_CH_LAYOUT_2_1 = 259;
    public static final long AV_CH_LAYOUT_2_2 = 1539;
    public static final long AV_CH_LAYOUT_3POINT1 = 15;
    public static final long AV_CH_LAYOUT_4POINT0 = 263;
    public static final long AV_CH_LAYOUT_4POINT1 = 271;
    public static final long AV_CH_LAYOUT_5POINT0 = 1543;
    public static final long AV_CH_LAYOUT_5POINT0_BACK = 55;
    public static final long AV_CH_LAYOUT_5POINT1 = 1551;
    public static final long AV_CH_LAYOUT_5POINT1_BACK = 63;
    public static final long AV_CH_LAYOUT_6POINT0 = 1799;
    public static final long AV_CH_LAYOUT_6POINT0_FRONT = 1731;
    public static final long AV_CH_LAYOUT_6POINT1 = 1807;
    public static final long AV_CH_LAYOUT_6POINT1_BACK = 319;
    public static final long AV_CH_LAYOUT_6POINT1_FRONT = 1739;
    public static final long AV_CH_LAYOUT_7POINT0 = 1591;
    public static final long AV_CH_LAYOUT_7POINT0_FRONT = 1735;
    public static final long AV_CH_LAYOUT_7POINT1 = 1599;
    public static final long AV_CH_LAYOUT_7POINT1_WIDE = 1743;
    public static final long AV_CH_LAYOUT_7POINT1_WIDE_BACK = 255;
    public static final long AV_CH_LAYOUT_HEXAGONAL = 311;
    public static final long AV_CH_LAYOUT_MONO = 4;
    public static final long AV_CH_LAYOUT_OCTAGONAL = 1847;
    public static final long AV_CH_LAYOUT_QUAD = 51;
    public static final long AV_CH_LAYOUT_STEREO = 3;
    public static final long AV_CH_LAYOUT_STEREO_DOWNMIX = 1610612736;
    public static final long AV_CH_LAYOUT_SURROUND = 7;
    public static final long AV_CH_LOW_FREQUENCY = 8;
    public static final long AV_CH_LOW_FREQUENCY_2 = 34359738368L;
    public static final long AV_CH_SIDE_LEFT = 512;
    public static final long AV_CH_SIDE_RIGHT = 1024;
    public static final long AV_CH_STEREO_LEFT = 536870912;
    public static final long AV_CH_STEREO_RIGHT = 1073741824;
    public static final long AV_CH_SURROUND_DIRECT_LEFT = 8589934592L;
    public static final long AV_CH_SURROUND_DIRECT_RIGHT = 17179869184L;
    public static final long AV_CH_TOP_BACK_CENTER = 65536;
    public static final long AV_CH_TOP_BACK_LEFT = 32768;
    public static final long AV_CH_TOP_BACK_RIGHT = 131072;
    public static final long AV_CH_TOP_CENTER = 2048;
    public static final long AV_CH_TOP_FRONT_CENTER = 8192;
    public static final long AV_CH_TOP_FRONT_LEFT = 4096;
    public static final long AV_CH_TOP_FRONT_RIGHT = 16384;
    public static final long AV_CH_WIDE_LEFT = 2147483648L;
    public static final long AV_CH_WIDE_RIGHT = 4294967296L;
    public static final String IJKM_KEY_AUDIO_CODEC = "acodec";
    public static final String IJKM_KEY_AUDIO_STREAM = "audio";
    public static final String IJKM_KEY_BITRATE = "bitrate";
    public static final String IJKM_KEY_CHANNEL_LAYOUT = "channel_layout";
    public static final String IJKM_KEY_CODEC_LEVEL = "codec_level";
    public static final String IJKM_KEY_CODEC_LONG_NAME = "codec_long_name";
    public static final String IJKM_KEY_CODEC_NAME = "codec_name";
    public static final String IJKM_KEY_CODEC_PIXEL_FORMAT = "codec_pixel_format";
    public static final String IJKM_KEY_CODEC_PROFILE = "codec_profile";
    public static final String IJKM_KEY_DURATION_US = "duration_us";
    public static final String IJKM_KEY_FORMAT = "format";
    public static final String IJKM_KEY_FPS_DEN = "fps_den";
    public static final String IJKM_KEY_FPS_NUM = "fps_num";
    public static final String IJKM_KEY_HEIGHT = "height";
    public static final String IJKM_KEY_HTTP_ANALYZE_DNS = "analyze_dns_time";
    public static final String IJKM_KEY_HTTP_CODE = "http_code";
    public static final String IJKM_KEY_HTTP_CONNECT_TIME = "connect_time";
    public static final String IJKM_KEY_HTTP_CONTENT_LENGHT = "http_content_length";
    public static final String IJKM_KEY_HTTP_CONTENT_RANGE = "http_content_range";
    public static final String IJKM_KEY_HTTP_FIRST_DATA_TIME = "first_data_time";
    public static final String IJKM_KEY_HTTP_REDIRECT = "http_redirect";
    public static final String IJKM_KEY_HTTP_X_CACHE = "http_x_cache";
    public static final String IJKM_KEY_INFO_STREAM_TYPE = "stream_type";
    public static final String IJKM_KEY_LANGUAGE = "language";
    public static final String IJKM_KEY_OPEN_STREAM_COST = "open_stream_cost";
    public static final String IJKM_KEY_PARSER_INFO_COST = "parser_info_cost";
    public static final String IJKM_KEY_PARSER_INFO_STATUS = "parser_info_status";
    public static final String IJKM_KEY_PREPARE_COST_TIME = "prepare_cost";
    public static final String IJKM_KEY_PREPARE_READ_BYTES = "prepare_read_bytes";
    public static final String IJKM_KEY_SAMPLE_RATE = "sample_rate";
    public static final String IJKM_KEY_SAR_DEN = "sar_den";
    public static final String IJKM_KEY_SAR_NUM = "sar_num";
    public static final String IJKM_KEY_START_US = "start_us";
    public static final String IJKM_KEY_STREAMID = "streamId";
    public static final String IJKM_KEY_STREAMS = "streams";
    public static final String IJKM_KEY_STREAM_INDEX = "stream_index";
    public static final String IJKM_KEY_SUBTITLE_STREAM = "subtitle";
    public static final String IJKM_KEY_TBR_DEN = "tbr_den";
    public static final String IJKM_KEY_TBR_NUM = "tbr_num";
    public static final String IJKM_KEY_TYPE = "type";
    public static final String IJKM_KEY_VIDEO_CODEC = "vcodec";
    public static final String IJKM_KEY_VIDEO_STREAM = "video";
    public static final String IJKM_KEY_WIDTH = "width";
    public static final String IJKM_VAL_TYPE__AUDIO = "audio";
    public static final String IJKM_VAL_TYPE__EXTERNAL_TIMED_TEXT = "external_timed_text";
    public static final String IJKM_VAL_TYPE__SUBTITLE = "subtitle";
    public static final String IJKM_VAL_TYPE__UNKNOWN = "unknown";
    public static final String IJKM_VAL_TYPE__VIDEO = "video";
    public String mACodec;
    public int mAnalyzeDnsTime;
    public KSYStreamMeta mAudioStream;
    public long mBitrate;
    public long mDurationUS;
    public String mFormat;
    public int mHttpCode;
    public int mHttpConnectTime;
    public String mHttpContentLength;
    public String mHttpContentRange;
    public int mHttpFirstDataTime;
    public String mHttpRedirect;
    public String mHttpXCache;
    public Bundle mMediaMeta;
    public int mOpenStreamCostTime;
    public int mParserInfoStatus;
    public int mPrepareCostTime;
    public int mPrepareReadBytes;
    public long mStartUS;
    public String mStreamId;
    public String mStreamType;
    public final ArrayList<KSYStreamMeta> mStreams;
    public String mVCodec;
    public KSYStreamMeta mVideoStream;

    public static class KSYStreamMeta {
        public long mBitrate;
        public long mChannelLayout;
        public String mCodecLongName;
        public String mCodecName;
        public String mCodecProfile;
        public int mFpsDen;
        public int mFpsNum;
        public int mHeight;
        public final int mIndex;
        public String mLanguage;
        public Bundle mMeta;
        public int mSampleRate;
        public int mSarDen;
        public int mSarNum;
        public int mTbrDen;
        public int mTbrNum;
        public String mType;
        public int mWidth;

        public KSYStreamMeta(int i) {
            this.mIndex = i;
        }

        public String getString(String str) {
            return this.mMeta.getString(str);
        }

        public int getInt(String str) {
            return getInt(str, 0);
        }

        public int getInt(String str, int i) {
            Object string = getString(str);
            if (!TextUtils.isEmpty(string)) {
                try {
                    i = Integer.parseInt(string);
                } catch (NumberFormatException e) {
                }
            }
            return i;
        }

        public long getLong(String str) {
            return getLong(str, 0);
        }

        public long getLong(String str, long j) {
            Object string = getString(str);
            if (!TextUtils.isEmpty(string)) {
                try {
                    j = Long.parseLong(string);
                } catch (NumberFormatException e) {
                }
            }
            return j;
        }

        public String getCodecLongNameInline() {
            if (!TextUtils.isEmpty(this.mCodecLongName)) {
                return this.mCodecLongName;
            }
            if (TextUtils.isEmpty(this.mCodecName)) {
                return "N/A";
            }
            return this.mCodecName;
        }

        public String getCodecShortNameInline() {
            if (TextUtils.isEmpty(this.mCodecName)) {
                return "N/A";
            }
            return this.mCodecName;
        }

        public String getResolutionInline() {
            if (this.mWidth <= 0 || this.mHeight <= 0) {
                return "N/A";
            }
            if (this.mSarNum <= 0 || this.mSarDen <= 0) {
                return String.format(Locale.US, "%d x %d", new Object[]{Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight)});
            }
            return String.format(Locale.US, "%d x %d [SAR %d:%d]", new Object[]{Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mSarNum), Integer.valueOf(this.mSarDen)});
        }

        public String getFpsInline() {
            if (this.mFpsNum <= 0 || this.mFpsDen <= 0) {
                return "N/A";
            }
            return String.valueOf(((float) this.mFpsNum) / ((float) this.mFpsDen));
        }

        public String getBitrateInline() {
            if (this.mBitrate <= 0) {
                return "N/A";
            }
            if (this.mBitrate < 1000) {
                return String.format(Locale.US, "%d bit/s", new Object[]{Long.valueOf(this.mBitrate)});
            }
            return String.format(Locale.US, "%d kb/s", new Object[]{Long.valueOf(this.mBitrate / 1000)});
        }

        public String getSampleRateInline() {
            if (this.mSampleRate <= 0) {
                return "N/A";
            }
            return String.format(Locale.US, "%d Hz", new Object[]{Integer.valueOf(this.mSampleRate)});
        }

        public String getChannelLayoutInline() {
            if (this.mChannelLayout <= 0) {
                return "N/A";
            }
            if (this.mChannelLayout == KSYMediaMeta.AV_CH_LAYOUT_MONO) {
                return "mono";
            }
            if (this.mChannelLayout == KSYMediaMeta.AV_CH_LAYOUT_STEREO) {
                return "stereo";
            }
            return String.format(Locale.US, "%x", new Object[]{Long.valueOf(this.mChannelLayout)});
        }
    }

    public KSYMediaMeta() {
        this.mStreams = new ArrayList();
        this.mHttpConnectTime = 0;
        this.mHttpFirstDataTime = 0;
    }

    public String getString(String str) {
        return this.mMediaMeta.getString(str);
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        Object string = getString(str);
        if (!TextUtils.isEmpty(string)) {
            try {
                i = Integer.parseInt(string);
            } catch (NumberFormatException e) {
            }
        }
        return i;
    }

    public long getLong(String str) {
        return getLong(str, 0);
    }

    public long getLong(String str, long j) {
        Object string = getString(str);
        if (!TextUtils.isEmpty(string)) {
            try {
                j = Long.parseLong(string);
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    public ArrayList<Bundle> getParcelableArrayList(String str) {
        return this.mMediaMeta.getParcelableArrayList(str);
    }

    public String getDurationInline() {
        long j = (this.mDurationUS + 5000) / 1000000;
        long j2 = j / 60;
        j %= 60;
        long j3 = j2 / 60;
        j2 %= 60;
        return String.format(Locale.US, "%02d:%02d:%02d", new Object[]{Long.valueOf(j3), Long.valueOf(j2), Long.valueOf(j)});
    }

    public String getStreamId() {
        return this.mStreamId;
    }

    public int getAnalyzeDnsTime() {
        return this.mAnalyzeDnsTime;
    }

    public int getHttpCode() {
        return this.mHttpCode;
    }

    public int getFirstDataTime() {
        return this.mHttpFirstDataTime;
    }

    public int getConnectTime() {
        return this.mHttpConnectTime;
    }

    public int getPrepareCostTime() {
        return this.mPrepareCostTime;
    }

    public int getPrepareReadBytes() {
        return this.mPrepareReadBytes;
    }

    public int getOpenStreamCostTime() {
        return this.mOpenStreamCostTime;
    }

    public int getParserInfoStatus() {
        return this.mParserInfoStatus;
    }

    public String getStreamType() {
        return this.mStreamType;
    }

    public String getVideoCodec() {
        return this.mVCodec;
    }

    public String getAudioCodec() {
        return this.mACodec;
    }

    public static KSYMediaMeta parse(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        KSYMediaMeta kSYMediaMeta = new KSYMediaMeta();
        kSYMediaMeta.mMediaMeta = bundle;
        kSYMediaMeta.mFormat = kSYMediaMeta.getString(IJKM_KEY_FORMAT);
        kSYMediaMeta.mDurationUS = kSYMediaMeta.getLong(IJKM_KEY_DURATION_US);
        kSYMediaMeta.mStartUS = kSYMediaMeta.getLong(IJKM_KEY_START_US);
        kSYMediaMeta.mBitrate = kSYMediaMeta.getLong(IJKM_KEY_BITRATE);
        int i = kSYMediaMeta.getInt(IJKM_VAL_TYPE__VIDEO, -1);
        int i2 = kSYMediaMeta.getInt(IJKM_VAL_TYPE__AUDIO, -1);
        kSYMediaMeta.mFormat = kSYMediaMeta.getString(IJKM_KEY_HTTP_X_CACHE);
        kSYMediaMeta.mHttpRedirect = kSYMediaMeta.getString(IJKM_KEY_HTTP_REDIRECT);
        kSYMediaMeta.mHttpContentRange = kSYMediaMeta.getString(IJKM_KEY_HTTP_CONTENT_RANGE);
        kSYMediaMeta.mHttpContentLength = kSYMediaMeta.getString(IJKM_KEY_HTTP_CONTENT_LENGHT);
        kSYMediaMeta.mAnalyzeDnsTime = kSYMediaMeta.getInt(IJKM_KEY_HTTP_ANALYZE_DNS, 0);
        kSYMediaMeta.mHttpCode = kSYMediaMeta.getInt(IJKM_KEY_HTTP_CODE, 0);
        kSYMediaMeta.mStreamId = kSYMediaMeta.getString(IJKM_KEY_STREAMID);
        try {
            if (kSYMediaMeta.getString(IJKM_KEY_HTTP_CONNECT_TIME) != null) {
                kSYMediaMeta.mHttpConnectTime = new Double(kSYMediaMeta.getString(IJKM_KEY_HTTP_CONNECT_TIME)).intValue();
            }
            if (kSYMediaMeta.getString(IJKM_KEY_HTTP_FIRST_DATA_TIME) != null) {
                kSYMediaMeta.mHttpFirstDataTime = new Double(kSYMediaMeta.getString(IJKM_KEY_HTTP_FIRST_DATA_TIME)).intValue();
            }
        } catch (NumberFormatException e) {
            kSYMediaMeta.mHttpConnectTime = 0;
            kSYMediaMeta.mHttpFirstDataTime = 0;
        }
        kSYMediaMeta.mPrepareCostTime = kSYMediaMeta.getInt(IJKM_KEY_PREPARE_COST_TIME, 0);
        kSYMediaMeta.mPrepareReadBytes = kSYMediaMeta.getInt(IJKM_KEY_PREPARE_READ_BYTES, 0);
        kSYMediaMeta.mOpenStreamCostTime = kSYMediaMeta.getInt(IJKM_KEY_OPEN_STREAM_COST, 0);
        kSYMediaMeta.mParserInfoStatus = kSYMediaMeta.getInt(IJKM_KEY_PARSER_INFO_STATUS, 0);
        kSYMediaMeta.mStreamType = kSYMediaMeta.getString(IJKM_KEY_INFO_STREAM_TYPE);
        kSYMediaMeta.mVCodec = kSYMediaMeta.getString(IJKM_KEY_VIDEO_CODEC);
        kSYMediaMeta.mACodec = kSYMediaMeta.getString(IJKM_KEY_AUDIO_CODEC);
        ArrayList parcelableArrayList = kSYMediaMeta.getParcelableArrayList(IJKM_KEY_STREAMS);
        if (parcelableArrayList == null) {
            return kSYMediaMeta;
        }
        Iterator it = parcelableArrayList.iterator();
        while (it.hasNext()) {
            Bundle bundle2 = (Bundle) it.next();
            if (bundle2 != null) {
                int i3 = bundle2.getInt(IJKM_KEY_STREAM_INDEX);
                KSYStreamMeta kSYStreamMeta = new KSYStreamMeta(i3);
                kSYStreamMeta.mMeta = bundle2;
                kSYStreamMeta.mType = kSYStreamMeta.getString(IJKM_KEY_TYPE);
                kSYStreamMeta.mLanguage = kSYStreamMeta.getString(IJKM_KEY_LANGUAGE);
                if (!TextUtils.isEmpty(kSYStreamMeta.mType)) {
                    kSYStreamMeta.mCodecName = kSYStreamMeta.getString(IJKM_KEY_CODEC_NAME);
                    kSYStreamMeta.mCodecProfile = kSYStreamMeta.getString(IJKM_KEY_CODEC_PROFILE);
                    kSYStreamMeta.mCodecLongName = kSYStreamMeta.getString(IJKM_KEY_CODEC_LONG_NAME);
                    kSYStreamMeta.mBitrate = (long) kSYStreamMeta.getInt(IJKM_KEY_BITRATE);
                    if (kSYStreamMeta.mType.equalsIgnoreCase(IJKM_VAL_TYPE__VIDEO)) {
                        kSYStreamMeta.mWidth = kSYStreamMeta.getInt(IJKM_KEY_WIDTH);
                        kSYStreamMeta.mHeight = kSYStreamMeta.getInt(IJKM_KEY_HEIGHT);
                        kSYStreamMeta.mFpsNum = kSYStreamMeta.getInt(IJKM_KEY_FPS_NUM);
                        kSYStreamMeta.mFpsDen = kSYStreamMeta.getInt(IJKM_KEY_FPS_DEN);
                        kSYStreamMeta.mTbrNum = kSYStreamMeta.getInt(IJKM_KEY_TBR_NUM);
                        kSYStreamMeta.mTbrDen = kSYStreamMeta.getInt(IJKM_KEY_TBR_DEN);
                        kSYStreamMeta.mSarNum = kSYStreamMeta.getInt(IJKM_KEY_SAR_NUM);
                        kSYStreamMeta.mSarDen = kSYStreamMeta.getInt(IJKM_KEY_SAR_DEN);
                        if (i == i3) {
                            kSYMediaMeta.mVideoStream = kSYStreamMeta;
                        }
                    } else if (kSYStreamMeta.mType.equalsIgnoreCase(IJKM_VAL_TYPE__AUDIO)) {
                        kSYStreamMeta.mSampleRate = kSYStreamMeta.getInt(IJKM_KEY_SAMPLE_RATE);
                        kSYStreamMeta.mChannelLayout = kSYStreamMeta.getLong(IJKM_KEY_CHANNEL_LAYOUT);
                        if (i2 == i3) {
                            kSYMediaMeta.mAudioStream = kSYStreamMeta;
                        }
                    }
                    kSYMediaMeta.mStreams.add(kSYStreamMeta);
                }
            }
        }
        return kSYMediaMeta;
    }
}
