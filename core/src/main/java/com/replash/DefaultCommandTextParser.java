package com.replash;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class DefaultCommandTextParser implements CommandTextParser {
    @Override
    public CommandParameters parse(ReplashRuntime runtime, String commandText) throws CommandTextFormatException {
        if(StringUtils.isEmpty(commandText)) {
            throw new CommandTextFormatException(commandText, "command text is empty");
        }

        String[] commandComponents = StringUtils.split(commandText, ' ');

        if(commandComponents.length < 1) {
            throw new CommandTextFormatException(commandText, "command text is missing command name");
        }

        return createCommandParameters(runtime, commandComponents);
    }

    protected CommandParameters createCommandParameters(ReplashRuntime runtime, String[] commandComponents) {
        String commandName = commandComponents[0];
        String[] arguments = Arrays.copyOfRange(commandComponents, 1, commandComponents.length);
        return new CommandParameters(commandName, arguments);
    }
}
