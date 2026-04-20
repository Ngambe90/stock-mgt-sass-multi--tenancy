package com.ngambe.sass_stock.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ngambe.sass_stock.exception.response.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=BusinessException.class)
	public ResponseEntity<ErrorResponse> handlerException(
			final BusinessException ex,
			final HttpServletRequest request
			){
		log.error("Entity not Found");
		ErrorResponse errorResponse = ErrorResponse.builder()
				//.errorCode("NOT FOUND")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		final HttpStatus status = getHttpStatus(ex);
		return ResponseEntity.status(status).body(errorResponse);
	}
	
	
	@ExceptionHandler(value=EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerException(
			final EntityNotFoundException ex,
			final HttpServletRequest request
			){
		log.error("Entity not Found");
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorCode("NOT FOUND")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(value=MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handlerException(
			final MethodArgumentNotValidException ex,
			final HttpServletRequest request
			){
		log.error("Entity not Found");
		final List<ErrorResponse.ValidationError> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error->{
			final String fieldName = ((FieldError)error).getField();
			final String errorCode = error.getDefaultMessage();
			final String defaultMessage = error.getDefaultMessage();// todo add translation later
			errors.add(ErrorResponse.ValidationError.builder()
													.field(fieldName)
													.code(errorCode)
													.message(defaultMessage)
													.build());
		});
		ErrorResponse errorResponse = ErrorResponse.builder()
													.validationErrors(errors)
													.path(request.getRequestURI())
													.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	
	private HttpStatus getHttpStatus(BusinessException ex) {
		if(ex instanceof DuplicateRessourceException) {
			return HttpStatus.CONFLICT;
		}
		return HttpStatus.BAD_REQUEST;
	}


}
