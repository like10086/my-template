package com.personal.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ThreadPoolUtil
 * @Description: 线程池工具类
 * @author: like
 * @date 2024/6/5 15:11
 */
@Component
public class ThreadPoolUtil {
    // 默认线程池配置
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 10;
    private static final long DEFAULT_KEEP_ALIVE_TIME = 60L;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    private static final int DEFAULT_QUEUE_CAPACITY = 100;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();
    private static final RejectedExecutionHandler DEFAULT_REJECTED_EXECUTION_HANDLER = new ThreadPoolExecutor.AbortPolicy();

    /**
     * 创建一个配置化的线程池
     *
     * @param corePoolSize  核心线程数
     * @param maxPoolSize   最大线程数
     * @param keepAliveTime 空闲线程存活时间
     * @param timeUnit      时间单位
     * @param queueCapacity 工作队列容量
     * @param threadFactory 线程工厂
     * @param handler       拒绝策略
     * @return 配置好的线程池实例
     */
    public ExecutorService createThreadPool(int corePoolSize,
                                            int maxPoolSize,
                                            long keepAliveTime,
                                            TimeUnit timeUnit,
                                            int queueCapacity,
                                            ThreadFactory threadFactory,
                                            RejectedExecutionHandler handler) {
        if (corePoolSize <= 0 || maxPoolSize <= 0 || keepAliveTime < 0 || queueCapacity <= 0) {
            throw new IllegalArgumentException("Invalid thread pool configuration");
        }
        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingQueue<>(queueCapacity),
                threadFactory != null ? threadFactory : DEFAULT_THREAD_FACTORY,
                handler != null ? handler : DEFAULT_REJECTED_EXECUTION_HANDLER
        );
    }

    /**
     * 创建一个具有默认配置的线程池
     *
     * @return 配置好的线程池实例
     */
    public ExecutorService createDefaultThreadPool() {
        return createThreadPool(DEFAULT_CORE_POOL_SIZE,
                DEFAULT_MAXIMUM_POOL_SIZE,
                DEFAULT_KEEP_ALIVE_TIME,
                DEFAULT_TIME_UNIT,
                DEFAULT_QUEUE_CAPACITY,
                DEFAULT_THREAD_FACTORY,
                DEFAULT_REJECTED_EXECUTION_HANDLER);
    }

    /**
     * 关闭线程池，包括正在等待的任务
     *
     * @param executorService 线程池实例
     */
    public void shutdownThreadPool(ExecutorService executorService) {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            try {
                if (!executorService.awaitTermination(60, DEFAULT_TIME_UNIT)) {
                    System.err.println("ThreadPool did not terminate");
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
