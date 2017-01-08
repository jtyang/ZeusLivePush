package com.yjt.zeuslivepush.media;

import android.media.AudioRecord;
import android.util.Log;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class AudioUtil {
    private static final String TAG = "AudioUtil";

    public static AudioRecord getRecorder(int source, int sample_rate, int channel_config, int sample_format, int buffer_size) {
        AudioRecord rec;
        try {
            rec = new AudioRecord(source, sample_rate, channel_config, sample_format, buffer_size);
        } catch (Throwable tr) {
            Log.e("AudioUtil", "AudioRecord creation failure", tr);
            return null;
        }

        if (rec.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e("AudioUtil", "AudioRecord initialization failure: " + rec.getState());
            rec.release();
            return null;
        }
        return rec;
    }

}
