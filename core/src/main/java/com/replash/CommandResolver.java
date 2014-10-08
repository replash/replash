package com.replash;

public interface CommandResolver {
    CommandResolutionContext resolve(ReplashRuntime runtime, CommandParameters commandParameters);
}
