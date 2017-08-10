package com.ksyun.media.player.https;

public class KsyHttpResponse {
    StringBuilder data;
    private int responseCode;

    KsyHttpResponse() {
        this.responseCode = 0;
        this.data = null;
        this.responseCode = -1;
        this.data = new StringBuilder();
    }

    public void restResponse() {
        this.responseCode = 0;
        this.data.setLength(0);
    }

    public void setResponseCode(int i) {
        this.responseCode = i;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public void appendData(String str) {
        this.data.append(str);
    }

    public String getData() {
        return this.data.toString();
    }
}
