package com.midea.wcp.commons.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSyncMessage {

    private String appId;
    private String appSecret;
    private String openId;
}
