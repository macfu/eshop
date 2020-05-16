package com.macfu.thread;

import com.macfu.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 */
public class RequestProcessThread implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessThread(ArrayBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        while (true) {
            break;
        }
        return true;
    }
}
