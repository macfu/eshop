package com.macfu.service.impl;

import com.macfu.dao.RedisDAO;
import com.macfu.mapper.ProductInventoryMapper;
import com.macfu.model.ProductInventory;
import com.macfu.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    private RedisDAO redisDAO;
    @Resource
    private ProductInventoryMapper productInventoryMapper;

    @Override
    public void updateProduceInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public void removeProduceInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        redisDAO.delete(key);
    }

    @Override
    public ProductInventory getProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void setProduceInventoryCache(ProductInventory productInventory) {
        String key = "product:inventory:" + productInventory.getProductId();
        this.redisDAO.set(key, productInventory.getInventoryCnt().toString());
    }
}
