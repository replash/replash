package com.replash;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractCommandExecutor implements CommandExecutor {
    protected final CommandTextParser commandTextParser;
    protected final CommandResolver commandResolver;

    public AbstractCommandExecutor(CommandResolver commandResolver, CommandTextParser commandTextParser) {
        this.commandResolver = commandResolver;
        this.commandTextParser = commandTextParser;
    }

    @Override
    public void execute(ReplashRuntime runtime, String commandText) throws ReplashException {
        if(isEmptyCommandText(commandText) && skipEmptyCommands()) {
            return;
        }
        else {
            CommandParameters commandParameters = parseCommandText(runtime, commandText);
            BasicCommand resolvedBasicCommand = resolveCommand(runtime, commandParameters);
            CommandContext executionContext = createExecutionContext(runtime, commandText, commandParameters, resolvedBasicCommand);
            executeCommand(executionContext);
        }
    }

    protected boolean isEmptyCommandText(String commandText) {
        return StringUtils.trim(commandText).isEmpty();
    }

    protected boolean skipEmptyCommands() {
        return true;
    }

    protected CommandParameters parseCommandText(ReplashRuntime runtime, String commandText) throws CommandTextFormatException {
        return commandTextParser.parse(runtime, commandText);
    }

    protected BasicCommand resolveCommand(ReplashRuntime runtime, CommandParameters commandParameters) throws UnknownCommandException {
        BasicCommand resolvedBasicCommand = commandResolver.resolve(runtime, commandParameters);
        if(resolvedBasicCommand == null) {
            throw new UnknownCommandException(commandParameters);
        }

        return resolvedBasicCommand;
    }

    protected CommandContext createExecutionContext(ReplashRuntime runtime, String commandText, CommandParameters commandParameters, BasicCommand basicCommand) throws ReplashException {
        return new CommandContext(runtime, commandText, commandParameters, basicCommand);
    }

    protected abstract void executeCommand(CommandContext executionContext) throws ReplashCommandExecutionException;
}
