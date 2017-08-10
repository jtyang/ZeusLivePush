package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
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
import com.ksyun.media.player.IMediaPlayer.OnTimedTextListener;
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

@TargetApi(16)
public class KSYTextureView extends FrameLayout implements SurfaceTextureListener, MediaPlayerControl {
    private static final int E = -1;
    private static final int F = 0;
    private static final int G = 1;
    private static final int H = 2;
    private static final int I = 3;
    private static final int J = 4;
    private static final int K = 5;
    private static final int L = 6;
    private static final int M = 7;
    protected static final int c = 8;
    private boolean A;
    private boolean B;
    private boolean C;
    private int D;
    private OnCompletionListener N;
    private OnPreparedListener O;
    private OnErrorListener P;
    private OnSeekCompleteListener Q;
    private OnInfoListener R;
    private OnBufferingUpdateListener S;
    private OnVideoSizeChangedListener T;
    private OnLogEventListener U;
    private OnMessageListener V;
    private OnTimedTextListener W;
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
    protected OnTimedTextListener m;
    public int mCurrentState;
    private a n;
    private IMediaController o;
    private KSYMediaPlayer p;
    private SurfaceTexture q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private boolean w;
    private boolean x;
    private boolean y;
    private boolean z;

    class a extends TextureView implements SurfaceTextureListener {
        public static final int b = 1;
        public static final int c = 2;
        public static final int d = 3;
        private float A;
        private float B;
        private float C;
        private float D;
        private float E;
        private float F;
        private int G;
        boolean a;
        final /* synthetic */ KSYTextureView e;
        private SurfaceTextureListener f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int m;
        private int n;
        private boolean o;
        private boolean p;
        private float q;
        private float r;
        private Matrix s;
        private int t;
        private int u;
        private float v;
        private float w;
        private float x;
        private float y;
        private float z;

        public a(KSYTextureView kSYTextureView, Context context) {
            this.e = kSYTextureView;
            super(context);
            this.m = b;
            this.o = false;
            this.a = false;
            this.q = 0.0f;
            this.r = 0.0f;
            this.s = new Matrix();
            this.D = 1.0f;
            this.G = b;
            super.setSurfaceTextureListener(this);
        }

        public a(KSYTextureView kSYTextureView, Context context, AttributeSet attributeSet) {
            this(kSYTextureView, context, attributeSet, KSYTextureView.F);
        }

