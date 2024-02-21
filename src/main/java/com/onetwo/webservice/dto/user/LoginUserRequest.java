package com.onetwo.webservice.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(@NotEmpty String userId,
                               @NotEmpty String password) {
}
