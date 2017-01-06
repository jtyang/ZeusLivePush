package com.yjt.zeuslivepush.camera;

import java.io.Serializable;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class Size implements Serializable {
    public int width;
    public int height;

    public Size(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public String toString() {
        return String.format("%dx%d", new Object[]{Integer.valueOf(this.width), Integer.valueOf(this.height)});
    }
}
