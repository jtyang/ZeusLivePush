package com.yjt.zeuslivepush.live;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class Resolution {
    int mWidth;
    int mHeight;

    public Resolution() {
    }

    public Resolution(int width, int height) {
        this.mHeight = height;
        this.mWidth = width;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public int size() {
        return this.mWidth * this.mHeight;
    }
}
