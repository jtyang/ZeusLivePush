package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.ksyun.media.player.misc.IMediaDataSource;
import com.ksyun.media.player.misc.ITrackInfo;
import com.ksyun.media.streamer.logstats.StatsConstant;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

public class AndroidMediaPlayer extends a {
    private static MediaInfo sMediaInfo;
    private String mDataSource;
    private final Object mInitLock;
    private final a mInternalListenerAdapter;
    private final MediaPlayer mInternalMediaPlayer;
    private boolean mIsReleased;
    private MediaDataSource mMediaDataSource;

    private class a implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener {
        public final WeakReference<AndroidMediaPlayer> a;
        final /* synthetic */ AndroidMediaPlayer b;

        public a(AndroidMediaPlayer androidMediaPlayer, AndroidMediaPlayer androidMediaPlayer2) {
            this.b = androidMediaPlayer;
            this.a = new WeakReference(androidMediaPlayer2);
        }

        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
            return ((AndroidMediaPlayer) this.a.get()) != null && this.b.notifyOnInfo(i, i2);
        }

        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            return ((AndroidMediaPlayer) this.a.get()) != null && this.b.notifyOnError(i, i2);
        }

        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            if (((AndroidMediaPlayer) this.a.get()) != null) {
                this.b.notifyOnVideoSizeChanged(i, i2, 1, 1);
            }
        }

        public void onSeekComplete(MediaPlayer mediaPlayer) {
            if (((AndroidMediaPlayer) this.a.get()) != null) {
                this.b.notifyOnSeekComplete();
            }
        }

        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            if (((AndroidMediaPlayer) this.a.get()) != null) {
                this.b.notifyOnBufferingUpdate(i);
            }
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            if (((AndroidMediaPlayer) this.a.get()) != null) {
                this.b.notifyOnCompletion();
            }
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            if (((AndroidMediaPlayer) this.a.get()) != null) {
                this.b.notifyOnPrepared();
            }
        }
    }

    @TargetApi(23)
    private static class b extends MediaDataSource {
        private final IMediaDataSource a;

        public b(IMediaDataSource iMediaDataSource) {
            this.a = iMediaDataSource;
        }

        public int readAt(long j, byte[] bArr, int i, int i2) throws IOException {
            return this.a.readAt(j, bArr, i, i2);
        }

        public long getSize() throws IOException {
            return this.a.getSize();
        }

        public void close() throws IOException {
            this.a.close();
        }
    }

    public AndroidMediaPlayer() {
        this.mInitLock = new Object();
        synchronized (this.mInitLock) {
            this.mInternalMediaPlayer = new MediaPlayer();
        }
        this.mInternalMediaPlayer.setAudioStreamType(3);
        this.mInternalListenerAdapter = new a(this, this);
        attachInternalListeners();
    }

    public MediaPlayer getInternalMediaPlayer() {
        return this.mInternalMediaPlayer;
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        synchronized (this.mInitLock) {
            if (!this.mIsReleased) {
                this.mInternalMediaPlayer.setDisplay(surfaceHolder);
            }
        }
    }

    @TargetApi(14)
    public void setSurface(Surface surface) {
        this.mInternalMediaPlayer.setSurface(surface);
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mInternalMediaPlayer.setDataSource(context, uri);
    }

    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mInternalMediaPlayer.setDataSource(context, uri, map);
    }

    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        this.mInternalMediaPlayer.setDataSource(fileDescriptor);
    }

    public void setDataSource(String str) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mDataSource = str;
        Uri parse = Uri.parse(str);
        Object scheme = parse.getScheme();
        if (TextUtils.isEmpty(scheme) || !scheme.equalsIgnoreCase("file")) {
            this.mInternalMediaPlayer.setDataSource(str);
        } else {
            this.mInternalMediaPlayer.setDataSource(parse.getPath());
        }
    }

    @TargetApi(23)
    public void setDataSource(IMediaDataSource iMediaDataSource) {
        releaseMediaDataSource();
        this.mMediaDataSource = new b(iMediaDataSource);
        this.mInternalMediaPlayer.setDataSource(this.mMediaDataSource);
    }

    public void addTimedTextSource(String str) {
    }

    public String getDataSource() {
        return this.mDataSource;
    }

    private void releaseMediaDataSource() {
        if (this.mMediaDataSource != null) {
            try {
                this.mMediaDataSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mMediaDataSource = null;
        }
    }

    public void prepareAsync() throws IllegalStateException {
        this.mInternalMediaPlayer.prepareAsync();
    }

    public void start() throws IllegalStateException {
        this.mInternalMediaPlayer.start();
    }

    public void stop() throws IllegalStateException {
        this.mInternalMediaPlayer.stop();
    }

    public void pause() throws IllegalStateException {
        this.mInternalMediaPlayer.pause();
    }

    public void setScreenOnWhilePlaying(boolean z) {
        this.mInternalMediaPlayer.setScreenOnWhilePlaying(z);
    }

    public ITrackInfo[] getTrackInfo() {
        return com.ksyun.media.player.misc.b.a(this.mInternalMediaPlayer);
    }

    public int getVideoWidth() {
        return this.mInternalMediaPlayer.getVideoWidth();
    }

    public int getVideoHeight() {
        return this.mInternalMediaPlayer.getVideoHeight();
    }

    public int getVideoSarNum() {
        return 1;
    }

    public int getVideoSarDen() {
        return 1;
    }

    public boolean isPlaying() {
        try {
            return this.mInternalMediaPlayer.isPlaying();
        } catch (Throwable e) {
            c.a.a(e);
            return false;
        }
    }

    public void seekTo(long j) throws IllegalStateException {
        this.mInternalMediaPlayer.seekTo((int) j);
    }

    public long getCurrentPosition() {
        try {
            return (long) this.mInternalMediaPlayer.getCurrentPosition();
        } catch (Throwable e) {
            c.a.a(e);
            return 0;
        }
    }

    public long getDuration() {
        try {
            return (long) this.mInternalMediaPlayer.getDuration();
        } catch (Throwable e) {
            c.a.a(e);
            return 0;
        }
    }

    public void release() {
        this.mIsReleased = true;
        this.mInternalMediaPlayer.release();
        releaseMediaDataSource();
        resetListeners();
        attachInternalListeners();
    }

    public void reset() {
        try {
            this.mInternalMediaPlayer.reset();
        } catch (Throwable e) {
            c.a.a(e);
        }
        releaseMediaDataSource();
        resetListeners();
        attachInternalListeners();
    }

    public void setLooping(boolean z) {
        this.mInternalMediaPlayer.setLooping(z);
    }

    public boolean isLooping() {
        return this.mInternalMediaPlayer.isLooping();
    }

    public void setVolume(float f, float f2) {
        this.mInternalMediaPlayer.setVolume(f, f2);
    }

    public int getAudioSessionId() {
        return this.mInternalMediaPlayer.getAudioSessionId();
    }

    public MediaInfo getMediaInfo() {
        if (sMediaInfo == null) {
            MediaInfo mediaInfo = new MediaInfo();
            mediaInfo.mVideoDecoder = StatsConstant.SYSTEM_PLATFORM_VALUE;
            mediaInfo.mVideoDecoderImpl = "HW";
            mediaInfo.mAudioDecoder = StatsConstant.SYSTEM_PLATFORM_VALUE;
            mediaInfo.mAudioDecoderImpl = "HW";
            sMediaInfo = mediaInfo;
        }
        return sMediaInfo;
    }

    public void setLogEnabled(boolean z) {
    }

    public boolean isPlayable() {
        return true;
    }

    public void setWakeMode(Context context, int i) {
        this.mInternalMediaPlayer.setWakeMode(context, i);
    }

    public void setAudioStreamType(int i) {
        this.mInternalMediaPlayer.setAudioStreamType(i);
    }

    public void setKeepInBackground(boolean z) {
    }

    private void attachInternalListeners() {
        this.mInternalMediaPlayer.setOnPreparedListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnBufferingUpdateListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnCompletionListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnSeekCompleteListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnVideoSizeChangedListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnErrorListener(this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnInfoListener(this.mInternalListenerAdapter);
    }
}
