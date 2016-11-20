/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.exception;

/**
 * @author will
 * @version : SeckillException.java, v 0.1 2016-10-19 17:03 will Exp $$
 */

public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
