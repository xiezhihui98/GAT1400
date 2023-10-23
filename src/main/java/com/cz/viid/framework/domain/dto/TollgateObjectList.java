package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TollgateObjectList {

    @JsonProperty("TollgateObject")
    private List<TollgateObject> TollgateObject;
}
