package com.navi.bootcamp.json.transformation.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommonUtilsTest {

    @Mock
    private ObjectMapper objectMapper;

    private CommonUtils commonUtils;

    @BeforeEach
    public void setUp() {
        commonUtils = new CommonUtils(objectMapper);
    }

    @Test
    public void whenConvertingObjectToString_thenReturnsJson() throws JsonProcessingException {
        Object o = new Object();
        String expected = "json";
        when(objectMapper.writeValueAsString(o)).thenReturn(expected);

        String actual = commonUtils.objectToString(o);

        assertEquals(expected, actual);
    }

    @Test
    public void whenConvertingObjectToString_thenThrowsException() throws JsonProcessingException {
        Object o = new Object();
        when(objectMapper.writeValueAsString(o)).thenThrow(JsonProcessingException.class);

        assertThrows(RuntimeException.class, () -> commonUtils.objectToString(o));
    }


}
