package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.posting.PostPostingRequestDto;
import com.onetwo.webservice.dto.posting.PostPostingResponse;
import com.onetwo.webservice.service.PostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @PostMapping(GlobalURI.POSTING_ROOT)
    public ResponseEntity<PostPostingResponse> postPosting(@RequestBody @Valid PostPostingRequestDto postPostingRequestDto) {
        return postingService.postPosting(postPostingRequestDto);
    }
}
