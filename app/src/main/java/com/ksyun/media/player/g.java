package com.ksyun.media.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.ksyun.media.player.IMediaPlayer.OnBufferingUpdateListener;
import com.ksyun.media.player.IMediaPlayer.OnCompletionListener;
import com.ksyun.media.player.IMediaPlayer.OnErrorListener;
import com.ksyun.media.player.IMediaPlayer.OnInfoListener;
import com.ksyun.media.player.IMediaPlayer.OnMessageListener;
import com.ksyun.media.player.IMediaPlayer.OnPreparedListener;
import com.ksyun.media.player.IMediaPlayer.OnSeekCompleteListener;
import com.ksyun.media.player.IMediaPlayer.OnTimedTextListener;
import com.ksyun.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import com.ksyun.media.player.misc.IMediaDataSource;
import com.ksyun.media.player.misc.ITrackInfo;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

/* compiled from: MediaPlayerProxy */
public class g implements IMediaPlayer {
    protected final IMediaPlayer a;

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.1 */
    class AnonymousClass1 implements OnPreparedListener {
        final /* synthetic */ OnPreparedListener a;
        final /* synthetic */ g b;

        AnonymousClass1(g gVar, OnPreparedListener onPreparedListener) {
            this.b = gVar;
            this.a = onPreparedListener;
        }

        public void onPrepared(IMediaPlayer iMediaPlayer) {
            this.a.onPrepared(this.b);
        }
    }

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.2 */
    class AnonymousClass2 implements OnCompletionListener {
        final /* synthetic */ OnCompletionListener a;
        final /* synthetic */ g b;

        AnonymousClass2(g gVar, OnCompletionListener onCompletionListener) {
            this.b = gVar;
            this.a = onCompletionListener;
        }

        public void onCompletion(IMediaPlayer iMediaPlayer) {
            this.a.onCompletion(this.b);
        }
    }

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.3 */
    class AnonymousClass3 implements OnBufferingUpdateListener {
        final /* synthetic */ OnBufferingUpdateListener a;
        final /* synthetic */ g b;

