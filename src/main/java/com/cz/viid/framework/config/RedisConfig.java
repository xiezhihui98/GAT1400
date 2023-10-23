package com.cz.viid.framework.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class RedisConfig {

    public static final String CACHE_10MIN = "CACHE_10MIN";
    public static final String CACHE_15MIN = "CACHE_15MIN";
    public static final String CACHE_30MIN = "CACHE_30MIN";
    public static final String CACHE_1HOUSE = "CACHE_1HOUSE";
    public static final String CACHE_1DAY = "CACHE_1DAY";

    @Resource
    ObjectMapper objectMapper;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(jackson);
        template.setHashValueSerializer(jackson);
        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);
        return template;
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        StringRedisSerializer serializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper copyMapper = objectMapper.copy();
        copyMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        copyMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        copyMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        copyMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson.setObjectMapper(copyMapper);

        Map<String, Duration> cacheMap = new HashMap<>();
        cacheMap.put(CACHE_10MIN, Duration.ofMinutes(10));
        cacheMap.put(CACHE_15MIN, Duration.ofMinutes(15));
        cacheMap.put(CACHE_30MIN, Duration.ofMinutes(30));
        cacheMap.put(CACHE_1HOUSE, Duration.ofHours(1));
        cacheMap.put(CACHE_1DAY, Duration.ofDays(1));
        return builder -> {
            for (Map.Entry<String, Duration> entry : cacheMap.entrySet()) {
                builder.withCacheConfiguration(entry.getKey(),
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(entry.getValue())
                                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson))
                                .disableCachingNullValues()

                );
            }
        };
    }

}
