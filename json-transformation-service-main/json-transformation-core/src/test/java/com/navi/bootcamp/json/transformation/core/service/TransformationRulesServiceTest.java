package com.navi.bootcamp.json.transformation.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navi.bootcamp.json.transformation.core.contract.RuleTemplate;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleCreationRequest;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleStatusChangeRequest;
import com.navi.bootcamp.json.transformation.core.entity.Status;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import com.navi.bootcamp.json.transformation.core.exception.TransformationDefinitionNotFoundException;
import com.navi.bootcamp.json.transformation.core.repository.TransformationRuleRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransformationRulesServiceTest {

    @Mock
    TransformationRuleRepository transformationRuleRepository;

    @InjectMocks
    TransformationRulesService transformationRulesService;

    @Test
    void expectsToFindTransformationRuleByRuleNameAndStatus() {
        final TransformationRule transformationRule = TransformationRule.builder().ruleName("dummy_rule")
            .ruleOwner("ruleOwner").ruleVersion(0).lastUpdatedBy("requester")
            .status(Status.INACTIVE).build();
        when(transformationRuleRepository.findByRuleNameAndStatus(any(), any())).thenReturn(
            Optional.of(transformationRule));

        Optional<TransformationRule> response = transformationRulesService
            .findTransformationRuleByRuleNameAndStatus("dummy_rule", Status.INACTIVE);

        assertThat(response).isPresent();
        assertThat(response.get().getRuleName()).isEqualTo("dummy_rule");
    }

    @Test
    void expectsToCreateTransformationRule() {
        final TransformationRuleCreationRequest request = TransformationRuleCreationRequest.builder()
            .ruleName("dummy_rule").ruleOwner("ruleOwner")
            .ruleSpecs(List.of(RuleTemplate.builder().build())).build();
        when(
            transformationRuleRepository.findTopByRuleNameOrderByRuleVersionDesc(any())).thenReturn(
            Optional.empty());

        transformationRulesService.createTransformationRule(request, "requester");

        verify(transformationRuleRepository, times(1)).save(any());
    }

    @Test
    void expectsToFetchTransformationRuleByRuleName() {
        // TODO: Implement the test
        //  Create a TransformationRule object with any ruleName, ruleOwner, ruleVersion,
        //  lastUpdatedBy and status
        //  Create a list of TransformationRule objects with the above TransformationRule object
        //  Use when() method to mock the transformationRuleRepository.findAllByRuleName() method
        //  Use the thenReturn() method to return the list of TransformationRule objects created above
        //  Call the fetchTransformationRuleByRuleName() method with the ruleName and store the
        //  result in a list
        //  Use the verify() method to verify the transformationRuleRepository.findAllByRuleName
        //  () method is called once
        //  Use the assertThat() method to assert that the transformationRules list is not empty
    }

    @Test
    void expectsToFailToFetchTransformationRuleByRuleName() {
        // TODO: Implement the test
    }

    @Test
    void expectsToChangeTransformationRuleStatus() {
        final TransformationRuleStatusChangeRequest request = TransformationRuleStatusChangeRequest.builder()
            .ruleName("dummy_rule").version(2).finalStatus(Status.ACTIVE).build();
        final TransformationRule transformationRule = TransformationRule.builder().ruleName("dummy_rule")
            .ruleOwner("ruleOwner").ruleVersion(0).lastUpdatedBy("requester").status(Status.ACTIVE)
            .build();
        when(transformationRuleRepository.findByRuleNameAndRuleVersion(any(), any()))
            .thenReturn(Optional.of(transformationRule));

        TransformationRule response = transformationRulesService.changeTransformationRuleStatus(
            request, "requester");

        verify(transformationRuleRepository, times(1)).findByRuleNameAndRuleVersion(any(), any());
        verify(transformationRuleRepository, times(1)).findByRuleNameAndStatus(any(), any());
        assertThat(response.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(response.getLastUpdatedBy()).isEqualTo("requester");
    }

    @Test
    @Disabled
    void expectsToFailToChangeTransformationRuleStatus() {
        final TransformationRuleStatusChangeRequest request = TransformationRuleStatusChangeRequest.builder()
            .ruleName("dummy_rule").version(2).finalStatus(Status.ACTIVE).build();
        when(transformationRuleRepository.findByRuleNameAndRuleVersion(any(), any()))
            .thenReturn(Optional.empty());

        assertThrows(TransformationDefinitionNotFoundException.class,
            () -> transformationRulesService
                .changeTransformationRuleStatus(request, "requester"));
    }
}
