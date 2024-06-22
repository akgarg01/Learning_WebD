package com.navi.bootcamp.json.transformation.core.metrics;

import io.prometheus.client.Counter;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class ExceptionMetrics {

    private static final Counter EXCEPTION_COUNTER = Counter.build("json_transformation_service_exceptions_total",
            "Counter to track json transformation service exceptions")
        .labelNames("exceptionName")
        .register();

    @AfterThrowing(pointcut = "execution(* com.navi.bootcamp.json.transformation.service..*.*(..)))"
        , throwing = "ex")
    public void raiseMetrics(Exception ex) {
        String exceptionName = Optional.ofNullable(ex.getCause())
            .map(Throwable::getClass)
            .map(Class::getSimpleName)
            .orElse(ex.getClass().getSimpleName());

        EXCEPTION_COUNTER.labels(exceptionName).inc();
    }
}
