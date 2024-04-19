package com.example.cloud.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author root
 */
@Component
public class ConfigurationRunnerPrint implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationRunnerPrint.class);


    public ConfigurationRunnerPrint() {
    }

    @Override
    public void run(ApplicationArguments args)  {
    }
}
