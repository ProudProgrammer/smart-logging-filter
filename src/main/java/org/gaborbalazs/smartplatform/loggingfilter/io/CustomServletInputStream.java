package org.gaborbalazs.smartplatform.loggingfilter.io;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * InputStream for holding ServletRequest body as stream.
 */
public class CustomServletInputStream extends ServletInputStream {

    private final InputStream inputStream;
    private boolean finished = false;

    public CustomServletInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        int data = inputStream.read();
        if (data == -1) {
            finished = true;
        }
        return data;
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }
}
