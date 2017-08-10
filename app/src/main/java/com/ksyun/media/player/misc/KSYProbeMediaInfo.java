package com.ksyun.media.player.misc;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;
import com.ksyun.media.player.KSYLibraryManager;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.player.d.d;
import com.ksyun.media.player.e;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class KSYProbeMediaInfo {
    private static String KSY_AUDIO_BITRATE;
    private static String KSY_AUDIO_CHANNEL;
    private static String KSY_AUDIO_CODEC;
    private static String KSY_AUDIO_FRAME_SIZE;
    private static String KSY_AUDIO_SAMPLE_FORMAT;
    private static String KSY_AUDIO_SAMPLE_RATE;
    private static String KSY_AUDIO_STREAM_NUMBER;
    private static String KSY_MEDIA_BITRATE;
    private static String KSY_MEDIA_DURATION;
    private static String KSY_MEDIA_FORMAT;
    private static String KSY_STREAM_ARRAY_LIST;
    private static String KSY_STREAM_TYPE;
    private static String KSY_VIDEO_CODEC;
    private static String KSY_VIDEO_HEIGHT;
    private static String KSY_VIDEO_STREAM_NUMBER;
    private static String KSY_VIDEO_WIDTH;
    private int mAudioStreamNum;
    private ArrayList<KSYProbeMediaData> mAudioStreams;
    private int mMediaBitrate;
    private long mMediaDuration;
    private KSYMediaFormat mMediaFormat;
    private int mVideoStreamNum;
    private ArrayList<KSYProbeMediaData> mVideoStreams;

    public enum KSYAudioCodecType {
        KSY_Audio_NULL,
        KSY_Audio_AAC,
        KSY_Audio_AC3,
        KSY_Audio_AC3_PLUS,
        KSY_Audio_PCM,
        KSY_Audio_ALAC,
        KSY_Audio_AMR_NB,
        KSY_Audio_AMR_WB,
        KSY_Audio_APE,
        KSY_Audio_MPEG,
        KSY_Audio_MP3,
        KSY_Audio_COOK,
        KSY_Audio_DTS,
        KSY_Audio_DIRAC,
        KSY_Audio_FLAC,
        KSY_Audio_G723_1,
        KSY_Audio_G729,
        KSY_Audio_GSM,
        KSY_Audio_GSM_MS,
        KSY_Audio_WMA1,
        KSY_Audio_WMA2,
        KSY_Audio_WMAPRO,
        KSY_Audio_NELLYMOSER
    }

    public enum KSYAudioSampleFormat {
        KSY_SAMPLE_FMT_NULL,
        KSY_SAMPLE_FMT_U8,
        KSY_SAMPLE_FMT_S16,
        KSY_SAMPLE_FMT_S32,
        KSY_SAMPLE_FMT_FLT,
        KSY_SAMPLE_FMT_DBL,
        KSY_SAMPLE_FMT_U8P,
        KSY_SAMPLE_FMT_S16P,
        KSY_SAMPLE_FMT_S32P,
        KSY_SAMPLE_FMT_FLTP,
        KSY_SAMPLE_FMT_DBLP,
        KSY_SAMPLE_FMT_NB
    }

    public enum KSYMediaFormat {
        KSY_FORMAT_NULL,
        KSY_FORMAT_MP2T,
        KSY_FORMAT_MOV,
        KSY_FORMAT_AVI,
        KSY_FORMAT_FLV,
        KSY_FORMAT_MKV,
        KSY_FORMAT_ASF,
        KSY_FORMAT_RM,
        KSY_FORMAT_WAV,
        KSY_FORMAT_OGG,
        KSY_FORMAT_APE,
        KSY_FORMAT_RAWVIDEO,
        KSY_FORMAT_HLS
    }

    public class KSYProbeMediaData {
        private int mAudioBitrate;
        private int mAudioChannel;
        private KSYAudioCodecType mAudioCodec;
        private int mAudioFrameSize;
        private KSYAudioSampleFormat mAudioSampleFormat;
        private int mAudioSampleRate;
        private KSYVideoCodecType mVideoCodec;
        private int mVideoHeight;
        private int mVideoWidth;

        public KSYVideoCodecType getVideoCodecType() {
            return this.mVideoCodec;
        }

        public int getVideoWidth() {
            return this.mVideoWidth;
        }

        public int getVideoHeight() {
            return this.mVideoHeight;
        }

        public KSYAudioCodecType getAudioCodecType() {
            return this.mAudioCodec;
        }

        public int getAudioBitrate() {
            return this.mAudioBitrate;
        }

        public int getAudioChannel() {
            return this.mAudioChannel;
        }

        public int getAudioSampleRate() {
            return this.mAudioSampleRate;
        }

        public KSYAudioSampleFormat getAudioFormat() {
            return this.mAudioSampleFormat;
        }

        public int getAudioFrameSize() {
            return this.mAudioFrameSize;
        }
    }

    public enum KSYVideoCodecType {
        KSY_VIDEO_NULL,
        KSY_VIDEO_MP1V,
        KSY_VIDEO_MP2V,
        KSY_VIDEO_MP4V,
        KSY_VIDEO_MJPEG,
        KSY_VIDEO_JPEG2000,
        KSY_VIDEO_H263,
        KSY_VIDEO_H264,
        KSY_VIDEO_H265,
        KSY_VIDEO_MAD,
        KSY_VIDEO_FLV,
        KSY_VIDEO_PNG,
        KSY_VIDEO_RV10,
        KSY_VIDEO_RV20,
        KSY_VIDEO_RV30,
        KSY_VIDEO_RV40,
        KSY_VIDEO_SVQ1,
        KSY_VIDEO_SVQ3,
        KSY_VIDEO_TARGA,
        KSY_VIDEO_TARGA_Y216,
        KSY_VIDEO_AYUV,
        KSY_VIDEO_YUV4,
        KSY_VIDEO_HUFFYUV,
        KSY_VIDEO_CYUV,
        KSY_VIDEO_PGMYUV,
        KSY_VIDEO_GIF,
        KSY_VIDEO_TIFF,
        KSY_VIDEO_VC1,
        KSY_VIDEO_VP6,
        KSY_VIDEO_VP6F,
        KSY_VIDEO_VP6A,
        KSY_VIDEO_VP8,
        KSY_VIDEO_WEBP,
        KSY_VIDEO_WMV1,
        KSY_VIDEO_WMV2,
        KSY_VIDEO_WMV3
    }

    private native Bundle _native_getMediaInfo(String str, int i, String str2, int i2);

    private native int _native_thumbnail(Bitmap bitmap, String str, long j, int i, int i2);

    static {
        KSY_MEDIA_FORMAT = "media_format";
        KSY_MEDIA_BITRATE = "media_bitrate";
        KSY_MEDIA_DURATION = "media_duration";
        KSY_STREAM_TYPE = d.af;
        KSY_STREAM_ARRAY_LIST = "ksy_streams";
        KSY_VIDEO_STREAM_NUMBER = "video_stream_num";
        KSY_VIDEO_CODEC = "video_codec";
        KSY_VIDEO_WIDTH = "video_width";
        KSY_VIDEO_HEIGHT = "video_height";
        KSY_AUDIO_STREAM_NUMBER = "audio_stream_num";
        KSY_AUDIO_CODEC = "audio_codec";
        KSY_AUDIO_BITRATE = StatsConstant.AUDIO_BITRATE;
        KSY_AUDIO_SAMPLE_RATE = "audio_sample_rate";
        KSY_AUDIO_CHANNEL = "audio_channel";
        KSY_AUDIO_SAMPLE_FORMAT = "audio_sample_format";
        KSY_AUDIO_FRAME_SIZE = "audio_frame_size";
        if (TextUtils.isEmpty(KSYLibraryManager.getLocalLibraryPath())) {
            if (!e.a("ksylive")) {
                e.a("ksyplayer");
            }
        } else if (!e.a(KSYLibraryManager.getLocalLibraryPath(), "ksylive")) {
            e.a(KSYLibraryManager.getLocalLibraryPath(), "ksyplayer");
        }
    }

    public KSYProbeMediaInfo() {
        this.mVideoStreams = new ArrayList();
        this.mAudioStreams = new ArrayList();
        this.mAudioStreamNum = 0;
        this.mVideoStreamNum = 0;
    }

    public void probeMediaInfo(String str, int i, Map<String, String> map, boolean z) {
        String str2;
        resetInternalData();
        if (map == null || map.isEmpty()) {
            str2 = null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append(":");
                if (!TextUtils.isEmpty((String) entry.getValue())) {
                    stringBuilder.append(" " + ((String) entry.getValue()));
                }
                stringBuilder.append("\r\n");
            }
            str2 = stringBuilder.toString();
        }
        Bundle _native_getMediaInfo = _native_getMediaInfo(str, i, str2, z ? 1 : 0);
        if (_native_getMediaInfo != null) {
            this.mMediaFormat = KSYMediaFormat.values()[_native_getMediaInfo.getInt(KSY_MEDIA_FORMAT, 0)];
            this.mMediaBitrate = _native_getMediaInfo.getInt(KSY_MEDIA_BITRATE, 0);
            this.mMediaDuration = _native_getMediaInfo.getLong(KSY_MEDIA_DURATION);
            this.mVideoStreamNum = _native_getMediaInfo.getInt(KSY_VIDEO_STREAM_NUMBER, 0);
            this.mAudioStreamNum = _native_getMediaInfo.getInt(KSY_AUDIO_STREAM_NUMBER, 0);
            ArrayList parcelableArrayList = _native_getMediaInfo.getParcelableArrayList(KSY_STREAM_ARRAY_LIST);
            if (parcelableArrayList != null) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    _native_getMediaInfo = (Bundle) it.next();
                    String string = _native_getMediaInfo.getString(KSY_STREAM_TYPE);
                    if (!TextUtils.isEmpty(string)) {
                        KSYProbeMediaData kSYProbeMediaData = new KSYProbeMediaData();
                        if (string.equals(KSYMediaMeta.IJKM_VAL_TYPE__VIDEO)) {
                            kSYProbeMediaData.mVideoCodec = KSYVideoCodecType.values()[_native_getMediaInfo.getInt(KSY_VIDEO_CODEC, 0)];
                            kSYProbeMediaData.mVideoWidth = _native_getMediaInfo.getInt(KSY_VIDEO_WIDTH);
                            kSYProbeMediaData.mVideoHeight = _native_getMediaInfo.getInt(KSY_VIDEO_HEIGHT);
                            this.mVideoStreams.add(kSYProbeMediaData);
                        } else if (string.equals(KSYMediaMeta.IJKM_VAL_TYPE__AUDIO)) {
                            kSYProbeMediaData.mAudioCodec = KSYAudioCodecType.values()[_native_getMediaInfo.getInt(KSY_AUDIO_CODEC, 0)];
                            kSYProbeMediaData.mAudioBitrate = _native_getMediaInfo.getInt(KSY_AUDIO_BITRATE, 0);
                            kSYProbeMediaData.mAudioChannel = _native_getMediaInfo.getInt(KSY_AUDIO_CHANNEL, 0);
                            kSYProbeMediaData.mAudioSampleFormat = KSYAudioSampleFormat.values()[_native_getMediaInfo.getInt(KSY_AUDIO_SAMPLE_FORMAT, 0)];
                            kSYProbeMediaData.mAudioSampleRate = _native_getMediaInfo.getInt(KSY_AUDIO_SAMPLE_RATE, 0);
                            kSYProbeMediaData.mAudioFrameSize = _native_getMediaInfo.getInt(KSY_AUDIO_FRAME_SIZE, 0);
                            this.mAudioStreams.add(kSYProbeMediaData);
                        }
                    }
                }
            }
        }
    }

    public KSYMediaFormat getMediaFormat() {
        return this.mMediaFormat;
    }

    public int getMediaBitrate() {
        return this.mMediaBitrate;
    }

    public long getMediaDuration() {
        return this.mMediaDuration;
    }

    public int getVideoStreamCount() {
        return this.mVideoStreamNum;
    }

    public ArrayList<KSYProbeMediaData> getVideoStreams() {
        return this.mVideoStreams;
    }

    public int getAudioStreamCount() {
        return this.mAudioStreamNum;
    }

    public ArrayList<KSYProbeMediaData> getAudioStreams() {
        return this.mAudioStreams;
    }

    private void resetInternalData() {
        this.mAudioStreamNum = 0;
        this.mVideoStreamNum = 0;
        this.mMediaBitrate = 0;
        this.mMediaFormat = KSYMediaFormat.KSY_FORMAT_NULL;
        if (this.mAudioStreams != null) {
            this.mAudioStreams.clear();
        }
        if (this.mVideoStreams != null) {
            this.mVideoStreams.clear();
        }
    }

    public Bitmap getVideoThumbnailAtTime(String str, long j, int i, int i2) {
        if (TextUtils.isEmpty(str) || j < 0 || i < 0 || i2 < 0) {
            return null;
        }
        int videoHeight;
        int i3;
        int i4;
        Bitmap bitmap = null;
        if (i <= 0 || i2 <= 0) {
            int videoWidth;
            probeMediaInfo(str, 5, null, true);
            if (this.mVideoStreamNum > 0) {
                KSYProbeMediaData kSYProbeMediaData = (KSYProbeMediaData) this.mVideoStreams.get(0);
                if (kSYProbeMediaData != null) {
                    videoWidth = kSYProbeMediaData.getVideoWidth();
                    videoHeight = kSYProbeMediaData.getVideoHeight();
                    if (videoHeight > 0 || videoWidth <= 0) {
                        i3 = i2;
                        i4 = i;
                    } else {
                        if (i <= 0 && i2 <= 0) {
                            i2 = videoHeight;
                            i = videoWidth;
                        } else if (i > 0) {
                            i2 = (videoHeight * i) / videoWidth;
                        } else {
                            i = (videoWidth * i2) / videoHeight;
                        }
                        bitmap = Bitmap.createBitmap(i, i2, Config.RGB_565);
                        i3 = i2;
                        i4 = i;
                    }
                }
            }
            videoHeight = 0;
            videoWidth = 0;
            if (videoHeight > 0) {
            }
            i3 = i2;
            i4 = i;
        } else {
            bitmap = Bitmap.createBitmap(i, i2, Config.RGB_565);
            i3 = i2;
            i4 = i;
        }
        videoHeight = _native_thumbnail(bitmap, str, j, i4, i3);
        if (videoHeight <= 0) {
            return bitmap;
        }
        float f = (float) (360 - videoHeight);
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, i4, i3, matrix, true);
        bitmap.recycle();
        return createBitmap;
    }
}
