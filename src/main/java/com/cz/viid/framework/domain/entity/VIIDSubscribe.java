package com.cz.viid.framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cz.viid.framework.handler.StringToArrayTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
@TableName(value = "viid_subscrube", autoResultMap = true)
public class VIIDSubscribe {

    @ApiModelProperty("订阅标识符")
    @TableId(value = "subscribe_id", type = IdType.NONE)
    private String subscribeId;
    @ApiModelProperty("订阅标题")
    @TableField("title")
    private String title;
    @ApiModelProperty("订阅类型,7=视频卡口设备,13=车辆信息(卡口过车记录)")
    @TableField("subscribe_detail")
    private String subscribeDetail;
    @ApiModelProperty("资源ID(卡口ID)")
    @TableField(value = "resource_uri", typeHandler = StringToArrayTypeHandler.class, jdbcType = JdbcType.ARRAY)
    private String resourceUri;
    @ApiModelProperty("申请人")
    @TableField("application_name")
    private String applicationName;
    @ApiModelProperty("申请单位")
    @TableField("application_org")
    private String applicationOrg;
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    @TableField("begin_time")
    private Date beginTime;
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    @TableField("end_time")
    private Date endTime;
    @ApiModelProperty("订阅回调地址")
    @TableField("receive_addr")
    private String receiveAddr;
    @ApiModelProperty("数据上报间隔")
    @TableField("report_interval")
    private Integer reportInterval;
    @ApiModelProperty("理由")
    @TableField("reason")
    private String reason;
    @ApiModelProperty("操作类型(0=订阅,1=取消订阅)")
    @TableField("operate_type")
    private Integer operateType;
    @ApiModelProperty("订阅状态(0=订阅中,1=已取消订阅,2=订阅到期,9=未订阅)")
    @TableField("subscribe_status")
    private Integer subscribeStatus;
    @TableField("resource_class")
    private Integer resourceClass;
    @JsonIgnore
    @TableField("result_image_declare")
    private String resultImageDeclare;
    @JsonIgnore
    @TableField("result_feature_declare")
    private Integer resultFeatureDeclare;
    @TableField("server_id")
    private String serverId;
    @TableField("create_time")
    private Date createTime = new Date();
}
