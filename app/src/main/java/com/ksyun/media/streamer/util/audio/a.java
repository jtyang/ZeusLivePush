package com.ksyun.media.streamer.util.audio;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build.VERSION;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.streamer.kit.StreamerConstants;

/* compiled from: AudioUtil */
public class a {
    public static int a(Context context) {
        if (VERSION.SDK_INT >= 17) {
            return Integer.parseInt(((AudioManager) context.getSystemService(KSYMediaMeta.IJKM_VAL_TYPE__AUDIO)).getProperty("android.media.property.OUTPUT_SAMPLE_RATE"));
        }
        return StreamerConstants.DEFAULT_AUDIO_SAMPLE_RATE;
    }

    public static int a(Context context, int i) {
        if (VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) context.getSystemService(KSYMediaMeta.IJKM_VAL_TYPE__AUDIO);
            if (i == Integer.parseInt(audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE"))) {
                return Integer.parseInt(audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER"));
            }
        }
        return 1024;
    }
}
