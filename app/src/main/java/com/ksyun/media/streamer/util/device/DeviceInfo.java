package com.ksyun.media.streamer.util.device;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.ksyun.media.player.b;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceInfo {
    public static final String BAND_VALUE = "band";
    public static final String ENCODE_264_VALUE = "264hw_enc";
    public static final String ENCODE_265_VALUE = "265hw_enc";
    public static final int ENCODE_HW_SUPPORT = 1;
    public static final int ENCODE_HW_UNSUPPORT = 0;
    public String band;
    public int decode;
    public int encode_h264;
    public int encode_h265;
    public String model;
    public String osver;

    public DeviceInfo(String str, String str2) {
        this.model = str;
        this.osver = str2;
        this.encode_h264 = 0;
        this.band = b.d;
    }

    public DeviceInfo(JSONObject jSONObject, String str, String str2) {
        this.model = str;
        this.osver = str2;
        try {
            this.band = jSONObject.getString(BAND_VALUE);
            this.encode_h264 = jSONObject.getInt(ENCODE_264_VALUE);
            this.encode_h265 = jSONObject.getInt(ENCODE_265_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceInfo(SharedPreferences sharedPreferences, String str, String str2) {
        this.model = str;
        this.osver = str2;
        this.encode_h264 = sharedPreferences.getInt(ENCODE_264_VALUE, 0);
        this.encode_h265 = sharedPreferences.getInt(ENCODE_265_VALUE, 0);
        this.band = sharedPreferences.getString(BAND_VALUE, b.d);
    }

    public void saveDeviceInfoForLocal(Editor editor) {
        editor.putInt(ENCODE_264_VALUE, this.encode_h264);
        editor.putInt(ENCODE_265_VALUE, this.encode_h265);
        editor.putString(BAND_VALUE, this.band);
    }

    public String printDeviceInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.model);
        stringBuilder.append("(model)_");
        stringBuilder.append(this.osver);
        stringBuilder.append("(osver)_");
        stringBuilder.append(this.band);
        stringBuilder.append("(band)_");
        stringBuilder.append(this.encode_h264);
        stringBuilder.append("(encode_h264)");
        stringBuilder.append(this.encode_h265);
        stringBuilder.append("(encode_h265)");
        return stringBuilder.toString();
    }

    public boolean compareDeviceInfo(DeviceInfo deviceInfo) {
        if (deviceInfo.encode_h264 == this.encode_h264 && deviceInfo.encode_h265 == this.encode_h265) {
            return true;
        }
        return false;
    }
}
