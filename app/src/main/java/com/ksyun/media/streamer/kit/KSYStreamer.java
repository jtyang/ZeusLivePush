package com.ksyun.media.streamer.kit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.streamer.capture.AudioCapture;
import com.ksyun.media.streamer.capture.AudioCapture.OnAudioCaptureListener;
import com.ksyun.media.streamer.capture.AudioPlayerCapture;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.capture.CameraCapture.OnCameraCaptureListener;
import com.ksyun.media.streamer.capture.ImageCapture;
import com.ksyun.media.streamer.capture.WaterMarkCapture;
import com.ksyun.media.streamer.encoder.AVCodecAudioEncoder;
import com.ksyun.media.streamer.encoder.AudioEncodeFormat;
import com.ksyun.media.streamer.encoder.AudioEncoderMgt;
import com.ksyun.media.streamer.encoder.Encoder;
import com.ksyun.media.streamer.encoder.Encoder.EncoderListener;
import com.ksyun.media.streamer.encoder.MediaCodecAudioEncoder;
import com.ksyun.media.streamer.encoder.VideoEncodeFormat;
import com.ksyun.media.streamer.encoder.VideoEncoderMgt;
import com.ksyun.media.streamer.filter.audio.AudioFilterMgt;
import com.ksyun.media.streamer.filter.audio.AudioMixer;
import com.ksyun.media.streamer.filter.audio.AudioPreview;
import com.ksyun.media.streamer.filter.audio.AudioResampleFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.filter.imgtex.ImgTexMixer;
import com.ksyun.media.streamer.filter.imgtex.ImgTexPreview;
import com.ksyun.media.streamer.filter.imgtex.ImgTexScaleFilter;
import com.ksyun.media.streamer.framework.AudioBufFormat;
import com.ksyun.media.streamer.logstats.StatsConstant;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.ksyun.media.streamer.logstats.StatsLogReport.OnLogEventListener;
import com.ksyun.media.streamer.publisher.FilePublisher;
import com.ksyun.media.streamer.publisher.Publisher.PubListener;
import com.ksyun.media.streamer.publisher.PublisherMgt;
import com.ksyun.media.streamer.publisher.RtmpPublisher;
import com.ksyun.media.streamer.publisher.RtmpPublisher.BwEstConfig;
import com.ksyun.media.streamer.util.BitmapLoader;
import com.ksyun.media.streamer.util.device.DeviceInfoTools;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GLRender.GLRenderListener;
import com.ksyun.media.streamer.util.gles.GLRender.ScreenShotListener;
import com.ksyun.media.streamer.util.gles.TexTransformUtil;
import com.ksyun.media.streamer.util.gles.d;
import java.util.concurrent.atomic.AtomicInteger;

public class KSYStreamer {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_PREVIEW_HEIGHT = 1280;
    private static final int DEFAULT_PREVIEW_WIDTH = 720;
    private static final String TAG = "KSYStreamer";
    protected com.ksyun.media.streamer.filter.audio.a mAudioAPMFilterMgt;
    protected int mAudioBitrate;
    protected AudioCapture mAudioCapture;
    protected int mAudioCaptureType;
    protected int mAudioChannels;
    protected AudioEncoderMgt mAudioEncoderMgt;
    protected AudioFilterMgt mAudioFilterMgt;
    protected AudioMixer mAudioMixer;
    protected AudioPlayerCapture mAudioPlayerCapture;
    protected AudioPreview mAudioPreview;
    protected int mAudioProfile;
    protected AudioResampleFilter mAudioResampleFilter;
    protected int mAudioSampleRate;
    protected AtomicInteger mAudioUsingCount;
    protected boolean mAutoAdjustVideoBitrate;
    protected boolean mAutoRestart;
    protected int mAutoRestartInterval;
    protected boolean mBluetoothPluged;
    protected int mBwEstStrategy;
    protected CameraCapture mCameraCapture;
    protected int mCameraFacing;
    protected Context mContext;
    protected boolean mDelayedStartCameraPreview;
    protected boolean mDelayedStartRecording;
    protected boolean mDelayedStartStreaming;
    protected boolean mEnableAudioLowDelay;
    protected boolean mEnableAudioMix;
    protected boolean mEnableDebugLog;
    protected boolean mEnableRepeatLastFrame;
    private boolean mEnableStreamStatModule;
    protected int mEncodeProfile;
    protected int mEncodeScene;
    protected FilePublisher mFilePublisher;
    protected boolean mFrontCameraMirror;
    protected GLRender mGLRender;
    private GLRenderListener mGLRenderListener;
    protected boolean mHeadSetPluged;
    private a mHeadSetReceiver;
    protected float mIFrameInterval;
    protected int mIdxAudioBgm;
    protected int mIdxAudioMic;
    protected int mIdxCamera;
    protected int mIdxWmLogo;
    protected int mIdxWmTime;
    protected ImageCapture mImageCapture;
    protected ImgTexFilterMgt mImgTexFilterMgt;
    protected ImgTexMixer mImgTexMixer;
    protected ImgTexPreview mImgTexPreview;
    protected ImgTexMixer mImgTexPreviewMixer;
    protected ImgTexScaleFilter mImgTexScaleFilter;
    protected int mInitVideoBitrate;
    protected boolean mIsAudioOnly;
    protected boolean mIsAudioPreviewing;
    protected boolean mIsCaptureStarted;
    protected boolean mIsEnableAudioPreview;
    protected volatile boolean mIsFileRecording;
    protected boolean mIsRecording;
    private Handler mMainHandler;
    protected int mMaxVideoBitrate;
    protected int mMinVideoBitrate;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    protected float mPreviewFps;
    protected int mPreviewHeight;
    protected int mPreviewResolution;
    protected int mPreviewWidth;
    protected PublisherMgt mPublisherMgt;
    protected String mRecordUri;
    private final Object mReleaseObject;
    protected int mRotateDegrees;
    protected RtmpPublisher mRtmpPublisher;
    protected int mScreenRenderHeight;
    protected int mScreenRenderWidth;
    protected float mTargetFps;
    protected int mTargetHeight;
    protected int mTargetResolution;
    protected int mTargetWidth;
    protected String mUri;
    protected boolean mUseDummyAudioCapture;
    protected int mVideoCodecId;
    protected VideoEncoderMgt mVideoEncoderMgt;
    protected WaterMarkCapture mWaterMarkCapture;

    public interface OnErrorListener {
        void onError(int i, int i2, int i3);
    }

    public interface OnInfoListener {
        void onInfo(int i, int i2, int i3);
    }

    private class a extends BroadcastReceiver {
        final /* synthetic */ KSYStreamer a;

        private a(KSYStreamer kSYStreamer) {
            this.a = kSYStreamer;
        }

