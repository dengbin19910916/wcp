package com.midea.wcp.commons.message;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSyncMessage implements Serializable {

    private String appId;
    private String appSecret;
    private List<String> openIds;
    private String host;
    private Integer port;

    public UserSyncMessage(String appId, String appSecret, List<String> openIds) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.openIds = openIds;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
