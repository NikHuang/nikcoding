package com.coding.huang.redis.prefix;

import com.coding.huang.redis.BasePrefix;

/**
 * Created by Administrator on 2019/10/23.
 */
public class UserPrefix extends BasePrefix {

    public UserPrefix(String prefix) {
        super(prefix);
    }

    public UserPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
