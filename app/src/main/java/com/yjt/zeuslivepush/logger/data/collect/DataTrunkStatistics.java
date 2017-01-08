package com.yjt.zeuslivepush.logger.data.collect;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class DataTrunkStatistics implements Runnable {
    private DataCollectTrunk mDataCollectTrunk;
    private int mInterval;
    private Thread mStatisticThread;
    private int mLastVideoCaptureFrameCount;
    private int mLastAudioCaptureFrameCount;
    private long mLastOutputDataSize;
    private int mLastAudioOutputFrameCount;
    private int mLastVideoOutputFrameCount;
    private int mLastVideoEncoderFrameCount;
    private int mLastAudioEncoderFrameCount;
    private long mCurrVideoFramePts;
    private long mCurrAudioFramePts;
    private long mLastTime;
    private int mLastVideoEncodeDataSize;

    public DataTrunkStatistics(int interval) {
        this.mInterval = interval;
        this.mDataCollectTrunk = DataCollectTrunk.getInstance();
    }

    public void start() {
        this.mStatisticThread = new Thread(this);
        this.mStatisticThread.start();
    }

    public void cancel() {
        if(this.mStatisticThread != null && !this.mStatisticThread.isInterrupted()) {
            this.mStatisticThread.interrupt();

            try {
                this.mStatisticThread.join();
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            } finally {
                this.mStatisticThread = null;
            }
        }

    }

    public void run() {
        while(true) {
            try {
                long e = System.currentTimeMillis();
                long diffTime = (e - this.mLastTime) / 1000L;
                this.mLastTime = e;
                long currVideoPts = this.mDataCollectTrunk.getCurrVideoFramePts();
                long currAudioPts = this.mDataCollectTrunk.getCurrAudioFramePts();
                if(this.mCurrAudioFramePts != currAudioPts || this.mCurrVideoFramePts != currVideoPts) {
                    this.mCurrVideoFramePts = currVideoPts;
                    this.mCurrAudioFramePts = currAudioPts;
                    this.mDataCollectTrunk.putLongValue(5, this.mCurrVideoFramePts - this.mCurrAudioFramePts);
                }

                this.mLastAudioOutputFrameCount = this.calculatePSValue(6, this.mLastAudioOutputFrameCount, this.mDataCollectTrunk.getInt(4102), diffTime);
                this.mLastVideoOutputFrameCount = this.calculatePSValue(7, this.mLastVideoOutputFrameCount, this.mDataCollectTrunk.getInt(4103), diffTime);
                this.mLastVideoCaptureFrameCount = this.calculatePSValue(1, this.mLastVideoCaptureFrameCount, this.mDataCollectTrunk.getVideoCaptureFrameCount(), diffTime);
                this.mLastOutputDataSize = this.calculatePSValue(4, this.mLastOutputDataSize, this.mDataCollectTrunk.getOutputDataCount(), diffTime);
                this.mLastAudioEncoderFrameCount = this.calculatePSValue(2, this.mLastAudioEncoderFrameCount, this.mDataCollectTrunk.getAudioEncoderFrameCount(), diffTime);
                this.mLastVideoEncoderFrameCount = this.calculatePSValue(3, this.mLastVideoEncoderFrameCount, this.mDataCollectTrunk.getVideoEncoderFrameCount(), diffTime);
                this.mLastVideoEncodeDataSize = this.calculatePSValue(20, this.mLastVideoEncodeDataSize, this.mDataCollectTrunk.getInt(4110), diffTime);
                Thread.sleep((long)this.mInterval);
            } catch (InterruptedException var9) {
                return;
            }
        }
    }

    private int calculatePSValue(int resKey, int lastValue, int currValue, long diffTime) {
        this.mDataCollectTrunk.putIntValue(resKey, (int)((long)(currValue - lastValue) * diffTime));
        return currValue;
    }

    private long calculatePSValue(int resKey, long lastValue, long currValue, long diffTime) {
        this.mDataCollectTrunk.putLongValue(resKey, (currValue - lastValue) * diffTime);
        return currValue;
    }

    public void reset() {
        this.mLastVideoCaptureFrameCount = 0;
        this.mLastAudioCaptureFrameCount = 0;
        this.mLastOutputDataSize = 0L;
        this.mLastAudioOutputFrameCount = 0;
        this.mLastVideoOutputFrameCount = 0;
        this.mLastVideoEncoderFrameCount = 0;
        this.mLastAudioEncoderFrameCount = 0;
        this.mCurrVideoFramePts = 0L;
        this.mCurrAudioFramePts = 0L;
        this.mLastTime = 0L;
        this.mLastVideoEncodeDataSize = 0;
    }
}
