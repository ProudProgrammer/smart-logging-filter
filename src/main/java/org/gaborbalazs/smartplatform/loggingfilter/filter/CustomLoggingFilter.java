package org.gaborbalazs.smartplatform.loggingfilter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    private boolean logRequest = true;
    private boolean logResponse = true;

    private LogTextFactory logTextFactory;

    public CustomLoggingFilter(LogTextFactory logTextFactory) {
        this.logTextFactory = logTextFactory;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (LOGGER.isDebugEnabled()) {
            BufferedRequestWrapper bufferedRequestWrapper = new BufferedRequestWrapper((HttpServletRequest) request);
            BufferedResponseWrapper bufferedResponseWrapper = new BufferedResponseWrapper((HttpServletResponse) response);

            if (logRequest) {
                LOGGER.debug(logTextFactory.createRequestLogText(bufferedRequestWrapper));
            }

            filterChain.doFilter(bufferedRequestWrapper, bufferedResponseWrapper);

            if (logResponse) {
                LOGGER.debug(logTextFactory.createResponseLogText(bufferedResponseWrapper));
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void setLogRequest(boolean logRequest) {
        this.logRequest = logRequest;
    }

    public void setLogResponse(boolean logResponse) {
        this.logResponse = logResponse;
    }
}

