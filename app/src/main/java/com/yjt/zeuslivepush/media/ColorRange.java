package com.yjt.zeuslivepush.media;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public enum ColorRange {
    UNSPECIFIED(0),
    MPEG(1),
    JPEG(2);

    public final int value;

    private ColorRange(int v) {
        this.value = v;
    }
}
