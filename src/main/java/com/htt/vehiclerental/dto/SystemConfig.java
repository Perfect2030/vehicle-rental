package com.htt.vehiclerental.dto;

import java.util.Map;

public class SystemConfig {
    private String key;
    private String value;

    public SystemConfig() {
    }

    public SystemConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static SystemConfig fromMap(Map<String, Object> map) {
        return new SystemConfig(
                (String) map.get("key"),
                (String) map.get("value")
        );
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SystemConfig{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
