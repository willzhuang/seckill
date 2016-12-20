/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.exception;

/**
 * 秒杀已经关闭异常
 *
 * @author will
 * @version : SeckillClosedException.java, v 0.1 2016-10-19 17:03 will Exp $$
 */

public class SeckillClosedException extends SeckillException {

    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
