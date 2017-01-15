package com.yjt.zeuslivepush.live;

import com.yjt.zeuslivepush.utils.MapUtil;

import java.util.Map;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class LiveRecordConfig {
    static final int DEFAULT_INITIAL_VIDEO_BITRATE = 600000;
    static final int DEFAULT_MAX_VIDEO_BITRATE = 800000;
    static final int DEFAULT_MIN_VIDEO_BITRATE = 400000;
    static final int DEFAULT_BEST_VIDEO_BITRATE = 600000;
    static final int DEFAULT_FRAME_RATE = 20;
    static final int DEFAULT_I_FRAME_INTERNAL = 2;
    static final int DEFAULT_AUDIO_SAMPLE_RATE = 44100;
    static final int DEFAULT_AUDIO_BITRATE = 32000;
    static final int DEFAULT_MAX_ZOOM_LEVEL = 3;
    static final int DEFAULT_EXPOSURE_COMPENSATION = -1;
    static final int DEFAULT_DISPLAY_ROTATION = 0;
    int mInitVideoBitRate = 600000;
    int mMaxVideoBitRate = 800000;
    int mMinVideoBitrate = 400000;
    int mBestVideoBitrate = 600000;
    int mFrameRate = 20;
    int mColorFormat = 0;
    int mIFrameInternal = 2;
    int mAudioSampleRate = 44100;
    int mAudioBitrate = 32000;
    int mOutputResolution = 1;
    int mMaxZoomLevel = 3;
    int mCameraInitialFacing = 0;
    private Resolution mOutputSize;
    private String mOutputUrl;
    int mExposureCompensation = -1;
    int mDisplayRotation = 0;
    private Resolution mScreenAspectRatio;
//    DQLiveWatermark mWatermark = null;

    LiveRecordConfig() {
    }

    public int getInitVideoBitRate() {
        return this.mInitVideoBitRate;
    }

    public int getMaxVideoBitRate() {
        return this.mMaxVideoBitRate;
    }

    public int getMinVideoBitrate() {
        return this.mMinVideoBitrate;
    }

    public int getFrameRate() {
        return this.mFrameRate;
    }

    public int getColorFormat() {
        return this.mColorFormat;
    }

    public int getIFrameInternal() {
        return this.mIFrameInternal;
    }

    public int getAudioSampleRate() {
        return this.mAudioSampleRate;
    }

    public int getAudioBitrate() {
        return this.mAudioBitrate;
    }

    public int getOutputResolution() {
        return this.mOutputResolution;
    }

    public int getMaxZoomLevel() {
        return this.mMaxZoomLevel;
    }

    public int getCameraInitialFacing() {
        return this.mCameraInitialFacing;
    }

    public Resolution getOutputSize() {
        return this.mOutputSize;
    }

    public String getOutputUrl() {
        return this.mOutputUrl;
    }

    public void setOutputUrl(String outputUrl) {
        this.mOutputUrl = outputUrl;
    }

    public int getExposureCompensation() {
        return this.mExposureCompensation;
    }

    public int getDisplayRotation() {
        return this.mDisplayRotation;
    }

    public int getBestVideoBitrate() {
        return this.mBestVideoBitrate;
    }

    public void setCameraInitialFacing(int cameraInitialFacing) {
        this.mCameraInitialFacing = cameraInitialFacing;
    }

//    public DQLiveWatermark getWatermark() {
//        return this.mWatermark;
//    }

    private static void swap(Resolution resolution) {
        if (resolution != null) {
            int tmp = resolution.mWidth;
            resolution.mWidth = resolution.mHeight;
            resolution.mHeight = tmp;
        }

    }

    static LiveRecordConfig newInstance(Map<String, Object> params, Resolution screenAspectRatio, int surfaceRotation) {
        if (params != null) {
            LiveRecordConfig config = (new LiveRecordConfig.Builder(surfaceRotation))
                    .screenAspectRatio(screenAspectRatio)
                    .audioBitrate(((Integer) MapUtil.parseMapValue(params, "audio-bitrate", Integer.valueOf(32000))).intValue())
                    .audioSampleRate(((Integer) MapUtil.parseMapValue(params, "sample-rate", Integer.valueOf('걄'))).intValue())
                    .cameraInitialFacing(((Integer) MapUtil.parseMapValue(params, "camera-facing", Integer.valueOf(0))).intValue())
                    .frameRate(((Integer) MapUtil.parseMapValue(params, "frame-rate", Integer.valueOf(20))).intValue())
                    .iFrameInternal(((Integer) MapUtil.parseMapValue(params, "i-frame-internal", Integer.valueOf(2))).intValue())
                    .initialVideoBitrate(((Integer) MapUtil.parseMapValue(params, "initial-video-bitrate", Integer.valueOf(600000))).intValue())
                    .maxVideoBitrate(((Integer) MapUtil.parseMapValue(params, "max-video-bitrate", Integer.valueOf(800000))).intValue())
                    .minVideoBitrate(((Integer) MapUtil.parseMapValue(params, "min-video-bitrate", Integer.valueOf(400000))).intValue())
                    .bestVideoBitrate(((Integer) MapUtil.parseMapValue(params, "best-bitrate", Integer.valueOf(600000))).intValue())
                    .outputResolution(((Integer) MapUtil.parseMapValue(params, "output-resolution", Integer.valueOf(1))).intValue())
                    .maxZoomLevel(((Integer) MapUtil.parseMapValue(params, "max-zoom-level", Integer.valueOf(3))).intValue())
                    .displayRotation(((Integer) MapUtil.parseMapValue(params, "display-rotation", Integer.valueOf(0))).intValue())
                    .exposureCompensation(((Integer) MapUtil.parseMapValue(params, "exposure-compensation", Integer.valueOf(-1))).intValue())
//                    .watermarkUrl((DQLiveWatermark) MapUtil.parseMapValue(params, "watermark", new DQLiveWatermark()))
                    .build();
            return config;
        } else {
            return (new LiveRecordConfig.Builder(surfaceRotation)).screenAspectRatio(screenAspectRatio).build();
        }
    }

    static class Builder {
        private int initialVideoBitrate = 600000;
        private int maxVideoBitrate = 800000;
        private int minVideoBitrate = 400000;
        private int frameRate = 20;
        private int iFrameInternal = 2;
        private int audioSampleRate = 44100;
        private int audioBitrate = 32000;
        private int outputResolution = 1;
        private int cameraInitialFacing = 0;
        private int maxZoomLevel = 3;
        private int exposureCompensation = -1;
        private Resolution screenAspectRatio = null;
        private int displayRotation = 0;
        private int bestVideoBitrate = 600000;
        private int surfaceRotation = 0;
//        private DQLiveWatermark watermark = null;

        Builder(int surfaceRotation) {
            this.surfaceRotation = surfaceRotation;
        }

        LiveRecordConfig build() {
            LiveRecordConfig config = new LiveRecordConfig();
            config.mInitVideoBitRate = this.initialVideoBitrate;
            config.mMaxVideoBitRate = this.maxVideoBitrate;
            config.mMinVideoBitrate = this.minVideoBitrate;
            config.mFrameRate = this.frameRate;
            config.mIFrameInternal = this.iFrameInternal;
            config.mAudioSampleRate = this.audioSampleRate;
            config.mAudioBitrate = this.audioBitrate;
            config.mOutputResolution = this.outputResolution;
            config.mCameraInitialFacing = this.cameraInitialFacing;
            config.mMaxZoomLevel = this.maxZoomLevel;
//            config.mWatermark = this.watermark;
            config.mBestVideoBitrate = this.bestVideoBitrate;
            switch (this.displayRotation) {
                case 90:
                case 270:
                    LiveRecordConfig.swap(this.screenAspectRatio);
                default:
                    config.mScreenAspectRatio = this.screenAspectRatio;
                    config.mExposureCompensation = this.exposureCompensation;
                    config.mDisplayRotation = this.displayRotation;
                    config.mOutputSize = this.parseSize(this.outputResolution);
                    return config;
            }
        }

        LiveRecordConfig.Builder initialVideoBitrate(int bitrate) {
            this.initialVideoBitrate = bitrate;
            return this;
        }

        LiveRecordConfig.Builder maxVideoBitrate(int bitrate) {
            this.maxVideoBitrate = bitrate;
            return this;
        }

        LiveRecordConfig.Builder minVideoBitrate(int bitrate) {
            this.minVideoBitrate = bitrate;
            return this;
        }

        LiveRecordConfig.Builder frameRate(int frameRate) {
            this.frameRate = frameRate;
            return this;
        }

        LiveRecordConfig.Builder iFrameInternal(int internal) {
            this.iFrameInternal = internal;
            return this;
        }

        LiveRecordConfig.Builder audioSampleRate(int sampleRate) {
            this.audioSampleRate = sampleRate;
            return this;
        }

        LiveRecordConfig.Builder audioBitrate(int bitrate) {
            this.audioBitrate = bitrate;
            return this;
        }

        LiveRecordConfig.Builder outputResolution(int resolution) {
            this.outputResolution = resolution;
            return this;
        }

        LiveRecordConfig.Builder cameraInitialFacing(int facing) {
            this.cameraInitialFacing = facing;
            return this;
        }

        LiveRecordConfig.Builder maxZoomLevel(int maxZoomLevel) {
            this.maxZoomLevel = maxZoomLevel;
            return this;
        }

//        LiveRecordConfig.Builder watermarkUrl(DQLiveWatermark watermark) {
//            this.watermark = watermark;
//            return this;
//        }

        LiveRecordConfig.Builder screenAspectRatio(Resolution aspectRatio) {
            this.screenAspectRatio = aspectRatio;
            return this;
        }

        LiveRecordConfig.Builder exposureCompensation(int exposureCompensation) {
            this.exposureCompensation = exposureCompensation;
            return this;
        }

        LiveRecordConfig.Builder displayRotation(int displayRotation) {
            this.displayRotation = displayRotation;
            return this;
        }

        LiveRecordConfig.Builder bestVideoBitrate(int bitrate) {
            this.bestVideoBitrate = bitrate;
            return this;
        }

        private Resolution parseSize(int outputResolution) {
            switch (outputResolution) {
                case 0:
                    return this.calculateResolution(240);
                case 1:
                    return this.calculateResolution(360);
                case 2:
                    return this.calculateResolution(480);
                case 3:
                    return this.calculateResolution(540);
                case 4:
                    return this.calculateResolution(720);
                case 5:
                    return this.calculateResolution(1080);
                default:
                    return this.calculateResolution(360);
            }
        }

        private Resolution calculateResolution(int sp) {
            if (this.displayRotation == 90 || this.displayRotation == 270) {
                switch (this.surfaceRotation) {
                    case 0:
                    case 2:
                        LiveRecordConfig.swap(this.screenAspectRatio);
                }
            }

            int mod = sp % 16;
            if (mod != 0) {
                sp -= mod;
            }

            Resolution resolution = new Resolution();
            resolution.setWidth(sp);
            int height = (int) ((float) sp * 1.0F * (float) this.screenAspectRatio.getHeight() / (float) this.screenAspectRatio.getWidth());
            mod = height % 16;
            if (mod != 0) {
                height -= mod;
            }

            resolution.setHeight(height);
            switch (this.displayRotation) {
                case 90:
                case 270:
                    LiveRecordConfig.swap(resolution);
                default:
                    return resolution;
            }
        }
    }
}
