package com.dan.shared.enums;

import java.util.HashMap;
import java.util.Map;

public enum BooleanStatus {
    ORA_FALSE(0, "FALSE"),
    PG_FALSE(false, "FALSE"),
    ORA_TRUE(1, "TRUE"),
    PG_TRUE(true, "TRUE");

    private final Object value;
    private final String msg;
    private static final Map<Object,Object> map = new HashMap();

    BooleanStatus(Object value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (BooleanStatus booleanStatus : BooleanStatus.values()) {
            map.put(booleanStatus.value, booleanStatus);
        }
    }

    public static BooleanStatus valueOf(int activeStatus) {
        return (BooleanStatus) map.get(activeStatus);
    }

    public Object getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
