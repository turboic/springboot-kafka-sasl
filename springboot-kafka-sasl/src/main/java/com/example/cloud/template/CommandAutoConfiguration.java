package com.example.cloud.template;

import com.example.cloud.service.KafkaService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter({KafkaService.class})
public class CommandAutoConfiguration {
    public CommandAutoConfiguration() {
    }

    @Bean(value = "commandAspect")
    public CommandAspect commandAspect(KafkaService kafkaService) {
        return new CommandAspect(kafkaService);
    }
}

