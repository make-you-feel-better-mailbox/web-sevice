package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.common.uri.PostingServiceURI;
import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.posting.*;
import com.onetwo.webservice.service.PostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @PostMapping(GlobalURI.POSTING_ROOT)
    public ResponseEntity<PostPostingResponse> postPosting(@RequestBody @Valid PostPostingRequestDto postPostingRequestDto) {
        return postingService.postPosting(postPostingRequestDto);
    }

    @GetMapping(GlobalURI.POSTING_FILTER)
    public Slice<FilteredPostingResponse> postingFilter(@ModelAttribute PostingFilterSliceRequest filterSliceRequest) {
        return postingService.postingFilter(filterSliceRequest);
    }

    @PutMapping(GlobalURI.POSTING_ROOT + PostingServiceURI.PATH_VARIABLE_POSTING_ID_WITH_BRACE)
    public ResponseEntity<UpdatePostingResponse> updatePosting(@PathVariable(PostingServiceURI.PATH_VARIABLE_POSTING_ID) Long postingId,
                                                               @RequestBody @Valid UpdatePostingRequestDto updatePostingRequestDto){
        return postingService.updatePosting(postingId, updatePostingRequestDto);
    }

    @DeleteMapping(GlobalURI.POSTING_ROOT + PostingServiceURI.PATH_VARIABLE_POSTING_ID_WITH_BRACE)
    public ResponseEntity<DeletePostingResponse> deletePosting(@PathVariable(PostingServiceURI.PATH_VARIABLE_POSTING_ID) Long postingId,
                                                               @RequestBody @Valid AccessTokenDto accessTokenDto){
        return postingService.deletePosting(postingId, accessTokenDto);
    }

    @GetMapping(GlobalURI.POSTING_ROOT + PostingServiceURI.PATH_VARIABLE_POSTING_ID_WITH_BRACE)
    public ResponseEntity<PostingDetailResponse> findPostingDetail(@PathVariable(PostingServiceURI.PATH_VARIABLE_POSTING_ID) Long postingId){
        return postingService.findPostingDetail(postingId);
    }
}
