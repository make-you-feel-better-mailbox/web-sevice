package com.onetwo.webservice.dto.posting;

public record PostingDetailResponse(long postingId, String userId, String content, boolean mediaExist, String postedDate) {
}
