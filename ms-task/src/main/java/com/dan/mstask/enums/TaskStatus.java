package com.dan.mstask.enums;

import java.util.HashMap;
import java.util.Map;

public enum TaskStatus {
    NEW(0, "NEW"),
    APPROVED(1, "APPROVED"),
    REJECTED(2, "REJECTED");


    private final Integer value;
    private final String msg;
    private static final Map<Integer,Object> map = new HashMap();

    TaskStatus(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            map.put(taskStatus.value, taskStatus);
        }
    }

    public static TaskStatus valueOf(int taskStatus) {
        return (TaskStatus) map.get(taskStatus);
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
