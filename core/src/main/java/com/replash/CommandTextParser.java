package com.replash;

public interface CommandTextParser {
    CommandParameters parse(ReplashRuntime runtime, String commandText) throws CommandTextFormatException;
}
