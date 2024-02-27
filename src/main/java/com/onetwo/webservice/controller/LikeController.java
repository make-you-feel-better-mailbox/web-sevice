package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.like.*;
import com.onetwo.webservice.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping(GlobalURI.LIKE_ROOT + GlobalURI.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalURI.PATH_VARIABLE_TARGET_ID_WITH_BRACE )
    public ResponseEntity<CountLikeResponse> getLikeCount(@PathVariable(GlobalURI.PATH_VARIABLE_CATEGORY) Integer category,
                                                          @PathVariable(GlobalURI.PATH_VARIABLE_TARGET_ID) Long targetId){
        return likeService.getLikeCount(category, targetId);
    }

    @GetMapping(GlobalURI.LIKE_ROOT)
    public ResponseEntity<LikeTargetCheckResponse> userLikeTargetCheck(@ModelAttribute @Valid LikeTargetCheckRequest likeTargetCheckRequest){
        return likeService.userLikeTargetCheck(likeTargetCheckRequest);
    }

    @PostMapping(GlobalURI.LIKE_ROOT)
    public ResponseEntity<RegisterLikeResponse> registerLike(@RequestBody @Valid RegisterLikeRequestDto registerLikeRequestDto){
        return likeService.registerLike(registerLikeRequestDto);
    }

    @DeleteMapping(GlobalURI.LIKE_ROOT)
    public ResponseEntity<DeleteLikeResponse> deleteLike(@RequestBody @Valid DeleteLikeRequestDto deleteLikeRequestDto){
        return likeService.deleteLike(deleteLikeRequestDto);
    }
}
