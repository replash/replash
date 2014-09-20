package com.replash;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultReflectionCommandFactory implements ReflectionCommandFactory {
    @Override
    public List<ReflectionCommandResult> create(Class<?> commandsClass) {
        return createForNonCommandClass(commandsClass);
    }

    @Override
    public List<ReflectionCommandResult> create(Object commandsObject) {
        Class<?> commandsClass = commandsObject.getClass();
        List<ReflectionCommandResult> results = new ArrayList<>();
        for(Method method : commandsClass.getMethods()) {
            Command commandAnnotation = method.getAnnotation(Command.class);
            if(commandAnnotation != null) {
                ReflectionCommandResult reflectionCommandResult = createCommand(commandsClass, method, commandsObject, commandAnnotation);
                results.add(reflectionCommandResult);
            }
        }
        return results;
    }

    protected List<ReflectionCommandResult> createForNonCommandClass(Class<?> commandsClass) {
        List<ReflectionCommandResult> results = new ArrayList<>();
        for(Method method : commandsClass.getMethods()) {
            Command commandAnnotation = method.getAnnotation(Command.class);
            if(commandAnnotation != null) {
                ReflectionCommandResult reflectionCommandResult = createCommand(commandsClass, method, null, commandAnnotation);
                results.add(reflectionCommandResult);
            }
        }
        return results;
    }

    protected ReflectionCommandResult createCommand(Class<?> commandsClass, Method method, Object instance, Command commandAnnotation) {
        ClassLoader classLoader = commandsClass.getClassLoader();
        Class<?>[] interfaces = {CommandWithHelp.class};
        ReflectionInvocationHandler invocationHandler = new ReflectionInvocationHandler(commandsClass, method, instance);
        BasicCommand basicCommand = (BasicCommand)Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        String commandName = method.getName();
        if(!StringUtils.isEmpty(commandAnnotation.name())) {
            commandName = commandAnnotation.name();
        }
        return new ReflectionCommandResult(commandName, basicCommand);
    }

    private static class ReflectionInvocationHandler implements InvocationHandler {
        private final Class<?> commandsClass;
        private final Method commandMethod;
        private final Object instance;

        private ReflectionInvocationHandler(Class<?> commandsClass, Method commandMethod, Object instance) {
            this.commandsClass = commandsClass;
            this.commandMethod = commandMethod;
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if("execute".equals(methodName)) {
                return invokeExecute(proxy, method, args);
            }
            else if("printHelp".equals(methodName)) {
                return invokePrintHelp(proxy, method, args);
            }
            else {
                throw new ReplashException(String.format("Unexpected delegate method %s for class %s", methodName, proxy.getClass().getName()));
            }
        }

        private Object invokePrintHelp(Object proxy, Method method, Object[] args) throws Exception {
            CommandContext commandContext = (CommandContext)args[0];
            List<String> parts = new ArrayList<>();
            parts.add(commandContext.getCommandParameters().getCommandName());
            int argIndex = 0;
            for(Class<?> paramType : commandMethod.getParameterTypes()) {
                if(CommandContext.class.equals(paramType)) {
                    continue;
                }
                String paramName = String.format("arg%d", argIndex++);
                parts.add(String.format("<%s>", paramName));
            }
            String helpText = StringUtils.join(" ", parts);
            commandContext.getRuntime().getConsoleAdapter().println(helpText);
            return null;
        }

        private Object invokeExecute(Object proxy, Method method, Object[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            assert(args.length > 0);

            CommandContext commandContext = (CommandContext)args[0];

            Object delegate;
            if(instance == null) {
                delegate = commandsClass.newInstance();
            }
            else {
                delegate = instance;
            }

            Object[] methodArgs = translateCommandArgumentsToMethodArguments(commandContext, commandContext.getCommandParameters().getArguments(), commandMethod, commandMethod.getParameterTypes());
            if(!commandMethod.isAccessible()) {
                commandMethod.setAccessible(true);
            }
            commandMethod.invoke(delegate, methodArgs);
            return null;
        }

        private Object[] translateCommandArgumentsToMethodArguments(CommandContext commandContext, String[] arguments, Method method, Class<?>[] parametersTypes) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            List<Object> results = new ArrayList<>();
            int argIndex = 0;
            for(int paramIndex = 0; paramIndex < parametersTypes.length; ++paramIndex) {
                Class<?> parameterType = parametersTypes[paramIndex];

                if(CommandContext.class.equals(parameterType)) {
                    results.add(commandContext);
                    continue;
                }

                if(argIndex >= arguments.length) {
                    Annotation[] parameterAnnotations = method.getParameterAnnotations()[paramIndex];
                    Argument argumentAttribute = null;
                    for(Annotation annotation : parameterAnnotations) {
                        if(annotation.annotationType().equals(Argument.class)) {
                            argumentAttribute = (Argument)annotation;
                        }
                    }
                    if(argumentAttribute != null && !argumentAttribute.required()) {
                        results.add(null);
                        continue;
                    }
                    throw new ReplashCommandUsageException("Missing one or more arguments");
                }

                Object result = translateCommandArgumentToMethodArgument(arguments[argIndex++], parameterType);
                results.add(result);
            }

            if(argIndex < arguments.length) {
                throw new ReplashCommandUsageException("Too many arguments");
            }

            return results.toArray();
        }

        private Object translateCommandArgumentToMethodArgument(String commandArgument, Class<?> targetType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            if(commandArgument == null) {
                return null;
            }

            if(String.class.equals(targetType)) {
                return commandArgument;
            }

            if(targetType.isPrimitive()) {
                targetType = ClassUtils.primitiveToWrapper(targetType);
            }

            return targetType.getConstructor(String.class).newInstance(commandArgument.toString());
        }
    }
}
