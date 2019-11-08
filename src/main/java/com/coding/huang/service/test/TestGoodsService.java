package com.coding.huang.service.test;

import com.coding.huang.annotations.login.WithAspectOperation;
import com.coding.huang.dao.test.TestGoodsDao;
import com.coding.huang.domain.TestGoods;
import com.coding.huang.enums.ActionTypeEnum;
import com.coding.huang.exceptions.CommonException;
import com.coding.huang.redis.RedisService;
import com.coding.huang.redis.prefix.TestGoodsPrefix;
import com.coding.huang.result.CodeMessage;
import com.coding.huang.utils.tools.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/10/31.
 */
@Service
public class TestGoodsService {

    public static final String REDIS_KEY = CommonUtil.getRedisKey(TestGoods.class);

    public static final TestGoodsPrefix REDIS_PREFIX = TestGoodsPrefix.getTestGoodsById;

    @Autowired
    TestGoodsDao testGoodsDao;

    @Autowired
    RedisService redisService;

    @WithAspectOperation(actionType = ActionTypeEnum.INSERT)
    public void insertTestGoods(TestGoods testGoods){
        testGoodsDao.insertTestGoodsData(testGoods);
    }

    @WithAspectOperation(actionType = ActionTypeEnum.UPDATE)
    public void updateTestGoods(TestGoods testGoods){
        testGoodsDao.updateTestGoodsData(testGoods);
    }

    @WithAspectOperation(actionType = ActionTypeEnum.DELETE)
    public void deleteTestGoods(TestGoods testGoods){
        testGoodsDao.deleteTestGoodsData(testGoods);
    }

    public TestGoods selectTestGoodsById(String goodsId){
        TestGoods testGoods = new TestGoods();
        //缓存获取对象
        testGoods = redisService.get(REDIS_PREFIX,goodsId,TestGoods.class);
        if (testGoods != null){
            //缓存获取命中
            return testGoods;
        }
        //缓存未命中 从数据看检索
        testGoods = testGoodsDao.selectTestGoodsById(goodsId);
        if (testGoods == null){
            //数据不存在 抛异常
            throw new CommonException(CodeMessage.DATA_NOT_FOUND);
        }
        //非user类缓存可以set失败 但是不可以影响程序的继续运行 所以try catch
        try {
            redisService.set(REDIS_PREFIX,goodsId,testGoods);
        }catch (Exception e){
            // TODO: 2019/11/5 日志管理 
        }
        return testGoods;
    }

    @WithAspectOperation
    public void noParamTestMethod(){
        // TODO: 2019/11/1
    }

}
