package com.ksyun.media.player.util;

import com.ksyun.media.player.KSYMediaPlayer;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

class AudioUtil {
    private static final String TAG = "AudioUtil";

    AudioUtil() {
    }

    public static void onAudioPCMReady(Object obj, ByteBuffer byteBuffer, long j, int i, int i2, int i3) {
        KSYMediaPlayer kSYMediaPlayer = (KSYMediaPlayer) ((WeakReference) obj).get();
        if (kSYMediaPlayer != null) {
            kSYMediaPlayer._onAudioPCMReady(byteBuffer, j, i, i2, i3);
        }
    }
}
