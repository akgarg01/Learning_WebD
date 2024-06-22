package com.navi.bootcamp.json.transformation.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.bootcamp.json.transformation.core.constants.Constants;
import com.navi.bootcamp.json.transformation.core.contract.JsonTransformationRequest;
import com.navi.bootcamp.json.transformation.core.service.JsonTransformationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebMvcTest(JsonTransformationController.class)
public class JsonTransformationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JsonTransformationService jsonTransformationService;

    @Test
    void testJsonTransformationController() throws Exception {
        final JsonTransformationRequest request = JsonTransformationRequest.builder()
            .requestId("requestId").ruleNames(List.of("dummy_rule")).input(new HashMap<>()).build();
        when(jsonTransformationService.transformJson(any(JsonTransformationRequest.class)))
            .thenReturn(Map.of("k1", "v1"));

        final MockHttpServletResponse response = mockMvc.perform(post("/v1/json-transformation/transform")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header(Constants.X_CLIENT_ID, "client")
                .header(Constants.X_CORRELATION_ID, "correlationId")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotEmpty();
    }

}
