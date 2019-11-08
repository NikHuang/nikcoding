package com.coding.huang.redis.prefix;

import com.coding.huang.redis.BasePrefix;
import com.coding.huang.service.test.TestOrderService;

/**
 * Created by Administrator on 2019/11/1.
 */
public class TestOrderPrefix extends BasePrefix{
    public TestOrderPrefix(String prefix) {
        super(prefix);
    }

    public TestOrderPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static TestOrderPrefix getTestOrderById = new TestOrderPrefix(3600, TestOrderService.REDIS_KEY);
}
