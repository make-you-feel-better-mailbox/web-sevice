package com.onetwo.webservice.service;

import com.onetwo.webservice.common.PropertiesInfo;
import com.onetwo.webservice.common.uri.LikeServiceURI;
import com.onetwo.webservice.dto.like.*;
import com.onetwo.webservice.utils.SenderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final SenderUtils senderUtils;
    private final PropertiesInfo propertiesInfo;

    @Override
    public ResponseEntity<CountLikeResponse> getLikeCount(Integer category, Long targetId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += LikeServiceURI.LIKE_COUNT;

        requestUri += "/" + category + "/" + targetId;

        ResponseEntity<CountLikeResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<CountLikeResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<LikeTargetCheckResponse> userLikeTargetCheck(LikeTargetCheckRequest likeTargetCheckRequest) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += LikeServiceURI.LIKE_ROOT;

        requestUri += "/" + likeTargetCheckRequest.getCategory() + "/" + likeTargetCheckRequest.getTargetId();

        ResponseEntity<LikeTargetCheckResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        senderUtils.getAccessTokenHeader(likeTargetCheckRequest),
                        null,
                        new ParameterizedTypeReference<LikeTargetCheckResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<RegisterLikeResponse> registerLike(RegisterLikeRequestDto registerLikeRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += LikeServiceURI.LIKE_ROOT;

        RegisterLikeRequest registerLikeRequest = new RegisterLikeRequest(registerLikeRequestDto.getCategory(), registerLikeRequestDto.getTargetId());

        ResponseEntity<RegisterLikeResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        senderUtils.getAccessTokenHeader(registerLikeRequestDto),
                        registerLikeRequest,
                        new ParameterizedTypeReference<RegisterLikeResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<DeleteLikeResponse> deleteLike(DeleteLikeRequestDto deleteLikeRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += LikeServiceURI.LIKE_ROOT;

        requestUri += "/" + deleteLikeRequestDto.getCategory() + "/" + deleteLikeRequestDto.getTargetId();

        ResponseEntity<DeleteLikeResponse> response =
                senderUtils.send(
                        HttpMethod.DELETE,
                        requestUri,
                        senderUtils.getAccessTokenHeader(deleteLikeRequestDto),
                        null,
                        new ParameterizedTypeReference<DeleteLikeResponse>() {
                        });

        return response;
    }
}
