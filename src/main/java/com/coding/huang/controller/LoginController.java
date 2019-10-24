package com.coding.huang.controller;

import com.coding.huang.result.Result;
import com.coding.huang.vo.LoginVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by Administrator on 2019/10/23.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> doLogin(HttpServletRequest request,HttpServletResponse response, @Valid LoginVo loginVo){
        return Result.success(true);
    }

    @RequestMapping(value = "/localTest",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> localTest(HttpServletRequest request,HttpServletResponse response){
        return Result.success(true);
    }


}
