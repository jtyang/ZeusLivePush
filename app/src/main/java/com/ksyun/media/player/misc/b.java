package com.ksyun.media.player.misc;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.TrackInfo;
import android.os.Build.VERSION;

/* compiled from: AndroidTrackInfo */
public class b implements ITrackInfo {
    private final TrackInfo a;

    public static b[] a(MediaPlayer mediaPlayer) {
        if (VERSION.SDK_INT >= 16) {
            return a(mediaPlayer.getTrackInfo());
        }
        return null;
    }

    private static b[] a(TrackInfo[] trackInfoArr) {
        if (trackInfoArr == null) {
            return null;
        }
        b[] bVarArr = new b[trackInfoArr.length];
        for (int i = 0; i < trackInfoArr.length; i++) {
            bVarArr[i] = new b(trackInfoArr[i]);
        }
        return bVarArr;
    }

    private b(TrackInfo trackInfo) {
        this.a = trackInfo;
    }

    @TargetApi(19)
    public c getFormat() {
        if (this.a == null || VERSION.SDK_INT < 19) {
            return null;
        }
        MediaFormat format = this.a.getFormat();
        if (format != null) {
            return new a(format);
        }
        return null;
    }

    @TargetApi(16)
    public String getLanguage() {
        if (this.a == null) {
            return "und";
        }
        return this.a.getLanguage();
    }

    @TargetApi(16)
    public int getTrackType() {
        if (this.a == null) {
            return 0;
        }
        return this.a.getTrackType();
    }

    @TargetApi(16)
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append('{');
        if (this.a != null) {
            stringBuilder.append(this.a.toString());
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @TargetApi(16)
    public String getInfoInline() {
        if (this.a != null) {
            return this.a.toString();
        }
        return "null";
    }

    public int getTrackIndex() {
        return 0;
    }
}
