package com.onetwo.webservice.dto.user;

public record UserInfoResponse(String userId,
                               String nickname,
                               String email,
                               String phoneNumber,
                               boolean oauth,
                               String registrationId) {
}
