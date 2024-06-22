package com.navi.bootcamp.json.transformation.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ReadinessCheckController.class)
public class ReadinessCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void expectsToReturnSuccessResponseForReadinessCheck() throws Exception {

        final MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/ping")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(mockHttpServletResponse.getContentAsString()).isEqualTo("PONG");
    }

}
