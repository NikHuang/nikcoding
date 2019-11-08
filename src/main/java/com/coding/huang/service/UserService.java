package com.coding.huang.service;

import com.coding.huang.annotations.login.AdminOnly;
import com.coding.huang.dao.UserDao;
import com.coding.huang.domain.User;
import com.coding.huang.exceptions.CommonException;
import com.coding.huang.redis.KeyPrefix;
import com.coding.huang.redis.RedisService;
import com.coding.huang.redis.prefix.UserPrefix;
import com.coding.huang.result.CodeMessage;
import com.coding.huang.utils.tools.CommonUtil;
import com.coding.huang.vo.LoginVo;
import com.coding.huang.vo.UserInfoVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2019/10/25.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;
    @Autowired
    CookieService cookieService;

    public static final String REDIS_KEY = CommonUtil.getRedisKey(User.class);

    public static final KeyPrefix REDIS_PREFIX = UserPrefix.getUserByToken;

    //个人认为 一般情况下 登录方法可以复用 所以说 写一个就可以了 所以不把缓存放到切面里操作
    //登录成功除了 成功更新user表的 登录时间和token以外 还必须保持 redis和cookie更新成功 否则不能算成功
    //而且数据必须一致 所以这里虽然只涉及到一个数据表的update操作 依然需要使用@Transactional
    @Transactional
    public User doLogin(HttpServletRequest request,HttpServletResponse response, LoginVo loginVo) {
        String userAccount = loginVo.getLoginAccount();
        String loginPwd = "123456";
        //获取用户
        User user = userDao.getUserByAccount(userAccount);
        if (user == null){
            //如果无此用户 抛异常
            throw new CommonException(CodeMessage.NOACCOOUNT.fillArgs(userAccount));
        }
        //用户存在 校验密码
        String salt = user.getUserSalt();
        String dbPwd = user.getUserPwd();
        if (!dbPwd.equals(CommonUtil.md5WithSalt(loginPwd,salt))){
            //密码错误 抛异常
            throw new CommonException(CodeMessage.WRONG_PASSWORD);
        }
        String oldToken = cookieService.getCookieValue(request,REDIS_KEY);
        System.out.println("oldToken = " + oldToken);
        //为避免高并发情况下的一致性问题 清除旧缓存
        redisService.delete(REDIS_PREFIX,oldToken);
        //生成新token(登录需要新token，其他的操作大部分不需要新生成)
        String newToken = CommonUtil.getUUID();
        System.out.println("token = " + newToken);
        //然后更新下数据库的 lastLogin和TOKEN
        user.setLastLogin(new Date());
        user.setTempToken(newToken);
        userDao.updateUserLoginTimeAndTokenByLogin(user);
        //更新新的缓存和cookie
        writeCookie(response,user,newToken);
        return user;
    }

    @AdminOnly
    public void doUserTest(){
        System.out.println("doUserTest");
    }

    //仅实现管理员对用户的增删改
    //仅插入即可
    @AdminOnly
    public void insertUserByAdmin(User user, UserInfoVo userInfoVo){
        UserInfoVo infoForInsert = insertMethodCommonHandler(userInfoVo);
        //存入数据库
        userDao.addUser(infoForInsert);
    }

    //事务操作 注册成功之后直接保持登录状态 所以 需要数据的一致性（数据库和缓存一致）
    @Transactional
    public void registerByUser(UserInfoVo userInfoVo,HttpServletResponse response){
        UserInfoVo infoForInsert = insertMethodCommonHandler(userInfoVo);
        infoForInsert.setLastLogin(new Date());
        String newUserToken = CommonUtil.getUUID();
        infoForInsert.setTempToken(newUserToken);
        //存入数据库
        userDao.addUser(infoForInsert);
        //保存之后 返回cookie 即注册成功即登录成功 同时保持登录状态一定时间
        writeCookie(response,infoForInsert,newUserToken);
    }
    private void writeCookie(HttpServletResponse response,User user,String token){
        //cookie的持续时间当与redis缓存持续时间一致
        //刷新缓存
        redisService.set(REDIS_PREFIX,token,user);
        //生成/刷新 cookie
        Cookie cookie = new Cookie(REDIS_KEY,token);
        cookie.setMaxAge(REDIS_PREFIX.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    private UserInfoVo insertMethodCommonHandler(UserInfoVo userInfoVo){
        //检查用户是否存在
        User userExists = userDao.getUserByAccount(userInfoVo.getUserAccount());
        if (userExists != null){
            //用户已经存在
            throw new CommonException(CodeMessage.ACCOUNT_ALREADY_EXISTS.fillArgs(userInfoVo.getUserAccount()));
        }
        //对User的输出 进行处理
        //生成盐值
        String salt = CommonUtil.getSalt();
        String ajaxPwd = userInfoVo.getUserPwd();
        String dbPwd = CommonUtil.md5WithSalt(ajaxPwd,salt);
        userInfoVo.setUserSalt(salt);
        userInfoVo.setUserPwd(dbPwd);
        return userInfoVo;
    }
}
