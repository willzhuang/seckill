/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.exception;

/**
 * 重复秒杀异常
 * @author will
 * @version : DuplicatedKillException.java, v 0.1 2016-10-19 17:03 will Exp $$
 */

public class DuplicatedKillException extends SeckillException {

    public DuplicatedKillException(String message) {
        super(message);
    }

    public DuplicatedKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
