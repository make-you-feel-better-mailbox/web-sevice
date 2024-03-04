package com.onetwo.webservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    private static final String RESPONSE_MODEL_ATTRIBUTE = "ResponseEntity";

    private AjaxErrorResponseView errorView = new AjaxErrorResponseView();

    private static final String ERROR_REDIRECT = GlobalURI.REDIRECT + GlobalURI.ERROR_URI_ROOT;

    @ExceptionHandler(Exception.class)
    public ModelAndView rootExceptionHandler(Exception e) {
        log.error("Exception", e);
        return handleView(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ModelAndView badRequestException(BadRequestException e) {
        log.info("BadRequestException", e);
        return handleView(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExpectResultException.class)
    public ModelAndView notExpectResultException(Exception e) {
        log.error("NotExpectResultException", e);
        return handleView(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView constraintViolationException(ConstraintViolationException e) {
        log.info("ConstraintViolationException", e);
        return handleView(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException", e);
        return handleView(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ModelAndView webClientResponseException(WebClientResponseException e) {
        log.info("WebClientResponseException", e);
        if (e.getHeaders().containsKey(GlobalStatus.TOKEN_VALIDATION_HEADER)) {
            String tokenValidationResult = e.getHeaders().getFirst(GlobalStatus.TOKEN_VALIDATION_HEADER);
            log.info("Token validation fail = {}", tokenValidationResult);
            return handleView(tokenValidationResult, HttpStatus.valueOf(e.getStatusCode().value()));
        }
        return handleView(e.getResponseBodyAsString(), HttpStatus.valueOf(e.getStatusCode().value()));
    }

    private ModelAndView handleView(Object response, HttpStatus httpStatus) {
        boolean ajaxRequest = ServletUtils.isAjaxRequest();

        ModelAndView mav = new ModelAndView();

        mav.setStatus(httpStatus);

        if (ajaxRequest) {
            mav.addObject(RESPONSE_MODEL_ATTRIBUTE, response);
            mav.setView(errorView);
            return mav;
        }

        mav.addObject(response);
        mav.setViewName("main/error");

        return mav;
    }

    private final class AjaxErrorResponseView extends AbstractView {

        @Override
        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
            logger.debug("renderMergedOutputModel called!!");

            Object responseModel = model.get(GlobalExceptionHandler.RESPONSE_MODEL_ATTRIBUTE);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            try {
                response.getWriter().write(objectMapper.writeValueAsString(responseModel));
            } catch (Exception e) {
                logger.info("client 에서 페이지를 닫아 Exception 발생, 별도 처리 하지 않음");
            }
        }

    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return om;
    }
}
