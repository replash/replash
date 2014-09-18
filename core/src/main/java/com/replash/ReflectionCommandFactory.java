package com.replash;

import java.util.List;

public interface ReflectionCommandFactory {
    List<ReflectionCommandResult> create(Class<?> commandsClass);

    List<ReflectionCommandResult> create(Object commandsObject);
}
