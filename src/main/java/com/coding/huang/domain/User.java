package com.coding.huang.domain;

import com.coding.huang.annotations.login.IsMobile;
import com.coding.huang.annotations.login.RedisKey;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2019/10/23.
 */
public class User {

    //这个是登录账号 数据库主键
    @NotEmpty(message = "账号不可为空")
    @IsMobile
    private String userAccount;

    @NotEmpty(message = "密码不可为空")
    private String userPwd;

    private String userSalt;

    private int userRole;

    private String userInfo;

    //这个userName是用户的真实姓名
    private String userName;

    private Date lastLogin;

    @RedisKey
    private String tempToken;

    public String getTempToken() {
        return tempToken;
    }

    public void setTempToken(String tempToken) {
        this.tempToken = tempToken;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
