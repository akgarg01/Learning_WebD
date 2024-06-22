package com.navi.bootcamp.json.transformation.core.service;

import static com.navi.bootcamp.json.transformation.core.entity.Status.INACTIVE;
import com.navi.bootcamp.json.transformation.core.constants.Constants;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleCreationRequest;
import com.navi.bootcamp.json.transformation.core.contract.TransformationRuleStatusChangeRequest;
import com.navi.bootcamp.json.transformation.core.entity.Status;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import com.navi.bootcamp.json.transformation.core.exception.TransformationDefinitionNotFoundException;
import com.navi.bootcamp.json.transformation.core.repository.TransformationRuleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TransformationRulesService {

    private final TransformationRuleRepository transformationRuleRepository;

    @Autowired
    public TransformationRulesService(TransformationRuleRepository transformationRuleRepository) {
        this.transformationRuleRepository = transformationRuleRepository;
    }

    public Optional<TransformationRule> findTransformationRuleByRuleNameAndStatus(String ruleName,
        Status status) {
        return transformationRuleRepository.findByRuleNameAndStatus(ruleName, status);
    }

    public TransformationRule createTransformationRule(TransformationRuleCreationRequest request,
        String requester) {
        TransformationRule transformationRule = TransformationRule.builder()
            .ruleName(request.getRuleName())
            .ruleOwner(request.getRuleOwner()).ruleSpecs(request.getRuleSpecs())
            .comment(request.getComment()).uploadedBy(requester)
            .status(INACTIVE).lastUpdatedBy(requester).build();

        Optional<TransformationRule> transformationRuleOptional = transformationRuleRepository
            .findTopByRuleNameOrderByRuleVersionDesc(request.getRuleName());

        if (transformationRuleOptional.isPresent()) {
            transformationRule.setRuleVersion(
                transformationRuleOptional.get().getRuleVersion() + Constants.INCREMENT_VALUE);
        } else {
            transformationRule.setRuleVersion(Constants.DEFAULT_VERSION);
        }
        return transformationRuleRepository.save(transformationRule);
    }

    public List<TransformationRule> fetchTransformationRuleByRuleName(String ruleName)
        throws TransformationDefinitionNotFoundException {
        // TODO: Implement this method
        //  should fetch all transformation rules with the given rule name
        //  The transformation rules should be fetched from the database
        //  If no transformation rules are found with the given rule name, throw a
        //  TransformationDefinitionNotFoundException
        //  Return the list of transformation rules
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // TODO: Fix this method
    //  The method should change the status of the transformation rule with the given rule name and version
    //  to the final status provided in the request
    //  If the final status is ACTIVE, then the status of the previously active rule with the same rule name
    //  should be changed to INACTIVE
    //  The method should return the updated transformation rule
    //  The method should also update the lastUpdatedBy field of the transformation rule
    //  The method should throw a TransformationDefinitionNotFoundException if no transformation
    //  rule is found for the given rule name and version
    public TransformationRule changeTransformationRuleStatus(
        TransformationRuleStatusChangeRequest request, String requester) {

        List<TransformationRule> updatedMetadataList = new ArrayList<>();
        Optional<TransformationRule> transformationRuleOptional = transformationRuleRepository
            .findByRuleNameAndRuleVersion(request.getRuleName(), request.getVersion());

        TransformationRule metadata = transformationRuleOptional.get();

        if (Status.ACTIVE.equals(request.getFinalStatus())) {

            Optional<TransformationRule> publishedRule = transformationRuleRepository
                .findByRuleNameAndStatus(request.getRuleName(), Status.ACTIVE);

            publishedRule.ifPresent((rule) -> {
                rule.setStatus(INACTIVE);
                rule.setLastUpdatedBy(requester);
                updatedMetadataList.add(rule);
            });
        }

        metadata.setStatus(request.getFinalStatus());
        metadata.setLastUpdatedBy(requester);
        updatedMetadataList.add(metadata);
        transformationRuleRepository.saveAll(updatedMetadataList);

        return metadata;
    }

}
