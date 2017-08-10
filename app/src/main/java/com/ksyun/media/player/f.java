package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: KSYMediaCodecInfo */
public class f {
    public static int a = 0;
    public static final int b = 800;
    public static final int c = 700;
    public static final int d = 600;
    public static final int e = 300;
    public static final int f = 200;
    public static final int g = 100;
    public static final int h = 0;
    private static final String l = "KSYMediaCodecInfo";
    private static Map<String, Integer> m;
    public MediaCodecInfo i;
    public int j;
    public String k;

    public f() {
        this.j = h;
    }

    static {
        a = StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
    }

    private static synchronized Map<String, Integer> a() {
        Map<String, Integer> map;
        synchronized (f.class) {
            if (m != null) {
                map = m;
            } else {
                m = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                m.put("OMX.Nvidia.h264.decode", Integer.valueOf(b));
                m.put("OMX.Nvidia.h264.decode.secure", Integer.valueOf(e));
                m.put("OMX.Intel.hw_vd.h264", Integer.valueOf(IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE));
                m.put("OMX.Intel.VideoDecoder.AVC", Integer.valueOf(b));
                m.put("OMX.qcom.video.decoder.avc", Integer.valueOf(b));
                m.put("OMX.ittiam.video.decoder.avc", Integer.valueOf(h));
                m.put("OMX.SEC.avc.dec", Integer.valueOf(b));
                m.put("OMX.SEC.AVC.Decoder", Integer.valueOf(799));
                m.put("OMX.SEC.avcdec", Integer.valueOf(798));
                m.put("OMX.SEC.avc.sw.dec", Integer.valueOf(f));
                m.put("OMX.SEC.hevc.sw.dec", Integer.valueOf(f));
                m.put("OMX.Exynos.avc.dec", Integer.valueOf(b));
                m.put("OMX.Exynos.AVC.Decoder", Integer.valueOf(799));
                m.put("OMX.k3.video.decoder.avc", Integer.valueOf(b));
                m.put("OMX.IMG.MSVDX.Decoder.AVC", Integer.valueOf(b));
                m.put("OMX.TI.DUCATI1.VIDEO.DECODER", Integer.valueOf(b));
                m.put("OMX.rk.video_decoder.avc", Integer.valueOf(b));
                m.put("OMX.amlogic.avc.decoder.awesome", Integer.valueOf(b));
                m.put("OMX.MARVELL.VIDEO.HW.CODA7542DECODER", Integer.valueOf(b));
                m.put("OMX.MARVELL.VIDEO.H264DECODER", Integer.valueOf(f));
                m.remove("OMX.Action.Video.Decoder");
                m.remove("OMX.allwinner.video.decoder.avc");
                m.remove("OMX.BRCM.vc4.decoder.avc");
                m.remove("OMX.brcm.video.h264.hw.decoder");
                m.remove("OMX.brcm.video.h264.decoder");
                m.remove("OMX.cosmo.video.decoder.avc");
                m.remove("OMX.duos.h264.decoder");
                m.remove("OMX.hantro.81x0.video.decoder");
                m.remove("OMX.hantro.G1.video.decoder");
                m.remove("OMX.hisi.video.decoder");
                m.remove("OMX.LG.decoder.video.avc");
                m.remove("OMX.MS.AVC.Decoder");
                m.remove("OMX.RENESAS.VIDEO.DECODER.H264");
                m.remove("OMX.RTK.video.decoder");
                m.remove("OMX.sprd.h264.decoder");
                m.remove("OMX.ST.VFM.H264Dec");
                m.remove("OMX.vpu.video_decoder.avc");
                m.remove("OMX.WMT.decoder.avc");
                m.remove("OMX.bluestacks.hw.decoder");
                m.put("OMX.google.h264.decoder", Integer.valueOf(f));
                m.put("OMX.google.h264.lc.decoder", Integer.valueOf(f));
                m.put("OMX.k3.ffmpeg.decoder", Integer.valueOf(f));
                m.put("OMX.ffmpeg.video.decoder", Integer.valueOf(f));
                m.put("OMX.sprd.soft.h264.decoder", Integer.valueOf(f));
                map = m;
            }
        }
        return map;
    }

