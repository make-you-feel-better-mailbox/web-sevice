package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.comment.*;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CommentCountResponse> getCommentCount(Integer category, Long targetId);

    Slice<FilteredCommentResponse> getCommentList(CommentFilterSliceRequest commentFilterSliceRequest);

    ResponseEntity<RegisterCommentResponse> registerComment(RegisterCommentRequestDto registerCommentRequestDto);

    ResponseEntity<DeleteCommentResponse> deleteComment(Long commentId, AccessTokenDto accessTokenDto);

    ResponseEntity<UpdateCommentResponse> updateComment(Long commentId, UpdateCommentRequestDto updateCommentRequestDto);

    ResponseEntity<CommentDetailResponse> findCommentsDetail(Long commentId);
}
