package com.yjt.zeuslivepush.gl;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public final class Sampler {
    public final int wrapS;
    public final int wrapT;
    public final int magFilter;
    public final int minFilter;

    public Sampler(int wrap_s, int wrap_t, int mag_filter, int min_filter) {
        this.wrapS = wrap_s;
        this.wrapT = wrap_t;
        this.magFilter = mag_filter;
        this.minFilter = min_filter;
    }
}
