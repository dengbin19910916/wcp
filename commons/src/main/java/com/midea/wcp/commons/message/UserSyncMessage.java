package com.midea.wcp.commons.message;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSyncMessage implements Serializable {

    private String appId;
    private String appSecret;
    private String openId;
    private String host;
    private Integer port;

    public UserSyncMessage(String appId, String appSecret, String openId) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.openId = openId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
