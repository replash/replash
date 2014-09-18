package com.replash.help;

import com.replash.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultDetailedHelpHandler implements DetailedHelpHandler {
    private final CommandTextParser commandTextParser;
    private final CommandResolver commandResolver;

    public DefaultDetailedHelpHandler(CommandTextParser commandTextParser, CommandResolver commandResolver) {
        this.commandTextParser = commandTextParser;
        this.commandResolver = commandResolver;
    }

    @Override
    public void execute(CommandContext commandContext) throws Exception {
        List<String> commandComponents = new ArrayList<>();
        for(Object arg : commandContext.getCommandParameters().getArguments()) {
            commandComponents.add(arg.toString());
        }
        String newCommandText = StringUtils.join(commandComponents, " ");

        CommandParameters commandParameters = commandTextParser.parse(commandContext.getRuntime(), newCommandText);
        BasicCommand basicCommand = commandResolver.resolve(commandContext.getRuntime(), commandParameters);
        if(basicCommand == null) {
            throw new UnknownCommandException(commandParameters);
        }

        ConsoleAdapter consoleAdapter = commandContext.getRuntime().getConsoleAdapter();
        if(basicCommand instanceof CommandWithHelp) {
            ((CommandWithHelp) basicCommand).printHelp(commandContext, commandParameters, consoleAdapter);
        }
        else {
            Console.println("No help available for this command");
        }
    }

    @Override
    public void execute(CommandContext commandContext, BasicCommand basicCommand) throws Exception {
        ConsoleAdapter consoleAdapter = commandContext.getRuntime().getConsoleAdapter();
        if(basicCommand instanceof CommandWithHelp) {
            ((CommandWithHelp) basicCommand).printHelp(commandContext, commandContext.getCommandParameters(), consoleAdapter);
        }
        else {
            Console.println("No help available for this command");
        }
    }
}