        public void onReceive(Context context, Intent intent) {
            boolean z = KSYStreamer.DEBUG;
            String action = intent.getAction();
            int intExtra;
            if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.d(KSYStreamer.TAG, "bluetooth state:" + intExtra);
                if (intExtra == 2) {
                    Log.d(KSYStreamer.TAG, "bluetooth Headset is plugged");
                    this.a.mBluetoothPluged = true;
                } else if (intExtra == 0) {
                    Log.d(KSYStreamer.TAG, "bluetooth Headset is unplugged");
                    this.a.mBluetoothPluged = KSYStreamer.DEBUG;
                }
            } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 10);
                if (intExtra == 12) {
                    Log.d(KSYStreamer.TAG, "bluetooth Headset is plugged");
                    this.a.mBluetoothPluged = true;
                } else if (intExtra == 10) {
                    Log.d(KSYStreamer.TAG, "bluetooth Headset is unplugged");
                    this.a.mBluetoothPluged = KSYStreamer.DEBUG;
                }
            } else if (action.equals("android.intent.action.HEADSET_PLUG")) {
                switch (intent.getIntExtra("state", -1)) {
                    case GLRender.VIEW_TYPE_NONE /*0*/:
                        Log.d(KSYStreamer.TAG, "Headset is unplugged");
                        this.a.mHeadSetPluged = KSYStreamer.DEBUG;
                        break;
                    case d.a /*1*/:
                        Log.d(KSYStreamer.TAG, "Headset is plugged");
                        this.a.mHeadSetPluged = true;
                        break;
                    default:
                        Log.d(KSYStreamer.TAG, "I have no idea what the headset state is");
                        break;
                }
            } else if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED") && intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10) == 10) {
                Log.d(KSYStreamer.TAG, "bluetooth Headset is unplugged");
                this.a.mBluetoothPluged = KSYStreamer.DEBUG;
            }
            if (this.a.mIsEnableAudioPreview) {
                KSYStreamer kSYStreamer = this.a;
                if (this.a.mHeadSetPluged || this.a.mBluetoothPluged || this.a.mAudioCapture.getEnableLatencyTest()) {
                    z = true;
                }
                kSYStreamer.setEnableAudioPreviewInternal(z);
            }
        }
    }

    public KSYStreamer(Context context) {
        this.mAudioCaptureType = 1;
        this.mIdxCamera = 0;
        this.mIdxWmLogo = 1;
        this.mIdxWmTime = 2;
        this.mIdxAudioMic = 0;
        this.mIdxAudioBgm = 1;
        this.mScreenRenderWidth = 0;
        this.mScreenRenderHeight = 0;
        this.mPreviewResolution = 0;
        this.mPreviewWidth = 0;
        this.mPreviewHeight = 0;
        this.mPreviewFps = 0.0f;
        this.mTargetResolution = 0;
        this.mTargetWidth = 0;
        this.mTargetHeight = 0;
        this.mTargetFps = 0.0f;
        this.mIFrameInterval = StreamerConstants.DEFAULT_IFRAME_INTERVAL;
        this.mVideoCodecId = 1;
        this.mEncodeScene = 1;
        this.mEncodeProfile = 3;
        this.mRotateDegrees = 0;
        this.mMaxVideoBitrate = StreamerConstants.DEFAULT_MAX_VIDEO_BITRATE;
        this.mInitVideoBitrate = StreamerConstants.DEFAULT_INIT_VIDEO_BITRATE;
        this.mMinVideoBitrate = StreamerConstants.DEFAILT_MIN_VIDEO_BITRATE;
        this.mAutoAdjustVideoBitrate = true;
        this.mBwEstStrategy = 0;
        this.mAudioBitrate = StreamerConstants.DEFAULT_AUDIO_BITRATE;
        this.mAudioSampleRate = StreamerConstants.DEFAULT_AUDIO_SAMPLE_RATE;
        this.mAudioChannels = 1;
        this.mAudioProfile = 4;
        this.mFrontCameraMirror = DEBUG;
        this.mEnableStreamStatModule = true;
        this.mCameraFacing = 1;
        this.mIsRecording = DEBUG;
        this.mIsFileRecording = DEBUG;
        this.mIsCaptureStarted = DEBUG;
        this.mIsAudioOnly = DEBUG;
        this.mIsAudioPreviewing = DEBUG;
        this.mIsEnableAudioPreview = DEBUG;
        this.mDelayedStartCameraPreview = DEBUG;
        this.mDelayedStartStreaming = DEBUG;
        this.mDelayedStartRecording = DEBUG;
        this.mEnableDebugLog = DEBUG;
        this.mEnableAudioMix = DEBUG;
        this.mEnableRepeatLastFrame = true;
        this.mUseDummyAudioCapture = DEBUG;
        this.mEnableAudioLowDelay = DEBUG;
        this.mAutoRestart = DEBUG;
        this.mAutoRestartInterval = 3000;
        this.mHeadSetPluged = DEBUG;
        this.mBluetoothPluged = DEBUG;
        this.mReleaseObject = new Object();
        this.mGLRenderListener = new GLRenderListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onReady() {
            }

            public void onSizeChanged(int i, int i2) {
                this.a.mScreenRenderWidth = i;
                this.a.mScreenRenderHeight = i2;
                this.a.mWaterMarkCapture.setPreviewSize(i, i2);
                this.a.setPreviewParams();
                if (this.a.mDelayedStartCameraPreview) {
                    this.a.mCameraCapture.start(this.a.mCameraFacing);
                    this.a.mDelayedStartCameraPreview = KSYStreamer.DEBUG;
                }
                if (this.a.mDelayedStartStreaming) {
                    this.a.startStream();
                    this.a.mDelayedStartStreaming = KSYStreamer.DEBUG;
                }
                if (this.a.mDelayedStartRecording) {
                    this.a.startRecord(this.a.mRecordUri);
                    this.a.mDelayedStartRecording = KSYStreamer.DEBUG;
                }
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        };
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null!");
        }
        this.mContext = context.getApplicationContext();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        initModules();
    }

    protected void initModules() {
        this.mGLRender = new GLRender();
        this.mWaterMarkCapture = new WaterMarkCapture(this.mGLRender);
        this.mImageCapture = new ImageCapture(this.mGLRender);
        this.mCameraCapture = new CameraCapture(this.mContext, this.mGLRender);
        this.mImgTexScaleFilter = new ImgTexScaleFilter(this.mGLRender);
        this.mImgTexFilterMgt = new ImgTexFilterMgt(this.mContext);
        this.mImgTexMixer = new ImgTexMixer(this.mGLRender);
        this.mImgTexMixer.setScalingMode(this.mIdxCamera, 2);
        this.mImgTexPreviewMixer = new ImgTexMixer(this.mGLRender);
        this.mImgTexPreviewMixer.setScalingMode(this.mIdxCamera, 2);
        this.mImgTexPreview = new ImgTexPreview();
        this.mCameraCapture.mImgTexSrcPin.connect(this.mImgTexScaleFilter.getSinkPin());
        this.mImgTexScaleFilter.getSrcPin().connect(this.mImgTexFilterMgt.getSinkPin());
        this.mImgTexFilterMgt.getSrcPin().connect(this.mImgTexMixer.getSinkPin(this.mIdxCamera));
        this.mWaterMarkCapture.mLogoTexSrcPin.connect(this.mImgTexMixer.getSinkPin(this.mIdxWmLogo));
        this.mWaterMarkCapture.mTimeTexSrcPin.connect(this.mImgTexMixer.getSinkPin(this.mIdxWmTime));
        this.mImgTexFilterMgt.getSrcPin().connect(this.mImgTexPreviewMixer.getSinkPin(this.mIdxCamera));
        this.mWaterMarkCapture.mLogoTexSrcPin.connect(this.mImgTexPreviewMixer.getSinkPin(this.mIdxWmLogo));
        this.mWaterMarkCapture.mTimeTexSrcPin.connect(this.mImgTexPreviewMixer.getSinkPin(this.mIdxWmTime));
        this.mImgTexPreviewMixer.getSrcPin().connect(this.mImgTexPreview.getSinkPin());
        this.mAudioPlayerCapture = new AudioPlayerCapture(this.mContext);
        this.mAudioCapture = new AudioCapture(this.mContext);
        this.mAudioCapture.setAudioCaptureType(this.mAudioCaptureType);
        this.mAudioFilterMgt = new AudioFilterMgt();
        this.mAudioPreview = new AudioPreview(this.mContext);
        this.mAudioResampleFilter = new AudioResampleFilter();
        this.mAudioMixer = new AudioMixer();
        this.mAudioAPMFilterMgt = new com.ksyun.media.streamer.filter.audio.a();
        this.mAudioCapture.getSrcPin().connect(this.mAudioFilterMgt.getSinkPin());
        this.mAudioFilterMgt.getSrcPin().connect(this.mAudioPreview.getSinkPin());
        this.mAudioPreview.getSrcPin().connect(this.mAudioResampleFilter.getSinkPin());
        this.mAudioResampleFilter.getSrcPin().connect(this.mAudioMixer.getSinkPin(this.mIdxAudioMic));
        if (this.mEnableAudioMix) {
            this.mAudioPlayerCapture.mSrcPin.connect(this.mAudioMixer.getSinkPin(this.mIdxAudioBgm));
        }
        this.mVideoEncoderMgt = new VideoEncoderMgt(this.mGLRender);
        this.mAudioEncoderMgt = new AudioEncoderMgt();
        this.mWaterMarkCapture.mLogoBufSrcPin.connect(this.mVideoEncoderMgt.getImgBufMixer().getSinkPin(this.mIdxWmLogo));
        this.mWaterMarkCapture.mTimeBufSrcPin.connect(this.mVideoEncoderMgt.getImgBufMixer().getSinkPin(this.mIdxWmTime));
        this.mImgTexMixer.getSrcPin().connect(this.mVideoEncoderMgt.getImgTexSinkPin());
        this.mCameraCapture.mImgBufSrcPin.connect(this.mVideoEncoderMgt.getImgBufSinkPin());
        this.mAudioMixer.getSrcPin().connect(this.mAudioEncoderMgt.getSinkPin());
        this.mRtmpPublisher = new RtmpPublisher();
        this.mFilePublisher = new FilePublisher();
        this.mFilePublisher.setForceVideoFrameFirst(true);
        this.mPublisherMgt = new PublisherMgt();
        this.mAudioEncoderMgt.getSrcPin().connect(this.mPublisherMgt.getAudioSink());
        this.mVideoEncoderMgt.getSrcPin().connect(this.mPublisherMgt.getVideoSink());
        this.mPublisherMgt.addPublisher(this.mRtmpPublisher);
        this.mGLRender.addListener(new GLRenderListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onReady() {
                this.a.mImgTexPreview.setEGL10Context(this.a.mGLRender.getEGL10Context());
            }

            public void onSizeChanged(int i, int i2) {
            }

            public void onDrawFrame() {
            }

            public void onReleased() {
            }
        });
        this.mAudioCapture.setAudioCaptureListener(new OnAudioCaptureListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onStatusChanged(int i) {
            }

            public void onError(int i) {
                int i2;
                Log.e(KSYStreamer.TAG, "AudioCapture error: " + i);
                switch (i) {
                    case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED /*-2003*/:
                        i2 = StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED;
                        break;
                    default:
                        i2 = StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN;
                        break;
                }
                if (this.a.mOnErrorListener != null) {
                    this.a.mOnErrorListener.onError(i2, 0, 0);
                }
            }
        });
        this.mCameraCapture.setOnCameraCaptureListener(new OnCameraCaptureListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onStarted() {
                Log.d(KSYStreamer.TAG, "CameraCapture ready");
                if (this.a.mOnInfoListener != null) {
                    this.a.mOnInfoListener.onInfo(StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD, 0, 0);
                }
            }

            public void onFacingChanged(int i) {
                this.a.mCameraFacing = i;
                this.a.updateFrontMirror();
                if (this.a.mOnInfoListener != null) {
                    this.a.mOnInfoListener.onInfo(StreamerConstants.KSY_STREAMER_CAMERA_FACEING_CHANGED, i, 0);
                }
            }

            public void onError(int i) {
                int i2;
                Log.e(KSYStreamer.TAG, "CameraCapture error: " + i);
                switch (i) {
                    case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED /*-2007*/:
                        i2 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED;
                        break;
                    case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED /*-2006*/:
                        i2 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED;
                        break;
                    case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED /*-2002*/:
                        i2 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED;
                        break;
                    default:
                        i2 = StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN;
                        break;
                }
                if (this.a.mOnErrorListener != null) {
                    this.a.mOnErrorListener.onError(i2, 0, 0);
                }
            }
        });
        EncoderListener anonymousClass4 = new EncoderListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onError(Encoder encoder, int i) {
                if (i != 0) {
                    this.a.stopStream();
                }
                int i2 = 1;
                if ((encoder instanceof MediaCodecAudioEncoder) || (encoder instanceof AVCodecAudioEncoder)) {
                    i2 = 0;
                }
                switch (i) {
                    case DeviceInfoTools.REQUEST_ERROR_PARSE_FILED /*-1002*/:
                        if (i2 == 0) {
                            i2 = StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED;
                            break;
                        } else {
                            i2 = StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED;
                            break;
                        }
                    default:
                        if (i2 == 0) {
                            i2 = RtmpPublisher.ERROR_CONNECT_FAILED;
                            break;
                        } else {
                            i2 = StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN;
                            break;
                        }
                }
                if (this.a.mOnErrorListener != null) {
                    this.a.mOnErrorListener.onError(i2, 0, 0);
                }
            }
        };
        this.mVideoEncoderMgt.setEncoderListener(anonymousClass4);
        this.mAudioEncoderMgt.setEncoderListener(anonymousClass4);
        this.mRtmpPublisher.setPubListener(new PubListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onInfo(int i, long j) {
                long min;
                switch (i) {
                    case d.a /*1*/:
                        if (this.a.mAudioEncoderMgt.getEncoder().isEncoding()) {
                            this.a.mRtmpPublisher.setAudioExtra(this.a.mAudioEncoderMgt.getEncoder().getExtra());
                        } else {
                            this.a.mAudioEncoderMgt.getEncoder().start();
                        }
                        if (this.a.mOnInfoListener != null) {
                            this.a.mOnInfoListener.onInfo(0, 0, 0);
                        }
                    case d.b /*2*/:
                        if (!this.a.mIsAudioOnly) {
                            if (this.a.mVideoEncoderMgt.getEncoder().isEncoding()) {
                                this.a.mRtmpPublisher.setVideoExtra(this.a.mVideoEncoderMgt.getEncoder().getExtra());
                                this.a.mVideoEncoderMgt.getEncoder().forceKeyFrame();
                                return;
                            }
                            this.a.mVideoEncoderMgt.start();
                        }
                    case RtmpPublisher.INFO_PACKET_SEND_SLOW /*100*/:
                        Log.i(KSYStreamer.TAG, "packet send slow, delayed " + j + "ms");
                        if (this.a.mOnInfoListener != null) {
                            this.a.mOnInfoListener.onInfo(StreamerConstants.KSY_STREAMER_FRAME_SEND_SLOW, (int) j, 0);
                        }
                    case RtmpPublisher.INFO_EST_BW_RAISE /*101*/:
                        if (!this.a.mIsAudioOnly && this.a.mAutoAdjustVideoBitrate) {
                            min = Math.min(j - ((long) this.a.mAudioBitrate), (long) this.a.mMaxVideoBitrate);
                            Log.d(KSYStreamer.TAG, "Raise video bitrate to " + min);
                            this.a.mVideoEncoderMgt.getEncoder().adjustBitrate((int) min);
                            if (this.a.mOnInfoListener != null) {
                                this.a.mOnInfoListener.onInfo(StreamerConstants.KSY_STREAMER_EST_BW_RAISE, (int) min, 0);
                            }
                        }
                    case RtmpPublisher.INFO_EST_BW_DROP /*102*/:
                        if (!this.a.mIsAudioOnly && this.a.mAutoAdjustVideoBitrate) {
                            min = Math.max(j - ((long) this.a.mAudioBitrate), (long) this.a.mMinVideoBitrate);
                            Log.d(KSYStreamer.TAG, "Drop video bitrate to " + min);
                            this.a.mVideoEncoderMgt.getEncoder().adjustBitrate((int) min);
                            if (this.a.mOnInfoListener != null) {
                                this.a.mOnInfoListener.onInfo(StreamerConstants.KSY_STREAMER_EST_BW_DROP, (int) min, 0);
                            }
                        }
                    default:
                }
            }

            public void onError(int i, long j) {
                int i2 = RtmpPublisher.ERROR_DNS_PARSE_FAILED;
                Log.e(KSYStreamer.TAG, "RtmpPub err=" + i);
                if (i != 0) {
                    this.a.stopStream();
                }
                if (this.a.mOnErrorListener != null) {
                    switch (i) {
                        case RtmpPublisher.ERROR_AV_ASYNC_ERROR /*-2004*/:
                            i2 = RtmpPublisher.ERROR_AV_ASYNC_ERROR;
                            break;
                        case RtmpPublisher.ERROR_CONNECT_BREAKED /*-1020*/:
                            i2 = StreamerConstants.KSY_STREAMER_ERROR_CONNECT_BREAKED;
                            break;
                        case RtmpPublisher.ERROR_CONNECT_FAILED /*-1011*/:
                            i2 = StreamerConstants.KSY_STREAMER_ERROR_CONNECT_FAILED;
                            break;
                        case RtmpPublisher.ERROR_DNS_PARSE_FAILED /*-1010*/:
                            i2 = StreamerConstants.KSY_STREAMER_ERROR_DNS_PARSE_FAILED;
                            break;
                    }
                    this.a.mOnErrorListener.onError(i2, (int) j, 0);
                    this.a.autoRestart();
                }
            }
        });
        this.mFilePublisher.setPubListener(new PubListener() {
            final /* synthetic */ KSYStreamer a;

            {
                this.a = r1;
            }

            public void onInfo(int i, long j) {
                Log.d(KSYStreamer.TAG, "file publisher info:" + i);
                switch (i) {
                    case d.a /*1*/:
                        if (this.a.mAudioEncoderMgt.getEncoder().isEncoding()) {
                            this.a.mFilePublisher.setAudioExtra(this.a.mAudioEncoderMgt.getEncoder().getExtra());
                        } else {
                            this.a.mAudioEncoderMgt.getEncoder().start();
                        }
                        if (this.a.mOnInfoListener != null) {
                            this.a.mOnInfoListener.onInfo(1, 0, 0);
                        }
                    case d.b /*2*/:
                        if (!this.a.mIsAudioOnly) {
                            if (this.a.mVideoEncoderMgt.getEncoder().isEncoding()) {
                                this.a.mFilePublisher.setVideoExtra(this.a.mVideoEncoderMgt.getEncoder().getExtra());
                                this.a.mVideoEncoderMgt.getEncoder().forceKeyFrame();
                                return;
                            }
                            this.a.mVideoEncoderMgt.start();
                        }
                    case TexTransformUtil.COORDS_COUNT /*4*/:
                        this.a.mPublisherMgt.removePublisher(this.a.mFilePublisher);
                        this.a.mIsFileRecording = KSYStreamer.DEBUG;
                        if (this.a.mOnInfoListener != null) {
                            this.a.mOnInfoListener.onInfo(2, 0, 0);
                        }
                    default:
                }
            }

            public void onError(int i, long j) {
                Log.e(KSYStreamer.TAG, "FilePublisher err=" + i);
                if (i != 0) {
                    this.a.stopRecord();
                }
                if (this.a.mOnErrorListener != null) {
                    int i2;
                    switch (i) {
                        case FilePublisher.FILE_PUBLISHER_FORMAT_NOT_SUPPORTED /*-4004*/:
                            i2 = FilePublisher.FILE_PUBLISHER_FORMAT_NOT_SUPPORTED;
                            break;
                        case FilePublisher.FILE_PUBLISHER_ERROR_CLOSE_FAILED /*-4003*/:
                            i2 = FilePublisher.FILE_PUBLISHER_ERROR_CLOSE_FAILED;
                            break;
                        case FilePublisher.FILE_PUBLISHER_ERROR_WRITE_FAILED /*-4002*/:
                            i2 = FilePublisher.FILE_PUBLISHER_ERROR_WRITE_FAILED;
                            break;
                        case FilePublisher.FILE_PUBLISHER_ERROR_OPEN_FAILED /*-4001*/:
                            i2 = FilePublisher.FILE_PUBLISHER_ERROR_OPEN_FAILED;
                            break;
                        default:
                            i2 = FilePublisher.FILE_PUBLISHER_ERROR_UNKNOWN;
                            break;
                    }
                    this.a.mOnErrorListener.onError(i2, (int) j, 0);
                }
            }
        });
        this.mGLRender.init(1, 1);
        if (this.mContext != null) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService(KSYMediaMeta.IJKM_VAL_TYPE__AUDIO);
            this.mHeadSetPluged = audioManager.isWiredHeadsetOn();
            this.mBluetoothPluged = audioManager.isBluetoothA2dpOn();
        }
        registerHeadsetPlugReceiver();
    }

    public GLRender getGLRender() {
        return this.mGLRender;
    }

    public CameraCapture getCameraCapture() {
        return this.mCameraCapture;
    }

    public AudioCapture getAudioCapture() {
        return this.mAudioCapture;
    }

    public ImgTexFilterMgt getImgTexFilterMgt() {
        return this.mImgTexFilterMgt;
    }

    public AudioFilterMgt getAudioFilterMgt() {
        return this.mAudioFilterMgt;
    }

    public AudioFilterMgt getBGMAudioFilterMgt() {
        return this.mAudioPlayerCapture.getAudioFilterMgt();
    }

    public ImgTexMixer getImgTexMixer() {
        return this.mImgTexMixer;
    }

    public ImgTexMixer getImgTexPreviewMixer() {
        return this.mImgTexPreviewMixer;
    }

    public AudioMixer getAudioMixer() {
        return this.mAudioMixer;
    }

    public VideoEncoderMgt getVideoEncoderMgt() {
        return this.mVideoEncoderMgt;
    }

    public AudioEncoderMgt getAudioEncoderMgt() {
        return this.mAudioEncoderMgt;
    }

    public AudioPlayerCapture getAudioPlayerCapture() {
        return this.mAudioPlayerCapture;
    }

    public RtmpPublisher getRtmpPublisher() {
        return this.mRtmpPublisher;
    }

    public void setDisplayPreview(GLSurfaceView gLSurfaceView) {
        this.mImgTexPreview.setDisplayPreview(gLSurfaceView);
        this.mImgTexPreview.getGLRender().addListener(this.mGLRenderListener);
    }

    public void setDisplayPreview(TextureView textureView) {
        this.mImgTexPreview.setDisplayPreview(textureView);
        this.mImgTexPreview.getGLRender().addListener(this.mGLRenderListener);
    }

    @Deprecated
    public void setOffscreenPreview(int i, int i2) {
    }

    public void setUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url can not be null");
        }
        this.mUri = str;
    }

    @Deprecated
    public void updateUrl(String str) {
        setUrl(str);
    }

    public String getUrl() {
        return this.mUri;
    }

    public void setRotateDegrees(int i) {
        Object obj = 1;
        int i2 = i % 360;
        if (i2 % 90 != 0) {
            throw new IllegalArgumentException("Invalid rotate degrees");
        } else if (this.mRotateDegrees != i2) {
            Object obj2 = this.mRotateDegrees % 180 != 0 ? 1 : null;
            if (i2 % 180 == 0) {
                obj = null;
            }
            if (obj2 != obj) {
                if (this.mPreviewWidth > 0 || this.mPreviewHeight > 0) {
                    setPreviewResolution(this.mPreviewHeight, this.mPreviewWidth);
                }
                if (this.mTargetWidth > 0 || this.mTargetHeight > 0) {
                    setTargetResolution(this.mTargetHeight, this.mTargetWidth);
                    this.mWaterMarkCapture.setTargetSize(this.mTargetWidth, this.mTargetHeight);
                }
            }
            this.mRotateDegrees = i2;
            this.mCameraCapture.setOrientation(i2);
        }
    }

    public int getRotateDegrees() {
        return this.mRotateDegrees;
    }

    public void setCameraCaptureResolution(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("Invalid resolution");
        }
        this.mCameraCapture.setPreviewSize(i, i2);
    }

    public void setCameraCaptureResolution(int i) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("Invalid resolution index");
        }
        int shortEdgeLength = getShortEdgeLength(i);
        this.mCameraCapture.setPreviewSize((shortEdgeLength * 16) / 9, shortEdgeLength);
    }

    public void setPreviewResolution(int i, int i2) {
        if (i < 0 || i2 < 0 || (i == 0 && i2 == 0)) {
            throw new IllegalArgumentException("Invalid resolution");
        }
        this.mPreviewWidth = i;
        this.mPreviewHeight = i2;
        if (this.mScreenRenderWidth != 0 && this.mScreenRenderHeight != 0) {
            calResolution();
            this.mImgTexScaleFilter.setTargetSize(this.mPreviewWidth, this.mPreviewHeight);
            this.mImgTexPreviewMixer.setTargetSize(this.mPreviewWidth, this.mPreviewHeight);
        }
    }

    public void setPreviewResolution(int i) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("Invalid resolution index");
        }
        this.mPreviewResolution = i;
        this.mPreviewWidth = 0;
        this.mPreviewHeight = 0;
        if (this.mScreenRenderWidth != 0 && this.mScreenRenderHeight != 0) {
            calResolution();
            this.mImgTexScaleFilter.setTargetSize(this.mPreviewWidth, this.mPreviewHeight);
            this.mImgTexPreviewMixer.setTargetSize(this.mPreviewWidth, this.mPreviewHeight);
        }
    }

    public int getPreviewWidth() {
        return this.mPreviewWidth;
    }

    public int getPreviewHeight() {
        return this.mPreviewHeight;
    }

    public void setPreviewFps(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("the fps must > 0");
        }
        this.mPreviewFps = f;
        if (this.mTargetFps == 0.0f) {
            this.mTargetFps = this.mPreviewFps;
        }
    }

    public float getPreviewFps() {
        return this.mPreviewFps;
    }

    public void setEncodeMethod(int i) {
        if (isValidEncodeMethod(i)) {
            setVideoEncodeMethod(i);
            setAudioEncodeMethod(i);
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setVideoEncodeMethod(int i) {
        if (!isValidEncodeMethod(i)) {
            throw new IllegalArgumentException();
        } else if (this.mIsRecording) {
            throw new IllegalStateException("Cannot set encode method while recording");
        } else {
            this.mVideoEncoderMgt.setEncodeMethod(i);
        }
    }

    public int getVideoEncodeMethod() {
        return this.mVideoEncoderMgt.getEncodeMethod();
    }

    public void setAudioEncodeMethod(int i) {
        if (!isValidEncodeMethod(i)) {
            throw new IllegalArgumentException();
        } else if (this.mIsRecording) {
            throw new IllegalStateException("Cannot set encode method while recording");
        } else {
            this.mAudioEncoderMgt.setEncodeMethod(i);
        }
    }

    public int getAudioEncodeMethod() {
        return this.mAudioEncoderMgt.getEncodeMethod();
    }

    private boolean isValidEncodeMethod(int i) {
        if (i == 3 || i == 1 || i == 2) {
            return true;
        }
        return DEBUG;
    }

    public void setTargetResolution(int i, int i2) {
        if (i < 0 || i2 < 0 || (i == 0 && i2 == 0)) {
            throw new IllegalArgumentException("Invalid resolution");
        }
        this.mTargetWidth = i;
        this.mTargetHeight = i2;
        if (this.mScreenRenderWidth != 0 && this.mScreenRenderHeight != 0) {
            calResolution();
            this.mImgTexMixer.setTargetSize(this.mTargetWidth, this.mTargetHeight);
            this.mVideoEncoderMgt.setImgBufTargetSize(this.mTargetWidth, this.mTargetHeight);
        }
    }

    public void setTargetResolution(int i) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("Invalid resolution index");
        }
        this.mTargetResolution = i;
        this.mTargetWidth = 0;
        this.mTargetHeight = 0;
        if (this.mScreenRenderWidth != 0 && this.mScreenRenderHeight != 0) {
            calResolution();
            this.mImgTexMixer.setTargetSize(this.mTargetWidth, this.mTargetHeight);
            this.mVideoEncoderMgt.setImgBufTargetSize(this.mTargetWidth, this.mTargetHeight);
        }
    }

    public int getTargetWidth() {
        return this.mTargetWidth;
    }

    public int getTargetHeight() {
        return this.mTargetHeight;
    }

    public void setTargetFps(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("the fps must > 0");
        }
        this.mTargetFps = f;
        if (this.mPreviewFps == 0.0f) {
            this.mPreviewFps = this.mTargetFps;
        }
    }

    public float getTargetFps() {
        return this.mTargetFps;
    }

    public void setIFrameInterval(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("the IFrameInterval must > 0");
        }
        this.mIFrameInterval = f;
    }

    public float getIFrameInterval() {
        return this.mIFrameInterval;
    }

    public void setVideoBitrate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("the VideoBitrate must > 0");
        }
        this.mInitVideoBitrate = i;
        this.mMaxVideoBitrate = i;
        this.mMinVideoBitrate = i;
        this.mAutoAdjustVideoBitrate = DEBUG;
    }

    public void setVideoKBitrate(int i) {
        setVideoBitrate(i * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD);
    }

    public void setVideoBitrate(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("the initial and max VideoBitrate must > 0");
        } else if (i3 < 0) {
            throw new IllegalArgumentException("the min VideoBitrate must >= 0");
        } else {
            this.mInitVideoBitrate = i;
            this.mMaxVideoBitrate = i2;
            this.mMinVideoBitrate = i3;
            this.mAutoAdjustVideoBitrate = true;
        }
    }

    public void setVideoKBitrate(int i, int i2, int i3) {
        setVideoBitrate(i * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD, i2 * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD, i3 * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD);
    }

    public void setBwEstStrategy(int i) {
        this.mBwEstStrategy = i;
    }

    public int getBwEstStrategy() {
        return this.mBwEstStrategy;
    }

    public int getInitVideoBitrate() {
        return this.mInitVideoBitrate;
    }

    public int getMinVideoBitrate() {
        return this.mMinVideoBitrate;
    }

    public int getMaxVideoBitrate() {
        return this.mMaxVideoBitrate;
    }

    public boolean isAutoAdjustVideoBitrate() {
        return this.mAutoAdjustVideoBitrate;
    }

    public void setVideoCodecId(int i) {
        if (i == 1 || i == 2) {
            this.mVideoCodecId = i;
            return;
        }
        throw new IllegalArgumentException("input video codecid error");
    }

    public int getVideoCodecId() {
        return this.mVideoCodecId;
    }

    public void setVideoEncodeScene(int i) {
        this.mEncodeScene = i;
    }

    public int getVideoEncodeScene() {
        return this.mEncodeScene;
    }

    public void setVideoEncodeProfile(int i) {
        this.mEncodeProfile = i;
    }

    public int getVideoEncodeProfile() {
        return this.mEncodeProfile;
    }

    public void setAudioSampleRate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("the AudioSampleRate must > 0");
        }
        this.mAudioSampleRate = i;
    }

    public void setAudioChannels(int i) {
        if (i == 1 || i == 2) {
            this.mAudioChannels = i;
            return;
        }
        throw new IllegalArgumentException("the AudioChannels must be mono or stereo");
    }

    public void setAudioBitrate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("the AudioBitrate must >0");
        }
        this.mAudioBitrate = i;
    }

    public void setAudioKBitrate(int i) {
        setAudioBitrate(i * StatsConstant.DEFAULT_NETWORK_TIMER_PERIOD);
    }

    public void setAudioEncodeProfile(int i) {
        this.mAudioProfile = i;
    }

    public int getAudioEncodeProfile() {
        return this.mAudioProfile;
    }

    public int getAudioBitrate() {
        return this.mAudioBitrate;
    }

    public int getAudioSampleRate() {
        return this.mAudioSampleRate;
    }

    public int getAudioChannels() {
        return this.mAudioChannels;
    }

    public void setEnableAudioLowDelay(boolean z) {
        int i = 1;
        this.mEnableAudioLowDelay = z;
        if (!this.mUseDummyAudioCapture) {
            this.mAudioCapture.setAudioCaptureType(z ? 2 : 1);
        }
        AudioPlayerCapture audioPlayerCapture = this.mAudioPlayerCapture;
        if (!z) {
            i = 0;
        }
        audioPlayerCapture.setAudioPlayerType(i);
    }

    public boolean getEnableAudioLowDelay() {
        return this.mEnableAudioLowDelay;
    }

    @Deprecated
    public void setEnableCameraMirror(boolean z) {
        setFrontCameraMirror(z);
    }

    public void setFrontCameraMirror(boolean z) {
        this.mFrontCameraMirror = z;
        updateFrontMirror();
        StatsLogReport.getInstance().setIsFrontCameraMirror(z);
    }

    public boolean isFrontCameraMirrorEnabled() {
        return this.mFrontCameraMirror;
    }

    public void setCameraFacing(int i) {
        this.mCameraFacing = i;
    }

    public int getCameraFacing() {
        return this.mCameraFacing;
    }

    public void startCameraPreview() {
        startCameraPreview(this.mCameraFacing);
    }

    public void startCameraPreview(int i) {
        this.mCameraFacing = i;
        if ((this.mPreviewWidth == 0 || this.mPreviewHeight == 0) && (this.mScreenRenderWidth == 0 || this.mScreenRenderHeight == 0)) {
            if (this.mImgTexPreview.getDisplayPreview() != null) {
                this.mDelayedStartCameraPreview = true;
                return;
            } else {
                this.mScreenRenderWidth = DEFAULT_PREVIEW_WIDTH;
                this.mScreenRenderHeight = DEFAULT_PREVIEW_HEIGHT;
            }
        }
        setPreviewParams();
        this.mCameraCapture.start(this.mCameraFacing);
    }

    public void stopCameraPreview() {
        this.mCameraCapture.stop();
    }

    private int getShortEdgeLength(int i) {
        switch (i) {
            case GLRender.VIEW_TYPE_NONE /*0*/:
                return 360;
            case d.a /*1*/:
                return 480;
            case d.b /*2*/:
                return 540;
            case TexTransformUtil.COORDS_COUNT /*4*/:
                return 1080;
            default:
                return DEFAULT_PREVIEW_WIDTH;
        }
    }

    private int align(int i, int i2) {
        return (((i + i2) - 1) / i2) * i2;
    }

    private void calResolution() {
        int shortEdgeLength;
        if (this.mPreviewWidth == 0 && this.mPreviewHeight == 0) {
            shortEdgeLength = getShortEdgeLength(this.mPreviewResolution);
            if (this.mScreenRenderWidth > this.mScreenRenderHeight) {
                this.mPreviewHeight = shortEdgeLength;
            } else {
                this.mPreviewWidth = shortEdgeLength;
            }
        }
        if (this.mTargetWidth == 0 && this.mTargetHeight == 0) {
            shortEdgeLength = getShortEdgeLength(this.mTargetResolution);
            if (this.mScreenRenderWidth > this.mScreenRenderHeight) {
                this.mTargetHeight = shortEdgeLength;
            } else {
                this.mTargetWidth = shortEdgeLength;
            }
        }
        if (!(this.mScreenRenderWidth == 0 || this.mScreenRenderHeight == 0)) {
            if (this.mPreviewWidth == 0) {
                this.mPreviewWidth = (this.mPreviewHeight * this.mScreenRenderWidth) / this.mScreenRenderHeight;
            } else if (this.mPreviewHeight == 0) {
                this.mPreviewHeight = (this.mPreviewWidth * this.mScreenRenderHeight) / this.mScreenRenderWidth;
            }
            if (this.mTargetWidth == 0) {
                this.mTargetWidth = (this.mTargetHeight * this.mScreenRenderWidth) / this.mScreenRenderHeight;
            } else if (this.mTargetHeight == 0) {
                this.mTargetHeight = (this.mTargetWidth * this.mScreenRenderHeight) / this.mScreenRenderWidth;
            }
        }
        this.mPreviewWidth = align(this.mPreviewWidth, 8);
        this.mPreviewHeight = align(this.mPreviewHeight, 8);
        this.mTargetWidth = align(this.mTargetWidth, 8);
        this.mTargetHeight = align(this.mTargetHeight, 8);
    }

    protected void updateFrontMirror() {
        boolean z = true;
        if (this.mCameraFacing == 1) {
            ImgTexMixer imgTexMixer = this.mImgTexMixer;
            int i = this.mIdxCamera;
            if (this.mFrontCameraMirror) {
                z = DEBUG;
            }
            imgTexMixer.setMirror(i, z);
            this.mVideoEncoderMgt.setImgBufMirror(this.mFrontCameraMirror);
            return;
        }
        this.mImgTexMixer.setMirror(this.mIdxCamera, DEBUG);
        this.mVideoEncoderMgt.setImgBufMirror(DEBUG);
    }

    protected void setAudioParams() {
        this.mAudioResampleFilter.setOutFormat(new AudioBufFormat(1, this.mAudioSampleRate, this.mAudioChannels));
    }

    protected void setPreviewParams() {
        calResolution();
        this.mWaterMarkCapture.setPreviewSize(this.mPreviewWidth, this.mPreviewHeight);
        this.mWaterMarkCapture.setTargetSize(this.mTargetWidth, this.mTargetHeight);
        this.mCameraCapture.setOrientation(this.mRotateDegrees);
        if (this.mPreviewFps == 0.0f) {
            this.mPreviewFps = StreamerConstants.DEFAULT_TARGET_FPS;
        }
        this.mCameraCapture.setPreviewFps(this.mPreviewFps);
        this.mImgTexScaleFilter.setTargetSize(this.mPreviewWidth, this.mPreviewHeight);
        this.mImgTexPreviewMixer.setTargetSize(this.mPreviewWidth, this.mPreviewHeight);
        this.mImgTexMixer.setTargetSize(this.mTargetWidth, this.mTargetHeight);
        setAudioParams();
    }

    protected void setRecordingParams() {
        calResolution();
        this.mImgTexMixer.setTargetSize(this.mTargetWidth, this.mTargetHeight);
        VideoEncodeFormat videoEncodeFormat = new VideoEncodeFormat(this.mVideoCodecId, this.mTargetWidth, this.mTargetHeight, this.mInitVideoBitrate);
        if (this.mTargetFps == 0.0f) {
            this.mTargetFps = StreamerConstants.DEFAULT_TARGET_FPS;
        }
        videoEncodeFormat.setFramerate(this.mTargetFps);
        videoEncodeFormat.setIframeinterval(this.mIFrameInterval);
        videoEncodeFormat.setScene(this.mEncodeScene);
        videoEncodeFormat.setProfile(this.mEncodeProfile);
        this.mVideoEncoderMgt.setEncodeFormat(videoEncodeFormat);
        if (this.mAudioProfile != 1) {
            this.mAudioEncoderMgt.setEncodeMethod(3);
        }
        AudioEncodeFormat audioEncodeFormat = new AudioEncodeFormat((int) StreamerConstants.CODEC_ID_AAC, 1, this.mAudioSampleRate, this.mAudioChannels, this.mAudioBitrate);
        audioEncodeFormat.setProfile(this.mAudioProfile);
        this.mAudioEncoderMgt.setEncodeFormat(audioEncodeFormat);
        BwEstConfig bwEstConfig = new BwEstConfig();
        bwEstConfig.strategy = this.mBwEstStrategy;
        bwEstConfig.initAudioBitrate = this.mAudioBitrate;
        bwEstConfig.initVideoBitrate = this.mInitVideoBitrate;
        bwEstConfig.minVideoBitrate = this.mMinVideoBitrate;
        bwEstConfig.maxVideoBitrate = this.mMaxVideoBitrate;
        bwEstConfig.isAdjustBitrate = this.mAutoAdjustVideoBitrate;
        this.mRtmpPublisher.setBwEstConfig(bwEstConfig);
        this.mRtmpPublisher.setFramerate(this.mTargetFps);
        this.mRtmpPublisher.setVideoBitrate(this.mMaxVideoBitrate);
        this.mRtmpPublisher.setAudioBitrate(this.mAudioBitrate);
        this.mFilePublisher.setVideoBitrate(this.mInitVideoBitrate);
        this.mFilePublisher.setAudioBitrate(this.mAudioBitrate);
        this.mFilePublisher.setFramerate(this.mTargetFps);
    }

    public boolean startStream() {
        if (this.mIsRecording) {
            return DEBUG;
        }
        if (!this.mIsAudioOnly && ((this.mTargetWidth == 0 || this.mTargetHeight == 0) && (this.mScreenRenderWidth == 0 || this.mScreenRenderHeight == 0))) {
            if (this.mImgTexPreview.getDisplayPreview() != null) {
                this.mDelayedStartStreaming = true;
                return true;
            }
            this.mScreenRenderWidth = DEFAULT_PREVIEW_WIDTH;
            this.mScreenRenderHeight = DEFAULT_PREVIEW_HEIGHT;
        }
        this.mIsRecording = true;
        startCapture();
        this.mRtmpPublisher.connect(this.mUri);
        return true;
    }

    public boolean startRecord(String str) {
        if (this.mIsFileRecording || TextUtils.isEmpty(str)) {
            return DEBUG;
        }
        this.mRecordUri = str;
        if (!this.mIsAudioOnly && ((this.mTargetWidth == 0 || this.mTargetHeight == 0) && (this.mScreenRenderWidth == 0 || this.mScreenRenderHeight == 0))) {
            if (this.mImgTexPreview.getDisplayPreview() != null) {
                this.mDelayedStartStreaming = true;
                return true;
            }
            this.mScreenRenderWidth = DEFAULT_PREVIEW_WIDTH;
            this.mScreenRenderHeight = DEFAULT_PREVIEW_HEIGHT;
        }
        this.mIsFileRecording = true;
        this.mFilePublisher.startRecording(str);
        this.mPublisherMgt.addPublisher(this.mFilePublisher);
        startCapture();
        return true;
    }

    public void stopRecord() {
        if (!this.mIsFileRecording) {
            return;
        }
        if (!this.mIsRecording && this.mVideoEncoderMgt.getEncoder().isEncoding() && this.mAudioEncoderMgt.getEncoder().isEncoding()) {
            stopCapture();
        } else {
            this.mFilePublisher.stop();
        }
    }

    protected void startCapture() {
        if (!this.mIsCaptureStarted) {
            this.mIsCaptureStarted = true;
            setAudioParams();
            setRecordingParams();
            startAudioCapture();
            this.mCameraCapture.startRecord();
        }
    }

    protected void stopCapture() {
        if (this.mIsCaptureStarted) {
            this.mIsCaptureStarted = DEBUG;
            stopAudioCapture();
            if (this.mCameraCapture.isRecording()) {
                this.mCameraCapture.stopRecord();
            }
            if (!this.mIsRecording) {
                this.mVideoEncoderMgt.getEncoder().flush();
                this.mAudioEncoderMgt.getEncoder().flush();
            }
            this.mVideoEncoderMgt.stop();
            this.mAudioEncoderMgt.getEncoder().stop();
        }
    }

    public boolean stopStream() {
        if (!this.mIsRecording) {
            return DEBUG;
        }
        if (!this.mIsFileRecording) {
            stopCapture();
        }
        this.mIsRecording = DEBUG;
        this.mRtmpPublisher.disconnect();
        return true;
    }

    public boolean isRecording() {
        return this.mIsRecording;
    }

    public boolean isFileRecording() {
        return this.mIsFileRecording;
    }

    public void setAudioOnly(boolean z) {
        if (this.mIsAudioOnly != z) {
            if (z) {
                this.mVideoEncoderMgt.getSrcPin().disconnect(DEBUG);
                if (this.mIsRecording) {
                    this.mVideoEncoderMgt.getEncoder().stop();
                }
                this.mPublisherMgt.setAudioOnly(true);
            } else {
                this.mVideoEncoderMgt.getSrcPin().connect(this.mPublisherMgt.getVideoSink());
                this.mPublisherMgt.setAudioOnly(DEBUG);
                if (this.mIsRecording) {
                    this.mVideoEncoderMgt.getEncoder().start();
                }
            }
            this.mIsAudioOnly = z;
        }
    }

    public void setUseDummyAudioCapture(boolean z) {
        this.mUseDummyAudioCapture = z;
        if (z) {
            this.mAudioCapture.setAudioCaptureType(3);
        } else {
            this.mAudioCapture.setAudioCaptureType(this.mEnableAudioLowDelay ? 2 : 1);
        }
    }

    public void setEnableRepeatLastFrame(boolean z) {
        this.mEnableRepeatLastFrame = z;
    }

    public boolean getEnableRepeatLastFrame() {
        return this.mEnableRepeatLastFrame;
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        if (this.mEnableRepeatLastFrame && this.mIsRecording && !this.mIsAudioOnly) {
            getVideoEncoderMgt().getEncoder().stopRepeatLastFrame();
        }
        this.mImgTexPreview.onResume();
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        this.mImgTexPreview.onPause();
        if (this.mEnableRepeatLastFrame && this.mIsRecording && !this.mIsAudioOnly) {
            getVideoEncoderMgt().getEncoder().startRepeatLastFrame();
        }
    }

    public void enableDebugLog(boolean z) {
        this.mEnableDebugLog = z;
        StatsLogReport.getInstance().setEnableDebugLog(this.mEnableDebugLog);
    }

    public long getEncodedFrames() {
        return (long) this.mVideoEncoderMgt.getEncoder().getFrameEncoded();
    }

    public int getDroppedFrameCount() {
        return this.mVideoEncoderMgt.getEncoder().getFrameDropped() + this.mRtmpPublisher.getDroppedVideoFrames();
    }

    public int getDnsParseTime() {
        return this.mRtmpPublisher.getDnsParseTime();
    }

    public int getConnectTime() {
        return this.mRtmpPublisher.getConnectTime();
    }

    @Deprecated
    public float getCurrentBitrate() {
        return (float) getCurrentUploadKBitrate();
    }

    public int getCurrentUploadKBitrate() {
        return this.mRtmpPublisher.getCurrentUploadKBitrate();
    }

    public int getUploadedKBytes() {
        return this.mRtmpPublisher.getUploadedKBytes();
    }

    public String getRtmpHostIP() {
        return this.mRtmpPublisher.getHostIp();
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void switchCamera() {
        this.mCameraCapture.switchCamera();
    }

    public boolean isFrontCamera() {
        return this.mCameraFacing == 1 ? true : DEBUG;
    }

    public boolean isTorchSupported() {
        return this.mCameraCapture.isTorchSupported();
    }

    public boolean toggleTorch(boolean z) {
        return this.mCameraCapture.toggleTorch(z);
    }

    public void startBgm(String str, boolean z) {
        this.mAudioPlayerCapture.start(str, z);
    }

    public void stopBgm() {
        this.mAudioPlayerCapture.stop();
    }

    @Deprecated
    public void setHeadsetPlugged(boolean z) {
        setEnableAudioMix(z);
    }

    public void setEnableAudioMix(boolean z) {
        this.mEnableAudioMix = z;
        if (this.mEnableAudioMix) {
            this.mAudioPlayerCapture.mSrcPin.connect(this.mAudioMixer.getSinkPin(this.mIdxAudioBgm));
        } else {
            this.mAudioPlayerCapture.mSrcPin.disconnect(this.mAudioMixer.getSinkPin(this.mIdxAudioBgm), DEBUG);
        }
    }

    public boolean isAudioMixEnabled() {
        return this.mEnableAudioMix;
    }

    public void setVoiceVolume(float f) {
        this.mAudioCapture.setVolume(f);
    }

    public float getVoiceVolume() {
        return this.mAudioCapture.getVolume();
    }

    public void setEnableImgBufBeauty(boolean z) {
        this.mVideoEncoderMgt.setEnableImgBufBeauty(z);
    }

    public void setMuteAudio(boolean z) {
        this.mAudioPlayerCapture.setMute(z);
        this.mAudioPreview.setMute(z);
        this.mAudioMixer.setMute(z);
    }

    public boolean isAudioMuted() {
        return this.mAudioMixer.getMute();
    }

    public void setEnableAudioPreview(boolean z) {
        this.mIsEnableAudioPreview = z;
        setEnableAudioPreviewInternal(z);
    }

    private void setEnableAudioPreviewInternal(boolean z) {
        if (z != this.mIsAudioPreviewing) {
            if (!z || this.mHeadSetPluged || this.mBluetoothPluged || this.mAudioCapture.getEnableLatencyTest()) {
                this.mIsAudioPreviewing = z;
                if (z) {
                    setAudioParams();
                    startAudioCapture();
                    this.mAudioPreview.start();
                    return;
                }
                this.mAudioPreview.stop();
                stopAudioCapture();
                return;
            }
            Log.w(TAG, "please connect the earphone");
        }
    }

    public boolean isAudioPreviewing() {
        return this.mIsEnableAudioPreview;
    }

    @Deprecated
    public void setEnableEarMirror(boolean z) {
        setEnableAudioPreview(z);
    }

    public void setEnableAutoRestart(boolean z, int i) {
        this.mAutoRestart = z;
        this.mAutoRestartInterval = i;
    }

    public boolean getEnableAutoRestart() {
        return this.mAutoRestart;
    }

    @Deprecated
    public void setOnPreviewFrameListener(OnPreviewFrameListener onPreviewFrameListener) {
        this.mCameraCapture.setOnPreviewFrameListener(onPreviewFrameListener);
    }

    @Deprecated
    public void setOnAudioRawDataListener(OnAudioRawDataListener onAudioRawDataListener) {
        this.mAudioCapture.setOnAudioRawDataListener(onAudioRawDataListener);
    }

    public void setOnLogEventListener(OnLogEventListener onLogEventListener) {
        StatsLogReport.getInstance().setOnLogEventListener(onLogEventListener);
    }

    public void setEnableStreamStatModule(boolean z) {
        this.mEnableStreamStatModule = z;
    }

    public void showWaterMarkLogo(String str, float f, float f2, float f3, float f4, float f5) {
        float min = Math.min(Math.max(0.0f, f5), 1.0f);
        this.mImgTexMixer.setRenderRect(this.mIdxWmLogo, f, f2, f3, f4, min);
        this.mImgTexPreviewMixer.setRenderRect(this.mIdxWmLogo, f, f2, f3, f4, min);
        this.mVideoEncoderMgt.getImgBufMixer().setRenderRect(1, f, f2, f3, f4, min);
        this.mWaterMarkCapture.showLogo(this.mContext, str, f3, f4);
    }

    public void showWaterMarkLogo(Bitmap bitmap, float f, float f2, float f3, float f4, float f5) {
        float min = Math.min(Math.max(0.0f, f5), 1.0f);
        this.mImgTexMixer.setRenderRect(this.mIdxWmLogo, f, f2, f3, f4, min);
        this.mImgTexPreviewMixer.setRenderRect(this.mIdxWmLogo, f, f2, f3, f4, min);
        this.mVideoEncoderMgt.getImgBufMixer().setRenderRect(1, f, f2, f3, f4, min);
        this.mWaterMarkCapture.showLogo(bitmap, f3, f4);
    }

    public void hideWaterMarkLogo() {
        this.mWaterMarkCapture.hideLogo();
    }

    public void showWaterMarkTime(float f, float f2, float f3, int i, float f4) {
        float min = Math.min(Math.max(0.0f, f4), 1.0f);
        this.mImgTexMixer.setRenderRect(this.mIdxWmTime, f, f2, f3, 0.0f, min);
        this.mImgTexPreviewMixer.setRenderRect(this.mIdxWmTime, f, f2, f3, 0.0f, min);
        this.mVideoEncoderMgt.getImgBufMixer().setRenderRect(2, f, f2, f3, 0.0f, min);
        this.mWaterMarkCapture.showTime(i, "yyyy-MM-dd HH:mm:ss", f3, 0.0f);
    }

    public void hideWaterMarkTime() {
        this.mWaterMarkCapture.hideTime();
    }

    public void startImageCapture(String str) {
        startImageCapture(BitmapLoader.loadBitmap(this.mContext, str), true);
    }

    public void startImageCapture(Bitmap bitmap) {
        startImageCapture(bitmap, DEBUG);
    }

    public void startImageCapture(Bitmap bitmap, boolean z) {
        this.mImageCapture.getSrcPin().connect(this.mImgTexPreviewMixer.getSinkPin(this.mIdxCamera));
        this.mImageCapture.getSrcPin().connect(this.mImgTexMixer.getSinkPin(this.mIdxCamera));
        this.mImageCapture.setRepeatFps(this.mPreviewFps);
        this.mImageCapture.start(bitmap, z);
    }

    public void stopImageCapture() {
        this.mImageCapture.getSrcPin().disconnect(this.mImgTexPreviewMixer.getSinkPin(this.mIdxCamera), DEBUG);
        this.mImageCapture.getSrcPin().disconnect(this.mImgTexMixer.getSinkPin(this.mIdxCamera), DEBUG);
        this.mImageCapture.stop();
    }

    public static String getVersion() {
        return StatsConstant.SDK_VERSION_VALUE;
    }

    public void release() {
        if (this.mMainHandler != null) {
            this.mMainHandler.removeCallbacksAndMessages(null);
            this.mMainHandler = null;
        }
        synchronized (this.mReleaseObject) {
            this.mImageCapture.release();
            this.mWaterMarkCapture.release();
            this.mAudioPlayerCapture.release();
            this.mCameraCapture.release();
            this.mAudioCapture.release();
            this.mGLRender.release();
            setOnLogEventListener(null);
            unregisterHeadsetPlugReceiver();
            if (!this.mAudioAPMFilterMgt.e()) {
                this.mAudioAPMFilterMgt.d();
            }
        }
    }

    public void requestScreenShot(ScreenShotListener screenShotListener) {
        this.mImgTexMixer.requestScreenShot(screenShotListener);
    }

    public void requestScreenShot(float f, ScreenShotListener screenShotListener) {
        this.mImgTexMixer.requestScreenShot(f, screenShotListener);
    }

    private void autoRestart() {
        if (this.mAutoRestart && this.mMainHandler != null) {
            stopStream();
            this.mMainHandler.postDelayed(new Runnable() {
                final /* synthetic */ KSYStreamer a;

                {
                    this.a = r1;
                }

                public void run() {
                    synchronized (this.a.mReleaseObject) {
                        if (this.a.mMainHandler != null) {
                            this.a.startStream();
                        }
                    }
                }
            }, (long) this.mAutoRestartInterval);
        }
    }

    protected void stopAudioCapture() {
        if (this.mAudioUsingCount == null) {
            this.mAudioUsingCount = new AtomicInteger(0);
        }
        if (this.mAudioUsingCount.get() != 0 && this.mAudioUsingCount.decrementAndGet() == 0) {
            this.mAudioCapture.stop();
        }
    }

    protected void startAudioCapture() {
        if (this.mAudioCapture.getSrcPin().isConnected()) {
            if (this.mAudioUsingCount == null) {
                this.mAudioUsingCount = new AtomicInteger(0);
            }
            if (this.mAudioUsingCount.getAndIncrement() == 0) {
                this.mAudioCapture.start();
            }
        }
    }

    private void registerHeadsetPlugReceiver() {
        if (this.mHeadSetReceiver == null && this.mContext != null) {
            this.mHeadSetReceiver = new a();
            IntentFilter intentFilter = new IntentFilter("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            this.mContext.registerReceiver(this.mHeadSetReceiver, intentFilter);
        }
    }

    private void unregisterHeadsetPlugReceiver() {
        if (this.mHeadSetReceiver != null) {
            this.mContext.unregisterReceiver(this.mHeadSetReceiver);
        }
    }

    public OnErrorListener getOnErrorListener() {
        return this.mOnErrorListener;
    }

    public OnInfoListener getOnInfoListener() {
        return this.mOnInfoListener;
    }

    public void setAudioNSLevel(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("the NS level must be between 0 and 3");
        }
        this.mAudioAPMFilterMgt.a(i);
    }

    public void setEnableAudioNS(boolean z) {
        if (this.mAudioAPMFilterMgt.e() != z) {
            if (z) {
                this.mAudioCapture.getSrcPin().disconnect(this.mAudioFilterMgt.getSinkPin(), DEBUG);
                this.mAudioCapture.getSrcPin().connect(this.mAudioAPMFilterMgt.a());
                this.mAudioAPMFilterMgt.b().connect(this.mAudioFilterMgt.getSinkPin());
            } else {
                this.mAudioCapture.getSrcPin().disconnect(this.mAudioAPMFilterMgt.a(), DEBUG);
                this.mAudioAPMFilterMgt.b().disconnect(this.mAudioFilterMgt.getSinkPin(), DEBUG);
                this.mAudioCapture.getSrcPin().connect(this.mAudioFilterMgt.getSinkPin());
            }
            this.mAudioAPMFilterMgt.a(z);
        }
    }
}
