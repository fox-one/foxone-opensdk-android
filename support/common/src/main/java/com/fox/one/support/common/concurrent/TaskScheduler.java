package com.fox.one.support.common.concurrent;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * 简单的多任务并发调度，UI发起的并发任务建议用带Weak的方法
 * Created by zhangyinghao on 2018/4/16.
 */
public class TaskScheduler {

    private TaskScheduler() {
    }

    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static ThreadPool mThreadPool = new ThreadPool("task_scheduler");

    /**
     * 提交一个轻量异步任务
     *
     * @param backgroundTask
     */
    public static void execute(final Runnable backgroundTask) {
        if (backgroundTask != null) {
            mThreadPool.add(backgroundTask);
        }
    }

    /**
     * 提交一个异步任务，任务完成后执行一个前台任务
     *
     * @param backgroundTask
     * @param foregroundTask
     */
    public static void execute(final Runnable backgroundTask, final Runnable foregroundTask) {
        mThreadPool.add(new Runnable() {
            @Override
            public void run() {
                if (backgroundTask != null) {
                    backgroundTask.run();
                }
                if (foregroundTask != null) {
                    mHandler.post(foregroundTask);
                }
            }
        });
    }

    /**
     * 提交一个异步任务，任务完成后执行一个弱引用的前台任务
     *
     * @param backgroundTask
     * @param foregroundTask
     */
    public static void executeWeak(final Runnable backgroundTask, final Runnable foregroundTask) {
        mThreadPool.add(new Runnable() {
            @Override
            public void run() {
                if (backgroundTask != null) {
                    backgroundTask.run();
                }
                if (foregroundTask != null) {
                    mHandler.post(foregroundTask);
                }
            }
        });
    }

    /**
     * 使用Task对execute方法的封装
     *
     * @param task
     * @param <Input>  入参类型
     * @param <Output> 输出类型
     */
    public static <Input, Output> void execute(final Task<Input, Output> task) {
        if (task != null) {
            mThreadPool.add(new Runnable() {
                @Override
                public void run() {
                    final Output out = task.onDoInBackground(task.mInput);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            task.onPostExecuteForeground(out);
                        }
                    });
                }
            });
        }
    }

    /**
     * 使用Task对executeWeak方法的封装
     *
     * @param task
     * @param <Input>  入参类型
     * @param <Output> 输出类型
     */
    public static <Input, Output> void executeWeak(final Task<Input, Output> task) {
        if (task != null) {
            mThreadPool.add(new Runnable() {
                @Override
                public void run() {
                    final Output out = task.onDoInBackground(task.mInput);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            task.onPostExecuteForeground(out);
                        }
                    });
                }
            });
        }
    }

    public static Executor getExecutor(){
        return mThreadPool.getExecutor();
    }
}
