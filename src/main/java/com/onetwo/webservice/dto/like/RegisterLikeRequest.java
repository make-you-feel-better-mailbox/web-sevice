package com.onetwo.webservice.dto.like;

import jakarta.validation.constraints.NotNull;

public record RegisterLikeRequest(@NotNull Integer category,
                                  @NotNull Long targetId) {
}
