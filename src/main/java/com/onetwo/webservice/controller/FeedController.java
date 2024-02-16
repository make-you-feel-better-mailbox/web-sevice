package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedController {

    @GetMapping(GlobalURI.FEED_ROOT)
    public ModelAndView getView() {
        return new ModelAndView("main/feed");
    }
}
