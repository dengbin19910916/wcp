package com.midea.wcp.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/wechat/users")
public class UserPorterController {

    private final UserPorter userPorter;

    public UserPorterController(UserPorter userPorter) {
        this.userPorter = userPorter;
    }

    @GetMapping
    public String pull(@RequestParam String appId, @RequestParam String appSecret,
                       @RequestParam(required = false) String host, @RequestParam(required = false) Integer port)
            throws IOException, InterruptedException {
        return userPorter.pull(appId, appSecret, host, port);
    }

}
