package com.coding.huang.dao.test;

import com.coding.huang.domain.TestOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/10/31.
 */
@Mapper
public interface TestOrderDao {

    void insertTestOrderData(TestOrder testOrder);

    void updateTestOrderData(TestOrder testOrder);

    void deleteTestOrderData(TestOrder testOrder);
}
