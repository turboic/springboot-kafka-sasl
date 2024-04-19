package com.example.cloud.task;

import com.example.cloud.entity.User;
import com.example.cloud.service.KafkaService;
import com.example.cloud.utils.UuIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author root
 */
@Component

public class ScheduledTaskConfig implements CommandLineRunner, SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskConfig.class);


    private static final String CRON_EXPRESSION = "0 */1 * * * ?";

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private TaskProperties taskProperties;


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        TriggerTask triggerTask = new TriggerTask(() -> execute(), triggerContext ->
                new CronTrigger(CRON_EXPRESSION).nextExecutionTime(triggerContext));
        scheduledTaskRegistrar.addTriggerTask(triggerTask);
    }

    @Override
    public void run(String... args) {
        log.error("ScheduledTaskConfig init done ....!");
    }


    @Transactional(rollbackFor = Exception.class)
    public void execute() {
        if (taskProperties.isEnable()) {
            User user = new User();
            user.setName("后台任务测试");
            user.setId(UuIdUtil.generateId());
            kafkaService.send(user);
        }
    }
}
