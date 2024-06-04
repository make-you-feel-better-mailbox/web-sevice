package com.onetwo.webservice.dto.user;

public record UserDetailResponse(String userId,
                                 String nickname,
                                 String email,
                                 String phoneNumber,
                                 String profileImageEndPoint,
                                 boolean oauth,
                                 String registrationId,
                                 boolean state) {
}
