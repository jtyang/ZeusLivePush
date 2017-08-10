package com.ksyun.media.streamer.filter.imgtex;

import android.content.Context;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase.OnErrorListener;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.PinAdapter;
import com.ksyun.media.streamer.framework.SinkPin;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.gles.GLRender;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ImgTexFilterMgt {
    public static final int KSY_FILTER_BEAUTY_DENOISE = 19;
    public static final int KSY_FILTER_BEAUTY_DISABLE = 0;
    public static final int KSY_FILTER_BEAUTY_ILLUSION = 18;
    public static final int KSY_FILTER_BEAUTY_PRO = 23;
    public static final int KSY_FILTER_BEAUTY_PRO1 = 24;
    public static final int KSY_FILTER_BEAUTY_PRO2 = 25;
    public static final int KSY_FILTER_BEAUTY_PRO3 = 26;
    public static final int KSY_FILTER_BEAUTY_PRO4 = 27;
    public static final int KSY_FILTER_BEAUTY_SKINWHITEN = 17;
    public static final int KSY_FILTER_BEAUTY_SMOOTH = 20;
    public static final int KSY_FILTER_BEAUTY_SOFT = 16;
    public static final int KSY_FILTER_BEAUTY_SOFT_EXT = 21;
    public static final int KSY_FILTER_BEAUTY_SOFT_SHARPEN = 22;
    private static final String a = "ImgTexFilterMgt";
    private Context b;
    private PinAdapter<ImgTexFrame> c;
    private PinAdapter<ImgTexFrame> d;
    private LinkedList<ImgFilterBase> e;
    private LinkedList<ImgFilterBase> f;
    private final Object g;
    private OnErrorListener h;

    public ImgTexFilterMgt(Context context) {
        this.g = new Object();
        this.b = context;
        this.c = new PinAdapter();
        this.d = new PinAdapter();
        this.c.mSrcPin.connect(this.d.mSinkPin);
        this.e = new LinkedList();
        this.f = new LinkedList();
    }

    public SinkPin<ImgTexFrame> getSinkPin() {
        return this.c.mSinkPin;
    }

    public SrcPin<ImgTexFrame> getSrcPin() {
        return this.d.mSrcPin;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.h = onErrorListener;
        synchronized (this.g) {
            if (!(this.h == null || this.e == null || this.e.isEmpty())) {
                Iterator it = this.e.iterator();
                while (it.hasNext()) {
                    ((ImgFilterBase) it.next()).setOnErrorListener(this.h);
                }
            }
        }
    }

    public void setFilter(GLRender gLRender, int i) {
        ImgFilterBase imgBeautySoftFilter;
        switch (i) {
            case KSY_FILTER_BEAUTY_SOFT /*16*/:
                imgBeautySoftFilter = new ImgBeautySoftFilter(gLRender);
                break;
            case KSY_FILTER_BEAUTY_SKINWHITEN /*17*/:
                imgBeautySoftFilter = new ImgBeautySkinWhitenFilter(gLRender);
                break;
            case KSY_FILTER_BEAUTY_ILLUSION /*18*/:
                imgBeautySoftFilter = new ImgBeautyIllusionFilter(gLRender);
                break;
            case KSY_FILTER_BEAUTY_DENOISE /*19*/:
                imgBeautySoftFilter = new ImgBeautyDenoiseFilter(gLRender);
                break;
            case KSY_FILTER_BEAUTY_SMOOTH /*20*/:
                imgBeautySoftFilter = new ImgBeautySmoothFilter(gLRender, this.b);
                break;
            case KSY_FILTER_BEAUTY_SOFT_EXT /*21*/:
                imgBeautySoftFilter = new ImgBeautySoftExtFilter(gLRender);
                break;
            case KSY_FILTER_BEAUTY_SOFT_SHARPEN /*22*/:
                imgBeautySoftFilter = new ImgBeautySoftSharpenFilter(gLRender);
                break;
            case KSY_FILTER_BEAUTY_PRO /*23*/:
                imgBeautySoftFilter = new ImgBeautyProFilter(gLRender, this.b);
                break;
            case KSY_FILTER_BEAUTY_PRO1 /*24*/:
                imgBeautySoftFilter = new ImgBeautyProFilter(gLRender, this.b, 1);
                break;
            case KSY_FILTER_BEAUTY_PRO2 /*25*/:
                imgBeautySoftFilter = new ImgBeautyProFilter(gLRender, this.b, 2);
                break;
            case KSY_FILTER_BEAUTY_PRO3 /*26*/:
                imgBeautySoftFilter = new ImgBeautyProFilter(gLRender, this.b, 3);
                break;
            case KSY_FILTER_BEAUTY_PRO4 /*27*/:
                imgBeautySoftFilter = new ImgBeautyProFilter(gLRender, this.b, 4);
                break;
            default:
                imgBeautySoftFilter = null;
                break;
        }
        setFilter(imgBeautySoftFilter);
    }

    public void setFilter(ImgFilterBase imgFilterBase) {
        List list = null;
        if (imgFilterBase != null) {
            list = new LinkedList();
            list.add(imgFilterBase);
        }
        setFilter(list);
    }

    public void setFilter(ImgFilterBase[] imgFilterBaseArr) {
        List list = null;
        if (imgFilterBaseArr != null && imgFilterBaseArr.length > 0) {
            list = new LinkedList();
            Collections.addAll(list, imgFilterBaseArr);
        }
        setFilter(list);
    }

    public synchronized void setFilter(List<? extends ImgFilterBase> list) {
        synchronized (this) {
            if (list == null) {
                StatsLogReport.getInstance().updateBeautyType("KSY_FILTER_BEAUTY_DISABLE");
            }
            if (!(this.h == null || list == null || list.isEmpty())) {
                for (ImgFilterBase onErrorListener : list) {
                    onErrorListener.setOnErrorListener(this.h);
                }
            }
            synchronized (this.g) {
                if (!this.e.isEmpty()) {
                    ((ImgFilterBase) this.e.get(this.e.size() - 1)).getSrcPin().disconnect(false);
                    this.c.mSrcPin.disconnect(true);
                    this.e.clear();
                } else if (list != null) {
                    if (!list.isEmpty()) {
                        this.c.mSrcPin.disconnect(false);
                    }
                }
                if (list != null && !list.isEmpty()) {
                    this.c.mSrcPin.connect(((ImgFilterBase) list.get(KSY_FILTER_BEAUTY_DISABLE)).getSinkPin());
                    for (int i = 1; i < list.size(); i++) {
                        ((ImgFilterBase) list.get(i - 1)).getSrcPin().connect(((ImgFilterBase) list.get(i)).getSinkPin());
                    }
                    if (this.f.isEmpty()) {
                        ((ImgFilterBase) list.get(list.size() - 1)).getSrcPin().connect(this.d.mSinkPin);
                    } else {
                        ((ImgFilterBase) list.get(list.size() - 1)).getSrcPin().connect(((ImgFilterBase) this.f.get(KSY_FILTER_BEAUTY_DISABLE)).getSinkPin());
                    }
                    this.e.addAll(list);
                } else if (this.f.isEmpty()) {
                    this.c.mSrcPin.connect(this.d.mSinkPin);
                } else {
                    this.c.mSrcPin.connect(((ImgFilterBase) this.f.get(KSY_FILTER_BEAUTY_DISABLE)).getSinkPin());
                }
            }
            a(list);
        }
    }

    public synchronized List<ImgFilterBase> getFilter() {
        return this.e;
    }

    public void setExtraFilter(ImgFilterBase imgFilterBase) {
        List list = null;
        if (imgFilterBase != null) {
            list = new LinkedList();
            list.add(imgFilterBase);
        }
        setExtraFilter(list);
    }

    public void setExtraFilter(List<? extends ImgFilterBase> list) {
        int i = 1;
        synchronized (this.g) {
            if (!this.f.isEmpty()) {
                ((ImgFilterBase) this.f.get(this.f.size() - 1)).getSrcPin().disconnect(false);
                if (this.e.isEmpty()) {
                    this.c.mSrcPin.disconnect(true);
                } else {
                    ((ImgFilterBase) this.e.get(this.e.size() - 1)).getSrcPin().disconnect(true);
                }
                this.f.clear();
            } else if (list != null) {
                if (!list.isEmpty()) {
                    if (this.e.isEmpty()) {
                        this.c.mSrcPin.disconnect(false);
                    } else {
                        ((ImgFilterBase) this.e.get(this.e.size() - 1)).getSrcPin().disconnect(false);
                    }
                }
            }
            if (list != null && !list.isEmpty()) {
                if (this.e.isEmpty()) {
                    this.c.mSrcPin.connect(((ImgFilterBase) list.get(KSY_FILTER_BEAUTY_DISABLE)).getSinkPin());
                } else {
                    ((ImgFilterBase) this.e.get(this.e.size() - 1)).getSrcPin().connect(((ImgFilterBase) list.get(KSY_FILTER_BEAUTY_DISABLE)).getSinkPin());
                }
                while (i < list.size()) {
                    ((ImgFilterBase) list.get(i - 1)).getSrcPin().connect(((ImgFilterBase) list.get(i)).getSinkPin());
                    i++;
                }
                ((ImgFilterBase) list.get(list.size() - 1)).getSrcPin().connect(this.d.mSinkPin);
                this.f.addAll(list);
            } else if (this.e.isEmpty()) {
                this.c.mSrcPin.connect(this.d.mSinkPin);
            } else {
                ((ImgFilterBase) this.e.get(this.e.size() - 1)).getSrcPin().connect(this.d.mSinkPin);
            }
        }
        a(list);
    }

    public synchronized List<ImgFilterBase> getExtraFilters() {
        return this.f;
    }

    private void a(List<? extends ImgFilterBase> list) {
        if (list != null && list.size() > 0) {
            for (int i = KSY_FILTER_BEAUTY_DISABLE; i < list.size(); i++) {
                if (list.get(i) != null) {
                    if (list.get(i) instanceof ImgBeautySpecialEffectsFilter) {
                        ((ImgBeautySpecialEffectsFilter) list.get(i)).setTakeEffect(true);
                        StatsLogReport.getInstance().updateBeautyType(((ImgFilterBase) list.get(i)).getClass().getSimpleName(), r0.getSpecialName());
                    } else {
                        StatsLogReport.getInstance().updateBeautyType(((ImgFilterBase) list.get(i)).getClass().getSimpleName());
                    }
                }
            }
        }
    }

    public void release() {
        synchronized (this.g) {
            this.c.mSrcPin.disconnect(true);
            this.e.clear();
            this.f.clear();
        }
    }
}
