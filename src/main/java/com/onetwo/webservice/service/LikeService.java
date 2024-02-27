package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.like.*;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<CountLikeResponse> getLikeCount(Integer category, Long targetId);

    ResponseEntity<LikeTargetCheckResponse> userLikeTargetCheck(LikeTargetCheckRequest likeTargetCheckRequest);

    ResponseEntity<RegisterLikeResponse> registerLike(RegisterLikeRequestDto registerLikeRequestDto);

    ResponseEntity<DeleteLikeResponse> deleteLike(DeleteLikeRequestDto deleteLikeRequestDto);
}
