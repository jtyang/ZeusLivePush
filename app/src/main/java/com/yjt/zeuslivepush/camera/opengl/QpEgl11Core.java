package com.yjt.zeuslivepush.camera.opengl;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLExt;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/**
 * EGL核心类
 * <p>
 * EGL是OpenGL ES和底层的native window system之间的接口
 * 官方文档：https://www.khronos.org/egl
 * 参考文章：http://www.cnblogs.com/kiffa/archive/2013/02/21/2921123.html
 * <p>
 * 参考demo：
 * https://github.com/google/grafika/blob/master/src/com/android/grafika/gles/EglCore.java
 * https://android.googlesource.com/platform/cts/+/jb-mr2-release/tests/tests/media/src/android/media/cts/OutputSurface.java
 * <p>
 * a,选择显示设备
 * b, 选择像素格式。
 * c, 选择某些特性，比如如果你打算画中国水墨画，你需要额外指定宣纸和毛笔。
 * d, 申请显存。
 * e, 创建上下文（Context），上下文本质上是一组状态的集合，描述了在某个特定时刻系统的状态， 用于处理暂停、恢复、销毁、重建等情况；
 * f, 指定当前的环境为绘制环境 。
 * 总体流程上，EGL按顺序分为若干步骤：
 * 1, 选择显示设备display，即上述的a.
 * 2，指定特性，包括上述的像素格式(b)和特定特性(c)，根据指定的特性来获取多个满足这些特性的config（比如你指定RGB中的R为5bits，
 * 那么可能会有RGB_565和RGB_555两种像素格式均满足此特性），用户从这些可用的configs中选择一个，根据display和config获取绘制用
 * 的buffer(一般为显存），即上述的d。
 * 3，使用display、config、buffer来创建context，及即上述的e.
 * 4, 使用display、buffer、context 设置当前的渲染环境，即上述的f.
 * <p>
 * AUTHOR: yangjiantong
 * DATE: 2016/12/18
 */
public class QpEgl11Core {
    private static final String TAG = "NnEgl11Core";
    public static final int FLAG_RECORDABLE = 0x01;
    // Android-specific extension.
    private static final int EGL_RECORDABLE_ANDROID = 0x3142;
    private EGL10 _egl = (EGL10) EGLContext.getEGL();
    private EGLDisplay mEGLDisplay = EGL11.EGL_NO_DISPLAY;
    private EGLContext mEGLContext = EGL11.EGL_NO_CONTEXT;
    private EGLConfig mEGLConfig = null;
    private String mEglExtensions;

