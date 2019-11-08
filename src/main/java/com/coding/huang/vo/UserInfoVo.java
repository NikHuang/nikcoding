package com.coding.huang.vo;

import com.coding.huang.annotations.login.IsMobile;
import com.coding.huang.domain.User;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2019/11/5.
 */
public class UserInfoVo extends User {
    @Override
    public void setUserAccount(String userAccount) {
        super.setUserAccount(userAccount);
    }

    @Override
    public String getUserAccount() {
        return super.getUserAccount();
    }
}
