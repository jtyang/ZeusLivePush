package com.yjt.zeuslivepush.quirks;

import android.os.Build;

import com.yjt.zeuslivepush.media.ColorRange;

import java.util.EnumMap;
import java.util.regex.Pattern;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class QuirksStorage {
    private static final EnumMap<Quirk, Object> _Current = new EnumMap(Quirk.class);

    public static boolean getBoolean(Quirk quirk) {
        return ((Boolean) get(quirk)).booleanValue();
    }

    public static int getInteger(Quirk quirk) {
        return ((Integer) get(quirk)).intValue();
    }

    public static Object get(Quirk quirk) {
        Object val = _Current.get(quirk);
        return val != null ? val : quirk.getDefaultValue();
    }

    static void add(Quirk quirk, Object val) {
        _Current.put(quirk, val);
    }

    static void addModel(Quirk quirk, Object val, String... models) {
        String[] var3 = models;
        int var4 = models.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String model = var3[var5];
            if (Build.MODEL.equals(model)) {
                _Current.put(quirk, val);
            }
        }

    }

    static void addModelSeries(Quirk quirk, Object val, String... series_list) {
        String[] var3 = series_list;
        int var4 = series_list.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String series = var3[var5];
            if (Pattern.matches(series, Build.MODEL)) {
                _Current.put(quirk, val);
            }
        }

    }

    static {
        addModel(Quirk.FRONT_CAMERA_PREVIEW_DATA_MIRRORED, Boolean.valueOf(true), new String[]{"ZTE U930"});
        addModel(Quirk.FRONT_CAMERA_PREVIEW_DATA_MIRRORED, Boolean.valueOf(true), new String[]{"2014501"});
        addModel(Quirk.CAMERA_RECORDING_HINT, Boolean.valueOf(true), new String[]{"MI 3"});
        addModel(Quirk.CAMERA_NO_AUTO_FOCUS_CALLBACK, Boolean.valueOf(true), new String[]{"MI NOTE LTE"});
        addModel(Quirk.CAMERA_ASPECT_RATIO_DEDUCTION, Boolean.valueOf(true), new String[]{"MI 3"});
        addModelSeries(Quirk.CAMERA_COLOR_RANGE, ColorRange.MPEG, new String[]{"SM\\-N90[0-9][0-9]"});
    }
}
