package com.ksyun.media.streamer.capture.camera;

import android.graphics.Rect;

public interface ICameraHintView {
    void setFocused(boolean z);

    void startFocus(Rect rect);

    void updateZoomRatio(float f);
}
