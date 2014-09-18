package com.replash;

import java.io.IOException;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;

/**
 * Created by cbeattie on 15/08/14.
 */
public class ConsoleAdapterImpl implements ConsoleAdapter {
    private final ConsoleReader consoleReader;

    public ConsoleAdapterImpl(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    @Override
    public void shutdown() {
        consoleReader.shutdown();
    }

    @Override
    public String readLine(String prompt) throws IOException {
        return consoleReader.readLine(prompt);
    }

    @Override
    public String readLine(String prompt, Character mask) throws IOException {
        return consoleReader.readLine(prompt, mask);
    }

    @Override
    public void print(CharSequence message) throws IOException {
        consoleReader.print(message);
    }

    @Override
    public void println(CharSequence message) throws IOException {
        consoleReader.println(message);
    }

    @Override
    public int getTerminalWidth() {
        return consoleReader.getTerminal().getWidth();
    }

    @Override
    public void addCompleter(Completer completer) {
        consoleReader.addCompleter(completer);
    }

    @Override
    public void removeCompleter(Completer completer) {
        consoleReader.removeCompleter(completer);
    }

    @Override
    public void flush() throws IOException {
        consoleReader.flush();
    }
}
