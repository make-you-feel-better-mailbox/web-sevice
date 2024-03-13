package com.onetwo.webservice.service;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.properties.PropertiesInfo;
import com.onetwo.webservice.common.uri.UserServiceURI;
import com.onetwo.webservice.dto.token.TokenResponse;
import com.onetwo.webservice.dto.user.AuthorizedURIResponse;
import com.onetwo.webservice.dto.user.LoginUserRequest;
import com.onetwo.webservice.dto.user.LogoutResponse;
import com.onetwo.webservice.dto.user.OAuthLoginRequest;
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
public class LoginServiceImpl implements LoginService {

    private final PropertiesInfo propertiesInfo;
    private final SenderUtils senderUtils;

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
    public ResponseEntity<LogoutResponse> logoutUser(String accessToken) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.USER_LOGIN;

        Map<String, String> headers = new HashMap<>();

        headers.put(GlobalStatus.ACCESS_TOKEN, accessToken);

        ResponseEntity<LogoutResponse> response =
                senderUtils.send(
                        HttpMethod.DELETE,
                        requestUri,
                        headers,
                        null,
                        new ParameterizedTypeReference<LogoutResponse>() {
                        });

        return response;
    }

    @Override
    public ResponseEntity<AuthorizedURIResponse> getSocialLoginUri(String registrationId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.OAUTH_ROOT;

        requestUri += "/" + registrationId;

        ResponseEntity<AuthorizedURIResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        null,
                        null,
                        new ParameterizedTypeReference<AuthorizedURIResponse>() {
                        });

        return response;
    }

    @Override
    public TokenResponse socialLogin(String code, String registrationId) {
        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceURI.OAUTH_ROOT;

        OAuthLoginRequest oAuthLoginRequest = new OAuthLoginRequest(code, registrationId);

        ResponseEntity<TokenResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        null,
                        oAuthLoginRequest,
                        new ParameterizedTypeReference<TokenResponse>() {
                        });

        return response.getBody();
    }
}
