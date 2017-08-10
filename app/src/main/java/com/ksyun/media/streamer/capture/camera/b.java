package com.ksyun.media.streamer.capture.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.OnZoomChangeListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import java.io.IOException;

/* compiled from: CameraManager */
public class b {
    private static final int A = 21;
    private static final int B = 22;
    private static final int C = 23;
    private static final String a = "CameraManager";
    private static b b = null;
    private static final int g = 1;
    private static final int h = 2;
    private static final int i = 3;
    private static final int j = 4;
    private static final int k = 5;
    private static final int l = 6;
    private static final int m = 7;
    private static final int n = 8;
    private static final int o = 9;
    private static final int p = 10;
    private static final int q = 11;
    private static final int r = 12;
    private static final int s = 13;
    private static final int t = 14;
    private static final int u = 15;
    private static final int v = 16;
    private static final int w = 17;
    private static final int x = 18;
    private static final int y = 19;
    private static final int z = 20;
    private Handler D;
    private b E;
    private Camera F;
    private ConditionVariable c;
    private Parameters d;
    private RuntimeException e;
    private IOException f;

    /* compiled from: CameraManager */
    private class a extends Handler {
        final /* synthetic */ b a;

        a(b bVar, Looper looper) {
            this.a = bVar;
            super(looper);
        }

