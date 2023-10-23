package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.DeviceIdObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterRequest {

    @JsonProperty("RegisterObject")
    private DeviceIdObject registerObject;

    public DeviceIdObject getRegisterObject() {
        return registerObject;
    }

    public void setRegisterObject(DeviceIdObject registerObject) {
        this.registerObject = registerObject;
    }
}
