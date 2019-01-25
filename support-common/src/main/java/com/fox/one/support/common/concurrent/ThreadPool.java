package com.fox.one.support.common.concurrent;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zhangyinghao on 2018/4/16.
 */
public final class ThreadPool {
    private static final int AWAIT_TERMINATION_TIMEOUT = 10000; // ms
    private String mName;// ThreadPool's Name
    private ThreadPoolExecutor mThreadPoolExecutor;
    private static final int DEF_CORE_POOL_SIZE = 8;
    private static final int DEF_MAX_POOL_SIZE = 64;
    private static final int KEEP_ALIVE_TIME = 3;

    public ThreadPool(@NonNull String name) {
        this(name, DEF_CORE_POOL_SIZE, DEF_MAX_POOL_SIZE);
    }

    public ThreadPool(@NonNull String name, int corePoolSize, int maxPoolSize) {
        mName = name;
        mThreadPoolExecutor = new ThreadPoolExecutor(DEF_CORE_POOL_SIZE,
                DEF_MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new DefaultThreadFactory());
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * 添加一个后台任务
     */
    public synchronized void add(Runnable runnable) {
        assertState();
        assertValidity(runnable);
        mThreadPoolExecutor.execute(runnable);
    }

    /**
     * 添加一个Callable任务
     *
     * @param callable
     * @param <T>      type
     * @return Future
     */
    public synchronized <T> Future<T> submit(Callable<T> callable) {
        assertState();
        assertValidity(callable);
        return mThreadPoolExecutor.submit(callable);
    }

    /**
     * 删除一个任务
     *
     * @param runnable
     * @return true 表示找到了了这个任务
     */
    public synchronized boolean remove(Runnable runnable) {
        mThreadPoolExecutor.purge();
        return mThreadPoolExecutor.remove(runnable);
    }

    /**
     * 添加一个Runnable任务
     *
     * @param runnable
     * @return Future
     */
    public synchronized Future<?> submit(Runnable runnable) {
        assertState();
        assertValidity(runnable);
        return mThreadPoolExecutor.submit(runnable);
    }

    /**
     * 关闭pool取消等待中的任务，无法关闭的等待完成，然后结束线程池
     *
     * @return List 被取消的任务
     * @throws InterruptedException
     */
    public synchronized List<Runnable> shutDownNowAndTermination() throws InterruptedException {
        List<Runnable> canceledRunnables = mThreadPoolExecutor.shutdownNow();
        mThreadPoolExecutor.awaitTermination(AWAIT_TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS);
        return canceledRunnables;
    }

    /**
     * 关闭并等待任务结束，然后关掉线程池
     *
     * @throws InterruptedException
     */
    public synchronized void shutDownAndTermination() throws InterruptedException {
        mThreadPoolExecutor.shutdown();
        mThreadPoolExecutor.awaitTermination(AWAIT_TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    /**
     * 是否已经关闭了 关闭后不能加入新任务 但已有任务可能还在继续执行
     *
     * @return true 表示已关闭了
     */
    public boolean isShutDown() {
        return mThreadPoolExecutor.isShutdown();
    }

    /**
     * 是否已经结束了 所有子任务都已结束
     *
     * @return true 表示已经结束了
     */
    public boolean isTerminated() {
        return mThreadPoolExecutor.isTerminated();
    }

    private void assertState() {
        if (mThreadPoolExecutor.isShutdown()) {
            throw new IllegalStateException("this ThreadPool has been shutdown!!");
        }
    }

    private void assertValidity(Object runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("runnable must not be null !!");
        }
    }

    public Executor getExecutor() {
        return mThreadPoolExecutor;
    }
}
