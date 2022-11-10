package com.dan.shared.enums;

import java.util.HashMap;
import java.util.Map;

public enum MessageCode {
    OK("0000", "OK"),
    NOK("9999", "NOK");

    private final String value;
    private final String msg;
    private static final Map<String,Object> map = new HashMap();

    MessageCode(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (MessageCode messageCode : MessageCode.values()) {
            map.put(messageCode.value, messageCode);
        }
    }

    public static MessageCode valueOf(int messageCode) {
        return (MessageCode) map.get(messageCode);
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