        public a(KSYTextureView kSYTextureView, Context context, AttributeSet attributeSet, int i) {
            this.e = kSYTextureView;
            super(context, attributeSet, i);
            this.m = b;
            this.o = false;
            this.a = false;
            this.q = 0.0f;
            this.r = 0.0f;
            this.s = new Matrix();
            this.D = 1.0f;
            this.G = b;
            super.setSurfaceTextureListener(this);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void d() {
            /*
            r11 = this;
            r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
            r3 = 0;
            r5 = r11.s;
            r0 = r11.g;
            r1 = r11.h;
            r2 = r11.o;
            if (r2 == 0) goto L_0x0017;
        L_0x000f:
            r2 = r11.t;
            r2 = (float) r2;
            r4 = r11.v;
            r2 = r2 - r4;
            r11.v = r2;
        L_0x0017:
            r2 = r11.i;
            if (r2 <= 0) goto L_0x0025;
        L_0x001b:
            r2 = r11.j;
            if (r2 <= 0) goto L_0x0025;
        L_0x001f:
            r2 = r11.i;
            r0 = r0 * r2;
            r2 = r11.j;
            r0 = r0 / r2;
        L_0x0025:
            r0 = (float) r0;
            r2 = r11.t;
            r2 = (float) r2;
            r2 = r0 / r2;
            r0 = (float) r1;
            r1 = r11.u;
            r1 = (float) r1;
            r4 = r0 / r1;
            r5.reset();
            r0 = r11.D;
            r0 = r0 * r2;
            r1 = r11.D;
            r1 = r1 * r4;
            r5.postScale(r0, r1);
            r0 = r11.n;
            r0 = (float) r0;
            r5.postRotate(r0);
            r0 = r11.t;
            r0 = (float) r0;
            r1 = r11.D;
            r0 = r0 * r1;
            r1 = r0 * r2;
            r0 = r11.u;
            r0 = (float) r0;
            r6 = r11.D;
            r0 = r0 * r6;
            r0 = r0 * r4;
            r6 = r11.n;
            r6 = r6 / 90;
            r6 = r6 % 2;
            if (r6 == 0) goto L_0x0069;
        L_0x005a:
            r0 = r11.u;
            r0 = (float) r0;
            r1 = r11.D;
            r0 = r0 * r1;
            r1 = r0 * r4;
            r0 = r11.t;
            r0 = (float) r0;
            r4 = r11.D;
            r0 = r0 * r4;
            r0 = r0 * r2;
        L_0x0069:
            r2 = r11.B;
            r4 = r11.E;
            r2 = r2 * r4;
            r4 = r11.v;
            r6 = r11.E;
            r6 = r9 - r6;
            r4 = r4 * r6;
            r2 = r2 + r4;
            r4 = r11.C;
            r6 = r11.E;
            r4 = r4 * r6;
            r6 = r11.w;
            r7 = r11.E;
            r7 = r9 - r7;
            r6 = r6 * r7;
            r4 = r4 + r6;
            r6 = r11.n;
            switch(r6) {
                case -270: goto L_0x0159;
                case -180: goto L_0x011b;
                case -90: goto L_0x00d4;
                case 0: goto L_0x0095;
                default: goto L_0x0088;
            };
        L_0x0088:
            r3 = r4;
        L_0x0089:
            r5.postTranslate(r2, r3);
            r11.B = r2;
            r11.C = r3;
            r11.z = r1;
            r11.A = r0;
            return;
        L_0x0095:
            r6 = r11.t;
            r6 = (float) r6;
            r6 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x00ae;
        L_0x009c:
            r2 = r11.t;
            r2 = (float) r2;
            r2 = r2 - r1;
            r2 = r2 / r8;
        L_0x00a1:
            r6 = r11.u;
            r6 = (float) r6;
            r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x00c2;
        L_0x00a8:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = r3 - r0;
            r3 = r3 / r8;
            goto L_0x0089;
        L_0x00ae:
            r6 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
            if (r6 <= 0) goto L_0x00b4;
        L_0x00b2:
            r2 = r3;
            goto L_0x00a1;
        L_0x00b4:
            r6 = r1 + r2;
            r7 = r11.t;
            r7 = (float) r7;
            r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1));
            if (r6 >= 0) goto L_0x00a1;
        L_0x00bd:
            r2 = r11.t;
            r2 = (float) r2;
            r2 = r2 - r1;
            goto L_0x00a1;
        L_0x00c2:
            r6 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1));
            if (r6 > 0) goto L_0x0089;
        L_0x00c6:
            r3 = r0 + r4;
            r6 = r11.u;
            r6 = (float) r6;
            r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1));
            if (r3 >= 0) goto L_0x0088;
        L_0x00cf:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = r3 - r0;
            goto L_0x0089;
        L_0x00d4:
            r6 = r11.t;
            r6 = (float) r6;
            r6 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x00f1;
        L_0x00db:
            r2 = r11.t;
            r2 = (float) r2;
            r2 = r2 - r1;
            r3 = r2 / r8;
        L_0x00e1:
            r2 = r11.u;
            r2 = (float) r2;
            r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0104;
        L_0x00e8:
            r2 = r11.u;
            r2 = (float) r2;
            r2 = r2 + r0;
            r2 = r2 / r8;
            r10 = r2;
            r2 = r3;
            r3 = r10;
            goto L_0x0089;
        L_0x00f1:
            r6 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
            if (r6 > 0) goto L_0x00e1;
        L_0x00f5:
            r3 = r1 + r2;
            r6 = r11.t;
            r6 = (float) r6;
            r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1));
            if (r3 >= 0) goto L_0x019b;
        L_0x00fe:
            r2 = r11.t;
            r2 = (float) r2;
            r3 = r2 - r1;
            goto L_0x00e1;
        L_0x0104:
            r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
            if (r2 <= 0) goto L_0x010c;
        L_0x0108:
            r2 = r3;
            r3 = r0;
            goto L_0x0089;
        L_0x010c:
            r2 = r11.u;
            r2 = (float) r2;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 >= 0) goto L_0x0197;
        L_0x0113:
            r2 = r11.u;
            r2 = (float) r2;
            r10 = r2;
            r2 = r3;
            r3 = r10;
            goto L_0x0089;
        L_0x011b:
            r3 = r11.t;
            r3 = (float) r3;
            r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
            if (r3 >= 0) goto L_0x0135;
        L_0x0122:
            r2 = r11.t;
            r2 = (float) r2;
            r2 = r2 + r1;
            r2 = r2 / r8;
        L_0x0127:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1));
            if (r3 >= 0) goto L_0x0146;
        L_0x012e:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = r3 + r0;
            r3 = r3 / r8;
            goto L_0x0089;
        L_0x0135:
            r3 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1));
            if (r3 <= 0) goto L_0x013b;
        L_0x0139:
            r2 = r1;
            goto L_0x0127;
        L_0x013b:
            r3 = r11.t;
            r3 = (float) r3;
            r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
            if (r3 >= 0) goto L_0x0127;
        L_0x0142:
            r2 = r11.t;
            r2 = (float) r2;
            goto L_0x0127;
        L_0x0146:
            r3 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
            if (r3 <= 0) goto L_0x014d;
        L_0x014a:
            r3 = r0;
            goto L_0x0089;
        L_0x014d:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1));
            if (r3 >= 0) goto L_0x0088;
        L_0x0154:
            r3 = r11.u;
            r3 = (float) r3;
            goto L_0x0089;
        L_0x0159:
            r6 = r11.t;
            r6 = (float) r6;
            r6 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x0173;
        L_0x0160:
            r2 = r11.t;
            r2 = (float) r2;
            r2 = r2 + r1;
            r2 = r2 / r8;
        L_0x0165:
            r6 = r11.u;
            r6 = (float) r6;
            r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x0184;
        L_0x016c:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = r3 - r0;
            r3 = r3 / r8;
            goto L_0x0089;
        L_0x0173:
            r6 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1));
            if (r6 <= 0) goto L_0x0179;
        L_0x0177:
            r2 = r1;
            goto L_0x0165;
        L_0x0179:
            r6 = r11.t;
            r6 = (float) r6;
            r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x0165;
        L_0x0180:
            r2 = r11.t;
            r2 = (float) r2;
            goto L_0x0165;
        L_0x0184:
            r6 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1));
            if (r6 > 0) goto L_0x0089;
        L_0x0188:
            r3 = r0 + r4;
            r6 = r11.u;
            r6 = (float) r6;
            r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1));
            if (r3 >= 0) goto L_0x0088;
        L_0x0191:
            r3 = r11.u;
            r3 = (float) r3;
            r3 = r3 - r0;
            goto L_0x0089;
        L_0x0197:
            r2 = r3;
            r3 = r4;
            goto L_0x0089;
        L_0x019b:
            r3 = r2;
            goto L_0x00e1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ksyun.media.player.KSYTextureView.a.d():void");
        }

        private void e() {
            Matrix matrix = this.s;
            int i = this.g;
            int i2 = this.h;
            if (this.o) {
                this.x = -this.x;
            }
            if (this.i > 0 && this.j > 0) {
                i = (i * this.i) / this.j;
            }
            float f = ((float) i) / ((float) this.t);
            float f2 = ((float) i2) / ((float) this.u);
            matrix.reset();
            matrix.postScale(f * this.D, f2 * this.D);
            matrix.postRotate((float) this.n);
            switch (this.n) {
                case -270:
                    f2 = (((float) this.t) + this.z) / 2.0f;
                    f = (((float) this.u) - this.A) / 2.0f;
                    break;
                case -180:
                    f2 = (((float) this.t) + this.z) / 2.0f;
                    f = (((float) this.u) + this.A) / 2.0f;
                    break;
                case -90:
                    f2 = (((float) this.t) - this.z) / 2.0f;
                    f = (((float) this.u) + this.A) / 2.0f;
                    break;
                case KSYTextureView.F /*0*/:
                    f2 = (((float) this.t) - this.z) / 2.0f;
                    f = (((float) this.u) - this.A) / 2.0f;
                    break;
                default:
                    f = 0.0f;
                    f2 = 0.0f;
                    break;
            }
            if (this.B + this.x > ((this.z - ((float) this.t)) / 2.0f) + f2) {
                this.x = 0.0f;
            } else if (this.B + this.x < f2 - ((this.z - ((float) this.t)) / 2.0f)) {
                this.x = 0.0f;
            }
            if (this.C + this.y > ((this.A - ((float) this.u)) / 2.0f) + f) {
                this.y = 0.0f;
            } else if (this.C + this.y < f - ((this.A - ((float) this.u)) / 2.0f)) {
                this.y = 0.0f;
            }
            f = this.B + this.x;
            float f3 = this.C + this.y;
            matrix.postTranslate(f, f3);
            this.B = f;
            this.C = f3;
        }

