package com.example.cloud.kafka;

import com.example.cloud.entity.Result;
import com.example.cloud.entity.User;
import com.example.cloud.service.KafkaService;
import com.example.cloud.template.CommandOperate;
import com.example.cloud.template.OperationType;
import com.example.cloud.utils.UuIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/kafka")

public class KafkaController {

    @Autowired
    private KafkaService kafkaService;


    @RequestMapping("/send")
    @CommandOperate
    public Result send(@RequestBody User user) {
        user.setId(UuIdUtil.generateId());
        kafkaService.send(user);
        try {
            return Result.ok("发送成功");
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
