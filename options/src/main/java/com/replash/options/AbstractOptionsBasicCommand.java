package com.replash.options;

import com.replash.CommandContext;
import com.replash.CommandParameters;
import com.replash.Console;
import com.replash.ConsoleAdapter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public abstract class AbstractOptionsBasicCommand implements OptionsBasicCommand {
    private final Options options;

    protected AbstractOptionsBasicCommand(Options options) {
        this.options = options;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandContext commandContext) throws Exception {
        CommandParameters commandParameters = commandContext.getCommandParameters();
        execute(commandContext,
                commandContext.getRuntime().getConsoleAdapter(),
                commandParameters.getCommandName(),
                commandParameters.getArguments(),
                ((CommandParametersWithOptions) commandParameters).getOptions());
    }

    @Override
    public void printHelp(CommandContext commandContext, CommandParameters detailedCommandParameters, ConsoleAdapter consoleAdapter) throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(
                printWriter,
                consoleAdapter.getTerminalWidth(),
                detailedCommandParameters.getCommandName(),
                null,
                getOptions(),
                2,
                2,
                null,
                true);
        Console.println(stringWriter.toString());
    }

    protected abstract void execute(CommandContext executionContext, ConsoleAdapter consoleAdapter, String commandName, Object[] arguments, Map<String, Object> options) throws Exception;
}
