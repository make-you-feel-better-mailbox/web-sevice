package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.follow.CountFollowResponse;
import org.springframework.http.ResponseEntity;

public interface FollowerService {
    ResponseEntity<CountFollowResponse> getFollowCount(String userId);
}
