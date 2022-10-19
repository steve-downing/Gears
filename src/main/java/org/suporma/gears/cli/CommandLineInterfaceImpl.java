package org.suporma.gears.cli;

import java.io.IOException;

public class CommandLineInterfaceImpl implements CommandLineInterface {
    private final TextInterface textInterface;
    
    public CommandLineInterfaceImpl(TextInterface textInterface) {
        this.textInterface = textInterface;
    }
    
    @Override
    public <T> T getUserInput(StringInterpreter<T> interpreter, String defaultString,
            boolean acceptNullResponse) {
        T response = null;
        boolean userHasEnteredResponse = false;
        while ((!userHasEnteredResponse || !acceptNullResponse) && response == null) {
            String textResponse = getText(defaultString);
            if (textResponse == null || textResponse.isEmpty()) {
                textResponse = defaultString;
            }
            response = interpreter.interpret(textResponse);
            userHasEnteredResponse = true;
        }
        return response;
    }
    
    @Override
    public String getUserInput(boolean acceptEmptyResponse) {
        while (true) {
            String textResponse = getText("");
            if (!textResponse.isEmpty()) return textResponse;
        }
    }
    
    private String getText(String defaultValue) {
        print("> ");
        if (defaultValue != null && !defaultValue.isEmpty()) {
            print("[").print(defaultValue).print("] ");
        }
        String message = defaultValue;
        try {
            String line = textInterface.getLine();
            if (line != null && !line.isEmpty()) {
                message = line;
            }
        } catch (IOException e) {
            // Nothing to do here.
        }
        return message;
    }

    @Override
    public CommandLineInterface print(String str) {
        try {
            textInterface.print(str);
        } catch (IOException e) {
            // Nothing to do here.
        }
        return this;
    }

    @Override
    public CommandLineInterface println() {
        try {
            textInterface.println();
        } catch (IOException e) {
            // Nothing to do here.
        }
        return this;
    }

    @Override
    public CommandLineInterface println(String str) {
        print(str);
        println();
        return this;
    }

    @Override
    public CommandLineInterface print(int i) {
        return print(Integer.toString(i));
    }
}
