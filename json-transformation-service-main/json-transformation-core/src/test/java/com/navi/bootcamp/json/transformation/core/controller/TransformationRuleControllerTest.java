package com.navi.bootcamp.json.transformation.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.bootcamp.json.transformation.core.constants.Constants;
import com.navi.bootcamp.json.transformation.core.contract.RuleTemplate;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleCreationRequest;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleStatusChangeRequest;
import com.navi.bootcamp.json.transformation.core.entity.Status;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import com.navi.bootcamp.json.transformation.core.service.TransformationRulesService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TransformationRuleController.class)
public class TransformationRuleControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransformationRulesService transformationRulesService;

    @Test
    void testUploadTransformationRuleValidCase() throws Exception {
        final TransformationRuleCreationRequest request = TransformationRuleCreationRequest.builder()
            .ruleName("dummy_rule").ruleOwner("ruleOwner").ruleSpecs(List.of(RuleTemplate.builder().build())).build();
        final TransformationRule transformationRule = TransformationRule.builder().ruleName("dummy_rule")
            .ruleOwner("ruleOwner").ruleVersion(0).lastUpdatedBy("requester").status(Status.INACTIVE).build();

        when(transformationRulesService.createTransformationRule(any(TransformationRuleCreationRequest.class), anyString()))
            .thenReturn(transformationRule);

        final MockHttpServletResponse response = mockMvc.perform(post("/v1/transformation-rules"
                + "/create")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header(Constants.X_CLIENT_ID, "requester")
                .header(Constants.X_CORRELATION_ID, "correlationId")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotEmpty();
    }

    @Test
    void testUpdateTransformationRuleStatusValidCase() throws Exception {
        final TransformationRuleStatusChangeRequest request = TransformationRuleStatusChangeRequest.builder()
            .ruleName("dummy_rule").version(2).finalStatus(Status.ACTIVE).build();
        final TransformationRule transformationRule = TransformationRule.builder().ruleName("dummy_rule")
            .ruleOwner("ruleOwner").ruleVersion(0).lastUpdatedBy("requester").status(Status.ACTIVE).build();
        when(transformationRulesService.changeTransformationRuleStatus(any(TransformationRuleStatusChangeRequest.class), anyString()))
            .thenReturn(transformationRule);

        final MockHttpServletResponse response = mockMvc.perform(put("/v1/transformation-rules/change-status")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header(Constants.X_CLIENT_ID, "requester")
                .header(Constants.X_CORRELATION_ID, "correlationId")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotEmpty();
    }

    @Test
    void testGetTransformationRuleValidCase() throws Exception {
        final TransformationRule transformationRule = TransformationRule.builder().ruleName("dummy_rule")
            .ruleOwner("ruleOwner").ruleVersion(0).lastUpdatedBy("requester").status(Status.ACTIVE).build();
        when(transformationRulesService.fetchTransformationRuleByRuleName(anyString()))
            .thenReturn(List.of(transformationRule));

        final MockHttpServletResponse response = mockMvc.perform(get("/v1/transformation-rules"
                + "/all-by-name")
                .param("ruleName", "dummy_rule")
                .contentType(MediaType.APPLICATION_JSON)
                .header(Constants.X_CLIENT_ID, "requester")
                .header(Constants.X_CORRELATION_ID, "correlationId")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotEmpty();
    }

}
