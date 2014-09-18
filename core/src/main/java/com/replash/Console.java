package com.replash;

import java.io.IOException;

public class Console {
    private static ThreadLocal<ConsoleAdapter> consoleAdapter = new ThreadLocal<>();

    public static ConsoleAdapter getConsoleAdapter() {
        return consoleAdapter.get();
    }

    public static void setConsoleAdapter(ConsoleAdapter consoleAdapter) {
        Console.consoleAdapter.set(consoleAdapter);
    }

    public static void print(CharSequence message) {
        try {
            getConsoleAdapterOrThrow().print(message);
        }
        catch (IOException e) {
            throw new ConsoleIOException(e);
        }
    }

    public static void print(ConsolePrintable printable) {
        try {
            printable.print(getConsoleAdapterOrThrow());
        }
        catch (IOException e) {
            throw new ConsoleIOException(e);
        }
    }

    public static void println() {
        println("");
    }

    public static void println(CharSequence message) {
        try {
            getConsoleAdapterOrThrow().println(message);
        }
        catch (IOException e) {
            throw new ConsoleIOException(e);
        }
    }

    public static void println(ConsolePrintable printable) {
        try {
            printable.print(getConsoleAdapterOrThrow());
            println();
        }
        catch (IOException e) {
            throw new ConsoleIOException(e);
        }
    }

    private static ConsoleAdapter getConsoleAdapterOrThrow() {
        ConsoleAdapter consoleAdapter = getConsoleAdapter();
        if(consoleAdapter == null) {
            throw new ConsoleAdapterUnavailableException();
        }
        return consoleAdapter;
    }

}
