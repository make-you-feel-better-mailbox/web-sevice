package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.follow.CountFollowResponse;
import com.onetwo.webservice.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    @GetMapping(GlobalURI.FOLLOW_COUNT + GlobalURI.PATH_VARIABLE_WITH_USER_ID)
    public ResponseEntity<CountFollowResponse> getFollowCount(@PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) String userId){
        return followerService.getFollowCount(userId);
    }
}
