package com.navi.bootcamp.json.transformation.core.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetricsBuilder {
    private static final String DURATION_METRIC_FORMAT = "service_%s_%s_duration_seconds";
    private static final String TOTAL_METRIC_FORMAT = "service_%s_%s_request_total";

    public static Histogram duration(String service, String endpoint, String... labels) {
        return Histogram.build().name(String.format(DURATION_METRIC_FORMAT, service, endpoint))
            .labelNames(allLabels(labels))
            .help("Time taken by a given API.")
            .register();
    }

    public static Counter counter(String service, String endpoint, String... labels) {
        return Counter.build(String.format(TOTAL_METRIC_FORMAT, service, endpoint),
                "Counter to track http response codes")
            .labelNames(allLabelsForCounter(labels)).register();
    }

    private static String[] allLabels(String[] labels) {
        List<String> allLabels = new ArrayList<>();
        allLabels.add("serviceName");
        allLabels.add("operation");
        allLabels.addAll(Arrays.asList(labels));
        return allLabels.toArray(new String[0]);
    }

    private static String[] allLabelsForCounter(String[] labels) {
        List<String> allLabels = new ArrayList<>();
        allLabels.add("serviceName");
        allLabels.add("operation");
        allLabels.add("code");
        allLabels.addAll(Arrays.asList(labels));
        return allLabels.toArray(new String[0]);
    }
}
