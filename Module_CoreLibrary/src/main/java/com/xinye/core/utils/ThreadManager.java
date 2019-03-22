package com.xinye.core.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理类
 */
public class ThreadManager {
    private static ThreadPool mThreadPool;

    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadManager.class) {
                if (mThreadPool == null) {
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    int threadCount = 3 * cpuCount + 1;
                    mThreadPool = new ThreadPool(threadCount, threadCount, 0);
                }
            }
        }
        return mThreadPool;
    }

    public static class ThreadPool {
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private ThreadPoolExecutor poolExecutor;

        private ThreadPool(int corePoolSize, int maxinumPoolSize, int keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maxinumPoolSize;
            this.keepAliveTime = keepAliveTime;

        }

        public Runnable execute(Runnable r) {
            if (poolExecutor == null) {
                poolExecutor = new ThreadPoolExecutor(
                        corePoolSize,//核心线程数
                        maximumPoolSize,//最大线程数
                        keepAliveTime,//2
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy()//异常处理策略
                );

            }
            poolExecutor.execute(r);
            return r;

        }

        public void remove(Runnable runnable) {
            if (poolExecutor != null && runnable != null) {
                poolExecutor.remove(runnable);
            }
        }


    }
}
