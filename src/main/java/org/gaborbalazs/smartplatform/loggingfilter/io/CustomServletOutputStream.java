package org.gaborbalazs.smartplatform.loggingfilter.io;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * OutputStream for buffering servlet response body.
 */
public class CustomServletOutputStream extends ServletOutputStream {

    private final OutputStream outputStream;
    private final StringBuilder stringBuilder;

    public CustomServletOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        stringBuilder = new StringBuilder();
    }

    @Override
    public void write(int c) throws IOException {
        stringBuilder.append((char) c);
        outputStream.write(c);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException();
    }

    public String getCopy() {
        return stringBuilder.toString();
    }
}
