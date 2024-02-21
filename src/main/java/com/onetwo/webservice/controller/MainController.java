package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(GlobalURI.ROOT_URI)
    public String rootRedirect() {
        return GlobalURI.REDIRECT + GlobalURI.FEED_ROOT;
    }
}
