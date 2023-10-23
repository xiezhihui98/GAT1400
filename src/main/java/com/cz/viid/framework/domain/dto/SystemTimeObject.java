package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class SystemTimeObject {

    @JsonProperty("VIIDServerID")
    private String viidServerId;
    @JsonProperty("TimeMode")
    private String timeMode;
    @JsonProperty("LocalTime")
    private Long localTime;
    @JsonProperty("TimeZone")
    private String timezone;

    public SystemTimeObject() {}

    public SystemTimeObject(String deviceId) {
        this.viidServerId = deviceId;
        this.timeMode = "0";
        this.localTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.timezone = "UTC+8";
    }
}
