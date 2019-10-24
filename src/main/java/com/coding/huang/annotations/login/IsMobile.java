package com.coding.huang.annotations.login;

import com.coding.huang.utils.validators.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/10/23.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {

    //用于判断是否需要输入手机号
    boolean required() default true;

    String message() default "手机格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
