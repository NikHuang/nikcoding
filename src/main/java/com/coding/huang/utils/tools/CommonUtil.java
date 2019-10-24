package com.coding.huang.utils.tools;

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
}
