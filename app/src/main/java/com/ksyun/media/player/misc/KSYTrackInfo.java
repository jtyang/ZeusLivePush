package com.ksyun.media.player.misc;

import android.text.TextUtils;
import com.ksyun.media.player.KSYMediaMeta.KSYStreamMeta;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.publisher.RtmpPublisher;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;

public class KSYTrackInfo implements ITrackInfo {
    private KSYStreamMeta mStreamMeta;
    private int mTrackIndex;
    private int mTrackType;

    public KSYTrackInfo(KSYStreamMeta kSYStreamMeta) {
        this.mTrackType = 0;
        this.mTrackIndex = -1;
        this.mStreamMeta = kSYStreamMeta;
    }

    public void setMediaMeta(KSYStreamMeta kSYStreamMeta) {
        this.mStreamMeta = kSYStreamMeta;
    }

    public c getFormat() {
        return new KSYMediaFormat(this.mStreamMeta);
    }

    public String getLanguage() {
        if (this.mStreamMeta == null || TextUtils.isEmpty(this.mStreamMeta.mLanguage)) {
            return "und";
        }
        return this.mStreamMeta.mLanguage;
    }

    public int getTrackType() {
        return this.mTrackType;
    }

    public int getTrackIndex() {
        return this.mTrackIndex;
    }

    public void setTrackIndex(int i) {
        this.mTrackIndex = i;
    }

    public void setTrackType(int i) {
        this.mTrackType = i;
    }

    public String toString() {
        return getClass().getSimpleName() + '{' + getInfoInline() + "}";
    }

    public String getInfoInline() {
        StringBuilder stringBuilder = new StringBuilder();
        switch (this.mTrackType) {
            case d.a /*1*/:
                stringBuilder.append("VIDEO");
                stringBuilder.append(", ");
                stringBuilder.append(this.mStreamMeta.getCodecShortNameInline());
                stringBuilder.append(", ");
                stringBuilder.append(this.mStreamMeta.getBitrateInline());
                stringBuilder.append(", ");
                stringBuilder.append(this.mStreamMeta.getResolutionInline());
                break;
            case d.b /*2*/:
                stringBuilder.append("AUDIO");
                stringBuilder.append(", ");
                stringBuilder.append(this.mStreamMeta.getCodecShortNameInline());
                stringBuilder.append(", ");
                stringBuilder.append(this.mStreamMeta.getBitrateInline());
                stringBuilder.append(", ");
                stringBuilder.append(this.mStreamMeta.getSampleRateInline());
                break;
            case GLRender.VIEW_TYPE_OFFSCREEN /*3*/:
                stringBuilder.append("TIMEDTEXT");
                stringBuilder.append(", ").append(this.mStreamMeta.getCodecShortNameInline());
                stringBuilder.append(",").append(this.mStreamMeta.mLanguage);
                break;
            case TexTransformUtil.COORDS_COUNT /*4*/:
                stringBuilder.append("SUBTITLE");
                break;
            case RtmpPublisher.INFO_PACKET_SEND_SLOW /*100*/:
                stringBuilder.append("EXTERNAL_TIMEDTEXT");
                stringBuilder.append(", ").append(this.mStreamMeta.getCodecShortNameInline());
                stringBuilder.append(",").append(this.mStreamMeta.mLanguage);
                break;
            default:
                stringBuilder.append(StatsConstant.STAT_CONSTANTS_UNKNOWN);
                break;
        }
        return stringBuilder.toString();
    }
}
