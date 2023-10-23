package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

@Data
public class ResponseStatusObject {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("RequestURL")
    private String requestUrl;
    @JsonProperty("StatusCode")
    private String statusCode;
    @JsonProperty("StatusString")
    private String statusString;
    @JsonProperty("LocalTime")
    private String localTime;

    public ResponseStatusObject() {
    }

    public ResponseStatusObject(String requestUrl, String statusCode, String statusString) {
        this(null, requestUrl, statusCode, statusString);
    }

    public ResponseStatusObject(String id, String requestUrl, String statusCode, String statusString) {
        this.id = id;
        this.requestUrl = requestUrl;
        this.statusCode = statusCode;
        this.statusString = statusString;
        this.localTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
    }

}
