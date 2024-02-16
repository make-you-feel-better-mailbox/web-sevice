package com.onetwo.webservice.service;

import com.onetwo.webservice.common.UserServiceUri;
import com.onetwo.webservice.dto.user.RegisterUserRequest;
import com.onetwo.webservice.dto.user.RegisterUserResponse;
import com.onetwo.webservice.dto.user.UserRegisterResponse;
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
//    private final PropertiesInfo propertiesInfo;

    @Override
    public UserRegisterResponse userRegister(RegisterUserRequest registerUserRequest) {
        String requestUri = "http://localhost:8000";

//        String requestUri = propertiesInfo.getApiGateway().getHost();

        requestUri += UserServiceUri.USER_ROOT;

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

        return new UserRegisterResponse(isRegisterSuccess);
    }
}
