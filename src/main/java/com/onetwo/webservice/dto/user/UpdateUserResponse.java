package com.onetwo.webservice.dto.user;

public record UpdateUserResponse(String userId,
                                 String nickname,
                                 String email,
                                 String phoneNumber,
                                 boolean state) {
}
