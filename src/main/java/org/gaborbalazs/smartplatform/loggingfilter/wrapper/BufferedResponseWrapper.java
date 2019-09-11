package org.gaborbalazs.smartplatform.loggingfilter.wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.gaborbalazs.smartplatform.loggingfilter.io.CustomPrintWriter;
import org.gaborbalazs.smartplatform.loggingfilter.io.CustomServletOutputStream;

/**
 * ServletResponse wrapper for buffering response body.
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper {

    private final HttpServletResponse httpServletResponse;
    private CustomServletOutputStream customServletOutputStream;
    private CustomPrintWriter customPrintWriter;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     *
     * @throws IllegalArgumentException
     *             if the response is null
     */
    public BufferedResponseWrapper(HttpServletResponse response) {
        super(response);
        httpServletResponse = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (customServletOutputStream == null) {
            customServletOutputStream = new CustomServletOutputStream(httpServletResponse.getOutputStream());
        }
        return customServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (customPrintWriter == null) {
            customPrintWriter = new CustomPrintWriter(httpServletResponse.getWriter());
        }
        return customPrintWriter;
    }

    public String getBody() {
        if (customServletOutputStream != null) {
            return customServletOutputStream.getCopy();
        } else if (customPrintWriter != null) {
            return customPrintWriter.getCopy();
        } else {
            return null;
        }
    }
}
