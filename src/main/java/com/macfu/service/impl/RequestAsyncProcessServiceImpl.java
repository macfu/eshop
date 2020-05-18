package com.macfu.service.impl;

import com.macfu.request.Request;
import com.macfu.request.RequestQueue;
import com.macfu.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    @Override
    public void process(Request request) {

    }

    /**
     * 获取请求到的内存队列
     *
     * @param productId
     * @return
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        // 先获取produceId的hash值
        String key = String.valueOf(productId);
        int h;
        /**
         * 对hash值取模，将hash值路由到指定的队列中，比如内存队列大小8
         * 则用内存队列的数量对hash取模之后，结果一定是在0~7之间
         * 所以任何一个商品id都会被固定路由到同一个内存队列中去
         */
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }
}
