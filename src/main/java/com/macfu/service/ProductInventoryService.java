package com.macfu.service;

import com.macfu.model.ProductInventory;

/**
 * 商品库存接口
 */
public interface ProductInventoryService {

    /**
     * 更新商品库存
     *
     * @param productInventory
     */
    void updateProduceInventory(ProductInventory productInventory);

    /**
     * 删除Redis中的商品库存的缓存
     *
     * @param productInventory
     */
    void removeProduceInventoryCache(ProductInventory productInventory);

    /**
     * 根据商品id查询商品库存
     *
     * @param productId
     * @return
     */
    ProductInventory getProductInventory(Integer productId);

    /**
     * 设置商品缓存
     *
     * @param productInventory
     */
    void setProduceInventoryCache(ProductInventory productInventory);
}
