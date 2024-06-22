package com.navi.bootcamp.json.transformation.core.controller;

import com.navi.bootcamp.json.transformation.core.constants.Constants;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleCreationRequest;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleStatusChangeRequest;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import com.navi.bootcamp.json.transformation.core.service.TransformationRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Transformation Rules Version", description = "API to manage transformation rules")
@RestController
@Log4j2
@RequestMapping("/v1/transformation-rules")
public class TransformationRuleController {

    private final TransformationRulesService transformationRulesService;

    @Autowired
    public TransformationRuleController(TransformationRulesService transformationRulesService) {
        this.transformationRulesService = transformationRulesService;
    }

    @ApiOperation(value = "Create a new transformation rule", response = TransformationRule.class)
    @PostMapping("/create")
    public ResponseEntity<?> uploadTransformationRule(
        @NotNull @RequestBody @Valid TransformationRuleCreationRequest request,
        @RequestHeader(value = Constants.X_CLIENT_ID) String requester) {
        log.info("Received request to upload new transformation rule by requester {}", requester);
        return new ResponseEntity<>(transformationRulesService.createTransformationRule(request, requester), HttpStatus.OK);
    }

    @ApiOperation(value = "Change the status of a transformation rule", response = TransformationRule.class)
    @PutMapping("/change-status")
    public ResponseEntity<?> updateTransformationRuleStatus(
        @NotNull @RequestBody TransformationRuleStatusChangeRequest request,
        @RequestHeader(value = Constants.X_CLIENT_ID) String requester) {
        log.info("Received request to change transformation rule status by requester {}", requester);
        return new ResponseEntity<>(transformationRulesService.changeTransformationRuleStatus(request, requester), HttpStatus.OK);

    }

    @ApiOperation(value = "Get all transformation rule by name", response = TransformationRule.class)
    @GetMapping("/all-by-name")
    public ResponseEntity<?> getTransformationRule(
        @NotNull @RequestParam String ruleName,
        @RequestHeader(value = Constants.X_CLIENT_ID) String requester) {
        log.info("Received request to fetch transformation rule details by requester {}", requester);
        List<TransformationRule> transformationRules = transformationRulesService.fetchTransformationRuleByRuleName(ruleName);
        return new ResponseEntity<>(transformationRules, HttpStatus.OK);
    }

    @ApiOperation(value = "Get active transformation rule by name", response = TransformationRule.class)
    @GetMapping("/active")
    public ResponseEntity<?> getActiveTransformationRule(
        @NotNull @RequestParam String ruleName,
        @RequestHeader(value = Constants.X_CLIENT_ID) String requester) {
        // TODO: Implement this method
        //  should fetch the active transformation rule with the given rule name
        //  The transformation rule should be fetched from the database
        //  If no transformation rule is found with the given rule name, return a
        //  HttpStatus.NOT_FOUND response
        //  Else Return the transformation rule with HttpStatus.OK
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
