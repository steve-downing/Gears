package org.suporma.gears.cli;

public interface CommandLineInterface {
    public CommandLineInterface print(String str);
    public CommandLineInterface print(int i);
    public CommandLineInterface println();
    public CommandLineInterface println(String str);
    
    public <T> T getUserInput(StringInterpreter<T> interpreter, String defaultString,
            boolean acceptNullResponse);
    
    public String getUserInput(boolean acceptEmptyResponse);
    
    public interface StringInterpreter<T> {
        T interpret(String str);
    }
}
