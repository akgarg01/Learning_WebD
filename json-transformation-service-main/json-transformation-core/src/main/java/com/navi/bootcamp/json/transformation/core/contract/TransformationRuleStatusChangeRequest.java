package com.navi.bootcamp.json.transformation.core.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.navi.bootcamp.json.transformation.core.entity.Status;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformationRuleStatusChangeRequest {

    @NotNull
    private String ruleName;
    @NotNull
    private Integer version;
    @NotNull
    private Status finalStatus;

}
