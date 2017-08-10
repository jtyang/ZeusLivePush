package com.ksyun.media.streamer.util.https;

public class KsyHttpClient implements HttpResponseListener {
    private KsyHttpConnection mConnection;
    private long mHandler;
    HttpResponseListener mListener;

    private native void _NativeResponse(long j, int i, String str);

    public KsyHttpClient() {
        this.mConnection = null;
        this.mConnection = new KsyHttpConnection();
        this.mConnection.setListener(this);
    }

    public void setHandler(long j) {
        this.mHandler = j;
    }

    public void setTimeout(int i) {
        this.mConnection.setTimeout(i);
    }

    public void setConnectTimetout(int i) {
        this.mConnection.setConnectTimeout(i);
    }

    public void setRequestProperty(String str, String str2) {
        if (this.mConnection != null) {
            this.mConnection.setRequestProperty(str, str2);
        }
    }

    public void setListener(HttpResponseListener httpResponseListener) {
        this.mListener = httpResponseListener;
    }

    public void performHttpRequest(String str) {
        if (this.mConnection != null) {
            this.mConnection.performHttpRequest(str);
        }
    }

    public void performHttpsRequest(String str) {
        if (this.mConnection != null) {
            this.mConnection.performHttpsRequest(str);
        }
    }

    public void onHttpResponse(KsyHttpResponse ksyHttpResponse) {
        if (this.mListener != null) {
            this.mListener.onHttpResponse(ksyHttpResponse);
        }
    }

    public void cancelHttpRequest() {
        if (this.mConnection != null) {
            this.mConnection.cancelHttpRequest();
        }
    }
}
