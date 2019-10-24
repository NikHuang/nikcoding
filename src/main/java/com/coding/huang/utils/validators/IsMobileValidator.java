package com.coding.huang.utils.validators;


import com.coding.huang.annotations.login.IsMobile;
import com.coding.huang.utils.tools.CommonUtil;
import org.apache.commons.lang3.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2019/10/23.
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    //校验手机格式的时候需要考虑 该项是不是必填 不一定都是必填
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
       required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return CommonUtil.isMobile(s);
        }else {
            if (StringUtils.isEmpty(s)){
                return true;
            }else {
                return CommonUtil.isMobile(s);
            }
        }
    }
}
