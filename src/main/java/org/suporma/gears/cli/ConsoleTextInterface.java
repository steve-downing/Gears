package org.suporma.gears.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleTextInterface implements TextInterface {
    public String getLine() throws IOException {
        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
        return bin.readLine();
    }

    public void print(String str) {
        System.out.print(str);
    }

    public void println() throws IOException {
        System.out.println();
    }
}
