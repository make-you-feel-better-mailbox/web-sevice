package com.onetwo.webservice.dto.posting;

public record PostingDetailResponse(long postingId, String userId,String userNickname, String content, boolean mediaExist, String postedDate) {
}
