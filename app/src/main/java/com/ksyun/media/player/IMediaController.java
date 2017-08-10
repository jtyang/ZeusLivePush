package com.ksyun.media.player;

import android.view.View;

public interface IMediaController {

    public interface MediaPlayerControl {
        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        int getBufferPercentage();

        long getCurrentPosition();

        long getDuration();

        boolean isPlaying();

        void pause();

        void seekTo(long j);

        void start();
    }

    void hide();

    boolean isShowing();

    void onPause();

    void onStart();

    void setAnchorView(View view);

    void setEnabled(boolean z);

    void setMediaPlayer(MediaPlayerControl mediaPlayerControl);

    void show();
}
