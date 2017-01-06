package com.yjt.zeuslivepush.camera;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public interface AutoFocusCallback {
    void onAutoFocus(boolean var1, CameraDevice var2);

    void onAutoFocusMoving(boolean var1, CameraDevice var2);
}
