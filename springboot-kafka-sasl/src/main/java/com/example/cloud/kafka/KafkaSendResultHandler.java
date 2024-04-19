package com.example.cloud.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSendResultHandler implements ProducerListener<Object, Object> {
    private static final Logger log = LoggerFactory.getLogger(KafkaSendResultHandler.class);


    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        log.info("消息发送成功：" + producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord producerRecord,Exception exception) {
        log.error("消息发送失败：" + producerRecord.toString() + exception.getMessage());
    }
}
