package com.example.cloud.service;

import com.example.cloud.entity.User;

import java.util.Map;

/**
 * @author root
 */
public interface KafkaService {

    /**
     * 发送kafka数据
     * @param user
     */
    void send(User user);

    void send(Map map);
}
