package com.onetwo.webservice.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserRequest(
        @NotEmpty String nickname,
        @NotEmpty String email,
        String phoneNumber) {
}
