package com.navi.bootcamp.json.transformation.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.Clock;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication(scanBasePackages = "com.navi.bootcamp")
@EnableCaching
@EnableAspectJAutoProxy
public class JsonTransformationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonTransformationServiceApplication.class, args);
	}

	private static final String dateFormat = "yyyy-MM-dd";
	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	@Bean
	ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
			.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
			.featuresToEnable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature())
			.build();
	}

	@Bean
	Clock clock() {
		return Clock.systemDefaultZone();
	}
}
