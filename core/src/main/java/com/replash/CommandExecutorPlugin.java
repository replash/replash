package com.replash;

public interface CommandExecutorPlugin {
    boolean canExecute(BasicCommand basicCommand);
    void execute(CommandContext commandContext) throws Exception;
}
