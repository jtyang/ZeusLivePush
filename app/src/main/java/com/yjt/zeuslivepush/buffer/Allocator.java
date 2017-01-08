package com.yjt.zeuslivepush.buffer;


public interface Allocator<T> {
    T allocate(Recycler<T> paramRecycler, T paramT);

    void release(T paramT);
}
