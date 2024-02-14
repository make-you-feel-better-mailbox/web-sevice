package com.onetwo.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedController {

    @GetMapping("/feed")
    public ModelAndView getView() {
        ModelAndView viewModel = new ModelAndView("main/feed");

        return viewModel;
    }
}
