package com.replash.options;

import com.replash.CommandParameters;

import java.util.HashMap;
import java.util.Map;

public class CommandParametersWithOptions extends CommandParameters {
    private Map<String, Object> options;

    public CommandParametersWithOptions(String command) {
        this(command, new String[]{});
    }

    public CommandParametersWithOptions(String commandName, String[] arguments) {
        this(commandName, arguments, new HashMap<String, Object>());
    }

    public CommandParametersWithOptions(String commandName, String[] arguments, Map<String, Object> options) {
        super(commandName, arguments);
        this.options = options;
    }

    public Map<String, Object> getOptions() {
        return options;
    }
}
