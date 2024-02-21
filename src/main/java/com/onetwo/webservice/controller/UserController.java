package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.token.TokenResponse;
import com.onetwo.webservice.dto.user.LoginUserRequest;
import com.onetwo.webservice.dto.user.RegisterUserRequest;
import com.onetwo.webservice.dto.user.UserIdExistCheckDto;
import com.onetwo.webservice.dto.user.UserRegisterResponse;
import com.onetwo.webservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GlobalURI.LOGIN_ROOT)
    public ModelAndView getLoginView() {
        return new ModelAndView("main/login");
    }

    @GetMapping(GlobalURI.REGISTER_ROOT)
    public ModelAndView getRegisterView() {
        return new ModelAndView("main/register");
    }

    @PostMapping(GlobalURI.REGISTER_ROOT)
    @ResponseBody
    public ResponseEntity<UserRegisterResponse> userRegister(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return userService.userRegister(registerUserRequest);
    }

    @PostMapping(GlobalURI.LOGIN_ROOT)
    @ResponseBody
    public ResponseEntity<TokenResponse> loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        return userService.loginUser(loginUserRequest);
    }

    @GetMapping(GlobalURI.USER_ID + "/{user-id}")
    @ResponseBody
    public ResponseEntity<UserIdExistCheckDto> userIdExistCheck(@PathVariable("user-id") String userId) {
        return userService.userIdExistCheck(userId);
    }
}
