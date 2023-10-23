package com.cz.viid.framework.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.TimeZone;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AppConfig {
    private final Logger log = LoggerFactory.getLogger(AppConfig.class);
    public static final ThreadPoolTaskExecutor GLOBAL_EXECUTOR = threadPoolTaskExecutor();

    /**
     * jackson格式化时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .timeZone(TimeZone.getTimeZone("GMT+8"))
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ThreadPoolTaskExecutor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(20);
        executor.setCorePoolSize(5);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(300);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }
}
