package com.coding.huang.enums;

import com.coding.huang.redis.KeyPrefix;
import com.coding.huang.redis.prefix.UserPrefix;
import com.coding.huang.service.UserService;

/**
 * Created by Administrator on 2019/10/28.
 */
public enum ActionTypeEnum {
    //UserToken
    INSERT(0,"INSERT"),
    UPDATE(1,"UPDATE"),
    DELETE(2,"DELETE"),
    ;
    private int code;

    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    ActionTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static String getTypeByCode(int code){
        for(ActionTypeEnum actionTypeEnum: ActionTypeEnum.values()){
            if (actionTypeEnum.getCode() == code){
                return actionTypeEnum.getType();
            }
        }
        return null;
    }
}
