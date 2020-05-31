package com.macfu.controller;

import com.macfu.model.ProductInventory;
import com.macfu.request.ProductInventoryCacheRefreshRequest;
import com.macfu.request.ProductInventoryDBUpdateRequest;
import com.macfu.request.Request;
import com.macfu.service.ProductInventoryService;
import com.macfu.service.RequestAsyncProcessService;
import com.macfu.vo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (1)一个更新商品库存的请求过来，此时会先删除redis中的缓存，然后模拟卡顿5s
 * (2)在这个卡顿的5s内，我们发送一个商品缓存的的读请求，因为此时redis中没有缓存，就会请求数据库中最新的数据刷新到缓存中
 * (3)此时读请求会路由到同一个队列中，阻塞中，不会执行
 * (4)等5s过后，写请求完成了数据库的更新之后，读请求才会继续执行
 * (5)读请求执行的时候，会将最新的库存从数据库中查询出来，然后更新到缓存中_
 */
@RestController
public class ProductInventoryController {

    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;
    @Resource
    private ProductInventoryService productInventoryService;

    @RequestMapping("/updateProductInventory")
    public Response updateProductInventory(ProductInventory productInventory) {
        System.out.println("------日志------: 接受到的更新商品库存请求，商品id=" + productInventory.getProductId() + ",商品数量=" + productInventory.getInventoryCnt());

        Response response = null;
        try {
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    @RequestMapping("/getProductInventory")
    public ProductInventory getPrductInventory(Integer produceId) {
        System.out.println("------日志------：接收到一个商品的库存读请求，商品id=" + produceId);
        ProductInventory productInventory = null;
        try {
            ProductInventoryCacheRefreshRequest request = new ProductInventoryCacheRefreshRequest(produceId, productInventoryService, false);
            requestAsyncProcessService.process(request);
            // 将请求扔给service异步处理以后，就需要while（true）一会，在这里hang住
            // 去尝试等待前面有商品库存更新处理，同时缓存刷新操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0;
            long waitTime = 0;
            while (true) {
                if (waitTime > 200) {
                    break;
                }
                productInventory = productInventoryService.getProduceInventoryCache(produceId);

                if (productInventory != null) {
                    System.out.println("------日志------：在200ms内读取到了redis中的库存缓存，商品id=" + productInventory.getProductId() + ",商品库存=" + productInventory.getInventoryCnt());
                    return productInventory;
                } else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime = startTime;
                }
            }
            // 直接从数据库中读取数据
            productInventory = productInventoryService.getProductInventory(produceId);
            if (productInventory != null) {
                // 将缓存刷新一下
                // 在这个过程中，实际上是一个读操作的过程，但是没有放在队列中串行执行，还是有数据不一致的问题
                request = new ProductInventoryCacheRefreshRequest(produceId, productInventoryService, true);
                requestAsyncProcessService.process(request);

                /**
                 * 1.上一次也是读请求，数据写入redis，但是redis中的lru算法给清理掉了，标志位还是false
                 * 所以此时下一个读请求还是从缓存中拿不到数据的，再放一个读request进队列，让数据去刷新一下
                 * 2.可能在200ms内，就是读请求在队列中一直积压着，没有等到它执行，所以就直接查数据库，然后给队列中塞进去一个刷新缓存的请求
                 * 3.数据库本身就没有这个数据，缓存穿透，，请求直接到达数据库
                 */
                return productInventory;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(produceId, -1L);
    }
}
