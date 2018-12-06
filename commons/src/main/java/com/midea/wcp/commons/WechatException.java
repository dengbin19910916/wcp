package com.midea.wcp.commons;

import com.google.gson.JsonObject;

public class WechatException extends RuntimeException {

    public WechatException(JsonObject message) {
        super(message.get("errcode").getAsInt() + ": " + message.get("errmsg").getAsString());
    }
}
