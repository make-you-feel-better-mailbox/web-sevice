package com.onetwo.webservice.service;

import com.onetwo.webservice.common.PropertiesInfo;
import com.onetwo.webservice.common.uri.CommentServiceURI;
import com.onetwo.webservice.dto.AccessTokenDto;
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

    @Override
    public ResponseEntity<DeleteCommentResponse> deleteComment(Long commentId, AccessTokenDto accessTokenDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += CommentServiceURI.COMMENT_ROOT;

        requestUri += "/" + commentId;

        ResponseEntity<DeleteCommentResponse> response =
                senderUtils.send(
                        HttpMethod.DELETE,
                        requestUri,
                        senderUtils.getAccessTokenHeader(accessTokenDto),
                        null,
                        new ParameterizedTypeReference<DeleteCommentResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<UpdateCommentResponse> updateComment(Long commentId, UpdateCommentRequestDto updateCommentRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += CommentServiceURI.COMMENT_ROOT;

        requestUri += "/" + commentId;

        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(updateCommentRequestDto.getContent());

        ResponseEntity<UpdateCommentResponse> response =
                senderUtils.send(
                        HttpMethod.PUT,
                        requestUri,
                        senderUtils.getAccessTokenHeader(updateCommentRequestDto),
                        updateCommentRequest,
                        new ParameterizedTypeReference<UpdateCommentResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<CommentDetailResponse> findCommentsDetail(Long commentId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += CommentServiceURI.COMMENT_ROOT;

        requestUri += "/" + commentId;

        ResponseEntity<CommentDetailResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<CommentDetailResponse>() {
                        });

        return response;
    }
}
