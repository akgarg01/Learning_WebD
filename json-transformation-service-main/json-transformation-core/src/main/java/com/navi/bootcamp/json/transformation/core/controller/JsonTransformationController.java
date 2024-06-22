package com.navi.bootcamp.json.transformation.core.controller;

import com.navi.bootcamp.json.transformation.core.constants.Constants;
import com.navi.bootcamp.json.transformation.core.contract.JsonTransformationRequest;
import com.navi.bootcamp.json.transformation.core.service.JsonTransformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Json Transformation", description = "API to transform JSON")
@RestController
@Log4j2
@RequestMapping("/v1/json-transformation")
public class JsonTransformationController {
    private final JsonTransformationService jsonTransformationService;

    @Autowired
    public JsonTransformationController(JsonTransformationService jsonTransformationService) {
        this.jsonTransformationService = jsonTransformationService;
    }

    @ApiOperation(value = "Transform JSON based on the rules", response = Map.class)
    @PostMapping("/transform")
    public ResponseEntity<?> transform(
        @NotNull @RequestBody JsonTransformationRequest request,
        @RequestHeader(value = Constants.X_CLIENT_ID) String requester,
        @RequestHeader(value = Constants.X_CORRELATION_ID) String correlationId) {
        log.info("Received transformation request for requestId : {} by requester {}",
            request.getRequestId(), requester);
        Map<String, Object> response = jsonTransformationService.transformJson(request);
        return response.isEmpty() ? ResponseEntity.badRequest().body(null) : ResponseEntity.ok(response);
    }

}
