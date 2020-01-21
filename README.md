# Smart Platform - Logging Filter
Universal logging filter for Smart Platform
### Maven profiles
```
Without tests: mvn clean install -Pfast
```
### Usage
 - Set debug level to DEBUG, eg.
```
<logger name="org.gaborbalazs.smartplatform.loggingfilter.filter.CustomLoggingFilter" level="DEBUG"/>
```
- Make CustomLoggingFilter visible to IoC container, eg. in Spring
```
@Bean
CustomLoggingFilter customLoggingFilter() {
    LogConfiguration logConfiguration = new LogConfiguration();
    CustomLoggingFilter customLoggingFilter = new CustomLoggingFilter(logConfiguration);
    return customLoggingFilter;
}
```
### Default settings
```
logConfiguration.setLogRequest(true);
logConfiguration.setLogResponse(true);
logConfiguration.setIncludeRequestQueryString(true);
logConfiguration.setIncludeRequestClientInfo(true);
logConfiguration.setIncludeRequestHeaders(true);
logConfiguration.setIncludeRequestPayload(true);
logConfiguration.setRequestMessagePrefix("Incoming request [");
logConfiguration.setRequestMessageSuffix("]");
logConfiguration.setIncludeResponseHeaders(true);
logConfiguration.setIncludeResponsePayload(true);
logConfiguration.setResponseMessagePrefix("Outgoing response [");
logConfiguration.setResponseMessageSuffix("]);
```
### Dependants
- https://github.com/ProudProgrammer/smart-lottery-service
- https://github.com/ProudProgrammer/smart-edge-service
### System architecture of Smart Platform
Applied software development techniques:
- Microservice Architecture
- API Gateway Pattern

![System Architecture](https://raw.githubusercontent.com/ProudProgrammer/smart-tools/master/plantuml/system-architecture.png)
