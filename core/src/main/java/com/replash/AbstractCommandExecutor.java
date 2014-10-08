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
            CommandResolutionContext resolutionContext = resolveCommand(runtime, commandParameters);
            CommandContext executionContext = createExecutionContext(runtime, commandText, resolutionContext);
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

    protected CommandResolutionContext resolveCommand(ReplashRuntime runtime, CommandParameters commandParameters) throws UnknownCommandException {
        CommandResolutionContext resolutionContext = commandResolver.resolve(runtime, commandParameters);
        if(resolutionContext == null) {
            throw new UnknownCommandException(commandParameters);
        }

        return resolutionContext;
    }

    protected CommandContext createExecutionContext(ReplashRuntime runtime, String commandText, CommandResolutionContext resolutionContext) throws ReplashException {
        return new CommandContext(runtime, commandText, resolutionContext);
    }

    protected abstract void executeCommand(CommandContext executionContext) throws ReplashCommandExecutionException;
}
