package com.replash.commands.builtin;

import com.replash.*;

import java.io.IOException;

public class HelpCommand implements CommandWithHelp {
    private final HelpCommandHandler commandHandler;

    public HelpCommand(HelpCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void execute(CommandContext commandContext) throws Exception {
        if(commandContext.getCommandParameters().getArguments().length == 0) {
            commandHandler.printHelp(commandContext);
        }
        else {
            commandHandler.printHelpWithContext(commandContext);
        }
    }

    @Override
    public void printHelp(CommandContext commandContext, CommandParameters detailedCommandParameters, ConsoleAdapter consoleAdapter) throws IOException {
        Console.println("Displays the list of commands or help on a specific command");
    }
}
