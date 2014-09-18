package com.replash.options;

import com.replash.*;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;

/**
 * Created by cbeattie on 24/08/14.
 */
public class OptionsCommandExecutorPlugin implements CommandExecutorPlugin {
    private final CommandLineParser defaultParser = new BasicParser();

    @Override
    public boolean canExecute(BasicCommand basicCommand) {
        return basicCommand instanceof OptionsBasicCommand;
    }

    @Override
    public void execute(CommandContext commandContext) throws Exception {
        CommandContext transformedCommandContext = transform(commandContext);
        executeImpl(transformedCommandContext);
    }

    private CommandContext transform(CommandContext defaultCommandContext) throws CommandTextFormatException {
        String commandText = defaultCommandContext.getCommandText();

        CommandParameters transformedCommandParameters;
        try {
            transformedCommandParameters = transformCommandParameters(defaultCommandContext, defaultCommandContext.getCommandParameters());
        }
        catch (ParseException e) {
            throw new CommandTextFormatException(commandText, e.getMessage());
        }

        return new CommandContext(defaultCommandContext.getRuntime(), commandText, transformedCommandParameters, defaultCommandContext.getCommand());
    }

    protected CommandParameters transformCommandParameters(CommandContext existingCommandContext, CommandParameters existingCommandParameters) throws ParseException {
        CommandLineParser commandLineParser = getCommandLineParser(existingCommandContext.getCommand());
        OptionsCommandParametersTransformer transformer = new OptionsCommandParametersTransformer(commandLineParser);
        return transformer.transform(existingCommandContext, existingCommandParameters);
    }

    protected CommandLineParser getCommandLineParser(BasicCommand basicCommand) {
        CliParser cliParserAnnotation = basicCommand.getClass().getAnnotation(CliParser.class);
        if(cliParserAnnotation != null) {
            Class<? extends CommandLineParser> commandLineParserClass = cliParserAnnotation.parser();
            try {
                return commandLineParserClass.getConstructor().newInstance();
            }
            catch (Exception e) {
                // Do nothing; just fall through and use the default parser.
            }
        }

        return defaultParser;
    }

    protected void executeImpl(CommandContext commandContext) throws Exception {
        commandContext.getCommand().execute(commandContext);
    }
}
