package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.user.RegisterUserRequest;
import com.onetwo.webservice.dto.user.UserRegisterResponse;

public interface UserService {
    UserRegisterResponse userRegister(RegisterUserRequest registerUserRequest);
}
