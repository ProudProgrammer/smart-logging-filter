package org.gaborbalazs.smartplatform.loggingfilter.configuration;

import org.gaborbalazs.smartplatform.loggingfilter.filter.CustomLoggingFilter;

/**
 * Configuration holder for {@link CustomLoggingFilter}.
 */
public class LogConfiguration {

    private static final String DEFAULT_REQUEST_MESSAGE_PREFIX = "Incoming request [";
    private static final String DEFAULT_REQUEST_MESSAGE_SUFFIX = "]";

    private static final String DEFAULT_RESPONSE_MESSAGE_PREFIX = "Outgoing response [";
    private static final String DEFAULT_RESPONSE_MESSAGE_SUFFIX = "]";

    private boolean logRequest = true;
    private boolean logResponse = true;
    private boolean includeRequestQueryString = true;
    private boolean includeRequestClientInfo = true;
    private boolean includeRequestHeaders = true;
    private boolean includeRequestPayload = true;
    private String requestMessagePrefix = DEFAULT_REQUEST_MESSAGE_PREFIX;
    private String requestMessageSuffix = DEFAULT_REQUEST_MESSAGE_SUFFIX;
    private boolean includeResponseHeaders = true;
    private boolean includeResponsePayload = true;
    private String responseMessagePrefix = DEFAULT_RESPONSE_MESSAGE_PREFIX;
    private String responseMessageSuffix = DEFAULT_RESPONSE_MESSAGE_SUFFIX;

    public boolean isLogRequest() {
        return logRequest;
    }

    public boolean isLogResponse() {
        return logResponse;
    }

    public boolean isIncludeRequestQueryString() {
        return includeRequestQueryString;
    }

    public boolean isIncludeRequestClientInfo() {
        return includeRequestClientInfo;
    }

    public boolean isIncludeRequestHeaders() {
        return includeRequestHeaders;
    }

    public boolean isIncludeRequestPayload() {
        return includeRequestPayload;
    }

    public String getRequestMessagePrefix() {
        return requestMessagePrefix;
    }

    public String getRequestMessageSuffix() {
        return requestMessageSuffix;
    }

    public boolean isIncludeResponseHeaders() {
        return includeResponseHeaders;
    }

    public boolean isIncludeResponsePayload() {
        return includeResponsePayload;
    }

    public String getResponseMessagePrefix() {
        return responseMessagePrefix;
    }

    public String getResponseMessageSuffix() {
        return responseMessageSuffix;
    }

    public void setLogRequest(boolean logRequest) {
        this.logRequest = logRequest;
    }

    public void setLogResponse(boolean logResponse) {
        this.logResponse = logResponse;
    }

    public void setIncludeRequestQueryString(boolean includeRequestQueryString) {
        this.includeRequestQueryString = includeRequestQueryString;
    }

    public void setIncludeRequestClientInfo(boolean includeRequestClientInfo) {
        this.includeRequestClientInfo = includeRequestClientInfo;
    }

    public void setIncludeRequestHeaders(boolean includeRequestHeaders) {
        this.includeRequestHeaders = includeRequestHeaders;
    }

    public void setIncludeRequestPayload(boolean includeRequestPayload) {
        this.includeRequestPayload = includeRequestPayload;
    }

    public void setRequestMessagePrefix(String requestMessagePrefix) {
        this.requestMessagePrefix = requestMessagePrefix;
    }

    public void setRequestMessageSuffix(String requestMessageSuffix) {
        this.requestMessageSuffix = requestMessageSuffix;
    }

    public void setIncludeResponseHeaders(boolean includeResponseHeaders) {
        this.includeResponseHeaders = includeResponseHeaders;
    }

    public void setIncludeResponsePayload(boolean includeResponsePayload) {
        this.includeResponsePayload = includeResponsePayload;
    }

    public void setResponseMessagePrefix(String responseMessagePrefix) {
        this.responseMessagePrefix = responseMessagePrefix;
    }

    public void setResponseMessageSuffix(String responseMessageSuffix) {
        this.responseMessageSuffix = responseMessageSuffix;
    }
}
