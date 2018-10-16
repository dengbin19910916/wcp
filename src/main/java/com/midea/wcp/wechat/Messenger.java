package com.midea.wcp.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.midea.wcp.api.AccessTokenBook;
import com.midea.wcp.site.model.Account;
import com.midea.wcp.site.model.User;
import com.midea.wcp.wechat.exception.WechatException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 信使负责处理消息的发送与接收。
 */
@Component
public class Messenger {

    @Reference(check = false)
    private AccessTokenBook accessTokenBook;

    @Retryable(value = WechatException.class, maxAttempts = 1)
    public void send(Account account) {
        String accessToken = accessTokenBook.get().getToken();
        System.out.println(account.getAppid());
        throw new WechatException(40001, "invalid credential");
    }

    public void send(Account account, String... openid) {
        String accessToken = accessTokenBook.get().getToken();
        System.out.println(account.getAppid() + ":: " + Arrays.toString(openid));
        System.out.println(Arrays.toString(openid));
        throw new WechatException(40001, "invalid credential");
    }

    public void send(Account account, User.Tag... tags) {
        String accessToken = accessTokenBook.get().getToken();
        for (User.Tag tag : tags) {
            System.out.println(tag);
        }
        throw new WechatException(40001, "invalid credential");
    }

    public void receive() {
        String accessToken = accessTokenBook.get().getToken();
        System.out.println(accessToken);
    }
}
