package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyAccountController {

    @GetMapping(GlobalURI.MY_ACCOUNT_URI)
    public ModelAndView getView(){
        return new ModelAndView("main/setting");
    }
}
