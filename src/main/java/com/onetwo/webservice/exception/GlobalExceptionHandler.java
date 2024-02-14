package com.onetwo.webservice.exception;

import com.onetwo.webservice.common.GlobalURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String ERROR_REDIRECT = GlobalURI.REDIRECT + GlobalURI.ERROR_URI_ROOT;

    @ExceptionHandler(Exception.class)
    public ModelAndView rootExceptionHandler(Exception e) {
        log.error("exception", e);
        return new ModelAndView("main/error");
    }

    @ExceptionHandler(NotExpectResultException.class)
    public String notExpectResultException(Exception e) {
        log.error("notExpectResultException", e);
        return ERROR_REDIRECT;
    }
}
