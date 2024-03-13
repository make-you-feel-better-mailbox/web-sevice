package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.token.TokenResponse;
import com.onetwo.webservice.dto.user.AuthorizedURIResponse;
import com.onetwo.webservice.dto.user.LoginUserRequest;
import com.onetwo.webservice.dto.user.LogoutResponse;
import com.onetwo.webservice.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(GlobalURI.LOGIN_ROOT)
    @ResponseBody
    public ResponseEntity<TokenResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        return loginService.loginUser(loginUserRequest);
    }

    @DeleteMapping(GlobalURI.LOGIN_ROOT + GlobalURI.PATH_VARIABLE_ACCESS_TOKEN_WITH_BRACE)
    @ResponseBody
    public ResponseEntity<LogoutResponse> logoutUser(@PathVariable(GlobalStatus.ACCESS_TOKEN) String accessToken) {
        return loginService.logoutUser(accessToken);
    }

    @GetMapping(GlobalURI.OAUTH_ROOT + "/{registrationId}")
    @ResponseBody
    public ResponseEntity<AuthorizedURIResponse> getSocialLoginUri(@PathVariable String registrationId){
        return loginService.getSocialLoginUri(registrationId);
    }

    @GetMapping(GlobalURI.GOOGLE_LOGIN + "/{registrationId}")
    public ModelAndView socialLogin(@RequestParam String code, @PathVariable String registrationId){
        ModelAndView modelAndView = new ModelAndView("main/feed");

        TokenResponse socialLogin = loginService.socialLogin(code, registrationId);

        modelAndView.addObject("accessToken", socialLogin.accessToken());
        modelAndView.addObject("refreshToken", socialLogin.refreshToken());

        return modelAndView;
    }
}
