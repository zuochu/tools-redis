package com.yuekong.tools.constant;

/**
 * @author zhangjj
 * 2020/10/16
 */
public class RedisSentinelConstants {

    public static String redisSentinelAddress = "127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381";
    public static String redisSentinelName = "mymaster";
    public static String redisSentinelPwd = "";
    public static int redisSentinelTimeout = 1000 * 6;
}
