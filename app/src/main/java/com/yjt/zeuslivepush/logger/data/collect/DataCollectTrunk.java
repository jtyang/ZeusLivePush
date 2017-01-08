package com.yjt.zeuslivepush.logger.data.collect;

import android.annotation.TargetApi;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
@TargetApi(18)
public class DataCollectTrunk {

    public static final int VIDEO_CAPTURE_FRAME_COUNT = 4096;
    public static final int AUDIO_ENCODER_FRAME_COUNT = 4097;
    public static final int VIDEO_ENCODER_FRAME_COUNT = 4098;
    public static final int OUTPUT_DATA_COUNT = 4099;
    public static final int LAST_AUDIO_FRAME_PTS = 4100;
    public static final int LAST_VIDEO_FRAME_PTS = 4101;
    public static final int AUDIO_OUTPUT_FRAME_COUNT = 4102;
    public static final int VIDEO_OUTPUT_FRAME_COUNT = 4103;
    public static final int CURR_AUDIO_FRAME_PTS = 4104;
    public static final int CURR_VIDEO_FRAME_PTS = 4105;
    public static final int VIDEO_OUTPUT_DATA_SIZE = 4106;
    public static final int VIDEO_BUFFER_COUNT = 4107;
    public static final int AUDIO_OUTPUT_DATA_SIZE = 4108;
    public static final int VIDEO_OUTPUT_DELAY = 4109;
    public static final int CURR_VIDEO_ENCODE_DATA_SIZE = 4110;
    public static final int VIDEO_CAPTURE_FPS = 1;
    public static final int AUDIO_ENCODER_FPS = 2;
    public static final int VIDEO_ENCODER_FPS = 3;
    public static final int OUTPUT_BITRATE = 4;
    public static final int AV_OUTPUT_DIFF = 5;
    public static final int AUDIO_OUTPUT_FPS = 6;
    public static final int VIDEO_OUTPUT_FPS = 7;
    public static final int STREAM_PUBLISH_TIME = 8;
    public static final int STREAM_SERVER_IP = 9;
    public static final int VIDEO_DELAY_DURATION = 10;
    public static final int AUDIO_DELAY_DURATION = 11;
    public static final int VIDEO_CACHE_FRAME_CNT = 12;
    public static final int AUDIO_CACHE_FRAME_CNT = 13;
    public static final int VIDEO_CACHE_BYTE_SIZE = 14;
    public static final int AUDIO_CACHE_BYTE_SIZE = 15;
    public static final int VIDEO_FRAME_DISCARD_CNT = 16;
    public static final int AUDIO_FRAME_DISCARD_CNT = 17;
    public static final int CUR_VIDEO_BEAUTY_DURATION = 18;
    public static final int CUR_VIDEO_ENCODER_DURATION = 19;
    public static final int CUR_VIDEO_ENCODE_BITRATE = 20;
    private static final DataCollectTrunk mInstance = new DataCollectTrunk();
    private int mVideoCaptureFrameCount;
    private int mAudioEncoderFrameCount;
    private int mVideoEncoderFrameCount;
    private long mOutputDataCount;
    private long mLastAudioFramePts;
    private long mLastVideoFramePts;
    private long mCurrAudioFramePts;
    private long mCurrVideoFramePts;
    private long mVideoEncoderStartTime;
    SparseIntArray mIntReportValues = new SparseIntArray();
    SparseLongArray mLongReportValues = new SparseLongArray();

    private DataCollectTrunk() {
    }

    public static DataCollectTrunk getInstance() {
        return mInstance;
    }

    public void putIntValue(int key, int value) {
        switch(key) {
            case 4096:
                this.mVideoCaptureFrameCount = value;
                break;
            case 4097:
                this.mAudioEncoderFrameCount = value;
                break;
            case 4098:
                this.mVideoEncoderFrameCount = value;
                break;
            default:
                this.mIntReportValues.put(key, value);
        }

    }

    public void putLongValue(int key, long value) {
        switch(key) {
            case 4099:
                this.mOutputDataCount = value;
                break;
            case 4104:
                this.mCurrAudioFramePts = value;
                this.mLongReportValues.put(11, this.mCurrAudioFramePts - this.mLastAudioFramePts);
                this.mLastAudioFramePts = this.mCurrAudioFramePts;
                break;
            case 4105:
                this.mCurrVideoFramePts = (long)((int)value);
                this.mLongReportValues.put(10, this.mCurrVideoFramePts - this.mLastVideoFramePts);
                this.mLastVideoFramePts = this.mCurrVideoFramePts;
                break;
            default:
                this.mLongReportValues.put(key, value);
        }

    }

