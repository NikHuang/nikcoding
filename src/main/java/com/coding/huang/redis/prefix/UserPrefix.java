package com.coding.huang.redis.prefix;

import com.coding.huang.redis.BasePrefix;
import com.coding.huang.service.UserService;

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

    //token有效时间10分钟
    public static UserPrefix getUserByToken = new UserPrefix(3600, UserService.REDIS_KEY);
}
