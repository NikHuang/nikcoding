package com.coding.huang.domain;

import com.coding.huang.annotations.login.RedisKey;

/**
 * Created by Administrator on 2019/10/31.
 */
public class TestOrder {

    @RedisKey
    private String orderId;

    private String goodsId;

    private String orderInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }
}
