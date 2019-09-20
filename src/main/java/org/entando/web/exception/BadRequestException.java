package org.entando.web.exception;

public class BadRequestException extends IllegalArgumentException {

    public BadRequestException() {
        super("org.entando.error.badFormatRequest");
    }

}
