package com.yjt.zeuslivepush.ref;


import com.yjt.zeuslivepush.utils.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class AtomicRefCounted implements Releasable {
    private final AtomicInteger _RefCount = new AtomicInteger();

    public AtomicRefCounted() {
        this._RefCount.set(1);
    }

    public void reset() {
        Assert.assertEquals(0, this._RefCount.get());
        this._RefCount.set(1);
    }

    public final void ref() {
        int rv = this._RefCount.incrementAndGet();
        Assert.assertGreaterThan(rv, 1);
    }

    protected abstract void onLastRef();

    public final void release() {
        int rv = this._RefCount.decrementAndGet();
        if(rv == 0) {
            this.onLastRef();
        } else {
            Assert.assertGreaterThan(rv, 0);
        }
    }
}
