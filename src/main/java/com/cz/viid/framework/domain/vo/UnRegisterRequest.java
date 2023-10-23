package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.DeviceIdObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UnRegisterRequest {

    @JsonProperty("UnRegisterObject")
    private DeviceIdObject unRegisterObject;
}
