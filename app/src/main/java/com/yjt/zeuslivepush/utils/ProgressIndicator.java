package com.yjt.zeuslivepush.utils;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public interface ProgressIndicator {
    long getDuration();

    void setDurationLimit(long var1);

    void setProgressListener(ProgressIndicator.ProgressListener var1);

    public interface ProgressListener {
        void onProgress(long var1);

        void onLimitReached();
    }
}
