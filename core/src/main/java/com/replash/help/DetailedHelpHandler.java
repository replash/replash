package com.replash.help;

import com.replash.BasicCommand;
import com.replash.CommandContext;

public interface DetailedHelpHandler {
    void execute(CommandContext commandContext) throws Exception;
    void execute(CommandContext commandContext, BasicCommand basicCommand) throws Exception;
}
