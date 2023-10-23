package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.ResponseStatusListObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VIIDNotificationResponse {

    @JsonProperty("ResponseStatusListObject")
    private ResponseStatusListObject responseStatusListObject;

    public VIIDNotificationResponse() {}

    public VIIDNotificationResponse(ResponseStatusListObject responseStatusListObject) {
        this.responseStatusListObject = responseStatusListObject;
    }

}
