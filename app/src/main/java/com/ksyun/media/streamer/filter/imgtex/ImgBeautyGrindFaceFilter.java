package com.ksyun.media.streamer.filter.imgtex;

import android.opengl.GLES20;
import com.ksyun.media.streamer.framework.ImgTexFormat;
import com.ksyun.media.streamer.framework.ImgTexFrame;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.ksyun.media.streamer.util.gles.GlUtil;
import java.nio.ByteBuffer;

public class ImgBeautyGrindFaceFilter extends ImgTexFilter {
    private int a;
    private int[] b;
    private int[] c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private ImgTexFormat j;
    private short[] k;
    private short[] l;

    public ImgBeautyGrindFaceFilter(GLRender gLRender) {
        super(gLRender, GlUtil.BASE_VERTEX_SHADER, 9);
        this.a = -1;
        this.b = new int[]{-1};
        this.c = new int[]{-1};
        this.k = new short[]{(short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 1, (short) 1, (short) 1, (short) 255, (short) 1, (short) 1, (short) 1, (short) 255, (short) 1, (short) 1, (short) 1, (short) 255, (short) 2, (short) 2, (short) 2, (short) 255, (short) 2, (short) 2, (short) 2, (short) 255, (short) 2, (short) 2, (short) 2, (short) 255, (short) 3, (short) 3, (short) 3, (short) 255, (short) 4, (short) 4, (short) 4, (short) 255, (short) 4, (short) 4, (short) 4, (short) 255, (short) 5, (short) 5, (short) 5, (short) 255, (short) 6, (short) 6, (short) 6, (short) 255, (short) 7, (short) 7, (short) 7, (short) 255, (short) 7, (short) 7, (short) 7, (short) 255, (short) 8, (short) 8, (short) 8, (short) 255, (short) 9, (short) 9, (short) 9, (short) 255, (short) 10, (short) 10, (short) 10, (short) 255, (short) 12, (short) 12, (short) 12, (short) 255, (short) 13, (short) 13, (short) 13, (short) 255, (short) 14, (short) 14, (short) 14, (short) 255, (short) 15, (short) 15, (short) 15, (short) 255, (short) 16, (short) 16, (short) 16, (short) 255, (short) 18, (short) 18, (short) 18, (short) 255, (short) 19, (short) 19, (short) 19, (short) 255, (short) 21, (short) 21, (short) 21, (short) 255, (short) 22, (short) 22, (short) 22, (short) 255, (short) 23, (short) 23, (short) 23, (short) 255, (short) 25, (short) 25, (short) 25, (short) 255, (short) 27, (short) 27, (short) 27, (short) 255, (short) 28, (short) 28, (short) 28, (short) 255, (short) 30, (short) 30, (short) 30, (short) 255, (short) 32, (short) 32, (short) 32, (short) 255, (short) 33, (short) 33, (short) 33, (short) 255, (short) 35, (short) 35, (short) 35, (short) 255, (short) 37, (short) 37, (short) 37, (short) 255, (short) 39, (short) 39, (short) 39, (short) 255, (short) 41, (short) 41, (short) 41, (short) 255, (short) 43, (short) 43, (short) 43, (short) 255, (short) 45, (short) 45, (short) 45, (short) 255, (short) 46, (short) 46, (short) 46, (short) 255, (short) 48, (short) 48, (short) 48, (short) 255, (short) 50, (short) 50, (short) 50, (short) 255, (short) 53, (short) 53, (short) 53, (short) 255, (short) 55, (short) 55, (short) 55, (short) 255, (short) 57, (short) 57, (short) 57, (short) 255, (short) 59, (short) 59, (short) 59, (short) 255, (short) 61, (short) 61, (short) 61, (short) 255, (short) 63, (short) 63, (short) 63, (short) 255, (short) 65, (short) 65, (short) 65, (short) 255, (short) 67, (short) 67, (short) 67, (short) 255, (short) 70, (short) 70, (short) 70, (short) 255, (short) 72, (short) 72, (short) 72, (short) 255, (short) 74, (short) 74, (short) 74, (short) 255, (short) 76, (short) 76, (short) 76, (short) 255, (short) 78, (short) 78, (short) 78, (short) 255, (short) 81, (short) 81, (short) 81, (short) 255, (short) 83, (short) 83, (short) 83, (short) 255, (short) 85, (short) 85, (short) 85, (short) 255, (short) 87, (short) 87, (short) 87, (short) 255, (short) 89, (short) 89, (short) 89, (short) 255, (short) 91, (short) 91, (short) 91, (short) 255, (short) 93, (short) 93, (short) 93, (short) 255, (short) 96, (short) 96, (short) 96, (short) 255, (short) 98, (short) 98, (short) 98, (short) 255, (short) 100, (short) 100, (short) 100, (short) 255, (short) 102, (short) 102, (short) 102, (short) 255, (short) 104, (short) 104, (short) 104, (short) 255, (short) 106, (short) 106, (short) 106, (short) 255, (short) 108, (short) 108, (short) 108, (short) 255, (short) 110, (short) 110, (short) 110, (short) 255, (short) 112, (short) 112, (short) 112, (short) 255, (short) 114, (short) 114, (short) 114, (short) 255, (short) 115, (short) 115, (short) 115, (short) 255, (short) 117, (short) 117, (short) 117, (short) 255, (short) 119, (short) 119, (short) 119, (short) 255, (short) 121, (short) 121, (short) 121, (short) 255, (short) 122, (short) 122, (short) 122, (short) 255, (short) 124, (short) 124, (short) 124, (short) 255, (short) 126, (short) 126, (short) 126, (short) 255, (short) 127, (short) 127, (short) 127, (short) 255, (short) 129, (short) 129, (short) 129, (short) 255, (short) 130, (short) 130, (short) 130, (short) 255, (short) 132, (short) 132, (short) 132, (short) 255, (short) 133, (short) 133, (short) 133, (short) 255, (short) 135, (short) 135, (short) 135, (short) 255, (short) 136, (short) 136, (short) 136, (short) 255, (short) 137, (short) 137, (short) 137, (short) 255, (short) 139, (short) 139, (short) 139, (short) 255, (short) 140, (short) 140, (short) 140, (short) 255, (short) 141, (short) 141, (short) 141, (short) 255, (short) 143, (short) 143, (short) 143, (short) 255, (short) 144, (short) 144, (short) 144, (short) 255, (short) 145, (short) 145, (short) 145, (short) 255, (short) 146, (short) 146, (short) 146, (short) 255, (short) 148, (short) 148, (short) 148, (short) 255, (short) 149, (short) 149, (short) 149, (short) 255, (short) 150, (short) 150, (short) 150, (short) 255, (short) 151, (short) 151, (short) 151, (short) 255, (short) 152, (short) 152, (short) 152, (short) 255, (short) 153, (short) 153, (short) 153, (short) 255, (short) 154, (short) 154, (short) 154, (short) 255, (short) 155, (short) 155, (short) 155, (short) 255, (short) 156, (short) 156, (short) 156, (short) 255, (short) 158, (short) 158, (short) 158, (short) 255, (short) 159, (short) 159, (short) 159, (short) 255, (short) 160, (short) 160, (short) 160, (short) 255, (short) 161, (short) 161, (short) 161, (short) 255, (short) 162, (short) 162, (short) 162, (short) 255, (short) 163, (short) 163, (short) 163, (short) 255, (short) 163, (short) 163, (short) 163, (short) 255, (short) 164, (short) 164, (short) 164, (short) 255, (short) 165, (short) 165, (short) 165, (short) 255, (short) 166, (short) 166, (short) 166, (short) 255, (short) 167, (short) 167, (short) 167, (short) 255, (short) 168, (short) 168, (short) 168, (short) 255, (short) 169, (short) 169, (short) 169, (short) 255, (short) 170, (short) 170, (short) 170, (short) 255, (short) 171, (short) 171, (short) 171, (short) 255, (short) 172, (short) 172, (short) 172, (short) 255, (short) 173, (short) 173, (short) 173, (short) 255, (short) 173, (short) 173, (short) 173, (short) 255, (short) 174, (short) 174, (short) 174, (short) 255, (short) 175, (short) 175, (short) 175, (short) 255, (short) 176, (short) 176, (short) 176, (short) 255, (short) 177, (short) 177, (short) 177, (short) 255, (short) 178, (short) 178, (short) 178, (short) 255, (short) 178, (short) 178, (short) 178, (short) 255, (short) 179, (short) 179, (short) 179, (short) 255, (short) 180, (short) 180, (short) 180, (short) 255, (short) 181, (short) 181, (short) 181, (short) 255, (short) 182, (short) 182, (short) 182, (short) 255, (short) 182, (short) 182, (short) 182, (short) 255, (short) 183, (short) 183, (short) 183, (short) 255, (short) 184, (short) 184, (short) 184, (short) 255, (short) 185, (short) 185, (short) 185, (short) 255, (short) 186, (short) 186, (short) 186, (short) 255, (short) 186, (short) 186, (short) 186, (short) 255, (short) 187, (short) 187, (short) 187, (short) 255, (short) 188, (short) 188, (short) 188, (short) 255, (short) 189, (short) 189, (short) 189, (short) 255, (short) 189, (short) 189, (short) 189, (short) 255, (short) 190, (short) 190, (short) 190, (short) 255, (short) 191, (short) 191, (short) 191, (short) 255, (short) 191, (short) 191, (short) 191, (short) 255, (short) 192, (short) 192, (short) 192, (short) 255, (short) 193, (short) 193, (short) 193, (short) 255, (short) 194, (short) 194, (short) 194, (short) 255, (short) 194, (short) 194, (short) 194, (short) 255, (short) 195, (short) 195, (short) 195, (short) 255, (short) 196, (short) 196, (short) 196, (short) 255, (short) 196, (short) 196, (short) 196, (short) 255, (short) 197, (short) 197, (short) 197, (short) 255, (short) 198, (short) 198, (short) 198, (short) 255, (short) 198, (short) 198, (short) 198, (short) 255, (short) 199, (short) 199, (short) 199, (short) 255, (short) 200, (short) 200, (short) 200, (short) 255, (short) 200, (short) 200, (short) 200, (short) 255, (short) 201, (short) 201, (short) 201, (short) 255, (short) 202, (short) 202, (short) 202, (short) 255, (short) 203, (short) 203, (short) 203, (short) 255, (short) 203, (short) 203, (short) 203, (short) 255, (short) 204, (short) 204, (short) 204, (short) 255, (short) 205, (short) 205, (short) 205, (short) 255, (short) 205, (short) 205, (short) 205, (short) 255, (short) 206, (short) 206, (short) 206, (short) 255, (short) 206, (short) 206, (short) 206, (short) 255, (short) 207, (short) 207, (short) 207, (short) 255, (short) 208, (short) 208, (short) 208, (short) 255, (short) 208, (short) 208, (short) 208, (short) 255, (short) 209, (short) 209, (short) 209, (short) 255, (short) 210, (short) 210, (short) 210, (short) 255, (short) 210, (short) 210, (short) 210, (short) 255, (short) 211, (short) 211, (short) 211, (short) 255, (short) 212, (short) 212, (short) 212, (short) 255, (short) 212, (short) 212, (short) 212, (short) 255, (short) 213, (short) 213, (short) 213, (short) 255, (short) 214, (short) 214, (short) 214, (short) 255, (short) 214, (short) 214, (short) 214, (short) 255, (short) 215, (short) 215, (short) 215, (short) 255, (short) 216, (short) 216, (short) 216, (short) 255, (short) 216, (short) 216, (short) 216, (short) 255, (short) 217, (short) 217, (short) 217, (short) 255, (short) 217, (short) 217, (short) 217, (short) 255, (short) 218, (short) 218, (short) 218, (short) 255, (short) 219, (short) 219, (short) 219, (short) 255, (short) 219, (short) 219, (short) 219, (short) 255, (short) 220, (short) 220, (short) 220, (short) 255, (short) 221, (short) 221, (short) 221, (short) 255, (short) 221, (short) 221, (short) 221, (short) 255, (short) 222, (short) 222, (short) 222, (short) 255, (short) 222, (short) 222, (short) 222, (short) 255, (short) 223, (short) 223, (short) 223, (short) 255, (short) 224, (short) 224, (short) 224, (short) 255, (short) 224, (short) 224, (short) 224, (short) 255, (short) 225, (short) 225, (short) 225, (short) 255, (short) 226, (short) 226, (short) 226, (short) 255, (short) 226, (short) 226, (short) 226, (short) 255, (short) 227, (short) 227, (short) 227, (short) 255, (short) 227, (short) 227, (short) 227, (short) 255, (short) 228, (short) 228, (short) 228, (short) 255, (short) 229, (short) 229, (short) 229, (short) 255, (short) 229, (short) 229, (short) 229, (short) 255, (short) 230, (short) 230, (short) 230, (short) 255, (short) 230, (short) 230, (short) 230, (short) 255, (short) 231, (short) 231, (short) 231, (short) 255, (short) 232, (short) 232, (short) 232, (short) 255, (short) 232, (short) 232, (short) 232, (short) 255, (short) 233, (short) 233, (short) 233, (short) 255, (short) 233, (short) 233, (short) 233, (short) 255, (short) 234, (short) 234, (short) 234, (short) 255, (short) 234, (short) 234, (short) 234, (short) 255, (short) 235, (short) 235, (short) 235, (short) 255, (short) 236, (short) 236, (short) 236, (short) 255, (short) 236, (short) 236, (short) 236, (short) 255, (short) 237, (short) 237, (short) 237, (short) 255, (short) 237, (short) 237, (short) 237, (short) 255, (short) 238, (short) 238, (short) 238, (short) 255, (short) 238, (short) 238, (short) 238, (short) 255, (short) 239, (short) 239, (short) 239, (short) 255, (short) 239, (short) 239, (short) 239, (short) 255, (short) 240, (short) 240, (short) 240, (short) 255, (short) 241, (short) 241, (short) 241, (short) 255, (short) 241, (short) 241, (short) 241, (short) 255, (short) 242, (short) 242, (short) 242, (short) 255, (short) 242, (short) 242, (short) 242, (short) 255, (short) 243, (short) 243, (short) 243, (short) 255, (short) 243, (short) 243, (short) 243, (short) 255, (short) 244, (short) 244, (short) 244, (short) 255, (short) 244, (short) 244, (short) 244, (short) 255, (short) 245, (short) 245, (short) 245, (short) 255, (short) 245, (short) 245, (short) 245, (short) 255, (short) 246, (short) 246, (short) 246, (short) 255, (short) 247, (short) 247, (short) 247, (short) 255, (short) 247, (short) 247, (short) 247, (short) 255, (short) 248, (short) 248, (short) 248, (short) 255, (short) 248, (short) 248, (short) 248, (short) 255, (short) 249, (short) 249, (short) 249, (short) 255, (short) 249, (short) 249, (short) 249, (short) 255, (short) 250, (short) 250, (short) 250, (short) 255, (short) 250, (short) 250, (short) 250, (short) 255, (short) 251, (short) 251, (short) 251, (short) 255, (short) 251, (short) 251, (short) 251, (short) 255, (short) 252, (short) 252, (short) 252, (short) 255, (short) 252, (short) 252, (short) 252, (short) 255, (short) 253, (short) 253, (short) 253, (short) 255, (short) 253, (short) 253, (short) 253, (short) 255, (short) 254, (short) 254, (short) 254, (short) 255, (short) 255, (short) 255, (short) 255, (short) 255};
        this.l = new short[]{(short) 0, (short) 0, (short) 0, (short) 255, (short) 0, (short) 0, (short) 0, (short) 255, (short) 1, (short) 1, (short) 1, (short) 255, (short) 2, (short) 2, (short) 2, (short) 255, (short) 2, (short) 2, (short) 2, (short) 255, (short) 3, (short) 3, (short) 3, (short) 255, (short) 4, (short) 4, (short) 4, (short) 255, (short) 4, (short) 4, (short) 4, (short) 255, (short) 5, (short) 5, (short) 5, (short) 255, (short) 6, (short) 6, (short) 6, (short) 255, (short) 6, (short) 6, (short) 6, (short) 255, (short) 7, (short) 7, (short) 7, (short) 255, (short) 8, (short) 8, (short) 8, (short) 255, (short) 8, (short) 8, (short) 8, (short) 255, (short) 9, (short) 9, (short) 9, (short) 255, (short) 10, (short) 10, (short) 10, (short) 255, (short) 11, (short) 11, (short) 11, (short) 255, (short) 11, (short) 11, (short) 11, (short) 255, (short) 12, (short) 12, (short) 12, (short) 255, (short) 13, (short) 13, (short) 13, (short) 255, (short) 13, (short) 13, (short) 13, (short) 255, (short) 14, (short) 14, (short) 14, (short) 255, (short) 15, (short) 15, (short) 15, (short) 255, (short) 15, (short) 15, (short) 15, (short) 255, (short) 16, (short) 16, (short) 16, (short) 255, (short) 17, (short) 17, (short) 17, (short) 255, (short) 17, (short) 17, (short) 17, (short) 255, (short) 18, (short) 18, (short) 18, (short) 255, (short) 19, (short) 19, (short) 19, (short) 255, (short) 20, (short) 20, (short) 20, (short) 255, (short) 20, (short) 20, (short) 20, (short) 255, (short) 21, (short) 21, (short) 21, (short) 255, (short) 22, (short) 22, (short) 22, (short) 255, (short) 22, (short) 22, (short) 22, (short) 255, (short) 23, (short) 23, (short) 23, (short) 255, (short) 24, (short) 24, (short) 24, (short) 255, (short) 24, (short) 24, (short) 24, (short) 255, (short) 25, (short) 25, (short) 25, (short) 255, (short) 26, (short) 26, (short) 26, (short) 255, (short) 27, (short) 27, (short) 27, (short) 255, (short) 27, (short) 27, (short) 27, (short) 255, (short) 28, (short) 28, (short) 28, (short) 255, (short) 29, (short) 29, (short) 29, (short) 255, (short) 29, (short) 29, (short) 29, (short) 255, (short) 30, (short) 30, (short) 30, (short) 255, (short) 31, (short) 31, (short) 31, (short) 255, (short) 32, (short) 32, (short) 32, (short) 255, (short) 32, (short) 32, (short) 32, (short) 255, (short) 33, (short) 33, (short) 33, (short) 255, (short) 34, (short) 34, (short) 34, (short) 255, (short) 34, (short) 34, (short) 34, (short) 255, (short) 35, (short) 35, (short) 35, (short) 255, (short) 36, (short) 36, (short) 36, (short) 255, (short) 37, (short) 37, (short) 37, (short) 255, (short) 37, (short) 37, (short) 37, (short) 255, (short) 38, (short) 38, (short) 38, (short) 255, (short) 39, (short) 39, (short) 39, (short) 255, (short) 39, (short) 39, (short) 39, (short) 255, (short) 40, (short) 40, (short) 40, (short) 255, (short) 41, (short) 41, (short) 41, (short) 255, (short) 42, (short) 42, (short) 42, (short) 255, (short) 42, (short) 42, (short) 42, (short) 255, (short) 43, (short) 43, (short) 43, (short) 255, (short) 44, (short) 44, (short) 44, (short) 255, (short) 45, (short) 45, (short) 45, (short) 255, (short) 45, (short) 45, (short) 45, (short) 255, (short) 46, (short) 46, (short) 46, (short) 255, (short) 47, (short) 47, (short) 47, (short) 255, (short) 47, (short) 47, (short) 47, (short) 255, (short) 48, (short) 48, (short) 48, (short) 255, (short) 49, (short) 49, (short) 49, (short) 255, (short) 50, (short) 50, (short) 50, (short) 255, (short) 50, (short) 50, (short) 50, (short) 255, (short) 51, (short) 51, (short) 51, (short) 255, (short) 52, (short) 52, (short) 52, (short) 255, (short) 53, (short) 53, (short) 53, (short) 255, (short) 53, (short) 53, (short) 53, (short) 255, (short) 54, (short) 54, (short) 54, (short) 255, (short) 55, (short) 55, (short) 55, (short) 255, (short) 56, (short) 56, (short) 56, (short) 255, (short) 57, (short) 57, (short) 57, (short) 255, (short) 57, (short) 57, (short) 57, (short) 255, (short) 58, (short) 58, (short) 58, (short) 255, (short) 59, (short) 59, (short) 59, (short) 255, (short) 60, (short) 60, (short) 60, (short) 255, (short) 60, (short) 60, (short) 60, (short) 255, (short) 61, (short) 61, (short) 61, (short) 255, (short) 62, (short) 62, (short) 62, (short) 255, (short) 63, (short) 63, (short) 63, (short) 255, (short) 63, (short) 63, (short) 63, (short) 255, (short) 64, (short) 64, (short) 64, (short) 255, (short) 65, (short) 65, (short) 65, (short) 255, (short) 66, (short) 66, (short) 66, (short) 255, (short) 67, (short) 67, (short) 67, (short) 255, (short) 67, (short) 67, (short) 67, (short) 255, (short) 68, (short) 68, (short) 68, (short) 255, (short) 69, (short) 69, (short) 69, (short) 255, (short) 70, (short) 70, (short) 70, (short) 255, (short) 71, (short) 71, (short) 71, (short) 255, (short) 71, (short) 71, (short) 71, (short) 255, (short) 72, (short) 72, (short) 72, (short) 255, (short) 73, (short) 73, (short) 73, (short) 255, (short) 74, (short) 74, (short) 74, (short) 255, (short) 75, (short) 75, (short) 75, (short) 255, (short) 75, (short) 75, (short) 75, (short) 255, (short) 76, (short) 76, (short) 76, (short) 255, (short) 77, (short) 77, (short) 77, (short) 255, (short) 78, (short) 78, (short) 78, (short) 255, (short) 79, (short) 79, (short) 79, (short) 255, (short) 80, (short) 80, (short) 80, (short) 255, (short) 80, (short) 80, (short) 80, (short) 255, (short) 81, (short) 81, (short) 81, (short) 255, (short) 82, (short) 82, (short) 82, (short) 255, (short) 83, (short) 83, (short) 83, (short) 255, (short) 84, (short) 84, (short) 84, (short) 255, (short) 85, (short) 85, (short) 85, (short) 255, (short) 85, (short) 85, (short) 85, (short) 255, (short) 86, (short) 86, (short) 86, (short) 255, (short) 87, (short) 87, (short) 87, (short) 255, (short) 88, (short) 88, (short) 88, (short) 255, (short) 89, (short) 89, (short) 89, (short) 255, (short) 90, (short) 90, (short) 90, (short) 255, (short) 91, (short) 91, (short) 91, (short) 255, (short) 91, (short) 91, (short) 91, (short) 255, (short) 92, (short) 92, (short) 92, (short) 255, (short) 93, (short) 93, (short) 93, (short) 255, (short) 94, (short) 94, (short) 94, (short) 255, (short) 95, (short) 95, (short) 95, (short) 255, (short) 96, (short) 96, (short) 96, (short) 255, (short) 97, (short) 97, (short) 97, (short) 255, (short) 98, (short) 98, (short) 98, (short) 255, (short) 98, (short) 98, (short) 98, (short) 255, (short) 99, (short) 99, (short) 99, (short) 255, (short) 100, (short) 100, (short) 100, (short) 255, (short) 101, (short) 101, (short) 101, (short) 255, (short) 102, (short) 102, (short) 102, (short) 255, (short) 103, (short) 103, (short) 103, (short) 255, (short) 104, (short) 104, (short) 104, (short) 255, (short) 105, (short) 105, (short) 105, (short) 255, (short) 106, (short) 106, (short) 106, (short) 255, (short) 107, (short) 107, (short) 107, (short) 255, (short) 108, (short) 108, (short) 108, (short) 255, (short) 108, (short) 108, (short) 108, (short) 255, (short) 109, (short) 109, (short) 109, (short) 255, (short) 110, (short) 110, (short) 110, (short) 255, (short) 111, (short) 111, (short) 111, (short) 255, (short) 112, (short) 112, (short) 112, (short) 255, (short) 113, (short) 113, (short) 113, (short) 255, (short) 114, (short) 114, (short) 114, (short) 255, (short) 115, (short) 115, (short) 115, (short) 255, (short) 116, (short) 116, (short) 116, (short) 255, (short) 117, (short) 117, (short) 117, (short) 255, (short) 118, (short) 118, (short) 118, (short) 255, (short) 119, (short) 119, (short) 119, (short) 255, (short) 120, (short) 120, (short) 120, (short) 255, (short) 121, (short) 121, (short) 121, (short) 255, (short) 122, (short) 122, (short) 122, (short) 255, (short) 123, (short) 123, (short) 123, (short) 255, (short) 124, (short) 124, (short) 124, (short) 255, (short) 125, (short) 125, (short) 125, (short) 255, (short) 126, (short) 126, (short) 126, (short) 255, (short) 127, (short) 127, (short) 127, (short) 255, (short) 128, (short) 128, (short) 128, (short) 255, (short) 129, (short) 129, (short) 129, (short) 255, (short) 130, (short) 130, (short) 130, (short) 255, (short) 131, (short) 131, (short) 131, (short) 255, (short) 132, (short) 132, (short) 132, (short) 255, (short) 133, (short) 133, (short) 133, (short) 255, (short) 134, (short) 134, (short) 134, (short) 255, (short) 135, (short) 135, (short) 135, (short) 255, (short) 136, (short) 136, (short) 136, (short) 255, (short) 137, (short) 137, (short) 137, (short) 255, (short) 138, (short) 138, (short) 138, (short) 255, (short) 139, (short) 139, (short) 139, (short) 255, (short) 140, (short) 140, (short) 140, (short) 255, (short) 141, (short) 141, (short) 141, (short) 255, (short) 142, (short) 142, (short) 142, (short) 255, (short) 143, (short) 143, (short) 143, (short) 255, (short) 144, (short) 144, (short) 144, (short) 255, (short) 145, (short) 145, (short) 145, (short) 255, (short) 146, (short) 146, (short) 146, (short) 255, (short) 148, (short) 148, (short) 148, (short) 255, (short) 149, (short) 149, (short) 149, (short) 255, (short) 150, (short) 150, (short) 150, (short) 255, (short) 151, (short) 151, (short) 151, (short) 255, (short) 152, (short) 152, (short) 152, (short) 255, (short) 153, (short) 153, (short) 153, (short) 255, (short) 154, (short) 154, (short) 154, (short) 255, (short) 155, (short) 155, (short) 155, (short) 255, (short) 156, (short) 156, (short) 156, (short) 255, (short) 157, (short) 157, (short) 157, (short) 255, (short) 159, (short) 159, (short) 159, (short) 255, (short) 160, (short) 160, (short) 160, (short) 255, (short) 161, (short) 161, (short) 161, (short) 255, (short) 162, (short) 162, (short) 162, (short) 255, (short) 163, (short) 163, (short) 163, (short) 255, (short) 164, (short) 164, (short) 164, (short) 255, (short) 165, (short) 165, (short) 165, (short) 255, (short) 167, (short) 167, (short) 167, (short) 255, (short) 168, (short) 168, (short) 168, (short) 255, (short) 169, (short) 169, (short) 169, (short) 255, (short) 170, (short) 170, (short) 170, (short) 255, (short) 171, (short) 171, (short) 171, (short) 255, (short) 172, (short) 172, (short) 172, (short) 255, (short) 174, (short) 174, (short) 174, (short) 255, (short) 175, (short) 175, (short) 175, (short) 255, (short) 176, (short) 176, (short) 176, (short) 255, (short) 177, (short) 177, (short) 177, (short) 255, (short) 178, (short) 178, (short) 178, (short) 255, (short) 179, (short) 179, (short) 179, (short) 255, (short) 181, (short) 181, (short) 181, (short) 255, (short) 182, (short) 182, (short) 182, (short) 255, (short) 183, (short) 183, (short) 183, (short) 255, (short) 184, (short) 184, (short) 184, (short) 255, (short) 186, (short) 186, (short) 186, (short) 255, (short) 187, (short) 187, (short) 187, (short) 255, (short) 188, (short) 188, (short) 188, (short) 255, (short) 189, (short) 189, (short) 189, (short) 255, (short) 190, (short) 190, (short) 190, (short) 255, (short) 192, (short) 192, (short) 192, (short) 255, (short) 193, (short) 193, (short) 193, (short) 255, (short) 194, (short) 194, (short) 194, (short) 255, (short) 195, (short) 195, (short) 195, (short) 255, (short) 197, (short) 197, (short) 197, (short) 255, (short) 198, (short) 198, (short) 198, (short) 255, (short) 199, (short) 199, (short) 199, (short) 255, (short) 200, (short) 200, (short) 200, (short) 255, (short) 202, (short) 202, (short) 202, (short) 255, (short) 203, (short) 203, (short) 203, (short) 255, (short) 204, (short) 204, (short) 204, (short) 255, (short) 205, (short) 205, (short) 205, (short) 255, (short) 207, (short) 207, (short) 207, (short) 255, (short) 208, (short) 208, (short) 208, (short) 255, (short) 209, (short) 209, (short) 209, (short) 255, (short) 211, (short) 211, (short) 211, (short) 255, (short) 212, (short) 212, (short) 212, (short) 255, (short) 213, (short) 213, (short) 213, (short) 255, (short) 214, (short) 214, (short) 214, (short) 255, (short) 216, (short) 216, (short) 216, (short) 255, (short) 217, (short) 217, (short) 217, (short) 255, (short) 218, (short) 218, (short) 218, (short) 255, (short) 219, (short) 219, (short) 219, (short) 255, (short) 221, (short) 221, (short) 221, (short) 255, (short) 222, (short) 222, (short) 222, (short) 255, (short) 223, (short) 223, (short) 223, (short) 255, (short) 225, (short) 225, (short) 225, (short) 255, (short) 226, (short) 226, (short) 226, (short) 255, (short) 227, (short) 227, (short) 227, (short) 255, (short) 228, (short) 228, (short) 228, (short) 255, (short) 230, (short) 230, (short) 230, (short) 255, (short) 231, (short) 231, (short) 231, (short) 255, (short) 232, (short) 232, (short) 232, (short) 255, (short) 234, (short) 234, (short) 234, (short) 255, (short) 235, (short) 235, (short) 235, (short) 255, (short) 236, (short) 236, (short) 236, (short) 255, (short) 238, (short) 238, (short) 238, (short) 255};
    }

    public boolean isGrindRatioSupported() {
        return true;
    }

    public boolean isWhitenRatioSupported() {
        return true;
    }

    public int getSinkPinNum() {
        return 2;
    }

    protected void onFormatChanged(ImgTexFormat imgTexFormat) {
        this.j = imgTexFormat;
    }

    private void a() {
        int i;
        byte[] bArr = new byte[1024];
        for (i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) this.k[i];
        }
        GLES20.glActiveTexture(33987);
        GLES20.glGenTextures(1, this.b, 0);
        GLES20.glBindTexture(3553, this.b[0]);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glTexImage2D(3553, 0, 6408, StreamerConstants.CODEC_ID_AAC, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
        bArr = new byte[1024];
        for (i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) this.l[i];
        }
        GLES20.glActiveTexture(33988);
        GLES20.glGenTextures(1, this.c, 0);
        GLES20.glBindTexture(3553, this.c[0]);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        GLES20.glTexImage2D(3553, 0, 6408, StreamerConstants.CODEC_ID_AAC, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
    }

    protected void onInitialized() {
        this.d = getUniformLocation("faceTexture");
        this.e = getUniformLocation("lightenTex");
        this.f = getUniformLocation("darkenTex");
        this.g = getUniformLocation("grindRatio");
        this.h = getUniformLocation("whitenRatio");
        this.i = getUniformLocation("singleStepOffset");
        GLES20.glUniform2f(this.i, 1.0f / ((float) this.j.width), 1.0f / ((float) this.j.height));
        a();
    }

    protected void onDrawArraysPre() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, this.a);
        GLES20.glUniform1i(this.d, 2);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.b[0]);
        GLES20.glUniform1i(this.e, 3);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, this.c[0]);
        GLES20.glUniform1i(this.f, 4);
        GLES20.glUniform1f(this.h, this.mWhitenRatio);
        GLES20.glUniform1f(this.g, this.mGrindRatio);
    }

    public void onDraw(ImgTexFrame[] imgTexFrameArr) {
        this.a = imgTexFrameArr[1].textureId;
        super.onDraw(imgTexFrameArr);
    }

    protected void onDrawArraysAfter() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, 0);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, 0);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, 0);
        GLES20.glActiveTexture(33984);
    }

    protected void onRelease() {
        super.onRelease();
        GLES20.glDeleteTextures(1, this.b, 0);
        GLES20.glDeleteTextures(1, this.c, 0);
        this.b[0] = -1;
        this.c[0] = -1;
    }
}
