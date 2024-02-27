package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.posting.FilteredPostingResponse;
import com.onetwo.webservice.dto.posting.PostPostingRequestDto;
import com.onetwo.webservice.dto.posting.PostPostingResponse;
import com.onetwo.webservice.dto.posting.PostingFilterSliceRequest;
import org.springframework.http.ResponseEntity;

public interface PostingService {
    ResponseEntity<PostPostingResponse> postPosting(PostPostingRequestDto postPostingRequestDto);

    Slice<FilteredPostingResponse> postingFilter(PostingFilterSliceRequest filterSliceRequest);
}
