package com.onetwo.webservice.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserPasswordRequest(@NotEmpty String currentPassword,
                                        @NotEmpty String newPassword,
                                        @NotEmpty String newPasswordCheck) {
}
