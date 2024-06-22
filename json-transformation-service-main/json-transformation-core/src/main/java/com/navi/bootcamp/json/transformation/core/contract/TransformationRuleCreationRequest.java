package com.navi.bootcamp.json.transformation.core.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class TransformationRuleCreationRequest {

    @NotNull
    @Size(min = 3, max = 25)
    @Pattern(regexp = "^[a-z_]*$", message = "Rule name should contain only lowercase alphabets and underscores")
    private String ruleName;

    @NotNull
    private String ruleOwner;

    @NotNull
    @NotEmpty
    private List<RuleTemplate> ruleSpecs;

    private String comment;
}
