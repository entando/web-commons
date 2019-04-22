package org.entando.web.controller;

import org.entando.web.exception.ErrorResponse;
import org.entando.web.exception.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
public class BaseErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;
	private final MessageSource messageSource;

	@Autowired
	public BaseErrorController(final ErrorAttributes errorAttributes, final MessageSource messageSource) {
		this.errorAttributes = errorAttributes;
		this.messageSource = messageSource;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/error")
	public ResponseEntity<ErrorResponse> error(final HttpServletRequest request, final Locale locale) {
		final Map<String, Object> errorAttribs = getErrorAttributes(request);
		final int statusCode = (int) errorAttribs.get("status");
		final List<FieldError> errors = (List<FieldError>) errorAttribs.get("errors");
		final String message = messageSource.getMessage("org.entando.error.validationError", null, locale);
		final ErrorResponse errorResponse = errors != null
				? new ValidationErrorResponse(message, errors)
				: new ErrorResponse((String) errorAttribs.get("message"));
		return ResponseEntity.status(statusCode).body(errorResponse);
	}

	private Map<String, Object> getErrorAttributes(final HttpServletRequest request) {
		return errorAttributes.getErrorAttributes(new ServletWebRequest(request), log.isDebugEnabled());
	}

}
