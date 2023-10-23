package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OperationLog {

    private Long id;
    private String pointer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date timestamp;
    private String method;
    private String resource;
    private String client;
    private String response;
    private String exception;
    private String userId;
}
