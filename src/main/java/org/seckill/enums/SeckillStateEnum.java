/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

package org.seckill.enums;

/**
 * @author will
 * @version : SeckillStateEnum.java, v 0.1 2016-10-19 17:03 will Exp $$
 */

public enum SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    DUPLICATED_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum stateof(int index) {
        for (SeckillStateEnum state : SeckillStateEnum.values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
