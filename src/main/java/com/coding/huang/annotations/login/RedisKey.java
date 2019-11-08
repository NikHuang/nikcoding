package com.coding.huang.annotations.login;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/10/25.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisKey {

}
