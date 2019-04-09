package com.entando.web.controller;

import com.entando.keycloak.exception.ForbiddenException;
import com.entando.keycloak.exception.UnauthorizedException;
import com.entando.web.exception.ErrorResponse;
import com.entando.web.exception.HttpException;
import com.entando.web.exception.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

	private final MessageSource messageSource;

	@Autowired
	public ControllerExceptionHandler(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> handleException(final UnauthorizedException exception, final Locale locale) {
		log.warn("Unauthozired exception caught {}", exception.getMessage(), exception);
		final String message = messageSource.getMessage("com.entando.error.unauthorized", null, locale);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(message));
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorResponse> handleException(final ForbiddenException exception, final Locale locale) {
		log.warn("Forbidden exception caught {}", exception.getMessage(), exception);
		final String message = messageSource.getMessage("com.entando.error.permissionDenied", null, locale);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(message));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleException(final HttpMediaTypeNotSupportedException exception) {
		return ResponseEntity.status(400).body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleException(final IllegalArgumentException exception) {
		return ResponseEntity.status(400).body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleException(final HttpRequestMethodNotSupportedException exception) {
		return ResponseEntity.status(405).body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(HttpException.class)
	public ResponseEntity<ErrorResponse> handleException(final HttpException exception, final Locale locale) {
		return ResponseEntity.status(exception.getStatus())
				.body(new ErrorResponse(messageSource.getMessage(exception.getMessage(), null, locale)));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleException(final HttpMessageNotReadableException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMostSpecificCause().getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationFailure(
			final MethodArgumentNotValidException exception, final Locale locale) {
		final String message = messageSource.getMessage("com.entando.error.validationError", null, locale);
		final ValidationErrorResponse response = new ValidationErrorResponse(message);

		exception.getBindingResult().getFieldErrors().forEach(fieldError ->
			response.addError(messageSource, locale, fieldError)
		);

		return ResponseEntity.unprocessableEntity().body(response);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ValidationErrorResponse> handleException(
			final ConstraintViolationException exception, final Locale locale) {
		log.error("ConstraintViolationException", exception);

		final String message = messageSource.getMessage("com.entando.error.validationError", null, locale);
		final ValidationErrorResponse response = new ValidationErrorResponse(message);
		exception.getConstraintViolations().forEach(fieldError ->
			response.addError(messageSource, locale, fieldError.getPropertyPath().toString(), fieldError.getMessage(), fieldError.getExecutableParameters())
		);

		return ResponseEntity.unprocessableEntity().body(response);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorResponse> handleException(final Throwable exception, final Locale locale) {
		final String message = messageSource.getMessage("com.entando.error.internalServerError", null, locale);
		log.error("Error while trying to process request", exception);
		return ResponseEntity.status(500).body(new ErrorResponse(message));
	}
	
}
