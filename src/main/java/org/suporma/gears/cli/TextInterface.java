package org.suporma.gears.cli;

import java.io.IOException;

public interface TextInterface {
    public void print(String str) throws IOException;
    public void println() throws IOException;
    public String getLine() throws IOException;
}
