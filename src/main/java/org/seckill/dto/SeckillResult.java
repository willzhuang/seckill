/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.dto;

/**
 * @author will
 * @version : SeckillResult.java, v 0.1 2016-10-19 17:03 will Exp $$
 */
//所有的ajax请求的返回类型都是SeckillResult，封装 json 结果
public class SeckillResult<T> {
    private boolean success;
    private T data;
    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
