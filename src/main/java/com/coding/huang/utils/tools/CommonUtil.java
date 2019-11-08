package com.coding.huang.utils.tools;

import com.coding.huang.annotations.login.RedisKey;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/10/23.
 */
//通用工具类
public class CommonUtil {

    //判断是否是手机格式
    public static boolean isMobile(String s){
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (s.length() != 11){
            return false;
        }else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(s);
            return m.matches();
        }
    }
    //MD5盐值加密
    public static String md5WithSalt(String input,String salt){
        String inputWithSalt = salt.charAt(1)+salt.charAt(5)+input+salt.charAt(2)+salt.charAt(3);
        return md5(inputWithSalt);
    }

    //UUID
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }

    //生成6位salt
    public static String getSalt(){
        String salt = "";
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            // 保证六位随机数之间没有重复的
            if (salt.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            salt = salt + c;
        }
        return salt;
    }

    public static <T> String getRedisKey(Class<?> clazz){

        Field[] fields = clazz.getDeclaredFields();
        if (fields == null){
            return null;
        }
        for (Field field : fields) {
            Annotation a = field.getAnnotation(RedisKey.class);
            if (a != null){
                System.out.println(field.getName());
                return field.getName();
            }
        }

        return null;

    }

    //md5加密
    private static String md5(String input){
        return  DigestUtils.md5Hex(input);
    }
}
