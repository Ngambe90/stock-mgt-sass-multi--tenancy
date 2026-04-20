package com.ngambe.sass_stock.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

	private final String message;

	public BusinessException( final String message) {
		super();
		this.message = message;
	}
	
	
}
