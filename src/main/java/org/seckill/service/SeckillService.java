/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.DuplicatedKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * @author will
 * @version : SeckillService.java, v 0.1 2016-10-19 17:03 will Exp $$
 * 业务接口：站在使用者的角度设计接口
 *  1. 方法定力的粒度；2. 参数；3. 返回类型(return 的类型/异常)
 */

public interface SeckillService {

    // 列表页面展示全部秒杀的记录
    List<Seckill> getSeckillList();

    // 列表页面展示单个秒杀的记录
    Seckill getById(long seckillId);

    // 秒杀开启时，输出秒杀接口地址
    // 否则输出系统时间和秒杀时间
    Exposer exportSeckillUrl(long seckillId);

    // excute second kill buy
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,DuplicatedKillException,SeckillClosedException;

}
