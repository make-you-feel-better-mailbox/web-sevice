package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.user.RegisterUserRequest;
import com.onetwo.webservice.dto.user.UserRegisterResponse;
import com.onetwo.webservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
        UserRegisterResponse userRegisterResponse = userService.userRegister(registerUserRequest);
        if (userRegisterResponse.isRegisterSuccess())
            return ResponseEntity.status(HttpStatus.CREATED).body(userRegisterResponse);
        else return ResponseEntity.ok().body(userRegisterResponse);
    }
}
