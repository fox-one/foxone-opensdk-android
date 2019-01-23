package com.fox.one.support.common.concurrent;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangyinghao on 2018/4/16.
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final ThreadGroup mGroup;
    private final AtomicInteger mThreadNumber = new AtomicInteger(1);
    private final String mNamePrefix;

    DefaultThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        mGroup = (null != securityManager) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        mNamePrefix = "pool_" + POOL_NUMBER.getAndIncrement() + "_thread_";
    }

    @Override
    public Thread newThread(@NonNull Runnable r) {
        Thread thread = new Thread(mGroup, r, mNamePrefix + mThreadNumber.getAndIncrement(), 0);
        thread.setDaemon(true);
        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        return thread;
    }
}
