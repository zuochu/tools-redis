package com.yuekong.tools.utils;

import com.yuekong.tools.initialize.RedisInitialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import static com.yuekong.tools.constant.RedisSentinelConstants.*;

/**
 * @author zhangjj
 * 2020/10/16
 */
@Slf4j
public class RedisSentinelUtils {

    private static GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();

    private static RedisInitialize redisInitialize;

    static {
        //最大活动的对象个数
        genericObjectPoolConfig.setMaxTotal(200);
        //对象最大空闲
        genericObjectPoolConfig.setMaxIdle(100);
        //对象最小空闲
        genericObjectPoolConfig.setMinIdle(30);
        redisInitialize = new RedisInitialize(redisSentinelAddress, redisSentinelName, redisSentinelPwd,
                redisSentinelTimeout, genericObjectPoolConfig);
    }

    public static String get(int dbNum, String key) {
        return RedisUtils.get(dbNum, key);
    }

    public static boolean set(int dbNum, String key, String value) {
        return RedisUtils.set(dbNum, key, value);
    }
}
