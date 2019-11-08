package com.coding.huang.controller;

import com.coding.huang.annotations.login.NeedLogin;
import com.coding.huang.domain.TestGoods;
import com.coding.huang.domain.TestOrder;
import com.coding.huang.domain.User;
import com.coding.huang.result.Result;
import com.coding.huang.service.UserService;
import com.coding.huang.service.test.TestGoodsService;
import com.coding.huang.service.test.TestOrderService;
import com.coding.huang.utils.tools.CommonUtil;
import com.coding.huang.vo.LoginVo;
import com.coding.huang.vo.UserInfoVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by Administrator on 2019/10/23.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    TestGoodsService testGoodsService;
    @Autowired
    TestOrderService testOrderService;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> doLogin(HttpServletRequest request,HttpServletResponse response, @Valid LoginVo loginVo){
        userService.doLogin(request,response,loginVo);
        return Result.success(true);
    }

    @RequestMapping(value = "/localTest",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> localTest(User user,HttpServletRequest request, HttpServletResponse response,@Valid UserInfoVo user1){
        userService.registerByUser(user1,response);
        return Result.success(true);
    }

}
