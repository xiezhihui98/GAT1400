package com.cz.viid.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServer;

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, "VIID-Server");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 3000);
        config.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1500);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        config.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 65536);
        config.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 5242880);
        config.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 5242880);
        config.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG, 10485760);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        config.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 300000);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<?, ?> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setBatchListener(true);
        containerFactory.setAutoStartup(false);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return containerFactory;
    }
}
