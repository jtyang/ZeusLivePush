package com.ksyun.media.streamer.capture.camera;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.util.Log;
import com.ksyun.media.streamer.capture.camera.b.b;

/* compiled from: CameraHolder */
public class a {
    private static final String a = "CameraHolder";
    private static a i;
    private b b;
    private final int c;
    private int d;
    private int e;
    private int f;
    private final CameraInfo[] g;
    private Parameters h;

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (i == null) {
                i = new a();
            }
            aVar = i;
        }
        return aVar;
    }

    private a() {
        int i = 0;
        this.d = -1;
        this.e = -1;
        this.f = -1;
        if (Camera.getNumberOfCameras() < 0) {
            this.c = 0;
        } else {
            this.c = Camera.getNumberOfCameras();
        }
        this.g = new CameraInfo[this.c];
        for (int i2 = 0; i2 < this.c; i2++) {
            this.g[i2] = new CameraInfo();
            try {
                Camera.getCameraInfo(i2, this.g[i2]);
            } catch (Exception e) {
                Log.w(a, "Failed to getCameraInfo");
            }
        }
        while (i < this.c) {
            if (this.e == -1 && this.g[i].facing == 0) {
                this.e = i;
            } else if (this.f == -1 && this.g[i].facing == 1) {
                this.f = i;
            }
            i++;
        }
    }

    public synchronized com.ksyun.media.streamer.capture.camera.b.b a(int r4) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.ksyun.media.streamer.capture.camera.a.a(int):com.ksyun.media.streamer.capture.camera.b$b. bs: [B:9:0x0018, B:26:0x0063]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:57)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        if (r0 == 0) goto L_0x0014;	 Catch:{ all -> 0x0052 }
    L_0x0005:
        r0 = r3.d;	 Catch:{ all -> 0x0052 }
        if (r0 == r4) goto L_0x0014;	 Catch:{ all -> 0x0052 }
    L_0x0009:
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        r0.a();	 Catch:{ all -> 0x0052 }
        r0 = 0;	 Catch:{ all -> 0x0052 }
        r3.b = r0;	 Catch:{ all -> 0x0052 }
        r0 = -1;	 Catch:{ all -> 0x0052 }
        r3.d = r0;	 Catch:{ all -> 0x0052 }
    L_0x0014:
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        if (r0 != 0) goto L_0x0063;
    L_0x0018:
        r0 = "CameraHolder";	 Catch:{ RuntimeException -> 0x0055 }
        r1 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0055 }
        r1.<init>();	 Catch:{ RuntimeException -> 0x0055 }
        r2 = "open camera ";	 Catch:{ RuntimeException -> 0x0055 }
        r1 = r1.append(r2);	 Catch:{ RuntimeException -> 0x0055 }
        r1 = r1.append(r4);	 Catch:{ RuntimeException -> 0x0055 }
        r1 = r1.toString();	 Catch:{ RuntimeException -> 0x0055 }
        android.util.Log.v(r0, r1);	 Catch:{ RuntimeException -> 0x0055 }
        r0 = com.ksyun.media.streamer.capture.camera.b.a();	 Catch:{ RuntimeException -> 0x0055 }
        r0 = r0.a(r4);	 Catch:{ RuntimeException -> 0x0055 }
        r3.b = r0;	 Catch:{ RuntimeException -> 0x0055 }
        r3.d = r4;	 Catch:{ RuntimeException -> 0x0055 }
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        if (r0 == 0) goto L_0x0048;	 Catch:{ all -> 0x0052 }
    L_0x0040:
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        r0 = r0.i();	 Catch:{ all -> 0x0052 }
        r3.h = r0;	 Catch:{ all -> 0x0052 }
    L_0x0048:
        r0 = r3.h;	 Catch:{ all -> 0x0052 }
        if (r0 != 0) goto L_0x006f;	 Catch:{ all -> 0x0052 }
    L_0x004c:
        r0 = new com.ksyun.media.streamer.capture.camera.CameraDisabledException;	 Catch:{ all -> 0x0052 }
        r0.<init>();	 Catch:{ all -> 0x0052 }
        throw r0;	 Catch:{ all -> 0x0052 }
    L_0x0052:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x0055:
        r0 = move-exception;
        r1 = "CameraHolder";	 Catch:{ all -> 0x0052 }
        r2 = "fail to connect Camera";	 Catch:{ all -> 0x0052 }
        android.util.Log.e(r1, r2, r0);	 Catch:{ all -> 0x0052 }
        r1 = new com.ksyun.media.streamer.capture.camera.CameraHardwareException;	 Catch:{ all -> 0x0052 }
        r1.<init>(r0);	 Catch:{ all -> 0x0052 }
        throw r1;	 Catch:{ all -> 0x0052 }
    L_0x0063:
        r0 = r3.b;	 Catch:{ IOException -> 0x0073 }
        r0.b();	 Catch:{ IOException -> 0x0073 }
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        r1 = r3.h;	 Catch:{ all -> 0x0052 }
        r0.a(r1);	 Catch:{ all -> 0x0052 }
    L_0x006f:
        r0 = r3.b;	 Catch:{ all -> 0x0052 }
        monitor-exit(r3);
        return r0;
    L_0x0073:
        r0 = move-exception;
        r1 = "CameraHolder";	 Catch:{ all -> 0x0052 }
        r2 = "reconnect failed.";	 Catch:{ all -> 0x0052 }
        android.util.Log.e(r1, r2);	 Catch:{ all -> 0x0052 }
        r1 = new com.ksyun.media.streamer.capture.camera.CameraHardwareException;	 Catch:{ all -> 0x0052 }
        r1.<init>(r0);	 Catch:{ all -> 0x0052 }
        throw r1;	 Catch:{ all -> 0x0052 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.streamer.capture.camera.a.a(int):com.ksyun.media.streamer.capture.camera.b$b");
    }

    public synchronized void b() {
        if (this.b != null) {
            this.b.a();
            this.b = null;
            this.h = null;
            this.d = -1;
        }
    }

    public int c() {
        return this.e;
    }

    public int d() {
        return this.f;
    }
}
