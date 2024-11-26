package com.onetwo.webservice.dto.posting;

import java.time.Instant;

public record PostingFilterSliceRequest(String userId,
                                        String content,
                                        Instant filterStartDate,
                                        Instant filterEndDate,
                                        Integer pageNumber,
                                        Integer pageSize,
                                        String sort) {
}
