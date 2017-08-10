package com.ksyun.media.player.misc;

import android.annotation.TargetApi;
import android.media.MediaFormat;

/* compiled from: AndroidMediaFormat */
public class a implements c {
    private final MediaFormat d;

    public a(MediaFormat mediaFormat) {
        this.d = mediaFormat;
    }

    @TargetApi(16)
    public int getInteger(String str) {
        if (this.d == null) {
            return 0;
        }
        return this.d.getInteger(str);
    }

    @TargetApi(16)
    public String getString(String str) {
        if (this.d == null) {
            return null;
        }
        return this.d.getString(str);
    }

    @TargetApi(16)
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(getClass().getName());
        stringBuilder.append('{');
        if (this.d != null) {
            stringBuilder.append(this.d.toString());
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
