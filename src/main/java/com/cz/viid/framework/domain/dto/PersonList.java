package com.cz.viid.framework.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonList {

    @JsonProperty("PersonObject")
    private List<Person> PersonObject;
}
