package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.token.ReissueTokenRequest;
import com.onetwo.webservice.dto.token.ReissuedTokenDto;
import com.onetwo.webservice.dto.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserRegisterResponse> userRegister(RegisterUserRequest registerUserRequest);

    ResponseEntity<UserIdExistCheckDto> userIdExistCheck(String userId);

    ResponseEntity<UserDetailResponse> getUserDetailInfo(String accessToken);

    ResponseEntity<ReissuedTokenDto> reissueAccessTokenByRefreshToken(ReissueTokenRequest reissueTokenRequest);

    ResponseEntity<UpdateUserResponse> updateUser(UpdateUserRequestDto updateUserRequestDto);

    ResponseEntity<UpdateUserPasswordResponse> updatePassword(UpdateUserPasswordRequestDto updateUserPasswordRequestDto);

    UserInfoResponse getUserInfo(String userId);
}
