package com.navi.bootcamp.json.transformation.core.repository;

import com.navi.bootcamp.json.transformation.core.entity.Status;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationRuleRepository extends CrudRepository<TransformationRule, Long> {

    List<TransformationRule> findAllByRuleName(String ruleName);

    Optional<TransformationRule> findByRuleNameAndStatus(String ruleName, Status status);

    Optional<TransformationRule> findByRuleNameAndRuleVersion(String ruleName, Integer ruleVersion);

    Optional<TransformationRule> findTopByRuleNameOrderByRuleVersionDesc(String ruleName);


}
