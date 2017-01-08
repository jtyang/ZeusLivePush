package com.yjt.zeuslivepush.camera;


import com.yjt.zeuslivepush.buffer.ByteArrayHolder;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public interface PreviewQueue {
    void setBuffersGeometry(int var1, int var2, int var3);

    void setOnBufferAvailableListener(PreviewQueue.OnBufferAvailableListener var1);

    ByteArrayHolder dequeueBuffer();

    void enqueueBuffer(ByteArrayHolder var1);

    void cancelBuffer(ByteArrayHolder var1);

    public interface OnBufferAvailableListener {
        void onBufferAvailable(PreviewQueue var1);
    }
}
