package com.midea.wcp.wechat.exception;

public class WechatException extends RuntimeException {

    protected int code;
    protected String message;

    public WechatException(int code, String message) {
        super(getMessage(code, message));

        this.code = code;
        this.message = message;
    }

    private static String getMessage(int code, String message) {
        return "WeChat Exception[" + code + "]: " + message;
    }

    @Override
    public String toString() {
        return getMessage(code, message);
    }
}
