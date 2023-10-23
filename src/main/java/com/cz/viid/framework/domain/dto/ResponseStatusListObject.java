package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ResponseStatusListObject {

    @JsonProperty("ResponseStatusObject")
    private List<ResponseStatusObject> responseStatusObject;

    public ResponseStatusListObject() {}

    public ResponseStatusListObject(List<ResponseStatusObject> responseStatusObject) {
        this.responseStatusObject = responseStatusObject;
    }

    public ResponseStatusListObject(ResponseStatusObject responseStatusObject) {
        this.responseStatusObject = Collections.singletonList(responseStatusObject);
    }

}
