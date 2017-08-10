package com.ksyun.media.streamer.capture.camera;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.ksy.statlibrary.interval.IntervalTask;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CameraTouchHelper implements View.OnTouchListener {
    private static final String a = "CameraTouchHelper";
    private static final boolean b = true;
    private CameraCapture c;
    private ICameraHintView d;
    private float e;
    private boolean f;
    private boolean g;
    private int h;
    private float i;
    private float j;
    private List<OnTouchListener> k;
    private int l;
    private int m;
    private Handler n;
    private Runnable o;
    private Runnable p;
    private boolean q;
    private int r;
    private int s;
    private Rect t;
    private Rect u;
    private boolean v;
    private int w;
    private float x;
    private long y;
    private int z;

    public interface OnTouchListener {
        boolean onTouch(View view, MotionEvent motionEvent);
    }

    public CameraTouchHelper() {
        this.e = 0.083333336f;
        this.f = b;
        this.g = b;
        this.h = IntervalTask.TIMEOUT_MILLIS;
        this.i = 4.0f;
        this.j = 1.0f;
        this.t = new Rect();
        this.u = new Rect();
        this.n = new Handler();
        this.o = new Runnable() {
            final /* synthetic */ CameraTouchHelper a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.c != null) {
                    Log.d(CameraTouchHelper.a, "Reset focus mode");
                    Parameters cameraParameters = this.a.c.getCameraParameters();
                    if (cameraParameters != null) {
                        c.a(cameraParameters);
                        List arrayList = new ArrayList();
                        arrayList.add(new Area(new Rect(0, 0, StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD, StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD), StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD));
                        cameraParameters.setMeteringAreas(arrayList);
                        this.a.c.setCameraParameters(cameraParameters);
                    }
                }
            }
        };
        this.p = new Runnable() {
            final /* synthetic */ CameraTouchHelper a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.d.setFocused(this.a.q);
            }
        };
        this.k = new LinkedList();
    }

    public void setCameraCapture(CameraCapture cameraCapture) {
        this.c = cameraCapture;
    }

    public void setCameraHintView(ICameraHintView iCameraHintView) {
        this.d = iCameraHintView;
    }

    public void setFocusAreaRadius(float f) {
        if (f <= 0.0f || f > 0.5f) {
            throw new IllegalArgumentException("radius must be > 0 && < 0.5");
        }
        this.e = f;
    }

    public void setRefocusDelay(int i) {
        this.h = i;
    }

    public void setEnableTouchFocus(boolean z) {
        this.f = z;
    }

    public void setEnableZoom(boolean z) {
        this.g = z;
    }

    public void setMaxZoomRatio(float f) {
        if (f < 1.0f) {
            f = 1.0f;
        }
        this.i = f;
    }

    public void setZoomSpeed(float f) {
        if (f < 0.1f) {
            f = 0.1f;
        } else if (f > 10.0f) {
            f = 10.0f;
        }
        this.j = f;
    }

    public synchronized void addTouchListener(OnTouchListener onTouchListener) {
        if (!this.k.contains(onTouchListener)) {
            this.k.add(onTouchListener);
        }
    }

    public synchronized void removeTouchListener(OnTouchListener onTouchListener) {
        if (this.k.contains(onTouchListener)) {
            this.k.remove(onTouchListener);
        }
    }

    public synchronized void removeAllTouchListener() {
        this.k.clear();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.l = view.getWidth();
        this.m = view.getHeight();
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.v = false;
            this.w = 1;
        } else if (action == 5) {
            this.v = b;
            this.w++;
            if (this.w == 2) {
                this.x = a(motionEvent);
                a(0, (boolean) b);
            }
        } else if (action == 6) {
            this.w--;
        } else if (action == 2) {
            if (this.w >= 2) {
                a((int) (a(motionEvent) - this.x), false);
            }
        } else if (action == 1) {
            if (!this.v) {
                a(motionEvent.getX(), motionEvent.getY());
            }
            this.v = false;
            this.w = 0;
        }
        a(view, motionEvent);
        return b;
    }

    private float a(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private boolean a(int i, boolean z) {
        int i2 = 0;
        if (!this.g || this.c == null) {
            return false;
        }
        Parameters cameraParameters = this.c.getCameraParameters();
        if (cameraParameters == null || !cameraParameters.isZoomSupported()) {
            return false;
        }
        if (z) {
            this.z = cameraParameters.getZoom();
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.y < 40) {
            return false;
        }
        int i3;
        this.y = currentTimeMillis;
        int i4 = (this.l < this.m ? this.l : this.m) / 2;
        int maxZoom = cameraParameters.getMaxZoom();
        List zoomRatios = cameraParameters.getZoomRatios();
        int i5 = (int) (this.i * 100.0f);
        int i6 = maxZoom;
        while (i6 >= 0) {
            if (i5 >= ((Integer) zoomRatios.get(i6)).intValue()) {
                i3 = i6;
                break;
            }
            i6--;
        }
        i3 = maxZoom;
        if (i6 < 0) {
            i3 = 0;
        }
        i6 = this.z + ((int) (((this.j * ((float) i)) * ((float) i3)) / ((float) i4)));
        if (i6 >= 0) {
            if (i6 > i3) {
                i2 = i3;
            } else {
                i2 = i6;
            }
        }
        if (i2 != this.z) {
            cameraParameters.setZoom(i2);
            this.c.setCameraParameters(cameraParameters);
        }
        if (this.d != null) {
            this.d.updateZoomRatio(((float) ((Integer) zoomRatios.get(i2)).intValue()) / 100.0f);
        }
        return b;
    }

    private boolean a(float f, float f2) {
        if (!this.f || this.c == null) {
            return false;
        }
        Parameters cameraParameters = this.c.getCameraParameters();
        if (cameraParameters == null) {
            return false;
        }
        List supportedFocusModes = cameraParameters.getSupportedFocusModes();
        if (supportedFocusModes == null || !supportedFocusModes.contains("auto")) {
            return false;
        }
        b(f, f2);
        Size previewSize = cameraParameters.getPreviewSize();
        int cameraDisplayOrientation = this.c.getCameraDisplayOrientation();
        a(previewSize, cameraDisplayOrientation);
        a(cameraDisplayOrientation);
        Log.d(a, "touchRect: " + this.u.toString() + " focusRect: " + this.t.toString());
        cameraParameters.setFocusMode("auto");
        List arrayList = new ArrayList();
        arrayList.add(new Area(this.t, StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD));
        cameraParameters.setFocusAreas(arrayList);
        cameraParameters.setMeteringAreas(arrayList);
        this.c.setCameraParameters(cameraParameters);
        this.c.cancelAutoFocus();
        this.c.autoFocus(new AutoFocusCallback() {
            final /* synthetic */ CameraTouchHelper a;

            {
                this.a = r1;
            }

            public void onAutoFocus(boolean z, Camera camera) {
                this.a.c.cancelAutoFocus();
                if (this.a.h > 0) {
                    this.a.n.postDelayed(this.a.o, (long) this.a.h);
                }
                if (this.a.d != null) {
                    this.a.q = z;
                    this.a.n.post(this.a.p);
                }
            }
        });
        if (this.d != null) {
            this.n.removeCallbacks(this.p);
            this.n.removeCallbacks(this.o);
            this.d.startFocus(this.u);
        }
        return b;
    }

    private void b(float f, float f2) {
        int i = this.l < this.m ? (int) (((float) this.l) * this.e) : (int) (((float) this.m) * this.e);
        int a = a((int) f, 0, this.l, i);
        int a2 = a((int) f2, 0, this.m, i);
        this.u.set(a - i, a2 - i, a + i, i + a2);
    }

    private void a(Size size, int i) {
        float f = ((float) this.l) / ((float) this.m);
        if (i % 180 == 0) {
            if (f > ((float) size.width) / ((float) size.height)) {
                this.s = (int) (((((float) size.width) / f) / ((float) size.height)) * 1000.0f);
                this.r = StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
                return;
            }
            this.s = StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
            this.r = (int) (((f * ((float) size.height)) / ((float) size.width)) * 1000.0f);
        } else if (f > ((float) size.height) / ((float) size.width)) {
            this.s = (int) (((((float) size.height) / f) / ((float) size.width)) * 1000.0f);
            this.r = StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
        } else {
            this.s = StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD;
            this.r = (int) (((f * ((float) size.width)) / ((float) size.height)) * 1000.0f);
        }
    }

    private void a(int i) {
        RectF rectF = new RectF((((((float) this.u.left) / ((float) this.l)) * ((float) this.r)) * 2.0f) - ((float) this.r), (((((float) this.u.top) / ((float) this.m)) * ((float) this.s)) * 2.0f) - ((float) this.s), (((((float) this.u.right) / ((float) this.l)) * ((float) this.r)) * 2.0f) - ((float) this.r), (((((float) this.u.bottom) / ((float) this.m)) * ((float) this.s)) * 2.0f) - ((float) this.s));
        Matrix matrix = new Matrix();
        matrix.postRotate((float) (360 - i), 0.0f, 0.0f);
        matrix.mapRect(rectF);
        rectF.round(this.t);
    }

    private int a(int i, int i2, int i3, int i4) {
        if (i < i2 + i4) {
            return i2 + i4;
        }
        if (i > i3 - i4) {
            return i3 - i4;
        }
        return i;
    }

    private synchronized void a(View view, MotionEvent motionEvent) {
        if (this.k != null) {
            for (int i = 0; i < this.k.size(); i++) {
                ((OnTouchListener) this.k.get(i)).onTouch(view, motionEvent);
            }
        }
    }
}
