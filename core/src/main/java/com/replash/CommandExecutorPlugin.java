package com.replash;

/**
* Created by cbeattie on 24/08/14.
*/
public interface CommandExecutorPlugin {
    boolean canExecute(BasicCommand basicCommand);
    void execute(CommandContext commandContext) throws Exception;
}
