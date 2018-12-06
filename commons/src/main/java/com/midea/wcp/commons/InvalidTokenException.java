package com.midea.wcp.commons;

import com.google.gson.JsonObject;

public class InvalidTokenException extends WechatException {

    public InvalidTokenException(JsonObject message) {
        super(message);
    }


}
