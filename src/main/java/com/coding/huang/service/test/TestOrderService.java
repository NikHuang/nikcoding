package com.coding.huang.service.test;

import com.coding.huang.annotations.login.WithAspectOperation;
import com.coding.huang.dao.test.TestOrderDao;
import com.coding.huang.domain.TestOrder;
import com.coding.huang.enums.ActionTypeEnum;
import com.coding.huang.redis.prefix.TestOrderPrefix;
import com.coding.huang.utils.tools.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/10/31.
 */
@Service
public class TestOrderService {

    public static final String REDIS_KEY = CommonUtil.getRedisKey(TestOrder.class);

    public static final TestOrderPrefix REDIS_PREFIX = TestOrderPrefix.getTestOrderById;

    @Autowired
    TestOrderDao testOrderDao;

    @WithAspectOperation(actionType = ActionTypeEnum.INSERT)
    public void insertTestOrder(TestOrder testOrder){
        testOrderDao.insertTestOrderData(testOrder);
    }

    @WithAspectOperation(actionType = ActionTypeEnum.UPDATE)
    public void updateTestOrder(TestOrder testOrder){
        testOrderDao.updateTestOrderData(testOrder);
    }

    @WithAspectOperation(actionType = ActionTypeEnum.DELETE)
    public void deleteTetOrder(TestOrder testOrder){
        testOrderDao.deleteTestOrderData(testOrder);
    }
}
