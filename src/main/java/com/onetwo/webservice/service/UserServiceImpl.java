package com.onetwo.webservice.service;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.PropertiesInfo;
import com.onetwo.webservice.common.uri.UserServiceURI;
import com.onetwo.webservice.dto.token.ReissueTokenRequest;
import com.onetwo.webservice.dto.token.ReissuedTokenDto;
import com.onetwo.webservice.dto.token.TokenResponse;
import com.onetwo.webservice.dto.user.*;
import com.onetwo.webservice.utils.SenderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SenderUtils senderUtils;
    private final PropertiesInfo propertiesInfo;

    @Override
    public ResponseEntity<UserRegisterResponse> userRegister(RegisterUserRequest registerUserRequest) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.USER_ROOT;

        ResponseEntity<RegisterUserResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        null,
                        registerUserRequest,
                        new ParameterizedTypeReference<RegisterUserResponse>() {
                        });

        boolean isRegisterSuccess =
                response != null
                        && response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)
                        && response.getBody() != null
                        && StringUtils.hasText(response.getBody().userId());

        return ResponseEntity.status(response.getStatusCode()).body(new UserRegisterResponse(isRegisterSuccess));
    }

    @Override
    public ResponseEntity<TokenResponse> loginUser(LoginUserRequest loginUserRequest) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.USER_LOGIN;

        ResponseEntity<TokenResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        null,
                        loginUserRequest,
                        new ParameterizedTypeReference<TokenResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<UserIdExistCheckDto> userIdExistCheck(String userId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.USER_ID;

        requestUri += "/" + userId;

        ResponseEntity<UserIdExistCheckDto> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<UserIdExistCheckDto>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<UserDetailResponse> getUserDetailInfo(String accessToken) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.USER_ROOT;

        Map<String, String> headers = new HashMap<>();

        headers.put(GlobalStatus.ACCESS_TOKEN, accessToken);

        ResponseEntity<UserDetailResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        headers,
                        null,
                        new ParameterizedTypeReference<UserDetailResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<ReissuedTokenDto> reissueAccessTokenByRefreshToken(ReissueTokenRequest reissueTokenRequest) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.TOKEN_REFRESH;

        ResponseEntity<ReissuedTokenDto> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        null,
                        reissueTokenRequest,
                        new ParameterizedTypeReference<ReissuedTokenDto>() {
                        });

        return response;
    }
}
