package com.replash.examples.spring;

import com.replash.PromptProvider;
import com.replash.StaticTextPromptProvider;
import com.replash.spring.SpringReplashRunnerBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringMain {
    @Bean
    public PromptProvider promptProvider() {
        return new StaticTextPromptProvider("#> ");
    }

    @Bean
    public SpringReplashRunnerBean springReplashRunnerBean() {
        return new SpringReplashRunnerBean();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringMain.class);
        applicationContext.start();
        applicationContext.registerShutdownHook();
    }
}
