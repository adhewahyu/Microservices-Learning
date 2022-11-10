package com.dan.shared.enums;

import java.util.HashMap;
import java.util.Map;

public enum RegexType {

    ANY("ANY"),
    NUMERIC_ONLY("NUMERIC ONLY"),
    ALPHANUMERIC("ALPHANUMERIC ONLY"),
    EMAIL("EMAIL"),
    ALPHABET_ONLY("ALPHABET_ONLY");

    private final String value;
    private static final Map<String,Object> map = new HashMap();

    RegexType(String value) {
        this.value = value;
    }

    static {
        for (RegexType regexType : RegexType.values()) {
            map.put(regexType.value, regexType);
        }
    }

    public static RegexType valueOf(int regexType) {
        return (RegexType) map.get(regexType);
    }

    public String getValue() {
        return value;
    }

}
