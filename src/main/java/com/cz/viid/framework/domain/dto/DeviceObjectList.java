package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeviceObjectList {

    @JsonProperty("APEObject")
    private List<APEObject> APEObject;
}
