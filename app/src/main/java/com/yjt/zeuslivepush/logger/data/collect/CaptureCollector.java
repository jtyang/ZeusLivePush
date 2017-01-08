package com.yjt.zeuslivepush.logger.data.collect;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class CaptureCollector {
    public CaptureCollector() {
    }

    public void addVideoCaptureFrameCount(int value) {
        DataCollectTrunk.getInstance().addIntValue(4096, value);
    }
}
