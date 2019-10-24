package com.coding.huang.vo;

import com.coding.huang.annotations.login.IsMobile;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2019/10/23.
 */
public class LoginVo {

    @NotNull(message = "账号不可为空")
    @IsMobile
    private String loginAccount;

    @NotNull(message = "密码不可为空")
    private String loginPwd;


    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
