package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NonMotorVehicle {

    @JsonProperty("NonMotorVehicleID")
    private String NonMotorVehicleID;
    @JsonProperty("InfoKind")
    private String InfoKind;
    @JsonProperty("SourceID")
    private String SourceID;
    @JsonProperty("DeviceID")
    private String DeviceID;
    @JsonProperty("LeftTopX")
    private String LeftTopX;
    @JsonProperty("LeftTopY")
    private String LeftTopY;
    @JsonProperty("RightBtmX")
    private String RightBtmX;
    @JsonProperty("RightBtmY")
    private String RightBtmY;
    @JsonProperty("MarkTime")
    private String MarkTime;
    @JsonProperty("AppearTime")
    private String AppearTime;
    @JsonProperty("DisappearTime")
    private String DisappearTime;
    @JsonProperty("HasPlate")
    private String HasPlate;
    @JsonProperty("PlateClass")
    private String PlateClass;
    @JsonProperty("PlateColor")
    private String PlateColor;
    @JsonProperty("PlateNo")
    private String PlateNo;
    @JsonProperty("PlateNoAttach")
    private String PlateNoAttach;
    @JsonProperty("PlateDescribe")
    private String PlateDescribe;
    @JsonProperty("IsDecked")
    private String IsDecked;
    @JsonProperty("IsAltered")
    private String IsAltered;
    @JsonProperty("IsCovered")
    private String IsCovered;
    @JsonProperty("Speed")
    private String Speed;
    @JsonProperty("DrivingStatusCode")
    private String DrivingStatusCode;
    @JsonProperty("UsingPropertiesCode")
    private String UsingPropertiesCode;
    @JsonProperty("VehicleBrand")
    private String VehicleBrand;
    @JsonProperty("VehicleType")
    private String VehicleType;
    @JsonProperty("VehicleLength")
    private String VehicleLength;
    @JsonProperty("VehicleWidth")
    private String VehicleWidth;
    @JsonProperty("VehicleHeight")
    private String VehicleHeight;
    @JsonProperty("VehicleColor")
    private String VehicleColor;
    @JsonProperty("VehicleHood")
    private String VehicleHood;
    @JsonProperty("VehicleTrunk")
    private String VehicleTrunk;
    @JsonProperty("VehicleWheel")
    private String VehicleWheel;
    @JsonProperty("WheelPrintedPattern")
    private String WheelPrintedPattern;
    @JsonProperty("VehicleWindow")
    private String VehicleWindow;
    @JsonProperty("VehicleRoof")
    private String VehicleRoof;
    @JsonProperty("VehicleDoor")
    private String VehicleDoor;
    @JsonProperty("SideOfVehicle")
    private String SideOfVehicle;
    @JsonProperty("CarOfVehicle")
    private String CarOfVehicle;
    @JsonProperty("RearviewMirror")
    private String RearviewMirror;
    @JsonProperty("VehicleChassis")
    private String VehicleChassis;
    @JsonProperty("VehicleShielding")
    private String VehicleShielding;
    @JsonProperty("FilmColor")
    private String FilmColor;
    @JsonProperty("IsModified")
    private Integer IsModified;
    @ApiModelProperty("图片列表")
    @JsonProperty("SubImageList")
    private SubImageList SubImageList;
}
