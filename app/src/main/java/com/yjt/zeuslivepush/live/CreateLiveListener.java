package com.yjt.zeuslivepush.live;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/6
 */
public interface CreateLiveListener {
    void onCreateLiveError(int var1, String var2);

    void onCreateLiveSuccess(String var1, String var2, String var3);
}
