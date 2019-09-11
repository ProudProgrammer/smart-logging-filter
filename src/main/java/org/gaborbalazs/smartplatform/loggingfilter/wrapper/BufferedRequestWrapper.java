package org.gaborbalazs.smartplatform.loggingfilter.wrapper;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.gaborbalazs.smartplatform.loggingfilter.io.CustomServletInputStream;

/**
 * ServletRequest wrapper for buffering request body.
 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private final InputStream inputStream;
    private final String body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     *
     * @throws IllegalArgumentException
     *             if the request is null
     */
    public BufferedRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        inputStream = IOUtils.toInputStream(body, request.getCharacterEncoding());
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CustomServletInputStream(inputStream);
    }

    public String getBody() {
        return body;
    }
}
