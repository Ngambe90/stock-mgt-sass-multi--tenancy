package com.ngambe.sass_stock.exception.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @JsonInclude(content = Include.NON_NULL)
public class ErrorResponse {
	
	private String errorCode;
	private String message;
	private String path;
	private List<ValidationError>validationErrors;
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
	public static class ValidationError{
		private String field;
		private String code;
		private String message;
	}

}
