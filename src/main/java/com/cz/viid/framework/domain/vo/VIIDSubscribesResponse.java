package com.cz.viid.framework.domain.vo;

import com.cz.viid.framework.domain.dto.ResponseStatusListObject;
import com.cz.viid.framework.domain.dto.ResponseStatusObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VIIDSubscribesResponse {

    @JsonProperty("ResponseStatusListObject")
    private ResponseStatusListObject responseStatusListObject;

    public static VIIDSubscribesResponse create(List<ResponseStatusObject> responseStatusObject) {
        ResponseStatusListObject responseStatusListObject = new ResponseStatusListObject();
        responseStatusListObject.setResponseStatusObject(responseStatusObject);
        return create(responseStatusListObject);
    }

    public static VIIDSubscribesResponse create(ResponseStatusListObject responseStatusListObject) {
        VIIDSubscribesResponse response = new VIIDSubscribesResponse();
        response.setResponseStatusListObject(responseStatusListObject);
        return response;
    }
}
