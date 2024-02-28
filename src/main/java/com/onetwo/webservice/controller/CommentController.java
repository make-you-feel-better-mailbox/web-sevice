package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.comment.*;
import com.onetwo.webservice.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(GlobalURI.COMMENT_COUNT + GlobalURI.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalURI.PATH_VARIABLE_TARGET_ID_WITH_BRACE)
    public ResponseEntity<CommentCountResponse> getCommentCount(@PathVariable(GlobalURI.PATH_VARIABLE_CATEGORY) Integer category,
                                                                @PathVariable(GlobalURI.PATH_VARIABLE_TARGET_ID) Long targetId){
        return commentService.getCommentCount(category, targetId);
    }

    @GetMapping(GlobalURI.COMMENT_FILTER)
    public Slice<FilteredCommentResponse> getCommentList(@ModelAttribute CommentFilterSliceRequest commentFilterSliceRequest){
        return commentService.getCommentList(commentFilterSliceRequest);
    }

    @PostMapping(GlobalURI.COMMENT_ROOT)
    public ResponseEntity<RegisterCommentResponse> registerComment(@RequestBody @Valid RegisterCommentRequestDto registerCommentRequestDto){
        return commentService.registerComment(registerCommentRequestDto);
    }
}
