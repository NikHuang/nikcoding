package com.coding.huang.redis.prefix;

import com.coding.huang.redis.BasePrefix;
import com.coding.huang.service.test.TestGoodsService;

/**
 * Created by Administrator on 2019/11/1.
 */
public class TestGoodsPrefix extends BasePrefix{
    public TestGoodsPrefix(String prefix) {
        super(prefix);
    }

    public TestGoodsPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static TestGoodsPrefix getTestGoodsById = new TestGoodsPrefix(300, TestGoodsService.REDIS_KEY);
}
