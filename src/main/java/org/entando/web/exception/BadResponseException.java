package org.entando.web.exception;

public class BadResponseException extends InternalServerException {

    public BadResponseException() {
        super("org.entando.error.badFormatResponse");
    }

}
