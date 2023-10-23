package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubscribeNotifications {

    @JsonProperty("PageRecordNum")
    private Long pageRecordNum;
    @JsonProperty("TotalNum")
    private Long totalNum;
    @JsonProperty("SubscribeNotificationObject")
    private List<SubscribeNotificationObject> subscribeNotificationObject;
}
