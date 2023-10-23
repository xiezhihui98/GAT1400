package com.cz.viid.framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("viid_tollgate_device")
public class TollgateDevice {

    @JsonProperty("TollgateID")
    @TableId(value = "tollgate_id", type = IdType.NONE)
    private String TollgateID;
    @JsonProperty("Name")
    @TableField("name")
    private String Name;
    @JsonProperty("Longitude")
    @TableField("longitude")
    private Double Longitude;
    @JsonProperty("Latitude")
    @TableField("latitude")
    private Double Latitude;
    @JsonProperty("PlaceCode")
    @TableField("place_code")
    private String PlaceCode;
    @JsonProperty("Status")
    @TableField("status")
    private String Status;
    @JsonProperty("TollgateCat")
    @TableField("tollgate_cat")
    private String TollgateCat;
    @JsonProperty("TollgateUsage")
    @TableField("tollgate_usage")
    private Integer TollgateUsage;
    @JsonProperty("LaneNum")
    @TableField("lane_num")
    private Integer LaneNum;
    @JsonProperty("OrgCode")
    @TableField("org_code")
    private String OrgCode;
}
