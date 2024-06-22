package com.navi.bootcamp.json.transformation.core.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
}
