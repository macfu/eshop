package com.macfu.request;

import com.macfu.model.ProductInventory;
import com.macfu.service.ProductInventoryService;

public class ProductInventoryCacheRefreshRequest implements Request {

    // 商品id
    private Integer productId;
    // 商品库存service
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.getProductInventory(productId);
        // 将最新的商品库存数量，刷新到redis缓存中去
        productInventoryService.setProduceInventoryCache(productInventory);
    }
}
