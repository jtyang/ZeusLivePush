package com.ksyun.media.streamer.util.gles;

import android.opengl.GLES20;
import android.util.Log;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FboManager {
    private static final String a = "FboManager";
    private static boolean b;
    private static boolean c;
    private static FboManager d;
    private HashMap<String, List<Integer>> e;
    private HashMap<Integer, a> f;
    private int g;

    private class a {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        final /* synthetic */ FboManager e;
        private int f;

        public a(FboManager fboManager, int i, int i2, int i3, int i4) {
            this.e = fboManager;
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.f = 0;
        }

        public synchronized boolean a() {
            return this.f != 0;
        }

        public synchronized void b() {
            this.f++;
        }

        public synchronized boolean c() {
            boolean z;
            if (this.f == 0) {
                z = false;
            } else {
                this.f--;
                z = true;
            }
            return z;
        }
    }

    static {
        b = true;
        c = false;
    }

    public FboManager() {
        this.e = new HashMap();
        this.f = new HashMap();
    }

    public synchronized void init() {
        if (b) {
            Log.d(a, "init");
        }
        this.f.clear();
        this.e.clear();
        this.g = 0;
    }

    public synchronized int getTextureAndLock(int i, int i2) {
        int i3;
        a aVar;
        String a = a(i, i2);
        List list = (List) this.e.get(a);
        List list2;
        if (list == null) {
            LinkedList linkedList = new LinkedList();
            this.e.put(a, linkedList);
            list2 = linkedList;
        } else {
            list2 = list;
        }
        for (Integer intValue : r2) {
            int intValue2 = intValue.intValue();
            aVar = (a) this.f.get(Integer.valueOf(intValue2));
            if (!aVar.a()) {
                aVar.b();
                if (c) {
                    Log.d(a, "reuse and lock " + intValue2);
                }
                i3 = intValue2;
            }
        }
        aVar = b(i, i2);
        this.g++;
        if (b) {
            Log.d(a, "Create and lock a new fbo: " + aVar.d + " " + i + "x" + i2 + " total:" + this.g);
        }
        aVar.b();
        this.f.put(Integer.valueOf(aVar.d), aVar);
        r2.add(Integer.valueOf(aVar.d));
        i3 = aVar.d;
        return i3;
    }

    public synchronized int getFramebuffer(int i) {
        int i2;
        a aVar = (a) this.f.get(Integer.valueOf(i));
        if (aVar != null) {
            i2 = aVar.c;
        } else {
            i2 = -1;
        }
        return i2;
    }

    public synchronized boolean lock(int i) {
        boolean z;
        a aVar = (a) this.f.get(Integer.valueOf(i));
        if (c && aVar != null) {
            Log.d(a, "lock: " + i);
        }
        if (aVar == null) {
            z = false;
        } else {
            aVar.b();
            z = true;
        }
        return z;
    }

    public synchronized boolean unlock(int i) {
        boolean z;
        a aVar = (a) this.f.get(Integer.valueOf(i));
        if (c && aVar != null) {
            Log.d(a, "unlock: " + i);
        }
        z = aVar != null && aVar.c();
        return z;
    }

    public synchronized int getTextureCount() {
        return this.g;
    }

    public synchronized void remove(int i) {
        a aVar = (a) this.f.get(Integer.valueOf(i));
        if (aVar != null) {
            this.g--;
            this.f.remove(Integer.valueOf(i));
            ((List) this.e.get(a(aVar.a, aVar.b))).remove(i);
            a(aVar);
        }
    }

    public synchronized void remove() {
        if (b) {
            Log.d(a, "remove all");
        }
        this.f.clear();
        this.e.clear();
        this.g = 0;
        int[] iArr = new int[this.f.size()];
        int[] iArr2 = new int[this.f.size()];
        for (a aVar : this.f.values()) {
            iArr[0] = aVar.d;
            iArr2[0] = aVar.c;
        }
        GLES20.glDeleteTextures(iArr.length, iArr, 0);
        GLES20.glDeleteFramebuffers(iArr2.length, iArr2, 0);
    }

    private String a(int i, int i2) {
        return Integer.toString(i) + "x" + Integer.toString(i2);
    }

    private a b(int i, int i2) {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        GLES20.glGenFramebuffers(1, iArr, 0);
        GLES20.glGenTextures(1, iArr2, 0);
        GLES20.glBindTexture(3553, iArr2[0]);
        GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glBindFramebuffer(36160, iArr[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, iArr2[0], 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        return new a(this, i, i2, iArr[0], iArr2[0]);
    }

    private void a(a aVar) {
        int[] iArr = new int[]{aVar.c};
        GLES20.glDeleteTextures(1, new int[]{aVar.d}, 0);
        GLES20.glDeleteFramebuffers(1, iArr, 0);
    }
}
