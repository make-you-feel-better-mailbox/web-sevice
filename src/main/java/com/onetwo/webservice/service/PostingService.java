package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.posting.PostPostingRequestDto;
import com.onetwo.webservice.dto.posting.PostPostingResponse;
import org.springframework.http.ResponseEntity;

public interface PostingService {
    ResponseEntity<PostPostingResponse> postPosting(PostPostingRequestDto postPostingRequestDto);
}
