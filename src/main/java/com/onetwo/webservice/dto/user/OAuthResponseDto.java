package com.onetwo.webservice.dto.user;

import java.math.BigInteger;

public record OAuthResponseDto(BigInteger id,
                               String email,
                               String name) {
}
