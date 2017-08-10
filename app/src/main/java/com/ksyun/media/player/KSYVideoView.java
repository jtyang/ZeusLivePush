package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.ksyun.media.player.IMediaController.MediaPlayerControl;
import com.ksyun.media.player.IMediaPlayer.OnBufferingUpdateListener;
import com.ksyun.media.player.IMediaPlayer.OnCompletionListener;
import com.ksyun.media.player.IMediaPlayer.OnErrorListener;
import com.ksyun.media.player.IMediaPlayer.OnInfoListener;
import com.ksyun.media.player.IMediaPlayer.OnLogEventListener;
import com.ksyun.media.player.IMediaPlayer.OnMessageListener;
import com.ksyun.media.player.IMediaPlayer.OnPreparedListener;
import com.ksyun.media.player.IMediaPlayer.OnSeekCompleteListener;
import com.ksyun.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import com.ksyun.media.player.KSYMediaPlayer.Builder;
import com.ksyun.media.player.KSYMediaPlayer.KSYDecodeMode;
import com.ksyun.media.player.KSYMediaPlayer.KSYDeinterlaceMode;
import com.ksyun.media.player.KSYMediaPlayer.KSYReloadMode;
import com.ksyun.media.player.KSYMediaPlayer.OnAudioPCMListener;
import com.ksyun.media.player.KSYMediaPlayer.OnVideoRawDataListener;
import com.ksyun.media.player.misc.KSYQosInfo;
import com.ksyun.media.player.misc.KSYTrackInfo;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class KSYVideoView extends FrameLayout implements Callback, MediaPlayerControl {
    private static final int C = -1;
    private static final int D = 0;
    private static final int E = 1;
    private static final int F = 2;
    private static final int G = 3;
    private static final int H = 4;
    private static final int I = 5;
    private static final int J = 6;
    private static final int K = 7;
    protected static final int c = 8;
    private a A;
    private SurfaceHolder B;
    private OnCompletionListener L;
    private OnPreparedListener M;
    private OnErrorListener N;
    private OnSeekCompleteListener O;
    private OnInfoListener P;
    private OnBufferingUpdateListener Q;
    private OnVideoSizeChangedListener R;
    private OnLogEventListener S;
    private OnMessageListener T;
    protected MediaInfo a;
    protected int b;
    OnVideoSizeChangedListener d;
    OnPreparedListener e;
    protected final OnCompletionListener f;
    protected final OnErrorListener g;
    protected final OnBufferingUpdateListener h;
    protected final OnInfoListener i;
    protected final OnSeekCompleteListener j;
    protected final OnLogEventListener k;
    protected final OnMessageListener l;
    private String m;
    public int mCurrentState;
    private IMediaController n;
    private KSYMediaPlayer o;
    private int p;
    private int q;
    private int r;
    private int s;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private int y;
    private int z;

    class a extends SurfaceView {
        final /* synthetic */ KSYVideoView a;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;

        public a(KSYVideoView kSYVideoView, Context context) {
            this.a = kSYVideoView;
            super(context);
            this.h = KSYVideoView.D;
            this.i = KSYVideoView.C;
        }

        public a(KSYVideoView kSYVideoView, Context context, AttributeSet attributeSet) {
            this(kSYVideoView, context, attributeSet, KSYVideoView.D);
        }

        public a(KSYVideoView kSYVideoView, Context context, AttributeSet attributeSet, int i) {
            this.a = kSYVideoView;
            super(context, attributeSet, i);
            this.h = KSYVideoView.D;
            this.i = KSYVideoView.C;
        }

        private void c(int i, int i2) {
            float f = 1.0f;
            if (this.b == 0 || this.c == 0) {
                this.f = MeasureSpec.getSize(i);
                this.g = MeasureSpec.getSize(i2);
                return;
            }
            float f2;
            float f3;
            int i3 = this.b;
            int i4 = this.c;
            int size = MeasureSpec.getSize(i);
            int size2 = MeasureSpec.getSize(i2);
            if (this.d > 0 && this.e > 0) {
                i3 = (i3 * this.d) / this.e;
            }
            float f4 = ((float) i3) / ((float) size);
            float f5 = ((float) i4) / ((float) size2);
            if ((this.h / 90) % KSYVideoView.F != 0) {
                i3 = this.b;
                i4 = this.c;
                if (this.d > 0 && this.e > 0) {
                    i3 = (i3 * this.d) / this.e;
                }
            } else {
                int i5 = i4;
                i4 = i3;
                i3 = i5;
            }
            switch (this.i) {
                case KSYVideoView.D /*0*/:
                    if ((this.h / 90) % KSYVideoView.F == 0) {
                        f2 = 1.0f;
                        f3 = 1.0f;
                        break;
                    }
                    f3 = ((float) size2) / ((float) size);
                    f = ((float) size) / ((float) size2);
                    f2 = 1.0f;
                    break;
                case KSYVideoView.E /*1*/:
                    f2 = Math.min(((float) size) / ((float) i4), ((float) size2) / ((float) i3));
                    f = f5;
                    f3 = f4;
                    break;
                case KSYVideoView.F /*2*/:
                    f2 = Math.max(((float) size) / ((float) i4), ((float) size2) / ((float) i3));
                    f = f5;
                    f3 = f4;
                    break;
                default:
                    f2 = 1.0f;
                    f3 = f4;
                    f = f5;
                    break;
            }
            if ((this.h / 90) % KSYVideoView.F != 0) {
                this.g = (int) (f3 * (((float) size) * f2));
                this.f = (int) ((f2 * ((float) size2)) * f);
                return;
            }
            this.f = (int) (f3 * (((float) size) * f2));
            this.g = (int) ((f2 * ((float) size2)) * f);
        }

        protected void onMeasure(int i, int i2) {
            c(i, i2);
            if (this.f <= 0 || this.g <= 0) {
                super.onMeasure(i, i2);
            } else {
                setMeasuredDimension(this.f, this.g);
            }
        }

        public void a(int i, int i2) {
            if (!(this.b == i && this.c == i2)) {
                this.b = i;
                this.c = i2;
            }
            getHolder().setFixedSize(KSYVideoView.D, KSYVideoView.D);
        }

        public void b(int i, int i2) {
            this.d = i;
            this.e = i2;
        }

        public int a() {
            return this.f;
        }

        public int b() {
            return this.g;
        }

        public void a(int i) {
            this.i = i;
            requestLayout();
        }

        public boolean b(int i) {
            this.h = i;
            requestLayout();
            return true;
        }

        public void c() {
            this.b = KSYVideoView.D;
            this.c = KSYVideoView.D;
            this.d = KSYVideoView.D;
            this.e = KSYVideoView.D;
            this.f = KSYVideoView.D;
            this.g = KSYVideoView.D;
            this.h = KSYVideoView.D;
            this.i = KSYVideoView.C;
        }
    }

    public KSYVideoView(Context context) {
        super(context);
        this.m = "KSYVideoView";
        this.t = true;
        this.x = false;
        this.y = C;
        this.z = D;
        this.mCurrentState = D;
        this.d = new OnVideoSizeChangedListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
                this.a.p = iMediaPlayer.getVideoWidth();
                this.a.q = iMediaPlayer.getVideoHeight();
                this.a.r = i3;
                this.a.s = i4;
                if (this.a.mCurrentState == KSYVideoView.G) {
                    this.a.A.a(this.a.p, this.a.q);
                    this.a.A.b(this.a.r, this.a.s);
                    this.a.requestLayout();
                }
                if (this.a.R != null) {
                    this.a.R.onVideoSizeChanged(iMediaPlayer, i, i2, i3, i4);
                }
            }
        };
        this.e = new OnPreparedListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onPrepared(IMediaPlayer iMediaPlayer) {
                this.a.p = iMediaPlayer.getVideoWidth();
                this.a.q = iMediaPlayer.getVideoHeight();
                if (this.a.p > 0 && this.a.q > 0) {
                    this.a.A.a(this.a.p, this.a.q);
                }
                if (this.a.x) {
                    this.a.z = KSYVideoView.D;
                    this.a.A.b(this.a.z);
                } else if (this.a.A != null) {
                    this.a.A.b(this.a.z);
                }
                if (this.a.M != null) {
                    this.a.M.onPrepared(iMediaPlayer);
                }
                this.a.mCurrentState = KSYVideoView.F;
                if (!(this.a.A == null || this.a.A.isShown())) {
                    this.a.A.setVisibility(KSYVideoView.D);
                }
                this.a.A.requestLayout();
                if (this.a.n != null) {
                    this.a.n.setEnabled(true);
                    if (this.a.t) {
                        this.a.n.onStart();
                    } else {
                        this.a.n.onPause();
                    }
                }
            }
        };
        this.f = new OnCompletionListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onCompletion(IMediaPlayer iMediaPlayer) {
                this.a.mCurrentState = KSYVideoView.c;
                if (this.a.L != null) {
                    this.a.L.onCompletion(iMediaPlayer);
                }
                if (this.a.n != null) {
                    this.a.n.hide();
                }
            }
        };
        this.g = new OnErrorListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
                if (this.a.n != null) {
                    this.a.n.hide();
                }
                if (this.a.N != null) {
                    this.a.mCurrentState = KSYVideoView.C;
                    if (this.a.N.onError(iMediaPlayer, i, i2)) {
                        return true;
                    }
                }
                return true;
            }
        };
        this.h = new OnBufferingUpdateListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                this.a.b = i;
                if (this.a.Q != null) {
                    this.a.Q.onBufferingUpdate(iMediaPlayer, i);
                }
            }
        };
        this.i = new OnInfoListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
                switch (i) {
                    case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED /*10001*/:
                        this.a.setRotateDegree(i2);
                        break;
                    case IMediaPlayer.MEDIA_INFO_HARDWARE_DECODE /*41000*/:
                        this.a.x = true;
                        break;
                    case IMediaPlayer.MEDIA_INFO_SOFTWARE_DECODE /*41001*/:
                        this.a.x = false;
                        break;
                    case IMediaPlayer.MEDIA_INFO_RELOADED /*50001*/:
                        this.a.b = KSYVideoView.D;
                        if (this.a.t && this.a.o != null) {
                            this.a.o.start();
                        }
                        if (this.a.A != null) {
                            this.a.A.setVisibility(KSYVideoView.D);
                        }
                        if (this.a.n != null) {
                            this.a.n.setEnabled(true);
                            if (this.a.t) {
                                this.a.n.onStart();
                            } else {
                                this.a.n.onPause();
                            }
                        }
                        if (this.a.t) {
                            this.a.mCurrentState = KSYVideoView.G;
                        } else {
                            this.a.mCurrentState = KSYVideoView.J;
                        }
                        this.a.setVideoScalingMode(this.a.y);
                        break;
                }
                if (this.a.P != null) {
                    this.a.P.onInfo(iMediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.j = new OnSeekCompleteListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (this.a.O != null) {
                    this.a.O.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.k = new OnLogEventListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onLogEvent(IMediaPlayer iMediaPlayer, String str) {
                if (this.a.S != null) {
                    this.a.S.onLogEvent(iMediaPlayer, str);
                }
            }
        };
        this.l = new OnMessageListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onMessage(IMediaPlayer iMediaPlayer, String str, String str2, double d) {
                if (this.a.T != null) {
                    this.a.T.onMessage(iMediaPlayer, str, str2, d);
                }
            }
        };
        a(context);
        b(context);
    }

    public KSYVideoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, D);
    }

    public KSYVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.m = "KSYVideoView";
        this.t = true;
        this.x = false;
        this.y = C;
        this.z = D;
        this.mCurrentState = D;
        this.d = new OnVideoSizeChangedListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
                this.a.p = iMediaPlayer.getVideoWidth();
                this.a.q = iMediaPlayer.getVideoHeight();
                this.a.r = i3;
                this.a.s = i4;
                if (this.a.mCurrentState == KSYVideoView.G) {
                    this.a.A.a(this.a.p, this.a.q);
                    this.a.A.b(this.a.r, this.a.s);
                    this.a.requestLayout();
                }
                if (this.a.R != null) {
                    this.a.R.onVideoSizeChanged(iMediaPlayer, i, i2, i3, i4);
                }
            }
        };
        this.e = new OnPreparedListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onPrepared(IMediaPlayer iMediaPlayer) {
                this.a.p = iMediaPlayer.getVideoWidth();
                this.a.q = iMediaPlayer.getVideoHeight();
                if (this.a.p > 0 && this.a.q > 0) {
                    this.a.A.a(this.a.p, this.a.q);
                }
                if (this.a.x) {
                    this.a.z = KSYVideoView.D;
                    this.a.A.b(this.a.z);
                } else if (this.a.A != null) {
                    this.a.A.b(this.a.z);
                }
                if (this.a.M != null) {
                    this.a.M.onPrepared(iMediaPlayer);
                }
                this.a.mCurrentState = KSYVideoView.F;
                if (!(this.a.A == null || this.a.A.isShown())) {
                    this.a.A.setVisibility(KSYVideoView.D);
                }
                this.a.A.requestLayout();
                if (this.a.n != null) {
                    this.a.n.setEnabled(true);
                    if (this.a.t) {
                        this.a.n.onStart();
                    } else {
                        this.a.n.onPause();
                    }
                }
            }
        };
        this.f = new OnCompletionListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onCompletion(IMediaPlayer iMediaPlayer) {
                this.a.mCurrentState = KSYVideoView.c;
                if (this.a.L != null) {
                    this.a.L.onCompletion(iMediaPlayer);
                }
                if (this.a.n != null) {
                    this.a.n.hide();
                }
            }
        };
        this.g = new OnErrorListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
                if (this.a.n != null) {
                    this.a.n.hide();
                }
                if (this.a.N != null) {
                    this.a.mCurrentState = KSYVideoView.C;
                    if (this.a.N.onError(iMediaPlayer, i, i2)) {
                        return true;
                    }
                }
                return true;
            }
        };
        this.h = new OnBufferingUpdateListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                this.a.b = i;
                if (this.a.Q != null) {
                    this.a.Q.onBufferingUpdate(iMediaPlayer, i);
                }
            }
        };
        this.i = new OnInfoListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
                switch (i) {
                    case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED /*10001*/:
                        this.a.setRotateDegree(i2);
                        break;
                    case IMediaPlayer.MEDIA_INFO_HARDWARE_DECODE /*41000*/:
                        this.a.x = true;
                        break;
                    case IMediaPlayer.MEDIA_INFO_SOFTWARE_DECODE /*41001*/:
                        this.a.x = false;
                        break;
                    case IMediaPlayer.MEDIA_INFO_RELOADED /*50001*/:
                        this.a.b = KSYVideoView.D;
                        if (this.a.t && this.a.o != null) {
                            this.a.o.start();
                        }
                        if (this.a.A != null) {
                            this.a.A.setVisibility(KSYVideoView.D);
                        }
                        if (this.a.n != null) {
                            this.a.n.setEnabled(true);
                            if (this.a.t) {
                                this.a.n.onStart();
                            } else {
                                this.a.n.onPause();
                            }
                        }
                        if (this.a.t) {
                            this.a.mCurrentState = KSYVideoView.G;
                        } else {
                            this.a.mCurrentState = KSYVideoView.J;
                        }
                        this.a.setVideoScalingMode(this.a.y);
                        break;
                }
                if (this.a.P != null) {
                    this.a.P.onInfo(iMediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.j = new OnSeekCompleteListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (this.a.O != null) {
                    this.a.O.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.k = new OnLogEventListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onLogEvent(IMediaPlayer iMediaPlayer, String str) {
                if (this.a.S != null) {
                    this.a.S.onLogEvent(iMediaPlayer, str);
                }
            }
        };
        this.l = new OnMessageListener() {
            final /* synthetic */ KSYVideoView a;

            {
                this.a = r1;
            }

            public void onMessage(IMediaPlayer iMediaPlayer, String str, String str2, double d) {
                if (this.a.T != null) {
                    this.a.T.onMessage(iMediaPlayer, str, str2, d);
                }
            }
        };
        a(context);
        b(context);
    }

    private void a(Context context) {
        LayoutParams layoutParams = new LayoutParams(C, C);
        layoutParams.gravity = 17;
        this.A = new a(this, context);
        this.A.getHolder().addCallback(this);
        addView(this.A, layoutParams);
        this.s = D;
        this.r = D;
        this.q = D;
        this.p = D;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    private void b(Context context) {
        this.o = new Builder(context).build();
        this.o.setOnPreparedListener(this.e);
        this.o.setOnVideoSizeChangedListener(this.d);
        this.o.setOnCompletionListener(this.f);
        this.o.setOnErrorListener(this.g);
        this.o.setOnBufferingUpdateListener(this.h);
        this.o.setOnInfoListener(this.i);
        this.o.setOnSeekCompleteListener(this.j);
        this.o.setOnLogEventListener(this.k);
        this.o.setOnMessageListener(this.l);
        this.o.shouldAutoPlay(false);
    }

    public void setMediaController(IMediaController iMediaController) {
        if (this.n != null) {
            this.n.hide();
        }
        this.n = iMediaController;
        a();
    }

    private void a() {
        if (this.o != null && this.n != null) {
            this.n.setMediaPlayer(this);
            this.n.setAnchorView(getParent() instanceof View ? (View) getParent() : this);
            this.n.setEnabled(false);
            this.n.hide();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (b() && this.n != null) {
            c();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (b() && this.n != null) {
            c();
        }
        return false;
    }

    private boolean b() {
        return this.o != null;
    }

    private void c() {
        if (this.n.isShowing()) {
            this.n.hide();
        } else {
            this.n.show();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = (i == H || i == 24 || i == 25 || i == 164 || i == 82 || i == I || i == J) ? false : true;
        if (b() && z && this.n != null) {
            if (i == 79 || i == 85) {
                if (this.o.isPlaying()) {
                    pause();
                    this.n.show();
                    return true;
                }
                start();
                this.n.hide();
                return true;
            } else if (i == 126) {
                if (this.o.isPlaying()) {
                    return true;
                }
                start();
                this.n.hide();
                return true;
            } else if (i != 86 && i != 127) {
                c();
            } else if (!this.o.isPlaying()) {
                return true;
            } else {
                pause();
                this.n.show();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.M = onPreparedListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.L = onCompletionListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.N = onErrorListener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.Q = onBufferingUpdateListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.O = onSeekCompleteListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.P = onInfoListener;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.R = onVideoSizeChangedListener;
    }

    public void setOnLogEventListener(OnLogEventListener onLogEventListener) {
        this.S = onLogEventListener;
    }

    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.T = onMessageListener;
    }

    protected void onMeasure(int i, int i2) {
        if (this.mCurrentState < F) {
            super.onMeasure(i, i2);
        } else if (this.p == 0 || this.q == 0) {
            super.onMeasure(i, i2);
            if (this.mCurrentState == F && this.t) {
                start();
            }
        } else if (this.A == null) {
            super.onMeasure(i, i2);
        } else {
            measureChild(this.A, i, i2);
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            int size2 = MeasureSpec.getSize(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                mode = size;
            } else {
                if (mode == 1073741824) {
                    mode = this.A.b();
                    if (mode > size2) {
                        mode = size;
                    }
                } else if (mode2 == 1073741824) {
                    mode = this.A.a();
                    if (mode > size) {
                        mode = size;
                    }
                } else {
                    mode2 = this.A.a();
                    mode = this.A.b();
                    if (mode2 <= size) {
                        size = mode2;
                    }
                    if (mode > size2) {
                        mode = size;
                    }
                }
                size2 = mode;
                mode = size;
            }
            setMeasuredDimension(mode, size2);
            if (this.mCurrentState == F && this.t) {
                start();
            }
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.A != null) {
            this.A.getHolder().setFixedSize(i2, i3);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (this.o != null) {
            this.o.setDisplay(surfaceHolder);
            this.B = surfaceHolder;
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (this.o != null) {
            this.o.setDisplay(null);
            this.B = null;
        }
        if (this.n != null) {
            this.n.hide();
        }
    }

    public void setDataSource(Context context, Uri uri) throws IOException {
        if (this.o != null) {
            this.o.setDataSource(context, uri);
        }
    }

    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException {
        if (this.o != null) {
            this.o.setDataSource(context, uri, (Map) map);
        }
    }

    public void setDataSource(String str) throws IOException {
        if (this.o != null) {
            this.o.setDataSource(str);
        }
    }

    public void setDataSource(String str, Map<String, String> map) throws IOException {
        if (this.o != null) {
            this.o.setDataSource(str, (Map) map);
        }
    }

    @TargetApi(13)
    public void setDataSource(FileDescriptor fileDescriptor) throws IOException {
        if (this.o != null) {
            this.o.setDataSource(fileDescriptor);
        }
    }

    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.o != null) {
            this.o.setDataSource(fileDescriptor, j, j2);
        }
    }

    public void setDataSource(List<String> list, Map<String, String> map) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.o != null) {
            this.o.setDataSource((List) list, (Map) map);
        }
    }

    public void prepareAsync() {
        if (this.o != null) {
            this.o.prepareAsync();
            this.mCurrentState = E;
        }
    }

    public void start() {
        if (this.o != null && this.mCurrentState >= F) {
            this.o.start();
            this.mCurrentState = G;
            if (this.n != null) {
                this.n.onStart();
            }
        }
    }

    public void pause() {
        if (this.o != null) {
            this.o.pause();
            this.mCurrentState = H;
            if (this.n != null) {
                this.n.onPause();
            }
        }
    }

    public long getDuration() {
        if (this.o != null) {
            return this.o.getDuration();
        }
        return 0;
    }

    public long getCurrentPosition() {
        if (this.o != null) {
            return this.o.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(long j) {
        if (this.o != null) {
            this.o.seekTo(j);
        }
    }

    public void seekTo(long j, boolean z) {
        if (this.o != null) {
            this.o.seekTo(j, z);
        }
    }

    public boolean isPlaying() {
        if (this.o != null) {
            return this.o.isPlaying();
        }
        return false;
    }

    public int getBufferPercentage() {
        if (this.o != null) {
            return this.b;
        }
        return D;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public int getVideoWidth() {
        return this.p;
    }

    public int getVideoHeight() {
        return this.q;
    }

    public void setScreenOnWhilePlaying(boolean z) {
        if (this.o != null) {
            this.o.setScreenOnWhilePlaying(z);
        }
    }

    public void stop() {
        if (this.o != null) {
            this.o.stop();
        }
        this.mCurrentState = K;
        this.u = false;
        this.w = false;
        this.w = false;
    }

    public void release() {
        if (this.o != null) {
            this.o.release();
            this.o = null;
            this.mCurrentState = D;
        }
    }

    private void d() {
        this.a = null;
        this.x = false;
        this.y = C;
        this.z = D;
        this.b = D;
        this.s = D;
        this.r = D;
        this.q = D;
        this.p = D;
        this.w = false;
        this.v = false;
        this.u = false;
        this.mCurrentState = D;
        this.t = true;
        if (this.A != null) {
            this.A.c();
            this.A.setVisibility(H);
        }
        if (this.o != null) {
            this.o.shouldAutoPlay(false);
            this.o.setDisplay(this.B);
        }
        if (this.n != null) {
            this.n.setEnabled(false);
        }
    }

    public void reset() {
        if (this.o != null) {
            this.o.reset();
            d();
        }
    }

    public MediaInfo getMediaInfo() {
        if (this.o != null) {
            if (this.a == null) {
                this.a = this.o.getMediaInfo();
            }
            return this.a;
        }
        this.a = null;
        return this.a;
    }

    public boolean isPlayable() {
        if (this.o != null) {
            return this.o.isPlayable();
        }
        return false;
    }

    public void reload(String str, boolean z) {
        this.w = false;
        this.v = false;
        this.u = false;
        this.b = D;
        this.mCurrentState = I;
        if (this.o != null) {
            this.o.reload(str, z);
        }
        if (z && this.A != null) {
            this.A.setVisibility(H);
        }
        if (this.n != null) {
            this.n.setEnabled(false);
        }
    }

    public void reload(String str, boolean z, KSYReloadMode kSYReloadMode) {
        this.w = false;
        this.v = false;
        this.u = false;
        this.b = D;
        this.mCurrentState = I;
        if (z && this.A != null) {
            this.A.setVisibility(H);
        }
        if (this.n != null) {
            this.n.setEnabled(false);
        }
        if (this.o != null) {
            this.o.reload(str, z, kSYReloadMode);
        }
    }

    public int getAudioSessionId() {
        if (this.o != null) {
            return this.o.getAudioSessionId();
        }
        return C;
    }

    public void setVideoScalingMode(int i) {
        this.y = i;
        if (this.A != null) {
            this.A.a(i);
        }
    }

    public boolean setRotateDegree(int i) {
        if (this.x) {
            return false;
        }
        this.z = i;
        if (this.mCurrentState > F) {
            this.A.b(i);
        }
        if (this.o != null) {
            this.o.setRotateDegree(i);
        }
        return true;
    }

    public void setRotation(float f) {
        setRotateDegree((int) f);
    }

    public void setDecodeMode(KSYDecodeMode kSYDecodeMode) {
        if (this.o != null) {
            this.o.setDecodeMode(kSYDecodeMode);
        }
    }

    public void setMirror(boolean z) {
        if (!this.x && this.o != null) {
            this.o.setMirror(z);
        }
    }

    public void setVideoOffset(float f, float f2) {
        if (!this.x && this.o != null) {
            this.o.setVideoOffset(f, f2);
        }
    }

    public void setDeinterlaceMode(KSYDeinterlaceMode kSYDeinterlaceMode) {
        if (this.o != null) {
            this.o.setDeinterlaceMode(kSYDeinterlaceMode);
        }
    }

    public String getVersion() {
        if (this.o == null) {
            return "N/A";
        }
        KSYMediaPlayer kSYMediaPlayer = this.o;
        return KSYMediaPlayer.getVersion();
    }

    public void shouldAutoPlay(boolean z) {
        this.t = z;
    }

    public String getDataSource() {
        if (this.o != null) {
            return this.o.getDataSource();
        }
        return null;
    }

    public void setWakeMode(Context context, int i) {
        if (this.o != null) {
            this.o.setWakeMode(context, i);
        }
    }

    public KSYTrackInfo[] getTrackInfo() {
        if (this.o != null) {
            return this.o.getTrackInfo();
        }
        return null;
    }

    public int getSelectedTrack(int i) {
        if (this.o != null) {
            return this.o.getSelectedTrack(i);
        }
        return D;
    }

    public void selectTrack(int i) {
        if (this.o != null) {
            this.o.selectTrack(i);
        }
    }

    public void deselectTrack(int i) {
        if (this.o != null) {
            this.o.deselectTrack(i);
        }
    }

    public int getVideoSarNum() {
        if (this.o != null) {
            return this.o.getVideoSarNum();
        }
        return D;
    }

    public int getVideoSarDen() {
        if (this.o != null) {
            return this.o.getVideoSarDen();
        }
        return D;
    }

    public void setLooping(boolean z) {
        if (this.o != null) {
            this.o.setLooping(z);
        }
    }

    public boolean isLooping() {
        if (this.o != null) {
            return this.o.isLooping();
        }
        return false;
    }

    public void softReset() {
        if (this.o != null) {
            this.o.softReset();
            d();
        }
    }

    public void setVideoRenderingState(int i) {
        if (this.o != null) {
            this.o.setVideoRenderingState(i);
        }
    }

    public void setSpeed(float f) {
        if (this.o != null) {
            this.o.setSpeed(f);
        }
    }

    public float getSpeed() {
        if (this.o != null) {
            return this.o.getSpeed();
        }
        return 1.0f;
    }

    public int getVideoDecoder() {
        if (this.o != null) {
            return this.o.getVideoDecoder();
        }
        return D;
    }

    public float getVideoOutputFramesPerSecond() {
        if (this.o != null) {
            return this.o.getVideoOutputFramesPerSecond();
        }
        return 0.0f;
    }

    public float getVideoDecodeFramesPerSecond() {
        if (this.o != null) {
            return this.o.getVideoDecodeFramesPerSecond();
        }
        return 0.0f;
    }

    public long getDecodedDataSize() {
        if (this.o != null) {
            return this.o.getDecodedDataSize();
        }
        return 0;
    }

    public long getDownloadDataSize() {
        if (this.o != null) {
            return this.o.getDownloadDataSize();
        }
        return 0;
    }

    public String getServerAddress() {
        if (this.o != null) {
            return this.o.getServerAddress();
        }
        return "N/A";
    }

    public int bufferEmptyCount() {
        if (this.o != null) {
            return this.o.bufferEmptyCount();
        }
        return D;
    }

    public float bufferEmptyDuration() {
        if (this.o != null) {
            return this.o.bufferEmptyDuration();
        }
        return 0.0f;
    }

    public long getVideoCachedDuration() {
        if (this.o != null) {
            return this.o.getVideoCachedDuration();
        }
        return 0;
    }

    public long getAudioCachedDuration() {
        if (this.o != null) {
            return this.o.getAudioCachedDuration();
        }
        return 0;
    }

    public long getVideoCachedBytes() {
        if (this.o != null) {
            return this.o.getVideoCachedBytes();
        }
        return 0;
    }

    public long getAudioCachedBytes() {
        if (this.o != null) {
            return this.o.getAudioCachedBytes();
        }
        return 0;
    }

    public long getVideoCachedPackets() {
        if (this.o != null) {
            return this.o.getVideoCachedPackets();
        }
        return 0;
    }

    public long getAudioCachedPackets() {
        if (this.o != null) {
            return this.o.getAudioCachedPackets();
        }
        return 0;
    }

    public long getCurrentPts() {
        if (this.o != null) {
            return this.o.getCurrentPts();
        }
        return 0;
    }

    public long getStreamStartTime() {
        if (this.o != null) {
            return this.o.getStreamStartTime();
        }
        return 0;
    }

    public void setVolume(float f, float f2) {
        if (this.o != null) {
            this.o.setVolume(f, f2);
        }
    }

    public Bundle getMediaMeta() {
        if (this.o != null) {
            return this.o.getMediaMeta();
        }
        return null;
    }

    public void setTimeout(int i, int i2) {
        if (this.o != null) {
            this.o.setTimeout(i, i2);
        }
    }

    public void setBufferSize(int i) {
        if (this.o != null) {
            this.o.setBufferSize(i);
        }
    }

    public KSYQosInfo getStreamQosInfo() {
        if (this.o != null) {
            return this.o.getStreamQosInfo();
        }
        return null;
    }

    public void setBufferTimeMax(float f) {
        if (this.o != null) {
            this.o.setBufferTimeMax(f);
        }
    }

    public float getBufferTimeMax() {
        if (this.o != null) {
            return this.o.getBufferTimeMax();
        }
        return 0.0f;
    }

    public Bitmap getScreenShot() {
        if (this.o == null || this.x) {
            return null;
        }
        return this.o.getScreenShot();
    }

    public void setPlayerMute(int i) {
        if (this.o != null) {
            this.o.setPlayerMute(i);
        }
    }

    public void setOnAudioPCMAvailableListener(OnAudioPCMListener onAudioPCMListener) {
        if (this.o != null) {
            this.o.setOnAudioPCMAvailableListener(onAudioPCMListener);
        }
    }

    public void setVideoRawDataListener(OnVideoRawDataListener onVideoRawDataListener) {
        if (this.o != null) {
            this.o.setVideoRawDataListener(onVideoRawDataListener);
        }
    }

    public void addVideoRawBuffer(byte[] bArr) {
        if (this.o != null) {
            this.o.addVideoRawBuffer(bArr);
        }
    }

    public void setOption(int i, String str, String str2) {
        if (this.o != null) {
            this.o.setOption(i, str, str2);
        }
    }

    public void setOption(int i, String str, long j) {
        if (this.o != null) {
            this.o.setOption(i, str, j);
        }
    }

    public String getLocalDnsIP() {
        if (this.o != null) {
            this.o.getLocalDnsIP();
        }
        return "N/A";
    }

    public String getClientIP() {
        if (this.o != null) {
            this.o.getClientIP();
        }
        return "N/A";
    }

    public void setPlayableRanges(long j, long j2) {
        if (this.o != null) {
            this.o.setPlayableRanges(j, j2);
        }
    }
}
