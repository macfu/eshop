package com.macfu.request;

import com.macfu.model.ProductInventory;
import com.macfu.service.ProductInventoryService;

/**
 * 1.删除缓存
 * 2.更新数据库
 */
public class ProductInventoryDBUpdateRequest implements Request {

    // 商品库存
    private ProductInventory productInventory;
    private ProductInventoryService productInventoryService;

    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        // 删除redis中的缓存
        productInventoryService.removeProduceInventoryCache(productInventory);
        // 修改数据库中的库存
        productInventoryService.updateProduceInventory(productInventory);
    }

    @Override
    public Integer getProduceId() {
        return productInventory.getProductId();
    }
}
