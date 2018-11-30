package com.midea.wcp.user;

import com.midea.wcp.commons.message.UserSyncMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {
    @RabbitListener(queues = "user")
    public void userListener(UserSyncMessage userSyncMessage){
        System.out.println("user");
        System.out.println(userSyncMessage);
    }


}
