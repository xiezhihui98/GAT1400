package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.SubscribeNotifications;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubscribeNotificationRequest {

    @JsonProperty("SubscribeNotificationListObject")
    private SubscribeNotifications subscribeNotificationListObject;
}
