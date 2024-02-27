package com.onetwo.webservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;

@Slf4j
public class UriBuilder {

    public static String getQueryStringUri(Object o){
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.newInstance();

        for (Field field : o.getClass().getDeclaredFields()){
            field.setAccessible(true);

            try {
                if (field.get(o) != null){
                    componentsBuilder.queryParam(field.getName(), field.get(o));
                }
            } catch (IllegalAccessException e){
                log.error("IllegalAccessException", e);
            }
        }

        return componentsBuilder.toUriString();
    }
}
