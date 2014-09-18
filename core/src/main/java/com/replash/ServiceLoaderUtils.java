package com.replash;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

/**
* Created by cbeattie on 24/08/14.
*/
public class ServiceLoaderUtils {
    private ServiceLoaderUtils() {}

    public static <T> Set<T> load(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        Iterator<T> commandExecutorIterator = serviceLoader.iterator();
        Set<T> resultSet = new HashSet<>();
        while(commandExecutorIterator.hasNext()) {
            resultSet.add(commandExecutorIterator.next());
        }
        return resultSet;
    }
}
