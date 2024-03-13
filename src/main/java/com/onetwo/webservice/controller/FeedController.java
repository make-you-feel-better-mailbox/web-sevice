package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.user.UserInfoResponse;
import com.onetwo.webservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FeedController {

    private final UserService userService;

    @GetMapping(GlobalURI.FEED_ROOT)
    public ModelAndView getView() {
        return new ModelAndView("main/feed");
    }

    @GetMapping(GlobalURI.FEED_DETAIL + GlobalURI.PATH_VARIABLE_WITH_USER_ID)
    public ModelAndView getDetailView(@PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) String userId) {
        ModelAndView modelAndView = new ModelAndView("main/feed-detail");

        UserInfoResponse userInfoResponse = userService.getUserInfo(userId);

        modelAndView.addObject("userInfo", userInfoResponse);

        return modelAndView;
    }
}
