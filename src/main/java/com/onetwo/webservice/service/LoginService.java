package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.token.TokenResponse;
import com.onetwo.webservice.dto.user.AuthorizedURIResponse;
import com.onetwo.webservice.dto.user.LoginUserRequest;
import com.onetwo.webservice.dto.user.LogoutResponse;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<TokenResponse> loginUser(LoginUserRequest loginUserRequest);

    ResponseEntity<LogoutResponse> logoutUser(String accessToken);

    ResponseEntity<AuthorizedURIResponse> getSocialLoginUri(String registrationId);

    TokenResponse socialLogin(String code, String registrationId);
}
