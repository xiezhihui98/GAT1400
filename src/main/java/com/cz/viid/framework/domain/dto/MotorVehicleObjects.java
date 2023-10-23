package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MotorVehicleObjects {

    @JsonProperty("PageRecordNum")
    private Long PageRecordNum;
    @JsonProperty("TotalNum")
    private Long TotalNum;
    @JsonProperty("MotorVehicleObject")
    private List<MotorVehicleObject> MotorVehicleObject;

}
