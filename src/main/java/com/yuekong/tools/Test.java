package com.yuekong.tools;

import com.yuekong.tools.utils.RedisSentinelUtils;

/**
 * @author zhangjj
 * 2020/10/16
 */
public class Test {


    public static void main(String[] args) {
        RedisSentinelUtils.set(2, "myKey", "123");
        System.out.println(RedisSentinelUtils.get(2, "myKey"));
    }
}
