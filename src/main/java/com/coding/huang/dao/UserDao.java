package com.coding.huang.dao;

import com.coding.huang.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/10/25.
 */
@Mapper
public interface UserDao {
    //根据账号获取user
    User getUserByAccount(@Param("userAccount") String userAccount);

    //登录刷新 上次登录时间以及token
    int updateUserLoginTimeAndTokenByLogin(User user);

    //插入新User 新增的user不需要 last_login和temp_token
    int addUser(User user);
}
