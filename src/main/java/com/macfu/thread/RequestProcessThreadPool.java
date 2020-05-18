package com.macfu.thread;

import com.macfu.request.Request;
import com.macfu.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求处理线程池
 */
public class RequestProcessThreadPool {

    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public RequestProcessThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < 10; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            requestQueue.addQueue(queue);
            threadPool.submit(new RequestProcessThread(queue));
        }
    }

    /**
     * 单例模式获得RequestProcessThreadPool
     */
    public static class Singleton {
        private static RequestProcessThreadPool instance;

        static {
            instance = new RequestProcessThreadPool();
        }

        public static RequestProcessThreadPool getInstance() {
            return instance;
        }
    }

    public static RequestProcessThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化的便捷方法
     */
    public static void init() {
        getInstance();
    }

}