        private void a(Object obj) {
            try {
                this.a.F.setPreviewTexture((SurfaceTexture) obj);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r5) {
            /*
            r4 = this;
            r3 = 0;
            if (r5 == 0) goto L_0x000b;
        L_0x0003:
            r0 = r4.a;
            r0 = r0.F;
            if (r0 != 0) goto L_0x0015;
        L_0x000b:
            r0 = r4.a;
            r0 = r0.c;
            r0.open();
        L_0x0014:
            return;
        L_0x0015:
            r0 = r5.what;	 Catch:{ RuntimeException -> 0x0035 }
            switch(r0) {
                case 1: goto L_0x0060;
                case 2: goto L_0x0076;
                case 3: goto L_0x008d;
                case 4: goto L_0x0097;
                case 5: goto L_0x00a1;
                case 6: goto L_0x00d9;
                case 7: goto L_0x00ef;
                case 8: goto L_0x00fa;
                case 9: goto L_0x0109;
                case 10: goto L_0x011a;
                case 11: goto L_0x0129;
                case 12: goto L_0x0134;
                case 13: goto L_0x0141;
                case 14: goto L_0x0150;
                case 15: goto L_0x015f;
                case 16: goto L_0x017c;
                case 17: goto L_0x018d;
                case 18: goto L_0x0056;
                case 19: goto L_0x00a8;
                case 20: goto L_0x01a6;
                case 21: goto L_0x00c4;
                case 22: goto L_0x00e4;
                case 23: goto L_0x00be;
                default: goto L_0x001a;
            };	 Catch:{ RuntimeException -> 0x0035 }
        L_0x001a:
            r0 = new java.lang.RuntimeException;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0035 }
            r1.<init>();	 Catch:{ RuntimeException -> 0x0035 }
            r2 = "Invalid CameraProxy message=";
            r1 = r1.append(r2);	 Catch:{ RuntimeException -> 0x0035 }
            r2 = r5.what;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r1.append(r2);	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r1.toString();	 Catch:{ RuntimeException -> 0x0035 }
            r0.<init>(r1);	 Catch:{ RuntimeException -> 0x0035 }
            throw r0;	 Catch:{ RuntimeException -> 0x0035 }
        L_0x0035:
            r0 = move-exception;
            r0 = r5.what;
            r1 = 1;
            if (r0 == r1) goto L_0x0056;
        L_0x003b:
            r0 = r4.a;
            r0 = r0.F;
            if (r0 == 0) goto L_0x0056;
        L_0x0043:
            r0 = r4.a;	 Catch:{ Exception -> 0x01b5 }
            r0 = r0.F;	 Catch:{ Exception -> 0x01b5 }
            r0.release();	 Catch:{ Exception -> 0x01b5 }
        L_0x004c:
            r0 = r4.a;
            r0.F = r3;
            r0 = r4.a;
            r0.E = r3;
        L_0x0056:
            r0 = r4.a;
            r0 = r0.c;
            r0.open();
            goto L_0x0014;
        L_0x0060:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.release();	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = 0;
            r0.F = r1;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = 0;
            r0.E = r1;	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0076:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = 0;
            r0.f = r1;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r4.a;	 Catch:{ IOException -> 0x0086 }
            r0 = r0.F;	 Catch:{ IOException -> 0x0086 }
            r0.reconnect();	 Catch:{ IOException -> 0x0086 }
            goto L_0x0056;
        L_0x0086:
            r0 = move-exception;
            r1 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1.f = r0;	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x008d:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.unlock();	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0097:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.lock();	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x00a1:
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r4.a(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0014;
        L_0x00a8:
            r0 = r4.a;	 Catch:{ IOException -> 0x00b7 }
            r1 = r0.F;	 Catch:{ IOException -> 0x00b7 }
            r0 = r5.obj;	 Catch:{ IOException -> 0x00b7 }
            r0 = (android.view.SurfaceHolder) r0;	 Catch:{ IOException -> 0x00b7 }
            r1.setPreviewDisplay(r0);	 Catch:{ IOException -> 0x00b7 }
            goto L_0x0014;
        L_0x00b7:
            r0 = move-exception;
            r1 = new java.lang.RuntimeException;	 Catch:{ RuntimeException -> 0x0035 }
            r1.<init>(r0);	 Catch:{ RuntimeException -> 0x0035 }
            throw r1;	 Catch:{ RuntimeException -> 0x0035 }
        L_0x00be:
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r4.a(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x00c4:
            r0 = r4.a;	 Catch:{ IOException -> 0x00d2 }
            r1 = r0.F;	 Catch:{ IOException -> 0x00d2 }
            r0 = r5.obj;	 Catch:{ IOException -> 0x00d2 }
            r0 = (android.view.SurfaceHolder) r0;	 Catch:{ IOException -> 0x00d2 }
            r1.setPreviewDisplay(r0);	 Catch:{ IOException -> 0x00d2 }
            goto L_0x0056;
        L_0x00d2:
            r0 = move-exception;
            r1 = new java.lang.RuntimeException;	 Catch:{ RuntimeException -> 0x0035 }
            r1.<init>(r0);	 Catch:{ RuntimeException -> 0x0035 }
            throw r1;	 Catch:{ RuntimeException -> 0x0035 }
        L_0x00d9:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.startPreview();	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0014;
        L_0x00e4:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.startPreview();	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x00ef:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.stopPreview();	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x00fa:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (android.hardware.Camera.PreviewCallback) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r1.setPreviewCallbackWithBuffer(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0109:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (byte[]) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (byte[]) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r1.addCallbackBuffer(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x011a:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (android.hardware.Camera.AutoFocusCallback) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r1.autoFocus(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0129:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0.cancelAutoFocus();	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0134:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r5.arg1;	 Catch:{ RuntimeException -> 0x0035 }
            r0.setDisplayOrientation(r1);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0141:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (android.hardware.Camera.OnZoomChangeListener) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r1.setZoomChangeListener(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x0150:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (android.hardware.Camera.ErrorCallback) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r1.setErrorCallback(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x015f:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = 0;
            r0.e = r1;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0174 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0174 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0174 }
            r0 = (android.hardware.Camera.Parameters) r0;	 Catch:{ RuntimeException -> 0x0174 }
            r1.setParameters(r0);	 Catch:{ RuntimeException -> 0x0174 }
            goto L_0x0056;
        L_0x0174:
            r0 = move-exception;
            r1 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1.e = r0;	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x017c:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r1.F;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r1.getParameters();	 Catch:{ RuntimeException -> 0x0035 }
            r0.d = r1;	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x018d:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x019c }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x019c }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x019c }
            r0 = (android.hardware.Camera.Parameters) r0;	 Catch:{ RuntimeException -> 0x019c }
            r1.setParameters(r0);	 Catch:{ RuntimeException -> 0x019c }
            goto L_0x0014;
        L_0x019c:
            r0 = move-exception;
            r0 = "CameraManager";
            r1 = "Camera set parameters failed";
            android.util.Log.e(r0, r1);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0014;
        L_0x01a6:
            r0 = r4.a;	 Catch:{ RuntimeException -> 0x0035 }
            r1 = r0.F;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = r5.obj;	 Catch:{ RuntimeException -> 0x0035 }
            r0 = (android.hardware.Camera.PreviewCallback) r0;	 Catch:{ RuntimeException -> 0x0035 }
            r1.setPreviewCallback(r0);	 Catch:{ RuntimeException -> 0x0035 }
            goto L_0x0056;
        L_0x01b5:
            r0 = move-exception;
            r0 = "CameraManager";
            r1 = "Fail to release the camera.";
            android.util.Log.e(r0, r1);
            goto L_0x004c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.capture.camera.b.a.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: CameraManager */
    public class b {
        final /* synthetic */ b a;

        /* compiled from: CameraManager */
        /* renamed from: com.ksyun.media.streamer.capture.camera.b.b.1 */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ ShutterCallback a;
            final /* synthetic */ PictureCallback b;
            final /* synthetic */ PictureCallback c;
            final /* synthetic */ PictureCallback d;
            final /* synthetic */ b e;

            AnonymousClass1(b bVar, ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2, PictureCallback pictureCallback3) {
                this.e = bVar;
                this.a = shutterCallback;
                this.b = pictureCallback;
                this.c = pictureCallback2;
                this.d = pictureCallback3;
            }

            public void run() {
                this.e.a.F.takePicture(this.a, this.b, this.c, this.d);
                this.e.a.c.open();
            }
        }

        public b(b bVar) {
            this.a = bVar;
        }

        public void a() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.g);
            this.a.c.block();
        }

