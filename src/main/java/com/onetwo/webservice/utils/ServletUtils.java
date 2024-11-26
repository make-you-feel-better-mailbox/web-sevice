package com.onetwo.webservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    public static boolean isAjaxRequest() {
        HttpServletRequest request = getRequest();
        String header = request.getHeader("x-requested-with");

        if (!StringUtils.hasText(header)) {
            return false;
        }
        return header.equals("XMLHttpRequest");
    }
}
