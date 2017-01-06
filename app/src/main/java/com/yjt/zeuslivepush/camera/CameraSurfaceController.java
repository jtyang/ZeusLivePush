package com.yjt.zeuslivepush.camera;

import android.graphics.Rect;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class CameraSurfaceController {
    private boolean mCodecController = false;
    private int mWidth;
    private int mHeight;
    private boolean mIsVisible = false;
    public static final int LeftAlign = 1;
    public static final int RightAlign = 2;
    public static final int TopAlign = 4;
    public static final int BottomAlign = 8;
    public static final int CenterAlign = 16;
    public static final int ScaleEnabled = 32;
    public static final int FullScreen = 64;
    public static final int Mirrored = 128;
    private int mDisplayMethod = 48;
    private CameraSurfaceController.Callback mRenderCallback;
    public static final int FLAG_TIMESTAMP = 1;
    public static final int FLAG_ONSTOPPING = 2;
    private int mFlags = 0;

    public void setResolution(int width, int height) {
        if (width > 0 && height > 0) {
            synchronized (this) {
                this.mWidth = width;
                this.mHeight = height;
            }
        } else {
            throw new RuntimeException("Invalid resolution " + width + "/" + height);
        }
    }

    public int getWidth() {
        synchronized (this) {
            return this.mWidth;
        }
    }

    public int getHeight() {
        synchronized (this) {
            return this.mHeight;
        }
    }

    public void setVisible(boolean visible) {
        synchronized (this) {
            this.mIsVisible = visible;
        }
    }

    public boolean isVisible() {
        synchronized (this) {
            return this.mIsVisible;
        }
    }

    public void setDisplayMethod(int flag) {
        synchronized (this) {
            this.mDisplayMethod = flag;
        }
    }

    public int getDisplayMethod() {
        synchronized (this) {
            return this.mDisplayMethod;
        }
    }

    public void setCallback(CameraSurfaceController.Callback callback) {
        synchronized (this) {
            this.mRenderCallback = callback;
        }
    }

    public CameraSurfaceController.Callback getCallback() {
        synchronized (this) {
            return this.mRenderCallback;
        }
    }

    public void addFlag(int flag) {
        synchronized (this) {
            this.mFlags |= flag;
        }
    }

    public void removeFlag(int flag) {
        synchronized (this) {
            this.mFlags &= ~flag;
        }
    }

    public int getFlags() {
        synchronized (this) {
            return this.mFlags;
        }
    }

    public void calculateViewPort(int sw, int sh, Rect rect) {
        if (rect == null) {
            rect = new Rect();
        }

        int dw = this.getWidth();
        int dh = this.getHeight();
        int temp1 = dw * sh;
        int temp2 = sw * dh;
        int display_method = this.getDisplayMethod();
        int width;
        int height;
        if ((display_method & FullScreen) != 0) {
            if (temp1 < temp2) {
                width = dw;
                height = dw * sh / sw;
            } else {
                width = dh * sw / sh;
                height = dh;
            }
        } else if ((display_method & ScaleEnabled) != 0) {
            if (temp1 < temp2) {
                width = dh * sw / sh;
                height = dh;
            } else {
                width = dw;
                height = dw * sh / sw;
            }
        } else {
            width = dw;
            height = dh;
        }

        int left;
        if ((display_method & LeftAlign) != 0) {
            left = 0;
        } else if ((display_method & RightAlign) != 0) {
            left = dw - width;
        } else {
            left = (dw - width) / 2;
        }

        int top;
        if ((display_method & TopAlign) != 0) {
            top = 0;
        } else if ((display_method & BottomAlign) != 0) {
            top = dh - height;
        } else {
            top = (dh - height) / 2;
        }

        rect.set(left, top, left + width, top + height);
    }

    public boolean isCodecController() {
        return this.mCodecController;
    }

    public void setCodecController(boolean isCodec) {
        this.mCodecController = isCodec;
    }

    public interface Callback {
        void onSurfaceRendered();
    }
}
