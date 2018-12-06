package com.midea.wcp.commons;

import com.google.gson.JsonObject;

class WechatExceptionFactory {

    static WechatException getWechatException(JsonObject result) {
        int errcode = result.get("errcode").getAsInt();

        WechatException exception;
        switch (errcode) {
            case -1:
            case 40001:
                exception = new InvalidTokenException(result);
                break;
            default:
                exception = new WechatException(result);
        }
        return exception;
    }
}
