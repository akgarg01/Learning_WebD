## Assignments

### Assignment 1

Fix the changeTransformationRuleStatus method in TransformationRulesService to throw the
TransformationDefinitionNotFoundException when the transformation rule is not found. To test this,
remove the @Disabled annotation from the expectsToFailToChangeTransformationRuleStatus test in
TransformationRulesServiceTest and run the test. It should pass.

### Assignment 2

Complete the fetchTransformationRuleByRuleName method in TransformationRulesService. To test
this method run the application and try to fetch the transformation rule by name using the
`/all-by-name` API for the rule name `assignment_rule`. The API should return 3 rules with
the name `assignment_rule`.

### Assignment 3

Complete the expectsToFetchTransformationRuleByRuleName and
expectsToFailToFetchTransformationRuleByRuleName test in TransformationRulesServiceTest. Both tests
should pass.

### Assignment 4

Implement the `/active` API in TransformationRulesController which fetches the
active transformation rule by the rule name. Also, write the test for the same. To verify the API,
run the application and try to fetch the active transformation rule by name using the
`/active` API for the rule name `assignment_rule`. The API should return only one
active rule with the name `assignment_rule`.

### Assignment 5

Write an API in the TransformationRulesVersionController to fetch rules by name and version. The API
should accept the rule name and optionally the version as request parameters, returning the
corresponding rule if it exists. If the version is not specified, the API should return the latest
version of the rule. Also, write the test for the same. To verify the API, run the application and
try to fetch the transformation rule by name and version using the created API for the rule name 
`assignment_rule` and version `2`. The API should return the rule with the name `assignment_rule` and 
version `2`.

### Assignment 6

Write an API to return all the transformation rules in the system. The API should return all the 
rules irrespective of their status. Also, write the test for the same. To verify the API, run the
application and try to fetch all the transformation rules using the created API. The API should
return all the rules.

### Assignment 7

Write an API to delete a transformation rule by name and version. The API should accept the rule
name and version as request parameters and delete the corresponding rule if it exists. Also, write
the test for the same. To verify the API, run the application and try to delete the transformation
rule by name and version using the created API for the rule name `assignment_rule` and version `3`. The
API should delete the rule with the name `assignment_rule` and version `3`.

Hint : 
```
DELETE Type method is not white-listed in the application. To enable the DELETE method, 
update the SpringSecurityConfig file.
```

### Assignment 8 - Optional

Introduced a new field `description` in the TransformationRule entity. Update the TransformationRule
entity, TransformationRuleRepository, TransformationRulesService, and TransformationRulesController to
support this new field. While creating a new transformation rule, the description field should be
mandatory passed in the request. Update the TransformationRuleCreationRequest to support this new field.
To verify the API, run the application and try to create a new transformation rule with the description
using the `/create` API. The API should create a new transformation rule with the description.
Try to fetch the transformation rule by name using the `/all-by-name` API for the rule name
to verify the description field is saved in the database. 

Hint :
To add new field in the database table, you will have to create a new migration file in 
json-transformation-migration module in resources/db/changelog folder. The migration file should
have the following content in the changeset:

```
    <addColumn tableName="transformation_rules">
      <column name="description" type="VARCHAR(255)">
        <constraints nullable="false"/>
      </column>
    </addColumn>
```
After creating the migration file, run the database application 
(json-transformation-migration/src/main/java/com/navi/bootcamp/database/Database.java) to apply the changes.