        AnonymousClass3(g gVar, OnBufferingUpdateListener onBufferingUpdateListener) {
            this.b = gVar;
            this.a = onBufferingUpdateListener;
        }

        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            this.a.onBufferingUpdate(this.b, i);
        }
    }

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.4 */
    class AnonymousClass4 implements OnSeekCompleteListener {
        final /* synthetic */ OnSeekCompleteListener a;
        final /* synthetic */ g b;

        AnonymousClass4(g gVar, OnSeekCompleteListener onSeekCompleteListener) {
            this.b = gVar;
            this.a = onSeekCompleteListener;
        }

        public void onSeekComplete(IMediaPlayer iMediaPlayer) {
            this.a.onSeekComplete(this.b);
        }
    }

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.5 */
    class AnonymousClass5 implements OnVideoSizeChangedListener {
        final /* synthetic */ OnVideoSizeChangedListener a;
        final /* synthetic */ g b;

        AnonymousClass5(g gVar, OnVideoSizeChangedListener onVideoSizeChangedListener) {
            this.b = gVar;
            this.a = onVideoSizeChangedListener;
        }

        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
            this.a.onVideoSizeChanged(this.b, i, i2, i3, i4);
        }
    }

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.6 */
    class AnonymousClass6 implements OnErrorListener {
        final /* synthetic */ OnErrorListener a;
        final /* synthetic */ g b;

        AnonymousClass6(g gVar, OnErrorListener onErrorListener) {
            this.b = gVar;
            this.a = onErrorListener;
        }

        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            return this.a.onError(this.b, i, i2);
        }
    }

    /* compiled from: MediaPlayerProxy */
    /* renamed from: com.ksyun.media.player.g.7 */
    class AnonymousClass7 implements OnInfoListener {
        final /* synthetic */ OnInfoListener a;
        final /* synthetic */ g b;

        AnonymousClass7(g gVar, OnInfoListener onInfoListener) {
            this.b = gVar;
            this.a = onInfoListener;
        }

        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            return this.a.onInfo(this.b, i, i2);
        }
    }

    public g(IMediaPlayer iMediaPlayer) {
        this.a = iMediaPlayer;
    }

    public IMediaPlayer b() {
        return this.a;
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.a.setDisplay(surfaceHolder);
    }

    @TargetApi(14)
    public void setSurface(Surface surface) {
        this.a.setSurface(surface);
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.a.setDataSource(context, uri);
    }

    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.a.setDataSource(context, uri, map);
    }

    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        this.a.setDataSource(fileDescriptor);
    }

    public void setDataSource(String str) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.a.setDataSource(str);
    }

    public void setDataSource(IMediaDataSource iMediaDataSource) {
        this.a.setDataSource(iMediaDataSource);
    }

    public String getDataSource() {
        return this.a.getDataSource();
    }

    public void prepareAsync() throws IllegalStateException {
        this.a.prepareAsync();
    }

    public void start() throws IllegalStateException {
        this.a.start();
    }

    public void stop() throws IllegalStateException {
        this.a.stop();
    }

    public void pause() throws IllegalStateException {
        this.a.pause();
    }

    public void setScreenOnWhilePlaying(boolean z) {
        this.a.setScreenOnWhilePlaying(z);
    }

    public int getVideoWidth() {
        return this.a.getVideoWidth();
    }

    public int getVideoHeight() {
        return this.a.getVideoHeight();
    }

    public boolean isPlaying() {
        return this.a.isPlaying();
    }

    public void seekTo(long j) throws IllegalStateException {
        this.a.seekTo(j);
    }

    public long getCurrentPosition() {
        return this.a.getCurrentPosition();
    }

    public long getDuration() {
        return this.a.getDuration();
    }

    public void release() {
        this.a.release();
    }

    public void reset() {
        this.a.reset();
    }

    public void setVolume(float f, float f2) {
        this.a.setVolume(f, f2);
    }

    public int getAudioSessionId() {
        return this.a.getAudioSessionId();
    }

    public MediaInfo getMediaInfo() {
        return this.a.getMediaInfo();
    }

    public void setLogEnabled(boolean z) {
    }

    public void addTimedTextSource(String str) {
    }

    public void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
    }

    public boolean isPlayable() {
        return false;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        if (onPreparedListener != null) {
            this.a.setOnPreparedListener(new AnonymousClass1(this, onPreparedListener));
        } else {
            this.a.setOnPreparedListener(null);
        }
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        if (onCompletionListener != null) {
            this.a.setOnCompletionListener(new AnonymousClass2(this, onCompletionListener));
        } else {
            this.a.setOnCompletionListener(null);
        }
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        if (onBufferingUpdateListener != null) {
            this.a.setOnBufferingUpdateListener(new AnonymousClass3(this, onBufferingUpdateListener));
        } else {
            this.a.setOnBufferingUpdateListener(null);
        }
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        if (onSeekCompleteListener != null) {
            this.a.setOnSeekCompleteListener(new AnonymousClass4(this, onSeekCompleteListener));
        } else {
            this.a.setOnSeekCompleteListener(null);
        }
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        if (onVideoSizeChangedListener != null) {
            this.a.setOnVideoSizeChangedListener(new AnonymousClass5(this, onVideoSizeChangedListener));
        } else {
            this.a.setOnVideoSizeChangedListener(null);
        }
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        if (onErrorListener != null) {
            this.a.setOnErrorListener(new AnonymousClass6(this, onErrorListener));
        } else {
            this.a.setOnErrorListener(null);
        }
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        if (onInfoListener != null) {
            this.a.setOnInfoListener(new AnonymousClass7(this, onInfoListener));
        } else {
            this.a.setOnInfoListener(null);
        }
    }

    public void setOnMessageListener(OnMessageListener onMessageListener) {
    }

    public void setAudioStreamType(int i) {
        this.a.setAudioStreamType(i);
    }

    public void setKeepInBackground(boolean z) {
        this.a.setKeepInBackground(z);
    }

    public int getVideoSarNum() {
        return this.a.getVideoSarNum();
    }

    public int getVideoSarDen() {
        return this.a.getVideoSarDen();
    }

    public void setWakeMode(Context context, int i) {
        this.a.setWakeMode(context, i);
    }

    public ITrackInfo[] getTrackInfo() {
        return this.a.getTrackInfo();
    }

    public void setLooping(boolean z) {
        this.a.setLooping(z);
    }

    public boolean isLooping() {
        return this.a.isLooping();
    }
}
