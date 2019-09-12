package org.gaborbalazs.smartplatform.loggingfilter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gaborbalazs.smartplatform.loggingfilter.configuration.LogConfiguration;
import org.gaborbalazs.smartplatform.loggingfilter.factory.LogTextFactory;
import org.gaborbalazs.smartplatform.loggingfilter.wrapper.BufferedRequestWrapper;
import org.gaborbalazs.smartplatform.loggingfilter.wrapper.BufferedResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter for logging request and response.
 * It can be customized via {@link LogTextFactory}.
 */
public class CustomLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLoggingFilter.class);
    private static final LogTextFactory LOG_TEXT_FACTORY = new LogTextFactory();

    private LogConfiguration logConfiguration;

    public CustomLoggingFilter(LogConfiguration logConfiguration) {
        this.logConfiguration = logConfiguration;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (LOGGER.isDebugEnabled()) {
            doFilterAndLog((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void doFilterAndLog(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        httpServletRequest = logConfiguration.isLogRequest() && logConfiguration.isIncludeRequestPayload() ? new BufferedRequestWrapper(httpServletRequest) : httpServletRequest;
        httpServletResponse = logConfiguration.isLogResponse() && logConfiguration.isIncludeResponsePayload() ? new BufferedResponseWrapper(httpServletResponse) : httpServletResponse;

        if (logConfiguration.isLogRequest()) {
            LOGGER.debug(LOG_TEXT_FACTORY.createRequestLogText(httpServletRequest, logConfiguration));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

        if (logConfiguration.isLogResponse()) {
            LOGGER.debug(LOG_TEXT_FACTORY.createResponseLogText(httpServletResponse, logConfiguration));
        }
    }
}
