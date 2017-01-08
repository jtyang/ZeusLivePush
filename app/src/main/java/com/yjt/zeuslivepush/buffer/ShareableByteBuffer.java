package com.yjt.zeuslivepush.buffer;


import com.yjt.zeuslivepush.ref.Releasable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ShareableByteBuffer extends AtomicShareable<ShareableByteBuffer>
        implements ByteArrayHolder, Releasable {
    private final ByteBuffer _Data;

    public ShareableByteBuffer(Recycler<ShareableByteBuffer> recycler, int size, boolean direct) {
        super(recycler);
        ByteBuffer data;
        if (direct) {
            data = ByteBuffer.allocateDirect(size);
        } else {
            data = ByteBuffer.allocate(size);
        }

        data.position(0);
        data.limit(0);

        data.order(ByteOrder.nativeOrder());

        this._Data = data;
    }

    public ByteBuffer getData() {
        return this._Data;
    }

    protected void onLastRef() {
        this._Recycler.recycle(this);
    }

    public byte[] getByteArray() {
        return this._Data.array();
    }

}
