package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.SystemTimeObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SystemTimeResponse {

    @JsonProperty("SystemTimeObject")
    private SystemTimeObject systemTimeObject;

    public SystemTimeResponse() {}

    public SystemTimeResponse(String deviceId) {
        this.systemTimeObject = new SystemTimeObject(deviceId);
    }
}
