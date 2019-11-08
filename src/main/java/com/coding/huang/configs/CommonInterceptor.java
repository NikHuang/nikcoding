package com.coding.huang.configs;

import com.alibaba.fastjson.JSONObject;
import com.coding.huang.annotations.login.NeedLogin;
import com.coding.huang.domain.User;
import com.coding.huang.redis.KeyPrefix;
import com.coding.huang.redis.RedisService;
import com.coding.huang.redis.prefix.UserPrefix;
import com.coding.huang.result.CodeMessage;
import com.coding.huang.result.Result;
import com.coding.huang.service.CookieService;
import com.coding.huang.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/10/23.
 */

@Component
public class CommonInterceptor implements HandlerInterceptor {
    /*
    * 关于HandlerInterceptor
    * 1.链式调用 可以有多个Interceptor按顺序执行
    * 2.preHandle 于 Controller之前调用 返回true才能执行controller的方法
    * 3. postHandl在controller之后 DispatcherServlet渲染之前 （前后端分离的话，暂时可能用不到）
    * 4. afterCompletion在DisPatcherServlet之后（前后端分离的话，暂时可能用不到）
    */
    @Autowired
    WebApplicationContext applicationContext;
    @Autowired
    CookieService cookieService;
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        * 需要拦截的操作(逐步完善)
        * 1.获取当前User
        * 2.是否needLogin
        * 3.判断请求url是否存在
        * 4.处理错误请求
        * */

        String requestUrl = request.getRequestURI();
        //处理错误请求
        if (requestUrl.equals("/error")) {
            writeErrorResponse(response, CodeMessage.SERVER_ERROR);
            return false;
        }
        //仅处理requestMapping方法 否则返回错误请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //获取所有HandlerMethod
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
            //判断是否包含该 HandlerMethod
            boolean hasUrl = false;
            for (RequestMappingInfo re : map.keySet()){
                Set<String> set = re.getPatternsCondition().getPatterns();
                if (set.contains(requestUrl)){
                    hasUrl = true;
                    break;
                }
            }
            if (hasUrl) {
                //先取user 并设置ThreadLocal
                String userToken = cookieService.getCookieValue(request, UserService.REDIS_KEY);
                User user = getUserByToken(response,userToken);
                UserContext.setUser(user);
                //再判断needLogin
                //先判断controller有没有needLogin
                Method m = hm.getMethod();
                Class<?> clazz = m.getDeclaringClass();
                Annotation[] as = clazz.getAnnotations();
                boolean controllerNeedLogin = false;
                for (Annotation a : as) {
                    if (a.annotationType() == NeedLogin.class){
                        controllerNeedLogin = true;
                        break;
                    }
                }
                //再判断方法有没有needLogin
                boolean methodNeedLogin = false;
                methodNeedLogin = hm.hasMethodAnnotation(NeedLogin.class);
                //判断是否NeedLogin
                if (controllerNeedLogin || methodNeedLogin){
                    if (user == null){
                        writeErrorResponse(response,CodeMessage.NEED_LOGIN);
                        return false;
                    }
                    //needLogin通过之后 也应当判断下权限
                    if (user.getUserRole() < 0){
                        //小于0 代表被封号
                        writeErrorResponse(response,CodeMessage.ACCOUNT_FROZEN);
                        return false;
                    }
                }

            } else {
                //该方法不存在
                writeErrorResponse(response, CodeMessage.NO_SUCH_API);
                return false;
            }
        } else {
            writeErrorResponse(response, CodeMessage.NO_SUCH_API);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

    private void writeErrorResponse(HttpServletResponse response, CodeMessage codeMessage) throws IOException {
        PrintWriter out = null;
        response.setContentType("application/json;charset=utf-8");
        out = response.getWriter();
        out.append(JSONObject.toJSONString(Result.error(codeMessage)));
        out.flush();
        out.close();
    }
    private User getUserByToken(HttpServletResponse response,String token){
        if (StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(UserPrefix.getUserByToken,token,User.class);
        if (user != null){
            //刷新cookie
            cookieService.writeCookie(response,user,token,UserPrefix.getUserByToken,UserService.REDIS_KEY);
        }
        return user;
    }
}
