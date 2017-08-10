package com.ksyun.media.player;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.ksyun.media.player.detector.KSYNetworkTrackerConfig;
import com.ksyun.media.player.detector.KSYTrackerRouterInfo;
import com.ksyun.media.streamer.util.gles.GLRender;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class KSYNetworkDetector {
    public static final int KSY_NETWORK_DETECTION_EVENT_ERROR = 3;
    public static final int KSY_NETWORK_DETECTION_EVENT_FINISH = 2;
    public static final int KSY_NETWORK_DETECTION_EVENT_ONCE_DONE = 1;
    public static final int KSY_NETWORK_DETECTOR_TYPE_TRACKER = 1;
    public static final int KSY_NETWORK_TRACKER_TYPE_DESTINATION = 1;
    private static final int a = 0;
    private static final int b = 0;
    private static KSYNetworkDetector d;
    private final String c;
    private a e;
    private KSYNetworkTrackerConfig f;
    private OnNetworkDetectionEventListener g;

    public enum KSYDetectorPacketType {
        KSY_DETECTOR_PACKET_TYPE_ICMP,
        KSY_DETECTOR_PACKET_TYPE_UDP,
        KSY_DETECTOR_PACKET_TYPE_TCP
    }

    public interface OnNetworkDetectionEventListener {
        void onNetworkDetectInfo(int i, int i2, double d);
    }

    private class a extends Handler {
        final /* synthetic */ KSYNetworkDetector a;
        private WeakReference<KSYNetworkDetector> b;

        public a(KSYNetworkDetector kSYNetworkDetector, KSYNetworkDetector kSYNetworkDetector2, Looper looper) {
            this.a = kSYNetworkDetector;
            super(looper);
            this.b = new WeakReference(kSYNetworkDetector2);
        }

        public void handleMessage(Message message) {
            double d = 0.0d;
            if (message.obj != null) {
                d = Double.parseDouble(message.obj.toString());
            }
            if (this.a.g != null) {
                this.a.g.onNetworkDetectInfo(message.what, message.arg1, d);
            }
        }
    }

    private native void _destroy();

    private native int _getStatus();

    private native Bundle _getTrackerConfig();

    private native Bundle _getTrackerDetectResult();

    private native int _open(int i, Object obj);

    private native void _start(String str, int i, Object obj);

    private native void _stop();

    public static KSYNetworkDetector getInstance() {
        if (d == null) {
            synchronized (KSYNetworkDetector.class) {
                if (d == null) {
                    d = new KSYNetworkDetector();
                }
            }
        }
        return d;
    }

    private KSYNetworkDetector() {
        this.c = "router_number";
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.e = new a(this, this, myLooper);
        } else {
            myLooper = Looper.getMainLooper();
            if (myLooper != null) {
                this.e = new a(this, this, myLooper);
            } else {
                this.e = null;
            }
        }
        a();
    }

    public int open(int i) {
        int i2;
        switch (i) {
            case GLRender.VIEW_TYPE_NONE /*0*/:
                i2 = 0;
                break;
            case KSY_NETWORK_TRACKER_TYPE_DESTINATION /*1*/:
                i2 = KSY_NETWORK_TRACKER_TYPE_DESTINATION;
                break;
            default:
                return -1;
        }
        return _open(i2, new WeakReference(this));
    }

    public int start(String str, KSYNetworkTrackerConfig kSYNetworkTrackerConfig) {
        Object toBundle;
        if (kSYNetworkTrackerConfig != null) {
            toBundle = kSYNetworkTrackerConfig.toBundle();
        } else {
            this.f = getTrackerConfig();
            toBundle = kSYNetworkTrackerConfig.toBundle();
        }
        _start(str, 0, toBundle);
        return 0;
    }

    public void stop() {
        _stop();
    }

    public void destroy() {
        _destroy();
    }

    public KSYNetworkTrackerConfig getTrackerConfig() {
        if (this.f != null) {
            return this.f;
        }
        Bundle _getTrackerConfig = _getTrackerConfig();
        if (_getTrackerConfig == null) {
            return null;
        }
        this.f = new KSYNetworkTrackerConfig();
        this.f.parse(_getTrackerConfig);
        return this.f;
    }

    public ArrayList<KSYTrackerRouterInfo> getTrackerDetectResult() {
        ArrayList<KSYTrackerRouterInfo> arrayList = null;
        Bundle _getTrackerDetectResult = _getTrackerDetectResult();
        if (_getTrackerDetectResult != null) {
            int i = _getTrackerDetectResult.getInt("router_number");
            if (i > 0) {
                arrayList = new ArrayList();
                for (int i2 = 0; i2 < i; i2 += KSY_NETWORK_TRACKER_TYPE_DESTINATION) {
                    String valueOf = String.valueOf(i2);
                    KSYTrackerRouterInfo kSYTrackerRouterInfo = new KSYTrackerRouterInfo();
                    if (kSYTrackerRouterInfo.parse(_getTrackerDetectResult.getBundle(valueOf)) >= 0) {
                        arrayList.add(i2, kSYTrackerRouterInfo);
                    }
                }
            }
        }
        return arrayList;
    }

    private static void postEventFromNative(Object obj, int i, int i2, double d, Object obj2) {
        if (obj != null) {
            KSYNetworkDetector kSYNetworkDetector = (KSYNetworkDetector) ((WeakReference) obj).get();
            if (kSYNetworkDetector != null) {
                Double valueOf = Double.valueOf(d);
                if (kSYNetworkDetector.e != null) {
                    kSYNetworkDetector.e.obtainMessage(i, i2, 0, valueOf).sendToTarget();
                }
            }
        }
    }

    public void setOnNetworkDetectionEventListener(OnNetworkDetectionEventListener onNetworkDetectionEventListener) {
        this.g = onNetworkDetectionEventListener;
    }

    private void a() {
        if (TextUtils.isEmpty(KSYLibraryManager.getLocalLibraryPath())) {
            if (!e.a("ksylive")) {
                e.a("ksyplayer");
            }
        } else if (!e.a(KSYLibraryManager.getLocalLibraryPath(), "ksylive")) {
            e.a(KSYLibraryManager.getLocalLibraryPath(), "ksyplayer");
        }
    }
}
