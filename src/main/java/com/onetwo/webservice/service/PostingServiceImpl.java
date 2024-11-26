package com.onetwo.webservice.service;

import com.onetwo.webservice.common.properties.PropertiesInfo;
import com.onetwo.webservice.common.uri.PostingServiceURI;
import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.dto.Slice;
import com.onetwo.webservice.dto.posting.*;
import com.onetwo.webservice.utils.SenderUtils;
import com.onetwo.webservice.utils.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService {

    private final SenderUtils senderUtils;
    private final PropertiesInfo propertiesInfo;

    @Override
    public ResponseEntity<PostPostingResponse> postPosting(PostPostingRequestDto postPostingRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += PostingServiceURI.POSTING_ROOT;

        PostPostingRequest postPostingRequest = new PostPostingRequest(postPostingRequestDto.getContent(), postPostingRequestDto.getMediaExist());

        ResponseEntity<PostPostingResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        senderUtils.getAccessTokenHeader(postPostingRequestDto),
                        postPostingRequest,
                        new ParameterizedTypeReference<PostPostingResponse>() {
                        });

        return response;
    }

    @Override
    public Slice<FilteredPostingResponse> postingFilter(PostingFilterSliceRequest filterSliceRequest) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += PostingServiceURI.POSTING_FILTER;

        requestUri += UriBuilder.getQueryStringUri(filterSliceRequest);

        ResponseEntity<Slice<FilteredPostingResponse>> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<Slice<FilteredPostingResponse>>() {
                        });

        return response.getBody();
    }

    @Override
    public ResponseEntity<UpdatePostingResponse> updatePosting(Long postingId, UpdatePostingRequestDto updatePostingRequestDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += PostingServiceURI.POSTING_ROOT;

        requestUri += "/" + postingId;

        UpdatePostingRequest updatePostingRequest = new UpdatePostingRequest(updatePostingRequestDto.getContent(), updatePostingRequestDto.getMediaExist());

        ResponseEntity<UpdatePostingResponse> response =
                senderUtils.send(
                        HttpMethod.PUT,
                        requestUri,
                        senderUtils.getAccessTokenHeader(updatePostingRequestDto),
                        updatePostingRequest,
                        new ParameterizedTypeReference<UpdatePostingResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<DeletePostingResponse> deletePosting(Long postingId, AccessTokenDto accessTokenDto) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += PostingServiceURI.POSTING_ROOT;

        requestUri += "/" + postingId;

        ResponseEntity<DeletePostingResponse> response =
                senderUtils.send(
                        HttpMethod.DELETE,
                        requestUri,
                        senderUtils.getAccessTokenHeader(accessTokenDto),
                        null,
                        new ParameterizedTypeReference<DeletePostingResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<PostingDetailResponse> findPostingDetail(Long postingId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += PostingServiceURI.POSTING_ROOT;

        requestUri += "/" + postingId;

        ResponseEntity<PostingDetailResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<PostingDetailResponse>() {
                        });

        return response;
    }
}