        private void c(int i, int i2) {
            float f;
            float f2;
            float f3;
            int i3 = this.g;
            int i4 = this.h;
            int i5 = this.t;
            int i6 = this.u;
            Matrix matrix = this.s;
            if (this.i > 0 && this.j > 0) {
                i3 = (i3 * this.i) / this.j;
            }
            float f4 = ((float) i3) / ((float) this.t);
            float f5 = ((float) i4) / ((float) this.u);
            if ((this.n / 90) % c != 0) {
                i3 = this.g;
                i4 = this.h;
                if (this.i > 0 && this.j > 0) {
                    i3 = (i3 * this.i) / this.j;
                }
            } else {
                int i7 = i4;
                i4 = i3;
                i3 = i7;
            }
            this.F = Math.min(((float) i5) / ((float) i4), ((float) i6) / ((float) i3));
            switch (this.m) {
                case KSYTextureView.F /*0*/:
                    if ((this.n / 90) % c != 0) {
                        f = ((float) i6) / ((float) i5);
                        f2 = ((float) i5) / ((float) i6);
                    } else {
                        f = 1.0f;
                        f2 = 1.0f;
                    }
                    this.F = 1.0f;
                    this.D = 1.0f;
                    f5 = 0.0f;
                    f4 = 0.0f;
                    f3 = 1.0f;
                    break;
                case b /*1*/:
                    f3 = Math.min(((float) i5) / ((float) i4), ((float) i6) / ((float) i3));
                    f = this.q;
                    f2 = this.r;
                    this.D = f3;
                    float f6 = f5;
                    f5 = f2;
                    f2 = f6;
                    float f7 = f4;
                    f4 = f;
                    f = f7;
                    break;
                case c /*2*/:
                    f = Math.max(((float) i5) / ((float) i4), ((float) i6) / ((float) i3));
                    this.D = f;
                    f3 = f;
                    f = f4;
                    f4 = 0.0f;
                    f2 = f5;
                    f5 = KSYTextureView.F;
                    break;
                default:
                    f2 = f5;
                    f = f4;
                    f5 = 0.0f;
                    f4 = 0.0f;
                    f3 = 1.0f;
                    break;
            }
            if ((this.n / 90) % c != 0) {
                this.z = (((float) i6) * f2) * f3;
                this.A = (((float) i5) * f) * f3;
            } else {
                this.z = (((float) i5) * f) * f3;
                this.A = (((float) i6) * f2) * f3;
            }
            matrix.reset();
            matrix.postScale(f3 * f, f3 * f2);
            matrix.postRotate((float) this.n);
            float f8 = 0.0f;
            float f9 = 0.0f;
            switch (this.n) {
                case -270:
                    f8 = (((float) i5) + this.z) / 2.0f;
                    f9 = (((float) i6) - this.A) / 2.0f;
                    break;
                case -180:
                    f8 = (((float) i5) + this.z) / 2.0f;
                    f9 = (((float) i6) + this.A) / 2.0f;
                    break;
                case -90:
                    f8 = (((float) i5) - this.z) / 2.0f;
                    f9 = (((float) i6) + this.A) / 2.0f;
                    break;
                case KSYTextureView.F /*0*/:
                    f8 = (((float) i5) - this.z) / 2.0f;
                    f9 = (((float) i6) - this.A) / 2.0f;
                    break;
            }
            this.B = ((f4 * ((float) i5)) / 2.0f) + f8;
            this.C = f9 - ((f5 * ((float) i6)) / 2.0f);
            matrix.postTranslate(this.B, this.C);
            this.k = (int) (f * (((float) i5) * f3));
            this.l = (int) (f2 * (((float) i6) * f3));
        }

        private void d(int i, int i2) {
            if (this.g != 0 && this.h != 0) {
                int mode = MeasureSpec.getMode(i);
                int size = MeasureSpec.getSize(i);
                int mode2 = MeasureSpec.getMode(i2);
                int size2 = MeasureSpec.getSize(i2);
                this.t = size;
                this.u = size2;
                if (this.G == b) {
                    c(mode, mode2);
                }
                setTransform(this.s);
            }
        }

        protected void onMeasure(int i, int i2) {
            d(i, i2);
            setMeasuredDimension(i, i2);
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            if (this.f != null) {
                this.f.onSurfaceTextureAvailable(surfaceTexture, i, i2);
            }
            requestLayout();
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            if (this.f != null) {
                this.f.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
            }
            requestLayout();
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (this.f != null) {
                return this.f.onSurfaceTextureDestroyed(surfaceTexture);
            }
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            if (this.f != null) {
                this.f.onSurfaceTextureUpdated(surfaceTexture);
            }
        }

        public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
            this.f = surfaceTextureListener;
        }

        public void a(int i, int i2) {
            this.g = i;
            this.h = i2;
        }

        public void b(int i, int i2) {
            this.i = i;
            this.j = i2;
        }

        public void a(boolean z) {
            this.o = z;
            setScaleX(z ? -1.0f : 1.0f);
        }

        public void a(int i) {
            this.n = i;
            this.G = b;
            requestLayout();
        }

