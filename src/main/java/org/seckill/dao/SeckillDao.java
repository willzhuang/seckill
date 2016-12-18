/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author will
 * @version : SeckillDao.java, v 0.1 2016-10-19 17:03 will Exp $$
 */

public interface SeckillDao {

    /*
    * reduce inventory.
    * @return if return int >= 1, it update successfully.
    * 如果没有@Param标签，会报错误： Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
    * 因为java没有保存形参的记录:queryAll(int offet,int limit) ->queryAll(arg0,arg1). SQL拿不到offset 和 limit.
    * */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /*
    * query entity by Id
    * */
    Seckill queryById(long seckillId);

    /*
    * query all entities list by offset
    * 如果没有@Param标签，会报错误： Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
    * 因为java没有保存形参的记录:queryAll(int offet,int limit) ->queryAll(arg0,arg1). SQL拿不到offset 和 limit.
    * */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /*
    * 使用存储过程执行秒杀
    * */
    void killByProcedure(Map<String, Object> paraMap);


}
