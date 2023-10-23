package com.cz.viid.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;

public class DefaultKafkaListenerErrorHandler implements ConsumerAwareListenerErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultKafkaListenerErrorHandler.class);

    @Override
    public Object handleError(Message<?> m, ListenerExecutionFailedException e, Consumer<?, ?> c) {
        MessageHeaders headers = m.getHeaders();
        String topic = headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class);
        Integer partitionId = headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class);
        Long offset = headers.get(KafkaHeaders.OFFSET, Long.class);
        if (Objects.nonNull(partitionId) && Objects.nonNull(offset)) {
            log.error("Kafka消费者出错: {}, 回撤主题:{}分区:{}偏移量至:{}", e.getMessage(), topic, partitionId, offset);
            c.seek(new org.apache.kafka.common.TopicPartition(topic, partitionId), offset);
        }
        return null;
    }
}
