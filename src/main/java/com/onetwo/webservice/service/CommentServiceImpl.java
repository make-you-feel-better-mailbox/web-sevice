package com.onetwo.webservice.service;

import com.onetwo.webservice.common.PropertiesInfo;
import com.onetwo.webservice.common.uri.CommentServiceURI;
import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.comment.*;
import com.onetwo.webservice.utils.SenderUtils;
import com.onetwo.webservice.utils.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final SenderUtils senderUtils;
    private final PropertiesInfo propertiesInfo;

    @Override
    public ResponseEntity<CommentCountResponse> getCommentCount(Integer category, Long targetId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += CommentServiceURI.COMMENT_COUNT;

        requestUri += "/" + category + "/" + targetId;

        ResponseEntity<CommentCountResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<CommentCountResponse>() {
                        });

        return response;
    }

    @Override
    public Slice<FilteredCommentResponse> getCommentList(CommentFilterSliceRequest commentFilterSliceRequest) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += CommentServiceURI.COMMENT_FILTER;

        requestUri += UriBuilder.getQueryStringUri(commentFilterSliceRequest);

        ResponseEntity<Slice<FilteredCommentResponse>> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<Slice<FilteredCommentResponse>>() {
                        });

        return response.getBody();
    }

    @Override
    public ResponseEntity<RegisterCommentResponse> registerComment(RegisterCommentRequestDto registerCommentRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += CommentServiceURI.COMMENT_ROOT;

        RegisterCommentRequest registerLikeRequest = new RegisterCommentRequest(
                registerCommentRequestDto.getCategory(),
                registerCommentRequestDto.getTargetId(),
                registerCommentRequestDto.getContent());

        ResponseEntity<RegisterCommentResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        senderUtils.getAccessTokenHeader(registerCommentRequestDto),
                        registerLikeRequest,
                        new ParameterizedTypeReference<RegisterCommentResponse>() {
                        });

        return response;
    }
}
