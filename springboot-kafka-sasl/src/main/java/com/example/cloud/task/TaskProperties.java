package com.example.cloud.task;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author root
 */
@Configuration
@ConfigurationProperties(prefix = "task")
@EnableConfigurationProperties
public class TaskProperties {
    /**
     * 设置成true表示需要节点注册
     */
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
