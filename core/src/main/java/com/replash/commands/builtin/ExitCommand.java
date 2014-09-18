package com.replash.commands.builtin;

import com.replash.*;

import java.io.IOException;

public class ExitCommand implements CommandWithHelp {
    @Override
    public void execute(CommandContext executionContext) {
        executionContext.getRuntime().shutdownRequested();
    }

    @Override
    public void printHelp(CommandContext commandContext, CommandParameters detailedCommandParameters, ConsoleAdapter consoleAdapter) throws IOException {
        Console.println("Exit the application");
    }
}
