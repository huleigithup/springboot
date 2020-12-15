package com.cy.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import javax.naming.NamingEnumeration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86158
 */
@Configuration
@EnableCaching
@EnableAutoConfiguration
public class RedisConfig extends CachingConfigurerSupport {
    private String password;
    private int maxActive;
    private long maxWait;
    private int maxldle;
    private int minldle;
    private int timeout;
    private String clusterNodes;
    private int maxAttempts;

    @Bean
    // 返回redis集群配置redisClusterConfiguration
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String[] cNodes = clusterNodes.split(",");
        List<RedisNode> redisNodes = new ArrayList<>();
        for (String node : cNodes) {
            String[] item = node.split(":");
            String ip = item[0];
            String port = item[1];
            redisNodes.add(new RedisNode(ip, Integer.parseInt(port)));
        }
        redisClusterConfiguration.setClusterNodes(redisNodes);
        return redisClusterConfiguration;
    }

    public RedisConnectionFactory redisConnectionFactory() {
//        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder defaultJedisClientConfigurationBuilder = new JedisClientConfiguration.DefaultJedisClientConfigurationBuilder();
//        new JedisConnectionFactory(redisClusterConfiguration(),)
        return null;
    }

    @Bean(name = "stringRedisTemplate")
    @ConditionalOnMissingBean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
    }

    /**
     * 配置redisTempalte
     * 设置添加序列化器，key使用string序列化器，value使用Json序列化器，还有简单设置通过，改变defaultSerializer对象实现
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> template(RedisConnectionFactory factory) {
        // 创建RedisTemplate<String, Object>对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置开启事物false
        redisTemplate.setEnableTransactionSupport(false);
        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 定义Jackson2JsonRedisSerializer序列化对象
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);
        StringRedisSerializer stringSerial = new StringRedisSerializer();
        // redis key 序列化方式使用stringSerial
        redisTemplate.setKeySerializer(stringSerial);
        // redis value 序列化方式使用jackson
        redisTemplate.setValueSerializer(jacksonSeial);
        // redis hash key 序列化方式使用stringSerial
        redisTemplate.setHashKeySerializer(stringSerial);
        // redis hash value 序列化方式使用jackson
        redisTemplate.setHashValueSerializer(jacksonSeial);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 设置RedisCacheManager使用cache注解管理redis缓存
     */
    @Override
    public CacheManager cacheManager() {
        return super.cacheManager();
    }

}
