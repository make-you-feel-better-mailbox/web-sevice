package com.onetwo.webservice.service;

import com.onetwo.webservice.common.PropertiesInfo;
import com.onetwo.webservice.common.UserServiceURI;
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
}
