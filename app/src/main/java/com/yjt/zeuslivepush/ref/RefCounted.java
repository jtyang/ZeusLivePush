package com.yjt.zeuslivepush.ref;


import com.yjt.zeuslivepush.utils.Assert;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class RefCounted implements Releasable {
    private int _RefCount = 1;

    public void reset() {
        Assert.assertEquals(0, this._RefCount);
        this._RefCount = 1;
    }

    public final void ref() {
        Assert.assertGreaterThan(this._RefCount, 0);
        ++this._RefCount;
    }

    protected abstract void onLastRef();

    public final void release() {
        --this._RefCount;
        if (this._RefCount == 0) {
            this.onLastRef();
        } else {
            Assert.assertGreaterThan(this._RefCount, 0);
        }
    }
}
