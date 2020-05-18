package com.macfu.mapper;

import com.macfu.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

public interface ProductInventoryMapper {

    /**
     * 更新库存数量
     *
     * @param productInventory
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品id查询商品库存信息
     *
     * @param productId
     * @return
     */
    ProductInventory findProductInventory(@Param("productId") Integer productId);
}
