package com.replash.options;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CliParser {
    Class<? extends CommandLineParser> parser() default BasicParser.class;
}
