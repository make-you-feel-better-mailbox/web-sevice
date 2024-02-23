package com.onetwo.webservice.service;

import com.onetwo.webservice.common.PostingServiceURI;
import com.onetwo.webservice.common.PropertiesInfo;
import com.onetwo.webservice.dto.posting.PostPostingRequest;
import com.onetwo.webservice.dto.posting.PostPostingRequestDto;
import com.onetwo.webservice.dto.posting.PostPostingResponse;
import com.onetwo.webservice.utils.SenderUtils;
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
}
