package com.cz.viid.framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("viid_ape_device")
public class APEDevice {

    @TableId(value = "ape_id", type = IdType.NONE)
    private String apeId;
    @TableField("name")
    private String name;
    @TableField("model")
    private String model;
    @TableField("ip_addr")
    private String ipAddr;
    @TableField("port")
    private String port;
    @TableField("longitude")
    private Double longitude;
    @TableField("latitude")
    private Double latitude;
    @TableField("place_code")
    private String placeCode;
    @TableField("is_online")
    private String isOnline;
    @TableField("user_id")
    private String userId;
    @TableField("password")
    private String password;
    @TableField("function_type")
    private String functionType;
}