    public QpEgl11Core(EGLContext sharedContext, int flags) {
        if (sharedContext == null) {
            sharedContext = EGL11.EGL_NO_CONTEXT;
        }

        //选择显示设备
        this.mEGLDisplay = this._egl.eglGetDisplay(EGL11.EGL_DEFAULT_DISPLAY);
        if (this.mEGLDisplay == EGL11.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        if (!this._egl.eglInitialize(this.mEGLDisplay, null)) {//param2:version中存放EGL 版本号，int[0]为主版本号，int[1]为子版本号
            this.mEGLDisplay = null;
            throw new RuntimeException("unable to initialize EGL14");
        }

        //egl.eglQueryString()用来查询EGL的相关信息，详见这里：http://www.khronos.org/registry/egl/sdk/docs/man/xhtml/
        //支持的EGL扩展
        this.mEglExtensions = this._egl.eglQueryString(this.mEGLDisplay, EGL11.EGL_EXTENSIONS);
        EGLConfig config = this.getConfig(flags, 2);
        if (config == null) {
            throw new RuntimeException("Unable to find a suitable EGLConfig");
        }

        int[] attrib2_list = new int[]{EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL11.EGL_NONE};
        //创建上下文（Context），上下文本质上是一组状态的集合，描述了在某个特定时刻系统的状态， 用于处理暂停、恢复、销毁、重建等情况
        EGLContext context = this._egl.eglCreateContext(this.mEGLDisplay, config, sharedContext, attrib2_list);
        this.checkEglError("eglCreateContext");
        this.mEGLConfig = config;
        this.mEGLContext = context;
    }

    private EGLConfig getConfig(int flags, int version) {
        /**
         * 虽然Android使用（实现）的是EGL 1.4（从打印的版本号中可见）, 但在Android 4.2（API 17)以前的版本没有EGL14，
         * 只有EGL10和EGL11，而这两个版本是不支持OpengGL ES 2.x的，因此在老版本中某些ES 2.x相关的常量参数只能用手写
         * 的硬编码代替，典型的如设定EGL渲染类型API的参数EGL10.EGL_RENDERABLE_TYPE，这个属性用不同的赋值指定的不同
         * 的渲染API，包括OpenGL，OpenGL ES 1.x, OpenGL ES 2.x，OpenVG等，
         * 如果采用ES 2.0，应该设置此值为： EGL14.EGL_OPENGL_ES2_BIT，
         * 但是在Android 4.2之前，没有EGL14接口，只能采取手写的硬编码来指定，类似： EGL_RENDERABLE_TYPE = 4;
         */
        byte renderableType = EGL14.EGL_OPENGL_ES2_BIT;
        int[] attribList = new int[]{
                EGL11.EGL_RED_SIZE, 8,//指定RGB中的R大小（bits）
                EGL11.EGL_GREEN_SIZE, 8,//指定G大小
                EGL11.EGL_BLUE_SIZE, 8,//指定B大小
                EGL11.EGL_ALPHA_SIZE, 8,//指定alpha大小
                EGL11.EGL_RENDERABLE_TYPE, renderableType,//指定渲染api类别,这里或者是硬编码的4，或者是EGL14.EGL_OPENGL_ES2_BIT
                EGL11.EGL_SURFACE_TYPE, 5,
                EGL11.EGL_NONE, 0,
                EGL11.EGL_NONE //总是以EGL10.EGL_NONE结尾
        };
        if ((flags & FLAG_RECORDABLE) != 0 && this.mEglExtensions != null) {
            if (!this.mEglExtensions.contains("EGL_ANDROID_recordable") || Build.MODEL.equals("M351") && Build.VERSION.SDK_INT == 19) {
                Log.d(TAG, "Extensions = " + this.mEglExtensions);
            } else {
                attribList[attribList.length - 3] = EGL_RECORDABLE_ANDROID;
                attribList[attribList.length - 2] = FLAG_RECORDABLE;
            }
        }

        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        if (!this._egl.eglChooseConfig(this.mEGLDisplay, attribList, configs, configs.length, numConfigs)) {
            Log.w(TAG, "unable to find RGB8888 / " + version + " EGLConfig");
            return null;
        } else {
            return configs[0];
        }
    }

    public void release() {
        if (this.mEGLContext != EGL11.EGL_NO_CONTEXT) {
            this._egl.eglMakeCurrent(this.mEGLDisplay, EGL11.EGL_NO_SURFACE, EGL11.EGL_NO_SURFACE, EGL11.EGL_NO_CONTEXT);
            this._egl.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
            this._egl.eglTerminate(this.mEGLDisplay);
        }

        this.mEGLDisplay = EGL11.EGL_NO_DISPLAY;
        this.mEGLContext = EGL11.EGL_NO_CONTEXT;
        this.mEGLConfig = null;
    }

    public void releaseSurface(EGLSurface eglSurface) {
        this._egl.eglDestroySurface(this.mEGLDisplay, eglSurface);
    }

    /**
     * 获取显存
     *
     * @param surface
     * @return
     */
    public EGLSurface createWindowSurface(Object surface) {
        if (!(surface instanceof Surface) && !(surface instanceof SurfaceTexture) && !(surface instanceof SurfaceHolder)) {
            throw new RuntimeException("invalid surface: " + surface);
        }
        int[] surfaceAttributes = new int[]{EGL11.EGL_NONE};
        EGLSurface eglSurface = this._egl.eglCreateWindowSurface(this.mEGLDisplay, this.mEGLConfig, surface, surfaceAttributes);
        this.checkEglError("eglCreateWindowSurface");
        if (eglSurface == null) {
            throw new RuntimeException("surface was null");
        }
        return eglSurface;
    }

    public EGLSurface createPbufferSurface(int w, int h) {
        int[] surfaceAttributes = new int[]{EGL11.EGL_WIDTH, w, EGL11.EGL_HEIGHT, h, EGL11.EGL_NONE};
        EGLSurface eglSurface = this._egl.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, surfaceAttributes);
        if (eglSurface == EGL10.EGL_NO_SURFACE) {
            this.checkEglError("createPbufferSurface");
            throw new RuntimeException("surface was null");
        } else {
            return eglSurface;
        }
    }

    /**
     * 设置为当前的渲染环境
     *
     * @param eglSurface
     */
    public void makeCurrent(EGLSurface eglSurface) {
        if (this.mEGLDisplay == EGL11.EGL_NO_DISPLAY) {
            Log.d(TAG, "NOTE: makeCurrent w/o display");
        }

        if (!this._egl.eglMakeCurrent(this.mEGLDisplay, eglSurface, eglSurface, this.mEGLContext)) {
            this.checkEglError("Make current");
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    /**
     * 交换前后缓冲
     *
     * @param eglSurface
     * @return
     */
    public boolean swapBuffers(EGLSurface eglSurface) {
        return this._egl.eglSwapBuffers(this.mEGLDisplay, eglSurface);
    }

    public void setPresentationTime(long nsecs) {
        if (this.mEglExtensions != null && this.mEglExtensions.contains("EGL_ANDROID_presentation_time")) {
            android.opengl.EGLDisplay display = EGL14.eglGetCurrentDisplay();
            android.opengl.EGLSurface surface = EGL14.eglGetCurrentSurface(EGL11.EGL_DRAW);
            EGLExt.eglPresentationTimeANDROID(display, surface, nsecs);
        }
    }

    private void checkEglError(String msg) {
        int error;
        if ((error = this._egl.eglGetError()) != EGL11.EGL_SUCCESS) {
            throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
        }
    }
}
