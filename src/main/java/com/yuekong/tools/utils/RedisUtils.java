package com.yuekong.tools.utils;

import com.yuekong.tools.initialize.RedisInitialize;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjj
 * 2020/10/19
 */
@Slf4j
public class RedisUtils {


    public RedisUtils() {
    }

    public static boolean set(int select, String key, String field) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        boolean var5;
        try {
            master.set(key, field);
            boolean var4 = true;
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = false;
        } finally {
            master.close();
        }

        return var5;
    }

    public static boolean setEx(int select, String key, String field, int timeOut) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        boolean var6;
        try {
            String msg = master.setex(key, timeOut, field);
            var6 = "OK".equals(msg);
            return var6;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = false;
        } finally {
            master.close();
        }

        return var6;
    }

    public static boolean psetEx(int select, String key, String field, int timeOut) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        boolean var6;
        try {
            String msg = master.psetex(key, timeOut, field);
            var6 = "OK".equals(msg);
            return var6;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = false;
        } finally {
            master.close();
        }

        return var6;
    }

    public static Long setNx(int select, String key, String field) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        Object var5;
        try {
            Long var4 = master.setnx(key, field);
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = null;
        } finally {
            master.close();
        }

        return (Long) var5;
    }

    public static boolean hset(int select, String key, String field, String values) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        boolean var6;
        try {
            master.hset(key, field, values);
            boolean var5 = true;
            return var5;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = false;
        } finally {
            master.close();
        }

        return var6;
    }


    public static String hmset(int select, String key, Map<String, String> field) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        String var5;
        try {
            String var4 = master.hmset(key, field);
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = "";
        } finally {
            master.close();
        }

        return var5;
    }


    public static long hsetnx(int select, String key, String field, String values) {
        return hsetnx(select, key.getBytes(), field.getBytes(), values.getBytes());
    }

    public static long hsetnx(int select, byte[] key, byte[] field, byte[] values) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        long var6;
        try {
            long var5 = master.hsetnx(key, field, values);
            return var5;
        } catch (Exception var11) {
            log.error(var11.getMessage());
            var6 = 0L;
        } finally {
            master.close();
        }

        return var6;
    }

    public static String get(int select, String key) {
        Jedis master = RedisInitialize.getClusterInstance();
        master.select(select);
        String getMsg = null;

        Object var5;
        try {
            getMsg = master.get(key);
            String var4 = getMsg;
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = getMsg;
        } finally {
            master.close();
        }

        return (String) var5;
    }

    public static String hget(int select, String key, String field) {
        Jedis master = RedisInitialize.getClusterInstance();
        master.select(select);
        String getMsg = null;

        Object var6;
        try {
            getMsg = master.hget(key, field);
            String var5 = getMsg;
            return var5;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = getMsg;
        } finally {
            master.close();
        }

        return (String) var6;
    }

    public static List<String> hmget(int select, String key, String... field) {
        Jedis master = RedisInitialize.getClusterInstance();
        master.select(select);
        List getMsg = null;

        Object var6;
        try {
            getMsg = master.hmget(key, field);
            List var5 = getMsg;
            return var5;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = getMsg;
        } finally {
            master.close();
        }

        return (List) var6;
    }

    public static Map<String, String> hgetAll(int select, String key) {
        Jedis master = RedisInitialize.getClusterInstance();
        master.select(select);
        Map getMsg = null;

        Object var5;
        try {
            getMsg = master.hgetAll(key);
            Map var4 = getMsg;
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = getMsg;
        } finally {
            master.close();
        }

        return (Map) var5;
    }

    public static Long del(int select, String key) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        Object var4;
        try {
            Long var3 = master.del(key);
            return var3;
        } catch (Exception var8) {
            log.error(var8.getMessage());
            var4 = null;
        } finally {
            master.close();
        }

        return (Long) var4;
    }

    public static Long hdel(int select, String key, String field) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        Object var5;
        try {
            Long var4 = master.hdel(key, new String[]{field});
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = null;
        } finally {
            master.close();
        }

        return (Long) var5;
    }

    public static Long exprie(int select, String key, int second) {
        Jedis master = RedisInitialize.getMasterInstance();
        master.select(select);

        Object var5;
        try {
            Long var4 = master.expire(key, second);
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = null;
        } finally {
            master.close();
        }

        return (Long) var5;
    }

    public static boolean exists(int select, String key) {
        Jedis jedis = RedisInitialize.getClusterInstance();
        jedis.select(select);

        boolean var4;
        try {
            boolean var3 = jedis.exists(key);
            return var3;
        } catch (Exception var8) {
            log.error(var8.getMessage());
            var4 = false;
        } finally {
            jedis.close();
        }

        return var4;
    }

    public static boolean hexists(int select, String key, String field) {
        Jedis jedis = RedisInitialize.getClusterInstance();
        jedis.select(select);

        boolean var5;
        try {
            boolean var4 = jedis.hexists(key, field);
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = false;
        } finally {
            jedis.close();
        }

        return var5;
    }

    public static Long getTTL(int select, String key) {
        Jedis jedis = RedisInitialize.getClusterInstance();
        jedis.select(select);

        Object var4;
        try {
            Long var3 = jedis.ttl(key);
            return var3;
        } catch (Exception var8) {
            log.error(var8.getMessage());
            var4 = null;
        } finally {
            jedis.close();
        }

        return (Long) var4;
    }

    public static Long incrBy(int select, String key, Long incrLong) {
        return incrBy(select, key.getBytes(), incrLong);
    }

    public static Long incrBy(int select, byte[] key, Long incrLong) {
        Jedis jedis = RedisInitialize.getMasterInstance();
        jedis.select(select);

        Object var5;
        try {
            Long var4 = jedis.incrBy(key, incrLong);
            return var4;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = null;
        } finally {
            jedis.close();
        }

        return (Long) var5;
    }

    public static Long incr(int select, String key) {
        return incr(select, key.getBytes());
    }

    public static Long incr(int select, byte[] key) {
        Jedis jedis = RedisInitialize.getMasterInstance();
        jedis.select(select);

        Object var4;
        try {
            Long var3 = jedis.incr(key);
            return var3;
        } catch (Exception var8) {
            log.error(var8.getMessage());
            var4 = null;
        } finally {
            jedis.close();
        }

        return (Long) var4;
    }

    public static Long hincr(int select, String key, String field, Long incrLong) {
        Jedis jedis = RedisInitialize.getMasterInstance();
        jedis.select(select);

        Object var6;
        try {
            Long var5 = jedis.hincrBy(key, field, incrLong);
            return var5;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = null;
        } finally {
            jedis.close();
        }

        return (Long) var6;
    }

    public static Double hincrByFloat(int select, String key, String field, double incrLong) {
        return hincrByFloat(select, key.getBytes(), field.getBytes(), incrLong);
    }

    public static Double hincrByFloat(int select, byte[] key, byte[] field, double incrLong) {
        Jedis jedis = RedisInitialize.getMasterInstance();
        jedis.select(select);

        Object var7;
        try {
            Double var6 = jedis.hincrByFloat(key, field, incrLong);
            return var6;
        } catch (Exception var11) {
            log.error(var11.getMessage());
            var7 = null;
        } finally {
            jedis.close();
        }

        return (Double) var7;
    }
}
