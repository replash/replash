package com.replash.commands.builtin;

import com.replash.*;
import com.replash.help.DetailedHelpHandler;
import com.replash.help.SimpleHelpHandler;

import java.io.IOException;

public class HelpCommand implements CommandWithHelp {
    private final SimpleHelpHandler simpleHelpHandler;
    private final DetailedHelpHandler detailedHelpHandler;

    public HelpCommand(SimpleHelpHandler simpleHelpHandler, DetailedHelpHandler detailedHelpHandler) {
        this.simpleHelpHandler = simpleHelpHandler;
        this.detailedHelpHandler = detailedHelpHandler;
    }

    @Override
    public void execute(CommandContext commandContext) throws Exception {
        if(commandContext.getCommandParameters().getArguments().length == 0) {
            simpleHelpHandler.execute(commandContext);
        }
        else {
            detailedHelpHandler.execute(commandContext);
        }
    }

    @Override
    public void printHelp(CommandContext commandContext, CommandParameters detailedCommandParameters, ConsoleAdapter consoleAdapter) throws IOException {
        Console.println("Displays the list of commands or help on a specific command");
    }
}
