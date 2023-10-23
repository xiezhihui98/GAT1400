package com.cz.viid.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

public interface CustomMessageListener {

    default void configure() {}

    void consumer(List<ConsumerRecord<String, String>> records, Acknowledgment ack);
}
