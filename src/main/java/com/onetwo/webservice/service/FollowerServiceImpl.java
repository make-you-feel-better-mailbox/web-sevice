package com.onetwo.webservice.service;

import com.onetwo.webservice.common.properties.PropertiesInfo;
import com.onetwo.webservice.common.uri.FollowServiceURI;
import com.onetwo.webservice.dto.follow.CountFollowResponse;
import com.onetwo.webservice.utils.SenderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