    public void addIntValue(int key, int value) {
        switch(key) {
            case 4096:
                this.mVideoCaptureFrameCount += value;
                break;
            case 4097:
                this.mAudioEncoderFrameCount += value;
                break;
            case 4098:
                this.mVideoEncoderFrameCount += value;
                break;
            default:
                if(this.mIntReportValues.indexOfKey(key) >= 0) {
                    int preValue = this.mIntReportValues.get(key);
                    this.mIntReportValues.put(key, preValue + value);
                } else {
                    this.mIntReportValues.put(key, value);
                }
        }

    }

    public void addLongValue(int key, long value) {
        switch(key) {
            case 4099:
                this.mOutputDataCount += value;
                break;
            default:
                if(this.mLongReportValues.indexOfKey(key) >= 0) {
                    long preValue = this.mLongReportValues.get(key);
                    this.mLongReportValues.put(key, preValue + value);
                } else {
                    this.mLongReportValues.put(key, value);
                }
        }

    }

    public void reset() {
        this.mIntReportValues.clear();
        this.mLongReportValues.clear();
        this.mLastVideoFramePts = 0L;
        this.mLastAudioFramePts = 0L;
        this.mAudioEncoderFrameCount = 0;
        this.mVideoCaptureFrameCount = 0;
        this.mVideoEncoderFrameCount = 0;
        this.mOutputDataCount = 0L;
        this.mCurrVideoFramePts = 0L;
        this.mCurrAudioFramePts = 0L;
    }

    public synchronized int getVideoCaptureFrameCount() {
        return this.mVideoCaptureFrameCount;
    }

    public synchronized int getAudioEncoderFrameCount() {
        return this.mAudioEncoderFrameCount;
    }

    public synchronized int getVideoEncoderFrameCount() {
        return this.mVideoEncoderFrameCount;
    }

    public synchronized long getOutputDataCount() {
        return this.mOutputDataCount;
    }

    public synchronized long getLastAudioFramePts() {
        return this.mLastAudioFramePts;
    }

    public synchronized long getLastVideoFramePts() {
        return this.mLastVideoFramePts;
    }

    public void setVideoCaptureFrameCount(int videoCaptureFrameCount) {
        this.mVideoCaptureFrameCount = videoCaptureFrameCount;
    }

    public void setAudioEncoderFrameCount(int audioEncoderFrameCount) {
        this.mAudioEncoderFrameCount = audioEncoderFrameCount;
    }

    public void setVideoEncoderFrameCount(int videoEncoderFrameCount) {
        this.mVideoEncoderFrameCount = videoEncoderFrameCount;
    }

    public void setOutputDataCount(int outputDataCount) {
        this.mOutputDataCount = (long)outputDataCount;
    }

    public void setLastAudioFramePts(int lastAudioFramePts) {
        this.mLastAudioFramePts = (long)lastAudioFramePts;
    }

    public void setLastVideoFramePts(int lastVideoFramePts) {
        this.mLastVideoFramePts = (long)lastVideoFramePts;
    }

    public long getVideoEncoderStartTime() {
        return this.mVideoEncoderStartTime;
    }

    public void setVideoEncoderStartTime(long videoEncoderStartTime) {
        this.mVideoEncoderStartTime = videoEncoderStartTime;
    }

    public long getCurrAudioFramePts() {
        return this.mCurrAudioFramePts;
    }

    public void setCurrAudioFramePts(long currAudioFramePts) {
        this.mCurrAudioFramePts = currAudioFramePts;
    }

    public long getCurrVideoFramePts() {
        return this.mCurrVideoFramePts;
    }

    public void setCurrVideoFramePts(long currVideoFramePts) {
        this.mCurrVideoFramePts = currVideoFramePts;
    }

    public int getInt(int key) {
        if(this.mIntReportValues.indexOfKey(key) >= 0) {
            try {
                return this.mIntReportValues.get(key);
            } catch (Exception var3) {
                throw new RuntimeException("This value is not a int type");
            }
        } else {
            return 0;
        }
    }

    public double getDouble(int key) {
        return 0.0D;
    }

    public long getLong(int key) {
        if(this.mLongReportValues.indexOfKey(key) >= 0) {
            try {
                return this.mLongReportValues.get(key);
            } catch (Exception var3) {
                throw new RuntimeException("This value is not a long type");
            }
        } else {
            return 0L;
        }
    }

    public float getFloat(int key) {
        return 0.0F;
    }

    public boolean getBoolean(int key) {
        return false;
    }

    public String getString(int key) {
        return null;
    }

    public Object getValue(int key) {
        return null;
    }
}
