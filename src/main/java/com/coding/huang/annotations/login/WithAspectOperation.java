package com.coding.huang.annotations.login;

import com.coding.huang.enums.ActionTypeEnum;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/10/25.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithAspectOperation {

    ActionTypeEnum actionType() default ActionTypeEnum.INSERT;

}
