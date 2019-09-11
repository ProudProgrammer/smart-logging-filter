package org.gaborbalazs.smartplatform.loggingfilter.io;

import java.io.PrintWriter;

/**
 * PrintWriter for buffering servlet response body.
 */
public class CustomPrintWriter extends PrintWriter {

    private final StringBuilder stringBuilder;

    public CustomPrintWriter(PrintWriter printWriter) {
        super(printWriter);
        stringBuilder = new StringBuilder();
    }

    @Override
    public void write(int c) {
        stringBuilder.append((char) c);
        super.write(c);
    }

    @Override
    public void write(char[] chars, int offset, int length) {
        stringBuilder.append(chars, offset, length);
        super.write(chars, offset, length);
    }

    @Override
    public void write(String string, int offset, int length) {
        stringBuilder.append(string, offset, length);
        super.write(string, offset, length);
    }

    public String getCopy() {
        return stringBuilder.toString();
    }
}
