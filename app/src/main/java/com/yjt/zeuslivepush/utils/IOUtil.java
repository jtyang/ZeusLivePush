package com.yjt.zeuslivepush.utils;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class IOUtil {
    private static final int BUF_SIZE = 2048;

    public IOUtil() {
    }

    public static String toString(Readable r) throws IOException {
        return toStringBuilder(r).toString();
    }

    private static StringBuilder toStringBuilder(Readable r) throws IOException {
        StringBuilder sb = new StringBuilder();
        copy(r, sb);
        return sb;
    }

    public static long copy(Readable from, Appendable to) throws IOException {
        checkNotNull(from);
        checkNotNull(to);
        CharBuffer buf = CharBuffer.allocate(2048);
        long total = 0L;

        while(from.read(buf) != -1) {
            buf.flip();
            to.append(buf);
            total += (long)buf.remaining();
            buf.clear();
        }

        return total;
    }

    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

}
