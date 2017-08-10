package com.ksyun.media.streamer.capture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.ksyun.media.streamer.capture.camera.c;
import com.ksyun.media.streamer.framework.ImgBufFormat;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.framework.SrcPin;
import com.ksyun.media.streamer.kit.OnPreviewFrameListener;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.publisher.RtmpPublisher;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GlUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class CameraCapture implements OnFrameAvailableListener {
    public static final int CAMERA_ERROR_EVICTED = -2007;
    public static final int CAMERA_ERROR_SERVER_DIED = -2006;
    public static final int CAMERA_ERROR_START_FAILED = -2002;
    public static final int CAMERA_ERROR_UNKNOWN = -2001;
    public static final int CAMERA_STATE_IDLE = 0;
    public static final int CAMERA_STATE_INITIALIZING = 1;
    public static final int CAMERA_STATE_PREVIEWING = 2;
    public static final int CAMERA_STATE_STOPPING = 3;
    public static final int DEFAULT_PREVIEW_FPS = 15;
    public static final int FACING_BACK = 0;
    public static final int FACING_FRONT = 1;
    private static final String a = "CameraCapture";
    private static final boolean b = true;
    private static final boolean c = true;
    private static final boolean d = false;
    private static final int e = 1;
    private static final int f = 2;
    private static final int g = 3;
    private static final int h = 4;
    private static final int i = 1;
    private static final int j = 2;
    private static final int k = 3;
    private static final int l = 4;
    private static final int m = 11;
    private SurfaceTexture A;
    private com.ksyun.media.streamer.capture.camera.b.b B;
    private Parameters C;
    private final Handler D;
    private HandlerThread E;
    private Handler F;
    private ConditionVariable G;
    private volatile boolean H;
    private ImgTexFormat I;
    private ImgBufFormat J;
    private byte[] K;
    private ByteBuffer L;
    private GLRender M;
    private boolean N;
    private boolean O;
    private boolean P;
    private boolean Q;
    private long R;
    private long S;
    private GLRenderListener T;
    private ErrorCallback U;
    private PreviewCallback V;
    public final SrcPin<ImgBufFrame> mImgBufSrcPin;
    public final SrcPin<ImgTexFrame> mImgTexSrcPin;
    private Context n;
    private OnCameraCaptureListener o;
    private OnPreviewFrameListener p;
    private int q;
    private b r;
    private float s;
    private b t;
    private float u;
    private int v;
    private String w;
    private AtomicInteger x;
    private final Object y;
    private int z;

    /* renamed from: com.ksyun.media.streamer.capture.CameraCapture.1 */
    class AnonymousClass1 extends Handler {
        final /* synthetic */ CameraCapture a;

        AnonymousClass1(CameraCapture cameraCapture, Looper looper) {
            this.a = cameraCapture;
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = CameraCapture.i;
            switch (message.what) {
                case CameraCapture.i /*1*/:
                    if (this.a.x.get() == 0) {
                        this.a.x.set(CameraCapture.i);
                        i = this.a.b();
                        if (i == 0) {
                            this.a.x.set(CameraCapture.j);
                            this.a.D.sendEmptyMessage(CameraCapture.j);
                            this.a.D.sendEmptyMessage(CameraCapture.l);
                            return;
                        }
                        this.a.x.set(CameraCapture.FACING_BACK);
                        this.a.D.sendMessage(this.a.D.obtainMessage(CameraCapture.m, i, CameraCapture.FACING_BACK));
                    }
                case CameraCapture.j /*2*/:
                    if (this.a.x.get() == CameraCapture.j) {
                        this.a.x.set(CameraCapture.k);
                        this.a.c();
                        this.a.x.set(CameraCapture.FACING_BACK);
                        this.a.D.sendEmptyMessage(CameraCapture.k);
                    }
                    this.a.G.open();
                case CameraCapture.k /*3*/:
                    if (this.a.x.get() == CameraCapture.j) {
                        this.a.x.set(CameraCapture.i);
                        this.a.c();
                        if (this.a.H) {
                            this.a.x.set(CameraCapture.FACING_BACK);
                            this.a.D.sendEmptyMessage(CameraCapture.k);
                            return;
                        }
                        CameraCapture cameraCapture = this.a;
                        if (this.a.q != 0) {
                            i = CameraCapture.FACING_BACK;
                        }
                        cameraCapture.q = i;
                        i = this.a.b();
                        if (i == 0) {
                            this.a.x.set(CameraCapture.j);
                            this.a.D.sendEmptyMessage(CameraCapture.l);
                            return;
                        }
                        this.a.x.set(CameraCapture.FACING_BACK);
                        this.a.D.sendMessage(this.a.D.obtainMessage(CameraCapture.m, i, CameraCapture.FACING_BACK));
                    }
                case CameraCapture.l /*4*/:
                    this.a.E.quit();
                default:
            }
        }
    }

    public interface OnCameraCaptureListener {
        void onError(int i);

        void onFacingChanged(int i);

        void onStarted();
    }

    private static class a extends Handler {
        private final WeakReference<CameraCapture> a;

        a(CameraCapture cameraCapture, Looper looper) {
            super(looper);
            this.a = new WeakReference(cameraCapture);
        }

        public void handleMessage(Message message) {
            CameraCapture cameraCapture = (CameraCapture) this.a.get();
            if (cameraCapture != null) {
                switch (message.what) {
                    case CameraCapture.i /*1*/:
                    case CameraCapture.j /*2*/:
                        Log.d(CameraCapture.a, "Camera preview started");
                        if (cameraCapture.o != null) {
                            cameraCapture.o.onStarted();
                        }
                    case CameraCapture.k /*3*/:
                        Log.d(CameraCapture.a, "Camera closed");
                    case CameraCapture.l /*4*/:
                        if (cameraCapture.o != null) {
                            cameraCapture.o.onFacingChanged(cameraCapture.q);
                        }
                    case CameraCapture.m /*11*/:
                        cameraCapture.stop();
                        cameraCapture.x.set(CameraCapture.FACING_BACK);
                        StatsLogReport.getInstance().reportError(message.arg1, CameraCapture.j);
                        if (cameraCapture.o != null) {
                            cameraCapture.o.onError(message.arg1);
                        }
                    default:
                }
            }
        }
    }

    public static class b {
        public final int a;
        public final int b;

        public b(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public String toString() {
            return "Size{height=" + this.b + ", width=" + this.a + "}";
        }
    }

    public CameraCapture(Context context, GLRender gLRender) {
        this.q = FACING_BACK;
        this.y = new Object();
        this.G = new ConditionVariable();
        this.H = d;
        this.N = d;
        this.O = d;
        this.P = d;
        this.Q = d;
        this.T = new GLRenderListener() {
            final /* synthetic */ CameraCapture a;

            {
                this.a = r1;
            }

            public void onReady() {
                Log.d(CameraCapture.a, "onGLContext ready");
                this.a.z = GlUtil.createOESTextureObject();
                synchronized (this.a.y) {
                    if (this.a.A != null) {
                        this.a.A.release();
                    }
                    this.a.A = new SurfaceTexture(this.a.z);
                    this.a.A.setOnFrameAvailableListener(this.a);
                    if (this.a.B != null) {
                        this.a.B.b(this.a.A);
                        this.a.B.e();
                    }
                }
                this.a.N = CameraCapture.d;
                this.a.P = CameraCapture.d;
            }

            public void onSizeChanged(int i, int i2) {
                Log.d(CameraCapture.a, "onSizeChanged " + i + "x" + i2);
            }

            public void onDrawFrame() {
                long nanoTime = (System.nanoTime() / 1000) / 1000;
                try {
                    this.a.A.updateTexImage();
                    if (this.a.x.get() == CameraCapture.j && this.a.P) {
                        if (!this.a.N) {
                            this.a.N = CameraCapture.c;
                            a();
                        }
                        float[] fArr = new float[16];
                        this.a.A.getTransformMatrix(fArr);
                        try {
                            this.a.mImgTexSrcPin.onFrameAvailable(new ImgTexFrame(this.a.I, this.a.z, fArr, nanoTime));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(CameraCapture.a, "Draw frame failed, ignore");
                        }
                        this.a.S = 1 + this.a.S;
                        long currentTimeMillis = System.currentTimeMillis();
                        long r = currentTimeMillis - this.a.R;
                        if (r >= 5000) {
                            float s = (((float) this.a.S) * 1000.0f) / ((float) r);
                            String str = CameraCapture.a;
                            StringBuilder append = new StringBuilder().append("preview fps: ");
                            Object[] objArr = new Object[CameraCapture.i];
                            objArr[CameraCapture.FACING_BACK] = Float.valueOf(s);
                            Log.d(str, append.append(String.format(Locale.getDefault(), "%.2f", objArr)).toString());
                            this.a.S = 0;
                            this.a.R = currentTimeMillis;
                        }
                    }
                } catch (Exception e2) {
                    Log.e(CameraCapture.a, "updateTexImage failed, ignore");
                }
            }

            public void onReleased() {
                Log.d(CameraCapture.a, "onGLContext released");
                this.a.P = CameraCapture.d;
                synchronized (this.a.y) {
                    if (this.a.B != null) {
                        this.a.B.g();
                    }
                    if (this.a.A != null) {
                        this.a.A.setOnFrameAvailableListener(null);
                        this.a.A.release();
                        this.a.A = null;
                    }
                }
            }

            private void a() {
                int a = c.a(this.a.v, this.a.a(this.a.q));
                int i = this.a.t.a;
                int i2 = this.a.t.b;
                if (a % 180 != 0) {
                    i = this.a.t.b;
                    i2 = this.a.t.a;
                }
                this.a.I = new ImgTexFormat(CameraCapture.k, i, i2);
                this.a.mImgTexSrcPin.onFormatChanged(this.a.I);
                this.a.R = System.currentTimeMillis();
                this.a.S = 0;
            }
        };
        this.U = new ErrorCallback() {
            final /* synthetic */ CameraCapture a;

            {
                this.a = r1;
            }

            public void onError(int i, Camera camera) {
                int i2;
                Log.e(CameraCapture.a, "onCameraError: " + i);
                switch (i) {
                    case CameraCapture.j /*2*/:
                        i2 = CameraCapture.CAMERA_ERROR_EVICTED;
                        break;
                    case RtmpPublisher.INFO_PACKET_SEND_SLOW /*100*/:
                        i2 = CameraCapture.CAMERA_ERROR_SERVER_DIED;
                        break;
                    default:
                        i2 = CameraCapture.CAMERA_ERROR_UNKNOWN;
                        break;
                }
                this.a.D.sendMessage(this.a.D.obtainMessage(CameraCapture.m, i2, CameraCapture.FACING_BACK));
            }
        };
        this.V = new PreviewCallback() {
            final /* synthetic */ CameraCapture a;

            {
                this.a = r1;
            }

            public void onPreviewFrame(byte[] bArr, Camera camera) {
                if (this.a.mImgBufSrcPin.isConnected() && bArr != null) {
                    long nanoTime = (System.nanoTime() / 1000) / 1000;
                    if (this.a.p != null) {
                        this.a.p.onPreviewFrame(bArr, this.a.t.a, this.a.t.b, this.a.Q);
                    }
                    if (this.a.L == null) {
                        this.a.L = ByteBuffer.allocateDirect(bArr.length);
                    }
                    if (this.a.L.capacity() < bArr.length) {
                        this.a.L = null;
                        this.a.L = ByteBuffer.allocateDirect(bArr.length);
                    }
                    this.a.L.clear();
                    this.a.L.put(bArr);
                    try {
                        if (!this.a.O) {
                            int a = c.a(this.a.v, this.a.q);
                            if (this.a.q == CameraCapture.i) {
                                a = (360 - a) % 360;
                            }
                            this.a.J = new ImgBufFormat(CameraCapture.i, this.a.t.a, this.a.t.b, a);
                            this.a.O = CameraCapture.c;
                            this.a.mImgBufSrcPin.onFormatChanged(this.a.J);
                        }
                        this.a.mImgBufSrcPin.onFrameAvailable(new ImgBufFrame(this.a.J, this.a.L, nanoTime));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (this.a.B != null) {
                    this.a.B.a(bArr);
                }
            }
        };
        this.n = context;
        this.mImgTexSrcPin = new SrcPin();
        this.mImgBufSrcPin = new SrcPin();
        this.x = new AtomicInteger(FACING_BACK);
        this.D = new a(this, Looper.getMainLooper());
        a();
        this.r = new b(1280, 720);
        this.s = StreamerConstants.DEFAULT_TARGET_FPS;
        this.M = gLRender;
        this.M.addListener(this.T);
    }

    public void setOnCameraCaptureListener(OnCameraCaptureListener onCameraCaptureListener) {
        this.o = onCameraCaptureListener;
    }

    @Deprecated
    public void setOnPreviewFrameListener(OnPreviewFrameListener onPreviewFrameListener) {
        this.p = onPreviewFrameListener;
    }

    public void setPreviewSize(int i, int i2) {
        if (i > i2) {
            this.r = new b(i, i2);
        } else {
            this.r = new b(i2, i);
        }
    }

    public void setPreviewFps(float f) {
        this.s = f;
    }

    public void setOrientation(int i) {
        StatsLogReport.getInstance().setIsLandscape(i % 180 != 0 ? c : d);
        if (this.v != i) {
            this.v = i;
            if (this.x.get() == j) {
                f();
                this.N = d;
                this.O = d;
            }
        }
    }

    public int getCameraDisplayOrientation() {
        return c.a(this.v, a(this.q));
    }

    public synchronized void start(int i) {
        Log.d(a, "start");
        this.q = i;
        this.F.removeCallbacksAndMessages(null);
        this.F.sendEmptyMessage(i);
    }

    public synchronized void stop() {
        Log.d(a, "stop");
        this.G.close();
        this.H = c;
        this.F.removeCallbacksAndMessages(null);
        this.F.sendEmptyMessage(j);
        this.G.block();
        this.H = d;
        Log.d(a, "stopped");
    }

    public synchronized boolean switchCamera() {
        boolean z = d;
        synchronized (this) {
            if (this.x.get() != j) {
                Log.e(a, "Call start on invalid state");
            } else if (this.q != 0 || a((int) i) >= 0) {
                this.F.removeMessages(k);
                this.F.sendEmptyMessage(k);
                z = c;
            }
        }
        return z;
    }

    public synchronized boolean isTorchSupported() {
        boolean z = d;
        synchronized (this) {
            if (this.x.get() == j && this.C != null) {
                List supportedFlashModes = this.C.getSupportedFlashModes();
                if (!(supportedFlashModes == null || supportedFlashModes.size() == 0)) {
                    z = supportedFlashModes.contains("torch");
                }
            }
        }
        return z;
    }

    public synchronized boolean toggleTorch(boolean z) {
        boolean z2 = d;
        synchronized (this) {
            if (this.x.get() == j && this.C != null) {
                List supportedFlashModes = this.C.getSupportedFlashModes();
                if (!(supportedFlashModes == null || supportedFlashModes.size() == 0)) {
                    if (z && supportedFlashModes.contains("torch")) {
                        this.C.setFlashMode("torch");
                    } else if (!z) {
                        if (supportedFlashModes.contains("off")) {
                            this.C.setFlashMode("off");
                        }
                    }
                    if (this.B.b(this.C)) {
                        z2 = c;
                    } else {
                        Log.e(a, "Toggle flash failed!");
                        this.C = this.B.i();
                    }
                }
            }
        }
        return z2;
    }

    public synchronized Parameters getCameraParameters() {
        Parameters parameters;
        if (this.x.get() != j || this.B == null) {
            parameters = null;
        } else {
            parameters = this.B.i();
        }
        return parameters;
    }

    public synchronized boolean setCameraParameters(Parameters parameters) {
        boolean z;
        if (this.x.get() != j) {
            z = d;
        } else {
            z = this.B.b(parameters);
            this.C = this.B.i();
        }
        return z;
    }

    public synchronized void setCameraParametersAsync(Parameters parameters) {
        if (this.x.get() == j) {
            this.B.c(parameters);
        }
    }

    public synchronized void autoFocus(AutoFocusCallback autoFocusCallback) {
        if (this.x.get() != j || this.B == null) {
            Log.e(a, "Call autoFocus on invalid state!");
        } else {
            this.B.a(autoFocusCallback);
        }
    }

    public synchronized void cancelAutoFocus() {
        if (this.x.get() != j || this.B == null) {
            Log.e(a, "Call cancelAutoFocus on invalid state!");
        } else {
            this.B.h();
        }
    }

    public synchronized void release() {
        stop();
        this.K = null;
        this.mImgTexSrcPin.disconnect(c);
        this.mImgBufSrcPin.disconnect(c);
        this.M.removeListener(this.T);
        synchronized (this.y) {
            if (this.A != null) {
                this.A.release();
                this.A = null;
            }
        }
        if (this.E != null) {
            this.F.sendEmptyMessage(l);
            try {
                this.E.join();
                this.E = null;
            } catch (InterruptedException e) {
                Log.d(a, "CameraSetUpThread Interrupted!");
                this.E = null;
            } catch (Throwable th) {
                this.E = null;
            }
        }
        if (this.D != null) {
            this.D.removeCallbacksAndMessages(null);
        }
    }

    public int getCameraFacing() {
        return this.q;
    }

    public int getState() {
        return this.x.get();
    }

    public b getTargetPreviewSize() {
        return this.t;
    }

    public float getTargetPreviewFps() {
        return this.u;
    }

    public void startRecord() {
        this.Q = c;
    }

    public void stopRecord() {
        this.Q = d;
    }

    public boolean isRecording() {
        return this.Q;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.P = c;
        this.M.requestRender();
    }

    private void a() {
        this.E = new HandlerThread("camera_setup_thread", 5);
        this.E.start();
        this.F = new AnonymousClass1(this, this.E.getLooper());
    }

    private int b() {
        if (this.q == i && a(this.q) < 0) {
            this.q = FACING_BACK;
        }
        int a = a(this.q);
        try {
            synchronized (this.y) {
                this.B = c.a(this.n, a);
                this.B.a(this.U);
                this.C = this.B.i();
                d();
                this.D.sendEmptyMessage(i);
                e();
            }
            this.N = d;
            this.O = d;
            return FACING_BACK;
        } catch (Exception e) {
            Log.e(a, "[setupCamera]-------setup failed");
            return CAMERA_ERROR_START_FAILED;
        }
    }

    private void c() {
        if (!TextUtils.isEmpty(this.w) && this.w.equals("auto")) {
            this.B.h();
        }
        synchronized (this.y) {
            this.B.g();
            this.B.a(null);
            this.B.a(null);
            com.ksyun.media.streamer.capture.camera.a.a().b();
            this.B = null;
        }
    }

    private int a(int i) {
        if (i == 0) {
            return com.ksyun.media.streamer.capture.camera.a.a().c();
        }
        return com.ksyun.media.streamer.capture.camera.a.a().d();
    }

    private void d() {
        if (this.C != null) {
            this.t = c.a(this.C, this.r);
            if (!this.B.b(this.C)) {
                Log.e(a, "setPreviewSize failed");
                this.C = this.B.i();
            }
            this.u = c.a(this.C, this.s, c);
            if (!this.B.b(this.C)) {
                Log.e(a, "setPreviewFps with fixed value failed, retry");
                this.u = c.a(this.C, this.s, d);
                this.B.b(this.C);
            }
            Log.d(a, "try to preview with: " + this.t.a + "x" + this.t.b + " " + this.u + "fps");
            try {
                this.w = c.a(this.C);
                if (!this.B.b(this.C)) {
                    Log.e(a, "setFocuseMode failed");
                    this.C = this.B.i();
                }
            } catch (Exception e) {
                Log.e(a, "setFocuseMode failed");
            }
            try {
                c.c(this.C);
                if (!this.B.b(this.C)) {
                    Log.e(a, "setVideoStabilization failed");
                    this.C = this.B.i();
                }
            } catch (Exception e2) {
                Log.e(a, "setVideoStabilization failed");
            }
            try {
                c.d(this.C);
                if (!this.B.b(this.C)) {
                    Log.e(a, "setAntibanding failed");
                    this.C = this.B.i();
                    this.B.b(this.C);
                }
            } catch (Exception e3) {
                Log.e(a, "setAntibanding failed");
            }
            int[] iArr = new int[j];
            this.C.getPreviewFpsRange(iArr);
            Log.d(a, "Preview with: \n" + this.C.getPreviewSize().width + "x" + this.C.getPreviewSize().height + " " + (((float) iArr[FACING_BACK]) / 1000.0f) + "-" + (((float) iArr[i]) / 1000.0f) + "fps\nFocusMode: " + this.C.getFocusMode() + "\nVideoStabilization: " + this.C.getVideoStabilization() + "\nAntibanding: " + this.C.getAntibanding());
        }
    }

    private void e() {
        f();
        this.B.a(this.V);
        int i = ((this.t.a * this.t.b) * k) / j;
        if (this.K == null || this.K.length != i) {
            this.K = new byte[i];
        }
        this.B.a(this.K);
        if (this.A != null) {
            this.B.b(this.A);
            this.B.f();
        }
        if (!TextUtils.isEmpty(this.w) && this.w.equals("auto")) {
            this.B.a(new AutoFocusCallback() {
                final /* synthetic */ CameraCapture a;

                {
                    this.a = r1;
                }

                public void onAutoFocus(boolean z, Camera camera) {
                    this.a.B.h();
                }
            });
        }
    }

    private void f() {
        this.B.a(c.a(this.v, a(this.q)));
    }
}
