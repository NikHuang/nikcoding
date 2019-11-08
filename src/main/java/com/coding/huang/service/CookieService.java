package com.coding.huang.service;

import com.coding.huang.redis.KeyPrefix;
import com.coding.huang.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2019/10/28.
 */
@Service
public class CookieService {
    @Autowired
    RedisService redisService;

    public String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void writeCookie(HttpServletResponse response, Object o, String token, KeyPrefix keyPrefix,String cookieName){
        redisService.set(keyPrefix,token,o);
        Cookie cookie = new Cookie(cookieName,token);
        cookie.setPath("/");
        cookie.setMaxAge(keyPrefix.expireSeconds());
        response.addCookie(cookie);

    }
    public void deleteCookie(HttpServletResponse response,String cookieName){
        Cookie cookie = new Cookie(cookieName,"");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

//    public <T> T getCacheByCookie(KeyPrefix keyPrefix,String key,Class<T> clazz){
//        if (keyPrefix == null || StringUtils.isEmpty(key)||clazz == null){
//            return null;
//        }
//        return redisService.get(keyPrefix,key,clazz);
//
//    }

}
