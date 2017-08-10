package com.ksyun.media.streamer.capture.camera;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.capture.camera.b.b;
import java.util.List;

/* compiled from: CameraUtil */
public class c {
    private static final String a = "CameraUtil";

    private static boolean a(String str, List list) {
        if (list == null || list.indexOf(str) < 0) {
            return false;
        }
        return true;
    }

    public static String a(Parameters parameters) {
        if (parameters == null) {
            return null;
        }
        List supportedFocusModes = parameters.getSupportedFocusModes();
        String focusMode = parameters.getFocusMode();
        if (a("continuous-video", supportedFocusModes)) {
            focusMode = "continuous-video";
        } else if (a("continuous-picture", supportedFocusModes)) {
            focusMode = "continuous-picture";
        } else if (a("auto", supportedFocusModes)) {
            focusMode = "auto";
        }
        parameters.setFocusMode(focusMode);
        return focusMode;
    }

    public static void b(Parameters parameters) {
        if (parameters != null) {
            if (a("auto", parameters.getSupportedWhiteBalance())) {
                parameters.setWhiteBalance("auto");
            } else {
                Log.e(a, "Auto white balance not found!");
            }
        }
    }

    public static void c(Parameters parameters) {
        if (parameters != null && parameters.isVideoStabilizationSupported()) {
            parameters.setVideoStabilization(true);
        }
    }

    public static void d(Parameters parameters) {
        if (parameters != null) {
            if (a("auto", parameters.getSupportedAntibanding())) {
                parameters.setAntibanding("auto");
            }
        }
    }

    private static void a(Context context) {
        if (((DevicePolicyManager) context.getSystemService("device_policy")).getCameraDisabled(null)) {
            throw new CameraDisabledException();
        }
    }

    public static b a(Context context, int i) {
        a(context);
        try {
            return a.a().a(i);
        } catch (Throwable e) {
            if ("eng".equals(Build.TYPE)) {
                throw new RuntimeException("openCamera failed", e);
            }
            throw e;
        }
    }

    public static float a(Parameters parameters, float f, boolean z) {
        if (parameters == null) {
            return 0.0f;
        }
        int i = (int) (f * 1000.0f);
        int[] iArr = new int[2];
        int i2 = Integer.MAX_VALUE;
        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        for (int[] iArr2 : supportedPreviewFpsRange) {
            Log.d(a, iArr2[0] + "-" + iArr2[1]);
        }
        for (int[] iArr22 : supportedPreviewFpsRange) {
            if (i < iArr22[0] || i > iArr22[1]) {
                for (int i3 : iArr22) {
                    int abs = Math.abs(i3 - i);
                    if (abs <= i2) {
                        if (z) {
                            iArr[0] = i3;
                            iArr[1] = i3;
                        } else {
                            iArr[0] = iArr22[0];
                            iArr[1] = iArr22[1];
                        }
                        i2 = abs;
                    }
                }
            } else {
                if (z) {
                    iArr[0] = i;
                    iArr[1] = i;
                } else {
                    iArr[0] = iArr22[0];
                    iArr[1] = iArr22[1];
                }
                parameters.setPreviewFpsRange(iArr[0], iArr[1]);
                return ((float) iArr[1]) / 1000.0f;
            }
        }
        parameters.setPreviewFpsRange(iArr[0], iArr[1]);
        return ((float) iArr[1]) / 1000.0f;
    }

    public static CameraCapture.b a(Parameters parameters, CameraCapture.b bVar) {
        if (parameters == null || bVar == null) {
            return null;
        }
        int i = Integer.MAX_VALUE;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = Integer.MAX_VALUE;
        for (Size size : parameters.getSupportedPreviewSizes()) {
            int i7;
            Log.d(a, "==== Camera Support: " + size.width + "x" + size.height);
            int i8 = ((size.width - bVar.a) * (size.width - bVar.a)) + ((size.height - bVar.b) * (size.height - bVar.b));
            if (i8 < i6) {
                i3 = size.width;
                i2 = size.height;
                i6 = i8;
            }
            if (size.width < bVar.a || size.height < bVar.b || i8 >= i) {
                i7 = i;
                i = i4;
                i4 = i5;
            } else {
                i5 = size.width;
                i4 = size.height;
                i7 = i8;
                i = i4;
                i4 = i5;
            }
            i5 = i4;
            i4 = i;
            i = i7;
        }
        if (i5 == 0 || i4 == 0) {
            i4 = i2;
            i5 = i3;
        }
        parameters.setPreviewSize(i5, i4);
        return new CameraCapture.b(i5, i4);
    }

    public static int a(int i, int i2) {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(i2, cameraInfo);
        if (cameraInfo.facing == 1) {
            return (360 - ((cameraInfo.orientation + i) % 360)) % 360;
        }
        return ((cameraInfo.orientation - i) + 360) % 360;
    }
}
