package com.onetwo.webservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class AccessTokenDto {

    @NotEmpty
    private String accessToken;
}
