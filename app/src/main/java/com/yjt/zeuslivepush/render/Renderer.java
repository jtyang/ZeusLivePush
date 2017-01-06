package com.yjt.zeuslivepush.render;

import android.graphics.Rect;

import com.yjt.zeuslivepush.gl.Texture;


/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public interface Renderer {
    void realize();

    void unrealize();

    void setInputTransform(float[] var1);

    void setInputSize(int var1, int var2, Rect var3);

    void setInputTexture(Texture var1);

    void isMirrored(boolean var1);

    void setRenderOutput(Renderer.RenderOutput var1);

    void draw();

    public interface RenderOutput {
        void beginFrame();

        void endFrame();
    }
}
