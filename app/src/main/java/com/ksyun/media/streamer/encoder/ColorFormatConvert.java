package com.ksyun.media.streamer.encoder;

import com.ksyun.media.streamer.util.LibraryLoader;
import java.nio.ByteBuffer;

public class ColorFormatConvert {
    public static native int I420ToRGBA(ByteBuffer byteBuffer, int i, int i2, int i3, ByteBuffer byteBuffer2);

    public static native int RGBAToBGR8(ByteBuffer byteBuffer, int i, int i2, int i3, ByteBuffer byteBuffer2);

    public static native int RGBAToI420(ByteBuffer byteBuffer, int i, int i2, int i3, ByteBuffer byteBuffer2);

    static {
        LibraryLoader.load();
    }
}
