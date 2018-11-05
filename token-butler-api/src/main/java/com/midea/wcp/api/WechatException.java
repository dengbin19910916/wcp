package com.midea.wcp.api;

import com.google.gson.JsonObject;

public class WechatException extends RuntimeException {

    public WechatException(JsonObject message) {
        super(message.get("errcode").getAsInt() + ": " + message.get("errmsg").getAsString());
    }
}
