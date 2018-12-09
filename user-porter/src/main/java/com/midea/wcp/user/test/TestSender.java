package com.midea.wcp.user.test;

import com.midea.wcp.commons.message.UserSyncMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestSender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TestSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/gkd")
    public void gkd() {
        String uuid = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend("sync-user","WeChat",new UserSyncMessage(uuid, "h", "h", "hi", 3306));
        rabbitTemplate.convertAndSend("sync-user","WeChat",new UserSyncMessage(uuid, "h", "h", "hi", 3306));
    }
    @GetMapping("/user")
    public void user() {
        String uuid = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend("get-detail","WeChat",new UserSyncMessage(uuid, "h", "h", "hi", 3306));
    }

}
