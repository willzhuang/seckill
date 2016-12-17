/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.service.Impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.DuplicatedKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author will
 * @version : SeckillServiceImpl.java, v 0.1 2016-10-19 17:03 will Exp $$
 */
//@Component @Service @Dao @Conroller
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 注入 service 依赖
    // 使用 @Autowired 之后，就不需要 seckillDao = new SeckillDao();
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    // md5 的盐值，起到混淆加密的作用
    private final static String salt = "zaq12wsxcde3$%^MLP)(" ;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,12);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        // 优化点：缓存优化。一致性建立在超时的基础之上。
        // 1. 访问 redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            // 2. 访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false,seckillId);
            } else {
                // 3. 放入 redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(),
                    startTime.getTime(), endTime.getTime());
        }
        //create md5 encryption string.
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    @Transactional
    /*
    * 使用注解控制事物方法的有点：
    * 1. 开发团队达成一致，明确标注事务方法的编程风格
    * 2. 保证事务方法的执行时间尽可能短，
    *    不要穿插其他的网络操作，例如 RPC/HTTP请求，非常耗时，这种超过要剥离到事务方法之外
    * 3. 不是所有的方法都需要事务，例如 只有一条修改操作，只读操作不需要事物
    * */
    public SeckillExecution executeSeckill(
            long seckillId, long userPhone, String md5)
            throws SeckillException, DuplicatedKillException, SeckillClosedException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("秒杀数据被篡改");
        }
        //执行秒杀逻辑：1.减库存；2.记录购买行为
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                // 没有更新到记录，秒杀结束
                throw new SeckillClosedException("秒杀结束！");
            } else {
                // 记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new DuplicatedKillException("重复的秒杀！");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (DuplicatedKillException e1) {
            throw e1;
        } catch (SeckillClosedException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            // 编译器异常转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
