package com.macfu.model;

public class ProductInventory {
    // 商品id
    private Integer productId;
    // 库存数量
    private Long inventoryCnt;

    public ProductInventory() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getInventoryCnt() {
        return inventoryCnt;
    }

    public void setInventoryCnt(Long inventoryCnt) {
        this.inventoryCnt = inventoryCnt;
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "productId=" + productId +
                ", inventoryCnt=" + inventoryCnt +
                '}';
    }
}