package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.posting.*;
import org.springframework.http.ResponseEntity;

public interface PostingService {
    ResponseEntity<PostPostingResponse> postPosting(PostPostingRequestDto postPostingRequestDto);

    Slice<FilteredPostingResponse> postingFilter(PostingFilterSliceRequest filterSliceRequest);

    ResponseEntity<UpdatePostingResponse> updatePosting(Long postingId, UpdatePostingRequestDto updatePostingRequestDto);

    ResponseEntity<DeletePostingResponse> deletePosting(Long postingId, AccessTokenDto accessTokenDto);

    ResponseEntity<PostingDetailResponse> findPostingDetail(Long postingId);
}
