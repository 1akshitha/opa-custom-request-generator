# Opa Custom Request Generator
This is the custom request generator implementation of WSO2 API manager integration with Open policy agent.

In this implementation, I have a custom implementation of the base [org.apache.synapse.mediators.opa.OPARequestGenerator](https://github.com/wso2/wso2-synapse/blob/v2.1.7-wso2v271/modules/extensions/src/main/java/org/apache/synapse/mediators/opa/OPARequestGenerator.java) class.  

## OPA Input payload
It will send the transport headers of the request to OPA as the opa payload.
```json
{
    "transportHeaders": {
          "Origin": "https://localhost:9443",
          "Connection": "keep-alive",
          "Referer": "https://localhost:9443/",
          "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36",
          "Sec-Fetch-Dest": "empty",
          "Sec-Fetch-Site": "same-site",
          "Host": "localhost:8243",
          "Accept-Encoding": "gzip, deflate, br",
          "accept": "*/*",
          "Sec-Fetch-Mode": "cors",
          "activityid": "263617be-bb5a-4a6b-aa15-e6addff1f49b",
          "sec-ch-ua": "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"",
          "sec-ch-ua-mobile": "?0",
          "sec-ch-ua-platform": "\"macOS\"",
          "Accept-Language": "en-GB,en-US;q=0.9,en;q=0.8"
      }
  }
```

## OPA Response
This will not evaluate a rule directly and will evaluate just the policy. Implementation expects a response as follows.

```json
{
    "allow": true,
    "message": "Verified with OPA"
}
```

## OPA Policy
[employee-attendance.rego](https://github.com/1akshitha/opa-custom-request-generator/blob/main/employee-attendance.rego) 
is the sample policy that will just check the origin header and return `allow` with a `message`. This message will be added 
to the transport headers if `allow` is true.

## WSO2 Policy definition
[opaPolicy.j2](https://github.com/1akshitha/opa-custom-request-generator/blob/main/opaPolicy.j2) file can be used to create a new policy in WSO2 API manager API policy framework. I have only parameterized
the `serverURL`, `policy` and `requestGenerator` and omitted the rule attribute. This way we can retrieve the policy decision 
of entire policy without specifying a rule.