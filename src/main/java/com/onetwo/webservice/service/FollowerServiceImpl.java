package com.onetwo.webservice.service;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.properties.PropertiesInfo;
import com.onetwo.webservice.common.uri.FollowServiceURI;
import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.follow.*;
import com.onetwo.webservice.utils.SenderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService{

    private final SenderUtils senderUtils;
    private final PropertiesInfo propertiesInfo;

    @Override
    public ResponseEntity<CountFollowResponse> getFollowCount(String userId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += FollowServiceURI.FOLLOW_COUNT;

        requestUri += "/" + userId;

        ResponseEntity<CountFollowResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<CountFollowResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<FollowTargetCheckResponse> userFollowTargetUserCheck(String userId, String accessToken) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += FollowServiceURI.FOLLOW_ROOT + "/" + userId;

        Map<String, String> headers = new HashMap<>();

        headers.put(GlobalStatus.ACCESS_TOKEN, accessToken);

        ResponseEntity<FollowTargetCheckResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        headers,
                        null,
                        new ParameterizedTypeReference<FollowTargetCheckResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<RegisterFollowResponse> registerFollow(RegisterFollowRequestDto registerFollowRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += FollowServiceURI.FOLLOW_ROOT;

        RegisterFollowRequest registerFollowRequest = new RegisterFollowRequest(registerFollowRequestDto.getTargetUserId());

        ResponseEntity<RegisterFollowResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        senderUtils.getAccessTokenHeader(registerFollowRequestDto),
                        registerFollowRequest,
                        new ParameterizedTypeReference<RegisterFollowResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<DeleteFollowResponse> deleteFollow(String userId, AccessTokenDto accessTokenDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += FollowServiceURI.FOLLOW_ROOT + "/" + userId;

        ResponseEntity<DeleteFollowResponse> response =
                senderUtils.send(
                        HttpMethod.DELETE,
                        requestUri,
                        senderUtils.getAccessTokenHeader(accessTokenDto),
                        null,
                        new ParameterizedTypeReference<DeleteFollowResponse>() {
                        });

        return response;
    }
}
