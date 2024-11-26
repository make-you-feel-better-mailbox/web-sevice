package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.follow.*;
import com.onetwo.webservice.service.FollowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    @GetMapping(GlobalURI.FOLLOW_COUNT + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE)
    public ResponseEntity<CountFollowResponse> getFollowCount(@PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) String userId){
        return followerService.getFollowCount(userId);
    }

    @GetMapping(GlobalURI.FOLLOW_ROOT + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE + GlobalURI.PATH_VARIABLE_ACCESS_TOKEN_WITH_BRACE)
    public ResponseEntity<FollowTargetCheckResponse> userFollowTargetUserCheck(@PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) String userId,
                                                                               @PathVariable(GlobalStatus.ACCESS_TOKEN) String accessToken){
        return followerService.userFollowTargetUserCheck(userId, accessToken);
    }

    @PostMapping(GlobalURI.FOLLOW_ROOT)
    public ResponseEntity<RegisterFollowResponse> registerFollow(@RequestBody @Valid RegisterFollowRequestDto registerFollowRequestDto){
        return followerService.registerFollow(registerFollowRequestDto);
    }

    @DeleteMapping(GlobalURI.FOLLOW_ROOT + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE)
    public ResponseEntity<DeleteFollowResponse> deleteFollow(@PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) String userId,
                                                             @RequestBody @Valid AccessTokenDto accessTokenDto){
        return followerService.deleteFollow(userId, accessTokenDto);
    }
}
