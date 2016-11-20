/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @author will
 * @version : SuccessKilledDao.java, v 0.1 2016-10-19 17:03 will Exp $$
 */

public interface SuccessKilledDao {

    /*
    * insert order. It should filter duplicated buy
    * @return if return int = 1, it insert successfully
    * */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /*query successKilled ,and the entity*/
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


}
