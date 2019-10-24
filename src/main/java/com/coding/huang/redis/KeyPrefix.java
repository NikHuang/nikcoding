package com.coding.huang.redis;

/**
 * Created by Administrator on 2019/10/23.
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
