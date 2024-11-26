package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalURI;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class BaseErrorController implements ErrorController {

    @GetMapping(GlobalURI.ERROR_URI_ROOT)
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.debug("================> error status  : [" + status + "] request url : [" + request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI) + "]");
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return GlobalURI.REDIRECT + GlobalURI.ERROR_URI_404;
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return GlobalURI.REDIRECT + GlobalURI.ERROR_URI_403;
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return GlobalURI.REDIRECT + GlobalURI.ERROR_URI_500;
            }
        }

        return "redirect:" + GlobalURI.ERROR_URI_ETC;
    }

    @GetMapping(GlobalURI.ERROR_URI_403)
    public ModelAndView error403() {
        return new ModelAndView("error/403");
    }

    @GetMapping(GlobalURI.ERROR_URI_404)
    public ModelAndView error404() {
        return new ModelAndView("error/404");
    }

    @GetMapping(GlobalURI.ERROR_URI_500)
    public ModelAndView error500() {
        return new ModelAndView("error/500");
    }

    @GetMapping(GlobalURI.ERROR_URI_ETC)
    public ModelAndView errorEtc() {
        return new ModelAndView("error/error");
    }
}
