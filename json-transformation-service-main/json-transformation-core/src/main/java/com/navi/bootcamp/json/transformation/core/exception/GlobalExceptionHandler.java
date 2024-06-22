package com.navi.bootcamp.json.transformation.core.exception;

import com.navi.bootcamp.json.transformation.core.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TransformationDefinitionNotFoundException.class})
    public final ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(ex.getMessage())
            .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(ex.getMessage() == null
                ? "Unknown exception occurred : " + ex
                : ex.getMessage())
            .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
