package com.replash;

import java.io.IOException;

import jline.console.completer.Completer;

/**
 * Created by cbeattie on 15/08/14.
 */
public interface ConsoleAdapter {
    void shutdown();

    String readLine(String prompt) throws IOException;

    String readLine(String prompt, Character mask) throws IOException;

    void print(CharSequence message) throws IOException;

    void println(CharSequence message) throws IOException;

    int getTerminalWidth();

    void addCompleter(Completer completer);

    void removeCompleter(Completer completer);

    void flush() throws IOException;
}
