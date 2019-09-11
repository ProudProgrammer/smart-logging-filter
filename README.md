# Smart Platform - Logging Filter
Universal logging filter for Smart Platform
### Usage:
 - Set debug level to DEBUG, eg.
`<logger name="org.gaborbalazs.smartplatform.loggingfilter.filter.CustomLoggingFilter" level="DEBUG"/>`
- Make CustomLoggingFilter visible to IoC container, eg. in Spring
```$xslt
@Bean
CustomLoggingFilter customLoggingFilter() {
    LogTextFactory logTextFactory = new LogTextFactory();
    CustomLoggingFilter customLoggingFilter = new CustomLoggingFilter(logTextFactory());
    return customLoggingFilter;
}
```
### Default settings:
- CustomLoggingFilter.setLogRequest(true);
- CustomLoggingFilter.setLogResponse(true);
- LogTextFactory.setIncludeRequestQueryString(true);
- LogTextFactory.setIncludeRequestClientInfo(true);
- LogTextFactory.setIncludeRequestHeaders(true);
- LogTextFactory.setIncludeRequestPayload(true);
- LogTextFactory.setRequestMessagePrefix("Incoming request [");
- LogTextFactory.setRequestMessageSuffix("]");
- LogTextFactory.setIncludeResponseHeaders(true);
- LogTextFactory.setIncludeResponsePayload(true);
- LogTextFactory.setResponseMessagePrefix("Outgoing response [");
- LogTextFactory.setResponseMessageSuffix("]);
