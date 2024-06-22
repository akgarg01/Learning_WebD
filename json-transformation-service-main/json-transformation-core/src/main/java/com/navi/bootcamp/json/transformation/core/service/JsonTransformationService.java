package com.navi.bootcamp.json.transformation.core.service;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.navi.bootcamp.json.transformation.core.contract.JsonTransformationRequest;
import com.navi.bootcamp.json.transformation.core.utils.CommonUtils;
import com.navi.bootcamp.json.transformation.core.entity.Status;
import com.navi.bootcamp.json.transformation.core.entity.TransformationRule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class JsonTransformationService {

    private final TransformationRulesService transformationRulesService;
    private final CommonUtils commonUtils;

    @Autowired
    public JsonTransformationService(TransformationRulesService transformationRulesService,
        CommonUtils commonUtils) {
        this.transformationRulesService = transformationRulesService;
        this.commonUtils = commonUtils;
    }

    public Map<String, Object> transformJson(JsonTransformationRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        List<TransformationRule> transformationRuleList = fetchTransformationRulesToExecute(
            request);
        for (TransformationRule transformationRule : transformationRuleList) {
            try {
                List<Object> chainrSpecJSON = JsonUtils.jsonToList(commonUtils
                    .objectToString(transformationRule.getRuleSpecs()));
                Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
                Object transformedOutput = chainr.transform(request.getInput());
                responseMap.put(transformationRule.getRuleName(), transformedOutput);
            } catch (Exception ex) {
                log.error("Failed to transform json for requestId {}", request.getRequestId());
            }
        }
        return responseMap;
    }


    private List<TransformationRule> fetchTransformationRulesToExecute(
        JsonTransformationRequest request) {
        List<TransformationRule> transformationRuleList = new ArrayList<>();
        for (String ruleName : request.getRuleNames()) {
            transformationRulesService.findTransformationRuleByRuleNameAndStatus(ruleName,
                    Status.ACTIVE)
                .map(transformationRuleList::add);
        }
        return transformationRuleList;
    }

}
