package com.vkams.personal.lasyEats.configurations;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Log4j2
@Component
@EnableCaching
public class RedisConfiguration {
     public static  String redisHost ;
     public static  int REDIS_ENTRY_EXPIRY_IN_SEC;
     private int redisPort;
     private JedisPool jedisPool;
     @Value("${spring.redis.port}")
     public void setRedisPort(int port)
     {
       log.info("Setting up redis port to:"+port);
       this.redisPort=port;
     }
    @Value("${spring.redis.host}")
    public void setRedisHost(String host)
    {
        log.info("Setting up redis Host to:"+host);
        this.redisHost=host;
    }
    @Value("${spring.redis.expiry}")
    public void setRedisEntryExpiryInSec(int expiryInSec)
    {
        log.info("Setting up redis entry expiry(in sec) to:"+expiryInSec);
        this.REDIS_ENTRY_EXPIRY_IN_SEC=expiryInSec;
    }
   private static JedisPoolConfig buildPoolConfig()
   {
      final JedisPoolConfig poolConfig = new JedisPoolConfig();
      poolConfig.setMaxTotal(128);
      poolConfig.setMaxIdle(128);
      poolConfig.setMinIdle(16);
      poolConfig.setTestOnBorrow(true);
      poolConfig.setTestOnReturn(true);
      poolConfig.setTestWhileIdle(true);
      poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
      poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
      poolConfig.setNumTestsPerEvictionRun(3);
      poolConfig.setBlockWhenExhausted(true);
      return poolConfig;
   }

  @PostConstruct
  public void initCache()
      {
          final JedisPoolConfig poolConfig = buildPoolConfig();
          try{
               jedisPool = new JedisPool(poolConfig,redisHost,redisPort);
          }catch (Exception e)
          {
           log.info("Unable to get connection from Jedis pool!:Method name initCache()");
           e.printStackTrace();
          }
      }

    public JedisPool getJedisPool()
    {
         if(jedisPool!=null)
         {
             return jedisPool;
         }
         else
         {
           try{
               final JedisPoolConfig poolConfig =buildPoolConfig();
               jedisPool = new JedisPool(poolConfig,redisHost,redisPort);
           }catch (Exception e)
           {
              log.info("Unable to get redis connection from Jedis pool!:Method name: getJedisPool()");
              e.printStackTrace();
           }
           return jedisPool;
         }
    }

        public boolean isCacheAvailable()
        {
            if(jedisPool==null)
                    return false;
                try(Jedis jedis=this.getJedisPool().getResource())
                {
                        return true;
                }catch (Exception e)
                {
                        e.printStackTrace();
                }
        return false;
        }

        public void destroyCache()
        {
        if(jedisPool!=null)
        {
            jedisPool.getResource().flushAll();
            jedisPool.destroy();
            jedisPool = null;
        }
        }


}
