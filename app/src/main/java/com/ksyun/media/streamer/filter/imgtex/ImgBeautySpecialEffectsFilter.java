package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.gles.GLRender;

public class ImgBeautySpecialEffectsFilter extends ImgBeautyLookUpFilter {
    public static final int KSY_SPECIAL_EFFECT_BEAUTY = 2;
    public static final int KSY_SPECIAL_EFFECT_BLUE = 5;
    public static final int KSY_SPECIAL_EFFECT_FRESHY = 1;
    public static final int KSY_SPECIAL_EFFECT_NATURE = 13;
    public static final int KSY_SPECIAL_EFFECT_NOSTALGIA = 6;
    public static final int KSY_SPECIAL_EFFECT_RUDDY = 11;
    public static final int KSY_SPECIAL_EFFECT_RUDDY_NIGHT = 9;
    public static final int KSY_SPECIAL_EFFECT_SAKURA = 7;
    public static final int KSY_SPECIAL_EFFECT_SAKURA_NIGHT = 8;
    public static final int KSY_SPECIAL_EFFECT_SEPIA = 4;
    public static final int KSY_SPECIAL_EFFECT_SUNSHINE_NIGHT = 10;
    public static final int KSY_SPECIAL_EFFECT_SUSHINE = 12;
    public static final int KSY_SPECIAL_EFFECT_SWEETY = 3;
    private static final String a = "assets://KSYResource/";
    private int b;
    private String c;
    private boolean d;

    public ImgBeautySpecialEffectsFilter(GLRender gLRender, Context context, int i) {
        super(gLRender, context);
        this.b = 0;
        this.c = null;
        this.d = false;
        setSpecialEffect(i);
    }

    ImgBeautySpecialEffectsFilter(GLRender gLRender, Context context, String str) {
        super(gLRender, context);
        this.b = 0;
        this.c = null;
        this.d = false;
        a(str);
    }

    public void setSpecialEffect(int i) {
        if (this.b != i) {
            this.b = i;
            setLookupBitmap(a(i));
            if (this.d) {
                StatsLogReport instance = StatsLogReport.getInstance();
                String[] strArr = new String[KSY_SPECIAL_EFFECT_BEAUTY];
                strArr[0] = getClass().getSimpleName();
                strArr[KSY_SPECIAL_EFFECT_FRESHY] = this.c;
                instance.updateBeautyType(strArr);
            }
        }
    }

    protected void onRelease() {
        super.onRelease();
        this.d = false;
        this.b = 0;
        this.c = null;
    }

    public void setTakeEffect(boolean z) {
        this.d = z;
    }

    public String getSpecialName() {
        return this.c;
    }

    private void a(String str) {
        setLookupBitmap(a + str);
    }

    private String a(int i) {
        String str;
        switch (i) {
            case KSY_SPECIAL_EFFECT_FRESHY /*1*/:
                str = "1_freshy";
                break;
            case KSY_SPECIAL_EFFECT_BEAUTY /*2*/:
                str = "2_beauty";
                break;
            case KSY_SPECIAL_EFFECT_SWEETY /*3*/:
                str = "3_sweety";
                break;
            case KSY_SPECIAL_EFFECT_SEPIA /*4*/:
                str = "4_sepia";
                break;
            case KSY_SPECIAL_EFFECT_BLUE /*5*/:
                str = "5_blue";
                break;
            case KSY_SPECIAL_EFFECT_NOSTALGIA /*6*/:
                str = "6_nostalgia";
                break;
            case KSY_SPECIAL_EFFECT_SAKURA /*7*/:
                str = "7_sakura";
                break;
            case KSY_SPECIAL_EFFECT_SAKURA_NIGHT /*8*/:
                str = "8_sakura_night";
                break;
            case KSY_SPECIAL_EFFECT_RUDDY_NIGHT /*9*/:
                str = "9_ruddy_night";
                break;
            case KSY_SPECIAL_EFFECT_SUNSHINE_NIGHT /*10*/:
                str = "10_sunshine_night";
                break;
            case KSY_SPECIAL_EFFECT_RUDDY /*11*/:
                str = "11_ruddy";
                break;
            case KSY_SPECIAL_EFFECT_SUSHINE /*12*/:
                str = "12_sunshine";
                break;
            case KSY_SPECIAL_EFFECT_NATURE /*13*/:
                str = "13_nature";
                break;
            default:
                str = null;
                break;
        }
        this.c = str;
        return a + str + ".png";
    }
}
