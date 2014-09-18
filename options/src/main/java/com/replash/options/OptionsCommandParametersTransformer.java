package com.replash.options;

import com.replash.BasicCommand;
import com.replash.CommandContext;
import com.replash.CommandParameters;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsCommandParametersTransformer {
    private final CommandLineParser commandLineParser;

    public OptionsCommandParametersTransformer(CommandLineParser commandLineParser) {
        this.commandLineParser = commandLineParser;
    }

    public CommandParametersWithOptions transform(CommandContext existingCommandContext, CommandParameters existingCommandParameters) throws ParseException {
        String commandName = existingCommandParameters.getCommandName();
        String[] existingArguments = convertToStringArray(existingCommandParameters.getArguments());

        Options options = getOptionsFromCommand(existingCommandContext.getCommand());

        CommandLine commandLine = commandLineParser.parse(options, existingArguments);

        Map<String, Object> optionsMap = convertCommandLineToOptionsMap(commandLine);

        return new CommandParametersWithOptions(commandName, commandLine.getArgs(), optionsMap);
    }

    protected Options getOptionsFromCommand(BasicCommand basicCommand) {
        if(basicCommand instanceof OptionsBasicCommand) {
            return ((OptionsBasicCommand) basicCommand).getOptions();
        }
        return new Options();
    }

    protected Map<String, Object> convertCommandLineToOptionsMap(CommandLine commandLine) {
        Map<String, Object> optionsMap = new HashMap<>();
        for(Option option : commandLine.getOptions()) {
            String key = option.getLongOpt();
            if(key == null) {
                key = option.getOpt();
            }
            String value = option.getValue();
            optionsMap.put(key, value);
        }
        return optionsMap;
    }

    private String[] convertToStringArray(Object[] arguments) {
        List<String> list = new ArrayList<>(arguments.length);
        for(Object obj : arguments) {
            list.add(obj.toString());
        }
        return list.toArray(new String[arguments.length]);
    }
}
