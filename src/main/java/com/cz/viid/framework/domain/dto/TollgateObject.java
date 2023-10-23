package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TollgateObject {

    @JsonProperty("TollgateID")
    private String TollgateID;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Longitude")
    private Double Longitude;
    @JsonProperty("Latitude")
    private Double Latitude;
    @JsonProperty("PlaceCode")
    private String PlaceCode;
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("TollgateCat")
    private String TollgateCat;
    @JsonProperty("TollgateUsage")
    private Integer TollgateUsage;
    @JsonProperty("LaneNum")
    private Integer LaneNum;
    @JsonProperty("OrgCode")
    private String OrgCode;
}
