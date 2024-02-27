package com.onetwo.webservice.dto.like;

import jakarta.validation.constraints.NotNull;

public record DeleteLikeRequest(@NotNull Integer category,
                                @NotNull Long targetId) {
}
