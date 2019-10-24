package com.coding.huang.exceptions;

import com.coding.huang.result.CodeMessage;
import com.coding.huang.result.Result;


import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created by Administrator on 2019/10/23.
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler{

       @ExceptionHandler(value = Exception.class)
       public Result<Object> myExceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception e){
           //抛异常打印一下
           e.printStackTrace();

           if (e instanceof BindException){
               BindException be = (BindException) e;
               List<ObjectError> list = be.getAllErrors();
               ObjectError objectError = list.get(0);
               String errMsg = objectError.getDefaultMessage();
               return Result.error(CodeMessage.PARAM_ERROR.fillArgs(errMsg));
           }else if (e instanceof CommonException){
               CommonException commonException = (CommonException) e;
               CodeMessage codeMessage = commonException.getCodeMessage();
               return Result.error(codeMessage);
           }else {
               return Result.error(CodeMessage.SERVER_ERROR);
           }

       }
}
