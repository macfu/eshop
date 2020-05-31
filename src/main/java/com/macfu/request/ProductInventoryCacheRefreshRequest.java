package com.macfu.request;

import com.macfu.model.ProductInventory;
import com.macfu.service.ProductInventoryService;

public class ProductInventoryCacheRefreshRequest implements Request {

    // 商品id
    private Integer productId;
    // 商品库存service
    private ProductInventoryService productInventoryService;
    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService,
                                               boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.getProductInventory(productId);
        System.out.println("------日志------：已查询到商品最新的库存数量，商品id=" + productId + "，商品库存数量=" + productInventory.getInventoryCnt());
        // 将最新的商品库存数量，刷新到redis缓存中去
        productInventoryService.setProduceInventoryCache(productInventory);
    }

    @Override
    public Integer getProduceId() {
        return productId;
    }

    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
