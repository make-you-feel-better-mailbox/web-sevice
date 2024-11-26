package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.follow.*;
import org.springframework.http.ResponseEntity;

public interface FollowerService {
    ResponseEntity<CountFollowResponse> getFollowCount(String userId);

    ResponseEntity<FollowTargetCheckResponse> userFollowTargetUserCheck(String userId, String accessToken);

    ResponseEntity<RegisterFollowResponse> registerFollow(RegisterFollowRequestDto registerFollowRequestDto);

    ResponseEntity<DeleteFollowResponse> deleteFollow(String userId, AccessTokenDto accessTokenDto);
}
