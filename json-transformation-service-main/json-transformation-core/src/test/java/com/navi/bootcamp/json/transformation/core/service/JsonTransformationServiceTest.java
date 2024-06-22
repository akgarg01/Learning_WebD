package com.navi.bootcamp.json.transformation.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.bootcamp.json.transformation.core.contract.JsonTransformationRequest;
import com.navi.bootcamp.json.transformation.core.contract.RuleTemplate;
import com.navi.bootcamp.json.transformation.core.entity.Status;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import com.navi.bootcamp.json.transformation.core.utils.CommonUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonTransformationServiceTest {

    @Mock
    TransformationRulesService transformationRulesService;

    @Spy
    ObjectMapper objectMapper;

    CommonUtils commonUtils;

    JsonTransformationService jsonTransformationService;

    @BeforeEach
    void setup() {
        commonUtils = new CommonUtils(objectMapper);
        jsonTransformationService = new JsonTransformationService(transformationRulesService, commonUtils);
    }

    @Test
    void generateTransformedJsonValidCase() {
        final JsonTransformationRequest request = JsonTransformationRequest.builder()
            .requestId("requestId").ruleNames(List.of("dummy_rule")).input(Map.of("customerId", "c_id")).build();
        final TransformationRule transformationRule = TransformationRule.builder().ruleName("dummy_rule")
            .ruleOwner("ruleOwner").ruleVersion(0).lastUpdatedBy("requester").status(Status.ACTIVE)
            .ruleSpecs(List.of(
                RuleTemplate.builder().operation("shift").spec(Map.of("customerId", "id" )).build())).build();
        when(transformationRulesService.findTransformationRuleByRuleNameAndStatus("dummy_rule", Status.ACTIVE))
            .thenReturn(Optional.of(transformationRule));

        Map<String, Object> response = jsonTransformationService.transformJson(request);

        assertThat(response).isNotEmpty();
        assertThat(response).containsKey("dummy_rule");
    }

    @Test
    void generateTransformedJsonInvalidCase() {
        final JsonTransformationRequest request = JsonTransformationRequest.builder()
            .requestId("requestId").ruleNames(List.of("dummy_rule")).input(new HashMap<>()).build();
        when(transformationRulesService.findTransformationRuleByRuleNameAndStatus("dummy_rule", Status.ACTIVE))
            .thenReturn(Optional.empty());

        Map<String, Object> response = jsonTransformationService.transformJson(request);

        assertThat(response).isEmpty();
    }
}
