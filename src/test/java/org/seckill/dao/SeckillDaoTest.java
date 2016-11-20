package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合, junit启动时, 加载spring IOC 容器
 * spring-test, junit
 * Created by will on 2016/11/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// tell junit where is the config file of spring setting file.
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // injext Dao container
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        try {
            Seckill seckill = seckillDao.queryById(id);
            System.out.println(seckill.getName());
            System.out.println(seckill);
        } catch (Error e) {
            System.out.println(e);
        }
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for (Seckill seckill : seckillList) {
            System.out.println(seckill.getSeckillId());
            System.out.println(seckill);
        }
    }

    @Test
    public void testReduceNumber() throws Exception {
        long seckillId = 1000L;
        Date date = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId, date);
        System.out.println("updateCount=" + updateCount);
    }


}