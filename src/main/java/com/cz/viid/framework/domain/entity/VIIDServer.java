package com.cz.viid.framework.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cz.viid.framework.config.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("viid_server")
public class VIIDServer {

    @ApiModelProperty(value = "视图库编号")
    @TableId(value = "server_id",type = IdType.NONE)
    private String serverId;
    @ApiModelProperty(value = "视图库名称")
    @TableField(value = "server_name")
    private String serverName;
    @ApiModelProperty(value = "交互协议")
    @TableField("scheme")
    private String scheme = "http";
    @ApiModelProperty(value = "视图库地址")
    @TableField(value = "host")
    private String host;
    @ApiModelProperty(value = "视图库端口")
    @TableField(value = "port")
    private Integer port;
    @ApiModelProperty(value = "授权用户")
    @TableField(value = "username")
    private String username;
    @ApiModelProperty(value = "授权用户凭证")
    @TableField(value = "authenticate")
    private String authenticate;
    @ApiModelProperty(value = "是否启用")
    @TableField(value = "enabled")
    private Boolean enabled;
    @ApiModelProperty(value = "服务类别", notes = "0=自己,1=下级,2=上级")
    @TableField(value = "category")
    private String category;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private Date createTime;
    @ApiModelProperty("是否开启双向保活")
    @TableField(value = "keepalive")
    private Boolean keepalive;
    @ApiModelProperty(value = "数据传输类型", example = "HTTP")
    @TableField("transmission")
    private String transmission = Constants.VIID_SERVER.TRANSMISSION.HTTP;
    @ApiModelProperty("在线状态")
    @TableField(value = "online", exist = false)
    private Boolean online = false;

    public String httpUrlBuilder() {
        return scheme + "://" + host + ":" + port;
    }
}
