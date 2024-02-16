package com.onetwo.webservice.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty String userId,
                                  @NotEmpty String password,
                                  @NotEmpty String nickname,
                                  @NotEmpty String email,
                                  String phoneNumber) {
}
