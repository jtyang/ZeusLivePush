package com.ksyun.media.streamer.util.https;

public class KsyHttpResponse {
    StringBuilder a;
    private int b;

    KsyHttpResponse() {
        this.b = 0;
        this.a = null;
        this.b = -1;
        this.a = new StringBuilder();
    }

    public void restResponse() {
        this.b = 0;
        this.a.setLength(0);
    }

    public void setResponseCode(int i) {
        this.b = i;
    }

    public int getResponseCode() {
        return this.b;
    }

    public void appendData(String str) {
        this.a.append(str);
    }

    public String getData() {
        return this.a.toString();
    }
}
