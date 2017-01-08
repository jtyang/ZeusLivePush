package com.yjt.zeuslivepush.buffer;

public class ByteBufferAllocator implements Allocator<ShareableByteBuffer> {
    private final int _Size;
    private final boolean _Direct;

    public ByteBufferAllocator(int size, boolean direct) {

        this._Size = size;

        this._Direct = direct;
    }

    public ShareableByteBuffer allocate(Recycler<ShareableByteBuffer> recycler, ShareableByteBuffer reused) {

        if (reused != null) {

            reused.reset();

            return reused;
        }

        return new ShareableByteBuffer(recycler, this._Size, this._Direct);
    }

    public void release(ShareableByteBuffer object) {
    }


    public int getSize() {
        return this._Size;
    }
}

