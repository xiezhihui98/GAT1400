package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.DeviceIdObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeepaliveRequest {

    @JsonProperty("KeepaliveObject")
    private DeviceIdObject keepaliveObject;

    public static KeepaliveRequest builder(DeviceIdObject keepaliveObject) {
        KeepaliveRequest keepaliveRequest = new KeepaliveRequest();
        keepaliveRequest.setKeepaliveObject(keepaliveObject);
        return keepaliveRequest;
    }

}
