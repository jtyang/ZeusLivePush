package com.ksyun.media.streamer.util.gles;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.ksyun.media.player.b;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Scanner;

public class GlUtil {
    public static final String BASE_FRAGMENT_SHADER_BODY = "precision mediump float;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    public static final String BASE_VERTEX_SHADER = "uniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
    public static final String FRAGMENT_SHADER_HEADER = "uniform sampler2D sTexture;\n";
    public static final String FRAGMENT_SHADER_OES_HEADER = "#extension GL_OES_EGL_image_external : require\nuniform samplerExternalOES sTexture;\n";
    public static final String TRANSFORM_VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
    private static final String a = "GlUtil";
    private static final int b = 4;

    private GlUtil() {
    }

    public static int createProgram(String str, String str2) {
        int loadShader = loadShader(35633, str);
        if (loadShader == 0) {
            return 0;
        }
        int loadShader2 = loadShader(35632, str2);
        if (loadShader2 == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        checkGlError("glCreateProgram");
        if (glCreateProgram == 0) {
            Log.e(a, "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, loadShader);
        checkGlError("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, loadShader2);
        checkGlError("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        Log.e(a, "Could not link program: ");
        Log.e(a, GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        checkGlError("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.e(a, "Could not compile shader " + i + ":");
        Log.e(a, " " + GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public static void checkGlError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            String str2 = str + ": glError 0x" + Integer.toHexString(glGetError);
            Log.e(a, str2);
            throw new RuntimeException(str2);
        }
    }

    public static void checkLocation(int i, String str) {
        if (i < 0) {
            throw new RuntimeException("Unable to locate '" + str + "' in program");
        }
    }

    public static FloatBuffer createFloatBuffer(float[] fArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length * b);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.position(0);
        return asFloatBuffer;
    }

    public static int createOESTextureObject() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        checkGlError("glGenTextures");
        int i = iArr[0];
        GLES20.glBindTexture(36197, i);
        checkGlError("glBindTexture " + i);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkGlError("glTexParameter");
        return i;
    }

    public static int loadTexture(Bitmap bitmap, int i) {
        int[] iArr = new int[1];
        if (i == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
            Log.d(a, "New Texture " + iArr[0]);
        } else {
            GLES20.glBindTexture(3553, i);
            GLUtils.texSubImage2D(3553, 0, 0, 0, bitmap);
            iArr[0] = i;
        }
        return iArr[0];
    }

    public static int loadTexture(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int[] iArr = new int[1];
        if (i3 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, byteBuffer);
            Log.d(a, "New Texture " + iArr[0]);
        } else {
            GLES20.glBindTexture(3553, i3);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, byteBuffer);
            iArr[0] = i3;
        }
        return iArr[0];
    }

    public static String loadShader(String str, Context context) {
        try {
            InputStream open = context.getAssets().open(str);
            String a = a(open);
            open.close();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return b.d;
        }
    }

    private static String a(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : b.d;
    }
}
