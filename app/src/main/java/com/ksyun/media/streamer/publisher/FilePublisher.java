package com.ksyun.media.streamer.publisher;

import android.util.Log;
import com.ksyun.media.streamer.framework.AVBufFrame;
import com.ksyun.media.streamer.framework.AVFrameBase;
import com.ksyun.media.streamer.framework.ImgBufFrame;
import com.ksyun.media.streamer.logstats.StatsLogReport;

public class FilePublisher extends Publisher {
    public static final int FILE_PUBLISHER_ERROR_CLOSE_FAILED = -4003;
    public static final int FILE_PUBLISHER_ERROR_OPEN_FAILED = -4001;
    public static final int FILE_PUBLISHER_ERROR_UNKNOWN = -4000;
    public static final int FILE_PUBLISHER_ERROR_WRITE_FAILED = -4002;
    public static final int FILE_PUBLISHER_FORMAT_NOT_SUPPORTED = -4004;
    public static final int INFO_OPENED = 1;
    public static final int INFO_STOPED = 4;
    private static final String a = "FilePublisher";
    private static final boolean b = false;
    private boolean c;

    public FilePublisher() {
        super("FilePub");
        this.c = false;
        setAutoWork(true);
        setUseSyncMode(true);
    }

    public void setForceVideoFrameFirst(boolean z) {
        this.c = z;
    }

    public void startRecording(String str) {
        super.start(str);
    }

    public void release() {
        stop();
        super.release();
    }

    protected boolean IsAddExtraForVideoKeyFrame() {
        return false;
    }

    protected void handleAVFrame(AVBufFrame aVBufFrame) {
        Object obj = INFO_OPENED;
        if (!this.c) {
            super.handleAVFrame(aVBufFrame);
        } else if (this.mState.get() == 2 && aVBufFrame != null) {
            boolean z = aVBufFrame instanceof ImgBufFrame;
            Object obj2 = (aVBufFrame.flags & 2) != 0 ? INFO_OPENED : null;
            Object obj3;
            if ((aVBufFrame.flags & INFO_STOPED) != 0) {
                obj3 = INFO_OPENED;
            } else {
                obj3 = null;
            }
            if (obj2 == null && r3 == null && !z && !isAudioOnly() && (this.mInitDts == Long.MIN_VALUE || aVBufFrame.dts < this.mInitDts)) {
                return;
            }
            if (obj2 != null || r3 != null || !z || this.mInitDts == Long.MIN_VALUE || aVBufFrame.pts >= this.mInitDts) {
                obj2 = (aVBufFrame.flags & AVFrameBase.FLAG_DUMMY_VIDEO_FRAME) != 0 ? INFO_OPENED : null;
                if ((aVBufFrame.flags & INFO_OPENED) == 0) {
                    obj = null;
                }
                if (this.mInitDts == Long.MIN_VALUE && z && !isAudioOnly() && !(obj2 == null && r1 == null)) {
                    Log.d(a, "Force init dts to " + aVBufFrame.pts);
                    this.mInitDts = aVBufFrame.pts;
                    if (obj2 != null) {
                        return;
                    }
                }
                super.handleAVFrame(aVBufFrame);
            }
        }
    }

    protected void postInfo(int i, long j) {
        super.postInfo(i, j);
        switch (i) {
            case INFO_OPENED /*1*/:
                StatsLogReport.getInstance().startRecordSuccess();
            case INFO_STOPED /*4*/:
                StatsLogReport.getInstance().stopRecord();
            default:
        }
    }
}
