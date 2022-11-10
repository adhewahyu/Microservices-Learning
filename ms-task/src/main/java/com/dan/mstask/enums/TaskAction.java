package com.dan.mstask.enums;

import java.util.HashMap;
import java.util.Map;

public enum TaskAction {
    INSERT("I", "INSERT"),
    UPDATE("U", "UPDATE"),
    DELETE("D", "DELETE");


    private final String value;
    private final String msg;
    private static final Map<String,Object> map = new HashMap();

    TaskAction(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (TaskAction taskAction : TaskAction.values()) {
            map.put(taskAction.value, taskAction);
        }
    }

    public static TaskAction valueOf(int taskAction) {
        return (TaskAction) map.get(taskAction);
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
