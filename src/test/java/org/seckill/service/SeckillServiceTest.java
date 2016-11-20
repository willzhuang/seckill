package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.DuplicatedKillException;
import org.seckill.exception.SeckillClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by will on 2016/11/20.
 */

@RunWith(SpringJUnit4ClassRunner.class)
// tell junit where is the config file of spring setting file.
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})

public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer.getMd5());
        logger.info("exposer={}",exposer.getSeckillId());

    }

    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}",exposer);
            long phoneNumber = 13509871234L;
            String md5 = exposer.getMd5();
            try {
                SeckillExcution seckillExcution = seckillService.executeSeckill(id,phoneNumber,md5);
                logger.info("result={}",seckillExcution);
            } catch (DuplicatedKillException | SeckillClosedException e) {
                logger.error(e.getMessage());
            }
        } else {
            // 秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }

}