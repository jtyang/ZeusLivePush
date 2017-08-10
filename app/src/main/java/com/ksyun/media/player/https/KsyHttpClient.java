package com.ksyun.media.player.https;

public class KsyHttpClient implements HttpResponseListener {
    private a a;
    private long b;

    private native void _NativeResponse(long j, int i, String str);

    KsyHttpClient() {
        this.a = null;
        this.a = new a();
        this.a.a((HttpResponseListener) this);
    }

    public void setHandler(long j) {
        this.b = j;
    }

    public void setTimeout(int i) {
        this.a.a(i);
    }

    public void setConnectTimetout(int i) {
        this.a.b(i);
    }

    public void setRequestProperty(String str, String str2) {
        if (this.a != null) {
            this.a.a(str, str2);
        }
    }

    public void performHttpRequest(String str) {
        if (this.a != null) {
            this.a.a(str);
        }
    }

    public void performHttpsRequest(String str) {
        if (this.a != null) {
            this.a.b(str);
        }
    }

    public void onHttpResponse(KsyHttpResponse ksyHttpResponse) {
        _NativeResponse(this.b, ksyHttpResponse.getResponseCode(), ksyHttpResponse.getData());
    }

    public void cancelHttpRequest() {
        if (this.a != null) {
            this.a.a();
        }
    }
}
