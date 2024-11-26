package com.onetwo.webservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class Slice<T> {

    @JsonProperty("content")
    private List<T> content;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("last")
    private Boolean last;
}
