package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class APEObject {

    @JsonProperty("ApeID")
    private String ApeID;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Model")
    private String Model;
    @JsonProperty("IPAddr")
    private String IPAddr;
    @JsonProperty("Port")
    private String Port;
    @JsonProperty("Longitude")
    private Double Longitude;
    @JsonProperty("Latitude")
    private Double Latitude;
    @JsonProperty("PlaceCode")
    private String PlaceCode;
    @JsonProperty("IsOnline")
    private String IsOnline;
    @JsonProperty("UserId")
    private String UserId;
    @JsonProperty("Password")
    private String Password;
    @JsonProperty("FunctionType")
    private String FunctionType;



}
