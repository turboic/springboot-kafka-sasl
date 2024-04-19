package com.example.cloud.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;


@RestController
public class KafkaHandler {
    private static final Logger log = LoggerFactory.getLogger(KafkaHandler.class);


    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public KafkaHandler(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    }

    /**
     * 监听kafka消息
     *
     * @param consumerRecord kafka的消息，用consumerRecord可以接收到更详细的信息，也可以用String message只接收消息
     * @param ack            kafka的消息确认
     *                       使用autoStartup = "false"必须指定id
     */
    @KafkaListener(topics = {"test"}, errorHandler = "myKafkaListenerErrorHandler")
    //@KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = {"topic1", "topic2"}, autoStartup = "false")
    public void listen1(ConsumerRecord<Object, Object> consumerRecord, Acknowledgment ack) {
        try {
            //用于测试异常处理
            //int i = 1 / 0;
            log.info("111111消费监听1111111111消费监听1111111111消费监听11111111111111消费监听11111111111消费监听1111111111消费监听1111111111消费监听11111111消费监听11111111111消费监听11111111111消费监听11111111");
            Object value = consumerRecord.value();
            if (value instanceof HashMap) {
                HashMap<String, Object> map = (HashMap) value;
                map.entrySet().stream().forEach(entry -> log.info("键 = {},值 = {}", entry.getKey(), entry.getValue()));
            } else {
                log.info("kafka消费者监听到数据 {}", value);
            }
            //手动确认
            ack.acknowledge();
        } catch (Exception e) {
            log.error("消费失败：", e);
        }
    }

    /**
     * 下面的方法可以手动操控kafka的队列监听情况
     * 先发送一条消息，因为autoStartup = "false"，所以并不会看到有消息进入监听器。
     * 接着启动监听器，/start/webGroup。可以看到有一条消息进来了。
     * pause是暂停监听，resume是继续监听
     *
     * @param listenerId consumer的group-id
     */
    @RequestMapping("/pause/{listenerId}")
    public void stop(@PathVariable String listenerId) {
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(listenerId)).pause();
    }

    @RequestMapping("/resume/{listenerId}")
    public void resume(@PathVariable String listenerId) {
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(listenerId)).resume();
    }

    @RequestMapping("/start/{listenerId}")
    public void start(@PathVariable String listenerId) {
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(listenerId)).start();
    }
}

