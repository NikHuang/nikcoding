package com.coding.huang.enums;

import com.coding.huang.redis.KeyPrefix;
import com.coding.huang.redis.prefix.UserPrefix;
import com.coding.huang.service.UserService;

/**
 * Created by Administrator on 2019/10/28.
 */
public enum TokenEnum {
    //UserToken
    USER_TOKEN(UserService.class, UserPrefix.getUserByToken)
    ;
    private Class<?> aClass;

    private KeyPrefix keyPrefix;

    TokenEnum(Class<?> aClass, KeyPrefix keyPrefix) {
        this.aClass = aClass;
        this.keyPrefix = keyPrefix;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public KeyPrefix getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(KeyPrefix keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public static KeyPrefix getPrefixValueByClass(Class<?> clazz){
        for(TokenEnum tokenEnum:TokenEnum.values()){
            if (tokenEnum.getaClass() == clazz){
                return tokenEnum.getKeyPrefix();
            }
        }
        return null;
    }
}
