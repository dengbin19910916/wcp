package com.midea.wcp.commons.message;

import lombok.Data;

@Data
public class UserSyncMessage {

    private String appId;
    private String appSecret;
    private String openId;
}
