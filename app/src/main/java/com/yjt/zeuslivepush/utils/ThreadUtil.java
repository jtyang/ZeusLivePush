package com.yjt.zeuslivepush.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class ThreadUtil {

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException var2) {
            Assert.fail();
        }

    }

    public static boolean join(Thread thread, int millisec) {
        try {
            thread.join((long) millisec);
        } catch (InterruptedException var3) {
            Assert.fail();
        }

        return !thread.isAlive();
    }

    public static void wait(Object object) {
        try {
            object.wait();
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }

    public static void wait(Object object, long millis) {
        try {
            object.wait(millis);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    public static ExecutionException waitForCompletion(Future<?> task) {
        try {
            task.get();
        } catch (InterruptedException var2) {
            Assert.fail();
        } catch (ExecutionException var3) {
            return var3;
        }

        return null;
    }

    public static <T> T get(Future<T> task) {
        try {
            return task.get();
        } catch (InterruptedException var2) {
            return Assert.fail(var2);
        } catch (ExecutionException var3) {
            return Assert.fail(var3);
        }
    }

    public static <T> T take(BlockingQueue<T> queue) {
        try {
            return queue.take();
        } catch (InterruptedException var2) {
            return Assert.fail(var2);
        }
    }

    public static <T> T exchange(Exchanger<T> exchanger, T object) {
        try {
            return exchanger.exchange(object);
        } catch (InterruptedException var3) {
            return Assert.fail(var3);
        }
    }

    public static <T> T exchange(Exchanger<T> exchanger) {
        return exchange(exchanger, null);
    }
}
