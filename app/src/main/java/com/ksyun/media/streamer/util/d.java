package com.ksyun.media.streamer.util;

import android.util.Log;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/* compiled from: FrameBufferCache */
public class d {
    private static final String a = "FrameBufferCache";
    private BlockingQueue<ByteBuffer> b;

    public d(int i, int i2) {
        this.b = new ArrayBlockingQueue(i);
        for (int i3 = 0; i3 < i; i3++) {
            this.b.add(ByteBuffer.allocateDirect(i2));
        }
    }

    public ByteBuffer a(int i) {
        return a(i, -1);
    }

    public ByteBuffer a(int i, long j) {
        ByteBuffer byteBuffer;
        if (j == -1) {
            try {
                byteBuffer = (ByteBuffer) this.b.poll();
            } catch (Exception e) {
                Log.d(a, "get cache buffer interrupted");
                byteBuffer = null;
            }
        } else {
            byteBuffer = (ByteBuffer) this.b.poll(j, TimeUnit.MILLISECONDS);
        }
        if (byteBuffer != null) {
            if (i > byteBuffer.capacity()) {
                int capacity = byteBuffer.capacity() * 2;
                while (capacity < i) {
                    capacity *= 2;
                }
                Log.d(a, "realloc buffer size from " + byteBuffer.capacity() + " to " + capacity);
                byteBuffer = ByteBuffer.allocateDirect(capacity);
            }
            byteBuffer.clear();
        }
        return byteBuffer;
    }

    public void a(ByteBuffer byteBuffer) {
        this.b.add(byteBuffer);
    }

    public void a() {
        this.b.clear();
    }
}
