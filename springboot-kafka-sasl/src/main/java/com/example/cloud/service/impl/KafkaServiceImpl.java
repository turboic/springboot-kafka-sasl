package com.example.cloud.service.impl;

import com.example.cloud.entity.User;
import com.example.cloud.kafka.KafkaSendResultHandler;
import com.example.cloud.service.KafkaService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author root
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class KafkaServiceImpl implements KafkaService {

    private static final String TOPIC = "test";

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public KafkaServiceImpl(KafkaTemplate<Object, Object> kafkaTemplate, KafkaSendResultHandler kafkaSendResultHandler) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setProducerListener(kafkaSendResultHandler);
    }



    @Override
    public void send(User user) {
        kafkaTemplate.send(TOPIC,user);
    }


    @Override
    public void send(Map map) {
        kafkaTemplate.send(TOPIC,map);
    }
}
