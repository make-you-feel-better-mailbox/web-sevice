package com.onetwo.webservice.dto.comment;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CommentFilterSliceRequest(@NotNull Integer category,
                                        @NotNull Long targetId,
                                        String userId,
                                        String content,
                                        Instant filterStartDate,
                                        Instant filterEndDate,
                                        Integer pageNumber,
                                        Integer pageSize,
                                        String sort) {
}
