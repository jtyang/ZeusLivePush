package com.yjt.zeuslivepush.buffer;


import com.yjt.zeuslivepush.ref.Releasable;

import java.util.ArrayList;


public class SynchronizedPool<T> implements Recycler<T>, Releasable {
    private final Allocator<T> _Allocator;
    private final OnBufferAvailableListener _OnBufferAvailableListener;
    private int _Limit;

    public SynchronizedPool(Allocator<T> alloc, OnBufferAvailableListener listener, int limit) {
        this._Allocator = alloc;
        this._OnBufferAvailableListener = listener;
        this._Limit = limit;
    }

    public SynchronizedPool(Allocator<T> alloc) {
        this(alloc, null, -1);
    }

    public Allocator<T> getAllocator() {
        return this._Allocator;
    }

    private final ArrayList<T> _Cache = new ArrayList();


    public synchronized T allocate() {
        if (!this._Cache.isEmpty()) {
            T item = this._Cache.remove(this._Cache.size() - 1);
            return (T) this._Allocator.allocate(this, item);
        }

        if (this._Limit == 0) {
            return null;
        }

        if (this._Limit > 0) {
            this._Limit -= 1;
        }

        return (T) this._Allocator.allocate(this, null);
    }


    public void recycle(T object) {
        boolean notify_available;
        synchronized (this) {
            notify_available = (this._Limit == 0) && (this._Cache.isEmpty());
            this._Cache.add(object);
        }
        if ((notify_available) && (this._OnBufferAvailableListener != null)) {
            this._OnBufferAvailableListener.onBufferAvailable(this);
        }
    }


    public synchronized void release() {
        for (T item : this._Cache) {
            this._Allocator.release(item);
        }
    }

    public interface OnBufferAvailableListener {
        void onBufferAvailable(SynchronizedPool<?> paramSynchronizedPool);
    }
}
