package com.navi.bootcamp.json.transformation.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Readiness Check", description = "API to check the readiness of the application")
@RestController
public class ReadinessCheckController {

    @ApiOperation(value = "Check the readiness of the application", response = String.class)
    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>("PONG", HttpStatus.OK);
    }
}
