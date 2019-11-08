package com.coding.huang.configs;

import com.coding.huang.domain.User;

/**
 * Created by Administrator on 2019/10/25.
 */
public class UserContext {

    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }
    public static User getUser() {
        return userHolder.get();
    }
}
