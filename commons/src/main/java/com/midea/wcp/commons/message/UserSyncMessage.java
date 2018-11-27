package com.midea.wcp.commons.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSyncMessage implements Serializable {
    private static final long serialVersionUID = 283542634428777960L;
    private String appId;
    private String appSecret;
    private String openId;
    private String host;
    private Integer port;
}
