package com.ksyun.media.streamer.capture;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.util.BitmapLoader;
import com.ksyun.media.streamer.util.f;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaterMarkCapture {
    private static final String a = "WaterMarkCapture";
    private static final int b = 512;
    private Timer c;
    private ExecutorService d;
    private GLRender e;
    private int f;
    private int g;
    private int h;
    private int i;
    private Runnable j;
    private int k;
    private String l;
    private float m;
    public a mLogoBufSrcPin;
    public ImgTexSrcPin mLogoTexSrcPin;
    public a mTimeBufSrcPin;
    public ImgTexSrcPin mTimeTexSrcPin;
    private float n;
    private Bitmap o;
    private final Object p;
    private GLRenderListener q;

    /* renamed from: com.ksyun.media.streamer.capture.WaterMarkCapture.1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ float c;
        final /* synthetic */ float d;
        final /* synthetic */ WaterMarkCapture e;

        AnonymousClass1(WaterMarkCapture waterMarkCapture, Context context, String str, float f, float f2) {
            this.e = waterMarkCapture;
            this.a = context;
            this.b = str;
            this.c = f;
            this.d = f2;
        }

        public void run() {
            this.e.a(this.a, this.b, this.c, this.d);
        }
    }

    /* renamed from: com.ksyun.media.streamer.capture.WaterMarkCapture.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ float a;
        final /* synthetic */ float b;
        final /* synthetic */ WaterMarkCapture c;

        AnonymousClass2(WaterMarkCapture waterMarkCapture, float f, float f2) {
            this.c = waterMarkCapture;
            this.a = f;
            this.b = f2;
        }

        public void run() {
            this.c.a(this.a, this.b);
        }
    }

    /* renamed from: com.ksyun.media.streamer.capture.WaterMarkCapture.4 */
    class AnonymousClass4 extends TimerTask {
        final /* synthetic */ int a;
        final /* synthetic */ String b;
        final /* synthetic */ float c;
        final /* synthetic */ float d;
        final /* synthetic */ WaterMarkCapture e;

        AnonymousClass4(WaterMarkCapture waterMarkCapture, int i, String str, float f, float f2) {
            this.e = waterMarkCapture;
            this.a = i;
            this.b = str;
            this.c = f;
            this.d = f2;
        }

        public void run() {
            this.e.a(this.a, this.b, this.c, this.d);
        }
    }

    public WaterMarkCapture(GLRender gLRender) {
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.p = new Object();
        this.q = new GLRenderListener() {
            final /* synthetic */ WaterMarkCapture a;

            {
                this.a = r1;
            }

            public void onReady() {
                this.a.e.queueEvent(new Runnable() {
                    final /* synthetic */ AnonymousClass5 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        synchronized (this.a.a.p) {
                            if (this.a.a.o != null) {
                                this.a.a.a(this.a.a.o, this.a.a.mLogoTexSrcPin, false);
                            }
                        }
                        if (this.a.a.c != null) {
                            this.a.a.a(this.a.a.k, this.a.a.l, this.a.a.m, this.a.a.n);
                        }
                    }
                });
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        };
        this.mLogoTexSrcPin = new ImgTexSrcPin(gLRender);
        this.mLogoBufSrcPin = new a();
        this.mTimeTexSrcPin = new ImgTexSrcPin(gLRender);
        this.mTimeBufSrcPin = new a();
        this.d = Executors.newSingleThreadExecutor();
        this.e = gLRender;
        this.e.addListener(this.q);
    }

    public void setPreviewSize(int i, int i2) {
        this.f = i;
        this.g = i2;
        if (a() && this.j != null) {
            this.d.execute(this.j);
            this.j = null;
        }
    }

    public void setTargetSize(int i, int i2) {
        this.h = i;
        this.i = i2;
        if (a() && this.j != null) {
            this.d.execute(this.j);
            this.j = null;
        }
    }

    public void showLogo(Context context, String str, float f, float f2) {
        if (!TextUtils.isEmpty(str)) {
            Runnable anonymousClass1 = new AnonymousClass1(this, context, str, f, f2);
            if (a()) {
                this.d.execute(anonymousClass1);
            } else {
                this.j = anonymousClass1;
            }
        }
    }

    public void showLogo(Bitmap bitmap, float f, float f2) {
        if (bitmap != null && !bitmap.isRecycled()) {
            synchronized (this.p) {
                if (!(this.o == null || this.o == bitmap)) {
                    this.o.recycle();
                }
                this.o = bitmap;
            }
            Runnable anonymousClass2 = new AnonymousClass2(this, f, f2);
            if (a()) {
                this.d.execute(anonymousClass2);
            } else {
                this.j = anonymousClass2;
            }
        }
    }

    public void hideLogo() {
        synchronized (this.p) {
            if (this.o != null) {
                this.o.recycle();
                this.o = null;
            }
        }
        if (!this.d.isShutdown()) {
            this.d.execute(new Runnable() {
                final /* synthetic */ WaterMarkCapture a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.mLogoBufSrcPin.a(null, false);
                    this.a.mLogoTexSrcPin.updateFrame(null, false);
                }
            });
        }
    }

    public void showTime(int i, String str, float f, float f2) {
        if (this.c == null) {
            this.k = i;
            this.l = str;
            this.m = f;
            this.n = f2;
            this.c = new Timer();
            this.c.schedule(new AnonymousClass4(this, i, str, f, f2), 0, 1000);
            StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_WATERMARK);
        }
    }

    public void hideTime() {
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        this.mTimeBufSrcPin.a(null, false);
        this.mTimeTexSrcPin.updateFrame(null, false);
    }

    public void release() {
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        synchronized (this.p) {
            if (this.o != null) {
                this.o.recycle();
                this.o = null;
            }
        }
        this.d.shutdown();
        this.mLogoTexSrcPin.release();
        this.mLogoBufSrcPin.a();
        this.mTimeTexSrcPin.release();
        this.mTimeBufSrcPin.a();
        this.e.removeListener(this.q);
    }

    private boolean a() {
        return (this.f == 0 || this.g == 0 || this.h == 0 || this.i == 0) ? false : true;
    }

    private void a(Context context, String str, float f, float f2) {
        int i = b;
        int i2 = (((int) (((float) this.f) * f)) / 2) * 2;
        int i3 = (((int) (((float) this.g) * f2)) / 2) * 2;
        if (i2 == 0 && i3 == 0) {
            i3 = b;
        } else {
            i = i3;
            i3 = i2;
        }
        synchronized (this.p) {
            if (this.o != null) {
                this.o.recycle();
            }
            this.o = BitmapLoader.loadBitmap(context, str, i3, i);
        }
        a(f, f2);
    }

    private void a(float f, float f2) {
        a(this.o, this.mLogoBufSrcPin, f, f2);
        a(this.o, this.mLogoTexSrcPin, false);
        StatsLogReport.getInstance().updateFunctionPoint(StatsConstant.FUNCTION_WATERMARK);
    }

    private void a(int i, String str, float f, float f2) {
        if (TextUtils.isEmpty(str)) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        Bitmap a = f.a(new SimpleDateFormat(str, Locale.getDefault()).format(new Date()), i, 32.0f);
        a(a, this.mTimeBufSrcPin, f, f2);
        a(a, this.mTimeTexSrcPin, true);
    }

    private void a(Bitmap bitmap, a aVar, float f, float f2) {
        if (aVar.isConnected() && bitmap != null) {
            int i = (((int) (((float) this.h) * f)) / 2) * 2;
            int i2 = (((int) (((float) this.i) * f2)) / 2) * 2;
            if (i != 0 || i2 != 0) {
                boolean z;
                if (i == 0) {
                    i = (((bitmap.getWidth() * i2) / bitmap.getHeight()) / 2) * 2;
                } else if (i2 == 0) {
                    i2 = (((bitmap.getHeight() * i) / bitmap.getWidth()) / 2) * 2;
                }
                if (i == bitmap.getWidth() && i2 == bitmap.getHeight()) {
                    z = false;
                } else {
                    bitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
                    z = true;
                }
                aVar.a(bitmap, z);
            }
        }
    }

    private void a(Bitmap bitmap, ImgTexSrcPin imgTexSrcPin, boolean z) {
        if (imgTexSrcPin.isConnected()) {
            imgTexSrcPin.updateFrame(bitmap, z);
        } else if (z) {
            bitmap.recycle();
        }
    }
}
