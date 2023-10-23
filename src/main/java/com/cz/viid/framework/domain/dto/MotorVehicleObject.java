package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MotorVehicleObject {

    @JsonProperty("InfoKind")
    private String InfoKind;
    @JsonProperty("SourceID")
    private String SourceID;
    @ApiModelProperty("全局ID")
    @JsonProperty("MotorVehicleID")
    private String MotorVehicleID;
    @ApiModelProperty("设备ID")
    @JsonProperty("DeviceID")
    private String DeviceID;
    @ApiModelProperty("卡口编号")
    @JsonProperty("TollgateID")
    private String TollgateID;
    @ApiModelProperty("过车时间")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    @JsonProperty("PassTime")
    private String PassTime;
    @ApiModelProperty("车道号")
    @JsonProperty("LaneNo")
    private Integer LaneNo;
    @ApiModelProperty("有无车牌")
    @JsonProperty("HasPlate")
    private String HasPlate;
    @ApiModelProperty("号牌种类")
    @JsonProperty("PlateClass")
    private String PlateClass;
    @ApiModelProperty("号牌颜色")
    @JsonProperty("PlateColor")
    private String PlateColor;
    @ApiModelProperty("车牌号")
    @JsonProperty("PlateNo")
    private String PlateNo;
    @ApiModelProperty("速度")
    @JsonProperty("Speed")
    private String Speed;
    @ApiModelProperty("车身颜色")
    @JsonProperty("VehicleColor")
    private String VehicleColor;
    @ApiModelProperty("车辆类型")
    @JsonProperty("VehicleClass")
    private String VehicleClass;
    @ApiModelProperty("车辆品牌")
    @JsonProperty("VehicleBrand")
    private String VehicleBrand;
    @ApiModelProperty("车辆型号")
    @JsonProperty("VehicleModel")
    private String VehicleModel;
    @ApiModelProperty("车辆长度")
    @JsonProperty("VehicleLength")
    private String VehicleLength;
    @ApiModelProperty("行驶方向")
    @JsonProperty("Direction")
    private String Direction;
    @ApiModelProperty("图片1")
    @JsonProperty("StorageUrl1")
    private String StorageUrl1;
    @ApiModelProperty("图片2")
    @JsonProperty("StorageUrl2")
    private String StorageUrl2;
    @ApiModelProperty("图片列表")
    @JsonProperty("SubImageList")
    private SubImageList SubImageList;
}