    @TargetApi(16)
    public static f a(MediaCodecInfo mediaCodecInfo, String str) {
        int i = f;
        if (mediaCodecInfo == null || VERSION.SDK_INT < 16) {
            return null;
        }
        Object name = mediaCodecInfo.getName();
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        String toLowerCase = name.toLowerCase(Locale.US);
        if (!toLowerCase.startsWith("omx.")) {
            i = g;
        } else if (!(toLowerCase.startsWith("omx.pv") || toLowerCase.startsWith("omx.google.") || toLowerCase.startsWith("omx.ffmpeg.") || toLowerCase.startsWith("omx.k3.ffmpeg.") || toLowerCase.startsWith("omx.avcodec."))) {
            if (toLowerCase.startsWith("omx.ittiam.")) {
                i = h;
            } else if (toLowerCase.startsWith("omx.mtk.")) {
                i = VERSION.SDK_INT < 18 ? h : b;
            } else {
                Integer num = (Integer) a().get(toLowerCase);
                if (num != null) {
                    i = num.intValue();
                } else {
                    try {
                        i = mediaCodecInfo.getCapabilitiesForType(str) != null ? c : d;
                    } catch (Throwable th) {
                        i = d;
                    }
                }
            }
        }
        f fVar = new f();
        fVar.i = mediaCodecInfo;
        fVar.j = i;
        fVar.k = str;
        return fVar;
    }

    @TargetApi(16)
    public void a(String str) {
        int i = h;
        if (VERSION.SDK_INT >= 16) {
            try {
                int i2;
                CodecCapabilities capabilitiesForType = this.i.getCapabilitiesForType(str);
                if (capabilitiesForType == null || capabilitiesForType.profileLevels == null) {
                    i2 = h;
                } else {
                    CodecProfileLevel[] codecProfileLevelArr = capabilitiesForType.profileLevels;
                    int length = codecProfileLevelArr.length;
                    i2 = h;
                    for (int i3 = h; i3 < length; i3++) {
                        CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i3];
                        if (codecProfileLevel != null) {
                            i2 = Math.max(i2, codecProfileLevel.profile);
                            i = Math.max(i, codecProfileLevel.level);
                        }
                    }
                }
                Log.i(l, String.format(Locale.US, "%s", new Object[]{a(i2, i)}));
            } catch (Throwable th) {
                Log.i(l, "profile-level: exception");
            }
        }
    }

    public static String a(int i, int i2) {
        return String.format(Locale.US, " %s Profile Level %s (%d,%d)", new Object[]{a(i), b(i2), Integer.valueOf(i), Integer.valueOf(i2)});
    }

    public static String a(int i) {
        switch (i) {
            case d.a /*1*/:
                return "Baseline";
            case d.b /*2*/:
                return "Main";
            case TexTransformUtil.COORDS_COUNT /*4*/:
                return "Extends";
            case TexTransformUtil.COORDS_STRIDE /*8*/:
                return "High";
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT /*16*/:
                return "High10";
            case 32:
                return "High422";
            case 64:
                return "High444";
            default:
                return "Unknown";
        }
    }

    public static String b(int i) {
        switch (i) {
            case d.a /*1*/:
                return "1";
            case d.b /*2*/:
                return "1b";
            case TexTransformUtil.COORDS_COUNT /*4*/:
                return "11";
            case TexTransformUtil.COORDS_STRIDE /*8*/:
                return "12";
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT /*16*/:
                return "13";
            case 32:
                return "2";
            case 64:
                return "21";
            case 128:
                return "22";
            case StreamerConstants.CODEC_ID_AAC /*256*/:
                return "3";
            case 512:
                return "31";
            case 1024:
                return "32";
            case 2048:
                return "4";
            case 4096:
                return "41";
            case 8192:
                return "42";
            case 16384:
                return "5";
            case 32768:
                return "51";
            case AVFrameBase.FLAG_DETACH_NATIVE_MODULE /*65536*/:
                return "52";
            default:
                return "0";
        }
    }
}
