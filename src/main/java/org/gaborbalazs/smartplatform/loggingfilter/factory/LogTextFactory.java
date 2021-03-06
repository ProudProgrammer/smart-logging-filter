package org.gaborbalazs.smartplatform.loggingfilter.factory;

import org.gaborbalazs.smartplatform.loggingfilter.configuration.LogConfiguration;
import org.gaborbalazs.smartplatform.loggingfilter.wrapper.BufferedRequestWrapper;
import org.gaborbalazs.smartplatform.loggingfilter.wrapper.BufferedResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Factory for request and response log texts.
 */
public class LogTextFactory {

    /**
     * Creator method for request log text.
     *
     * @param request          {@link HttpServletRequest} or {@link BufferedRequestWrapper} depends on payload
     * @param logConfiguration is logConfiguration holder
     * @return request log text
     */
    public <T extends HttpServletRequest> String createRequestLogText(T request, LogConfiguration logConfiguration) {
        StringBuilder logBuilder = new StringBuilder(logConfiguration.getRequestMessagePrefix());
        injectMethod(request, logBuilder);
        injectSeparator(logBuilder);
        injectUri(request, logBuilder);
        if (logConfiguration.isIncludeRequestQueryString()) {
            injectSeparator(logBuilder);
            injectQueryString(request, logBuilder);
        }
        if (logConfiguration.isIncludeRequestClientInfo()) {
            injectSeparator(logBuilder);
            injectClientInfo(request, logBuilder);
        }
        if (logConfiguration.isIncludeRequestHeaders()) {
            injectSeparator(logBuilder);
            Map<String, String> headers = createHeadersMap(request);
            injectHeaders(headers, logBuilder);
        }
        if (logConfiguration.isIncludeRequestPayload()) {
            injectSeparator(logBuilder);
            injectBody(logBuilder, ((BufferedRequestWrapper) request).getBody());
        }
        logBuilder.append(logConfiguration.getRequestMessageSuffix());
        return logBuilder.toString();
    }

    /**
     * Creator method for response log text.
     *
     * @param response         {@link HttpServletResponse} or {@link BufferedResponseWrapper} depends on payload
     * @param logConfiguration is logConfiguration holder
     * @return response log text
     */
    public <T extends HttpServletResponse> String createResponseLogText(T response, LogConfiguration logConfiguration) {
        StringBuilder logBuilder = new StringBuilder(logConfiguration.getResponseMessagePrefix());
        injectHttpStatus(response, logBuilder);
        if (logConfiguration.isIncludeResponseHeaders()) {
            injectSeparator(logBuilder);
            Map<String, String> headers = createHeadersMap(response);
            injectHeaders(headers, logBuilder);
        }
        if (logConfiguration.isIncludeResponsePayload()) {
            injectSeparator(logBuilder);
            injectBody(logBuilder, ((BufferedResponseWrapper) response).getBody());
        }
        logBuilder.append(logConfiguration.getResponseMessageSuffix());
        return logBuilder.toString();
    }

    private void injectSeparator(StringBuilder logBuilder) {
        logBuilder.append("; ");
    }

    private void injectHttpStatus(HttpServletResponse response, StringBuilder logBuilder) {
        logBuilder.append("status-code=");
        logBuilder.append(response.getStatus());
    }

    private void injectMethod(HttpServletRequest request, StringBuilder logBuilder) {
        logBuilder.append("method=");
        logBuilder.append(request.getMethod());
    }

    private void injectUri(HttpServletRequest request, StringBuilder logBuilder) {
        logBuilder.append("uri=");
        logBuilder.append(request.getRequestURI());
    }

    private void injectQueryString(HttpServletRequest request, StringBuilder logBuilder) {
        logBuilder.append("query=[");
        logBuilder.append(request.getQueryString() == null ? "" : request.getQueryString());
        logBuilder.append("]");
    }

    private void injectClientInfo(HttpServletRequest request, StringBuilder logBuilder) {
        logBuilder.append("client=");
        logBuilder.append(request.getRemoteAddr());
    }

    private void injectHeaders(Map<String, String> headers, StringBuilder logBuilder) {
        logBuilder.append("headers=[");
        headers.forEach((key, value) -> {
            logBuilder.append(key);
            logBuilder.append(":");
            logBuilder.append(value);
            logBuilder.append(", ");
        });
        if (logBuilder.charAt(logBuilder.length() - 2) == ',') {
            logBuilder.delete(logBuilder.length() - 2, logBuilder.length());
        }
        logBuilder.append("]");
    }

    private void injectBody(StringBuilder logBuilder, String body) {
        logBuilder.append("payload=");
        logBuilder.append(body);
    }

    private Map<String, String> createHeadersMap(HttpServletRequest request) {
        Map<String, String> headersMap = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            String headersLine = "";
            while (headers.hasMoreElements()) {
                String header = headers.nextElement();
                headersLine = headersLine.equals("") ? (headersLine + "\"" + header + "\"") : (headersLine + ";\"" + header + "\"");
            }
            headersMap.put(headerName, headersLine);
        }
        return headersMap;
    }

    private Map<String, String> createHeadersMap(HttpServletResponse response) {
        Map<String, String> headers = new LinkedHashMap<>();
        response.getHeaderNames().forEach(headerName -> headers.put(headerName, response.getHeaders(headerName).stream().map(header -> "\"" + header + "\"").collect(Collectors.joining(";"))));
        return headers;
    }
}
