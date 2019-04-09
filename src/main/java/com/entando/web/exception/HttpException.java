package com.entando.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {

    private final HttpStatus status;

    public HttpException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }

    public HttpException(final HttpStatus status, final String message, final Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }

}