        public void b(int i) {
            this.m = i;
            this.a = false;
            this.G = b;
            requestLayout();
        }

        public void a(float f, float f2, float f3) {
            if (((double) f) >= 0.25d && f <= 100.0f) {
                if (this.m != b || (this.q <= 0.0f && this.q >= 0.0f && this.r <= 0.0f && this.r >= 0.0f)) {
                    this.E = f / this.D;
                    this.D = f;
                    this.v = f2;
                    this.w = f3;
                    this.G = c;
                    d();
                    requestLayout();
                }
            }
        }

        public float a() {
            return this.D;
        }

        public void b(boolean z) {
            this.p = z;
            this.G = b;
            requestLayout();
        }

        void a(float f, float f2) {
            this.q = f;
            this.r = f2;
            this.G = b;
            requestLayout();
        }

        void b(float f, float f2) {
            if (this.m != b || (this.q <= 0.0f && this.q >= 0.0f && this.r <= 0.0f && this.r >= 0.0f)) {
                this.x = f;
                this.y = f2;
                this.G = d;
                e();
                requestLayout();
            }
        }

        public int b() {
            return this.k;
        }

        public int c() {
            return this.l;
        }
    }

    public KSYTextureView(Context context) {
        super(context);
        this.q = null;
        this.v = F;
        this.w = false;
        this.x = true;
        this.y = false;
        this.C = true;
        this.D = G;
        this.mCurrentState = F;
        this.d = new OnVideoSizeChangedListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
                this.a.r = iMediaPlayer.getVideoWidth();
                this.a.s = iMediaPlayer.getVideoHeight();
                this.a.t = i3;
                this.a.u = i4;
                Object obj = (this.a.mCurrentState == KSYTextureView.I || this.a.mCurrentState == KSYTextureView.J) ? KSYTextureView.G : null;
                if (!(this.a.n == null || obj == null)) {
                    this.a.n.a(this.a.r, this.a.s);
                    this.a.n.b(this.a.t, this.a.u);
                    this.a.setVideoScalingMode(this.a.D);
                }
                if (this.a.T != null) {
                    this.a.T.onVideoSizeChanged(iMediaPlayer, i, i2, i3, i4);
                }
            }
        };
        this.e = new OnPreparedListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onPrepared(IMediaPlayer iMediaPlayer) {
                this.a.z = this.a.A = this.a.B = true;
                if (this.a.O != null) {
                    this.a.O.onPrepared(iMediaPlayer);
                }
                if (this.a.x) {
                    this.a.mCurrentState = KSYTextureView.I;
                } else {
                    this.a.mCurrentState = KSYTextureView.H;
                }
                if (this.a.o != null) {
                    this.a.o.setEnabled(true);
                    if (this.a.x) {
                        this.a.o.onStart();
                    } else {
                        this.a.o.onPause();
                    }
                }
            }
        };
        this.f = new OnCompletionListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onCompletion(IMediaPlayer iMediaPlayer) {
                if (this.a.N != null) {
                    this.a.N.onCompletion(iMediaPlayer);
                }
                this.a.mCurrentState = KSYTextureView.c;
                if (this.a.o != null) {
                    this.a.o.hide();
                }
            }
        };
        this.g = new OnErrorListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
                if (this.a.P == null || !this.a.P.onError(iMediaPlayer, i, i2)) {
                    this.a.mCurrentState = KSYTextureView.E;
                    if (this.a.o != null) {
                        this.a.o.hide();
                    }
                }
                return true;
            }
        };
        this.h = new OnBufferingUpdateListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                this.a.b = i;
                if (this.a.S != null) {
                    this.a.S.onBufferingUpdate(iMediaPlayer, i);
                }
            }
        };
        this.i = new OnInfoListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
                switch (i) {
                    case KSYTextureView.I /*3*/:
                        if (this.a.n != null) {
                            this.a.n.a(this.a.r, this.a.s);
                            this.a.n.b(this.a.t, this.a.u);
                        }
                        this.a.setVideoScalingMode(this.a.D);
                        this.a.n.setVisibility(KSYTextureView.F);
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED /*10001*/:
                        if (!(this.a.p == null || this.a.C)) {
                            this.a.p.setRotateDegree(KSYTextureView.F);
                        }
                        if (this.a.mCurrentState != KSYTextureView.K) {
                            this.a.setRotateDegree(i2);
                            break;
                        }
                        this.a.setRotateDegree(this.a.v);
                        break;
                    case IMediaPlayer.MEDIA_INFO_HARDWARE_DECODE /*41000*/:
                        this.a.C = true;
                        break;
                    case IMediaPlayer.MEDIA_INFO_SOFTWARE_DECODE /*41001*/:
                        this.a.C = false;
                        break;
                    case IMediaPlayer.MEDIA_INFO_RELOADED /*50001*/:
                        this.a.n.setVisibility(KSYTextureView.J);
                        this.a.z = this.a.A = this.a.B = true;
                        this.a.b = KSYTextureView.F;
                        if (this.a.x) {
                            this.a.mCurrentState = KSYTextureView.I;
                        } else {
                            this.a.mCurrentState = KSYTextureView.L;
                        }
                        if (this.a.o != null) {
                            this.a.o.setEnabled(true);
                            if (!this.a.x) {
                                this.a.o.onPause();
                                break;
                            }
                            this.a.o.onStart();
                            break;
                        }
                        break;
                }
                if (this.a.R != null) {
                    this.a.R.onInfo(iMediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.j = new OnSeekCompleteListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (this.a.Q != null) {
                    this.a.Q.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.k = new OnLogEventListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onLogEvent(IMediaPlayer iMediaPlayer, String str) {
                if (this.a.U != null) {
                    this.a.U.onLogEvent(iMediaPlayer, str);
                }
            }
        };
        this.l = new OnMessageListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onMessage(IMediaPlayer iMediaPlayer, String str, String str2, double d) {
                if (this.a.V != null) {
                    this.a.V.onMessage(iMediaPlayer, str, str2, d);
                }
            }
        };
        this.m = new OnTimedTextListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onTimedText(IMediaPlayer iMediaPlayer, String str) {
                if (this.a.W != null) {
                    this.a.W.onTimedText(iMediaPlayer, str);
                }
            }
        };
        a(context);
        b(context);
    }

    public KSYTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, F);
    }

    public KSYTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.q = null;
        this.v = F;
        this.w = false;
        this.x = true;
        this.y = false;
        this.C = true;
        this.D = G;
        this.mCurrentState = F;
        this.d = new OnVideoSizeChangedListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
                this.a.r = iMediaPlayer.getVideoWidth();
                this.a.s = iMediaPlayer.getVideoHeight();
                this.a.t = i3;
                this.a.u = i4;
                Object obj = (this.a.mCurrentState == KSYTextureView.I || this.a.mCurrentState == KSYTextureView.J) ? KSYTextureView.G : null;
                if (!(this.a.n == null || obj == null)) {
                    this.a.n.a(this.a.r, this.a.s);
                    this.a.n.b(this.a.t, this.a.u);
                    this.a.setVideoScalingMode(this.a.D);
                }
                if (this.a.T != null) {
                    this.a.T.onVideoSizeChanged(iMediaPlayer, i, i2, i3, i4);
                }
            }
        };
        this.e = new OnPreparedListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onPrepared(IMediaPlayer iMediaPlayer) {
                this.a.z = this.a.A = this.a.B = true;
                if (this.a.O != null) {
                    this.a.O.onPrepared(iMediaPlayer);
                }
                if (this.a.x) {
                    this.a.mCurrentState = KSYTextureView.I;
                } else {
                    this.a.mCurrentState = KSYTextureView.H;
                }
                if (this.a.o != null) {
                    this.a.o.setEnabled(true);
                    if (this.a.x) {
                        this.a.o.onStart();
                    } else {
                        this.a.o.onPause();
                    }
                }
            }
        };
        this.f = new OnCompletionListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onCompletion(IMediaPlayer iMediaPlayer) {
                if (this.a.N != null) {
                    this.a.N.onCompletion(iMediaPlayer);
                }
                this.a.mCurrentState = KSYTextureView.c;
                if (this.a.o != null) {
                    this.a.o.hide();
                }
            }
        };
        this.g = new OnErrorListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
                if (this.a.P == null || !this.a.P.onError(iMediaPlayer, i, i2)) {
                    this.a.mCurrentState = KSYTextureView.E;
                    if (this.a.o != null) {
                        this.a.o.hide();
                    }
                }
                return true;
            }
        };
        this.h = new OnBufferingUpdateListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                this.a.b = i;
                if (this.a.S != null) {
                    this.a.S.onBufferingUpdate(iMediaPlayer, i);
                }
            }
        };
        this.i = new OnInfoListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
                switch (i) {
                    case KSYTextureView.I /*3*/:
                        if (this.a.n != null) {
                            this.a.n.a(this.a.r, this.a.s);
                            this.a.n.b(this.a.t, this.a.u);
                        }
                        this.a.setVideoScalingMode(this.a.D);
                        this.a.n.setVisibility(KSYTextureView.F);
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED /*10001*/:
                        if (!(this.a.p == null || this.a.C)) {
                            this.a.p.setRotateDegree(KSYTextureView.F);
                        }
                        if (this.a.mCurrentState != KSYTextureView.K) {
                            this.a.setRotateDegree(i2);
                            break;
                        }
                        this.a.setRotateDegree(this.a.v);
                        break;
                    case IMediaPlayer.MEDIA_INFO_HARDWARE_DECODE /*41000*/:
                        this.a.C = true;
                        break;
                    case IMediaPlayer.MEDIA_INFO_SOFTWARE_DECODE /*41001*/:
                        this.a.C = false;
                        break;
                    case IMediaPlayer.MEDIA_INFO_RELOADED /*50001*/:
                        this.a.n.setVisibility(KSYTextureView.J);
                        this.a.z = this.a.A = this.a.B = true;
                        this.a.b = KSYTextureView.F;
                        if (this.a.x) {
                            this.a.mCurrentState = KSYTextureView.I;
                        } else {
                            this.a.mCurrentState = KSYTextureView.L;
                        }
                        if (this.a.o != null) {
                            this.a.o.setEnabled(true);
                            if (!this.a.x) {
                                this.a.o.onPause();
                                break;
                            }
                            this.a.o.onStart();
                            break;
                        }
                        break;
                }
                if (this.a.R != null) {
                    this.a.R.onInfo(iMediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.j = new OnSeekCompleteListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (this.a.Q != null) {
                    this.a.Q.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.k = new OnLogEventListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onLogEvent(IMediaPlayer iMediaPlayer, String str) {
                if (this.a.U != null) {
                    this.a.U.onLogEvent(iMediaPlayer, str);
                }
            }
        };
        this.l = new OnMessageListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onMessage(IMediaPlayer iMediaPlayer, String str, String str2, double d) {
                if (this.a.V != null) {
                    this.a.V.onMessage(iMediaPlayer, str, str2, d);
                }
            }
        };
        this.m = new OnTimedTextListener() {
            final /* synthetic */ KSYTextureView a;

            {
                this.a = r1;
            }

            public void onTimedText(IMediaPlayer iMediaPlayer, String str) {
                if (this.a.W != null) {
                    this.a.W.onTimedText(iMediaPlayer, str);
                }
            }
        };
        a(context);
        b(context);
    }

    private void a(Context context) {
        LayoutParams layoutParams = new LayoutParams(E, E);
        layoutParams.gravity = 17;
        this.n = new a(this, context);
        this.n.setLayoutParams(layoutParams);
        this.n.setSurfaceTextureListener(this);
        addView(this.n);
        if (getResources().getConfiguration().orientation == H) {
            if (this.n != null) {
                this.n.b(false);
            }
        } else if (getResources().getConfiguration().orientation == G && this.n != null) {
            this.n.b(true);
        }
        this.u = F;
        this.t = F;
        this.s = F;
        this.r = F;
        this.B = false;
        this.A = false;
        this.z = false;
        this.x = true;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    private void b(Context context) {
        this.p = new Builder(context).build();
        this.p.setOnPreparedListener(this.e);
        this.p.setOnVideoSizeChangedListener(this.d);
        this.p.setOnCompletionListener(this.f);
        this.p.setOnErrorListener(this.g);
        this.p.setOnBufferingUpdateListener(this.h);
        this.p.setOnInfoListener(this.i);
        this.p.setOnSeekCompleteListener(this.j);
        this.p.setOnLogEventListener(this.k);
        this.p.setOnMessageListener(this.l);
        this.p.setOnTimedTextListener(this.m);
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (getResources().getConfiguration().orientation == H) {
            if (this.n != null) {
                this.n.b(false);
            }
        } else if (getResources().getConfiguration().orientation == G && this.n != null) {
            this.n.b(true);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.r == 0 || this.s == 0) {
            super.onMeasure(i, i2);
        } else if (this.n == null) {
            super.onMeasure(i, i2);
        } else {
            measureChildren(i, i2);
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            int size2 = MeasureSpec.getSize(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                mode = size;
            } else {
                if (mode == 1073741824) {
                    mode = this.n.c();
                    if ((this.v / 90) % H != 0) {
                        mode = this.n.b();
                    }
                    if (mode > size2) {
                        mode = size;
                    }
                } else if (mode2 == 1073741824) {
                    mode = this.n.b();
                    if ((this.v / 90) % H != 0) {
                        mode = this.n.c();
                    }
                    if (mode > size) {
                        mode = size;
                    }
                } else {
                    mode2 = this.n.b();
                    mode = this.n.c();
                    if ((this.v / 90) % H != 0) {
                        mode = this.n.b();
                        mode2 = this.n.c();
                    }
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
        }
    }

    public void setMediaController(IMediaController iMediaController) {
        if (this.o != null) {
            this.o.hide();
        }
        this.o = iMediaController;
        a();
    }

    private void a() {
        if (this.p != null && this.o != null) {
            this.o.setMediaPlayer(this);
            this.o.setAnchorView(getParent() instanceof View ? (View) getParent() : this);
            this.o.setEnabled(false);
            this.o.hide();
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.q != null && isComeBackFromShare()) {
            this.q.release();
            this.q = surfaceTexture;
        }
        if (this.q == null) {
            this.q = surfaceTexture;
        }
        if (this.p != null) {
            this.p.setSurface(new Surface(this.q));
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.n != null) {
            this.n.b(this.D);
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.o != null) {
            this.o.hide();
        }
        return this.q == null;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.O = onPreparedListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.N = onCompletionListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.P = onErrorListener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.S = onBufferingUpdateListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.Q = onSeekCompleteListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.R = onInfoListener;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.T = onVideoSizeChangedListener;
    }

    public void setOnLogEventListener(OnLogEventListener onLogEventListener) {
        this.U = onLogEventListener;
    }

    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.V = onMessageListener;
    }

    public void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
        this.W = onTimedTextListener;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (b() && this.o != null) {
            c();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (b() && this.o != null) {
            c();
        }
        return false;
    }

    private boolean b() {
        return this.p != null;
    }

    private void c() {
        if (this.o.isShowing()) {
            this.o.hide();
        } else {
            this.o.show();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = (i == J || i == 24 || i == 25 || i == 164 || i == 82 || i == K || i == L) ? false : true;
        if (b() && z && this.o != null) {
            if (i == 79 || i == 85) {
                if (this.p.isPlaying()) {
                    pause();
                    this.o.show();
                    return true;
                }
                start();
                this.o.hide();
                return true;
            } else if (i == 126) {
                if (this.p.isPlaying()) {
                    return true;
                }
                start();
                this.o.hide();
                return true;
            } else if (i != 86 && i != 127) {
                c();
            } else if (!this.p.isPlaying()) {
                return true;
            } else {
                pause();
                this.o.show();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void setDataSource(Context context, Uri uri) throws IOException {
        if (this.p != null) {
            this.p.setDataSource(context, uri);
        }
    }

    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException {
        if (this.p != null) {
            this.p.setDataSource(context, uri, (Map) map);
        }
    }

    public void setDataSource(String str) throws IOException {
        if (this.p != null) {
            this.p.setDataSource(str);
        }
    }

    public void setDataSource(String str, Map<String, String> map) throws IOException {
        if (this.p != null) {
            this.p.setDataSource(str, (Map) map);
        }
    }

    @TargetApi(13)
    public void setDataSource(FileDescriptor fileDescriptor) throws IOException {
        if (this.p != null) {
            this.p.setDataSource(fileDescriptor);
        }
    }

    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.p != null) {
            this.p.setDataSource(fileDescriptor, j, j2);
        }
    }

    public void setDataSource(List<String> list, Map<String, String> map) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.p != null) {
            this.p.setDataSource((List) list, (Map) map);
        }
    }

    public void prepareAsync() {
        if (this.p != null) {
            this.p.prepareAsync();
            this.mCurrentState = G;
        }
    }

    public void setScreenOnWhilePlaying(boolean z) {
        if (this.p != null) {
            this.p.setScreenOnWhilePlaying(z);
        }
    }

    public void stop() {
        if (this.p != null) {
            this.p.stop();
        }
        this.mCurrentState = M;
        this.w = false;
        this.z = false;
        this.B = false;
        this.B = false;
    }

    public void release() {
        if (this.p != null) {
            this.p.release();
            this.p = null;
        }
        this.mCurrentState = F;
        this.q = null;
    }

    private void d() {
        this.v = F;
        this.a = null;
        this.w = false;
        this.y = false;
        this.D = G;
        this.u = F;
        this.t = F;
        this.s = F;
        this.r = F;
        this.B = false;
        this.A = false;
        this.z = false;
        this.x = true;
        this.mCurrentState = F;
        if (this.n != null) {
            this.n.a(0.0f, 0.0f);
            this.n.a((int) F, (int) F);
            this.n.b((int) F, (int) F);
        }
        if (!(this.q == null || this.p == null)) {
            this.p.setSurface(new Surface(this.q));
        }
        if (this.o != null) {
            this.o.setEnabled(false);
        }
    }

    public void reset() {
        if (this.p != null) {
            this.p.reset();
            d();
        }
    }

    public boolean isPlayable() {
        if (this.p != null) {
            return this.p.isPlayable();
        }
        return false;
    }

    public void start() {
        if (this.p != null) {
            this.p.start();
        }
        this.w = false;
        this.mCurrentState = I;
        if (this.o != null) {
            this.o.onStart();
        }
    }

    public void pause() {
        if (this.p != null) {
            this.p.pause();
        }
        this.w = true;
        this.mCurrentState = J;
        if (this.o != null) {
            this.o.onPause();
        }
    }

    public long getDuration() {
        if (this.p != null) {
            return this.p.getDuration();
        }
        return 0;
    }

    public long getCurrentPosition() {
        if (this.p != null) {
            return this.p.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(long j) {
        if (this.p != null) {
            this.p.seekTo(j);
        }
    }

    public void seekTo(long j, boolean z) {
        if (this.p != null) {
            this.p.seekTo(j, z);
        }
    }

    public boolean isPlaying() {
        if (this.p != null) {
            return this.p.isPlaying();
        }
        return false;
    }

    public MediaInfo getMediaInfo() {
        if (this.p != null) {
            if (this.a == null) {
                this.a = this.p.getMediaInfo();
            }
            return this.a;
        }
        this.a = null;
        return this.a;
    }

    public int getBufferPercentage() {
        if (this.p != null) {
            return this.b;
        }
        return F;
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
        return this.r;
    }

    public int getVideoHeight() {
        return this.s;
    }

    public void reload(String str, boolean z) {
        this.y = false;
        this.B = false;
        this.A = false;
        this.z = false;
        this.w = false;
        this.b = F;
        this.mCurrentState = K;
        if (this.p != null) {
            this.p.reload(str, z);
        }
        if (this.o != null) {
            this.o.setEnabled(false);
        }
    }

    public void reload(String str, boolean z, KSYReloadMode kSYReloadMode) {
        this.y = false;
        this.B = false;
        this.A = false;
        this.z = false;
        this.w = false;
        this.b = F;
        this.mCurrentState = K;
        if (this.o != null) {
            this.o.setEnabled(false);
        }
        if (this.p != null) {
            this.p.reload(str, z, kSYReloadMode);
        }
    }

    public int getAudioSessionId() {
        if (this.p != null) {
            return this.p.getAudioSessionId();
        }
        return E;
    }

    public void runInBackground(boolean z) {
        if (!(this.p == null || this.w || z)) {
            this.p.pause();
        }
        if (this.n != null) {
            this.n.setVisibility(J);
        }
    }

    public void runInForeground() {
        if (!(this.n == null || this.n.isAvailable() || this.q == null)) {
            this.n.setSurfaceTexture(this.q);
        }
        setComeBackFromShare(false);
        if (this.n != null) {
            this.n.setVisibility(F);
        }
    }

    public boolean isComeBackFromShare() {
        return this.y;
    }

    public void setComeBackFromShare(boolean z) {
        this.y = z;
    }

    public void setVideoScalingMode(int i) {
        if (this.n != null) {
            this.D = i;
            this.n.b(i);
        }
    }

    public void setVideoScaleRatio(float f, float f2, float f3) {
        if (this.n != null && f >= 0.25f && f <= 100.0f) {
            this.n.a(f, f2, f3);
        }
    }

    public void setPlayableRanges(long j, long j2) {
        if (this.p != null) {
            this.p.setPlayableRanges(j, j2);
        }
    }

    public float getVideoScaleRatio() {
        if (this.n != null) {
            return this.n.a();
        }
        return 1.0f;
    }

    public boolean setRotateDegree(int i) {
        if (i % 90 != 0) {
            return false;
        }
        this.v = i;
        if (this.n != null) {
            this.n.a(-i);
        }
        return true;
    }

    public void setRotation(float f) {
        setRotateDegree((int) f);
    }

    public void setDecodeMode(KSYDecodeMode kSYDecodeMode) {
        if (this.p != null) {
            this.p.setDecodeMode(kSYDecodeMode);
        }
    }

    public void setMirror(boolean z) {
        if (this.n != null) {
            this.n.a(z);
        }
    }

    public void setVideoOffset(float f, float f2) {
        if (this.n != null) {
            this.n.a(f, f2);
        }
    }

    public void moveVideo(float f, float f2) {
        if (this.n != null) {
            this.n.b(f, f2);
        }
    }

    public void setDeinterlaceMode(KSYDeinterlaceMode kSYDeinterlaceMode) {
        if (this.p != null) {
            this.p.setDeinterlaceMode(kSYDeinterlaceMode);
        }
    }

    private Bitmap a(IMediaPlayer iMediaPlayer) {
        if (this.n == null) {
            return null;
        }
        int videoHeight;
        int i;
        if (iMediaPlayer != null) {
            int videoWidth = iMediaPlayer.getVideoWidth();
            videoHeight = iMediaPlayer.getVideoHeight();
            i = videoWidth;
        } else {
            videoHeight = F;
            i = F;
        }
        if (videoHeight == 0 || i == 0) {
            return null;
        }
        Bitmap bitmap = this.n.getBitmap();
        if (bitmap == null) {
            return null;
        }
        videoWidth = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i2 = i / H;
        int i3 = videoHeight / H;
        float f = ((float) i) / ((float) videoWidth);
        float f2 = ((float) videoHeight) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2, (float) i2, (float) i3);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, F, F, videoWidth, height, matrix, false);
        if (createBitmap.equals(bitmap)) {
            return createBitmap;
        }
        bitmap.recycle();
        return createBitmap;
    }

    public String getVersion() {
        if (this.p == null) {
            return "N/A";
        }
        KSYMediaPlayer kSYMediaPlayer = this.p;
        return KSYMediaPlayer.getVersion();
    }

    public void shouldAutoPlay(boolean z) {
        if (this.p != null) {
            this.p.shouldAutoPlay(z);
            this.x = z;
        }
    }

    public String getDataSource() {
        if (this.p != null) {
            return this.p.getDataSource();
        }
        return null;
    }

    public void setWakeMode(Context context, int i) {
        if (this.p != null) {
            this.p.setWakeMode(context, i);
        }
    }

    public KSYTrackInfo[] getTrackInfo() {
        if (this.p != null) {
            return this.p.getTrackInfo();
        }
        return null;
    }

    public int getSelectedTrack(int i) {
        if (this.p != null) {
            return this.p.getSelectedTrack(i);
        }
        return F;
    }

    public void selectTrack(int i) {
        if (this.p != null) {
            this.p.selectTrack(i);
        }
    }

    public void deselectTrack(int i) {
        if (this.p != null) {
            this.p.deselectTrack(i);
        }
    }

    public int getVideoSarNum() {
        if (this.p != null) {
            return this.p.getVideoSarNum();
        }
        return F;
    }

    public int getVideoSarDen() {
        if (this.p != null) {
            return this.p.getVideoSarDen();
        }
        return F;
    }

    public void setLooping(boolean z) {
        if (this.p != null) {
            this.p.setLooping(z);
        }
    }

    public boolean isLooping() {
        if (this.p != null) {
            return this.p.isLooping();
        }
        return false;
    }

    public void softReset() {
        if (this.p != null) {
            this.p.softReset();
            d();
        }
    }

    public void setVideoRenderingState(int i) {
        if (this.p != null) {
            this.p.setVideoRenderingState(i);
        }
    }

    public void setSpeed(float f) {
        if (this.p != null) {
            this.p.setSpeed(f);
        }
    }

    public float getSpeed() {
        if (this.p != null) {
            return this.p.getSpeed();
        }
        return 1.0f;
    }

    public int getVideoDecoder() {
        if (this.p != null) {
            return this.p.getVideoDecoder();
        }
        return F;
    }

    public float getVideoOutputFramesPerSecond() {
        if (this.p != null) {
            return this.p.getVideoOutputFramesPerSecond();
        }
        return 0.0f;
    }

    public float getVideoDecodeFramesPerSecond() {
        if (this.p != null) {
            return this.p.getVideoDecodeFramesPerSecond();
        }
        return 0.0f;
    }

    public long getDecodedDataSize() {
        if (this.p != null) {
            return this.p.getDecodedDataSize();
        }
        return 0;
    }

    public long getDownloadDataSize() {
        if (this.p != null) {
            return this.p.getDownloadDataSize();
        }
        return 0;
    }

    public String getServerAddress() {
        if (this.p != null) {
            return this.p.getServerAddress();
        }
        return "N/A";
    }

    public int bufferEmptyCount() {
        if (this.p != null) {
            return this.p.bufferEmptyCount();
        }
        return F;
    }

    public float bufferEmptyDuration() {
        if (this.p != null) {
            return this.p.bufferEmptyDuration();
        }
        return 0.0f;
    }

    public long getVideoCachedDuration() {
        if (this.p != null) {
            return this.p.getVideoCachedDuration();
        }
        return 0;
    }

    public long getAudioCachedDuration() {
        if (this.p != null) {
            return this.p.getAudioCachedDuration();
        }
        return 0;
    }

    public long getVideoCachedBytes() {
        if (this.p != null) {
            return this.p.getVideoCachedBytes();
        }
        return 0;
    }

    public long getAudioCachedBytes() {
        if (this.p != null) {
            return this.p.getAudioCachedBytes();
        }
        return 0;
    }

    public long getVideoCachedPackets() {
        if (this.p != null) {
            return this.p.getVideoCachedPackets();
        }
        return 0;
    }

    public long getAudioCachedPackets() {
        if (this.p != null) {
            return this.p.getAudioCachedPackets();
        }
        return 0;
    }

    public long getCurrentPts() {
        if (this.p != null) {
            return this.p.getCurrentPts();
        }
        return 0;
    }

    public long getStreamStartTime() {
        if (this.p != null) {
            return this.p.getStreamStartTime();
        }
        return 0;
    }

    public void setVolume(float f, float f2) {
        if (this.p != null) {
            this.p.setVolume(f, f2);
        }
    }

    public Bundle getMediaMeta() {
        if (this.p != null) {
            return this.p.getMediaMeta();
        }
        return null;
    }

    public void setTimeout(int i, int i2) {
        if (this.p != null) {
            this.p.setTimeout(i, i2);
        }
    }

    public void addTimedTextSource(String str) {
        if (this.p != null) {
            this.p.addTimedTextSource(str);
        }
    }

    public void setBufferSize(int i) {
        if (this.p != null) {
            this.p.setBufferSize(i);
        }
    }

    public KSYQosInfo getStreamQosInfo() {
        if (this.p != null) {
            return this.p.getStreamQosInfo();
        }
        return null;
    }

    public void setBufferTimeMax(float f) {
        if (this.p != null) {
            this.p.setBufferTimeMax(f);
        }
    }

    public float getBufferTimeMax() {
        if (this.p != null) {
            return this.p.getBufferTimeMax();
        }
        return 0.0f;
    }

    public Bitmap getScreenShot() {
        if (this.p != null) {
            return a(this.p);
        }
        return null;
    }

    public void setPlayerMute(int i) {
        if (this.p != null) {
            this.p.setPlayerMute(i);
        }
    }

    public void setOnAudioPCMAvailableListener(OnAudioPCMListener onAudioPCMListener) {
        if (this.p != null) {
            this.p.setOnAudioPCMAvailableListener(onAudioPCMListener);
        }
    }

    public void setVideoRawDataListener(OnVideoRawDataListener onVideoRawDataListener) {
        if (this.p != null) {
            this.p.setVideoRawDataListener(onVideoRawDataListener);
        }
    }

    public void addVideoRawBuffer(byte[] bArr) {
        if (this.p != null) {
            this.p.addVideoRawBuffer(bArr);
        }
    }

    public void setOption(int i, String str, String str2) {
        if (this.p != null) {
            this.p.setOption(i, str, str2);
        }
    }

    public void setOption(int i, String str, long j) {
        if (this.p != null) {
            this.p.setOption(i, str, j);
        }
    }

    public String getLocalDnsIP() {
        if (this.p != null) {
            this.p.getLocalDnsIP();
        }
        return "N/A";
    }

    public String getClientIP() {
        if (this.p != null) {
            this.p.getClientIP();
        }
        return "N/A";
    }
}
