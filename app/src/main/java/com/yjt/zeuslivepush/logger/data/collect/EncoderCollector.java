package com.yjt.zeuslivepush.logger.data.collect;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class EncoderCollector {
    public EncoderCollector() {
    }

    public void addVideoEncoderFrameCount(int value) {
        DataCollectTrunk.getInstance().addIntValue(4098, value);
    }

    public void addAudioEncoderFrameCount(int value) {
        DataCollectTrunk.getInstance().addIntValue(4097, value);
    }

    public void addVideoEncoderDataSize(int value) {
        DataCollectTrunk.getInstance().addIntValue(4110, value);
    }

    public void setVideoEncoderCostTime(long value) {
        DataCollectTrunk.getInstance().putLongValue(19, value);
    }
}
