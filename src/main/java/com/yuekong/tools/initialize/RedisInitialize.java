package com.yuekong.tools.initialize;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * @author zhangjj
 * 2020/10/16
 */
public class RedisInitialize {
    private static String name;
    private static String pwd;
    private static String address;
    private static Set<String> hostAndPorts;
    private static GenericObjectPoolConfig config;
    private static int timeOut;
    public static JedisSentinelPool pool;

    public RedisInitialize() {
    }

    public RedisInitialize(String address, String name, String pwd, int timeOut, GenericObjectPoolConfig config) {
        RedisInitialize.name = name;
        RedisInitialize.pwd = pwd;
        RedisInitialize.address = address;
        RedisInitialize.config = config;
        RedisInitialize.timeOut = timeOut;
        //sentinels地址转换成set
        if (StringUtils.isNotBlank(address)) {
            String[] addressInfos = address.split(",");
            hostAndPorts = new HashSet(Arrays.asList(addressInfos));
        }
        if (StringUtils.isBlank(pwd)) {
            pool = new JedisSentinelPool(name, hostAndPorts, config, timeOut);
        } else {
            pool = new JedisSentinelPool(name, hostAndPorts, config, timeOut, pwd);
        }

    }

    public static Jedis getMasterInstance() {
        if (pool == null) {
            throw new JedisException("JedisSentinelPool is null，Cannot get master.......");
        } else {
            Jedis master = null;
            try {
                HostAndPort masterHostAndPort = pool.getCurrentHostMaster();
                master = new Jedis(masterHostAndPort.getHost(), masterHostAndPort.getPort());
                //如果设置密码了要执行auth
                //master.auth(pwd);
            } catch (Exception var5) {
                throw new JedisException("JedisSentinelPool get master  has some question which is " + var5.getMessage());
            } finally {
                if (master != null) {
                    master.close();
                }
            }
            return master;
        }
    }

    public static Jedis getClusterInstance() {
        if (StringUtils.isBlank(name)) {
            throw new JedisException("server name  is null ");
        } else {
            Jedis slave = null;
            Jedis sentinelJedis = null;

            try {
                Iterator var2 = hostAndPorts.iterator();
                while (var2.hasNext()) {
                    String hostAndPort = (String) var2.next();
                    if (!"".equals(hostAndPort) && hostAndPort.contains(":")) {
                        String[] hostAndPortInfo = hostAndPort.split(":");
                        HostAndPort sentinelHostAndPort = new HostAndPort(hostAndPortInfo[0], Integer.valueOf(hostAndPortInfo[1]));
                        sentinelJedis = new Jedis(sentinelHostAndPort.getHost(), sentinelHostAndPort.getPort());
                        if (sentinelJedis != null) {
                            String pingResult = sentinelJedis.ping();
                            if ("PONG".equals(pingResult)) {
                                List<Map<String, String>> slaves = sentinelJedis.sentinelSlaves(name);
                                Random random = new Random();
                                int i = random.nextInt(slaves.size());
                                slave = getJedis((String) ((Map) slaves.get(i)).get("ip"), Integer.parseInt((String) ((Map) slaves.get(i)).get("port")), slaves, i, 0);
                                if (slave != null) {
                                    return slave;
                                }

                                Jedis var10 = getMasterInstance();
                                return var10;
                            }

                            Jedis var7 = getMasterInstance();
                            return var7;
                        }
                    }
                }

                return slave;
            } catch (Exception var14) {
                var14.printStackTrace();
                System.out.println("获取从节点异常=====" + var14);
                Jedis var3 = getMasterInstance();
                return var3;
            } finally {
                if (sentinelJedis != null) {
                    sentinelJedis.close();
                }
                if (slave != null) {
                    slave.close();
                }
            }
        }
    }

    private static Jedis getJedis(String ip, int port, List<Map<String, String>> slaves, int i, int count) {
        Jedis slave = new Jedis((String) ((Map) slaves.get(i)).get("ip"), Integer.parseInt((String) ((Map) slaves.get(i)).get("port")));

        Object var7;
        try {
            if (count == 2) {
                Object var6 = null;
                return (Jedis) var6;
            }
            ++count;
            if (!"slave".equals(((Map) slaves.get(i)).get("flags"))) {
                if (i > 0) {
                    i = 0;
                }
                if (i == 0) {
                    i = 1;
                }
                getJedis(ip, port, slaves, i, count);
            }
            slave.auth(pwd);
            return slave;
        } catch (Exception var11) {
            var7 = null;
        } finally {
            slave.close();
        }

        return (Jedis) var7;
    }
}
