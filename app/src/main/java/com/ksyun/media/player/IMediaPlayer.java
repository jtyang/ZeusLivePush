package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.ksyun.media.player.misc.IMediaDataSource;
import com.ksyun.media.player.misc.ITrackInfo;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

public interface IMediaPlayer {
    public static final int MEDIA_ERROR_3XX_OVERFLOW = -10018;
    public static final int MEDIA_ERROR_ACCESSS_FORBIDDEN = -10007;
    public static final int MEDIA_ERROR_AUDIO_DECODE_FAILED = -10017;
    public static final int MEDIA_ERROR_BAD_REQUEST = -10005;
    public static final int MEDIA_ERROR_CONNECT_SERVER_FAILED = -10004;
    public static final int MEDIA_ERROR_CREATE_SOCKET_FAILED = -10003;
    public static final int MEDIA_ERROR_DNS_PARSE_FAILED = -10002;
    public static final int MEDIA_ERROR_INVALID_DATA = -10011;
    public static final int MEDIA_ERROR_INVALID_URL = -10019;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_OTHER_ERROR_CODE = -10009;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_SERVER_EXCEPTION = -10010;
    public static final int MEDIA_ERROR_TARGET_NOT_FOUND = -10008;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNAUTHORIZED_CLIENT = -10006;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    public static final int MEDIA_ERROR_UNSUPPORT_AUDIO_CODEC = -10013;
    public static final int MEDIA_ERROR_UNSUPPORT_PROTOCOL = -10001;
    public static final int MEDIA_ERROR_UNSUPPORT_VIDEO_CODEC = -10012;
    public static final int MEDIA_ERROR_VIDEO_DECODE_FAILED = -10016;
    public static final int MEDIA_INFO_AUDIO_RENDERING_START = 10002;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_HARDWARE_DECODE = 41000;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_RELOADED = 50001;
    public static final int MEDIA_INFO_SOFTWARE_DECODE = 41001;
    public static final int MEDIA_INFO_SPEED_LOW = 40003;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_SUGGEST_RELOAD = 40020;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i);
    }

    public interface OnCompletionListener {
        void onCompletion(IMediaPlayer iMediaPlayer);
    }

    public interface OnErrorListener {
        boolean onError(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnInfoListener {
        boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnLogEventListener {
        void onLogEvent(IMediaPlayer iMediaPlayer, String str);
    }

    public interface OnMessageListener {
        void onMessage(IMediaPlayer iMediaPlayer, String str, String str2, double d);
    }

    public interface OnPreparedListener {
        void onPrepared(IMediaPlayer iMediaPlayer);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(IMediaPlayer iMediaPlayer);
    }

    public interface OnTimedTextListener {
        void onTimedText(IMediaPlayer iMediaPlayer, String str);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4);
    }

    void addTimedTextSource(String str);

    int getAudioSessionId();

    long getCurrentPosition();

    String getDataSource();

    long getDuration();

    MediaInfo getMediaInfo();

    ITrackInfo[] getTrackInfo();

    int getVideoHeight();

    int getVideoSarDen();

    int getVideoSarNum();

    int getVideoWidth();

    boolean isLooping();

    @Deprecated
    boolean isPlayable();

    boolean isPlaying();

    void pause() throws IllegalStateException;

    void prepareAsync() throws IllegalStateException;

    void release();

    void reset();

    void seekTo(long j) throws IllegalStateException;

    void setAudioStreamType(int i);

    void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    @TargetApi(14)
    void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    void setDataSource(IMediaDataSource iMediaDataSource);

    void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException;

    void setDataSource(String str) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    void setDisplay(SurfaceHolder surfaceHolder);

    @Deprecated
    void setKeepInBackground(boolean z);

    @Deprecated
    void setLogEnabled(boolean z);

    void setLooping(boolean z);

    void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnMessageListener(OnMessageListener onMessageListener);

    void setOnPreparedListener(OnPreparedListener onPreparedListener);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void setOnTimedTextListener(OnTimedTextListener onTimedTextListener);

    void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener);

    void setScreenOnWhilePlaying(boolean z);

    void setSurface(Surface surface);

    void setVolume(float f, float f2);

    @Deprecated
    void setWakeMode(Context context, int i);

    void start() throws IllegalStateException;

    void stop() throws IllegalStateException;
}
