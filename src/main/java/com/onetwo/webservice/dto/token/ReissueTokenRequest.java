package com.onetwo.webservice.dto.token;

import jakarta.validation.constraints.NotEmpty;

public record ReissueTokenRequest(@NotEmpty String accessToken,
                                  @NotEmpty String refreshToken) {
}
