package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.token.TokenResponse;
import com.onetwo.webservice.dto.user.LoginUserRequest;
import com.onetwo.webservice.dto.user.RegisterUserRequest;
import com.onetwo.webservice.dto.user.UserIdExistCheckDto;
import com.onetwo.webservice.dto.user.UserRegisterResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserRegisterResponse> userRegister(RegisterUserRequest registerUserRequest);

    ResponseEntity<TokenResponse> loginUser(LoginUserRequest loginUserRequest);

    ResponseEntity<UserIdExistCheckDto> userIdExistCheck(String userId);
}
