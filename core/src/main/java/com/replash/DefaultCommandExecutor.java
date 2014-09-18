package com.replash;

import java.util.Set;

public class DefaultCommandExecutor extends AbstractCommandExecutor {
    private final Set<CommandExecutorPlugin> commandExecutorPlugins;

    public DefaultCommandExecutor(CommandTextParser commandTextParser, CommandResolver commandResolver) {
        this(commandTextParser, commandResolver, ServiceLoaderUtils.load(CommandExecutorPlugin.class));
    }

    public DefaultCommandExecutor(CommandTextParser commandTextParser, CommandResolver commandResolver, Set<CommandExecutorPlugin> commandExecutorPlugins) {
        super(commandResolver, commandTextParser);
        this.commandExecutorPlugins = commandExecutorPlugins;
    }

    @Override
    protected void executeCommand(CommandContext executionContext) throws ReplashCommandExecutionException {
        CommandExecutorPlugin matchingPlugin = null;

        if(!commandExecutorPlugins.isEmpty()) {
            BasicCommand basicCommand = executionContext.getCommand();
            for (CommandExecutorPlugin commandExecutorPlugin : commandExecutorPlugins) {
                if(commandExecutorPlugin.canExecute(basicCommand)) {
                    if(matchingPlugin != null) {
                        throw new AmbiguousCommandExecutorPluginException();
                    }
                    matchingPlugin = commandExecutorPlugin;
                }
            }
        }

        try {
            Console.setConsoleAdapter(executionContext.getRuntime().getConsoleAdapter());

            if(matchingPlugin != null) {
                delegateToPlugin(executionContext, matchingPlugin);
            }
            else {
                executeCommandInternal(executionContext);
            }
        }
        catch (Exception e) {
            throw new ReplashCommandExecutionException(executionContext, e);
        }
        finally {
            Console.setConsoleAdapter(null);
        }
    }

    protected void executeCommandInternal(CommandContext commandContext) throws Exception {
        commandContext.getCommand().execute(commandContext);
    }

    protected void delegateToPlugin(CommandContext executionContext, CommandExecutorPlugin commandExecutorPlugin) throws Exception {
        commandExecutorPlugin.execute(executionContext);
    }
}
