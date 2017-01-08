package com.yjt.zeuslivepush.utils;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class MathUtil {

    public static int signum(long value) {
        return value == 0L ? 0 : (value < 0L ? -1 : 1);
    }

    public static float clamp(float f, float min, float max) {
        return Math.min(Math.max(f, min), max);
    }

    public static double gaussian(double sigma, double x) {
        return 1.0D / (sigma * Math.sqrt(6.283185307179586D)) * Math.exp(-(x * x) / (2.0D * sigma * sigma));
    }

    public static int align2(int x, int align) {
        return x + align - 1 & ~(align - 1);
    }
}
