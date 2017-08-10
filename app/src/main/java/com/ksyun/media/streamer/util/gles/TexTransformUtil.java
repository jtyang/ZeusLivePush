package com.ksyun.media.streamer.util.gles;

import android.graphics.PointF;
import java.nio.FloatBuffer;

public class TexTransformUtil {
    public static final int COORDS_COUNT = 4;
    public static final int COORDS_PER_VERTEX = 2;
    public static final int COORDS_STRIDE = 8;
    public static final float[] TEX_COORDS;
    public static final FloatBuffer TEX_COORDS_BUF;
    public static final float[] TEX_MIRROR_COORDS;
    public static final FloatBuffer TEX_MIRROR_COORDS_BUF;
    public static final float[] VERTEX_COORDS;
    public static final FloatBuffer VERTEX_COORDS_BUF;
    public static final float[] VERTEX_MIRROR_COORDS;
    public static final FloatBuffer VERTEX_MIRROR_COORDS_BUF;
    protected static final int a = 4;

    static {
        TEX_COORDS = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
        TEX_COORDS_BUF = GlUtil.createFloatBuffer(TEX_COORDS);
        TEX_MIRROR_COORDS = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
        TEX_MIRROR_COORDS_BUF = GlUtil.createFloatBuffer(TEX_MIRROR_COORDS);
        VERTEX_COORDS = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
        VERTEX_COORDS_BUF = GlUtil.createFloatBuffer(VERTEX_COORDS);
        VERTEX_MIRROR_COORDS = new float[]{1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
        VERTEX_MIRROR_COORDS_BUF = GlUtil.createFloatBuffer(VERTEX_MIRROR_COORDS);
    }

    private TexTransformUtil() {
    }

    public static FloatBuffer getTexCoordsBuf() {
        return TEX_COORDS_BUF;
    }

    public static FloatBuffer getTexMirrorCoordsBuf() {
        return TEX_MIRROR_COORDS_BUF;
    }

    public static FloatBuffer getVFlipTexCoordsBuf() {
        return getTexCoordsBuf(0.0f, 0.0f, 0, false, true);
    }

    public static FloatBuffer getHFlipTexCoordsBuf() {
        return getTexCoordsBuf(0.0f, 0.0f, 0, true, false);
    }

    public static FloatBuffer getRotateTexCoordsBuf(int i) {
        return getTexCoordsBuf(0.0f, 0.0f, i, false, false);
    }

    public static FloatBuffer getCropTexCoordsBuf(float f, float f2) {
        return getTexCoordsBuf(f, f2, 0, false, false);
    }

    public static FloatBuffer getTexCoordsBuf(float f, float f2, int i, boolean z, boolean z2) {
        int i2;
        Object obj = new float[TEX_COORDS.length];
        Object obj2 = new float[TEX_COORDS.length];
        System.arraycopy(TEX_COORDS, 0, obj, 0, TEX_COORDS.length);
        System.arraycopy(TEX_COORDS, 0, obj2, 0, TEX_COORDS.length);
        if (i % 180 == 0) {
            boolean z3 = z2;
            z2 = z;
            z = z3;
            float f3 = f2;
            f2 = f;
            f = f3;
        }
        if (!(f2 == 0.0f && f == 0.0f)) {
            for (i2 = 0; i2 < obj.length; i2 += COORDS_PER_VERTEX) {
                obj[i2] = a(obj[i2], f2);
                obj[i2 + 1] = a(obj[i2 + 1], f);
            }
        }
        if (z2) {
            for (i2 = 0; i2 < obj.length; i2 += COORDS_PER_VERTEX) {
                obj[i2] = a(obj[i2]);
            }
        }
        if (z) {
            for (i2 = 0; i2 < obj.length; i2 += COORDS_PER_VERTEX) {
                obj[i2 + 1] = a(obj[i2 + 1]);
            }
        }
        switch (i) {
            case 90:
                System.arraycopy(obj, a, obj2, 0, COORDS_PER_VERTEX);
                System.arraycopy(obj, 0, obj2, COORDS_PER_VERTEX, COORDS_PER_VERTEX);
                System.arraycopy(obj, 6, obj2, a, COORDS_PER_VERTEX);
                System.arraycopy(obj, COORDS_PER_VERTEX, obj2, 6, COORDS_PER_VERTEX);
                break;
            case 180:
                System.arraycopy(obj, 6, obj2, 0, COORDS_PER_VERTEX);
                System.arraycopy(obj, a, obj2, COORDS_PER_VERTEX, COORDS_PER_VERTEX);
                System.arraycopy(obj, COORDS_PER_VERTEX, obj2, a, COORDS_PER_VERTEX);
                System.arraycopy(obj, 0, obj2, 6, COORDS_PER_VERTEX);
                break;
            case 270:
                System.arraycopy(obj, COORDS_PER_VERTEX, obj2, 0, COORDS_PER_VERTEX);
                System.arraycopy(obj, 6, obj2, COORDS_PER_VERTEX, COORDS_PER_VERTEX);
                System.arraycopy(obj, 0, obj2, a, COORDS_PER_VERTEX);
                System.arraycopy(obj, a, obj2, 6, COORDS_PER_VERTEX);
                break;
            default:
                System.arraycopy(obj, 0, obj2, 0, obj.length);
                break;
        }
        return GlUtil.createFloatBuffer(obj2);
    }

    private static float a(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    private static float a(float f) {
        return 1.0f - f;
    }

    public static FloatBuffer getVertexCoordsBuf() {
        return VERTEX_COORDS_BUF;
    }

    public static FloatBuffer getVertexMirrorCoordsBuf() {
        return VERTEX_MIRROR_COORDS_BUF;
    }

    public static PointF calCrop(float f, float f2) {
        PointF pointF = new PointF();
        if (f > f2) {
            pointF.x = 1.0f - (f2 / f);
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = 1.0f - (f / f2);
        }
        pointF.x /= 2.0f;
        pointF.y /= 2.0f;
        return pointF;
    }

    public static FloatBuffer getVertexArray() {
        return VERTEX_COORDS_BUF;
    }

    public static int getVertexCount() {
        return a;
    }

    public static int getVertexStride() {
        return COORDS_STRIDE;
    }

    public static int getCoordsPerVertex() {
        return COORDS_PER_VERTEX;
    }
}
