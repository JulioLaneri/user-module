package com.api.distri.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String mensaje) {
        super(HttpStatus.BAD_REQUEST, mensaje);
    }

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "Bad Request");
    }

}