package com.onetwo.webservice.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
