package com.boyoi.core.utils;

import com.boyoi.core.domain.BaseDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import java.util.Arrays;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Redis帮助类
 * 获得Jedis客户端,添加（修改），删除，查找
 *
 * @author Chenj
 */
public class RedisUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);
    //防止实例化
    private RedisUtil(){ }
    private static JedisPool jedisPool;
    //连接是否正常 的标志
    private static Boolean redisIsOk = false;
    //redis配置文件路径（classpath下）
    private static PropertiesUtil propUtil = new PropertiesUtil("/redis.properties");
    private static int database = propUtil.readInt("redis.database", 0);

    //初始化连接池，并清空缓存
    static {
        initPool();
    }

    /**
     * 获得Redis客户端
     * 如系统第一次运行没获得客户端。将返回Null.
     * 直接从数据库查询
     * @return jedis
     */
    public static Jedis getJedis(){
        if(redisIsOk){
            try {
                Jedis resource = jedisPool.getResource();
                resource.select(database);
                return resource;
            }catch (Exception e){
                //添加定时任务,重新获得连接池
                redisIsOk = false;
                reConnection();
            }
        }
        LOG.error("没有获得Jedis客户端,请检查Redis服务器!");
        return null;
    }

    /**
     * 增加一个key-value类型的BaseDomain对象
     * @param t 实体，必须继续BaseDomain
     * @return Status code reply
     */
	public static <T extends BaseDomain> String add(T t){
        if(null != t  && redisIsOk){
            LOG.debug("开始向Redis缓存中添加(更新)数据!Key值为：{}", t.getGuid());
            Jedis jedis = getJedis();
            if(null != jedis){
                try{
                    String status = jedis.set(SafeEncoder.encode(t.getGuid()),
                            FstUtil.serialize(t));
                    LOG.debug("添加(更新)数据结束!");
                    return status;
                }catch (Exception e){
                    LOG.error("添加(更新)数据发生异常!将返回Null,从数据库中查询!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 增加一个key-value类型的Object对象
     * @param key 关键字
     * @param value Object
     * @return Status code reply
     */
	public static String add(String key, Object value){
        if(null != value && redisIsOk){
            LOG.debug("开始向Redis缓存中添加(更新)数据!此对象为:{}", value.getClass().getSimpleName());
            Jedis jedis=getJedis();
            if(null != jedis) {
                try{
                    String status = jedis.set(SafeEncoder.encode(key),
                                              FstUtil.serialize(value));
                    LOG.debug("添加(更新)数据成功!");
                    return status;
                }catch (Exception e){
                    LOG.error("添加(更新)数据发生异常!将返回Null,从数据库中查询!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 增加一个key-value类型的Object对象
     * @param key 关键字
     * @param value Object
     * @param expireTime 过期时间(秒)
     * @param cover 是否覆盖
     * @return Status code reply
     */
	public static String add(String key, Object value, long expireTime, boolean cover){
        if(null != value && redisIsOk){
            LOG.debug("开始向Redis缓存中添加(更新)数据!此对象为:{}", value.getClass().getSimpleName());
            Jedis jedis=getJedis();
            if(null != jedis) {
                try{
                    String status = jedis.set(SafeEncoder.encode(key),
                            FstUtil.serialize(value),
                            cover ? SafeEncoder.encode("XX") : SafeEncoder.encode("NX"), // 是否覆盖
                            SafeEncoder.encode("EX"), // 单位秒
                            expireTime);              // 过期时间
                    LOG.debug("添加(更新)数据成功!");
                    return status;
                }catch (Exception e){
                    LOG.error("添加(更新)数据发生异常!将返回Null,从数据库中查询!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 增加一个key-value类型的Object对象
     * @param key 关键字
     * @param value Object
     * @param expireTime 过期时间(秒)
     * @param cover 是否覆盖
     * @return Status code reply
     */
	public static String add(String key, Object value, long expireTime, boolean cover, int db){
        if(null != value && redisIsOk){
            LOG.debug("开始向Redis缓存中添加(更新)数据!此对象为:{}", value.getClass().getSimpleName());
            Jedis jedis=getJedis();
            if(null != jedis) {
                try{
                    // 选择一个db
                    jedis.select(db);
                    String status = jedis.set(SafeEncoder.encode(key),
                            FstUtil.serialize(value),
                            cover ? SafeEncoder.encode("XX") : SafeEncoder.encode("NX"), // 是否覆盖
                            SafeEncoder.encode("EX"), // 单位秒
                            expireTime);              // 过期时间
                    LOG.debug("添加(更新)数据成功!");
                    return status;
                }catch (Exception e){
                    LOG.error("添加(更新)数据发生异常!将返回Null,从数据库中查询!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 根据key批量删除
     * @param keys key数组
     * @return 大于0表示删除了，等于0表示未删除，-1表示发生异常
     */
	public static Long del(String... keys){
        if(keys.length>0  && redisIsOk){
            LOG.debug("开始向Redis缓存中删除数据!Key值为：{}", Arrays.toString(keys));
            Jedis jedis=getJedis();
            if(null != jedis) {
                try {
                    Long reply = jedis.del(keys);
                    LOG.debug("删除数据成功!");
                    return reply;
                } catch (Exception e) {
                    LOG.error("删除数据发生异常!将返回0!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return -1l;
    }

    /**
     * 根据key匹配模糊删除
     * @param pattern 匹配规则
     * @return 大于0表示删除了，等于0表示未删除，-1表示发生异常
     */
	public static Long delByPattern(String pattern){
        if(null != pattern  && redisIsOk){
            LOG.debug("开始向Redis缓存中模糊删除数据!规则是：{}", pattern);
            Jedis jedis=getJedis();
            if(null != jedis){
                try {
                    Set<String> keys = jedis.keys(pattern);
                    //批量删除
                    return del(keys.toArray(new String[keys.size()]));
                }catch (Exception e){
                    LOG.error("模糊删除数据发生异常!将返回0!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return -1l;
    }

    /**
     * 根据key获得实体
     * @param key key
     * @param <T> 被转化的泛型
     * @return 实体
     */
	public static <T> T get(String key){
        if(null != key  && redisIsOk){
            LOG.debug("开始向Redis缓存中获得数据!Key值为：{}", key);
            Jedis jedis=getJedis();
            if(null != jedis){
                byte[] bytes;
                try {
                    bytes = jedis.get(SafeEncoder.encode(key));
                    //不为空。反序列化并返回对象
                    if(null != bytes){
                        T t = FstUtil.unserialize(bytes);
                        LOG.debug("获得数据成功!");
                        return t;
                    }
                    LOG.info("获得数据失败,找不到对应的值!");
                }catch (Exception e){
                    LOG.error("获得数据发生异常!将返回Null!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return null;
    }

    /**
     * 根据key获得实体
     * @param key key
     * @param db 数据库
     * @param <T> 被转化的泛型
     * @return 实体
     */
	public static <T> T get(String key, int db){
        if(null != key  && redisIsOk){
            LOG.debug("开始向Redis缓存中获得数据!Key值为：{}", key);
            Jedis jedis=getJedis();
            if(null != jedis){
                byte[] bytes;
                try {
                    jedis.select(db);
                    bytes = jedis.get(SafeEncoder.encode(key));
                    //不为空。反序列化并返回对象
                    if(null != bytes){
                        T t = FstUtil.unserialize(bytes);
                        LOG.debug("获得数据成功!");
                        return t;
                    }
                    LOG.info("获得数据失败,找不到对应的值!");
                }catch (Exception e){
                    LOG.error("获得数据发生异常!将返回Null!异常信息为:{}", e.getMessage());
                }finally {
                    jedis.close();
                }
            }
        }
        return null;
    }

    public static boolean clearCache(){
        //第一次初始化,清空缓存!
        Jedis jedis = jedisPool.getResource();
        if(null != jedis && jedis.isConnected()){
            jedis.select(propUtil.readInt("redis.database", 0));
            jedis.flushDB();
            LOG.info("成功清除Redis缓存!");
            return true;
        }
        LOG.info("清除Redis缓存失败!");
        return false;
    }

    /**
     * 重新获得redis链接
     * 通过配置文件传值,没有值设置为半小时
     */
    private static void reConnection(){
        if(!redisIsOk){
            Timer timer = new Timer();
            //任务
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(initPool()){
                        redisIsOk = true;
                        this.cancel();//初始化成功,取消任务!
                    }
                }
            };
            //执行任务
            timer.schedule(timerTask, propUtil.readLong("redis.reConnectionSecond", 180000l));
        }
    }

    /**
     * 初始化redis连接池
     */
	private static Boolean initPool(){
        if(!redisIsOk){
            Jedis jedis = null;
            try {
                //初始化连接池
                LOG.info("开始初始化Redis连接池!");
                jedisPool = new JedisPool(getJedisPoolConfig(),
                                          propUtil.readString("redis.Host", "192.168.1.17"),
                                          propUtil.readInt("redis.Port", Protocol.DEFAULT_PORT),
                                          propUtil.readInt("redis.connTimeOut", Protocol.DEFAULT_TIMEOUT),
                                          propUtil.readString("redis.password", null));

                //第一次初始化,清空缓存!
                clearCache();

                //初始化成功!
                redisIsOk = true;
                LOG.info("初始化Redis连接池成功!");
                return true;

            }catch (Exception e){
                //初始化出错,添加定时任务,重新获得连接
                LOG.error("初始化Redis连接池出错,请检查{}服务器!", propUtil.readString("redis.Host", "127.0.0.1"));
                redisIsOk = false;
                reConnection();
            }finally {
                if (null != jedis)
                    jedis.close();
            }
        }
        return false;
    }

    /**
     * Redis 连接池配置
     * @return JedisPoolConfig
     */
    private static JedisPoolConfig getJedisPoolConfig(){
        //连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(propUtil.readInt("redis.MaxIdle", 100));
        jedisPoolConfig.setMinIdle(propUtil.readInt("redis.MinIdle", 10));
        jedisPoolConfig.setMaxTotal(propUtil.readInt("redis.MaxTotal", 100));
        jedisPoolConfig.setMaxWaitMillis(propUtil.readLong("redis.MaxWaitMillis", 2000l));
        return jedisPoolConfig;
    }


}