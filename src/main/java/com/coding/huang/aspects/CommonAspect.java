package com.coding.huang.aspects;

import com.coding.huang.annotations.login.WithAspectOperation;
import com.coding.huang.configs.UserContext;
import com.coding.huang.domain.User;
import com.coding.huang.enums.ActionTypeEnum;
import com.coding.huang.exceptions.CommonException;
import com.coding.huang.redis.KeyPrefix;
import com.coding.huang.redis.RedisService;
import com.coding.huang.result.CodeMessage;
import com.coding.huang.service.CookieService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/10/28.
 */
@Component
@Aspect
public class CommonAspect {

    //该常量值为 各类service对应的cookie常量的变量名称
    private static final String COMMON_COOKIE_NAME = "COOKIE_NAME";
    //该常量值为 各类service对应的cookie_prefix常量的变量名称
    private static final String COMMON_COOKIE_PREFIX = "COOKIE_PREFIX";
    //对于 存在修改缓存行为的domain 必须有这个字段 必须！
    private static final String TEMP_TOKEN = "tempToken";

    private static final String REDIS_KEY_FIELD = "REDIS_KEY";

    private static final String REDIS_PREFIX_FIELD = "REDIS_PREFIX";

    @Autowired
    RedisService redisService;

    @Autowired
    CookieService cookieService;

    //处理login方法及其他（待补充）
    //该切点旨在 控制缓存和数据库一致性 (缓存可以不存在，但是如果存在必须与数据库一致 user缓存必须存在 我们需要他维持登录状态)
    //@Around("execution(public * com.coding.huang.service.UserService.doLogin(..))")
    //原loginAOP 逻辑错误 暂废弃 别删
    public Object aroundAop(ProceedingJoinPoint point) throws Throwable {
        //Aop中获取 HttpServletRequest
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //Aop中获取 HttpServletResponse
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //获取需要操作缓存的service class
        Class<?> clazz = point.getSignature().getDeclaringType();
        //new Instance（） 实例化一个对象 用来 get 常量
        Object o = clazz.newInstance();
        //获取 COOKIE_NAME
        Field fieldName = clazz.getDeclaredField(COMMON_COOKIE_NAME);
        Field fieldPrefix = clazz.getDeclaredField(COMMON_COOKIE_PREFIX);
        //field.get访问常量值 如果set则需要 field.setAccessible(true) 来忽略修饰符所标记的权限
        // 常量（static final）无法set
        Object o1 = fieldName.get(o);
        KeyPrefix keyPrefix = (KeyPrefix) fieldPrefix.get(o);
        String cookieName = o1.toString();
        //获取 cookie值
        String cookieValue = cookieService.getCookieValue(request,cookieName);
        //清除旧的缓存
        Object retObj = null;
        if (!StringUtils.isEmpty(cookieValue)){
            redisService.delete(keyPrefix,cookieValue);
        }
        //执行方法
        retObj = point.proceed();
        //方法执行完毕之后 后续的方法必须保证正确（如果错误了需要回滚还原） 因为涉及到一个保持登录状态的问题

        //执行后操作 生成缓存 写入cookie
        Class<?> clazz2 = retObj.getClass();
        //反射获取tempToken 需要修改token的domain 一定要声明这个字段 very important!!!!!
        Field fieldTempToken = clazz2.getDeclaredField(TEMP_TOKEN);
        fieldTempToken.setAccessible(true);
        Object o2 = fieldTempToken.get(retObj);
        String token = o2.toString();
        //登录操作如果成功 则必须有缓存和cookie 所以下一步必须成功 否则清楚cookie并抛系统异常
        try{
            cookieService.writeCookie(response,retObj,token,keyPrefix,cookieName);
        }catch (Exception e){
            //删除cookie 仅删除cookie即可 保证请求不到缓存数据 缓存数据即便存在错误也会自然过期
            cookieService.deleteCookie(response,cookieName);
            //是否需要提示缓存异常？
            // TODO: 2019/10/29
            throw new CommonException(CodeMessage.SERVER_ERROR);
        }

        return retObj;

    }
    //给login操作建立一个新的aop暂不作操作
    @Around("execution(public * com.coding.huang.service.UserService.*Login(..))")
    public Object loginAop(ProceedingJoinPoint point) throws Throwable {
        Object o = null;
        System.out.println("enter login aop");
        o = point.proceed();
        System.out.println("end login aop");
        return o;
    }

    @Around("@annotation(com.coding.huang.annotations.login.WithAspectOperation)")
    public Object itemAop(ProceedingJoinPoint point) throws Throwable {
        Object o = null;
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method targetMethod = methodSignature.getMethod();
        Class<?> clazz = targetMethod.getDeclaringClass();
        //获取redisKey字段名称
        Field field = clazz.getDeclaredField(REDIS_KEY_FIELD);
        String key = (String) field.get(clazz.newInstance());
        Field prefix = clazz.getDeclaredField(REDIS_PREFIX_FIELD);
        KeyPrefix pre = (KeyPrefix) prefix.get(clazz.newInstance());
        //数据库操作直接穿bean 而且规定实体类操作 第一个参数为实体类
        Object domainArg = point.getArgs()[0];
        Field keyField = domainArg.getClass().getDeclaredField(key);
        keyField.setAccessible(true);
        String keyValue = (String) keyField.get(domainArg);

        //获取注解属性
        WithAspectOperation annotation = targetMethod.getAnnotation(WithAspectOperation.class);
        ActionTypeEnum actionTypeEnum = annotation.actionType();
        int opt = actionTypeEnum.getCode();

        if (opt == 0){
            // TODO: 2019/11/1 日志功能
        }else if (opt == 1){
            redisService.delete(pre,keyValue);
            // TODO: 2019/11/1 日志功能
        }else if (opt == 2){
            redisService.delete(pre,keyValue);
            // TODO: 2019/11/1 日志功能
        }
        o = point.proceed();
        //item缓存非必须 在查询中如果没查到缓存会 访问数据库然后设置缓存 所以在这里不设置
        //日志操作待完成
        // TODO: 2019/11/1 日志功能 
        return o;
    }

    @Before("@annotation(com.coding.huang.annotations.login.AdminOnly)")
    public void adminOnlyAop(){
        User user = UserContext.getUser();
        if (user == null){
            throw new CommonException(CodeMessage.NEED_LOGIN);
        }
        if (user.getUserRole() < 1){
            throw new CommonException(CodeMessage.ADMIN_ONLY);
        }
    }
}
