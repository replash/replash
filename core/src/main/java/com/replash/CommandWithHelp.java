package com.replash;

import java.io.IOException;

public interface CommandWithHelp extends BasicCommand {
    void printHelp(CommandContext commandContext, CommandParameters detailedCommandParameters, ConsoleAdapter consoleAdapter) throws IOException;
}
