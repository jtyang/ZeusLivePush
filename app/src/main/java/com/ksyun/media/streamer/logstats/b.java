package com.ksyun.media.streamer.logstats;

/* compiled from: StatsTimer */
public class b {
    private static b o;
    public volatile int a;
    public volatile int b;
    public volatile int c;
    public volatile int d;
    public volatile long e;
    public volatile int f;
    public volatile int g;
    public volatile int h;
    public volatile int i;
    public volatile float j;
    public volatile long k;
    public volatile long l;
    public volatile int m;
    public volatile long n;

    public static b a() {
        b bVar;
        synchronized (b.class) {
            if (o == null) {
                o = new b();
            }
            bVar = o;
        }
        return bVar;
    }

    public static void b() {
        o = null;
    }

    public void c() {
        synchronized (b.class) {
            this.a++;
        }
    }

    public void d() {
        this.a = 0;
    }

    public void e() {
        synchronized (b.class) {
            this.b++;
        }
    }

    public int f() {
        return this.b;
    }

    public void g() {
        this.b = 0;
    }

    public void h() {
        synchronized (b.class) {
            this.c++;
        }
    }

    public int i() {
        return this.c;
    }

    public void j() {
        this.e = 0;
    }

    public int k() {
        return this.d;
    }

    public void a(int i) {
        synchronized (b.class) {
            this.d = i;
        }
    }

    private void w() {
        this.d = 0;
    }

    public long l() {
        return this.e;
    }

    public void a(long j) {
        synchronized (b.class) {
            this.e = j;
        }
    }

    private void x() {
        this.e = 0;
    }

    public int m() {
        return this.f;
    }

    public void b(int i) {
        synchronized (b.class) {
            this.f = i;
        }
    }

    public int n() {
        return this.g;
    }

    public void c(int i) {
        synchronized (b.class) {
            this.g = i;
        }
    }

    public int o() {
        return this.h;
    }

    public void d(int i) {
        synchronized (b.class) {
            this.h = i;
        }
    }

    private void y() {
        this.f = 0;
    }

    private void z() {
        this.g = 0;
    }

    private void A() {
        this.h = 0;
    }

    public int p() {
        return this.i;
    }

    public void e(int i) {
        synchronized (b.class) {
            this.i = i;
        }
    }

    private void B() {
        this.i = 0;
    }

    public void a(float f) {
        this.j = f;
    }

    public float q() {
        return this.j;
    }

    private void C() {
        this.j = 0.0f;
    }

    public void b(long j) {
        this.k = j;
    }

    public long r() {
        return this.k;
    }

    private void D() {
        this.k = 0;
    }

    public void c(long j) {
        this.l = j;
    }

    public long s() {
        return this.l;
    }

    private void E() {
        this.l = 0;
    }

    public void f(int i) {
        this.m = i;
    }

    public int t() {
        return this.m;
    }

    private void F() {
        this.m = 0;
    }

    public long u() {
        return this.n;
    }

    public void d(long j) {
        this.n = j;
    }

    private void G() {
        this.n = 0;
    }

    public void v() {
        g();
        d();
        w();
        x();
        j();
        B();
        y();
        z();
        A();
        C();
        D();
        E();
        F();
        G();
    }
}