        public void b() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.h);
            this.a.c.block();
            if (this.a.f != null) {
                throw this.a.f;
            }
        }

        public void c() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.i);
            this.a.c.block();
        }

        public void d() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.j);
            this.a.c.block();
        }

        public void a(SurfaceTexture surfaceTexture) {
            this.a.D.obtainMessage(b.k, surfaceTexture).sendToTarget();
        }

        public void b(SurfaceTexture surfaceTexture) {
            this.a.c.close();
            this.a.D.obtainMessage(b.C, surfaceTexture).sendToTarget();
            this.a.c.block();
        }

        public void a(SurfaceHolder surfaceHolder) {
            this.a.D.obtainMessage(b.y, surfaceHolder).sendToTarget();
        }

        public void b(SurfaceHolder surfaceHolder) {
            this.a.c.close();
            this.a.D.obtainMessage(b.A, surfaceHolder).sendToTarget();
            this.a.c.block();
        }

        public void e() {
            this.a.D.sendEmptyMessage(b.l);
        }

        public void f() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.B);
            this.a.c.block();
        }

        public void g() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.m);
            this.a.c.block();
        }

        public void a(PreviewCallback previewCallback) {
            this.a.c.close();
            this.a.D.obtainMessage(b.n, previewCallback).sendToTarget();
            this.a.c.block();
        }

        public void a(byte[] bArr) {
            this.a.c.close();
            this.a.D.obtainMessage(b.o, bArr).sendToTarget();
            this.a.c.block();
        }

        public void a(AutoFocusCallback autoFocusCallback) {
            this.a.c.close();
            this.a.D.obtainMessage(b.p, autoFocusCallback).sendToTarget();
            this.a.c.block();
        }

        public void h() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.q);
            this.a.c.block();
        }

        public void a(ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2, PictureCallback pictureCallback3) {
            this.a.c.close();
            this.a.D.post(new AnonymousClass1(this, shutterCallback, pictureCallback, pictureCallback2, pictureCallback3));
            this.a.c.block();
        }

        public void a(int i) {
            this.a.c.close();
            this.a.D.obtainMessage(b.r, i, 0).sendToTarget();
            this.a.c.block();
        }

        public void a(OnZoomChangeListener onZoomChangeListener) {
            this.a.c.close();
            this.a.D.obtainMessage(b.s, onZoomChangeListener).sendToTarget();
            this.a.c.block();
        }

        public void a(ErrorCallback errorCallback) {
            this.a.c.close();
            this.a.D.obtainMessage(b.t, errorCallback).sendToTarget();
            this.a.c.block();
        }

        public void a(Parameters parameters) {
            this.a.c.close();
            this.a.D.obtainMessage(b.u, parameters).sendToTarget();
            this.a.c.block();
            if (this.a.e != null) {
                throw this.a.e;
            }
        }

        public boolean b(Parameters parameters) {
            try {
                a(parameters);
                return true;
            } catch (RuntimeException e) {
                Log.e(b.a, "Camera set parameters failed");
                return false;
            }
        }

        public void c(Parameters parameters) {
            this.a.D.removeMessages(b.w);
            this.a.D.obtainMessage(b.w, parameters).sendToTarget();
        }

        public Parameters i() {
            this.a.c.close();
            this.a.D.sendEmptyMessage(b.v);
            this.a.c.block();
            Parameters f = this.a.d;
            this.a.d = null;
            return f;
        }
    }

    static {
        b = new b();
    }

    public static b a() {
        return b;
    }

    private b() {
        this.c = new ConditionVariable();
        HandlerThread handlerThread = new HandlerThread("Camera Handler Thread");
        handlerThread.start();
        this.D = new a(this, handlerThread.getLooper());
    }

    b a(int i) {
        this.F = Camera.open(i);
        if (this.F == null) {
            return null;
        }
        this.E = new b(this);
        return this.E;
    }
}
