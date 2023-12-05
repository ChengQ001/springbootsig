package com.chengq.ulit.redisson;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengqing
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(value = "redisson.enabled", havingValue = "true")
public class RedissonAutoConfiguration {


    @Autowired
    private RedissonProperties properties;

    /**
     * 哨兵模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonSentinel() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(properties.getSentinelAddresses())
                .setMasterName(properties.getMasterName())
                .setTimeout(properties.getTimeout())
                .setMasterConnectionPoolSize(properties.getMasterConnectionPoolSize())
                .setDatabase(properties.getDatabase())
                .setSlaveConnectionPoolSize(properties.getSlaveConnectionPoolSize());

        if (properties.getPassword() != null && !"".equals(properties.getPassword())) {
            serverConfig.setPassword(properties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(properties.getAddress())
                .setTimeout(properties.getTimeout())
                .setConnectionPoolSize(properties.getConnectionPoolSize())
                .setDatabase(properties.getDatabase())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize());

        if (properties.getPassword() != null && !"".equals(properties.getPassword())) {
            serverConfig.setPassword(properties.getPassword());
        }

        return Redisson.create(config);

    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     *
     * @return
     */
    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        RedissonDistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        RedissLockUtil.setLocker(locker);
        return locker;
    }

}
