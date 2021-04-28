package com.johnwu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice	
@SuppressWarnings({"unchecked", "rawtypes"})
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UrlNotFoundException.class)
	public ResponseEntity<Object> postNotFound(UrlNotFoundException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e) {
        return new ResponseEntity("Unexpected exception: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
