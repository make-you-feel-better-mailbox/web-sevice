package com.onetwo.webservice.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OAuthLoginRequest(@NotNull String code,
                                @NotEmpty String registrationId) {
}
