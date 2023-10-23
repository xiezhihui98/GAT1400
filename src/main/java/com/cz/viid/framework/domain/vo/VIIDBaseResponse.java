package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VIIDBaseResponse {

    @JsonProperty("ResponseStatusObject")
    private ResponseStatusObject responseStatusObject;

    public VIIDBaseResponse() {}

    public VIIDBaseResponse(ResponseStatusObject responseStatusObject) {
        this.responseStatusObject = responseStatusObject;
    }
}
