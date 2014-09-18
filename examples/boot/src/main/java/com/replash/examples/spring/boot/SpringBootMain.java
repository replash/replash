package com.replash.examples.spring.boot;

import com.replash.Console;
import com.replash.Command;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SpringBootMain {
    @Bean
    public Object customBean() {
        return new Object() {
            @Command
            public void hello() {
                Console.println("Hello World!");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }
}
