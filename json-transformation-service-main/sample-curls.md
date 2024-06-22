## Sample CURLs


### CURL for creating a rule 
```agsl
curl -X POST "http://localhost:8080/json-transformation-service/v1/transformation-rules/create" -H "accept: */*" -H "x-client-id: jhon.doe" -H "Content-Type: application/json" -d "{ \"comment\": \"Created\", \"ruleName\": \"assignment_rule\", \"ruleOwner\": \"jhon\", \"ruleSpecs\": [ { \"operation\": \"shift\", \"spec\": { \"rating\": { \"primary\": { \"value\": \"Rating\" }, \"*\": { \"max\": \"SecondaryRatings.&1.Range\", \"value\": \"SecondaryRatings.&1.Value\", \"$\": \"SecondaryRatings.&1.Id\" } } } }]}"
```

### CURl for changing status of rule
```agsl
curl -X PUT "http://localhost:8080/json-transformation-service/v1/transformation-rules/change-status" -H "accept: */*" -H "x-client-id: jhon.doe" -H "Content-Type: application/json" -d "{ \"finalStatus\": \"ACTIVE\", \"ruleName\": \"assignment_rule\", \"version\": 0}"
```

### CURL for transforming a JSON
```agsl
curl -X POST "http://localhost:8080/json-transformation-service/v1/json-transformation/transform" -H "accept: */*" -H "X-Correlation-Id: test" -H "x-client-id: jhon.doe" -H "Content-Type: application/json" -d "{ \"input\": { \"rating\": { \"primary\": { \"value\": 3 }, \"quality\": { \"value\": 3 } } }, \"requestId\": \"be466499-844a-49a4-aa25-a76ad98fa3bc\", \"ruleNames\": [ \"assignment_rule\" ]}"
```