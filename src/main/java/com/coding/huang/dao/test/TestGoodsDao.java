package com.coding.huang.dao.test;

import com.coding.huang.domain.TestGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2019/10/31.
 */
@Mapper
public interface TestGoodsDao {

    void insertTestGoodsData(TestGoods testGoods);

    void updateTestGoodsData(TestGoods testGoods);

    void deleteTestGoodsData(TestGoods testGoods);

    TestGoods selectTestGoodsById(@Param("goodsId") String goodsId);
}
