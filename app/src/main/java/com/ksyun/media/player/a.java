package com.ksyun.media.player;

import com.ksyun.media.player.IMediaPlayer.OnBufferingUpdateListener;
import com.ksyun.media.player.IMediaPlayer.OnCompletionListener;
import com.ksyun.media.player.IMediaPlayer.OnErrorListener;
import com.ksyun.media.player.IMediaPlayer.OnInfoListener;
import com.ksyun.media.player.IMediaPlayer.OnLogEventListener;
import com.ksyun.media.player.IMediaPlayer.OnMessageListener;
import com.ksyun.media.player.IMediaPlayer.OnPreparedListener;
import com.ksyun.media.player.IMediaPlayer.OnSeekCompleteListener;
import com.ksyun.media.player.IMediaPlayer.OnTimedTextListener;
import com.ksyun.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import com.ksyun.media.player.misc.IMediaDataSource;

/* compiled from: AbstractMediaPlayer */
public abstract class a implements IMediaPlayer {
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnLogEventListener mOnLogEventListener;
    private OnMessageListener mOnMessageListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnTimedTextListener mOnTimedTextListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;

    public final void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public final void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public final void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public final void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public final void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public final void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public final void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public final void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
        this.mOnTimedTextListener = onTimedTextListener;
    }

    public final void setOnLogEventListener(OnLogEventListener onLogEventListener) {
        this.mOnLogEventListener = onLogEventListener;
    }

    public final void setOnMessageListener(OnMessageListener onMessageListener) {
        this.mOnMessageListener = onMessageListener;
    }

    public void resetListeners() {
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnLogEventListener = null;
        this.mOnMessageListener = null;
    }

    protected final void notifyOnPrepared() {
        if (this.mOnPreparedListener != null) {
            this.mOnPreparedListener.onPrepared(this);
        }
    }

    protected final void notifyOnCompletion() {
        if (this.mOnCompletionListener != null) {
            this.mOnCompletionListener.onCompletion(this);
        }
    }

    protected final void notifyOnBufferingUpdate(int i) {
        if (this.mOnBufferingUpdateListener != null) {
            this.mOnBufferingUpdateListener.onBufferingUpdate(this, i);
        }
    }

    protected final void notifyOnSeekComplete() {
        if (this.mOnSeekCompleteListener != null) {
            this.mOnSeekCompleteListener.onSeekComplete(this);
        }
    }

    protected final void notifyOnVideoSizeChanged(int i, int i2, int i3, int i4) {
        if (this.mOnVideoSizeChangedListener != null) {
            this.mOnVideoSizeChangedListener.onVideoSizeChanged(this, i, i2, i3, i4);
        }
    }

    protected final boolean notifyOnError(int i, int i2) {
        return this.mOnErrorListener != null && this.mOnErrorListener.onError(this, i, i2);
    }

    protected final boolean notifyOnInfo(int i, int i2) {
        return this.mOnInfoListener != null && this.mOnInfoListener.onInfo(this, i, i2);
    }

    protected final void notifyOnTimedText(String str) {
        if (this.mOnTimedTextListener != null) {
            this.mOnTimedTextListener.onTimedText(this, str);
        }
    }

    public final void notifyOnLogEvent(String str) {
        if (this.mOnLogEventListener != null) {
            this.mOnLogEventListener.onLogEvent(this, str);
        }
    }

    protected final void notifyMessageInfo(String str, String str2, double d) {
        if (this.mOnMessageListener != null) {
            this.mOnMessageListener.onMessage(this, str, str2, d);
        }
    }

    public void setDataSource(IMediaDataSource iMediaDataSource) {
        throw new UnsupportedOperationException();
    }
}
