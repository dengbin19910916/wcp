package com.midea.wcp.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.midea.wcp.api.AccessTokenBook;
import org.springframework.stereotype.Component;

@Component
public class Messenger {

    @Reference(check = false)
    private AccessTokenBook accessTokenBook;

    public void sendOut() {
        String accessToken = accessTokenBook.get().getToken();
        System.out.println(accessToken);
    }
}
