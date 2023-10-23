package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeviceIdObject {

    @JsonProperty("DeviceID")
    private String deviceId;

    public DeviceIdObject() {}

    public DeviceIdObject(String deviceId) {
        this.deviceId = deviceId;
    }
}
