package com.yjt.zeuslivepush.buffer;


import com.yjt.zeuslivepush.ref.Releasable;

import java.util.ArrayList;

public class Pool<T> implements Recycler<T>, Releasable {
    private final Allocator<T> _Allocator;

    public Pool(Allocator<T> alloc) {
        this._Allocator = alloc;
    }

    public Allocator<T> getAllocator() {
        return this._Allocator;
    }

    private final ArrayList<T> _Cache = new ArrayList();


    public T allocate() {
        T item = this._Cache.isEmpty() ? null : this._Cache.remove(this._Cache.size() - 1);

        return (T) this._Allocator.allocate(this, item);
    }


    public void recycle(T object) {
        this._Cache.add(object);
    }


    public void release() {
        for (T item : this._Cache) {
            this._Allocator.release(item);
        }
    }
}
