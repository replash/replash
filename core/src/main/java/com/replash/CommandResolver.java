package com.replash;

public interface CommandResolver {
    BasicCommand resolve(ReplashRuntime runtime, CommandParameters commandParameters);
}
